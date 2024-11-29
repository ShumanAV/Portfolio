package ru.shuman.Project_Aibolit_Server.util.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.shuman.Project_Aibolit_Server.models.Address;
import ru.shuman.Project_Aibolit_Server.services.AddressService;

import static ru.shuman.Project_Aibolit_Server.util.GeneralMethods.searchNameFieldInParentEntity;

@Component
public class AddressValidator implements Validator {

    private final AddressService addressService;
    private final RegionValidator regionValidator;
    private final RegionIdValidator regionIdValidator;

    @Autowired
    public AddressValidator(AddressService addressService, RegionValidator regionValidator, RegionIdValidator regionIdValidator) {
        this.addressService = addressService;
        this.regionValidator = regionValidator;
        this.regionIdValidator = regionIdValidator;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return Address.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Address address = (Address) o;

        String field = searchNameFieldInParentEntity(errors, address.getClass());

        if (address.getRegion() == null) {
            errors.rejectValue(field == null ? "region": field, "", "В адресе не выбран регион!");
        } else {
            regionIdValidator.validate(address.getRegion(), errors);
            regionValidator.validate(address.getRegion(), errors);
        }
    }
}
