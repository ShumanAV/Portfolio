package ru.shuman.Project_Aibolit_Server.util.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.shuman.Project_Aibolit_Server.models.Address;
import ru.shuman.Project_Aibolit_Server.services.AddressService;
import ru.shuman.Project_Aibolit_Server.util.StandardMethods;

@Component
public class AddressIdValidator implements Validator {

    private final AddressService addressService;

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

        String field = StandardMethods.searchNameFieldInTargetClass(errors, address.getClass());

        if (address.getId() == null) {
            errors.rejectValue(field == null ? "id" : field, "", "У адреса отсутствует id!");

        } else if (addressService.findById(address.getId()).isEmpty()) {
            errors.rejectValue(field == null ? "id": field, "", "Адреса с таким id не существует!");
        }
    }
}
