package ru.shuman.Project_Aibolit_Server.util.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.shuman.Project_Aibolit_Server.models.Diary;
import ru.shuman.Project_Aibolit_Server.services.DiaryService;
import ru.shuman.Project_Aibolit_Server.util.StandardMethods;

@Component
public class DiaryIdValidator implements Validator {

    private final DiaryService diaryService;

    @Autowired
    public DiaryIdValidator(DiaryService diaryService) {
        this.diaryService = diaryService;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return Diary.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Diary diary = (Diary) o;

        String field = StandardMethods.searchNameFieldInTargetClass(errors, diary.getClass());

        if (diary.getId() == null) {
            errors.rejectValue(field == null ? "id" : field, "", "У дневника отсутствует id!");

        } else if (diaryService.findById(diary.getId()).isEmpty()) {
            errors.rejectValue(field == null ? "id": field, "", "Дневника с таким id не существует!");
        }
    }
}
