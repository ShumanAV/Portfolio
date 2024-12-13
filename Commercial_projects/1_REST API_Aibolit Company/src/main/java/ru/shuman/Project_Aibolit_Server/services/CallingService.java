package ru.shuman.Project_Aibolit_Server.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.shuman.Project_Aibolit_Server.models.*;
import ru.shuman.Project_Aibolit_Server.repositories.CallingRepository;
import ru.shuman.Project_Aibolit_Server.util.GeneralMethods;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static ru.shuman.Project_Aibolit_Server.util.GeneralMethods.copyNonNullProperties;

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

        //создаем новую карточку вызова врача, плюс для кэша добавляем вызов врача в карточку вызова
        journalService.create(newCalling.getJournal());
        newCalling.getJournal().setCalling(newCalling);

        //для кэша добавляем вызов врача в список вызовов для прайса указанного в вызове
        priceService.addCallingAtListForPrice(newCalling, newCalling.getPrice());

        //если id пациента, указанного в вызове null, это значит что пациент создан новый и его нужно создать в БД,
        // если id не null, значит пациент выбран уже существующий в БД и его апдейтим
        if (newCalling.getPatient().getId() == null) {
            patientService.create(newCalling.getPatient());
        } else {
            patientService.update(newCalling.getPatient());
        }

        //для кэша добавляем вызов врача в список вызовов для пациента указанного в вызове, данная строка должна быть ниже
        //создания пациента, т.к. в методе потребуется id пациента
        patientService.addCallingAtListForPatient(newCalling, newCalling.getPatient());

        callingRepository.save(newCalling);
    }

    /*
    Метод сохраняет измененный вызов врача в БД, копирует все значения полей кроме null из измененного вызова врача
    в существуюещий вызов в БД, вносит дату и время изменения.
    Апдейтит по цепочке дочернюю сущность Journal - карточка вызова врача, создает или обновляет Patient - пациента.
    Для кэша данный вызов добавляется в список вызовов доктора, прайса, в журнал, пациента.
    */
    @Transactional
    public void update(Calling updatedCalling) {

        //находим существующий вызов в БД по id
        Calling existingCalling = callingRepository.findById(updatedCalling.getId()).get();

        //копируем значения всех полей кроме тех, которые не null, из изменяемого вызова в существующий
        copyNonNullProperties(updatedCalling, existingCalling);

        //обновляем дату и время изменения вызова
        existingCalling.setUpdatedAt(LocalDateTime.now());

        //для кэша добавляем вызов врача в список вызовов для доктора указанного в вызове
        doctorService.addCallingAtListForDoctor(existingCalling, existingCalling.getDoctor());

        //апдейтим карточку вызова, плюс для кэша добавляем вызов врача журнал
        journalService.update(existingCalling.getJournal());
        existingCalling.getJournal().setCalling(existingCalling);

        //для кэша добавляем вызов врача в список вызовов для прайса указанного в вызове
        priceService.addCallingAtListForPrice(existingCalling, existingCalling.getPrice());

        //если id пациента равен null, это значит, что пациент создан новый и его нужно создать, если не null, значит
        // пациент выбран существующий и поэтому его нужно апдейтить
        if (existingCalling.getPatient().getId() == null) {
            patientService.create(existingCalling.getPatient());
        } else {
            patientService.update(existingCalling.getPatient());
        }

        //для кэша добавляем вызов врача в список вызовов для пациента указанного в вызове, эта строка должна быть ниже
        // создания пациента, т.к. в методе понадобится его id
        patientService.addCallingAtListForPatient(existingCalling, existingCalling.getPatient());

        //сохранять существующий вызов врача не нужно, т.к. он находится в персистенс контексте
    }
}
