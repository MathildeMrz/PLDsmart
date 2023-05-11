package org.H4212.api.jsonSerializers;

import jakarta.json.JsonObject;
import org.H4212.entities.Pharmacist;

public class RegisterPharmacistRequest {

    private Pharmacist pharmacist;

    public RegisterPharmacistRequest(JsonObject jsonObject){
        this.pharmacist = new Pharmacist(jsonObject.getString("lastName"), jsonObject.getString("firstName"),
                    jsonObject.getString("username"), jsonObject.getString("password"), jsonObject.getString("telephone"),
                jsonObject.getString("pharmacyAddress"), jsonObject.getString("ethAddress"));
    }

    /*public RegisterPharmacistRequest(JsonObject jsonObject){
        this.pharmacist = new Pharmacist(jsonObject.getString("lastName"), jsonObject.getString("firstName"),
                jsonObject.getString("pseudo"), jsonObject.getString("password"));
    }*/

    public RegisterPharmacistRequest(){

    }

    public Pharmacist getPharmacist() {
        return pharmacist;
    }

    public void setPharmacist(Pharmacist pharmacist) {
        this.pharmacist = pharmacist;
    }
}
