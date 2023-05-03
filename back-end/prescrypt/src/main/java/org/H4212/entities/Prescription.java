package org.H4212.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.time.Duration;
import java.time.OffsetDateTime;

@Entity
public class Prescription {

    @Id
    @GeneratedValue
    @NotNull
    private Long id;

    @ManyToOne
    @JoinColumn(name = "DoctorId", referencedColumnName = "id")
    @NotNull
    @Column(name="Doctor")
    private Doctor doctor;

    @ManyToOne
    @JoinColumn(name = "PatientId", referencedColumnName = "id")
    @NotNull
    @Column(name="Patient")
    private Patient patient;

    @Column(name="Consultation date")
    @NotNull
    private OffsetDateTime consultationDate;

    @ManyToOne
    @JoinColumn(name = "MedicationId", referencedColumnName = "id")
    @Column(name = "Medication")
    @NotNull
    private Medication medication;

    @Column(name = "Treatment duration")
    @NotNull
    private Duration duration;

    @Column(name = "Number of renewals")
    @NotNull
    private int nbRenewals;

    @Column(name = "Non renewable")
    @NotNull
    private boolean NR;

    @Column(name = "Notes", nullable = true)
    private String notes;

    public Prescription(Doctor doctor, Patient patient, OffsetDateTime consultationDate, Medication medication, Duration duration, int nbRenewals, boolean NR, String notes) {
        this.doctor = doctor;
        this.patient = patient;
        this.consultationDate = consultationDate;
        this.medication = medication;
        this.duration = duration;
        this.nbRenewals = nbRenewals;
        this.NR = NR;
        this.notes = notes;
    }

    public Prescription(Doctor doctor, Patient patient, OffsetDateTime consultationDate, Medication medication, Duration duration, int nbRenewals, boolean NR) {
        this.doctor = doctor;
        this.patient = patient;
        this.consultationDate = consultationDate;
        this.medication = medication;
        this.duration = duration;
        this.nbRenewals = nbRenewals;
        this.NR = NR;
    }

    public Prescription(){

    }

    public Long getId() {
        return id;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public OffsetDateTime getConsultationDate() {
        return consultationDate;
    }

    public void setConsultationDate(OffsetDateTime consultationDate) {
        this.consultationDate = consultationDate;
    }

    public Medication getMedication() {
        return medication;
    }

    public void setMedication(Medication medication) {
        this.medication = medication;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public int getNbRenewals() {
        return nbRenewals;
    }

    public void setNbRenewals(int nbRenewals) {
        this.nbRenewals = nbRenewals;
    }

    public boolean isNR() {
        return NR;
    }

    public void setNR(boolean NR) {
        this.NR = NR;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public String toString() {
        return "Prescription{" +
                "id=" + id +
                ", doctor=" + doctor +
                ", patient=" + patient +
                ", consultationDate=" + consultationDate +
                ", medication=" + medication +
                ", duration=" + duration +
                ", nbRenewals=" + nbRenewals +
                ", NR=" + NR +
                ", notes='" + notes + '\'' +
                '}';
    }
}
