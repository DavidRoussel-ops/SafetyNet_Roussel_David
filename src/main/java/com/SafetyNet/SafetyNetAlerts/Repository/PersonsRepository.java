package com.SafetyNet.SafetyNetAlerts.Repository;

import com.SafetyNet.SafetyNetAlerts.Model.Persons;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

@Repository
public interface PersonsRepository extends CrudRepository<Persons, Long> {

    default ArrayList<Persons> findAllPersons() {
        try {
            Long id = 1L;
            ArrayList<Persons> personsArrayList = new ArrayList<>();
            ObjectMapper mapper = new ObjectMapper();
            File file = new File("src/main/resources/data.json");
            JsonNode jsonNode = mapper.readTree(file);
            JsonNode PersonsNode = jsonNode.path("persons");
            for (JsonNode node : PersonsNode) {
                Persons persons = new Persons();
                persons.setId(id);
                persons.setFirstName(node.path("firstName").asText());
                persons.setLastName(node.path("lastName").asText());
                persons.setAddress(node.path("address").asText());
                persons.setCity(node.path("city").asText());
                persons.setZip(node.path("zip").asText());
                persons.setPhone(node.path("phone").asText());
                persons.setEmail(node.path("email").asText());
                personsArrayList.add(persons);
                id ++;
            }
            return personsArrayList;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    default void updatePerson(Persons persons) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        File file = new File("src/main/resources/data.json");
        JsonNode jsonNode = mapper.readTree(file);
        ArrayList<Persons> personsArrayList = findAllPersons();
        for (Persons persons1 : personsArrayList) {
            if (Objects.equals(persons1.getId(), persons.getId())) {
                persons1.setFirstName(persons.getFirstName());
                persons1.setLastName(persons.getLastName());
                persons1.setAddress(persons.getAddress());
                persons1.setCity(persons.getCity());
                persons1.setZip(persons.getZip());
                persons1.setPhone(persons.getPhone());
                persons1.setEmail(persons.getEmail());
                JsonNode nodeMedicalRecord = mapper.valueToTree(personsArrayList);
                ObjectNode addNode = ((ObjectNode) jsonNode).set("persons", nodeMedicalRecord);
                mapper.writerWithDefaultPrettyPrinter().writeValue(file, addNode);
            }
        }
    }

    default void deletePerson(Long id) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        File file = new File("src/main/resources/data.json");
        JsonNode jsonNode = mapper.readTree(file);
        ArrayList<Persons> personsArrayList = findAllPersons();
        personsArrayList.removeIf(persons1 -> Objects.equals(persons1.getId(), id));
        JsonNode nodeMedicalRecord = mapper.valueToTree(personsArrayList);
        ObjectNode addNode = ((ObjectNode) jsonNode).set("persons", nodeMedicalRecord);
        mapper.writerWithDefaultPrettyPrinter().writeValue(file, addNode);
    }

    default void savePerson(Persons persons) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        File file = new File("src/main/resources/data.json");
        JsonNode jsonNode = mapper.readTree(file);
        ArrayList<Persons> personsArrayList = findAllPersons();
        Persons lastPerson = personsArrayList.get(personsArrayList.size() - 1);
        Long lastId = lastPerson.getId();
        Long newId = lastId + 1;
        Persons personToSave = new Persons();
        personToSave.setId(newId);
        personToSave.setFirstName(persons.getFirstName());
        personToSave.setLastName(persons.getLastName());
        personToSave.setAddress(persons.getAddress());
        personToSave.setCity(persons.getCity());
        personToSave.setZip(persons.getZip());
        personToSave.setPhone(persons.getPhone());
        personToSave.setEmail(persons.getEmail());
        personsArrayList.add(personToSave);
        JsonNode nodeMedicalRecord = mapper.valueToTree(personsArrayList);
        ObjectNode addNode = ((ObjectNode) jsonNode).set("persons", nodeMedicalRecord);
        mapper.writerWithDefaultPrettyPrinter().writeValue(file, addNode);
    }

    default Persons findPersonById(final Long id) {
        ArrayList<Persons> persons = findAllPersons();
        for (Persons persons1 : persons) {
            if (Objects.equals(id, persons1.getId())) {
                return persons1;
            }
        }
        return null;
    }

}
