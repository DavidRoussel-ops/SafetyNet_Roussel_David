package com.SafetyNet.SafetyNetAlerts.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Entity
public class Persons {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String firstname;
    private String lastname;
    private String address;
    private String city;
    private String zip;
    private String phone;
    private String email;

    public Persons() {}

    @Override
    public String toString() {
        return super.toString();
    }
}
