package com.SafetyNet.SafetyNetAlerts.Service;

import com.SafetyNet.SafetyNetAlerts.Model.Firestations;
import com.SafetyNet.SafetyNetAlerts.Model.MedicalRecords;
import com.SafetyNet.SafetyNetAlerts.Model.Persons;
import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonPointer;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Component
public class BusinessService {

    @Autowired
    PersonService personService;

    @Autowired
    FirestationsService firestationsService;

    @Autowired
    MedicalRecordsService medicalRecordsService;

    public void createJsonBdd() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            File file = new File("src/main/resources/data.json");
            JsonNode jsonNode = mapper.readTree(file);
            mapper.writerWithDefaultPrettyPrinter().writeValue(file, jsonNode);
            /*JsonNode PersonsNode = jsonNode.path("persons");
            JsonNode FirestationsNode = jsonNode.path("firestations");
            JsonNode MedicalRecordsNode = jsonNode.path("medicalrecords");
            for (JsonNode node : PersonsNode) {
                Persons persons = new Persons();
                persons.setFirstName(node.path("firstName").asText());
                persons.setLastName(node.path("lastName").asText());
                persons.setAddress(node.path("address").asText());
                persons.setCity(node.path("city").asText());
                persons.setZip(node.path("zip").asText());
                persons.setPhone(node.path("phone").asText());
                persons.setEmail(node.path("email").asText());
                mapper.writerWithDefaultPrettyPrinter().writeValue(file, PersonsNode);
            }
            for (JsonNode node : FirestationsNode) {
                Firestations firestations = new Firestations();
                firestations.setAddress(node.path("address").asText());
                firestations.setStation(node.path("station").asText());
                mapper.writerWithDefaultPrettyPrinter().writeValue(file, FirestationsNode);
            }
            for (JsonNode node : MedicalRecordsNode) {
                MedicalRecords medicalRecords = new MedicalRecords();
                medicalRecords.setFirstName(node.path("firstName").asText());
                medicalRecords.setLastName(node.path("lastName").asText());
                medicalRecords.setBirthdate(node.path("birthdate").asText());
                medicalRecords.setMedications(String.valueOf(node.findValue("medications")));
                medicalRecords.setAllergies(String.valueOf(node.findValue("allergies")));
                mapper.writerWithDefaultPrettyPrinter().writeValue(file, MedicalRecordsNode);
            }*/
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
