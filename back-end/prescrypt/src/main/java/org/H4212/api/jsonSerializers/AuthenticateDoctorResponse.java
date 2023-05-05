package org.H4212.api.jsonSerializers;

import jakarta.json.JsonObject;
import org.H4212.entities.Doctor;

import jakarta.validation.*;

public class AuthenticateDoctorResponse {

    private @Valid Doctor doctor;

    public AuthenticateDoctorResponse(Doctor user) {
        this.doctor = user;
    }

    public AuthenticateDoctorResponse() {
    }

    public Doctor getDoctor(){
        return doctor;
    }

    public void setUser(Doctor user){
        this.doctor = user;
    }

    public JsonObject toJson()
    {
        return doctor.toJsonBuilder().build();
    }
}
