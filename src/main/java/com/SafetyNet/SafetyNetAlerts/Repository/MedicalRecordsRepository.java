package com.SafetyNet.SafetyNetAlerts.Repository;

import com.SafetyNet.SafetyNetAlerts.Model.MedicalRecords;
import com.SafetyNet.SafetyNetAlerts.Model.Persons;
import com.SafetyNet.SafetyNetAlerts.Service.MedicalRecordsService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

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
                String arrayMedication = node.path("medications").asText();
                MedicalRecords medicalRecords = new MedicalRecords();
                medicalRecords.setId(id);
                medicalRecords.setFirstName(node.path("firstName").asText());
                medicalRecords.setLastName(node.path("lastName").asText());
                medicalRecords.setBirthdate(node.path("birthdate").asText());
                medicalRecords.setMedications(arrayMedication);
                medicalRecords.setAllergies(String.valueOf(node.path("allergies").withArrayProperty("allergies")));
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
        Map<String, ArrayList<MedicalRecords>> map = new HashMap<>();
        ArrayList<MedicalRecords> medicalRecordsArrayList = findAllMedicalRecords();
        for (MedicalRecords medicalRecords1 : medicalRecordsArrayList) {
            if (Objects.equals(medicalRecords1.getId(), medicalRecords.getId())) {
                medicalRecords1.setFirstName(medicalRecords.getFirstName());
                medicalRecords1.setLastName(medicalRecords.getLastName());
                medicalRecords1.setBirthdate(medicalRecords.getBirthdate());
                medicalRecords1.setMedications(medicalRecords.getMedications());
                medicalRecords1.setAllergies(medicalRecords.getAllergies());
            }
        }
        map.put("medicalrecords", medicalRecordsArrayList);
        mapper.writerWithDefaultPrettyPrinter().writeValue(file, map);
    }

    default void deleteMedicalRecord(Long id) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        File file = new File("src/main/resources/data.json");
        Map<String, ArrayList<MedicalRecords>> map = new HashMap<>();
        ArrayList<MedicalRecords> medicalRecordsArrayList = findAllMedicalRecords();
        medicalRecordsArrayList.removeIf(medicalRecords1 -> Objects.equals(medicalRecords1.getId(), id));
        map.put("medicalrecords", medicalRecordsArrayList);
        mapper.writerWithDefaultPrettyPrinter().writeValue(file, map);
    }

    default void saveMedicalRecord(MedicalRecords medicalRecords) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        File file = new File("src/main/resources/data.json");
        JsonNode jsonNode = mapper.readTree(file);
        Map<String, ArrayList<MedicalRecords>> map = new HashMap<>();
        System.out.println(jsonNode);
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
        map.put("medicalrecords", medicalRecordsArrayList);
        mapper.writerWithDefaultPrettyPrinter().writeValue(file, map);
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
