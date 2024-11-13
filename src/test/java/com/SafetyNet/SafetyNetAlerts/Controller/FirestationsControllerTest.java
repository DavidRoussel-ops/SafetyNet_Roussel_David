package com.SafetyNet.SafetyNetAlerts.Controller;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.SafetyNet.SafetyNetAlerts.Model.Firestations;
import com.SafetyNet.SafetyNetAlerts.Model.Persons;
import com.SafetyNet.SafetyNetAlerts.Service.FirestationsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;


@SpringBootTest
@AutoConfigureMockMvc
public class FirestationsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private FirestationsService service;

    @Test
    public void testGetFirestations() throws Exception {
        mockMvc.perform(get("/firestation"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetOnePerson() throws Exception {
        mockMvc.perform(get("/firestation/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("station", is("3")));
    }

    @Test
    public void testAddOnePerson() throws Exception {
        Firestations firestations = new Firestations();
        firestations.setStation("2");
        firestations.setAddress("1254 street of street");
        firestations.setId(14L);
        service.saveFirestation(firestations);
        mockMvc.perform(get("/firestation/14"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("address", is("1254 street of street")));
    }

}
