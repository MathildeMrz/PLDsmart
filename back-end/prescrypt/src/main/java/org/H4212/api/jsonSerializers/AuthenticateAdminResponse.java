package org.H4212.api.jsonSerializers;

import jakarta.json.JsonObject;
import org.H4212.entities.Person;
import jakarta.validation.*;

public class AuthenticateAdminResponse {

    private @Valid Person person;

    public AuthenticateAdminResponse(Person user) {
        this.person = user;
    }

    public AuthenticateAdminResponse() {
    }

    public Person getPerson(){
        return person;
    }

    public void setUser(Person user){
        this.person = user;
    }

    public boolean isNull(){
        if(this.person.getLastName() == null)
        {
            return true;
        }
        return false;
    }

    public JsonObject toJson()
    {
        return person.toJsonBuilder().build();
    }
}
