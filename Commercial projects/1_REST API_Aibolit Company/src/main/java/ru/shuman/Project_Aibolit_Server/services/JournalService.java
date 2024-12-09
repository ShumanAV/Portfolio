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
    public Optional<Journal> findById(Integer journalId) {
        return journalRepository.findById(journalId);
    }

    /*
    Метод сохраняет новую карточку вызова врача в БД
     */
    @Transactional
    public void create(Journal newJournal) {

        //записываем дату и время создания и изменения карточки вызова врача
        newJournal.setCreatedAt(LocalDateTime.now());
        newJournal.setUpdatedAt(LocalDateTime.now());

        //сохраняем новую карточку вызова врача
        journalRepository.save(newJournal);
    }

    /*
    Метод сохраняет измененную карточку вызова врача в БД, значения всех полей кроме null копируются с изменяемого объекта в
    существующий, обновляется дата и время изменения
     */
    @Transactional
    public void update(Journal updatedJournal) {

        //находим существующую карточку вызова врача в БД по id
        Journal existingJournal = journalRepository.findById(updatedJournal.getId()).get();

        //копируем значения всех полей кроме тех, которые null, из измененной карточки в существующую
        copyNonNullProperties(updatedJournal, existingJournal);

        //обновляем дату и время изменения
        existingJournal.setUpdatedAt(LocalDateTime.now());
    }
}
