package ru.shuman.Project_Aibolit_Server.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.shuman.Project_Aibolit_Server.models.Diary;
import ru.shuman.Project_Aibolit_Server.repositories.DiaryRepository;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class DiaryService {

    private final DiaryRepository diaryRepository;

    @Autowired
    public DiaryService(DiaryRepository diaryRepository) {
        this.diaryRepository = diaryRepository;
    }

    public Optional<Diary> findById(Integer diaryId) {
        return diaryRepository.findById(diaryId);
    }

    @Transactional
    public void create(Diary diary) {

        diary.setCreatedAt(LocalDateTime.now());
        diary.setUpdatedAt(LocalDateTime.now());

        diaryRepository.save(diary);
    }

    @Transactional
    public void update(Diary diary) {

        diary.setUpdatedAt(LocalDateTime.now());

        diaryRepository.save(diary);
    }
}
