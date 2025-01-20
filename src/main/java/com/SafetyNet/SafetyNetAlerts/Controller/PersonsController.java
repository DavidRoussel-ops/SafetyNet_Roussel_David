package com.SafetyNet.SafetyNetAlerts.Controller;


import com.SafetyNet.SafetyNetAlerts.Model.Persons;
import com.SafetyNet.SafetyNetAlerts.Service.PersonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
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
    public ResponseEntity<HttpStatus> createPersons(@RequestBody Persons persons) throws IOException {
        logger.info("Requête createPersons avec en paramètre: {}", persons);
        personService.savePerson(persons);
        try {
            logger.info("Réponse réussi pour la requête createPersons: {}", HttpStatus.CREATED);
            return new ResponseEntity<>(HttpStatus.CREATED);
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
        Persons persons = personService.getPerson(id);
        logger.debug("personService.getPerson() en cours : {}", persons);
        try {
            return new ResponseEntity<>(persons, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Erreur lors du traitement de la requête getPerson: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Read - Get all person
     *
     * @return - An Iterable object of person
     */
    @GetMapping("/person")
    public ResponseEntity<ArrayList<Persons>> getAllPersons() {
        logger.info("Requête getAllPersons");
        ArrayList<Persons> persons = personService.getPersons();
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
    public ResponseEntity<HttpStatus> updatePerson(@PathVariable("id") final Long id, @RequestBody Persons persons) throws IOException {
        logger.info("Requête updatePerson avec en paramètre: {}", id);
        persons.setId(id);
        personService.putPerson(persons);
        try {
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Erreur lors du traitement de la requête updatePerson: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
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
