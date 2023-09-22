package ru.shuman.Project_Aibolit_Server.util.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.shuman.Project_Aibolit_Server.models.Price;
import ru.shuman.Project_Aibolit_Server.services.PriceService;
import ru.shuman.Project_Aibolit_Server.util.StandardMethods;

@Component
public class PriceIdValidator implements Validator {

    private final PriceService priceService;

    @Autowired
    public PriceIdValidator(PriceService priceService) {
        this.priceService = priceService;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return Price.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Price price = (Price) o;

        String field = StandardMethods.searchNameFieldInTargetClass(errors, price.getClass());

        if (price.getId() == null) {
            errors.rejectValue(field == null ? "id": field, "", "У прайса отсутствует id!");

        } else if (priceService.findById(price.getId()).isEmpty()) {
            errors.rejectValue(field == null ? "id": field, "", "Прайса с таким id не существует!");
        }
    }
}
