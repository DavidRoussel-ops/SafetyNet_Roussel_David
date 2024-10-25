package com.SafetyNet.SafetyNetAlerts;

import com.SafetyNet.SafetyNetAlerts.Service.BusinessService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.*;

@SpringBootApplication
public class SafetyNetAlertsApplication implements CommandLineRunner {

	@Autowired
	BusinessService businessService;

    {
        try {
			ObjectMapper mapper = new ObjectMapper();
			File file = new File("src/main/resources/data.json");
			JsonNode jsonNode = mapper.readTree(file);
			JsonNode locatedNode = jsonNode.path("persons");
			System.out.println("jsonNode : " + jsonNode);
			System.out.println("persons : " + locatedNode);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
		SpringApplication.run(SafetyNetAlertsApplication.class, args);
	}

	public void run(String... args) throws Exception {
		System.out.println("L'application est lancer");
	}

}
