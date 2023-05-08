package org.H4212.api.jsonSerializers;

import jakarta.json.*;
import org.H4212.entities.*;
import org.H4212.services.*;
import java.sql.SQLException;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

public class CreatePrescriptionRequest {
    private Prescription prescription;
    private long doctorId;
    public CreatePrescriptionRequest(long doctorId, JsonObject jsonObject) throws SQLException {
        this.doctorId = doctorId;
        ServiceUser serviceUser = new ServiceUser();
        List<Medication> medicationsList = new ArrayList<Medication>();
        for(int i = 0; i < jsonObject.getJsonArray("medications").size(); i++)
        {
            medicationsList.add(new Medication(jsonObject.getJsonArray("medications").getJsonObject(i)));
        }
        this.prescription = new Prescription(serviceUser.getDoctor(this.doctorId), new Patient(jsonObject.getString("patientLastName"), jsonObject.getString("patientFirstName"),
                jsonObject.getInt("patientAge"), jsonObject.getInt("patientWeight"), jsonObject.getBoolean("patientSex"), jsonObject.getInt("patientHeight")),
                OffsetDateTime.now(), medicationsList, jsonObject.getInt("nbRenewals"), jsonObject.getBoolean("NR"), jsonObject.getString("notes"));
    }

    public CreatePrescriptionRequest(){

    }

    public long getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(long doctorId) {
        this.doctorId = doctorId;
    }

    public Prescription getPrescription() {
        return prescription;
    }

    public void setPrescription(Prescription prescription) {
        this.prescription = prescription;
    }
}
