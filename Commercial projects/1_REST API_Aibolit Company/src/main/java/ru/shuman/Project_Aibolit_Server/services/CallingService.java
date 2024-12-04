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
    Для кэша данный вызов добавляется в список вызовов доктора, прайса, в журнал, пациента.
    */
    @Transactional
    public void create(Calling calling) {

        calling.setCreatedAt(LocalDateTime.now());
        calling.setUpdatedAt(LocalDateTime.now());

        doctorService.addCallingAtListForDoctor(calling, calling.getDoctor());

        calling.getJournal().setCalling(calling);
        journalService.create(calling.getJournal());

        priceService.addCallingAtListForPrice(calling, calling.getPrice());

        patientService.addCallingAtListForPatient(calling, calling.getPatient());
        if (calling.getPatient().getId() == null) {
            patientService.create(calling.getPatient());
        } else {
            patientService.update(calling.getPatient());
        }

        callingRepository.save(calling);
    }

    /*
    Метод сохраняет измененный вызов врача в БД, вносит дату и время изменения.
    Создает по цепочке дочернюю сущность Journal - карточка вызова врача, создает или обновляет Patient - пациента.
    Для кэша данный вызов добавляется в список вызовов доктора, прайса, в журнал, пациента.
    */
    @Transactional
    public void update(Calling calling) {

        Optional<Calling> existingCalling = callingRepository.findById(calling.getId());

        calling.setCreatedAt(existingCalling.get().getCreatedAt());
        calling.setUpdatedAt(LocalDateTime.now());

        doctorService.addCallingAtListForDoctor(calling, calling.getDoctor());

        calling.getJournal().setCalling(calling);
        journalService.update(calling.getJournal());

        priceService.addCallingAtListForPrice(calling, calling.getPrice());

        patientService.addCallingAtListForPatient(calling, calling.getPatient());
        if (calling.getPatient().getId() == null) {
            patientService.create(calling.getPatient());
        } else {
            patientService.update(calling.getPatient());
        }

        callingRepository.save(calling);
    }
}
