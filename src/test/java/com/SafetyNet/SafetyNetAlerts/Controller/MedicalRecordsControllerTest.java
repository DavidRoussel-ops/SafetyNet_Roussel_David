package com.SafetyNet.SafetyNetAlerts.Controller;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.SafetyNet.SafetyNetAlerts.Model.Firestations;
import com.SafetyNet.SafetyNetAlerts.Model.MedicalRecords;
import com.SafetyNet.SafetyNetAlerts.Service.MedicalRecordsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
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
    public void testGetOneMedicalRecords() throws Exception {
        mockMvc.perform(get("/medicalRecord/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("firstname", is("John")));
    }

    @Test
    public void testAddOneMedicalRecords() throws Exception {
        String medicalRecordToPost = "{ \"firstName\":\"John\", \"lastName\":\"Boyd\", \"birthdate\":\"03/06/1984\", \"medications\":[\"aznol:350mg\", \"hydrapermazol:100mg\"], \"allergies\":[\"nillacilan\"] }";
        mockMvc.perform(get("/medicalRecord")
                        .content(medicalRecordToPost)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].birthdate", is("03/06/1984")));
    }

    @Test
    public void testPutOneMedicalRecords() throws Exception {
        String medicalRecordToPut = "{ \"firstName\":\"Pierre\", \"lastName\":\"Boyd\", \"birthdate\":\"03/06/1984\", \"medications\":[\"aznol:350mg\", \"hydrapermazol:100mg\"], \"allergies\":[\"nillacilan\"] }";
        mockMvc.perform(put("/medicalRecord/{id}", 1)
                        .content(medicalRecordToPut)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("firstname", is("Pierre")));
    }

    @Test
    public void testDeleteOneMedicalRecords() throws Exception {
        mockMvc.perform(delete("/medicalRecord/{id}", 1))
                .andExpect(status().isOk());
    }

}