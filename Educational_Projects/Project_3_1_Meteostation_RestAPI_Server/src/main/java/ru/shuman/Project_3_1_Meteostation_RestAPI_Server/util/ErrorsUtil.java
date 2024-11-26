package ru.shuman.Project_3_1_Meteostation_RestAPI_Server.util;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.List;

/*
Класс общих методов, используемых в коде
 */

public class ErrorsUtil {

    /*
    Метод принимает объект BindingResult с ошибками, которые возникают при валидации приходящих новых объектов
    в контроллерах (новые сенсоры, измерения), и класс исключение, которое необходимо бросить,
    формирует объект типа StringBuilder из полей и текстов сообщений данных ошибок и кидает исключение нужного класса
    с текстом сообщения об ошибке
    */
    public static void returnErrorsToClient(BindingResult bindingResult, Class<? extends Exception> exceptionClass) {
        StringBuilder errorMsg = new StringBuilder();

        List<FieldError> errors = bindingResult.getFieldErrors();
        for (FieldError error : errors) {
            errorMsg.append(error.getField())
                    .append(" - ").append(error.getDefaultMessage())
                    .append("; ");
        }
        try {
            throw exceptionClass.getConstructor(String.class).newInstance(errorMsg.toString());
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }
}
