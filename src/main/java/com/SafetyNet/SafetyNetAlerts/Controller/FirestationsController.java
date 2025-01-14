package com.SafetyNet.SafetyNetAlerts.Controller;

import com.SafetyNet.SafetyNetAlerts.Model.Firestations;
import com.SafetyNet.SafetyNetAlerts.Service.FirestationsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class FirestationsController {

    @Autowired
    private FirestationsService firestationsService;

    private static final Logger logger = LoggerFactory.getLogger(FirestationsController.class);

    /**
     * Create - Add a new Firestation
     * @param firestations an object
     * @return the firestation object saved
     */
    @PostMapping("/firestation")
    public ResponseEntity<Firestations> createFirestations(@RequestBody Firestations firestations) {
        logger.info("Requête createFirestations avec en paramètre: {}", firestations);
        Firestations firestations1 = firestationsService.saveFirestation(firestations);
        logger.debug("firestationsService.saveFirestation() en cours : {}", firestations1);
        try {
            logger.info("Réponse réussi pour la requête createFirestations: {}", firestations1);
            return new ResponseEntity<>(firestations1, HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("Erreur lors du traitement de la requête createFirestations : {}", e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Read - Get one firestation
     * @param id the id of the firestation
     * @return an firestation object
     */
    @GetMapping("/firestation/{id}")
    public ResponseEntity<Firestations> getFirestation(@PathVariable("id") final Long id) {
        logger.info("Requête getFirestation avec en paramètre: {}", id);
        Optional<Firestations> firestation = firestationsService.getFirestation(id);
        logger.debug("firestationsService.getFirestation() en cours : {}", firestation);
        return firestation.map(station -> new ResponseEntity<>(station, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Read - Get all firestations
     * @return - An iterable object of Firestations
     */
    @GetMapping("/firestation")
    public ResponseEntity<Iterable<Firestations>> getAllFirestations() {
        logger.info("Requête getAllFirestations");
        Iterable<Firestations> firestations = firestationsService.getFirestations();
        logger.debug("firestationsService.getFirestations() en cours : {}", firestations);
        try {
            logger.info("Réponse réussi pour la requête getAllFirestations: {}", firestations);
            return new ResponseEntity<>(firestations, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Erreur lors du traitement de la requête getAllFirestations: {}", e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Update - Update an existing firestation
     * @param id - The id of the firestation to update
     * @param firestation - The firestation object updated
     * @return
     */
    @PutMapping("/firestation/{id}")
    public ResponseEntity<Firestations> updateFirestation(@PathVariable("id") final Long id, @RequestBody Firestations firestation) {
        logger.info("Requête updateFirestation avec en paramètre: {}", id);
        Firestations firestations1 = firestationsService.putFirestation(id, firestation);
        try {
            return new ResponseEntity<>(firestations1, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Erreur lors du traitement de la requête updateFirestation: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Delete - Delete an firestation
     * @param id - the id of the firestation to delete
     */
    @DeleteMapping("/firestation/{id}")
    public ResponseEntity<HttpStatus> deleteFirestation(@PathVariable("id") final Long id) {
        logger.info("Requête deleteFirestation avec en paramètre: {}", id);
        try {
            firestationsService.deleteFirestation(id);
            logger.info("Réponse réussi pour la requête deleteFirestation: {}", HttpStatus.NO_CONTENT);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            logger.error("Erreur lors du traitement de la requête deleteFirestation: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
