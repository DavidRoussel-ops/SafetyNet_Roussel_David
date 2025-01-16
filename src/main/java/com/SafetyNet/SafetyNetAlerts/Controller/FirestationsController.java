package com.SafetyNet.SafetyNetAlerts.Controller;

import com.SafetyNet.SafetyNetAlerts.Model.Firestations;
import com.SafetyNet.SafetyNetAlerts.Service.FirestationsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
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
    public ResponseEntity<HttpStatus> createFirestations(@RequestBody Firestations firestations) throws IOException {
        logger.info("Requête createFirestations avec en paramètre: {}", firestations);
        firestationsService.saveFirestation(firestations);
        //logger.debug("firestationsService.saveFirestation() en cours : {}", firestationsService.saveFirestation(firestations));
        try {
            logger.info("Réponse réussi pour la requête createFirestations: {}", HttpStatus.CREATED);
            return new ResponseEntity<>(HttpStatus.CREATED);
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
        Firestations firestation = firestationsService.getFirestation(id);
        logger.debug("firestationsService.getFirestation() en cours : {}", firestation);
        try {
            return new ResponseEntity<>(firestation, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Erreur lors du traitement de la requête getFirestation: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Read - Get all firestations
     * @return - An iterable object of Firestations
     */
    @GetMapping("/firestation")
    public ResponseEntity<ArrayList<Firestations>> getAllFirestations() {
        logger.info("Requête getAllFirestations");
        ArrayList<Firestations> firestations = firestationsService.getFirestations();
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
    public ResponseEntity<Firestations> updateFirestation(@PathVariable("id") final Long id, @RequestBody Firestations firestation) throws IOException {
        logger.info("Requête updateFirestation avec en paramètre: {}", id);
        Firestations firestations1 = firestationsService.putFirestation(id, firestation);
        try {
            return new ResponseEntity<>(firestations1, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Erreur lors du traitement de la requête updateFirestation: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
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
