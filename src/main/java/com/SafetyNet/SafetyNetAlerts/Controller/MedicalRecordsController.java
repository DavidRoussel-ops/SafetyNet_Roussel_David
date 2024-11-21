package com.SafetyNet.SafetyNetAlerts.Controller;

import com.SafetyNet.SafetyNetAlerts.Model.MedicalRecords;
import com.SafetyNet.SafetyNetAlerts.Service.MedicalRecordsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<MedicalRecords> createMedicalRecord(@RequestBody MedicalRecords medicalRecords) {
        MedicalRecords medicalRecords1 =  medicalRecordsService.saveMedicalRecord(medicalRecords);
        try {
            return new ResponseEntity<>(medicalRecords1, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Read - Get one medicalRecords
     * @param id the id of the medicalRecords
     * @return an medicalRecords object
     */
    @GetMapping("/medicalRecord/{id}")
    public ResponseEntity<MedicalRecords> getMedicalRecord(@PathVariable("id") final Long id) {
        Optional<MedicalRecords> medicalRecords = medicalRecordsService.getMedicalRecord(id);
        return medicalRecords.map(records -> new ResponseEntity<>(records, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Read - Get all medicalRecords
     * @return - An iterable object of firestation
     */
    @GetMapping("/medicalRecord")
    public ResponseEntity<Iterable<MedicalRecords>> getAllMedicalRecords() {
        Iterable<MedicalRecords> medicalRecords = medicalRecordsService.getMedicalRecords();
        try {
            return new ResponseEntity<>(medicalRecords, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Update - Update an existing medicalRecords
     * @param id - the id of the medicalRecord to update
     * @param medicalRecord - the medicalRecord object updated
     * @return currentMedicalRecords
     */
    @PutMapping("/medicalRecord/{id}")
    public ResponseEntity<MedicalRecords> updateMedicalRecord(@PathVariable("id") final Long id, @RequestBody MedicalRecords medicalRecord) {
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
            return new ResponseEntity<>(medicalRecordsService.saveMedicalRecord(currentMedicalRecords), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Delete - delete an medicalRecords
     * @param id - the id of the medicalRecords to delete
     */
    @DeleteMapping("/medicalRecord/{id}")
    public ResponseEntity<HttpStatus> deleteMedicalRecord(@PathVariable("id") final Long id) {
        try {
            medicalRecordsService.deleteMedicalRecord(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
