package com.SafetyNet.SafetyNetAlerts;

import com.SafetyNet.SafetyNetAlerts.Model.Persons;
import com.SafetyNet.SafetyNetAlerts.Service.BusinessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SafetyNetAlertsApplication implements CommandLineRunner {

	@Autowired
	BusinessService businessService;

	public static void main(String[] args) {
		SpringApplication.run(SafetyNetAlertsApplication.class, args);
	}

	public void run(String... args) throws Exception {
		Persons persons = businessService.getPersons();
		persons.setFirstname("John");
		System.out.println(persons.getFirstname());
	}

}
