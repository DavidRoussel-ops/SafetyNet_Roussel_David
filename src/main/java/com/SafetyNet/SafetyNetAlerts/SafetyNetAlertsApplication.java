package com.SafetyNet.SafetyNetAlerts;

import com.SafetyNet.SafetyNetAlerts.Service.BusinessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SafetyNetAlertsApplication implements CommandLineRunner {

	@Autowired
	private BusinessService businessService;

	/**
	 * @param args
	 */
    public static void main(String[] args) {
		SpringApplication.run(SafetyNetAlertsApplication.class, args);
	}

	/**
	 * @param args
	 * @throws Exception
	 */
	public void run(String... args) throws Exception {
		System.out.println("L'application est lancer");
		businessService.createJsonBdd();
	}

}
