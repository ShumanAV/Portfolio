package ru.shuman.Project_Aibolit_Server.util.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.shuman.Project_Aibolit_Server.models.Price;
import ru.shuman.Project_Aibolit_Server.services.PriceService;

import static ru.shuman.Project_Aibolit_Server.util.GeneralMethods.searchNameFieldInParentEntity;

@Component
public class PriceIdValidator implements Validator {

    private final PriceService priceService;

    /*
    Внедрение зависимостей
     */
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

        //находим название поля в родительской сущности, к которому относится текущая сущность
        String field = searchNameFieldInParentEntity(errors, price.getClass());

        //проверяем есть ли id у прайса
        if (price.getId() == null) {
            errors.rejectValue(field == null ? "id": field, "", "У прайса отсутствует id!");

            //проверяем есть ли прайс в БД с таким id
        } else if (priceService.findById(price.getId()).isEmpty()) {
            errors.rejectValue(field == null ? "id": field, "", "Прайса с таким id не существует!");
        }
    }
}
