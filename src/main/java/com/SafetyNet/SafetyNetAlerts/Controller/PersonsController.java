package com.SafetyNet.SafetyNetAlerts.Controller;


import com.SafetyNet.SafetyNetAlerts.Model.Firestations;
import com.SafetyNet.SafetyNetAlerts.Model.MedicalRecords;
import com.SafetyNet.SafetyNetAlerts.Model.Persons;
import com.SafetyNet.SafetyNetAlerts.ModelDTO.*;
import com.SafetyNet.SafetyNetAlerts.Service.BusinessService;
import com.SafetyNet.SafetyNetAlerts.Service.FirestationsService;
import com.SafetyNet.SafetyNetAlerts.Service.MedicalRecordsService;
import com.SafetyNet.SafetyNetAlerts.Service.PersonService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Consumer;

@RestController
public class PersonsController {

    @Autowired
    private PersonService personService;

    @Autowired
    private BusinessService businessService;

    @Autowired
    private FirestationsService firestationsService;

    @Autowired
    private MedicalRecordsService medicalRecordsService;

    /**
     * Create - Add a new person
     *
     * @param persons an Object Persons
     * @return the persons object saved
     */
    @PostMapping("/person")
    public ResponseEntity<Persons> createPersons(@RequestBody Persons persons) {
        Persons persons1 = personService.savePerson(persons);
        try {
            return new ResponseEntity<>(persons1, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Read - Get one persons
     *
     * @param id the id of the persons
     * @return an persons object
     */
    @GetMapping("/person/{id}")
    public ResponseEntity<Persons> getPerson(@PathVariable("id") final Long id) {
        Optional<Persons> persons = personService.getPerson(id);
        return persons.map(person -> new ResponseEntity<>(person, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Read - Get all person
     *
     * @return - An Iterable object of person
     */
    @GetMapping("/person")
    public ResponseEntity<Iterable<Persons>> getAllPersons() {
        Iterable<Persons> persons = personService.getPersons();
        try {
            return new ResponseEntity<>(persons, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Read - Get  person
     *
     * @return - An ArrayList object of EmailDTO
     */
    @GetMapping("/communityEmail")
    @ResponseBody
    public ResponseEntity<ArrayList<EmailDTO>> getAllMail(@RequestParam(defaultValue = "city") String city) {
        Iterable<Persons> persons = personService.getPersons();
        ArrayList<EmailDTO> mail = new ArrayList<>();
        try {
            for (Persons person : persons) {
                if (Objects.equals(city, person.getCity())) {
                    EmailDTO emailDTO = new EmailDTO();
                    emailDTO.setEmail(person.getEmail());
                    mail.add(emailDTO);
                } else {
                    return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
                }
            }
            return new ResponseEntity<>(mail, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/childAlert")
    @ResponseBody
    public ResponseEntity<ArrayList<ChildAlertDTO>> getChildAlert(@RequestParam(defaultValue = "address") String address) throws ParseException {
        Iterable<Persons> persons = personService.getPersons();
        Iterable<MedicalRecords> medicalRecords = medicalRecordsService.getMedicalRecords();
        ArrayList<ChildAlertDTO> childAlertDTOS = new ArrayList<>();
        ArrayList<OtherPeopleDTO> arrayList = new ArrayList<>();
        try {
            for (Persons persons1 : persons) {
                if (Objects.equals(address, persons1.getAddress())) {
                    for (MedicalRecords medicalRecords1 : medicalRecords) {
                        if (Objects.equals(persons1.getFirstname(), medicalRecords1.getFirstname())) {
                            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                            Date birthdate = format.parse(medicalRecords1.getBirthdate());
                            int isChild = getYears(birthdate);
                            if (isChild > 18) {
                                OtherPeopleDTO otherPeopleDTO = new OtherPeopleDTO();
                                otherPeopleDTO.setFirstname(persons1.getFirstname());
                                otherPeopleDTO.setLastname(persons1.getLastname());
                                arrayList.add(otherPeopleDTO);
                            } else {
                                ChildAlertDTO childAlertDTO = new ChildAlertDTO();
                                childAlertDTO.setLastname(persons1.getLastname());
                                childAlertDTO.setFirstname(persons1.getFirstname());
                                childAlertDTO.setAge(getYears(birthdate));
                                childAlertDTO.setOthePeople(arrayList);
                                childAlertDTOS.add(childAlertDTO);
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(childAlertDTOS, HttpStatus.OK);
    }

    @GetMapping("/phoneAlert")
    @ResponseBody
    public ResponseEntity<ArrayList<PhoneDTO>> getPhoneAlerts(@RequestParam(defaultValue = "firestation") String firestation) {
        Iterable<Persons> persons = personService.getPersons();
        Iterable<Firestations> firestations = firestationsService.getFirestations();
        ArrayList<PhoneDTO> phoneDTOS = new ArrayList<>();
        try {
            for (Firestations firestations1 : firestations) {
                if (Objects.equals(firestations1.getStation(), firestation)) {
                    for (Persons persons1 : persons) {
                        if (Objects.equals(persons1.getAddress(), firestations1.getAddress())) {
                            PhoneDTO phoneDTO = new PhoneDTO();
                            phoneDTO.setPhone(persons1.getPhone());
                            phoneDTOS.add(phoneDTO);
                        }
                    }
                }
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(phoneDTOS, HttpStatus.OK);
    }

    @GetMapping("/fire")
    @ResponseBody
    public ResponseEntity<ArrayList<FireAddressDTO>> getAddress(@RequestParam(defaultValue = "address") String address) throws ParseException {
        Iterable<Persons> persons = personService.getPersons();
        Iterable<MedicalRecords> medicalRecords = medicalRecordsService.getMedicalRecords();
        Iterable<Firestations> firestations = firestationsService.getFirestations();
        ArrayList<FireAddressDTO> fireAddressDTOS = new ArrayList<>();
        try {
            for (Persons persons1 : persons) {
                if (Objects.equals(address, persons1.getAddress())) {
                    FireAddressDTO fireAddressDTO = new FireAddressDTO();
                    fireAddressDTO.setLastname(persons1.getLastname());
                    fireAddressDTO.setPhone(persons1.getPhone());
                    for (MedicalRecords medicalRecords1 : medicalRecords) {
                        if (Objects.equals(persons1.getFirstname(), medicalRecords1.getFirstname())) {
                            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                            Date birthdate = format.parse(medicalRecords1.getBirthdate());
                            fireAddressDTO.setAge(getYears(birthdate));
                            fireAddressDTO.setMedications(medicalRecords1.getMedications());
                            fireAddressDTO.setAllergies(medicalRecords1.getAllergies());
                            for (Firestations firestations1 : firestations) {
                                if (Objects.equals(firestations1.getAddress(), persons1.getAddress())) {
                                    fireAddressDTO.setStations(firestations1.getStation());
                                    fireAddressDTOS.add(fireAddressDTO);
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(fireAddressDTOS, HttpStatus.OK);
    }

    @GetMapping("/flood/stations")
    @ResponseBody
    public ResponseEntity<ArrayList<ListStationDTO>> getHomeByStation(@RequestParam String stations) throws ParseException {
        Iterable<Persons> persons = personService.getPersons();
        Iterable<MedicalRecords> medicalRecords = medicalRecordsService.getMedicalRecords();
        Iterable<Firestations> firestations = firestationsService.getFirestations();
        ArrayList<ListStationDTO> listStationDTOS = new ArrayList<>();
        try {
            for (Firestations firestations1 : firestations) {
                if (Objects.equals(stations, firestations1.getStation())) {
                    for (Persons persons1 : persons) {
                        if (Objects.equals(firestations1.getAddress(), persons1.getAddress())) {
                            ListStationDTO listStationDTO = new ListStationDTO();
                            listStationDTO.setLastname(persons1.getLastname());
                            listStationDTO.setPhone(persons1.getPhone());
                            for (MedicalRecords medicalRecords1 : medicalRecords) {
                                if (Objects.equals(persons1.getFirstname(), medicalRecords1.getFirstname())) {
                                    SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                                    Date birthdate = format.parse(medicalRecords1.getBirthdate());
                                    listStationDTO.setAge(getYears(birthdate));
                                    listStationDTO.setMedications(medicalRecords1.getMedications());
                                    listStationDTO.setAllergies(medicalRecords1.getAllergies());
                                    listStationDTOS.add(listStationDTO);
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(listStationDTOS, HttpStatus.OK);
    }

    /**
     * Read - Get  person, medicalRecord
     *
     * @param lastname - The lastname of person to search
     * @return - An ArrayList object of InfolastNameDTO
     */
    @GetMapping("/personInfolastName={lastName}")
    @ResponseBody
    public ResponseEntity<ArrayList<InfolastNameDTO>> getPersonsByLastname(@PathVariable("lastName") String lastname) throws ParseException {
        Iterable<Persons> persons = personService.getPersons();
        Iterable<MedicalRecords> medicalRecords = medicalRecordsService.getMedicalRecords();
        ArrayList<InfolastNameDTO> infoPerson = new ArrayList<>();
        try {
            for (Persons persons1 : persons) {
                if (Objects.equals(lastname, persons1.getLastname())) {
                    InfolastNameDTO infolastNameDTO = new InfolastNameDTO();
                    infolastNameDTO.setLastname(persons1.getLastname());
                    infolastNameDTO.setEmail(persons1.getEmail());
                    infolastNameDTO.setAddress(persons1.getAddress());
                    for (MedicalRecords medicalRecords1 : medicalRecords) {
                        if (Objects.equals(medicalRecords1.getFirstname(), persons1.getFirstname())) {
                            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                            Date birthdate = format.parse(medicalRecords1.getBirthdate());
                            infolastNameDTO.setAge(getYears(birthdate));
                            infolastNameDTO.setMedications(medicalRecords1.getMedications());
                            infolastNameDTO.setAllergies(medicalRecords1.getAllergies());
                            infoPerson.add(infolastNameDTO);
                        }
                    }
                }
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(infoPerson, HttpStatus.OK);
    }

    /**
     * Update - Update an existing persons
     *
     * @param id      - The id of the persons to update
     * @param persons - The persons object update
     * @return
     */
    @PutMapping("/person/{id}")
    public ResponseEntity<Persons> updatePerson(@PathVariable("id") final Long id, @RequestBody Persons persons) {
        Optional<Persons> personsOptional = personService.getPerson(id);
        if (personsOptional.isPresent()) {
            Persons currentPersons = personsOptional.get();

            String firstName = persons.getFirstname();
            if (firstName != null) {
                currentPersons.setFirstname(firstName);
            }
            String lastName = persons.getLastname();
            if (lastName != null) {
                currentPersons.setLastname(lastName);
            }
            String address = persons.getAddress();
            if (address != null) {
                currentPersons.setAddress(address);
            }
            String city = persons.getCity();
            if (city != null) {
                currentPersons.setCity(city);
            }
            String zip = persons.getZip();
            if (zip != null) {
                currentPersons.setZip(zip);
            }
            String phone = persons.getPhone();
            if (phone != null) {
                currentPersons.setPhone(phone);
            }
            String email = persons.getEmail();
            if (email != null) {
                currentPersons.setEmail(email);
            }
            return new ResponseEntity<>(personService.savePerson(currentPersons), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Delete - Delete an person
     *
     * @param id - The id of the person to delete
     */
    @DeleteMapping("/person/{id}")
    public ResponseEntity<HttpStatus> deletePerson(@PathVariable("id") final Long id) {
        try {
            personService.deletePerson(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public static int getYears(Date date) {
        Calendar current = Calendar.getInstance();
        Calendar birthdate = Calendar.getInstance();
        birthdate.setTime(date);
        int yeardiff = current.get(Calendar.YEAR) - birthdate.get(Calendar.YEAR);
        current.add(Calendar.YEAR, -yeardiff);
        if (birthdate.after(current)) {
            yeardiff = yeardiff - 1;
        }
        return yeardiff;
    }
}
