package org.H4212.api.jsonSerializers;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import org.H4212.entities.Person;

import javax.validation.Valid;
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

    @ApiModelProperty(value = "The user is authenticated")
    @JsonProperty("authenticated")
    public boolean isAuthenticated() {
        return authenticated;
    }

    @JsonProperty("authenticated")
    public void setAuthenticated(boolean authenticated) {
        this.authenticated = authenticated;
    }

    @ApiModelProperty(value = "The user")
    @JsonProperty("user")
    public Person getPerson(){
        return person;
    }

    @JsonProperty("user")
    public void setUser(Person user){
        this.person = user;
    }
}
