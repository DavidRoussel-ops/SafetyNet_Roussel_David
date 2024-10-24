package com.SafetyNet.SafetyNetAlerts.Controller;

import com.SafetyNet.SafetyNetAlerts.Model.Firestations;
import com.SafetyNet.SafetyNetAlerts.Service.FirestationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FirestationsController {

    @Autowired
    private FirestationsService firestationsService;

    @GetMapping("/firestation")
    public Iterable<Firestations> getFirestations() {
        return firestationsService.getFirestations();
    }
}
