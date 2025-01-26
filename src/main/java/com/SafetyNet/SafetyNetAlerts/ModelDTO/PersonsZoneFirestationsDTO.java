package com.SafetyNet.SafetyNetAlerts.ModelDTO;

import java.util.ArrayList;

public class PersonsZoneFirestationsDTO {

    private ArrayList<InfoPersonsZoneDTO> infoPersonsZoneDTOS;

    private int adult;

    private int child;

    public ArrayList<InfoPersonsZoneDTO> getInfoPersonsZoneDTOS() {
        return infoPersonsZoneDTOS;
    }

    public void setInfoPersonsZoneDTOS(ArrayList<InfoPersonsZoneDTO> infoPersonsZoneDTOS) {
        this.infoPersonsZoneDTOS = infoPersonsZoneDTOS;
    }

    public int getAdult() {
        return adult;
    }

    public void setAdult(int adult) {
        this.adult = adult;
    }

    public int getChild() {
        return child;
    }

    public void setChild(int child) {
        this.child = child;
    }
}
