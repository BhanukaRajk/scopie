package com.scopie.authservice;

import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaTemplate;

@SpringBootApplication
public class AuthServiceApplication {

	public static void main(String[] args) {
		System.out.println("Authentication microservice started to running on port 3202");
		SpringApplication.run(AuthServiceApplication.class, args);
	}

//	@Bean
//	CommandLineRunner commandLineRunner(KafkaTemplate<String, Long> kafkaTemplate) {
//			long kfkReservation =12231;
//		return args -> {
//			kafkaTemplate.send("SeatAdd", kfkReservation);
//		};
//	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

}
