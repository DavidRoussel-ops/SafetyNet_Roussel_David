package com.SafetyNet.SafetyNetAlerts.Controller;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.SafetyNet.SafetyNetAlerts.Model.Firestations;
import com.SafetyNet.SafetyNetAlerts.Model.MedicalRecords;
import com.SafetyNet.SafetyNetAlerts.Model.Persons;
import com.SafetyNet.SafetyNetAlerts.Repository.FirestationsRepository;
import com.SafetyNet.SafetyNetAlerts.Repository.PersonsRepository;
import com.SafetyNet.SafetyNetAlerts.Service.BusinessService;
import com.SafetyNet.SafetyNetAlerts.Service.FirestationsService;
import com.SafetyNet.SafetyNetAlerts.Service.MedicalRecordsService;
import com.SafetyNet.SafetyNetAlerts.Service.PersonService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.security.RunAs;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.StreamingHttpOutputMessage;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


@WebMvcTest
public class PersonsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BusinessService businessService;

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
        persons1.setFirstname("Paul");
        persons1.setLastname("Boyd");
        persons1.setAddress("1509 Culver St");
        persons1.setCity("Culver");
        persons1.setZip("97451");
        persons1.setPhone("841-874-6512");
        persons1.setEmail("jaboy@email.com");
        Persons persons2 = new Persons();
        persons2.setId(2L);
        persons2.setFirstname("Paul");
        persons2.setLastname("Boyd");
        persons2.setAddress("1509 Culver St");
        persons2.setCity("Culver");
        persons2.setZip("97451");
        persons2.setPhone("841-874-6512");
        persons2.setEmail("jaboy@email.com");
        Iterable<Persons> persons = new ArrayList<>(
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
        persons.setFirstname("Paul");
        persons.setLastname("Boyd");
        persons.setAddress("1509 Culver St");
        persons.setCity("Culver");
        persons.setZip("97451");
        persons.setPhone("841-874-6512");
        persons.setEmail("jaboy@email.com");
        when(service.getPerson(id)).thenReturn(Optional.of(persons));
        mockMvc.perform(get("/person/{id}", id))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void testGetOnePersonNotFound() throws Exception {
        Long id = 1L;
        when(service.getPerson(id)).thenReturn(Optional.empty());
        mockMvc.perform(get("/person/{id}", id))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    public void testAddOnePerson() throws Exception {
        Persons persons = new Persons();
        persons.setId(null);
        persons.setFirstname("Paul");
        persons.setLastname("Boyd");
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
        personsInitial.setFirstname("Paul");
        personsInitial.setLastname("Boyd");
        personsInitial.setAddress("1509 Culver St");
        personsInitial.setCity("Culver");
        personsInitial.setZip("97451");
        personsInitial.setPhone("841-874-6512");
        personsInitial.setEmail("jaboy@email.com");
        Persons personsUpdated = new Persons();
        personsUpdated.setId(id);
        personsUpdated.setFirstname("Pierre");
        personsUpdated.setLastname("Boyd");
        personsUpdated.setAddress("1509 Culver St");
        personsUpdated.setCity("Culver");
        personsUpdated.setZip("97451");
        personsUpdated.setPhone("841-874-6512");
        personsUpdated.setEmail("jaboy@email.com");
        when(service.getPerson(id)).thenReturn(Optional.of(personsInitial));
        when(service.savePerson(any(Persons.class))).thenReturn(personsUpdated);
        mockMvc.perform(put("/person/{id}", id)
                        .content(asJsonString(personsUpdated))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstname").value(personsUpdated.getFirstname()))
                .andDo(print());
    }

    @Test
    public void testPutOnePersonNotFound() throws Exception {
        Long id = 1L;
        Persons personsUpdated = new Persons();
        personsUpdated.setId(id);
        personsUpdated.setFirstname("Pierre");
        personsUpdated.setLastname("Boyd");
        personsUpdated.setAddress("1509 Culver St");
        personsUpdated.setCity("Culver");
        personsUpdated.setZip("97451");
        personsUpdated.setPhone("841-874-6512");
        personsUpdated.setEmail("jaboy@email.com");
        when(service.getPerson(id)).thenReturn(Optional.empty());
        when(service.savePerson(any(Persons.class))).thenReturn(personsUpdated);
        mockMvc.perform(put("/person/{id}", id)
                        .content(asJsonString(personsUpdated))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
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

