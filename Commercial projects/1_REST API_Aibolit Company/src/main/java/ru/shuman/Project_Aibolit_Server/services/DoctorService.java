package ru.shuman.Project_Aibolit_Server.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.shuman.Project_Aibolit_Server.models.*;
import ru.shuman.Project_Aibolit_Server.repositories.DoctorRepository;
import ru.shuman.Project_Aibolit_Server.util.GeneralMethods;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static ru.shuman.Project_Aibolit_Server.util.GeneralMethods.copyNonNullProperties;

@Service
@Transactional(readOnly = true)
public class DoctorService {

    private final DoctorRepository doctorRepository;
    private final ProfileService profileService;
    private final SpecializationService specializationService;

    /*
    Внедрение зависимостей
     */
    @Autowired
    public DoctorService(DoctorRepository doctorRepository, ProfileService profileService, SpecializationService specializationService) {
        this.doctorRepository = doctorRepository;
        this.profileService = profileService;
        this.specializationService = specializationService;
    }

    // Метод осуществляет поиск врачей по номеру телефона и возвращает его в обертке Optional
    public Optional<Doctor> findByPhone(String phone) {
        return doctorRepository.findByPhone(phone);
    }

    // Метод осуществляет поиск врача по СНИЛСу и возвращает его в обертке Optional
    public Optional<Doctor> findBySnils(String snils) {
        return doctorRepository.findBySnils(snils);
    }

    // Метод осуществляет поиск врача по ИНН и возвращает его в обертке Optional
    public Optional<Doctor> findByInn(String inn) {
        return doctorRepository.findByInn(inn);
    }

    // Метод осуществляет поиск всех врачей и возвращает их список
    public List<Doctor> findAll() {
        return doctorRepository.findAll();
    }

    // Метод осуществляет поиск врачей с published = True или False и возвращает их список
    public List<Doctor> findAllByPublished(boolean published) {
        return doctorRepository.findByPublished(published);
    }

    // Метод осуществляет поиск врачей с showInSchedule = True или False и возвращает их список
    public List<Doctor> findAllByShowInSchedule(boolean showInSchedule) {
        return doctorRepository.findByShowInSchedule(showInSchedule);
    }

    // Метод осуществляет поиск врачей с published = True или False и showInSchedule = True или False и возвращает их список
    public List<Doctor> findAllByPublishedAndShowInSchedule(boolean published, boolean showInSchedule) {
        return doctorRepository.findByPublishedAndShowInSchedule(published, showInSchedule);
    }

    // Метод осуществляет поиск врача по id и возвращает его в обертке Optional
    public Optional<Doctor> findById(Integer doctorId) {
        return doctorRepository.findById(doctorId);
    }

    /*
    Метод register создает нового врача и пользователя для него.

    В методе данный врач добавляется в список врачей для объекта специализации из БД, для кэша.
    Устанавливается дата и время создания и изменения врача.

    В случае если у врача есть доступ к системе, т.е. AccessToSystem = true создается новый пользователь для данного врача.

    Далее происходит сохранение врача.
     */
    @Transactional
    public void register(Doctor doctor) {

        specializationService.addDoctorAtListForSpecialization(doctor, doctor.getSpecialization());

        doctor.setCreatedAt(LocalDateTime.now());
        doctor.setUpdatedAt(LocalDateTime.now());

        if (doctor.getAccessToSystem()) {
            doctor.getProfile().setDoctor(doctor);
            profileService.create(doctor.getProfile());
        }

        doctorRepository.save(doctor);
    }

    /*
    Метод update сохраняет изменения в БД, которые сделаны в изменяемом враче.
     */
    @Transactional
    public void update(Doctor updatedDoctor) {

        //находим в БД существующего врача
        Doctor existingDoctor = doctorRepository.findById(updatedDoctor.getId()).get();

        //если у существующего врача из БД и у измененного врача разные специализации, значит специализацию изменили,
        // поэтому для кэша удалим из списка докторов у старой специализации существующего доктора
        if (!existingDoctor.getSpecialization().equals(updatedDoctor.getSpecialization())) {
            existingDoctor.getSpecialization().getDoctors().remove(existingDoctor);
        }

        //копируем значения всех полей не null из изменяемого доктора в существующего
        copyNonNullProperties(updatedDoctor, existingDoctor);

        //у существующего доктора обновляем время изменения
        existingDoctor.setUpdatedAt(LocalDateTime.now());

        //для кэша, добавляем в список докторов у специализации или заменяем, если он уже там есть
        specializationService.addDoctorAtListForSpecialization(existingDoctor, existingDoctor.getSpecialization());

        //находим существующий профиль у существующего доктора
        Profile existingProfile = existingDoctor.getProfile();

        //Проверяем есть ли у врача - пользователя доступ к системе, если есть, значит должен быть профиль
        if (existingDoctor.getAccessToSystem()) {

            //Если профиль уже был, т.е. его id не равен null, значит его могли изменить, поэтому делаем апдейт профиля
            if (existingProfile.getId() != null) {
                profileService.update(existingProfile, existingDoctor.getProfile());

                //иначе если id профиля равен null, значит профиль новый, ранее доступа у данного пользователя не было,
                // значит создаем новый профиль
            } else {
                profileService.create(existingDoctor.getProfile());
            }

            //для кэша, для профиля установим измененного доктора
            existingDoctor.getProfile().setDoctor(existingDoctor);

        } else {
            //Если у пользователя нет доступа к системе, но при этом в БД есть профиль, это значит что доступ к системе
            // у данного пользователя ранее был, но его убрали, значит и старый профиль больше не нужен, удаляем его
            if (existingProfile != null) {
                profileService.delete(existingProfile);
            }
        }
    }

    /*
    Метод добавляет вызов в список доктора для кэша
     */
    public void addCallingAtListForDoctor(Calling calling, Doctor doctor) {
        GeneralMethods.addObjectOneInListForObjectTwo(calling, doctor, this);
    }

    /*
    Метод добавляет договор в список доктора для кэша
    */
    public void addContractAtListForDoctor(Contract contract, Doctor doctor) {
        GeneralMethods.addObjectOneInListForObjectTwo(contract, doctor, this);
    }
}
