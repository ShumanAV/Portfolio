package com.example.Project_3_1_Meteostation_RestAPI_Server.util.exception;

// данный Exception о том, что в тех данных, которые прислал клиент, либо в имени сенсора были ошибки, либо сенсор с таким именем уже существует, в результате
// сенсор не был создан
public class SensorNotCreatedException extends RuntimeException{
    public SensorNotCreatedException(String msg) {
        // будем принимать сообщение об ошибке и передавать его в супер класс RuntimeException
        super(msg);
    }
}
