package com.SafetyNet.SafetyNetAlerts.Controller;

import com.SafetyNet.SafetyNetAlerts.Model.Firestations;
import com.SafetyNet.SafetyNetAlerts.Service.FirestationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class FirestationsController {

    @Autowired
    private FirestationsService firestationsService;

    /**
     * Create - Add a new Firestation
     * @param firestations an object
     * @return the firestation object saved
     */
    @PostMapping("/firestation")
    public ResponseEntity<Firestations> createFirestations(@RequestBody Firestations firestations) {
        Firestations firestations1 = firestationsService.saveFirestation(firestations);
        try {
            return new ResponseEntity<>(firestations1, HttpStatus.CREATED);
        } catch (Exception e) {
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
        Optional<Firestations> firestation = firestationsService.getFirestation(id);
        return firestation.map(station -> new ResponseEntity<>(station, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Read - Get all firestations
     * @return - An iterable object of Firestations
     */
    @GetMapping("/firestation")
    public ResponseEntity<Iterable<Firestations>> getAllFirestations() {
        Iterable<Firestations> firestations = firestationsService.getFirestations();
        try {
            return new ResponseEntity<>(firestations, HttpStatus.OK);
        } catch (Exception e) {
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
        Optional<Firestations> firestationsOptional = firestationsService.getFirestation(id);
        if (firestationsOptional.isPresent()) {
            Firestations currentFirestation = firestationsOptional.get();

            String address = firestation.getAddress();
            if (address != null) {
                currentFirestation.setAddress(address);
            }

            String station = firestation.getStation();
            if (station != null) {
                currentFirestation.setStation(station);
            }
            return new ResponseEntity<>(firestationsService.saveFirestation(currentFirestation), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Delete - Delete an firestation
     * @param id - the id of the firestation to delete
     */
    @DeleteMapping("/firestation/{id}")
    public ResponseEntity<HttpStatus> deleteFirestation(@PathVariable("id") final Long id) {
        try {
            firestationsService.deleteFirestation(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
