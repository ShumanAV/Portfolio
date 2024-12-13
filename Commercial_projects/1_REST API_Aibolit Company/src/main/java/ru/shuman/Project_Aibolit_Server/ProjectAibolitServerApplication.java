package ru.shuman.Project_Aibolit_Server;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ProjectAibolitServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProjectAibolitServerApplication.class, args);
	}

	//создадим бин ModelMapper для конвертации модели в DTO и наоборот
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
}
