package org.H4212.api.jsonSerializers;

import org.H4212.entities.Person;

import jakarta.validation.*;
import java.io.Serializable;

public class AuthenticateUserResponse implements Serializable {

    private @Valid boolean authenticated = false;

    private @Valid Person person;

    public AuthenticateUserResponse(boolean authenticated, Person user) {
        this.authenticated = authenticated;
        this.person = user;
    }

    public AuthenticateUserResponse() {
    }

    public boolean isAuthenticated() {
        return authenticated;
    }

    public void setAuthenticated(boolean authenticated) {
        this.authenticated = authenticated;
    }

    public Person getPerson(){
        return person;
    }

    public void setUser(Person user){
        this.person = user;
    }
}
