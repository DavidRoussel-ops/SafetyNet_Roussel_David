package com.SafetyNet.SafetyNetAlerts.Controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import com.SafetyNet.SafetyNetAlerts.Model.MedicalRecords;
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

@WebMvcTest
public class MedicalRecordsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MedicalRecordsService service;

    @MockBean
    private FirestationsService firestationsService;

    @MockBean
    private PersonService personService;

    @Test
    public void testGetMedicalRecords() throws Exception {
        String[] arrayMedications = new String[]{"Dolliprane : 500mg"};
        String[] arrayAllergies = new String[]{"aspirine"};
        MedicalRecords medicalRecords1 = new MedicalRecords();
        medicalRecords1.setId(1L);
        medicalRecords1.setFirstName("Pierre");
        medicalRecords1.setLastName("Dupont");
        medicalRecords1.setBirthdate("01/01/1981");
        medicalRecords1.setMedications(arrayMedications);
        medicalRecords1.setAllergies(arrayAllergies);
        MedicalRecords medicalRecords2 = new MedicalRecords();
        medicalRecords2.setId(2L);
        medicalRecords2.setFirstName("Paul");
        medicalRecords2.setLastName("Dupont");
        medicalRecords2.setBirthdate("01/01/1981");
        medicalRecords2.setMedications(arrayMedications);
        medicalRecords2.setAllergies(arrayAllergies);
        MedicalRecords medicalRecords3 = new MedicalRecords();
        medicalRecords3.setId(3L);
        medicalRecords3.setFirstName("Jacques");
        medicalRecords3.setLastName("Dupont");
        medicalRecords3.setBirthdate("01/01/1981");
        medicalRecords3.setMedications(arrayMedications);
        medicalRecords3.setAllergies(arrayAllergies);
        ArrayList<MedicalRecords> medicalRecords = new ArrayList<>(
                Arrays.asList(medicalRecords1, medicalRecords2, medicalRecords3)
        );
        when(service.getMedicalRecords()).thenReturn(medicalRecords);
        mockMvc.perform(get("/medicalRecord"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void testGetOneMedicalRecords() throws Exception {
        String[] arrayMedications = new String[]{"Dolliprane : 500mg"};
        String[] arrayAllergies = new String[]{"aspirine"};
        Long id = 1L;
        MedicalRecords medicalRecords = new MedicalRecords();
        medicalRecords.setId(id);
        medicalRecords.setFirstName("Pierre");
        medicalRecords.setLastName("Dupont");
        medicalRecords.setBirthdate("01/01/1981");
        medicalRecords.setMedications(arrayMedications);
        medicalRecords.setAllergies(arrayAllergies);
        when(service.getMedicalRecord(id)).thenReturn(medicalRecords);
        mockMvc.perform(get("/medicalRecord/{id}", id))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void testGetOneMedicalRecordsBadRequest() throws Exception {
        Long id = 52L;
        mockMvc.perform(get("/medicalRecord/{id}", id))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    public void testGetOneMedicalRecordsNotFound() throws Exception {
        Long id = null;
        mockMvc.perform(get("/medicalRecord/{id}", id))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    public void testAddOneMedicalRecords() throws Exception {
        String[] arrayMedications = new String[]{"Dolliprane : 500mg"};
        String[] arrayAllergies = new String[]{"aspirine"};
        MedicalRecords medicalRecords = new MedicalRecords();
        medicalRecords.setId(null);
        medicalRecords.setFirstName("Pierre");
        medicalRecords.setLastName("Dupont");
        medicalRecords.setBirthdate("01/01/1981");
        medicalRecords.setMedications(arrayMedications);
        medicalRecords.setAllergies(arrayAllergies);
        mockMvc.perform(post("/medicalRecord", medicalRecords)
                        .content(asJsonString(medicalRecords))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andDo(print());
    }

    @Test
    public void testPutOneMedicalRecords() throws Exception {
        String[] arrayMedications = new String[]{"Dolliprane : 500mg"};
        String[] newArrayMedications = new String[]{"Dolliprane : 1000mg"};
        String[] arrayAllergies = new String[]{"aspirine"};
        Long id = 1L;
        MedicalRecords medicalRecordsInitial = new MedicalRecords();
        medicalRecordsInitial.setId(id);
        medicalRecordsInitial.setFirstName("Pierre");
        medicalRecordsInitial.setLastName("Dupont");
        medicalRecordsInitial.setBirthdate("01/01/1981");
        medicalRecordsInitial.setMedications(arrayMedications);
        medicalRecordsInitial.setAllergies(arrayAllergies);
        MedicalRecords medicalRecordsUpdated = new MedicalRecords();
        medicalRecordsUpdated.setId(id);
        medicalRecordsUpdated.setFirstName("Pierre");
        medicalRecordsUpdated.setLastName("Dupont");
        medicalRecordsUpdated.setBirthdate("01/01/1981");
        medicalRecordsUpdated.setMedications(newArrayMedications);
        medicalRecordsUpdated.setAllergies(arrayAllergies);
        when(service.getMedicalRecord(id)).thenReturn(medicalRecordsInitial);
        mockMvc.perform(put("/medicalRecord/{id}", id)
                        .content(asJsonString(medicalRecordsUpdated))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void testPutOneMedicalRecordsBadRequest() throws Exception {
        Long id = 25L;
        MedicalRecords medicalRecords = new MedicalRecords();
        when(service.getMedicalRecord(id)).thenReturn(medicalRecords);
        mockMvc.perform(put("/medicalRecord/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
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