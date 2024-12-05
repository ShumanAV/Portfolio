package ru.shuman.Project_Aibolit_Server.util.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.shuman.Project_Aibolit_Server.models.Doctor;
import ru.shuman.Project_Aibolit_Server.services.DoctorService;

import java.util.Optional;

import static ru.shuman.Project_Aibolit_Server.util.GeneralMethods.searchNameFieldInParentEntity;

@Component
public class DoctorValidator implements Validator {

    private final DoctorService doctorService;
    private final SpecializationValidator specializationValidator;
    private final SpecializationIdValidator specializationIdValidator;
    private final ProfileValidator profileValidator;
    private final ProfileIdValidator profileIdValidator;

    @Autowired
    public DoctorValidator(DoctorService doctorService, SpecializationValidator specializationValidator,
                           SpecializationIdValidator specializationIdValidator, ProfileValidator profileValidator, ProfileIdValidator profileIdValidator) {
        this.doctorService = doctorService;
        this.specializationValidator = specializationValidator;
        this.specializationIdValidator = specializationIdValidator;
        this.profileValidator = profileValidator;
        this.profileIdValidator = profileIdValidator;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return Doctor.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Doctor doctor = (Doctor) o;

        String field = searchNameFieldInParentEntity(errors, doctor.getClass());

        // Блок проверки отсутствия пользователя с таким телефоном
        Optional<Doctor> existingDoctor = doctorService.findByPhone(doctor.getPhone());
        if (existingDoctor.isPresent() && doctor.getId() != existingDoctor.get().getId()) {
            errors.rejectValue(field == null ? "phone" : field, "", "Доктор с таким номером телефона уже существует!");
        }

        // Блок проверки отсутствия пользователя с таким СНИЛС
        existingDoctor = doctorService.findBySnils(doctor.getSnils());
        if (existingDoctor.isPresent() && doctor.getId() != existingDoctor.get().getId()) {
            errors.rejectValue(field == null ? "snils" : field, "", "Доктор с таким номером СНИЛС уже существует!");
        }

        // Блок проверки отсутствия пользователя с таким ИНН
        existingDoctor = doctorService.findByInn(doctor.getInn());
        if (existingDoctor.isPresent() && doctor.getId() != existingDoctor.get().getId()) {
            errors.rejectValue(field == null ? "inn" : field, "", "Доктор с таким номером ИНН уже существует!");
        }

        // Блок валидации специализации у пользователя
        if (doctor.getSpecialization() != null) {
            specializationIdValidator.validate(doctor.getSpecialization(), errors);

            // Для валидации специализации потребуется id, если есть id то валидируем, в случае отсутствия id -
            // ошибка об отсутствии id будет сформирована выше в валидаторе id специализации
            if (doctor.getSpecialization().getId() != null) {
                specializationValidator.validate(doctor.getSpecialization(), errors);
            }
        }

        // Блок проверки профиля пользователя, если у пользователя есть доступ к системе, значит должен быть профиль
        // не равен null
        if (doctor.getAccessToSystem() != null) {
            if (doctor.getAccessToSystem()) {

                // Блок проверки наличия профиля у пользователя
                if (doctor.getProfile() == null) {
                    errors.rejectValue(field == null ? "profile" : field, "", "У доктора есть доступ к системе, но отсутствует профиль!");
                } else {
                    if (doctor.getProfile().getId() != null) {
                        profileIdValidator.validate(doctor.getProfile(), errors);
                    }
                    profileValidator.validate(doctor.getProfile(), errors);
                }

            } else {

                // Блок проверки отсутствия профайла у пользователя в случае отсутствия доступа к системе
                if (doctor.getProfile() != null) {
                    errors.rejectValue(field == null ? "profile" : field, "", "У данного доктора нет доступа к системе, но при этом есть профиль, его не должно быть!");
                }
            }
        }
    }
}
