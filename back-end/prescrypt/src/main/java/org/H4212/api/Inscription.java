package org.H4212.api;

import jakarta.json.JsonObject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Response;
import org.H4212.api.jsonSerializers.*;
import org.H4212.services.ServiceUser;

import java.sql.SQLException;

@Path("/api/")
public class Inscription {

    @POST
    @Consumes("application/json")
    @Produces("application/json")
    @Path("/register/doctor")
    public Response registerDoctor(JsonObject jsonObject) throws SQLException {

        ServiceUser serviceUser = new ServiceUser();

        RegisterDoctorRequest registerUserRequest = new RegisterDoctorRequest(jsonObject);

        serviceUser.registerDoctor(registerUserRequest);

        return Response.ok().build();
    }

    @POST
    @Consumes("application/json")
    @Produces("application/json")
    @Path("/register/pharmacist")
    public Response registerPharmacist(JsonObject jsonObject) throws SQLException {

        ServiceUser serviceUser = new ServiceUser();

        RegisterPharmacistRequest registerUserRequest = new RegisterPharmacistRequest(jsonObject);

        serviceUser.registerPharmacist(registerUserRequest);

        return Response.ok().build();
    }

}
