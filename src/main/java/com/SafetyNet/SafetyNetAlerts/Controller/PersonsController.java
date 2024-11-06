package com.SafetyNet.SafetyNetAlerts.Controller;

import com.SafetyNet.SafetyNetAlerts.Model.Persons;
import com.SafetyNet.SafetyNetAlerts.Service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public Persons createPersons(@RequestBody Persons persons) {
        return personService.savePerson(persons);
    }

    /**
     * Read - Get one persons
     * @param id the id of the persons
     * @return an persons object
     */
    @GetMapping("/person/{id}")
    public Persons getPerson(@PathVariable("id") final Long id) {
        Optional<Persons> persons = personService.getPerson(id);
        return persons.orElse(null);
    }

    /**
     * Read - Get all person
     * @return - An Iterable object of Employee
     */
    @GetMapping("/person")
    public Iterable<Persons> getPersons() {
        return personService.getPersons();
    }

    /**
     * Update - Update an existing persons
     * @param id - The id of the persons to update
     * @param persons - The persons object update
     * @return
     */
    @PutMapping("/person/{id}")
    public Persons updatePerson(@PathVariable("id") final Long id, @RequestBody Persons persons) {
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
            personService.savePerson(currentPersons);
            return currentPersons;
        } else {
            return null;
        }
    }

    /**
     * Delete - Delete an person
     * @param id - The id of the person to delete
     */
    @DeleteMapping("/person/{id}")
    public void deletePerson(@PathVariable("id") final Long id) {
        personService.deletePerson(id);
    }
}
