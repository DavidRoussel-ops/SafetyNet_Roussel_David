package com.SafetyNet.SafetyNetAlerts.Service;

import com.SafetyNet.SafetyNetAlerts.Model.MedicalRecords;
import com.SafetyNet.SafetyNetAlerts.Repository.MedicalRecordsRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
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
}
