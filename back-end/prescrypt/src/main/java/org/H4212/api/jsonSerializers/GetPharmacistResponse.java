package org.H4212.api.jsonSerializers;

import jakarta.json.*;
import org.H4212.entities.Pharmacist;
import javax.validation.Valid;

public class GetPharmacistResponse {
    private @Valid Pharmacist pharmacist;

    public GetPharmacistResponse(Pharmacist pharmacist) {
        this.pharmacist = pharmacist;
    }
    public GetPharmacistResponse(){}

    public Pharmacist getPharmacist() {
        return pharmacist;
    }

    public void setPharmacist(Pharmacist pharmacist) {
        this.pharmacist = pharmacist;
    }
    public JsonObject toJson()
    {
        return pharmacist.toJsonBuilder().build();
    }
    public JsonObjectBuilder getJsonObjectBuilder(){
        return pharmacist.toJsonBuilder();
    }
    public boolean isNull(){
        if(this.pharmacist.getLastName() == null)
        {
            return true;
        }
        return false;
    }
}
