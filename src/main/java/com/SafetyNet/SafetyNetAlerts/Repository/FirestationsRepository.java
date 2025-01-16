package com.SafetyNet.SafetyNetAlerts.Repository;

import com.SafetyNet.SafetyNetAlerts.Model.Firestations;
import com.SafetyNet.SafetyNetAlerts.Service.FirestationsService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

@Repository
public interface FirestationsRepository extends CrudRepository<Firestations, Long> {

    default ArrayList<Firestations> findAllFirestations() {
        try {
            Long id = 1L;
            ArrayList<Firestations> firestationsArrayList = new ArrayList<>();
            ObjectMapper mapper = new ObjectMapper();
            File file = new File("src/main/resources/data.json");
            JsonNode jsonNode = mapper.readTree(file);
            JsonNode FirestationsNode = jsonNode.path("firestations");
            for (JsonNode node : FirestationsNode) {
                Firestations firestations = new Firestations();
                firestations.setId(id);
                firestations.setAddress(node.path("address").asText());
                firestations.setStation(node.path("station").asText());
                firestationsArrayList.add(firestations);
                id ++;
            }
            return firestationsArrayList;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    default void saveFirestation(Firestations firestations) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        File file = new File("src/main/resources/data.json");
        JsonNode jsonNode = mapper.readTree(file);
        JsonNode FirestationsNode = jsonNode.path("firestations");
        ArrayList<Firestations> firestationsArrayList = findAllFirestations();
        Firestations lastFirestation = firestationsArrayList.get(firestationsArrayList.size() - 1);
        Long lastId = lastFirestation.getId();
        Long newId = lastId + 1;
        Firestations firestationToSave = new Firestations();
        firestationToSave.setId(newId);
        firestationToSave.setStation(firestations.getStation());
        firestationToSave.setAddress(firestations.getAddress());
        firestationsArrayList.add(firestationToSave);
        System.out.println(firestationToSave);
        /*for (JsonNode node : FirestationsNode) {

        }*/
        mapper.writeValue(file, firestationsArrayList);
    }

    default Firestations findFirestationById(final Long id) {
        ArrayList<Firestations> firestations = findAllFirestations();
        for (Firestations firestation : firestations) {
            if (Objects.equals(id, firestation.getId())) {
                return firestation;
            }
        }
        return null;
    }



}
