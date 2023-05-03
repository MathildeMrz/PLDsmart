package org.H4212.api.jsonSerializers;

import jakarta.json.Json;
import jakarta.json.JsonBuilderFactory;
import jakarta.json.JsonObject;
import org.H4212.entities.Person;

import jakarta.validation.*;
import java.io.Serializable;

public class AuthenticateUserResponse {

    private @Valid Person person;

    public AuthenticateUserResponse(Person user) {
        this.person = user;
    }

    public AuthenticateUserResponse() {
    }

    public Person getPerson(){
        return person;
    }

    public void setUser(Person user){
        this.person = user;
    }

    public JsonObject toJson()
    {
        return person.toJson();


    }
}
