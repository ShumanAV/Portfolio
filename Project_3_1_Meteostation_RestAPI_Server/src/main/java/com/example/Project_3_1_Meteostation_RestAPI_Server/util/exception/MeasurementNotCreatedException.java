package com.example.Project_3_1_Meteostation_RestAPI_Server.util.exception;

// данный Exception о том, что в тех данных, которые прислал клиент, либо в данных измерений были ошибки, либо сенсора с таким именем не существует в БД,
// в результате измерение не было создано
public class MeasurementNotCreatedException extends RuntimeException{
    public MeasurementNotCreatedException(String msg) {
        // будем принимать сообщение об ошибке и передавать его в супер класс RuntimeException
        super(msg);
    }
}
