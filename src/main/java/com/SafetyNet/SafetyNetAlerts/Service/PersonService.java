package com.SafetyNet.SafetyNetAlerts.Service;

import com.SafetyNet.SafetyNetAlerts.Model.Persons;
import com.SafetyNet.SafetyNetAlerts.Repository.PersonsRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonService {

    private final PersonsRepository personsRepository;

    public PersonService(PersonsRepository personsRepository) {
        this.personsRepository = personsRepository;
    }

    public Iterable<Persons> list() {
        return personsRepository.findAll();
    }

    public Persons save(Persons persons) {
        return personsRepository.save(persons);
    }

    public void save(List<Persons> persons) {
        personsRepository.saveAll(persons);
    }
}
