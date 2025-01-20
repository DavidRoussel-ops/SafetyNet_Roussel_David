package com.SafetyNet.SafetyNetAlerts.Service;

import com.SafetyNet.SafetyNetAlerts.Model.Persons;
import com.SafetyNet.SafetyNetAlerts.Repository.PersonsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;


@Service
public class PersonService {

    @Autowired
    private PersonsRepository personsRepository;

    /**
     * @param id
     * @return Persons
     */
    public Persons getPerson(final Long id) {
        return personsRepository.findPersonById(id);
    }

    /**
     * @return Persons
     */
    public ArrayList<Persons> getPersons() {
        return personsRepository.findAllPersons();
    }

    /**
     * @param id
     */
    public void deletePerson(final Long id) throws IOException {
        personsRepository.deletePerson(id);
    }

    /**
     * @param persons
     * @return Persons
     */
    public void savePerson(Persons persons) throws IOException {
        personsRepository.savePerson(persons);
    }

    /**
     *
     * @param id
     * @param persons
     * @return currentPersons
     */
    public void putPerson(final Persons persons) throws IOException {
        personsRepository.updatePerson(persons);
        /*Optional<Persons> personsOptional = getPerson(id);
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
            return savePerson(currentPersons);
        } else {
            return null;
        }*/
    }
}
