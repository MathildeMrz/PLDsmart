package org.H4212.api.jsonSerializers;

import jakarta.json.JsonObject;

public class AuthenticateUserRequest {

    private String username;
    private String password;

    public AuthenticateUserRequest(JsonObject jsonObject)
    {
        this.username = jsonObject.getString("username");
        this.password = jsonObject.getString("password");
    }

    public AuthenticateUserRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public AuthenticateUserRequest(){

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
