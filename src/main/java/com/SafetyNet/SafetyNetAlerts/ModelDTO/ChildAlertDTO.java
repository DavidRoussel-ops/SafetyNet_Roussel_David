package com.SafetyNet.SafetyNetAlerts.ModelDTO;

import lombok.Data;

import java.util.ArrayList;

@Data
public class ChildAlertDTO {

    private String firstname;

    private String lastname;

    private int age;

    private ArrayList<OtherPeopleDTO> othePeople;
}
