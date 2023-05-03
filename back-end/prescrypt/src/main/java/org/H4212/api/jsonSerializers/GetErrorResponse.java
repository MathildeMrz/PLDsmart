package org.H4212.api.jsonSerializers;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.Valid;
import java.io.Serializable;

@JsonTypeName("GetErrorResponse")
public class GetErrorResponse  implements Serializable {

    private @Valid Error error;

    /**
     *
     **/
    public GetErrorResponse error(Error error) {
        this.error = error;
        return this;
    }


    @ApiModelProperty(value = "")
    @JsonProperty("error")
    public Error getError() {
        return error;
    }

    @JsonProperty("error")
    public void setError(Error error) {
        this.error = error;
    }

    @Override
    public String toString() {
        return "GetErrorResponse{" +
                "error=" + error +
                '}';
    }
}

