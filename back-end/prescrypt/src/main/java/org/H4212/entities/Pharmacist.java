package org.H4212.entities;

import jakarta.persistence.Entity;

@Entity
public class Pharmacist extends Person{

    public Pharmacist(String lastName, String firstName, String pseudo, String password) {
        super(lastName, firstName, pseudo, password);
    }

    public Pharmacist(){}

    @Override
    public String toString() {
        return "Pharmacist{} " + super.toString();
    }
}
