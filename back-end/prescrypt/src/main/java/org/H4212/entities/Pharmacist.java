package org.H4212.entities;

import jakarta.json.JsonObjectBuilder;
import jakarta.persistence.Entity;

@Entity
public class Pharmacist extends Person{

    private String telephone;
    private String ethAddress;
    private String pharmacyAddress;
    /*public Pharmacist(long id, String lastName, String firstName, String pseudo, String password) {
        super(id, lastName, firstName, pseudo, password);
    }*/
    public Pharmacist(String lastName, String firstName, String pseudo, String password) {
        super(lastName, firstName, pseudo, password);
    }
    public Pharmacist(String lastName, String firstName, String pseudo, String password, String telephone, String pharmacyAddress, String ethAddress) {
        super(lastName, firstName, pseudo, password);
        this.telephone = telephone;
        this.pharmacyAddress = pharmacyAddress;
        this.ethAddress = ethAddress;
    }
    public Pharmacist(long id, String lastName, String firstName){
        super(id, lastName,firstName);
    }
    public Pharmacist(long id, String lastName, String firstName, String telephone, String pharmacyAddress, String ethAddress){
        super(id, lastName,firstName);
        this.telephone = telephone;
        this.pharmacyAddress = pharmacyAddress;
        this.ethAddress = ethAddress;
    }
    public Pharmacist(){}

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getPharmacyAddress() {
        return pharmacyAddress;
    }

    public void setPharmacyAddress(String pharmacyAddress) {
        this.pharmacyAddress = pharmacyAddress;
    }

    public String getEthAddress() {
        return ethAddress;
    }

    public void setEthAddress(String ethAddress) {
        this.ethAddress = ethAddress;
    }

    @Override
    public JsonObjectBuilder toJsonBuilder() {
        return super.toJsonBuilder()
                .add("telephone", telephone)
                .add("pharmacyAddress", pharmacyAddress)
                .add("ethAddress", ethAddress);
    }

    @Override
    public String toString() {
        return "Pharmacist{} " + super.toString();
    }
}
