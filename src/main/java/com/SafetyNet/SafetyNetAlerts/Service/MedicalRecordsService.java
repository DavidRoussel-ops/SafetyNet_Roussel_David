package com.SafetyNet.SafetyNetAlerts.Service;

import com.SafetyNet.SafetyNetAlerts.Model.MedicalRecords;
import com.SafetyNet.SafetyNetAlerts.Repository.MedicalRecordsRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Data
@Service
public class MedicalRecordsService {

    @Autowired
    private MedicalRecordsRepository medicalRecordsRepository;

    /**
     * @param id
     * @return MedicalRecords
     */
    public Optional<MedicalRecords> getMedicalRecord(final Long id) {
        return medicalRecordsRepository.findById(id);
    }

    /**
     * @return MedicalRecords
     */
    public Iterable<MedicalRecords> getMedicalRecords() {
        return medicalRecordsRepository.findAll();
    }

    /**
     * @param id
     */
    public void deleteMedicalRecord(final Long id) {
        medicalRecordsRepository.deleteById(id);
    }

    /**
     * @param medicalRecords
     * @return MedicalRecords
     */
    public MedicalRecords saveMedicalRecord(MedicalRecords medicalRecords) {
        return medicalRecordsRepository.save(medicalRecords);
    }

    public MedicalRecords putMedicalRecord(final Long id, final MedicalRecords medicalRecord) {
        Optional<MedicalRecords> medicalRecordsOptional = getMedicalRecord(id);
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
            return saveMedicalRecord(currentMedicalRecords);
        } else {
            return null;
        }
    }
}
