package com.SafetyNet.SafetyNetAlerts;

import com.SafetyNet.SafetyNetAlerts.Model.Persons;
import com.SafetyNet.SafetyNetAlerts.Service.BusinessService;
import com.SafetyNet.SafetyNetAlerts.Service.PersonService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.*;
import java.util.*;

@SpringBootApplication
public class SafetyNetAlertsApplication implements CommandLineRunner {

	@Autowired
	BusinessService businessService;

	@Autowired
	PersonService personService;

    public static void main(String[] args) {
		SpringApplication.run(SafetyNetAlertsApplication.class, args);
	}

	public void run(String... args) throws Exception {
		System.out.println("L'application est lancer");
		{
			try {
				Persons persons = new Persons();
				ObjectMapper mapper = new ObjectMapper();
				File file = new File("src/main/resources/data.json");
				JsonNode jsonNode = mapper.readTree(file);
				JsonNode locatedNode = jsonNode.path("persons");
				//System.out.println("jsonNode : " + jsonNode);
				//System.out.println("locatedNode : " + locatedNode);
				for (JsonNode node : locatedNode) {
					persons.setFirstname(node.path("firstName").asText());
					persons.setLastname(node.path("lastName").asText());
					persons.setAddress(node.path("address").asText());
					persons.setCity(node.path("city").asText());
					persons.setZip(node.path("zip").asText());
					persons.setPhone(node.path("phone").asText());
					persons.setEmail(node.path("email").asText());
					personService.getPersons();
					personService.savePerson(persons);
				}
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	}

}
