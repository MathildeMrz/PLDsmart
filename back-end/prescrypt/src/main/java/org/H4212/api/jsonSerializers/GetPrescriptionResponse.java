package org.H4212.api.jsonSerializers;

import jakarta.json.JsonObject;
import org.H4212.entities.Prescription;

import javax.validation.Valid;

public class GetPrescriptionResponse {
    private @Valid Prescription prescription;

    public GetPrescriptionResponse(Prescription prescription) {
        this.prescription = prescription;
    }
    public GetPrescriptionResponse(){

    }

    public Prescription getPrescription() {
        return prescription;
    }

    public void setPrescription(Prescription prescription) {
        this.prescription = prescription;
    }
    /*public JsonObject toJson()
    {
        return prescription.toJsonBuilder().build();
    }*/
}
