package org.H4212.api.jsonSerializers;

import jakarta.validation.*;
import java.io.Serializable;

public class GetErrorResponse  implements Serializable {

    private @Valid Error error;

    /**
     *
     **/
    public GetErrorResponse error(Error error) {
        this.error = error;
        return this;
    }

    public Error getError() {
        return error;
    }

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

