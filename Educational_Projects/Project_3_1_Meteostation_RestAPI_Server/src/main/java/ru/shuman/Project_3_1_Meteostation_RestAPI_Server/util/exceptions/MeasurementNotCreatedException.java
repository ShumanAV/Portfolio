package ru.shuman.Project_3_1_Meteostation_RestAPI_Server.util.exceptions;

/*
Класс исключение, которое выбрасываем в случае наличия ошибок, возникающих при валидации новых измерений
 */

public class MeasurementNotCreatedException extends RuntimeException{
    public MeasurementNotCreatedException(String msg) {
        super(msg);
    }
}
