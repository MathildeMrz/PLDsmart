package org.H4212.api.jsonSerializers;

import jakarta.json.JsonObject;
import org.H4212.entities.*;
import jakarta.validation.*;

public class AuthenticatePharmacistResponse {

    private @Valid Pharmacist pharmacist;

    public AuthenticatePharmacistResponse(Pharmacist user) {
        this.pharmacist = user;
    }

    public AuthenticatePharmacistResponse() {
    }

    public Pharmacist getPharmacist(){
        return pharmacist;
    }

    public void setUser(Pharmacist user){
        this.pharmacist = user;
    }

    public JsonObject toJson()
    {
        return pharmacist.toJsonBuilder().build();
    }
}
