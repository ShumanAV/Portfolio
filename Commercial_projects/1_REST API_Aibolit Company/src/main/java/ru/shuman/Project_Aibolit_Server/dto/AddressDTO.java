package ru.shuman.Project_Aibolit_Server.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
public class AddressDTO {

    private Integer id;

    @NotNull(message = "Город отсутствует")
    @Size(max = 100, message = "Город должен быть не более 100 символов")
    private String city;

    @NotNull(message = "Область отсутствует")
    @Size(max = 100, message = "Область должна быть не более 100 символов")
    private String district;

    @NotNull(message = "Поселение отсутствует")
    @Size(max = 100, message = "Поселение должно быть не более 100 символов")
    private String village;

    @NotNull(message = "Улица отсутствует")
    @Size(max = 100, message = "Улица должна быть не более 100 символов")
    private String street;

    @NotNull(message = "Номер дома отсутствует")
    @Size(max = 20, message = "Номер дома должен быть не более 20 символов")
    private String house;

    @NotNull(message = "Номер квартиры отсутствует")
    @Size(max = 20, message = "Номер квартиры должен быть не более 20 символов")
    private String apartment;

    @NotNull(message = "Почтовый индекс отсутствует")
    @Size(max = 6, message = "Почтовый индекс должен быть не более 6 символов")
    private String postcode;

    @NotNull(message = "Регион отсутствует")
    private RegionDTO region;

}
