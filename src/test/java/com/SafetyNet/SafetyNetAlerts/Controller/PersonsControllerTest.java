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
    public void testGetMail() throws Exception {
        String city = "Culver";
        Persons persons1 = new Persons();
        persons1.setId(1L);
        persons1.setFirstname("Paul");
        persons1.setLastname("Boyd");
        persons1.setAddress("1509 Culver St");
        persons1.setCity("Culver");
        persons1.setZip("97451");
        persons1.setPhone("841-874-6512");
        persons1.setEmail("pierre@email.com");
        Persons persons2 = new Persons();
        persons2.setId(2L);
        persons2.setFirstname("Paul");
        persons2.setLastname("Boyd");
        persons2.setAddress("1509 Culver St");
        persons2.setCity("Culver");
        persons2.setZip("97451");
        persons2.setPhone("841-874-6512");
        persons2.setEmail("paul@email.com");
        Persons persons3 = new Persons();
        persons3.setId(3L);
        persons3.setFirstname("Michel");
        persons3.setLastname("Boyd");
        persons3.setAddress("1509 Culver St");
        persons3.setCity("Culver");
        persons3.setZip("97451");
        persons3.setPhone("841-874-6512");
        persons3.setEmail("michel@email.com");
        Iterable<Persons> persons = new ArrayList<>(
                Arrays.asList(persons1, persons2, persons3)
        );
        when(service.getPersons()).thenReturn(persons);
        mockMvc.perform(get("/communityEmail?city={city}", city))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void testGetMailNNotFound() throws Exception {
        String city = "Paris";
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
        persons2.setEmail("paul@email.com");
        Iterable<Persons> persons = new ArrayList<>(
                Arrays.asList(persons1, persons2)
        );
        when(service.getPersons()).thenReturn(persons);
        mockMvc.perform(get("/communityEmail?city={city}", city))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    public void getHomeByStation() throws Exception {
        String stations = "3";
        Firestations firestations1 = new Firestations();
        firestations1.setId(1L);
        firestations1.setAddress("15 rue de la gloire");
        firestations1.setStation("2");
        Firestations firestations2 = new Firestations();
        firestations2.setId(2L);
        firestations2.setAddress("10 rue de la gloire");
        firestations2.setStation("1");
        Firestations firestations3 = new Firestations();
        firestations3.setId(3L);
        firestations3.setAddress("12 rue de la gloire");
        firestations3.setStation("3");
        Persons persons1 = new Persons();
        persons1.setId(1L);
        persons1.setFirstname("Michel");
        persons1.setLastname("Boyd");
        persons1.setAddress("15 rue de la gloire");
        persons1.setCity("Culver");
        persons1.setZip("97451");
        persons1.setPhone("841-874-6512");
        persons1.setEmail("michel@email.com");
        Persons persons2 = new Persons();
        persons2.setId(2L);
        persons2.setFirstname("Paul");
        persons2.setLastname("Boyd");
        persons2.setAddress("10 rue de la gloire");
        persons2.setCity("Culver");
        persons2.setZip("97465");
        persons2.setPhone("841-874-3254");
        persons2.setEmail("paul@email.com");
        Persons persons3 = new Persons();
        persons3.setId(3L);
        persons3.setFirstname("Jacques");
        persons3.setLastname("Boyd");
        persons3.setAddress("10 rue de la gloire");
        persons3.setCity("Culver");
        persons3.setZip("97451");
        persons3.setPhone("841-874-4596");
        persons3.setEmail("jacques@email.com");
        MedicalRecords medicalRecords1 = new MedicalRecords();
        medicalRecords1.setId(1L);
        medicalRecords1.setFirstname("Michel");
        medicalRecords1.setLastname("Boyd");
        medicalRecords1.setBirthdate("01/01/1965");
        medicalRecords1.setMedications("Paracetamol : 1000mg");
        medicalRecords1.setAllergies("Aspirine");
        MedicalRecords medicalRecords2 = new MedicalRecords();
        medicalRecords2.setId(2L);
        medicalRecords2.setFirstname("Paul");
        medicalRecords2.setLastname("Boyd");
        medicalRecords2.setBirthdate("01/01/1985");
        medicalRecords2.setMedications("Paracetamol : 500mg");
        medicalRecords2.setAllergies("Codéine");
        MedicalRecords medicalRecords3 = new MedicalRecords();
        medicalRecords3.setId(3L);
        medicalRecords3.setFirstname("Jacques");
        medicalRecords3.setLastname("Boyd");
        medicalRecords3.setBirthdate("01/01/1999");
        medicalRecords3.setMedications("Codéine : 1500mg");
        medicalRecords3.setAllergies("Paracetamol");
        Iterable<Persons> persons = new ArrayList<>(
                Arrays.asList(persons1, persons2, persons3)
        );
        Iterable<MedicalRecords> medicalRecords = new ArrayList<>(
                Arrays.asList(medicalRecords1, medicalRecords2, medicalRecords3)
        );
        Iterable<Firestations> firestations = new ArrayList<>(
                Arrays.asList(firestations1, firestations2, firestations3)
        );
        when(service.getPersons()).thenReturn(persons);
        when(medicalRecordsService.getMedicalRecords()).thenReturn(medicalRecords);
        when(firestationsService.getFirestations()).thenReturn(firestations);
        mockMvc.perform(get("/flood/stations?stations={stations}", stations))
                .andExpect(status().isOk())
                .andDo(print());
    }


    @Test
    public void testGetPersonsByLastnameFindOne() throws Exception {
        String lastname = "Boyd";
        Persons persons1 = new Persons();
        persons1.setId(1L);
        persons1.setFirstname("Michel");
        persons1.setLastname(lastname);
        persons1.setAddress("1509 Culver St");
        persons1.setCity("Culver");
        persons1.setZip("97451");
        persons1.setPhone("841-874-6512");
        persons1.setEmail("michel@email.com");
        MedicalRecords medicalRecords1 = new MedicalRecords();
        medicalRecords1.setId(1L);
        medicalRecords1.setFirstname("Michel");
        medicalRecords1.setLastname(lastname);
        medicalRecords1.setBirthdate("01/01/1965");
        medicalRecords1.setMedications("Paracetamol : 1000mg");
        medicalRecords1.setAllergies("Aspirine");
        Iterable<Persons> persons = new ArrayList<>(
                Arrays.asList(persons1)
        );
        Iterable<MedicalRecords> medicalRecords = new ArrayList<>(
                Arrays.asList(medicalRecords1)
        );
        when(service.getPersons()).thenReturn(persons);
        when(medicalRecordsService.getMedicalRecords()).thenReturn(medicalRecords);
        mockMvc.perform(get("/personInfolastName={lastName}", lastname))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void testGetPersonsByLastnameFindAll() throws Exception {
        String lastname = "Boyd";
        Persons persons1 = new Persons();
        persons1.setId(1L);
        persons1.setFirstname("Michel");
        persons1.setLastname(lastname);
        persons1.setAddress("1509 Culver St");
        persons1.setCity("Culver");
        persons1.setZip("97451");
        persons1.setPhone("841-874-6512");
        persons1.setEmail("michel@email.com");
        Persons persons2 = new Persons();
        persons2.setId(2L);
        persons2.setFirstname("Paul");
        persons2.setLastname(lastname);
        persons2.setAddress("10 rue de la gloire");
        persons2.setCity("Culver");
        persons2.setZip("97465");
        persons2.setPhone("841-874-3254");
        persons2.setEmail("paul@email.com");
        Persons persons3 = new Persons();
        persons3.setId(3L);
        persons3.setFirstname("Jacques");
        persons3.setLastname(lastname);
        persons3.setAddress("15 rue de Mont Miraille");
        persons3.setCity("Culver");
        persons3.setZip("97451");
        persons3.setPhone("841-874-6512");
        persons3.setEmail("jacques@email.com");
        MedicalRecords medicalRecords1 = new MedicalRecords();
        medicalRecords1.setId(1L);
        medicalRecords1.setFirstname("Michel");
        medicalRecords1.setLastname(lastname);
        medicalRecords1.setBirthdate("01/01/1965");
        medicalRecords1.setMedications("Paracetamol : 1000mg");
        medicalRecords1.setAllergies("Aspirine");
        MedicalRecords medicalRecords2 = new MedicalRecords();
        medicalRecords2.setId(2L);
        medicalRecords2.setFirstname("Paul");
        medicalRecords2.setLastname(lastname);
        medicalRecords2.setBirthdate("01/01/1985");
        medicalRecords2.setMedications("Paracetamol : 500mg");
        medicalRecords2.setAllergies("Codéine");
        MedicalRecords medicalRecords3 = new MedicalRecords();
        medicalRecords3.setId(2L);
        medicalRecords3.setFirstname("Jacques");
        medicalRecords3.setLastname(lastname);
        medicalRecords3.setBirthdate("01/01/1999");
        medicalRecords3.setMedications("Codéine : 1500mg");
        medicalRecords3.setAllergies("Paracetamol");
        Iterable<Persons> persons = new ArrayList<>(
                Arrays.asList(persons1, persons2, persons3)
        );
        Iterable<MedicalRecords> medicalRecords = new ArrayList<>(
                Arrays.asList(medicalRecords1, medicalRecords2, medicalRecords3)
        );
        when(service.getPersons()).thenReturn(persons);
        when(medicalRecordsService.getMedicalRecords()).thenReturn(medicalRecords);
        mockMvc.perform(get("/personInfolastName={lastName}", lastname))
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

