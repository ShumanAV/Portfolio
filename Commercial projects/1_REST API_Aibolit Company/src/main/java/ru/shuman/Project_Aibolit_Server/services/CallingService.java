package ru.shuman.Project_Aibolit_Server.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.shuman.Project_Aibolit_Server.models.*;
import ru.shuman.Project_Aibolit_Server.repositories.CallingRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class CallingService {

    private final CallingRepository callingRepository;
    private final DoctorService doctorService;
    private final JournalService journalService;
    private final PriceService priceService;
    private final PatientService patientService;

    /*
    Внедрение зависимостей
     */
    @Autowired
    public CallingService(CallingRepository callingRepository, DoctorService doctorService, JournalService journalService,
                          PriceService priceService, PatientService patientService) {
        this.callingRepository = callingRepository;
        this.doctorService = doctorService;
        this.journalService = journalService;
        this.priceService = priceService;
        this.patientService = patientService;
    }

    /*
    Метод находит вызов врача по id и возвращает его в обертке Optional
     */
    public Optional<Calling> findById(Integer callingId) {
        return callingRepository.findById(callingId);
    }

    /*
    Метод формирует список всех вызовов врачей и возвращает его
    */
    public List<Calling> findAll() {
        return callingRepository.findAll();
    }

    /*
    Метод сохраняет новый вызов врача в БД, вносит дату и время создания и изменения.
    Создает по цепочке дочернюю сущность Journal - карточка вызова врача, создает или обновляет Patient - пациента.
    Для кэша данный вызов добавляется в список вызовов доктора, прайса, карточки, пациента.
    */
    @Transactional
    public void create(Calling newCalling) {

        //записываем дату и время создания и изменения
        newCalling.setCreatedAt(LocalDateTime.now());
        newCalling.setUpdatedAt(LocalDateTime.now());

        //для кэша добавляем вызов врача в список вызовов для доктора указанного в вызове
        doctorService.addCallingAtListForDoctor(newCalling, newCalling.getDoctor());

        //для кэша добавляем вызов врача в карточку вызова и создаем новую карточку
        newCalling.getJournal().setCalling(newCalling);
        journalService.create(newCalling.getJournal());

        //для кэша добавляем вызов врача в список вызовов для прайса указанного в вызове
        priceService.addCallingAtListForPrice(newCalling, newCalling.getPrice());

        //для кэша добавляем вызов врача в список вызовов для пациента указанного в вызове
        patientService.addCallingAtListForPatient(newCalling, newCalling.getPatient());

        //если id пациента, указанного в вызове null, это значит что пациент создан новый и его нужно создать в БД,
        // если id не null, значит пациент выбран уже существующий в БД и его апдейтим
        if (newCalling.getPatient().getId() == null) {
            patientService.create(newCalling.getPatient());
        } else {
            patientService.update(newCalling.getPatient());
        }

        callingRepository.save(newCalling);
    }

    /*
    Метод сохраняет измененный вызов врача в БД, вносит дату и время изменения.
    Создает по цепочке дочернюю сущность Journal - карточка вызова врача, создает или обновляет Patient - пациента.
    Для кэша данный вызов добавляется в список вызовов доктора, прайса, в журнал, пациента.
    */
    @Transactional
    public void update(Calling updatedCalling) {

        Optional<Calling> existingCalling = callingRepository.findById(updatedCalling.getId());

        updatedCalling.setCreatedAt(existingCalling.get().getCreatedAt());
        updatedCalling.setUpdatedAt(LocalDateTime.now());

        doctorService.addCallingAtListForDoctor(updatedCalling, updatedCalling.getDoctor());

        updatedCalling.getJournal().setCalling(updatedCalling);
        journalService.update(updatedCalling.getJournal());

        priceService.addCallingAtListForPrice(updatedCalling, updatedCalling.getPrice());

        patientService.addCallingAtListForPatient(updatedCalling, updatedCalling.getPatient());
        if (updatedCalling.getPatient().getId() == null) {
            patientService.create(updatedCalling.getPatient());
        } else {
            patientService.update(updatedCalling.getPatient());
        }

        callingRepository.save(updatedCalling);
    }
}
