package com.SafetyNet.SafetyNetAlerts.Controller;

import com.SafetyNet.SafetyNetAlerts.Model.Persons;
import com.SafetyNet.SafetyNetAlerts.Service.PersonService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/personInfolastName=<lastName>")
public class PersonsController {

    private final PersonService personService;

    public PersonsController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping("/person")
    public Iterable<Persons> list() {
        return personService.list();
    }
}
