package org.H4212.api.jsonSerializers;

import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
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
    public JsonObject toJson()
    {
        return prescription.toJsonBuilder().build();
    }
    public JsonObjectBuilder getJsonObjectBuilder(){
        return prescription.toJsonBuilder();
    }
    public boolean isNull(){
        if(this.prescription.getDoctor().getLastName() == null)
        {
            return true;
        }
        return false;
    }
}
