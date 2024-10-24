package com.SafetyNet.SafetyNetAlerts.Model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "firestations")
public class Firestations {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "address")
    private String address;

    @Column(name = "station")
    private int station;

}
