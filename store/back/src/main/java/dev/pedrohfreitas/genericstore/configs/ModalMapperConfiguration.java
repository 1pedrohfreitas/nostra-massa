package dev.pedrohfreitas.genericstore.configs;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class ModalMapperConfiguration{
	
	@Bean
	ModelMapper modelMapper() {
		return new ModelMapper();
	}
}
