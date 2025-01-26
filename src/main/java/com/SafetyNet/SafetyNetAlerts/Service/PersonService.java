package com.SafetyNet.SafetyNetAlerts.Service;

import com.SafetyNet.SafetyNetAlerts.Model.Persons;
import com.SafetyNet.SafetyNetAlerts.Repository.PersonsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;


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
     * @param persons
     * @return currentPersons
     */
    public void putPerson(final Persons persons) throws IOException {
        personsRepository.updatePerson(persons);
    }
}
