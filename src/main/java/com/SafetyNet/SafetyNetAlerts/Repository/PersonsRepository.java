package com.SafetyNet.SafetyNetAlerts.Repository;

import com.SafetyNet.SafetyNetAlerts.Model.Persons;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;

@Repository
public interface PersonsRepository extends CrudRepository<Persons, Long> {

    static String personsJSON() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            File file = new File("src/main/resources/data.json");
            JsonNode jsonNode = mapper.readTree(file);
            JsonNode PersonsNode = jsonNode.path("persons");
            for (JsonNode node : PersonsNode) {
                Persons persons = new Persons();
                persons.setFirstname(node.path("firstName").asText());
                persons.setLastname(node.path("lastName").asText());
                persons.setAddress(node.path("address").asText());
                persons.setCity(node.path("city").asText());
                persons.setZip(node.path("zip").asText());
                persons.setPhone(node.path("phone").asText());
                persons.setEmail(node.path("email").asText());
                return mapper.writeValueAsString(persons);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
