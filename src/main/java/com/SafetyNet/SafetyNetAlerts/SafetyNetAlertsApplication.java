package com.SafetyNet.SafetyNetAlerts;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SafetyNetAlertsApplication implements CommandLineRunner {

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
	}

}
