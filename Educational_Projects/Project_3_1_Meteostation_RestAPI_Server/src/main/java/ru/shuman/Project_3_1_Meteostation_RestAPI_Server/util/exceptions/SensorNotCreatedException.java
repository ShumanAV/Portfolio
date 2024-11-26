package ru.shuman.Project_3_1_Meteostation_RestAPI_Server.util.exceptions;

/*
Класс исключение, которое выбрасываем в случае наличия ошибок, возникающих при валидации новых сенсоров
 */

public class SensorNotCreatedException extends RuntimeException{
    public SensorNotCreatedException(String msg) {
        super(msg);
    }
}
