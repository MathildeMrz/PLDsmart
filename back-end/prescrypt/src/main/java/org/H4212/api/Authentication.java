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
import org.H4212.entities.Person;
import org.H4212.services.ServiceUser;
import org.jboss.logging.annotations.Param;

@Path("/api/")
public class Authentication {

    @POST
    @Consumes("application/json")
    @Produces("application/json")
    @Path("/auth")
    public Response authenticate(JsonObject jsonObject){

        ServiceUser serviceUser = new ServiceUser();

        AuthenticateUserRequest authenticateUserRequest = new AuthenticateUserRequest(jsonObject);

        Person person = new Person();

        AuthenticateUserResponse authenticateUserResponse = new AuthenticateUserResponse();

        try
        {
            person = serviceUser.authenticate(authenticateUserRequest.getUsername(), authenticateUserRequest.getPassword());
            authenticateUserResponse.setUser(person);
        }
        catch(Exception e)
        {
            person = null;
            e.printStackTrace();
        }

        /*JsonBuilderFactory factory = Json.createBuilderFactory(null);
        JsonObject value = factory.createObjectBuilder()
                .add("firstName", "John")
                .add("lastName", "Smith").build();*/

        return Response.ok(authenticateUserResponse.toJson()).build();

    }


}
