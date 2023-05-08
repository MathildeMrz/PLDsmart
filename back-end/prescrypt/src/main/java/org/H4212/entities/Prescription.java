package org.H4212.entities;

import jakarta.json.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.time.*;
import java.util.*;

@Entity
public class Prescription {

    @Id
    @GeneratedValue
    @NotNull
    private long id;

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

    @OneToMany
    @Column(name = "Medication list")
    @NotNull
    private List<Medication> medicationList;

    @Column(name = "Number of renewals")
    @NotNull
    private int nbRenewals;

    @Column(name = "Non renewable")
    @NotNull
    private boolean NR;

    @Column(name = "Notes", nullable = true)
    private String notes;

    public Prescription(long id, Doctor doctor, Patient patient, OffsetDateTime consultationDate, List<Medication> medicationList, int nbRenewals, boolean NR, String notes) {
        this.id = id;
        this.doctor = doctor;
        this.patient = patient;
        this.consultationDate = consultationDate;
        this.medicationList = medicationList;
        this.nbRenewals = nbRenewals;
        this.NR = NR;
        this.notes = notes;
    }

    public Prescription(long id, Doctor doctor, Patient patient, OffsetDateTime consultationDate, List<Medication> medicationList, int nbRenewals, boolean NR) {
        this.id = id;
        this.doctor = doctor;
        this.patient = patient;
        this.consultationDate = consultationDate;
        this.medicationList = medicationList;
        this.nbRenewals = nbRenewals;
        this.NR = NR;
    }
    public Prescription(long id, Doctor doctor, Patient patient, OffsetDateTime consultationDate, Medication medication, int nbRenewals, boolean NR) {
        this.id = id;
        this.doctor = doctor;
        this.patient = patient;
        this.consultationDate = consultationDate;
        this.nbRenewals = nbRenewals;
        this.NR = NR;
        this.medicationList = new ArrayList<Medication>();
        this.medicationList.add(medication);
    }

    public Prescription(){

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public List<Medication> getMedicationList() {
        return medicationList;
    }

    public void setMedicationList(List<Medication> medicationList) {
        this.medicationList = medicationList;
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
    public JsonObjectBuilder toJsonBuilder(){
        JsonObjectBuilder ret;
        JsonArrayBuilder medicationsArray;
        try{
            ret = Json.createBuilderFactory(null).createObjectBuilder()
                    .add("id", id)
                    .add("doctorId", doctor.getId())
                    .add("doctorFirstName", doctor.getFirstName())
                    .add("doctorLastName", doctor.getLastName())
                    .add("doctorQualification", doctor.getQualification())
                    .add("doctorIdPS", doctor.getIdPSdoctor())
                    .add("patientId", patient.getId())
                    .add("patientFirstName", patient.getFirstName())
                    .add("patientLastName", patient.getLastName())
                    .add("consultationDate", String.valueOf(consultationDate));
            medicationsArray = Json.createBuilderFactory(null).createArrayBuilder();
                    for(Medication medication : medicationList)
                    {
                        medicationsArray.add(medication.getJsonObjectBuilder());
                    }
                    ret.add("medications", medicationsArray);
                    ret.add("nbRenewals", nbRenewals)
                            .add("NR", NR)
                            .add("notes", notes);
        }catch(Exception e){
            System.out.println(e);
            ret = Json.createBuilderFactory(null).createObjectBuilder();
        }
        return ret;
    }

    @Override
    public String toString() {
        return "Prescription{" +
                "id=" + id +
                ", doctor=" + doctor +
                ", patient=" + patient +
                ", consultationDate=" + consultationDate +
                ", medicationList=" + medicationList +
                ", nbRenewals=" + nbRenewals +
                ", NR=" + NR +
                ", notes='" + notes + '\'' +
                '}';
    }
}
