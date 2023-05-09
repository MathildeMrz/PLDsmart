package org.H4212.api.jsonSerializers;

import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import org.H4212.entities.Doctor;
import javax.validation.Valid;

public class GetDoctorResponse {
    private @Valid Doctor doctor;

    public GetDoctorResponse(Doctor doctor) {
        this.doctor = doctor;
    }
    public GetDoctorResponse(){}

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }
    public JsonObject toJson()
    {
        return doctor.toJsonBuilder().build();
    }
    public JsonObjectBuilder getJsonObjectBuilder(){
        return doctor.toJsonBuilder();
    }
    public boolean isNull(){
        if(this.doctor.getLastName() == null)
        {
            return true;
        }
        return false;
    }
}
