package ru.shuman.Project_Aibolit_Server.util.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.shuman.Project_Aibolit_Server.models.Address;
import ru.shuman.Project_Aibolit_Server.services.AddressService;

import static ru.shuman.Project_Aibolit_Server.util.GeneralMethods.searchNameFieldInParentEntity;

@Component
public class AddressIdValidator implements Validator {

    private final AddressService addressService;

    /*
    Внедрение зависимостей
     */
    @Autowired
    public AddressIdValidator(AddressService addressService) {
        this.addressService = addressService;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return Address.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Address address = (Address) o;

        //находим название поля в родительской сущности, к которому относится текущая сущность
        String field = searchNameFieldInParentEntity(errors, address.getClass());

        //проверяем есть ли id у адреса
        if (address.getId() == null) {
            errors.rejectValue(field == null ? "id" : field, "", "У адреса отсутствует id!");

            // если id есть, проверяем есть ли адрес в БД с таким id
        } else if (addressService.findById(address.getId()).isEmpty()) {
            errors.rejectValue(field == null ? "id": field, "", "Адреса с таким id не существует!");
        }
    }
}
