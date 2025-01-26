package com.SafetyNet.SafetyNetAlerts.Repository;

import com.SafetyNet.SafetyNetAlerts.Model.Persons;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.util.ArrayList;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class PersonsRepositoryTest {

    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;

    public void setUpMockMvc() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Autowired
    private PersonsRepository personsRepository;

    @AfterEach
    public void setUp() throws IOException {
        ArrayList<Persons> persons = personsRepository.findAllPersons();
        if (persons.size() == 24) {
            personsRepository.deletePerson(24L);
        }
    }

    @Test
    public void testFindAllPersons() {
        ArrayList<Persons> expect = personsRepository.findAllPersons();
        assertThat(expect).isNotNull();
        Assertions.assertEquals(23, expect.size());
    }

    @Test
    public void testFindPersonById() {
        Long id = 1L;
        Persons persons = personsRepository.findPersonById(id);
        assertThat(persons).isNotNull();
        Assertions.assertEquals("John", persons.getFirstName());
    }

    @Test
    public void testSavePerson() throws IOException {
        Persons persons = new Persons();
        persons.setFirstName("Michel");
        persons.setLastName("Boydo");
        persons.setAddress("1509 Culver St");
        persons.setCity("Culver");
        persons.setZip("97451");
        persons.setPhone("841-874-6512");
        persons.setEmail("michel@email.com");
        personsRepository.savePerson(persons);
        ArrayList<Persons> expect = personsRepository.findAllPersons();
        assertThat(expect).isNotNull();
        Assertions.assertEquals("Michel", expect.get(23).getFirstName());
    }

    @Test
    public void testUpdatePerson() throws IOException {
        Persons persons = new Persons();
        persons.setId(1L);
        persons.setFirstName("John");
        persons.setLastName("Boyd");
        persons.setAddress("1509 Culver St");
        persons.setCity("Culver");
        persons.setZip("97451");
        persons.setPhone("841-874-6524");
        persons.setEmail("jaboyd@email.com");
        personsRepository.updatePerson(persons);
        ArrayList<Persons> expect = personsRepository.findAllPersons();
        assertThat(expect).isNotNull();
        Assertions.assertEquals("841-874-6524", expect.get(0).getPhone());
    }

    @Test
    public void testDeletePerson() throws IOException {
        personsRepository.deletePerson(24L);
        ArrayList<Persons> expect = personsRepository.findAllPersons();
        assertThat(expect).isNotNull();
        Assertions.assertEquals(23, expect.size());
    }
}
