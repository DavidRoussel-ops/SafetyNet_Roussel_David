package com.SafetyNet.SafetyNetAlerts.Service;

import com.SafetyNet.SafetyNetAlerts.Model.Persons;
import com.SafetyNet.SafetyNetAlerts.Repository.PersonsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class PersonService {

    @Autowired
    private PersonsRepository personsRepository;

    /**
     * @param id
     * @return Persons
     */
    public Optional<Persons> getPerson(final Long id) {
        return personsRepository.findById(id);
    }

    /**
     * @return Persons
     */
    public Iterable<Persons> getPersons() {
        return personsRepository.findAll();
    }

    /**
     * @param id
     */
    public void deletePerson(final Long id) {
        personsRepository.deleteById(id);
    }

    /**
     * @param persons
     * @return Persons
     */
    public Persons savePerson(Persons persons) {
        return personsRepository.save(persons);
    }

    /**
     *
     * @param id
     * @param persons
     * @return currentPersons
     */
    public Persons putPerson(final Long id, final Persons persons) {
        Optional<Persons> personsOptional = getPerson(id);
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
        }
    }
}
