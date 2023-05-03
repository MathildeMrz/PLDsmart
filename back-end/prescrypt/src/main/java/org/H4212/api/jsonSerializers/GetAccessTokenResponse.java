package org.H4212.api.jsonSerializers;

import javax.validation.Valid;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.*;
import io.swagger.annotations.*;

public class GetAccessTokenResponse implements Serializable {

    private @Valid String accessToken;
    private @Valid String refreshToken;
    private @Valid String tokenType;
    private @Valid Long expiresIn;

    /**
     * The generated access token.
     **/
    public GetAccessTokenResponse accessToken(String accessToken) {
        this.accessToken = accessToken;
        return this;
    }

    @ApiModelProperty(value = "The generated access token.")
    @JsonProperty("access_token")
    public String getAccessToken() {
        return accessToken;
    }

    @JsonProperty("access_token")
    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    /**
     * The refresh token.
     **/
    public GetAccessTokenResponse refreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
        return this;
    }


    @ApiModelProperty(value = "The refresh token.")
    @JsonProperty("refresh_token")
    public String getRefreshToken() {
        return refreshToken;
    }

    @JsonProperty("refresh_token")
    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    /**
     * The type of token, for example &#x60;bearer&#x60;.
     **/
    public GetAccessTokenResponse tokenType(String tokenType) {
        this.tokenType = tokenType;
        return this;
    }


    @ApiModelProperty(value = "The type of token, for example `bearer`.")
    @JsonProperty("token_type")
    public String getTokenType() {
        return tokenType;
    }

    @JsonProperty("token_type")
    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    /**
     * The number of seconds until the token expires.
     **/
    public GetAccessTokenResponse expiresIn(Long expiresIn) {
        this.expiresIn = expiresIn;
        return this;
    }


    @ApiModelProperty(value = "The number of seconds until the token expires.")
    @JsonProperty("expires_in")
    public Long getExpiresIn() {
        return expiresIn;
    }

    @JsonProperty("expires_in")
    public void setExpiresIn(Long expiresIn) {
        this.expiresIn = expiresIn;
    }
}
