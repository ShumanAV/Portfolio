package com.example.Project_3_1_Meteostation_RestAPI_Server;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Project31MeteostationRestAPIServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(Project31MeteostationRestAPIServerApplication.class, args);
	}

	// создадим бин modelMapper для повсеместного внедрения
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

}
