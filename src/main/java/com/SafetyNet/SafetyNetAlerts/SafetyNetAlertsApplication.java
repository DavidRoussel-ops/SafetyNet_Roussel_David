package com.SafetyNet.SafetyNetAlerts;

import com.SafetyNet.SafetyNetAlerts.Model.Firestations;
import com.SafetyNet.SafetyNetAlerts.Model.MedicalRecords;
import com.SafetyNet.SafetyNetAlerts.Model.Persons;
import com.SafetyNet.SafetyNetAlerts.Service.BusinessService;
import com.SafetyNet.SafetyNetAlerts.Service.FirestationsService;
import com.SafetyNet.SafetyNetAlerts.Service.MedicalRecordsService;
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

	@Autowired
	FirestationsService firestationsService;

	@Autowired
	MedicalRecordsService medicalRecordsService;

    public static void main(String[] args) {
		SpringApplication.run(SafetyNetAlertsApplication.class, args);
	}

	public void run(String... args) throws Exception {
		System.out.println("L'application est lancer");
		{
			try {
				ObjectMapper mapper = new ObjectMapper();
				File file = new File("src/main/resources/data.json");
				JsonNode jsonNode = mapper.readTree(file);
				JsonNode PersonsNode = jsonNode.path("persons");
				JsonNode FirestationsNode = jsonNode.path("firestations");
				JsonNode MedicalRecordsNode = jsonNode.path("medicalrecords");
				for (JsonNode node : PersonsNode) {
					Persons persons = new Persons();
					persons.setFirstname(node.path("firstName").asText());
					persons.setLastname(node.path("lastName").asText());
					persons.setAddress(node.path("address").asText());
					persons.setCity(node.path("city").asText());
					persons.setZip(node.path("zip").asText());
					persons.setPhone(node.path("phone").asText());
					persons.setEmail(node.path("email").asText());
					personService.savePerson(persons);
				}
				for (JsonNode node : FirestationsNode) {
					Firestations firestations = new Firestations();
					firestations.setAddress(node.path("address").asText());
					firestations.setStation(node.path("station").asInt());
					firestationsService.saveFirestation(firestations);
				}
				for (JsonNode node : MedicalRecordsNode) {
					MedicalRecords medicalRecords = new MedicalRecords();
					medicalRecords.setFirstname(node.path("firstName").asText());
					medicalRecords.setLastname(node.path("lastName").asText());
					medicalRecords.setBirthdate(node.path("birthdate").asText());
					medicalRecords.setMedications(node.path("medications").asText());
					medicalRecords.setAllergies(node.path("allergies").asText());
					medicalRecordsService.saveMedicalRecord(medicalRecords);
				}
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	}

}
