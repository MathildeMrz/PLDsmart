package org.H4212.entities;

import jakarta.json.JsonObjectBuilder;
import jakarta.persistence.Entity;

@Entity
public class Pharmacist extends Person{

    public Pharmacist(String lastName, String firstName, String pseudo, String password) {
        super(lastName, firstName, pseudo, password);
    }
    public Pharmacist(String lastName, String firstName){
        super(lastName,firstName);
    }
    public Pharmacist(){}

    @Override
    public JsonObjectBuilder toJsonBuilder() {
        return super.toJsonBuilder();
    }

    @Override
    public String toString() {
        return "Pharmacist{} " + super.toString();
    }
}
