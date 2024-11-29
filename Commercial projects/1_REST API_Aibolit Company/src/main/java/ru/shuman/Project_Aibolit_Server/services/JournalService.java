package ru.shuman.Project_Aibolit_Server.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.shuman.Project_Aibolit_Server.models.Journal;
import ru.shuman.Project_Aibolit_Server.repositories.JournalRepository;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class JournalService {

    private final JournalRepository journalRepository;

    @Autowired
    public JournalService(JournalRepository journalRepository) {
        this.journalRepository = journalRepository;
    }

    public Optional<Journal> findById(Integer diaryId) {
        return journalRepository.findById(diaryId);
    }

    @Transactional
    public void create(Journal journal) {

        journal.setCreatedAt(LocalDateTime.now());
        journal.setUpdatedAt(LocalDateTime.now());

        journalRepository.save(journal);
    }

    @Transactional
    public void update(Journal journal) {

        journal.setUpdatedAt(LocalDateTime.now());

        journalRepository.save(journal);
    }
}
