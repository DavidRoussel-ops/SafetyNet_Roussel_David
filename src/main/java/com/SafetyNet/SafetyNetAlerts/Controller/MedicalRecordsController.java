package com.SafetyNet.SafetyNetAlerts.Controller;

import com.SafetyNet.SafetyNetAlerts.Model.MedicalRecords;
import com.SafetyNet.SafetyNetAlerts.Service.MedicalRecordsService;
import jakarta.persistence.Column;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class MedicalRecordsController {

    @Autowired
    private MedicalRecordsService medicalRecordsService;

    /**
     * Create - add a new medicalRecord
     * @param medicalRecords an object MedicalRecords
     * @return the medicalRecords object saved
     */
    @PostMapping("/medicalRecord")
    public MedicalRecords createMedicalRecord(@RequestBody MedicalRecords medicalRecords) {
        return medicalRecordsService.saveMedicalRecord(medicalRecords);
    }

    /**
     * Read - Get one medicalRecords
     * @param id the id of the medicalRecords
     * @return an medicalRecords object
     */
    @GetMapping("/medicalRecord/{id}")
    public MedicalRecords getMedicalRecord(@PathVariable("id") final Long id) {
        Optional<MedicalRecords> medicalRecords = medicalRecordsService.getMedicalRecord(id);
        return medicalRecords.orElse(null);
    }

    /**
     * Read - Get all medicalRecords
     * @return - An iterable object of firestation
     */
    @GetMapping("/medicalRecord")
    public Iterable<MedicalRecords> getMedicalRecords() {
        return medicalRecordsService.getMedicalRecords();
    }

    /**
     * Update - Update an existing medicalRecords
     * @param id - the id of the medicalRecord to update
     * @param medicalRecord - the medicalRecord object updated
     * @return
     */
    @PutMapping("/medicalRecord/{id}")
    public MedicalRecords updateMedicalRecord(@PathVariable("id") final Long id, @RequestBody MedicalRecords medicalRecord) {
        Optional<MedicalRecords> medicalRecordsOptional = medicalRecordsService.getMedicalRecord(id);
        if (medicalRecordsOptional.isPresent()) {
            MedicalRecords currentMedicalRecords = medicalRecordsOptional.get();


            String firstname = medicalRecord.getFirstname();
            if (firstname != null) {
                currentMedicalRecords.setFirstname(firstname);
            }
            String lastname = medicalRecord.getLastname();
            if (lastname != null) {
                currentMedicalRecords.setLastname(lastname);
            }
            String birthdate = medicalRecord.getBirthdate();
            if (birthdate != null) {
                currentMedicalRecords.setBirthdate(birthdate);
            }
            String medications = medicalRecord.getMedications();
            if (medications != null) {
                currentMedicalRecords.setMedications(medications);
            }
            String allergies = medicalRecord.getAllergies();
            if (allergies != null) {
                currentMedicalRecords.setAllergies(allergies);
            }
            medicalRecordsService.saveMedicalRecord(currentMedicalRecords);
            return currentMedicalRecords;
        } else {
            return null;
        }
    }

    /**
     * Delete - delete an medicalRecords
     * @param id - the id of the medicalRecords to delete
     */
    @DeleteMapping("/medicalRecord/{id}")
    public void deleteMedicalRecord(@PathVariable("id") final Long id) {
        medicalRecordsService.deleteMedicalRecord(id);
    }
}
