package ru.shuman.Project_Aibolit_Server.util.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.shuman.Project_Aibolit_Server.models.Journal;
import ru.shuman.Project_Aibolit_Server.services.JournalService;

import static ru.shuman.Project_Aibolit_Server.util.GeneralMethods.searchNameFieldInParentEntity;

@Component
public class JournalIdValidator implements Validator {

    private final JournalService journalService;

    @Autowired
    public JournalIdValidator(JournalService journalService) {
        this.journalService = journalService;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return Journal.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Journal journal = (Journal) o;

        String field = searchNameFieldInParentEntity(errors, journal.getClass());

        if (journal.getId() == null) {
            errors.rejectValue(field == null ? "id" : field, "", "У дневника отсутствует id!");

        } else if (journalService.findById(journal.getId()).isEmpty()) {
            errors.rejectValue(field == null ? "id": field, "", "Дневника с таким id не существует!");
        }
    }
}
