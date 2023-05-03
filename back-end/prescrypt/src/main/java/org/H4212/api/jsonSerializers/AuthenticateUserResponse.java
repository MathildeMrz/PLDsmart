package org.H4212.api.jsonSerializers;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.Valid;
import java.io.Serializable;

public class AuthenticateUserResponse implements Serializable {

    private @Valid boolean authenticated;

    public AuthenticateUserResponse(boolean authenticated) {
        this.authenticated = authenticated;
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
}
