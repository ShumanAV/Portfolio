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

    @Autowired
    public CallingService(CallingRepository callingRepository, DoctorService doctorService, JournalService journalService,
                          PriceService priceService, PatientService patientService) {
        this.callingRepository = callingRepository;
        this.doctorService = doctorService;
        this.journalService = journalService;
        this.priceService = priceService;
        this.patientService = patientService;
    }

    public Optional<Calling> findById(Integer callingId) {
        return callingRepository.findById(callingId);
    }

    public List<Calling> findAll() {
        return callingRepository.findAll();
    }

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

    @Transactional
    public void update(Calling calling) {

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
