package org.H4212.api;

import jakarta.inject.Inject;
import jakarta.json.Json;
import jakarta.json.JsonBuilderFactory;
import jakarta.json.JsonObject;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.H4212.api.jsonSerializers.*;
import org.H4212.entities.Doctor;
import org.H4212.entities.Person;
import org.H4212.services.ServiceUser;
import org.jboss.logging.annotations.Param;

@Path("/api/")
public class Authentication {

    @POST
    @Consumes("application/json")
    @Produces("application/json")
    @Path("/auth")
    public Response authenticateDoctor(JsonObject jsonObject){

        ServiceUser serviceUser = new ServiceUser();

        AuthenticateUserRequest authenticateUserRequest = new AuthenticateUserRequest(jsonObject);

        Doctor doctor = new Doctor();

        AuthenticateDoctorResponse authenticateUserResponse = new AuthenticateDoctorResponse();

        try
        {
            doctor = serviceUser.authenticateDoctor(authenticateUserRequest.getUsername(), authenticateUserRequest.getPassword());
            authenticateUserResponse.setUser(doctor);
        }
        catch(Exception e)
        {
            doctor = null;
            e.printStackTrace();
        }

        return Response.ok(authenticateUserResponse.toJson()).build();

    }


}
