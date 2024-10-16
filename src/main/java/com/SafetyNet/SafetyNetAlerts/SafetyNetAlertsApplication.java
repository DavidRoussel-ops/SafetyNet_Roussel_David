package com.SafetyNet.SafetyNetAlerts;

import com.SafetyNet.SafetyNetAlerts.Model.Firestations;
import com.SafetyNet.SafetyNetAlerts.Model.MedicalRecords;
import com.SafetyNet.SafetyNetAlerts.Model.Persons;
import com.SafetyNet.SafetyNetAlerts.Service.BusinessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SafetyNetAlertsApplication implements CommandLineRunner {

	@Autowired
	private BusinessService businessService;

	public static void main(String[] args) {
		SpringApplication.run(SafetyNetAlertsApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Persons bonjour = businessService.getPersons();
		String bonjourSpring = bonjour.ditBonjour();
		Firestations pompier = businessService.getFirestations();
		String lesPompiers = pompier.lesPompier();
		MedicalRecords medical = businessService.getMedicalRecords();
		String registreMedical = medical.leRegistre();
		System.out.println(bonjourSpring);
		System.out.println(lesPompiers);
		System.out.println(registreMedical);
	}

}
