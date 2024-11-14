package com.SafetyNet.SafetyNetAlerts.Controller;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.SafetyNet.SafetyNetAlerts.Model.Persons;
import com.SafetyNet.SafetyNetAlerts.Repository.PersonsRepository;
import com.SafetyNet.SafetyNetAlerts.Service.PersonService;
import jakarta.annotation.security.RunAs;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.StreamingHttpOutputMessage;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


@SpringBootTest
@AutoConfigureMockMvc
public class PersonsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PersonService service;

    @Test
    public void testGetPersons() throws Exception {
        mockMvc.perform(get("/person"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetOnePerson() throws Exception {
        mockMvc.perform(get("/person/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("firstname", is("John")));
    }

    @Test
    public void testAddOnePerson() throws Exception {
        String personToPost = "{ \"firstname\":\"Paul\", \"lastname\":\"Boyd\", \"address\":\"1509 Culver St\", \"city\":\"Culver\", \"zip\":\"97451\", \"phone\":\"841-874-6512\", \"email\":\"jaboyd@email.com\" }";
        mockMvc.perform(post("/person")
                        .content(personToPost)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("firstname", is("Paul")));
    }

    @Test
    public void testPutOnePerson() throws Exception {
        String personToPut = "{ \"firstname\":\"Paul\", \"lastname\":\"Boyd\", \"address\":\"1509 Culver St\", \"city\":\"Culver\", \"zip\":\"97451\", \"phone\":\"841-874-6512\", \"email\":\"jaboyd@email.com\" }";
        mockMvc.perform(put("/person/{id}", 1)
                        .content(personToPut)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("firstname", is("Paul")));
    }

    @Test
    public void testDeleteOnePerson() throws Exception {
        mockMvc.perform(delete("/person/{id}", 1))
                .andExpect(status().isOk());
    }

}

