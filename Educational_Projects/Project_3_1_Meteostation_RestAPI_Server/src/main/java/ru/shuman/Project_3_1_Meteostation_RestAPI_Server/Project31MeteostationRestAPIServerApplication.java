package ru.shuman.Project_3_1_Meteostation_RestAPI_Server;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Project31MeteostationRestAPIServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(Project31MeteostationRestAPIServerApplication.class, args);
	}

	/*
	Создание бина ModelMapper для использования в контроллерах для конвертации из DTO в модели и обратно
	 */
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
}
