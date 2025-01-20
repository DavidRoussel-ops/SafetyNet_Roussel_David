package com.SafetyNet.SafetyNetAlerts.Service;

import com.SafetyNet.SafetyNetAlerts.Model.MedicalRecords;
import com.SafetyNet.SafetyNetAlerts.Repository.MedicalRecordsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

@Service
public class MedicalRecordsService {

    @Autowired
    private MedicalRecordsRepository medicalRecordsRepository;

    /**
     * @param id
     * @return MedicalRecords
     */
    public MedicalRecords getMedicalRecord(final Long id) {
        return medicalRecordsRepository.findMedicalRecordById(id);
    }

    /**
     * @return MedicalRecords
     */
    public ArrayList<MedicalRecords> getMedicalRecords() {
        return medicalRecordsRepository.findAllMedicalRecords();
    }

    /**
     * @param id
     */
    public void deleteMedicalRecord(final Long id) throws IOException {
        medicalRecordsRepository.deleteMedicalRecord(id);
    }

    /**
     * @param medicalRecords
     * @return MedicalRecords
     */
    public void saveMedicalRecord(MedicalRecords medicalRecords) throws IOException {
        medicalRecordsRepository.saveMedicalRecord(medicalRecords);
    }

    /**
     *
     * @param id
     * @param medicalRecord
     * @return currentMedicalRecords
     */
    public void putMedicalRecord(final MedicalRecords medicalRecord) throws IOException {
        medicalRecordsRepository.updateMedicalRecord(medicalRecord);
    }
}
