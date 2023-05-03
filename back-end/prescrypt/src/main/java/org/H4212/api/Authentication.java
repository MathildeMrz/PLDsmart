package org.H4212.api;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import io.swagger.annotations.*;
import org.H4212.api.jsonSerializers.*;
import org.H4212.entities.Person;
import org.H4212.services.ServiceUser;
import org.H4212.util.*;

import java.util.List;

@Path("/api/")
@Api(description = "the authentication API")
public class Authentication {

    @POST
    @Path("/auth/token")
    @Consumes({ "application/json", "application/x-www-form-urlencoded" })
    @Produces({ "application/json" })
    @ApiOperation(value = "Authenticates user and generates an access token", notes = "Validate credentials of a user and, if available, get an access token. ", tags={ "Authentication" })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "An access token was generated successfully. ", response = AuthenticateUserResponse.class),
            @ApiResponse(code = 400, message = "Bad request", response = GetErrorResponse.class),
            @ApiResponse(code = 429, message = "Too Many requests", response = GetErrorResponse.class) })
    public Response authenticate(@Valid @NotNull AuthenticateUserRequest authenticateUserRequest){

        ServiceUser serviceUser = new ServiceUser();

        Person person = new Person();

        AuthenticateUserResponse authenticateUserResponse = new AuthenticateUserResponse();

        try
        {
            person = serviceUser.authenticate(authenticateUserRequest.getUsername(), authenticateUserRequest.getPassword());
            authenticateUserResponse.setAuthenticated(true);
            authenticateUserResponse.setUser(person);
        }
        catch(Exception e)
        {
            person = null;
            e.printStackTrace();
        }

        return ResponseUtil.ok(authenticateUserResponse).build();

    }


}
