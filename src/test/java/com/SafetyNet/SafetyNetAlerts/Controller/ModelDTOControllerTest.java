package com.SafetyNet.SafetyNetAlerts.Controller;

import com.SafetyNet.SafetyNetAlerts.Model.Firestations;
import com.SafetyNet.SafetyNetAlerts.Model.MedicalRecords;
import com.SafetyNet.SafetyNetAlerts.Model.Persons;
import com.SafetyNet.SafetyNetAlerts.Service.BusinessService;
import com.SafetyNet.SafetyNetAlerts.Service.FirestationsService;
import com.SafetyNet.SafetyNetAlerts.Service.MedicalRecordsService;
import com.SafetyNet.SafetyNetAlerts.Service.PersonService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class ModelDTOControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MedicalRecordsService medicalRecordsService;

    @MockBean
    private FirestationsService firestationsService;

    @MockBean
    private PersonService service;

    @Test
    public void testGetMail() throws Exception {
        String city = "Culver";
        Persons persons1 = new Persons();
        persons1.setId(1L);
        persons1.setFirstName("Paul");
        persons1.setLastName("Boyd");
        persons1.setAddress("1509 Culver St");
        persons1.setCity("Culver");
        persons1.setZip("97451");
        persons1.setPhone("841-874-6512");
        persons1.setEmail("pierre@email.com");
        Persons persons2 = new Persons();
        persons2.setId(2L);
        persons2.setFirstName("Paul");
        persons2.setLastName("Boyd");
        persons2.setAddress("1509 Culver St");
        persons2.setCity("Culver");
        persons2.setZip("97451");
        persons2.setPhone("841-874-6512");
        persons2.setEmail("paul@email.com");
        Persons persons3 = new Persons();
        persons3.setId(3L);
        persons3.setFirstName("Michel");
        persons3.setLastName("Boyd");
        persons3.setAddress("1509 Culver St");
        persons3.setCity("Culver");
        persons3.setZip("97451");
        persons3.setPhone("841-874-6512");
        persons3.setEmail("michel@email.com");
        ArrayList<Persons> persons = new ArrayList<>(
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
        persons2.setEmail("paul@email.com");
        ArrayList<Persons> persons = new ArrayList<>(
                Arrays.asList(persons1, persons2)
        );
        when(service.getPersons()).thenReturn(persons);
        mockMvc.perform(get("/communityEmail?city={city}", city))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    public void testGetPhoneAlerts() throws Exception {
        String firestation = "1";
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
        persons1.setFirstName("Michel");
        persons1.setLastName("Boyd");
        persons1.setAddress("15 rue de la gloire");
        persons1.setCity("Culver");
        persons1.setZip("97451");
        persons1.setPhone("841-874-6512");
        persons1.setEmail("michel@email.com");
        Persons persons2 = new Persons();
        persons2.setId(2L);
        persons2.setFirstName("Paul");
        persons2.setLastName("Boyd");
        persons2.setAddress("10 rue de la gloire");
        persons2.setCity("Culver");
        persons2.setZip("97465");
        persons2.setPhone("841-874-3254");
        persons2.setEmail("paul@email.com");
        Persons persons3 = new Persons();
        persons3.setId(3L);
        persons3.setFirstName("Jacques");
        persons3.setLastName("Boyd");
        persons3.setAddress("10 rue de la gloire");
        persons3.setCity("Culver");
        persons3.setZip("97451");
        persons3.setPhone("841-874-4596");
        persons3.setEmail("jacques@email.com");
        ArrayList<Persons> persons = new ArrayList<>(
                Arrays.asList(persons1, persons2, persons3)
        );
        ArrayList<Firestations> firestations = new ArrayList<>(
                Arrays.asList(firestations1, firestations2, firestations3)
        );
        when(service.getPersons()).thenReturn(persons);
        when(firestationsService.getFirestations()).thenReturn(firestations);
        mockMvc.perform(get("/phoneAlert?firestation={firestation_number}", firestation))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void testGetChildAlert() throws Exception {
        String[] arrayMedical1 = new String[]{"Paracetamol : 1000mg"};
        String[] arrayMedical2 = new String[]{"Paracetamol : 500mg"};
        String[] arrayMedical3 = new String[]{"Codéine : 1500mg"};
        String[] arrayAllergies1 = new String[]{"Aspirine"};
        String[] arrayAllergies2 = new String[]{"Codéine"};
        String[] arrayAllergies3 = new String[]{"Paracetamol"};
        String address = "10 rue de la gloire";
        Persons persons1 = new Persons();
        persons1.setId(1L);
        persons1.setFirstName("Michel");
        persons1.setLastName("Boyd");
        persons1.setAddress("15 rue de la gloire");
        persons1.setCity("Culver");
        persons1.setZip("97451");
        persons1.setPhone("841-874-6512");
        persons1.setEmail("michel@email.com");
        Persons persons2 = new Persons();
        persons2.setId(2L);
        persons2.setFirstName("Paul");
        persons2.setLastName("Boyd");
        persons2.setAddress("10 rue de la gloire");
        persons2.setCity("Culver");
        persons2.setZip("97465");
        persons2.setPhone("841-874-3254");
        persons2.setEmail("paul@email.com");
        Persons persons3 = new Persons();
        persons3.setId(3L);
        persons3.setFirstName("Jacques");
        persons3.setLastName("Boyd");
        persons3.setAddress("10 rue de la gloire");
        persons3.setCity("Culver");
        persons3.setZip("97451");
        persons3.setPhone("841-874-4596");
        persons3.setEmail("jacques@email.com");
        MedicalRecords medicalRecords1 = new MedicalRecords();
        medicalRecords1.setId(1L);
        medicalRecords1.setFirstName("Michel");
        medicalRecords1.setLastName("Boyd");
        medicalRecords1.setBirthdate("01/01/1965");
        medicalRecords1.setMedications(arrayMedical1);
        medicalRecords1.setAllergies(arrayAllergies1);
        MedicalRecords medicalRecords2 = new MedicalRecords();
        medicalRecords2.setId(2L);
        medicalRecords2.setFirstName("Paul");
        medicalRecords2.setLastName("Boyd");
        medicalRecords2.setBirthdate("01/01/1985");
        medicalRecords2.setMedications(arrayMedical2);
        medicalRecords2.setAllergies(arrayAllergies2);
        MedicalRecords medicalRecords3 = new MedicalRecords();
        medicalRecords3.setId(3L);
        medicalRecords3.setFirstName("Jacques");
        medicalRecords3.setLastName("Boyd");
        medicalRecords3.setBirthdate("01/01/2014");
        medicalRecords3.setMedications(arrayMedical3);
        medicalRecords3.setAllergies(arrayAllergies3);
        ArrayList<Persons> persons = new ArrayList<>(
                Arrays.asList(persons1, persons2, persons3)
        );
        ArrayList<MedicalRecords> medicalRecords = new ArrayList<>(
                Arrays.asList(medicalRecords1, medicalRecords2, medicalRecords3)
        );
        when(service.getPersons()).thenReturn(persons);
        when(medicalRecordsService.getMedicalRecords()).thenReturn(medicalRecords);
        mockMvc.perform(get("/childAlert?address={address}", address))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void testGetAddress() throws Exception {
        String[] arrayMedical1 = new String[]{"Paracetamol : 1000mg"};
        String[] arrayMedical2 = new String[]{"Paracetamol : 500mg"};
        String[] arrayMedical3 = new String[]{"Codéine : 1500mg"};
        String[] arrayAllergies1 = new String[]{"Aspirine"};
        String[] arrayAllergies2 = new String[]{"Codéine"};
        String[] arrayAllergies3 = new String[]{"Paracetamol"};
        String address = "15 rue de la gloire";
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
        persons1.setFirstName("Michel");
        persons1.setLastName("Boyd");
        persons1.setAddress("15 rue de la gloire");
        persons1.setCity("Culver");
        persons1.setZip("97451");
        persons1.setPhone("841-874-6512");
        persons1.setEmail("michel@email.com");
        Persons persons2 = new Persons();
        persons2.setId(2L);
        persons2.setFirstName("Paul");
        persons2.setLastName("Boyd");
        persons2.setAddress("10 rue de la gloire");
        persons2.setCity("Culver");
        persons2.setZip("97465");
        persons2.setPhone("841-874-3254");
        persons2.setEmail("paul@email.com");
        Persons persons3 = new Persons();
        persons3.setId(3L);
        persons3.setFirstName("Jacques");
        persons3.setLastName("Boyd");
        persons3.setAddress("10 rue de la gloire");
        persons3.setCity("Culver");
        persons3.setZip("97451");
        persons3.setPhone("841-874-4596");
        persons3.setEmail("jacques@email.com");
        MedicalRecords medicalRecords1 = new MedicalRecords();
        medicalRecords1.setId(1L);
        medicalRecords1.setFirstName("Michel");
        medicalRecords1.setLastName("Boyd");
        medicalRecords1.setBirthdate("01/01/1965");
        medicalRecords1.setMedications(arrayMedical1);
        medicalRecords1.setAllergies(arrayAllergies1);
        MedicalRecords medicalRecords2 = new MedicalRecords();
        medicalRecords2.setId(2L);
        medicalRecords2.setFirstName("Paul");
        medicalRecords2.setLastName("Boyd");
        medicalRecords2.setBirthdate("01/01/1985");
        medicalRecords2.setMedications(arrayMedical2);
        medicalRecords2.setAllergies(arrayAllergies2);
        MedicalRecords medicalRecords3 = new MedicalRecords();
        medicalRecords3.setId(3L);
        medicalRecords3.setFirstName("Jacques");
        medicalRecords3.setLastName("Boyd");
        medicalRecords3.setBirthdate("01/01/1999");
        medicalRecords3.setMedications(arrayMedical3);
        medicalRecords3.setAllergies(arrayAllergies3);
        ArrayList<Persons> persons = new ArrayList<>(
                Arrays.asList(persons1, persons2, persons3)
        );
        ArrayList<MedicalRecords> medicalRecords = new ArrayList<>(
                Arrays.asList(medicalRecords1, medicalRecords2, medicalRecords3)
        );
        ArrayList<Firestations> firestations = new ArrayList<>(
                Arrays.asList(firestations1, firestations2, firestations3)
        );
        when(service.getPersons()).thenReturn(persons);
        when(medicalRecordsService.getMedicalRecords()).thenReturn(medicalRecords);
        when(firestationsService.getFirestations()).thenReturn(firestations);
        mockMvc.perform(get("/fire?address={address}", address))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void testGetPersonsByZone() throws Exception {
        String[] arrayMedical1 = new String[]{"Paracetamol : 1000mg"};
        String[] arrayMedical2 = new String[]{"Paracetamol : 500mg"};
        String[] arrayMedical3 = new String[]{"Codéine : 1500mg"};
        String[] arrayAllergies1 = new String[]{"Aspirine"};
        String[] arrayAllergies2 = new String[]{"Codéine"};
        String[] arrayAllergies3 = new String[]{"Paracetamol"};
        String station_number = "1";
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
        persons1.setFirstName("Michel");
        persons1.setLastName("Boyd");
        persons1.setAddress("15 rue de la gloire");
        persons1.setCity("Culver");
        persons1.setZip("97451");
        persons1.setPhone("841-874-6512");
        persons1.setEmail("michel@email.com");
        Persons persons2 = new Persons();
        persons2.setId(2L);
        persons2.setFirstName("Paul");
        persons2.setLastName("Boyd");
        persons2.setAddress("10 rue de la gloire");
        persons2.setCity("Culver");
        persons2.setZip("97465");
        persons2.setPhone("841-874-3254");
        persons2.setEmail("paul@email.com");
        Persons persons3 = new Persons();
        persons3.setId(3L);
        persons3.setFirstName("Jacques");
        persons3.setLastName("Boyd");
        persons3.setAddress("10 rue de la gloire");
        persons3.setCity("Culver");
        persons3.setZip("97451");
        persons3.setPhone("841-874-4596");
        persons3.setEmail("jacques@email.com");
        MedicalRecords medicalRecords1 = new MedicalRecords();
        medicalRecords1.setId(1L);
        medicalRecords1.setFirstName("Michel");
        medicalRecords1.setLastName("Boyd");
        medicalRecords1.setBirthdate("01/01/1965");
        medicalRecords1.setMedications(arrayMedical1);
        medicalRecords1.setAllergies(arrayAllergies1);
        MedicalRecords medicalRecords2 = new MedicalRecords();
        medicalRecords2.setId(2L);
        medicalRecords2.setFirstName("Paul");
        medicalRecords2.setLastName("Boyd");
        medicalRecords2.setBirthdate("01/01/1985");
        medicalRecords2.setMedications(arrayMedical2);
        medicalRecords2.setAllergies(arrayAllergies2);
        MedicalRecords medicalRecords3 = new MedicalRecords();
        medicalRecords3.setId(3L);
        medicalRecords3.setFirstName("Jacques");
        medicalRecords3.setLastName("Boyd");
        medicalRecords3.setBirthdate("01/01/2010");
        medicalRecords3.setMedications(arrayMedical3);
        medicalRecords3.setAllergies(arrayAllergies3);
        ArrayList<Persons> persons = new ArrayList<>(
                Arrays.asList(persons1, persons2, persons3)
        );
        ArrayList<MedicalRecords> medicalRecords = new ArrayList<>(
                Arrays.asList(medicalRecords1, medicalRecords2, medicalRecords3)
        );
        ArrayList<Firestations> firestations = new ArrayList<>(
                Arrays.asList(firestations1, firestations2, firestations3)
        );
        when(service.getPersons()).thenReturn(persons);
        when(medicalRecordsService.getMedicalRecords()).thenReturn(medicalRecords);
        when(firestationsService.getFirestations()).thenReturn(firestations);
        mockMvc.perform(get("/firestations?stationNumber={stationNumber}", station_number))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void testGetHomeByStation() throws Exception {
        String[] arrayMedical1 = new String[]{"Paracetamol : 1000mg"};
        String[] arrayMedical2 = new String[]{"Paracetamol : 500mg"};
        String[] arrayMedical3 = new String[]{"Codéine : 1500mg"};
        String[] arrayAllergies1 = new String[]{"Aspirine"};
        String[] arrayAllergies2 = new String[]{"Codéine"};
        String[] arrayAllergies3 = new String[]{"Paracetamol"};
        String stations = "1";
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
        persons1.setFirstName("Michel");
        persons1.setLastName("Boyd");
        persons1.setAddress("15 rue de la gloire");
        persons1.setCity("Culver");
        persons1.setZip("97451");
        persons1.setPhone("841-874-6512");
        persons1.setEmail("michel@email.com");
        Persons persons2 = new Persons();
        persons2.setId(2L);
        persons2.setFirstName("Paul");
        persons2.setLastName("Boyd");
        persons2.setAddress("10 rue de la gloire");
        persons2.setCity("Culver");
        persons2.setZip("97465");
        persons2.setPhone("841-874-3254");
        persons2.setEmail("paul@email.com");
        Persons persons3 = new Persons();
        persons3.setId(3L);
        persons3.setFirstName("Jacques");
        persons3.setLastName("Boyd");
        persons3.setAddress("10 rue de la gloire");
        persons3.setCity("Culver");
        persons3.setZip("97451");
        persons3.setPhone("841-874-4596");
        persons3.setEmail("jacques@email.com");
        MedicalRecords medicalRecords1 = new MedicalRecords();
        medicalRecords1.setId(1L);
        medicalRecords1.setFirstName("Michel");
        medicalRecords1.setLastName("Boyd");
        medicalRecords1.setBirthdate("01/01/1965");
        medicalRecords1.setMedications(arrayMedical1);
        medicalRecords1.setAllergies(arrayAllergies1);
        MedicalRecords medicalRecords2 = new MedicalRecords();
        medicalRecords2.setId(2L);
        medicalRecords2.setFirstName("Paul");
        medicalRecords2.setLastName("Boyd");
        medicalRecords2.setBirthdate("01/01/1985");
        medicalRecords2.setMedications(arrayMedical2);
        medicalRecords2.setAllergies(arrayAllergies2);
        MedicalRecords medicalRecords3 = new MedicalRecords();
        medicalRecords3.setId(3L);
        medicalRecords3.setFirstName("Jacques");
        medicalRecords3.setLastName("Boyd");
        medicalRecords3.setBirthdate("01/01/1999");
        medicalRecords3.setMedications(arrayMedical3);
        medicalRecords3.setAllergies(arrayAllergies3);
        ArrayList<Persons> persons = new ArrayList<>(
                Arrays.asList(persons1, persons2, persons3)
        );
        ArrayList<MedicalRecords> medicalRecords = new ArrayList<>(
                Arrays.asList(medicalRecords1, medicalRecords2, medicalRecords3)
        );
        ArrayList<Firestations> firestations = new ArrayList<>(
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
        String[] arrayMedical1 = new String[]{"Paracetamol : 1000mg"};
        String[] arrayAllergies1 = new String[]{"Aspirine"};
        String lastname = "Boyd";
        Persons persons1 = new Persons();
        persons1.setId(1L);
        persons1.setFirstName("Michel");
        persons1.setLastName(lastname);
        persons1.setAddress("1509 Culver St");
        persons1.setCity("Culver");
        persons1.setZip("97451");
        persons1.setPhone("841-874-6512");
        persons1.setEmail("michel@email.com");
        MedicalRecords medicalRecords1 = new MedicalRecords();
        medicalRecords1.setId(1L);
        medicalRecords1.setFirstName("Michel");
        medicalRecords1.setLastName(lastname);
        medicalRecords1.setBirthdate("01/01/1965");
        medicalRecords1.setMedications(arrayMedical1);
        medicalRecords1.setAllergies(arrayAllergies1);
        ArrayList<Persons> persons = new ArrayList<>(
                Arrays.asList(persons1)
        );
        ArrayList<MedicalRecords> medicalRecords = new ArrayList<>(
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
        String[] arrayMedical1 = new String[]{"Paracetamol : 1000mg"};
        String[] arrayMedical2 = new String[]{"Paracetamol : 500mg"};
        String[] arrayMedical3 = new String[]{"Codéine : 1500mg"};
        String[] arrayAllergies1 = new String[]{"Aspirine"};
        String[] arrayAllergies2 = new String[]{"Codéine"};
        String[] arrayAllergies3 = new String[]{"Paracetamol"};
        String lastname = "Boyd";
        Persons persons1 = new Persons();
        persons1.setId(1L);
        persons1.setFirstName("Michel");
        persons1.setLastName(lastname);
        persons1.setAddress("1509 Culver St");
        persons1.setCity("Culver");
        persons1.setZip("97451");
        persons1.setPhone("841-874-6512");
        persons1.setEmail("michel@email.com");
        Persons persons2 = new Persons();
        persons2.setId(2L);
        persons2.setFirstName("Paul");
        persons2.setLastName(lastname);
        persons2.setAddress("10 rue de la gloire");
        persons2.setCity("Culver");
        persons2.setZip("97465");
        persons2.setPhone("841-874-3254");
        persons2.setEmail("paul@email.com");
        Persons persons3 = new Persons();
        persons3.setId(3L);
        persons3.setFirstName("Jacques");
        persons3.setLastName(lastname);
        persons3.setAddress("15 rue de Mont Miraille");
        persons3.setCity("Culver");
        persons3.setZip("97451");
        persons3.setPhone("841-874-6512");
        persons3.setEmail("jacques@email.com");
        MedicalRecords medicalRecords1 = new MedicalRecords();
        medicalRecords1.setId(1L);
        medicalRecords1.setFirstName("Michel");
        medicalRecords1.setLastName(lastname);
        medicalRecords1.setBirthdate("01/01/1965");
        medicalRecords1.setMedications(arrayMedical1);
        medicalRecords1.setAllergies(arrayAllergies1);
        MedicalRecords medicalRecords2 = new MedicalRecords();
        medicalRecords2.setId(2L);
        medicalRecords2.setFirstName("Paul");
        medicalRecords2.setLastName(lastname);
        medicalRecords2.setBirthdate("01/01/1985");
        medicalRecords2.setMedications(arrayMedical2);
        medicalRecords2.setAllergies(arrayAllergies2);
        MedicalRecords medicalRecords3 = new MedicalRecords();
        medicalRecords3.setId(2L);
        medicalRecords3.setFirstName("Jacques");
        medicalRecords3.setLastName(lastname);
        medicalRecords3.setBirthdate("01/01/1999");
        medicalRecords3.setMedications(arrayMedical3);
        medicalRecords3.setAllergies(arrayAllergies3);
        ArrayList<Persons> persons = new ArrayList<>(
                Arrays.asList(persons1, persons2, persons3)
        );
        ArrayList<MedicalRecords> medicalRecords = new ArrayList<>(
                Arrays.asList(medicalRecords1, medicalRecords2, medicalRecords3)
        );
        when(service.getPersons()).thenReturn(persons);
        when(medicalRecordsService.getMedicalRecords()).thenReturn(medicalRecords);
        mockMvc.perform(get("/personInfolastName={lastName}", lastname))
                .andExpect(status().isOk())
                .andDo(print());
    }
}
