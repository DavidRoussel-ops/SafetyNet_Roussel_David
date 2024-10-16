package com.SafetyNet.SafetyNetAlerts.Service;

import com.SafetyNet.SafetyNetAlerts.Model.Firestations;
import com.SafetyNet.SafetyNetAlerts.Model.MedicalRecords;
import com.SafetyNet.SafetyNetAlerts.Model.Persons;
import org.springframework.stereotype.Component;

@Component
public class BusinessService {

    public Persons getPersons() {
        Persons persons = new Persons();
        return persons;
    }

    public Firestations getFirestations() {
        Firestations firestations = new Firestations();
        return firestations;
    }

    public MedicalRecords getMedicalRecords() {
        MedicalRecords medicalRecords = new MedicalRecords();
        return medicalRecords;
    }
}
