package com.SafetyNet.SafetyNetAlerts.Controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.SafetyNet.SafetyNetAlerts.Model.Firestations;
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
public class FirestationsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BusinessService businessService;

    @MockBean
    private MedicalRecordsService medicalRecordsService;

    @MockBean
    private FirestationsService service;

    @MockBean
    private PersonService personService;

    @Test
    public void testGetFirestations() throws Exception {
        Firestations firestations1 = new Firestations();
        firestations1.setId(1L);
        firestations1.setStation("2");
        firestations1.setAddress("1504 Wall Street");
        Firestations firestations2 = new Firestations();
        firestations2.setId(2L);
        firestations2.setStation("1");
        firestations2.setAddress("1504 Wall Street");
        Iterable<Firestations> firestations = new ArrayList<>(
                Arrays.asList(firestations1, firestations2)
        );
        when(service.getFirestations()).thenReturn(firestations);
        mockMvc.perform(get("/firestation"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void testGetOneFirestations() throws Exception {
        Long id = 1L;
        Firestations firestations = new Firestations();
        firestations.setId(id);
        firestations.setStation("2");
        firestations.setAddress("1504 Wall Street");
        when(service.getFirestation(id)).thenReturn(Optional.of(firestations));
        mockMvc.perform(get("/firestation/{id}", id))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void testGetOneFirestationsNotFound() throws Exception {
        Long id = 1L;
        when(service.getFirestation(id)).thenReturn(Optional.empty());
        mockMvc.perform(get("/firestation/{id}", id))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    public void testAddOneFirestations() throws Exception {
        Firestations firestations = new Firestations();
        firestations.setId(null);
        firestations.setAddress("12 rue de l'aéroport");
        firestations.setStation("1");
        mockMvc.perform(post("/firestation", firestations)
                        .content(asJsonString(firestations))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andDo(print());
    }

    @Test
    public void testPutOneFirestations() throws Exception {
        Long id = 1L;
        Firestations firestationsInitial = new Firestations();
        firestationsInitial.setId(id);
        firestationsInitial.setAddress("12 rue de l'aéroport");
        firestationsInitial.setStation("1");
        Firestations firestationsUpdated = new Firestations();
        firestationsUpdated.setId(id);
        firestationsUpdated.setAddress("12 rue de l'aéroport");
        firestationsUpdated.setStation("2");
        when(service.getFirestation(id)).thenReturn(Optional.of(firestationsInitial));
        when(service.saveFirestation(any(Firestations.class))).thenReturn(firestationsUpdated);
        mockMvc.perform(put("/firestation/{id}", id)
                        .content(asJsonString(firestationsUpdated))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void testPutOneFirestationsNotFound() throws Exception {
        Long id = 1L;
        Firestations firestationsUpdated = new Firestations();
        firestationsUpdated.setId(id);
        firestationsUpdated.setAddress("12 rue de l'aéroport");
        firestationsUpdated.setStation("2");
        when(service.getFirestation(id)).thenReturn(Optional.empty());
        when(service.saveFirestation(any(Firestations.class))).thenReturn(firestationsUpdated);
        mockMvc.perform(put("/firestation/{id}", id)
                        .content(asJsonString(firestationsUpdated))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    public void testDeleteOneFirestations() throws Exception {
        Long id = 1L;
        mockMvc.perform(delete("/firestation/{id}", id))
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
