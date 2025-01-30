package com.SafetyNet.SafetyNetAlerts.Controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import com.SafetyNet.SafetyNetAlerts.Model.Persons;
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
public class PersonsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MedicalRecordsService medicalRecordsService;

    @MockBean
    private FirestationsService firestationsService;

    @MockBean
    private PersonService service;

    @Test
    public void testGetPersons() throws Exception {
        Persons persons1 = new Persons();
        persons1.setId(1L);
        persons1.setFirstName("Paul");
        persons1.setLastName("Boyd");
        persons1.setAddress("1509 Culver St");
        persons1.setCity("Culver");
        persons1.setZip("97451");
        persons1.setPhone("841-874-6512");
        persons1.setEmail("jaboy@email.com");
        Persons persons2 = new Persons();
        persons2.setId(2L);
        persons2.setFirstName("Paul");
        persons2.setLastName("Boyd");
        persons2.setAddress("1509 Culver St");
        persons2.setCity("Culver");
        persons2.setZip("97451");
        persons2.setPhone("841-874-6512");
        persons2.setEmail("jaboy@email.com");
        ArrayList<Persons> persons = new ArrayList<>(
                Arrays.asList(persons1, persons2)
        );
        when(service.getPersons()).thenReturn(persons);
        mockMvc.perform(get("/person"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void testGetOnePerson() throws Exception {
        Long id = 1L;
        Persons persons = new Persons();
        persons.setId(id);
        persons.setFirstName("Paul");
        persons.setLastName("Boyd");
        persons.setAddress("1509 Culver St");
        persons.setCity("Culver");
        persons.setZip("97451");
        persons.setPhone("841-874-6512");
        persons.setEmail("jaboy@email.com");
        when(service.getPerson(id)).thenReturn(persons);
        mockMvc.perform(get("/person/{id}", id))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void testGetOnePersonBadRequest() throws Exception {
        Long id = 55L;
        mockMvc.perform(get("/person/{id}", id))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    public void testGetOnePersonNotFound() throws Exception {
        Long id = null;
        mockMvc.perform(get("/person/{id}", id))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    public void testAddOnePerson() throws Exception {
        Persons persons = new Persons();
        persons.setId(null);
        persons.setFirstName("Paul");
        persons.setLastName("Boyd");
        persons.setAddress("1509 Culver St");
        persons.setCity("Culver");
        persons.setZip("97451");
        persons.setPhone("841-874-6512");
        persons.setEmail("jaboy@email.com");
        mockMvc.perform(post("/person", persons)
                        .content(asJsonString(persons))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andDo(print());
    }

    @Test
    public void testPutOnePerson() throws Exception {
        Long id = 1L;
        Persons personsInitial = new Persons();
        personsInitial.setId(id);
        personsInitial.setFirstName("Paul");
        personsInitial.setLastName("Boyd");
        personsInitial.setAddress("1509 Culver St");
        personsInitial.setCity("Culver");
        personsInitial.setZip("97451");
        personsInitial.setPhone("841-874-6512");
        personsInitial.setEmail("jaboy@email.com");
        Persons personsUpdated = new Persons();
        personsUpdated.setId(id);
        personsUpdated.setFirstName(personsInitial.getFirstName());
        personsUpdated.setLastName(personsInitial.getLastName());
        personsUpdated.setAddress("1510 Culver St");
        personsUpdated.setCity("Culver");
        personsUpdated.setZip("97451");
        personsUpdated.setPhone("841-874-6512");
        personsUpdated.setEmail("jaboy@email.com");
        when(service.getPerson(id)).thenReturn(personsInitial);
        mockMvc.perform(put("/person/{id}", id)
                        .content(asJsonString(personsUpdated))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void testPutOnePersonBadRequest() throws Exception {
        Long id = 25L;
        Persons persons = new Persons();
        when(service.getPerson(id)).thenReturn(persons);
        mockMvc.perform(put("/person/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    public void testDeleteOnePerson() throws Exception {
        Long id = 1L;
        mockMvc.perform(delete("/person/{id}", id))
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

