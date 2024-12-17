package com.SafetyNet.SafetyNetAlerts.Controller;


import com.SafetyNet.SafetyNetAlerts.Model.Persons;
import com.SafetyNet.SafetyNetAlerts.Service.BusinessService;
import com.SafetyNet.SafetyNetAlerts.Service.FirestationsService;
import com.SafetyNet.SafetyNetAlerts.Service.MedicalRecordsService;
import com.SafetyNet.SafetyNetAlerts.Service.PersonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class PersonsController {

    @Autowired
    private PersonService personService;

    private static final Logger logger = LoggerFactory.getLogger(PersonsController.class);

    /**
     * Create - Add a new person
     *
     * @param persons an Object Persons
     * @return the persons object saved
     */
    @PostMapping("/person")
    public ResponseEntity<Persons> createPersons(@RequestBody Persons persons) {
        logger.info("Requête createPersons avec en paramètre: {}", persons);
        Persons persons1 = personService.savePerson(persons);
        logger.debug("personService.savePerson() en cours : {}", persons1);
        try {
            logger.info("Réponse réussi pour la requête createPersons: {}", persons1);
            return new ResponseEntity<>(persons1, HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("Erreur lors du traitement de la requête createPersons: {}", e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Read - Get one persons
     *
     * @param id the id of the persons
     * @return an persons object
     */
    @GetMapping("/person/{id}")
    public ResponseEntity<Persons> getPerson(@PathVariable("id") final Long id) {
        logger.info("Requête getPerson avec en paramètre: {}", id);
        Optional<Persons> persons = personService.getPerson(id);
        logger.debug("personService.getPerson() en cours : {}", persons);
        return persons.map(person -> new ResponseEntity<>(person, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Read - Get all person
     *
     * @return - An Iterable object of person
     */
    @GetMapping("/person")
    public ResponseEntity<Iterable<Persons>> getAllPersons() {
        logger.info("Requête getAllPersons");
        Iterable<Persons> persons = personService.getPersons();
        logger.debug("personService.getPersons() en cours : {}", persons);
        try {
            logger.info("Réponse réussi pour la requête getAllPersons: {}", persons);
            return new ResponseEntity<>(persons, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Erreur lors du traitement de la requête getAllPersons: {}", e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Update - Update an existing persons
     *
     * @param id      - The id of the persons to update
     * @param persons - The persons object update
     * @return
     */
    @PutMapping("/person/{id}")
    public ResponseEntity<Persons> updatePerson(@PathVariable("id") final Long id, @RequestBody Persons persons) {
        logger.info("Requête updatePerson avec en paramètre: {}", id);
        Optional<Persons> personsOptional = personService.getPerson(id);
        logger.debug("personService.getPerson() en cours : {}", personsOptional);
        if (personsOptional.isPresent()) {
            Persons currentPersons = personsOptional.get();

            String firstName = persons.getFirstname();
            if (firstName != null) {
                currentPersons.setFirstname(firstName);
            }
            String lastName = persons.getLastname();
            if (lastName != null) {
                currentPersons.setLastname(lastName);
            }
            String address = persons.getAddress();
            if (address != null) {
                currentPersons.setAddress(address);
            }
            String city = persons.getCity();
            if (city != null) {
                currentPersons.setCity(city);
            }
            String zip = persons.getZip();
            if (zip != null) {
                currentPersons.setZip(zip);
            }
            String phone = persons.getPhone();
            if (phone != null) {
                currentPersons.setPhone(phone);
            }
            String email = persons.getEmail();
            if (email != null) {
                currentPersons.setEmail(email);
            }
            logger.info("Réponse réussi pour la requête updatePerson: {}", personsOptional);
            return new ResponseEntity<>(personService.savePerson(currentPersons), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Delete - Delete an person
     *
     * @param id - The id of the person to delete
     */
    @DeleteMapping("/person/{id}")
    public ResponseEntity<HttpStatus> deletePerson(@PathVariable("id") final Long id) {
        logger.info("Requête deletePerson avec en paramètre: {}", id);
        try {
            personService.deletePerson(id);
            logger.info("Réponse réussi pour la requête deletePerson: {}", HttpStatus.NO_CONTENT);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            logger.error("Erreur lors du traitement de la requête deletePerson: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
