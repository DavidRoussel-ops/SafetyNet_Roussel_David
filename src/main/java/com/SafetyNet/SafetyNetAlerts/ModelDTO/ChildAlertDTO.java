package com.SafetyNet.SafetyNetAlerts.ModelDTO;

import java.util.ArrayList;

public class ChildAlertDTO {

    private String firstname;

    private String lastname;

    private int age;

    private ArrayList<OtherPeopleDTO> othePeople;

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public ArrayList<OtherPeopleDTO> getOthePeople() {
        return othePeople;
    }

    public void setOthePeople(ArrayList<OtherPeopleDTO> othePeople) {
        this.othePeople = othePeople;
    }
}
