package com.SafetyNet.SafetyNetAlerts.ModelDTO;

import lombok.Data;

import java.util.ArrayList;

@Data
public class PersonsZoneFirestationsDTO {

    private ArrayList<InfoPersonsZoneDTO> infoPersonsZoneDTOS;

    private int adult;

    private int child;

}
