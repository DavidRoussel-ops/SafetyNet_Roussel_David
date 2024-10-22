package com.SafetyNet.SafetyNetAlerts;

import com.SafetyNet.SafetyNetAlerts.Model.Persons;
import com.SafetyNet.SafetyNetAlerts.Service.BusinessService;
import com.SafetyNet.SafetyNetAlerts.Service.PersonService;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@SpringBootApplication
public class SafetyNetAlertsApplication implements CommandLineRunner {

	@Autowired
	BusinessService businessService;

	public static void main(String[] args) {
		SpringApplication.run(SafetyNetAlertsApplication.class, args);
	}

	@Bean
	CommandLineRunner runner(PersonService personService) {
		return args -> {
			// Lire JSON et écrire en BDD
			ObjectMapper mapper = new ObjectMapper();
			mapper.configure(DeserializationFeature.UNWRAP_ROOT_VALUE, true);
			TypeReference<List<Persons>> typeReference = new TypeReference<List<Persons>>(){};
			InputStream inputStream = TypeReference.class.getResourceAsStream("/data.json");
			try {
				List<Persons> persons = mapper.readValue(inputStream, typeReference);
				personService.save(persons);
				System.out.println("Persons enregistrer");
			} catch (IOException ioException) {
				System.out.println("Persons non enregistrer " + ioException.getMessage());
			}
		};
	}

	public void run(String... args) throws Exception {
		System.out.println("L'application est lancer");
	}

}
