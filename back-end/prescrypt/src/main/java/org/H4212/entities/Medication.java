package org.H4212.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;

import java.time.Duration;

@Entity
public class Medication {

    @Id
    @GeneratedValue
    @NotNull
    private long id;

    @Column(name = "Name")
    @NotNull
    private String name;

    @Column(name = "Dosage")
    @NotNull
    private int dosage;

    @Column(name = "Instructions", nullable = true)
    private String instructions;
    @Column(name = "Treatment duration")
    @NotNull
    private Duration duration;

    public Medication(long id, String name, int dosage, String instructions, Duration duration) {
        this.id = id;
        this.name = name;
        this.dosage = dosage;
        this.instructions = instructions;
        this.duration = duration;
    }

    public Medication(){

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
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
                ", duration=" + duration +
                '}';
    }
}
