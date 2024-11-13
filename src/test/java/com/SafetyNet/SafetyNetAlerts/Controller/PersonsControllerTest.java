package com.SafetyNet.SafetyNetAlerts.Controller;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
        Persons persons = new Persons();
        persons.setFirstname("Nigel");
        persons.setLastname("Miguel");
        persons.setAddress("26 rue des rosiers");
        persons.setZip("123456789");
        persons.setCity("Orlando");
        persons.setEmail("nigelisthebest@gmail.com");
        persons.setPhone("1234-555-6789");
        persons.setId(24L);
        service.savePerson(persons);
        mockMvc.perform(get("/person/24"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("firstname", is("Nigel")));
    }


}

