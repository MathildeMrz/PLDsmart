package org.H4212.entities;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class Medication {

    @Id
    @GeneratedValue
    @NotNull
    private Long id;

    @Column(name = "Name")
    @NotNull
    private String name;

    @Column(name = "Dosage")
    @NotNull
    private int dosage;

    @Column(name = "Instructions", nullable = true)
    private String instructions;

    public Medication(String name, int dosage, String instructions) {
        this.name = name;
        this.dosage = dosage;
        this.instructions = instructions;
    }

    public Medication(String name, int dosage) {
        this.name = name;
        this.dosage = dosage;
    }

    public Medication(){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDosage() {
        return dosage;
    }

    public void setDosage(int dosage) {
        this.dosage = dosage;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    @Override
    public String toString() {
        return "Medication{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", dosage=" + dosage +
                ", instructions='" + instructions + '\'' +
                '}';
    }
}
