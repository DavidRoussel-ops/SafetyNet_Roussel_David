package com.SafetyNet.SafetyNetAlerts.Repository;

import com.SafetyNet.SafetyNetAlerts.Model.MedicalRecords;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class MedicalRecordsRepositoryTest {

    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;

    public void setUpMockMvc() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Autowired
    private MedicalRecordsRepository medicalRecordsRepository;

    @AfterEach
    public void setUp() throws IOException {
        ArrayList<MedicalRecords> medicalRecords = medicalRecordsRepository.findAllMedicalRecords();
        if (medicalRecords.size() == 24) {
            medicalRecordsRepository.deleteMedicalRecord(24L);
        }
    }

    @Test
    public void testFindAllMedicalRecords() {
        ArrayList<MedicalRecords> expect = medicalRecordsRepository.findAllMedicalRecords();
        assertThat(expect).isNotNull();
        Assertions.assertEquals(23, expect.size());
    }

    @Test
    public void testFindMedicalRecordById() {
        Long id = 1L;
        MedicalRecords medicalRecords = medicalRecordsRepository.findMedicalRecordById(id);
        assertThat(medicalRecords).isNotNull();
        Assertions.assertEquals("John", medicalRecords.getFirstName());
    }

    @Test
    public void testSaveMedicalRecord() throws IOException {
        String[] arrayMedications = new String[]{"aznol:350mg", "hydrapermazol:100mg"};
        String[] arrayAllergies = new String[]{"nillacilan"};
        MedicalRecords medicalRecords = new MedicalRecords();
        medicalRecords.setFirstName("Michel");
        medicalRecords.setLastName("Boydo");
        medicalRecords.setBirthdate("03/06/1984");
        medicalRecords.setMedications(arrayMedications);
        medicalRecords.setAllergies(arrayAllergies);
        medicalRecordsRepository.saveMedicalRecord(medicalRecords);
        ArrayList<MedicalRecords> expect = medicalRecordsRepository.findAllMedicalRecords();
        assertThat(expect).isNotNull();
        Assertions.assertEquals("Michel", expect.get(23).getFirstName());
    }

    @Test
    public void testUpdateMedicalRecord() throws IOException {
        String[] arrayMedications = new String[]{"aznol:350mg", "hydrapermazol:100mg"};
        String[] arrayAllergies = new String[]{"pénicilline"};
        MedicalRecords medicalRecords = new MedicalRecords();
        medicalRecords.setId(1L);
        medicalRecords.setFirstName("John");
        medicalRecords.setLastName("Boyd");
        medicalRecords.setBirthdate("03/06/1984");
        medicalRecords.setMedications(arrayMedications);
        medicalRecords.setAllergies(arrayAllergies);
        medicalRecordsRepository.updateMedicalRecord(medicalRecords);
        ArrayList<MedicalRecords> expect = medicalRecordsRepository.findAllMedicalRecords();
        assertThat(expect).isNotNull();
        Assertions.assertEquals(Arrays.toString(arrayAllergies), Arrays.toString(expect.get(0).getAllergies()));
    }

    @Test
    public void testDeleteMedicalRecord() {
        medicalRecordsRepository.deleteById(24L);
        ArrayList<MedicalRecords> expect = medicalRecordsRepository.findAllMedicalRecords();
        assertThat(expect).isNotNull();
        Assertions.assertEquals(23, expect.size());
    }

}
