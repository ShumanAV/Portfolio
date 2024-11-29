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
    private final UserValidator userValidator;
    private final UserIdValidator userIdValidator;

    @Autowired
    public DoctorValidator(DoctorService doctorService, SpecializationValidator specializationValidator,
                           SpecializationIdValidator specializationIdValidator, UserValidator userValidator, UserIdValidator userIdValidator) {
        this.doctorService = doctorService;
        this.specializationValidator = specializationValidator;
        this.specializationIdValidator = specializationIdValidator;
        this.userValidator = userValidator;
        this.userIdValidator = userIdValidator;
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
        Optional<Doctor> existingUser = doctorService.findByPhone(doctor.getPhone());
        if (existingUser.isPresent() && doctor.getId() != existingUser.get().getId()) {
            errors.rejectValue(field == null ? "phone": field, "", "Доктор с таким номером телефона уже существует!");
        }

        // Блок проверки отсутствия пользователя с таким СНИЛС
        existingUser = doctorService.findBySnils(doctor.getSnils());
        if (existingUser.isPresent() && doctor.getId() != existingUser.get().getId()) {
            errors.rejectValue(field == null ? "snils": field, "", "Доктор с таким номером СНИЛС уже существует!");
        }

        // Блок проверки отсутствия пользователя с таким ИНН
        existingUser = doctorService.findByInn(doctor.getInn());
        if (existingUser.isPresent() && doctor.getId() != existingUser.get().getId()) {
            errors.rejectValue(field == null ? "inn": field, "", "Доктор с таким номером ИНН уже существует!");
        }

        // Блог проверки наличия специализации у пользователя
        if (doctor.getSpecialization() == null) {
            errors.rejectValue(field == null ? "specialization": field, "", "Поле специализация не заполнено!");

        } else {
            // Блог проверки наличия Id специализации у пользователя
            specializationIdValidator.validate(doctor.getSpecialization(), errors);
            specializationValidator.validate(doctor.getSpecialization(), errors);
        }

        // Блок проверки профиля пользователя
        if (doctor.isAccessToSystem()) {

            // Блок проверки наличия профайла у пользователя
            if (doctor.getUser() == null) {
                errors.rejectValue(field == null ? "user": field, "", "У доктора есть доступ к системе, но отсутствует профиль!");
            } else {
                if (doctor.getUser().getId() != null) {
                    userIdValidator.validate(doctor.getUser(), errors);
                }
                userValidator.validate(doctor.getUser(), errors);
            }

        } else {

            // Блок проверки отсутствия профайла у пользователя в случае отсутствия доступа к системе
            if (doctor.getUser() != null) {
                errors.rejectValue(field == null ? "user": field, "", "У данного доктора нет доступа к системе, но при этом есть профайл, его не должно быть!");
            }
        }
    }
}
