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

    /*
    Внедрение зависимостей
     */
    @Autowired
    public DoctorValidator(DoctorService doctorService, SpecializationValidator specializationValidator,
                           SpecializationIdValidator specializationIdValidator, ProfileValidator profileValidator,
                           ProfileIdValidator profileIdValidator) {
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

        //проверяем уникальность номера телефона, отсутствие пользователя с таким номером телефона
        Optional<Doctor> existingDoctor = doctorService.findByPhone(doctor.getPhone());
        if (existingDoctor.isPresent() && doctor.getId() != existingDoctor.get().getId()) {
            errors.rejectValue("phone", "", "Доктор с таким номером телефона уже существует!");
        }

        //проверяем уникальность номера СНИЛС, отсутствие пользователя с таким СНИЛС
        existingDoctor = doctorService.findBySnils(doctor.getSnils());
        if (existingDoctor.isPresent() && doctor.getId() != existingDoctor.get().getId()) {
            errors.rejectValue("snils", "", "Доктор с таким номером СНИЛС уже существует!");
        }

        //проверяем уникальность ИНН, отсутствие пользователя с таким ИНН
        existingDoctor = doctorService.findByInn(doctor.getInn());
        if (existingDoctor.isPresent() && doctor.getId() != existingDoctor.get().getId()) {
            errors.rejectValue("inn", "", "Доктор с таким номером ИНН уже существует!");
        }

        // проверяем есть ли id у специализации, валидируем ее id, сама специализация была провалидирована при создании
        if (doctor.getSpecialization().getId() != null) {
            specializationIdValidator.validate(doctor.getSpecialization(), errors);
        }

        //если у пользователя есть доступ к системе, значит должен быть профиль не равен null, если доступа нет, то
        // профиля нет - null, поэтому проверка на null делается здесь, а не в dto
        if (doctor.getAccessToSystem()) {

            //проверяем если нет профиля то ошибка
            if (doctor.getProfile() == null) {
                errors.rejectValue("profile", "", "У доктора есть доступ к системе, но отсутствует профиль!");
            } else {
                //если профиль есть, проверяем если есть id у профиля, валидируем его id
                if (doctor.getProfile().getId() != null) {
                    profileIdValidator.validate(doctor.getProfile(), errors);
                }
                //валидируем сам профиль, он может быть создан новый или изменен существующий
                profileValidator.validate(doctor.getProfile(), errors);
            }

        } else {

            //проверяем отсутствие профайла у доктора в случае отсутствия доступа к системе, если есть профайл, то ошибка
            if (doctor.getProfile() != null) {
                errors.rejectValue("profile", "", "У данного доктора нет доступа к системе, но при этом есть профиль, его не должно быть!");
            }
        }
    }
}
