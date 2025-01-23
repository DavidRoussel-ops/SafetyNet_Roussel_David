package com.SafetyNet.SafetyNetAlerts.Repository;

import com.SafetyNet.SafetyNetAlerts.Model.MedicalRecords;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.util.*;

@Repository
public interface MedicalRecordsRepository extends CrudRepository<MedicalRecords, Long> {

    default ArrayList<MedicalRecords> findAllMedicalRecords() {
        try {
            Long id = 1L;
            ArrayList<MedicalRecords> medicalRecordsArrayList = new ArrayList<>();
            ObjectMapper mapper = new ObjectMapper();
            File file = new File("src/main/resources/data.json");
            JsonNode jsonNode = mapper.readTree(file);
            JsonNode medicalRecordsNode = jsonNode.path("medicalrecords");
            for (JsonNode node : medicalRecordsNode) {
                JsonNode arrayMed = node.path("medications");
                JsonNode arrayAl = node.path("allergies");
                TypeReference<String[]> typeReferenceString = new TypeReference<String[]>() {};
                MedicalRecords medicalRecords = new MedicalRecords();
                medicalRecords.setId(id);
                medicalRecords.setFirstName(node.path("firstName").asText());
                medicalRecords.setLastName(node.path("lastName").asText());
                medicalRecords.setBirthdate(node.path("birthdate").asText());
                medicalRecords.setMedications(mapper.convertValue(arrayMed, typeReferenceString));
                medicalRecords.setAllergies(mapper.convertValue(arrayAl, typeReferenceString));
                medicalRecordsArrayList.add(medicalRecords);
                id ++;
            }
            return medicalRecordsArrayList;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    default void updateMedicalRecord(MedicalRecords medicalRecords) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        File file = new File("src/main/resources/data.json");
        JsonNode jsonNode = mapper.readTree(file);
        ArrayList<MedicalRecords> medicalRecordsArrayList = findAllMedicalRecords();
        for (MedicalRecords medicalRecords1 : medicalRecordsArrayList) {
            if (Objects.equals(medicalRecords1.getId(), medicalRecords.getId())) {
                medicalRecords1.setFirstName(medicalRecords.getFirstName());
                medicalRecords1.setLastName(medicalRecords.getLastName());
                medicalRecords1.setBirthdate(medicalRecords.getBirthdate());
                medicalRecords1.setMedications(medicalRecords.getMedications());
                medicalRecords1.setAllergies(medicalRecords.getAllergies());
                JsonNode nodeMedicalRecord = mapper.valueToTree(medicalRecordsArrayList);
                ObjectNode addNode = ((ObjectNode) jsonNode).set("medicalrecords", nodeMedicalRecord);
                mapper.writerWithDefaultPrettyPrinter().writeValue(file, addNode);
            }
        }
    }

    default void deleteMedicalRecord(Long id) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        File file = new File("src/main/resources/data.json");
        JsonNode jsonNode = mapper.readTree(file);
        ArrayList<MedicalRecords> medicalRecordsArrayList = findAllMedicalRecords();
        medicalRecordsArrayList.removeIf(medicalRecords1 -> Objects.equals(medicalRecords1.getId(), id));
        JsonNode nodeMedicalRecord = mapper.valueToTree(medicalRecordsArrayList);
        ObjectNode addNode = ((ObjectNode) jsonNode).set("medicalrecords", nodeMedicalRecord);
        mapper.writerWithDefaultPrettyPrinter().writeValue(file, addNode);
    }

    default void saveMedicalRecord(MedicalRecords medicalRecords) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        File file = new File("src/main/resources/data.json");
        JsonNode jsonNode = mapper.readTree(file);
        ArrayList<MedicalRecords> medicalRecordsArrayList = findAllMedicalRecords();
        MedicalRecords lastMedicalRecords = medicalRecordsArrayList.get(medicalRecordsArrayList.size() - 1);
        Long lastId = lastMedicalRecords.getId();
        Long newId = lastId + 1;
        MedicalRecords medicalRecordToSave = new MedicalRecords();
        medicalRecordToSave.setId(newId);
        medicalRecordToSave.setFirstName(medicalRecords.getFirstName());
        medicalRecordToSave.setLastName(medicalRecords.getLastName());
        medicalRecordToSave.setBirthdate(medicalRecords.getBirthdate());
        medicalRecordToSave.setMedications(medicalRecords.getMedications());
        medicalRecordToSave.setAllergies(medicalRecords.getAllergies());
        medicalRecordsArrayList.add(medicalRecordToSave);
        JsonNode nodeMedicalRecord = mapper.valueToTree(medicalRecordsArrayList);
        ObjectNode addNode = ((ObjectNode) jsonNode).set("medicalrecords", nodeMedicalRecord);
        mapper.writerWithDefaultPrettyPrinter().writeValue(file, addNode);
    }

    default MedicalRecords findMedicalRecordById(final Long id) {
        ArrayList<MedicalRecords> medicalRecords = findAllMedicalRecords();
        for (MedicalRecords medicalRecords1 : medicalRecords) {
            if (Objects.equals(id, medicalRecords1.getId())) {
                return medicalRecords1;
            }
        }
        return null;
    }
}
