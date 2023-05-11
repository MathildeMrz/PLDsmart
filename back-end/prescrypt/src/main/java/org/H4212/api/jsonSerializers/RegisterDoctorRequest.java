package org.H4212.api.jsonSerializers;

import jakarta.json.JsonObject;
import org.H4212.entities.Doctor;

import static java.lang.Integer.TYPE;
import static java.lang.Integer.parseInt;

public class RegisterDoctorRequest {

    private Doctor doctor;

    public RegisterDoctorRequest(JsonObject jsonObject){
        try{
            this.doctor = new Doctor(jsonObject.getString("lastName"), jsonObject.getString("firstName"),
                    jsonObject.getString("username"), jsonObject.getString("password"),
                    (long) Integer.parseInt(jsonObject.getString("idPSdoctor")), jsonObject.getString("qualification"),
                    jsonObject.getString("officeAddress"), jsonObject.getString("telephone"), jsonObject.getString("ethAddress"));
        }catch(Exception e){
            this.doctor = new Doctor(jsonObject.getString("lastName"), jsonObject.getString("firstName"),
                    jsonObject.getString("username"), jsonObject.getString("password"),
                    (long) jsonObject.getInt("idPSdoctor"), jsonObject.getString("qualification"),
                    jsonObject.getString("officeAddress"), jsonObject.getString("telephone"), jsonObject.getString("ethAddress"));
        }
    }

    public RegisterDoctorRequest(){

    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }
}
