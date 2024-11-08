package com.example.Project_3_1_Meteostation_RestAPI_Server.util;

import com.example.Project_3_1_Meteostation_RestAPI_Server.util.exceptions.SensorAndMeasurementException;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import java.util.List;

// класс для формирования списка всех ошибок в данных запроса от клиента
@Component
public class CreateMessageException {

    public static void returnErrorsToClient(BindingResult bindingResult) {
        StringBuilder errorMsg = new StringBuilder();

        // создадим лист ошибок
        List<FieldError> errors = bindingResult.getFieldErrors();
        for (FieldError error : errors) {
            // преобразуем все ошибки сделанные клиентом в большую строку
            errorMsg.append(error.getField())
                    .append(" - ").append(error.getDefaultMessage()).append(";");
        }
        // выбросим нашу ошибку и передадим в нее текст ошибки валидации данных, нам остается ее поймать в другом методе
        throw new SensorAndMeasurementException(errorMsg.toString());
    }

}
