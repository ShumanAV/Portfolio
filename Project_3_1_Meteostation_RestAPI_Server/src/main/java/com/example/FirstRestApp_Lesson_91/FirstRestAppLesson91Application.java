package com.example.FirstRestApp_Lesson_91;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class FirstRestAppLesson91Application {

	public static void main(String[] args) {
		SpringApplication.run(FirstRestAppLesson91Application.class, args);
	}

	// создадим бин modelMapper для повсеместного внедрения
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

}
