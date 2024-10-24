package com.SafetyNet.SafetyNetAlerts.Model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "medical_records")
public class MedicalRecords {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    private String firstname;

    @Column(name = "last_name")
    private String lastname;

    @Column(name = "birthdate")
    private Date birthdate;

    @Column(name = "medications")
    private String[] medications;

    @Column(name = "allergies")
    private String[] allergies;

}
