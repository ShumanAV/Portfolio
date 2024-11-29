package ru.shuman.Project_Aibolit_Server.util.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.shuman.Project_Aibolit_Server.models.Journal;
import ru.shuman.Project_Aibolit_Server.services.JournalService;

@Component
public class JournalValidator implements Validator {

    private final JournalService journalService;

    @Autowired
    public JournalValidator(JournalService journalService) {
        this.journalService = journalService;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return Journal.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
    }
}
