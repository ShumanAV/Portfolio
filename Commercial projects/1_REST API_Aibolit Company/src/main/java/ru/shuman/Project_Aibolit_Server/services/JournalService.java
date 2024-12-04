package ru.shuman.Project_Aibolit_Server.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.shuman.Project_Aibolit_Server.models.Journal;
import ru.shuman.Project_Aibolit_Server.repositories.JournalRepository;

import java.time.LocalDateTime;
import java.util.Optional;

import static ru.shuman.Project_Aibolit_Server.util.GeneralMethods.copyNonNullProperties;

@Service
@Transactional(readOnly = true)
public class JournalService {

    private final JournalRepository journalRepository;

    /*
    Внедрение зависимостей
     */
    @Autowired
    public JournalService(JournalRepository journalRepository) {
        this.journalRepository = journalRepository;
    }

    /*
    Метод находит карточку вызова врача по id и возвращает ее в обертке Optional
     */
    public Optional<Journal> findById(Integer diaryId) {
        return journalRepository.findById(diaryId);
    }

    /*
    Метод сохраняет новую карточку вызова врача в БД, вносится дата и время создания и изменения
     */
    @Transactional
    public void create(Journal journal) {

        journal.setCreatedAt(LocalDateTime.now());
        journal.setUpdatedAt(LocalDateTime.now());

        journalRepository.save(journal);
    }

    /*
    Метод сохраняет изменения в карточке вызова врача в БД, все изменения переносятся с изменяемого объекта в
    существующий, обновляется дата и время изменения
     */
    @Transactional
    public void update(Journal updatedJournal) {

        Journal existingJournal = journalRepository.findById(updatedJournal.getId()).get();

        copyNonNullProperties(updatedJournal, existingJournal);
        existingJournal.setUpdatedAt(LocalDateTime.now());
    }
}
