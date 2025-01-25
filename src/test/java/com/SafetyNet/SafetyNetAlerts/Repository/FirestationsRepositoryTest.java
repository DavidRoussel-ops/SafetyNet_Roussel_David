package com.SafetyNet.SafetyNetAlerts.Repository;

import com.SafetyNet.SafetyNetAlerts.Model.Firestations;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.util.ArrayList;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class FirestationsRepositoryTest {

    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;

    public void setUpMockMvc() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Autowired
    private FirestationsRepository firestationsRepository;

    @AfterEach
    public void setUp() throws IOException {
        ArrayList<Firestations> firestations = firestationsRepository.findAllFirestations();
        if (firestations.size() == 14) {
            firestationsRepository.deleteFirestation(14L);
        }
    }

    @Test
    public void testFindAllFirestations() {
        ArrayList<Firestations> expect = firestationsRepository.findAllFirestations();
        assertThat(expect).isNotNull();
        Assertions.assertEquals(13, expect.size());
    }

    @Test
    public void testFindFirestationsById() {
        Long id = 1L;
        Firestations firestations = firestationsRepository.findFirestationById(id);
        assertThat(firestations).isNotNull();
        Assertions.assertEquals("1509 Culver St", firestations.getAddress());
    }

    @Test
    public void testSaveFirestation() throws IOException {
        Firestations firestations = new Firestations();
        firestations.setAddress("12 rue de l'espoir");
        firestations.setStation("5");
        firestationsRepository.saveFirestation(firestations);
        ArrayList<Firestations> expect = firestationsRepository.findAllFirestations();
        assertThat(expect).isNotNull();
        Assertions.assertEquals("5", expect.get(13).getStation());
    }

    @Test
    public void testUpdateFirestation() throws IOException {
        Firestations firestations = new Firestations();
        firestations.setId(1L);
        firestations.setAddress("1509 Culver St");
        firestations.setStation("4");
        firestationsRepository.updateFirestation(firestations);
        ArrayList<Firestations> expect = firestationsRepository.findAllFirestations();
        assertThat(expect).isNotNull();
        Assertions.assertEquals("4", expect.get(0).getStation());
    }

    @Test
    public void testDeleteFirestation() throws IOException {
        firestationsRepository.deleteFirestation(14L);
        ArrayList<Firestations> expect = firestationsRepository.findAllFirestations();
        assertThat(expect).isNotNull();
        Assertions.assertEquals(13, expect.size());
    }
}
