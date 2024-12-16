package com.SafetyNet.SafetyNetAlerts.Service;

import com.SafetyNet.SafetyNetAlerts.Model.Firestations;
import com.SafetyNet.SafetyNetAlerts.Repository.FirestationsRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Data
@Service
public class FirestationsService {

    @Autowired
    private FirestationsRepository firestationsRepository;

    /**
     * @param id
     * @return Firestations
     */
    public Optional<Firestations> getFirestation(final Long id) {
        return firestationsRepository.findById(id);
    }

    /**
     * @return Firestations
     */
    public Iterable<Firestations> getFirestations() {
        return firestationsRepository.findAll();
    }

    /**
     * @param id
     */
    public void deleteFirestation(final Long id) {
        firestationsRepository.deleteById(id);
    }

    /**
     * @param firestations
     * @return Firestations
     */
    public Firestations saveFirestation(Firestations firestations) {
        return firestationsRepository.save(firestations);
    }
}
