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

        if (doctor.isAccessToSystem()) {
            doctor.getProfile().setDoctor(doctor);
            profileService.create(doctor.getProfile());
        }

        doctorRepository.save(doctor);
    }

        /*
    Метод update осуществляет изменение существующего врача в БД.

    В методе данный врач добавляется в список врачей для объекта специализации из БД, для кэша.
    Переносит из существующего в БД доктора дату создания в измененного доктора.
    Устанавливает дату и время изменения.
    Выполняет поиск пользователя у данного врача.

    Далее, в случае если у входящего врача есть доступ к системе, т.е. AccessToSystem = True,
    если для данного врача уже есть пользователь в БД, то происходит его изменение,
    если пользователя нет, то происходит создание и сохранение нового пользователя.
    Делается это без каскадирования, т.е. отдельно для пользователя.

    Если AccessToSystem = False, но при этом есть уже пользователь в БД, происходит его удаление.

    Далее происходит сохранение обновленного врача.
     */
    @Transactional
    public void update(Doctor updatedDoctor) {

        specializationService.addDoctorAtListForSpecialization(updatedDoctor, updatedDoctor.getSpecialization());

        Optional<Doctor> existingDoctor = doctorRepository.findById(updatedDoctor.getId());

        updatedDoctor.setCreatedAt(existingDoctor.get().getCreatedAt());
        updatedDoctor.setUpdatedAt(LocalDateTime.now());

        Profile existingProfile = doctorRepository.findById(updatedDoctor.getId()).get().getProfile();

        if (updatedDoctor.isAccessToSystem()) {

            updatedDoctor.getProfile().setDoctor(updatedDoctor);
            if (existingProfile != null) {
                profileService.update(existingProfile, updatedDoctor.getProfile());
            } else {
                profileService.create(updatedDoctor.getProfile());
            }

        } else {

            if (existingProfile != null) {
                profileService.delete(existingProfile);
            }
        }

        doctorRepository.save(updatedDoctor);
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
