package com.SafetyNet.SafetyNetAlerts.Controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.SafetyNet.SafetyNetAlerts.Model.MedicalRecords;
import com.SafetyNet.SafetyNetAlerts.Service.BusinessService;
import com.SafetyNet.SafetyNetAlerts.Service.FirestationsService;
import com.SafetyNet.SafetyNetAlerts.Service.MedicalRecordsService;
import com.SafetyNet.SafetyNetAlerts.Service.PersonService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

@WebMvcTest
public class MedicalRecordsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BusinessService businessService;

    @MockBean
    private MedicalRecordsService service;

    @MockBean
    private FirestationsService firestationsService;

    @MockBean
    private PersonService personService;

    @Test
    public void testGetMedicalRecords() throws Exception {
        MedicalRecords medicalRecords1 = new MedicalRecords();
        medicalRecords1.setId(1L);
        medicalRecords1.setFirstname("Pierre");
        medicalRecords1.setLastname("Dupont");
        medicalRecords1.setBirthdate("01/01/1981");
        medicalRecords1.setMedications("Dolliprane : 500mg");
        medicalRecords1.setAllergies("aspirine");
        MedicalRecords medicalRecords2 = new MedicalRecords();
        medicalRecords2.setId(2L);
        medicalRecords2.setFirstname("Paul");
        medicalRecords2.setLastname("Dupont");
        medicalRecords2.setBirthdate("01/01/1981");
        medicalRecords2.setMedications("Dolliprane : 500mg");
        medicalRecords2.setAllergies("aspirine");
        MedicalRecords medicalRecords3 = new MedicalRecords();
        medicalRecords3.setId(3L);
        medicalRecords3.setFirstname("Jacques");
        medicalRecords3.setLastname("Dupont");
        medicalRecords3.setBirthdate("01/01/1981");
        medicalRecords3.setMedications("Dolliprane : 500mg");
        medicalRecords3.setAllergies("aspirine");
        Iterable<MedicalRecords> medicalRecords = new ArrayList<>(
                Arrays.asList(medicalRecords1, medicalRecords2, medicalRecords3)
        );
        when(service.getMedicalRecords()).thenReturn(medicalRecords);
        mockMvc.perform(get("/medicalRecord"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void testGetOneMedicalRecords() throws Exception {
        Long id = 1L;
        MedicalRecords medicalRecords = new MedicalRecords();
        medicalRecords.setId(id);
        medicalRecords.setFirstname("Pierre");
        medicalRecords.setLastname("Dupont");
        medicalRecords.setBirthdate("01/01/1981");
        medicalRecords.setMedications("Dolliprane : 500mg");
        medicalRecords.setAllergies("aspirine");
        when(service.getMedicalRecord(id)).thenReturn(Optional.of(medicalRecords));
        mockMvc.perform(get("/medicalRecord/{id}", id))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void testGetOneMedicalRecordsNotFound() throws Exception {
        Long id = 1L;
        when(service.getMedicalRecord(id)).thenReturn(Optional.empty());
        mockMvc.perform(get("/medicalRecord/{id}", id))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    public void testAddOneMedicalRecords() throws Exception {
        MedicalRecords medicalRecords = new MedicalRecords();
        medicalRecords.setId(null);
        medicalRecords.setFirstname("Pierre");
        medicalRecords.setLastname("Dupont");
        medicalRecords.setBirthdate("01/01/1981");
        medicalRecords.setMedications("Dolliprane : 500mg");
        medicalRecords.setAllergies("aspirine");
        mockMvc.perform(post("/medicalRecord", medicalRecords)
                        .content(asJsonString(medicalRecords))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andDo(print());
    }

    @Test
    public void testPutOneMedicalRecords() throws Exception {
        Long id = 1L;
        MedicalRecords medicalRecordsInitial = new MedicalRecords();
        medicalRecordsInitial.setId(id);
        medicalRecordsInitial.setFirstname("Pierre");
        medicalRecordsInitial.setLastname("Dupont");
        medicalRecordsInitial.setBirthdate("01/01/1981");
        medicalRecordsInitial.setMedications("Dolliprane : 500mg");
        medicalRecordsInitial.setAllergies("aspirine");
        MedicalRecords medicalRecordsUpdated = new MedicalRecords();
        medicalRecordsUpdated.setId(id);
        medicalRecordsUpdated.setFirstname("Pierre");
        medicalRecordsUpdated.setLastname("Dupont");
        medicalRecordsUpdated.setBirthdate("01/01/1981");
        medicalRecordsUpdated.setMedications("Dolliprane : 1000mg");
        medicalRecordsUpdated.setAllergies("aspirine");
        when(service.getMedicalRecord(id)).thenReturn(Optional.of(medicalRecordsInitial));
        when(service.saveMedicalRecord(any(MedicalRecords.class))).thenReturn(medicalRecordsUpdated);
        mockMvc.perform(put("/medicalRecord/{id}", id)
                        .content(asJsonString(medicalRecordsUpdated))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.medications").value(medicalRecordsUpdated.getMedications()))
                .andDo(print());
    }

    @Test
    public void testPutOneMedicalRecordsNotFound() throws Exception {
        Long id = 1L;
        MedicalRecords medicalRecordsUpdated = new MedicalRecords();
        medicalRecordsUpdated.setId(id);
        medicalRecordsUpdated.setFirstname("Pierre");
        medicalRecordsUpdated.setLastname("Dupont");
        medicalRecordsUpdated.setBirthdate("01/01/1981");
        medicalRecordsUpdated.setMedications("Dolliprane : 1000mg");
        medicalRecordsUpdated.setAllergies("aspirine");
        when(service.getMedicalRecord(id)).thenReturn(Optional.empty());
        when(service.saveMedicalRecord(any(MedicalRecords.class))).thenReturn(medicalRecordsUpdated);
        mockMvc.perform(put("/medicalRecord/{id}", id)
                        .content(asJsonString(medicalRecordsUpdated))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    public void testDeleteOneMedicalRecords() throws Exception {
        Long id = 1L;
        mockMvc.perform(delete("/medicalRecord/{id}", id))
                .andExpect(status().isNoContent())
                .andDo(print());
    }

    public static String asJsonString(final Object object) {
        try {
            return new ObjectMapper().writeValueAsString(object);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}