package com.scopie.authservice;

import com.scopie.authservice.kafka.dto.KafkaReservationDTO;
import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.Date;

@SpringBootApplication
public class AuthServiceApplication {

	public static void main(String[] args) {
		System.out.println("Authentication microservice started to running on port 3202");
		SpringApplication.run(AuthServiceApplication.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner(KafkaTemplate<String, KafkaReservationDTO> kafkaTemplate) {
		Date date=new Date();
			KafkaReservationDTO kfkReservation = new KafkaReservationDTO(
					1,
					date,
					2300.00
			);
		return args -> {
			kafkaTemplate.send("test_message", kfkReservation);
		};
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

}
