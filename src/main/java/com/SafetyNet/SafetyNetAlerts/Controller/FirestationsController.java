package com.SafetyNet.SafetyNetAlerts.Controller;

import com.SafetyNet.SafetyNetAlerts.Model.Firestations;
import com.SafetyNet.SafetyNetAlerts.Service.FirestationsService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public Firestations createFirestations(@RequestBody Firestations firestations) {
        return firestationsService.saveFirestation(firestations);
    }

    /**
     * Read - Get one firestation
     * @param id the id of the firestation
     * @return an firestation object
     */
    @GetMapping("/firestation/{id}")
    public Firestations getFirestation(@PathVariable("id") final Long id) {
        Optional<Firestations> firestation = firestationsService.getFirestation(id);
        return firestation.orElse(null);
    }

    /**
     * Read - Get all firestations
     * @return - An iterable object of Firestations
     */
    @GetMapping("/firestation")
    public Iterable<Firestations> getFirestations() {
        return firestationsService.getFirestations();
    }

    /**
     * Update - Update an existing firestation
     * @param id - The id of the firestation to update
     * @param firestation - The firestation object updated
     * @return
     */
    @PutMapping("/firestation/{id}")
    public Firestations updateFirestation(@PathVariable("id") final Long id, @RequestBody Firestations firestation) {
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
            firestationsService.saveFirestation(currentFirestation);
            return currentFirestation;
        } else {
            return null;
        }
    }

    /**
     * Delete - Delete an firestation
     * @param id - the id of the firestation to delete
     */
    @DeleteMapping("/firestation/{id}")
    public void deleteFirestation(@PathVariable("id") final Long id) {
        firestationsService.deleteFirestation(id);
    }
}
