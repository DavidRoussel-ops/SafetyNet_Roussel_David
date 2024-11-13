package com.SafetyNet.SafetyNetAlerts.Controller;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.SafetyNet.SafetyNetAlerts.Model.Firestations;
import com.SafetyNet.SafetyNetAlerts.Model.MedicalRecords;
import com.SafetyNet.SafetyNetAlerts.Service.MedicalRecordsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class MedicalRecordsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MedicalRecordsService service;

    @Test
    public void testGetMedicalRecords() throws Exception {
        mockMvc.perform(get("/medicalRecord"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetOnePerson() throws Exception {
        mockMvc.perform(get("/medicalRecord/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("firstname", is("John")));
    }

    @Test
    public void testAddOnePerson() throws Exception {
        MedicalRecords medicalRecords = new MedicalRecords();
        medicalRecords.setFirstname("Nigel");
        medicalRecords.setLastname("Miguel");
        medicalRecords.setBirthdate("05/06/1953");
        medicalRecords.setMedications("aznol:350mg");
        medicalRecords.setAllergies("nillacilan");
        medicalRecords.setId(24L);
        service.saveMedicalRecord(medicalRecords);
        mockMvc.perform(get("/medicalRecord/24"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("birthdate", is("05/06/1953")));
    }

}