package ru.shuman.Project_Aibolit_Server.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
public class AddressDTO {

    private Integer id;

    @Size(max = 100, message = "Город должен быть не более 100 символов")
    private String city;

    @Size(max = 100, message = "Область должна быть не более 100 символов")
    private String district;

    @Size(max = 100, message = "Поселение должно быть не более 100 символов")
    private String village;

    @Size(max = 100, message = "Улица должна быть не более 100 символов")
    private String street;

    @Size(max = 20, message = "Номер дома должен быть не более 20 символов")
    private String house;

    @Size(max = 20, message = "Номер квартиры должен быть не более 20 символов")
    private String apartment;

    @Size(max = 6, message = "Почтовый индекс должен быть не более 6 символов")
    private String postcode;

    private RegionDTO region;

}
