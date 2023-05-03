package org.H4212.api.jsonSerializers;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.Valid;
import java.io.Serializable;

public class AuthenticateUserRequest implements Serializable {

    private @Valid String username;
    private @Valid String password;

    public AuthenticateUserRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @ApiModelProperty(value = "The user name. ")
    @JsonProperty("username")
    public String getUsername() {
        return username;
    }

    @JsonProperty("username")
    public void setUsername(String username) {
        this.username = username;
    }

    @ApiModelProperty(value = "The user password or one time password. ")
    @JsonProperty("password")
    public String getPassword() {
        return password;
    }

    @JsonProperty("password")
    public void setPassword(String password) {
        this.password = password;
    }

}
