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

    public Optional<MedicalRecords> getMedicalRecord(final Long id) {
        return medicalRecordsRepository.findById(id);
    }

    public Iterable<MedicalRecords> getMedicalRecords() {
        return medicalRecordsRepository.findAll();
    }

    public void deleteMedicalRecord(final Long id) {
        medicalRecordsRepository.deleteById(id);
    }

    public MedicalRecords saveMedicalRecord(MedicalRecords medicalRecords) {
        return medicalRecordsRepository.save(medicalRecords);
    }
}
