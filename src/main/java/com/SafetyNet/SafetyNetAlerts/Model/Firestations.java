package com.SafetyNet.SafetyNetAlerts.Model;

public class Firestations {

    private String address;

    private int station;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getStation() {
        return station;
    }

    public void setStation(int station) {
        this.station = station;
    }

    @Override
    public String toString() {
        return super.toString();
    }

    String pompier = "Nous sommes la caserne des pompier";
    public String lesPompier() {
        return pompier;
    }
}
