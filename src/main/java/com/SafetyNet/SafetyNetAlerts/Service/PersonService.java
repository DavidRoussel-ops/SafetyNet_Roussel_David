package com.SafetyNet.SafetyNetAlerts.Service;

import com.SafetyNet.SafetyNetAlerts.Model.Persons;
import com.SafetyNet.SafetyNetAlerts.Repository.PersonsRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Data
@Service
public class PersonService {

    @Autowired
    private final PersonsRepository personsRepository;

    public Optional<Persons> getPerson(final Long id) {
        return personsRepository.findById(id);
    }

    public Iterable<Persons> getPersons() {
        return personsRepository.findAll();
    }

    public void deletePerson(final Long id) {
        personsRepository.deleteById(id);
    }

    public Persons savePerson(Persons persons) {
        return personsRepository.save(persons);
    }
}
