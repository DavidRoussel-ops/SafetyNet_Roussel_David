package com.SafetyNet.SafetyNetAlerts.Service;

import com.SafetyNet.SafetyNetAlerts.Model.Firestations;
import com.SafetyNet.SafetyNetAlerts.Repository.FirestationsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;

@Service
public class FirestationsService {

    @Autowired
    private FirestationsRepository firestationsRepository;

    /**
     * @param id
     * @return Firestations
     */
    public Firestations getFirestation(final Long id) {
        return firestationsRepository.findFirestationById(id);
    }

    /**
     * @return Firestations
     */
    public ArrayList<Firestations> getFirestations() {
        return firestationsRepository.findAllFirestations();
    }

    /**
     * @param id
     */
    public void deleteFirestation(final Long id) throws IOException {
        firestationsRepository.deleteFirestation(id);
    }

    /**
     * @param firestations
     * @return Firestations
     */
    public void saveFirestation(Firestations firestations) throws IOException {
        firestationsRepository.saveFirestation(firestations);
    }

    /**
     * @param firestations
     * @return currentFirestation
     */
    public void putFirestation(final Firestations firestations) throws IOException {
        firestationsRepository.updateFirestation(firestations);
    }
}
