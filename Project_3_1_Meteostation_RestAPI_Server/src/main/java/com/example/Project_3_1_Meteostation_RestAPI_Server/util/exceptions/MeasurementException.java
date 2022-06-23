package com.example.Project_3_1_Meteostation_RestAPI_Server.util.exceptions;

// данный Exception о том, что в тех данных, которые прислал клиент, были ошибки
public class MeasurementException extends RuntimeException{
    public MeasurementException(String msg) {
        // будем принимать сообщение об ошибке и передавать его в супер класс RuntimeException
        super(msg);
    }
}
