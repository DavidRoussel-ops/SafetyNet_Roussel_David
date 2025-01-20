package com.SafetyNet.SafetyNetAlerts.Controller;

import com.SafetyNet.SafetyNetAlerts.Model.Firestations;
import com.SafetyNet.SafetyNetAlerts.Model.MedicalRecords;
import com.SafetyNet.SafetyNetAlerts.Model.Persons;
import com.SafetyNet.SafetyNetAlerts.ModelDTO.*;
import com.SafetyNet.SafetyNetAlerts.Service.BusinessService;
import com.SafetyNet.SafetyNetAlerts.Service.FirestationsService;
import com.SafetyNet.SafetyNetAlerts.Service.MedicalRecordsService;
import com.SafetyNet.SafetyNetAlerts.Service.PersonService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@Log4j2
public class ModelDTOController {

    @Autowired
    private PersonService personService;

    @Autowired
    private FirestationsService firestationsService;

    @Autowired
    private MedicalRecordsService medicalRecordsService;

    private static final Logger logger = LoggerFactory.getLogger(ModelDTOController.class);


    /**
     * @param city
     * @return ArrayList<EmailDTO>>
     */
    @GetMapping("/communityEmail")
    @ResponseBody
    public ResponseEntity<ArrayList<EmailDTO>> getAllMail(@RequestParam(defaultValue = "city") String city) {
        Iterable<Persons> persons = personService.getPersons();
        ArrayList<EmailDTO> mail = new ArrayList<>();
        try {
            logger.info("Requête getAllMail avec en paramètre: {}", city);
            for (Persons person : persons) {
                logger.debug("Boucle des Persons en cours : {}", person);
                if (Objects.equals(city, person.getCity())) {
                    EmailDTO emailDTO = new EmailDTO();
                    emailDTO.setEmail(person.getEmail());
                    mail.add(emailDTO);
                } else {
                    log.error("Erreur de type 404 not found.");
                    return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
                }
            }
            logger.info("Réponse réussi pour la requête getAllMail: {}", mail);
            return new ResponseEntity<>(mail, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Erreur lors du traitement de la requête getAllMail: {}", e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * @param address
     * @return ArrayList<ChildAlertDTO>
     * @throws ParseException
     */
    @GetMapping("/childAlert")
    @ResponseBody
    public ResponseEntity<ArrayList<ChildAlertDTO>> getChildAlert(@RequestParam(defaultValue = "address") String address) throws ParseException {
        Iterable<Persons> persons = personService.getPersons();
        Iterable<MedicalRecords> medicalRecords = medicalRecordsService.getMedicalRecords();
        ArrayList<ChildAlertDTO> childAlertDTOS = new ArrayList<>();
        ArrayList<OtherPeopleDTO> arrayList = new ArrayList<>();
        try {
            logger.info("Requête getChildAlert avec en paramètre: {}", address);
            for (Persons persons1 : persons) {
                logger.debug("Boucle de persons1 en cours : {}", persons1);
                if (Objects.equals(address, persons1.getAddress())) {
                    for (MedicalRecords medicalRecords1 : medicalRecords) {
                        logger.debug("Boucle de medicalRecords1 en cours : {}", medicalRecords1);
                        if (Objects.equals(persons1.getFirstName(), medicalRecords1.getFirstName())) {
                            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                            Date birthdate = format.parse(medicalRecords1.getBirthdate());
                            int isChild = getYears(birthdate);
                            if (isChild > 18) {
                                OtherPeopleDTO otherPeopleDTO = new OtherPeopleDTO();
                                otherPeopleDTO.setFirstname(persons1.getFirstName());
                                otherPeopleDTO.setLastname(persons1.getLastName());
                                arrayList.add(otherPeopleDTO);
                            } else {
                                ChildAlertDTO childAlertDTO = new ChildAlertDTO();
                                childAlertDTO.setLastname(persons1.getLastName());
                                childAlertDTO.setFirstname(persons1.getFirstName());
                                childAlertDTO.setAge(getYears(birthdate));
                                childAlertDTO.setOthePeople(arrayList);
                                childAlertDTOS.add(childAlertDTO);
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            logger.error("Erreur lors du traitement de la requête getChildAlert: {}", e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        logger.info("Réponse réussi pour la requête getChildAlert: {}", childAlertDTOS);
        return new ResponseEntity<>(childAlertDTOS, HttpStatus.OK);
    }

    /**
     * @param firestation
     * @return <ArrayList<PhoneDTO>>
     */
    @GetMapping("/phoneAlert")
    @ResponseBody
    public ResponseEntity<ArrayList<PhoneDTO>> getPhoneAlerts(@RequestParam(defaultValue = "firestation") String firestation) {
        Iterable<Persons> persons = personService.getPersons();
        Iterable<Firestations> firestations = firestationsService.getFirestations();
        ArrayList<PhoneDTO> phoneDTOS = new ArrayList<>();
        try {
            logger.info("Requête getPhoneAlerts avec en paramètre: {}", firestation);
            for (Firestations firestations1 : firestations) {
                logger.debug("Boucle de firestations1 en cours : {}", firestations1);
                if (Objects.equals(firestations1.getStation(), firestation)) {
                    for (Persons persons1 : persons) {
                        logger.debug("Boucle de persons1 en cours : {}", persons1);
                        if (Objects.equals(persons1.getAddress(), firestations1.getAddress())) {
                            PhoneDTO phoneDTO = new PhoneDTO();
                            phoneDTO.setPhone(persons1.getPhone());
                            phoneDTOS.add(phoneDTO);
                        }
                    }
                }
            }
        } catch (Exception e) {
            logger.error("Erreur lors du traitement de la requête getPhoneAlerts: {}", e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        logger.info("Réponse réussi pour la requête getPhoneAlerts: {}", phoneDTOS);
        return new ResponseEntity<>(phoneDTOS, HttpStatus.OK);
    }

    /**
     * @param address
     * @return <ArrayList<FireAddressDTO>>
     * @throws ParseException
     */
    @GetMapping("/fire")
    @ResponseBody
    public ResponseEntity<ArrayList<FireAddressDTO>> getAddress(@RequestParam(defaultValue = "address") String address) throws ParseException {
        Iterable<Persons> persons = personService.getPersons();
        Iterable<MedicalRecords> medicalRecords = medicalRecordsService.getMedicalRecords();
        Iterable<Firestations> firestations = firestationsService.getFirestations();
        ArrayList<FireAddressDTO> fireAddressDTOS = new ArrayList<>();
        try {
            logger.info("Requête getAddress avec en paramètre: {}", address);
            for (Persons persons1 : persons) {
                logger.debug("Boucle de persons1 en cours : {}", persons1);
                if (Objects.equals(address, persons1.getAddress())) {
                    FireAddressDTO fireAddressDTO = new FireAddressDTO();
                    fireAddressDTO.setLastname(persons1.getLastName());
                    fireAddressDTO.setPhone(persons1.getPhone());
                    for (MedicalRecords medicalRecords1 : medicalRecords) {
                        logger.debug("Boucle de medicalRecords1 en cours : {}", medicalRecords1);
                        if (Objects.equals(persons1.getFirstName(), medicalRecords1.getFirstName())) {
                            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                            Date birthdate = format.parse(medicalRecords1.getBirthdate());
                            fireAddressDTO.setAge(getYears(birthdate));
                            fireAddressDTO.setMedications(medicalRecords1.getMedications());
                            fireAddressDTO.setAllergies(medicalRecords1.getAllergies());
                            for (Firestations firestations1 : firestations) {
                                logger.debug("Boucle de firestations1 en cours : {}", firestations1);
                                if (Objects.equals(firestations1.getAddress(), persons1.getAddress())) {
                                    fireAddressDTO.setStations(firestations1.getStation());
                                }
                            }
                        }
                    }
                    fireAddressDTOS.add(fireAddressDTO);
                }
            }
        } catch (Exception e) {
            logger.error("Erreur lors du traitement de la requête getAddress: {}", e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        logger.info("Réponse réussi pour la requête getAddress: {}", fireAddressDTOS);
        return new ResponseEntity<>(fireAddressDTOS, HttpStatus.OK);
    }

    /**
     * @param stationNumber
     * @return <ArrayList<PersonsZoneFirestationsDTO>>
     * @throws ParseException
     */
    @GetMapping("/firestations")
    @ResponseBody
    public ResponseEntity<ArrayList<PersonsZoneFirestationsDTO>> getPersonsByZone(@RequestParam(defaultValue = "stationNumber") String stationNumber) throws ParseException {
        Iterable<Persons> persons = personService.getPersons();
        Iterable<MedicalRecords> medicalRecords = medicalRecordsService.getMedicalRecords();
        Iterable<Firestations> firestations = firestationsService.getFirestations();
        ArrayList<PersonsZoneFirestationsDTO> personsZoneFirestationsDTOS = new ArrayList<>();
        PersonsZoneFirestationsDTO personsZoneFirestationsDTO = new PersonsZoneFirestationsDTO();
        ArrayList<InfoPersonsZoneDTO> infoPersonsZoneDTOS = new ArrayList<>();
        int adult = 0;
        int child = 0;
        try {
            logger.info("Requête getPersonsByZone avec en paramètre: {}", stationNumber);
            for (Firestations firestations1 : firestations) {
                logger.debug("Boucle de firestations1 en cours : {}", firestations1);
                if (Objects.equals(stationNumber, firestations1.getStation())) {
                    for (Persons persons1 : persons) {
                        logger.debug("Boucle de persons1 en cours : {}", persons1);
                        if (Objects.equals(firestations1.getAddress(), persons1.getAddress())) {
                            for (MedicalRecords medicalRecords1 : medicalRecords) {
                                logger.debug("Boucle de medicalRecords1 en cours : {}", medicalRecords1);
                                if (Objects.equals(persons1.getFirstName(), medicalRecords1.getFirstName())) {
                                    InfoPersonsZoneDTO infoPersonsZoneDTO = new InfoPersonsZoneDTO();
                                    infoPersonsZoneDTO.setFirstname(persons1.getFirstName());
                                    infoPersonsZoneDTO.setLastname(persons1.getLastName());
                                    infoPersonsZoneDTO.setPhone(persons1.getPhone());
                                    infoPersonsZoneDTO.setAddress(persons1.getAddress());
                                    infoPersonsZoneDTOS.add(infoPersonsZoneDTO);
                                    SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                                    Date birthdate = format.parse(medicalRecords1.getBirthdate());
                                    int isAdult = getYears(birthdate);
                                    if (isAdult > 18) {
                                        adult++;
                                    } else {
                                        child++;
                                    }
                                }
                            }
                        }
                    }
                }
            }
            personsZoneFirestationsDTO.setInfoPersonsZoneDTOS(infoPersonsZoneDTOS);
            personsZoneFirestationsDTO.setAdult(adult);
            personsZoneFirestationsDTO.setChild(child);
            personsZoneFirestationsDTOS.add(personsZoneFirestationsDTO);
        } catch (Exception e) {
            logger.error("Erreur lors du traitement de la requête getPersonsByZone: {}", e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        logger.info("Réponse réussi pour la requête getPersonsByZone: {}", personsZoneFirestationsDTOS);
        return new ResponseEntity<>(personsZoneFirestationsDTOS, HttpStatus.OK);
    }

    /**
     * @param stations
     * @return <ArrayList<ListStationDTO>>
     * @throws ParseException
     */
    @GetMapping("/flood/stations")
    @ResponseBody
    public ResponseEntity<ArrayList<ListStationDTO>> getHomeByStation(@RequestParam(defaultValue = "stations") String stations) throws ParseException {
        Iterable<Persons> persons = personService.getPersons();
        Iterable<MedicalRecords> medicalRecords = medicalRecordsService.getMedicalRecords();
        Iterable<Firestations> firestations = firestationsService.getFirestations();
        ArrayList<ListStationDTO> listStationDTOS = new ArrayList<>();
        try {
            logger.info("Requête getHomeByStation avec en paramètre: {}", stations);
            for (Firestations firestations1 : firestations) {
                logger.debug("Boucle de firestations1 en cours : {}", firestations1);
                if (Objects.equals(stations, firestations1.getStation())) {
                    for (Persons persons1 : persons) {
                        logger.debug("Boucle de persons1 en cours : {}", persons1);
                        if (Objects.equals(firestations1.getAddress(), persons1.getAddress())) {
                            ListStationDTO listStationDTO = new ListStationDTO();
                            listStationDTO.setLastname(persons1.getLastName());
                            listStationDTO.setPhone(persons1.getPhone());
                            for (MedicalRecords medicalRecords1 : medicalRecords) {
                                logger.debug("Boucle de medicalRecords1 en cours : {}", medicalRecords1);
                                if (Objects.equals(persons1.getFirstName(), medicalRecords1.getFirstName())) {
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
            logger.error("Erreur lors du traitement de la requête getHomeByStation: {}", e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        logger.info("Réponse réussi pour la requête getHomeByStation: {}", listStationDTOS);
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
            logger.info("Requête getPersonsByLastname avec en paramètre: {}", lastname);
            for (Persons persons1 : persons) {
                logger.debug("Boucle de persons1 en cours : {}", persons1);
                if (Objects.equals(lastname, persons1.getLastName())) {
                    InfolastNameDTO infolastNameDTO = new InfolastNameDTO();
                    infolastNameDTO.setLastname(persons1.getLastName());
                    infolastNameDTO.setEmail(persons1.getEmail());
                    infolastNameDTO.setAddress(persons1.getAddress());
                    for (MedicalRecords medicalRecords1 : medicalRecords) {
                        logger.debug("Boucle de medicalRecords1 en cours : {}", medicalRecords1);
                        if (Objects.equals(medicalRecords1.getFirstName(), persons1.getFirstName())) {
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
            logger.error("Erreur lors du traitement de la requête getPersonsByLastname: {}", e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        logger.info("Réponse réussi pour la requête getPersinsByLastname: {}", infoPerson);
        return new ResponseEntity<>(infoPerson, HttpStatus.OK);
    }

    /**
     * @param date
     * @return yeardiff
     */
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
