package com.SafetyNet.SafetyNetAlerts.Controller;

import com.SafetyNet.SafetyNetAlerts.Model.Persons;
import com.SafetyNet.SafetyNetAlerts.Service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class PersonsController {

    @Autowired
    private PersonService personService;

    /**
     * Create - Add a new person
     * @param persons an Object Persons
     * @return the persons object saved
     */
    @PostMapping("/person")
    public ResponseEntity<Persons> createPersons(@RequestBody Persons persons) {
        Persons persons1 = personService.savePerson(persons);
        try {
            return new ResponseEntity<>(persons1, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Read - Get one persons
     * @param id the id of the persons
     * @return an persons object
     */
    @GetMapping("/person/{id}")
    public ResponseEntity<Persons> getPerson(@PathVariable("id") final Long id) {
        Optional<Persons> persons = personService.getPerson(id);
        return persons.map(person -> new ResponseEntity<>(person, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Read - Get all person
     * @return - An Iterable object of Employee
     */
    @GetMapping("/person")
    public ResponseEntity<Iterable<Persons>> getAllPersons() {
        Iterable<Persons> persons = personService.getPersons();
        try {
            return new ResponseEntity<>(persons, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Update - Update an existing persons
     * @param id - The id of the persons to update
     * @param persons - The persons object update
     * @return
     */
    @PutMapping("/person/{id}")
    public ResponseEntity<Persons> updatePerson(@PathVariable("id") final Long id, @RequestBody Persons persons) {
        Optional<Persons> personsOptional = personService.getPerson(id);
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
            return new ResponseEntity<>(personService.savePerson(currentPersons), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Delete - Delete an person
     * @param id - The id of the person to delete
     */
    @DeleteMapping("/person/{id}")
    public ResponseEntity<HttpStatus> deletePerson(@PathVariable("id") final Long id) {
        try {
            personService.deletePerson(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
