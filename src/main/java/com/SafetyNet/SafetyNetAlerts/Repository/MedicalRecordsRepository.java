package com.SafetyNet.SafetyNetAlerts.Repository;

import com.SafetyNet.SafetyNetAlerts.Model.MedicalRecords;
import com.SafetyNet.SafetyNetAlerts.Service.MedicalRecordsService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;

@Repository
public interface MedicalRecordsRepository extends CrudRepository<MedicalRecords, Long> {

    static MedicalRecords medialRecordsJSON() {
        try {
            MedicalRecordsService medicalRecordsService = new MedicalRecordsService();
            ObjectMapper mapper = new ObjectMapper();
            File file = new File("src/main/resources/data.json");
            JsonNode jsonNode = mapper.readTree(file);
            JsonNode MedicalRecordsNode = jsonNode.path("medicalrecords");
            for (JsonNode node : MedicalRecordsNode) {
                MedicalRecords medicalRecords = new MedicalRecords();
                medicalRecords.setFirstname(node.path("firstName").asText());
                medicalRecords.setLastname(node.path("lastName").asText());
                medicalRecords.setBirthdate(node.path("birthdate").asText());
                medicalRecords.setMedications(String.valueOf(node.findValue("medications")));
                medicalRecords.setAllergies(String.valueOf(node.findValue("allergies")));
                medicalRecordsService.saveMedicalRecord(medicalRecords);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
