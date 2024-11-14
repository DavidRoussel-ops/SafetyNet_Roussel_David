package com.SafetyNet.SafetyNetAlerts.Controller;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.SafetyNet.SafetyNetAlerts.Model.Firestations;
import com.SafetyNet.SafetyNetAlerts.Model.Persons;
import com.SafetyNet.SafetyNetAlerts.Service.FirestationsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
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
    public void testGetOneFirestations() throws Exception {
        mockMvc.perform(get("/firestation/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("address", is("1509 Culver St")));
    }

    @Test
    public void testAddOneFirestations() throws Exception {
        String firestationsToPost = "{ \"address\":\"12 rue de Mont Léon\", \"station\":\"3\" }";
        mockMvc.perform(post("/firestation")
                        .content(firestationsToPost)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("address", is("12 rue de Mont Léon")));
    }

    @Test
    public void testPutOneFirestations() throws Exception {
        String firestationsToPut = "{ \"address\":\"12 rue de Mont Pierre\", \"station\":\"3\" }";
        mockMvc.perform(put("/firestation/{id}", 1)
                        .content(firestationsToPut)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("address", is("12 rue de Mont Pierre")));
    }

    @Test
    public void testDeleteOneFirestations() throws Exception {
        mockMvc.perform(delete("/firestation/{id}", 1))
                .andExpect(status().isOk());
    }

}
