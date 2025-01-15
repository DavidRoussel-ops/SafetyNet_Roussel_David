package com.SafetyNet.SafetyNetAlerts.Repository;

import com.SafetyNet.SafetyNetAlerts.Model.Firestations;
import com.SafetyNet.SafetyNetAlerts.Service.FirestationsService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;

@Repository
public interface FirestationsRepository extends CrudRepository<Firestations, Long> {

    static Firestations firestationJSON() {
        try {
            FirestationsService firestationsService = new FirestationsService();
            ObjectMapper mapper = new ObjectMapper();
            File file = new File("src/main/resources/data.json");
            JsonNode jsonNode = mapper.readTree(file);
            JsonNode FirestationsNode = jsonNode.path("firestations");
            for (JsonNode node : FirestationsNode) {
                Firestations firestations = new Firestations();
                firestations.setAddress(node.path("address").asText());
                firestations.setStation(node.path("station").asText());
                firestationsService.saveFirestation(firestations);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

}
