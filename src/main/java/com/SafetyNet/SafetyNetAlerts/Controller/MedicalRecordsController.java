package com.SafetyNet.SafetyNetAlerts.Controller;

import com.SafetyNet.SafetyNetAlerts.Model.MedicalRecords;
import com.SafetyNet.SafetyNetAlerts.Service.MedicalRecordsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class MedicalRecordsController {

    @Autowired
    private MedicalRecordsService medicalRecordsService;

    private static final Logger logger = LoggerFactory.getLogger(MedicalRecordsController.class);

    /**
     * Create - add a new medicalRecord
     *
     * @param medicalRecords an object MedicalRecords
     * @return the medicalRecords object saved
     */
    @PostMapping("/medicalRecord")
    public ResponseEntity<MedicalRecords> createMedicalRecord(@RequestBody MedicalRecords medicalRecords) {
        logger.info("Requête createMedicalRecord avec en paramètre: {}", medicalRecords);
        MedicalRecords medicalRecords1 = medicalRecordsService.saveMedicalRecord(medicalRecords);
        logger.debug("medicalRecordsService.saveMedicalRecord() en cours : {}", medicalRecords1);
        try {
            logger.info("Réponse réussi pour lla requête createMedicalRecord : {}", medicalRecords);
            return new ResponseEntity<>(medicalRecords1, HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("Erreur lors du traitement de la requête createMedicalRecord : {}", e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Read - Get one medicalRecords
     *
     * @param id the id of the medicalRecords
     * @return an medicalRecords object
     */
    @GetMapping("/medicalRecord/{id}")
    public ResponseEntity<MedicalRecords> getMedicalRecord(@PathVariable("id") final Long id) {
        logger.info("Requête getMedicalRecord avec en paramètre: {}", id);
        Optional<MedicalRecords> medicalRecords = medicalRecordsService.getMedicalRecord(id);
        logger.debug("medicalRecordsService.getMedicalRecord() en cours : {}", medicalRecords);
        try {
            logger.info("Réponse réussi pour la requête getMedicalRecord : {}", medicalRecords);
            return medicalRecords.map(records -> new ResponseEntity<>(records, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            logger.error("Erreur lors du traitement de la requête getMedicalRecord : {}", e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Read - Get all medicalRecords
     *
     * @return - An iterable object of firestation
     */
    @GetMapping("/medicalRecord")
    public ResponseEntity<Iterable<MedicalRecords>> getAllMedicalRecords() {
        logger.info("Requête getAllMMedicalRecords");
        Iterable<MedicalRecords> medicalRecords = medicalRecordsService.getMedicalRecords();
        logger.debug("medicalRecordsService.getMedicalRecords() en cours : {}", medicalRecords);
        try {
            logger.info("Réponse réussi pour la requête getAllMedicalRecords: {}", medicalRecords);
            return new ResponseEntity<>(medicalRecords, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error lors du traitement de la requête getAllMedicalRecords: {}", e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Update - Update an existing medicalRecords
     *
     * @param id            - the id of the medicalRecord to update
     * @param medicalRecord - the medicalRecord object updated
     * @return currentMedicalRecords
     */
    @PutMapping("/medicalRecord/{id}")
    public ResponseEntity<MedicalRecords> updateMedicalRecord(@PathVariable("id") final Long id, @RequestBody MedicalRecords medicalRecord) {
        logger.info("Requête updateMedicalRecord avec en paramêtre: {}", id);
        Optional<MedicalRecords> medicalRecordsOptional = medicalRecordsService.getMedicalRecord(id);
        logger.debug("medicalRecordsService.getMedicalRecord() en cours : {}", medicalRecordsOptional);
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
            logger.info("Réponse réussi pour la requête updateMedicalRecord: {}", medicalRecordsOptional);
            return new ResponseEntity<>(medicalRecordsService.saveMedicalRecord(currentMedicalRecords), HttpStatus.OK);
        } else {
            logger.error("Erreur lors du traitement de la requête updateMedicalRecord");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Delete - delete an medicalRecords
     *
     * @param id - the id of the medicalRecords to delete
     */
    @DeleteMapping("/medicalRecord/{id}")
    public ResponseEntity<HttpStatus> deleteMedicalRecord(@PathVariable("id") final Long id) {
        logger.info("Requête deleteMedicalRecord avec en paramètre: {}", id);
        try {
            medicalRecordsService.deleteMedicalRecord(id);
            logger.info("Réponse réussi pour la requête deleteMedicalRecord: {}", HttpStatus.NO_CONTENT);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            logger.error("Erreur lors du traitement de la requête deleteMedicalRecord: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
