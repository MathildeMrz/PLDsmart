package org.H4212.api;

import jakarta.json.JsonObject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import org.H4212.api.jsonSerializers.*;
import org.H4212.entities.*;
import org.H4212.services.ServiceUser;

@Path("/api/")
public class Authentication {

    @POST
    @Consumes("application/json")
    @Produces("application/json")
    @Path("/auth/doctor")
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

        if(!authenticateUserResponse.isNull()){
            return Response.ok(authenticateUserResponse.toJson()).build();
        }else{
            return Response.status(500).entity("""
                    {
                        "status":"error"
                    }
                    """).build();
        }

    }

    @POST
    @Consumes("application/json")
    @Produces("application/json")
    @Path("/auth/pharmacist")
    public Response authenticatePharmacist(JsonObject jsonObject){

        ServiceUser serviceUser = new ServiceUser();

        AuthenticateUserRequest authenticateUserRequest = new AuthenticateUserRequest(jsonObject);

        Pharmacist pharmacist = new Pharmacist();

        AuthenticatePharmacistResponse authenticateUserResponse = new AuthenticatePharmacistResponse();

        try
        {
            pharmacist = serviceUser.authenticatePharmacist(authenticateUserRequest.getUsername(), authenticateUserRequest.getPassword());
            authenticateUserResponse.setUser(pharmacist);
        }
        catch(Exception e)
        {
            pharmacist = null;
            e.printStackTrace();
        }

        if(!authenticateUserResponse.isNull()){
            return Response.ok(authenticateUserResponse.toJson()).build();
        }else{
            return Response.status(500).entity("""
                    {
                        "status":"error"
                    }
                    """).build();
        }

    }

    @POST
    @Consumes("application/json")
    @Produces("application/json")
    @Path("/auth/admin")
    public Response authenticateAdmin(JsonObject jsonObject){

        ServiceUser serviceUser = new ServiceUser();

        AuthenticateUserRequest authenticateUserRequest = new AuthenticateUserRequest(jsonObject);

        Person admin = new Person();

        AuthenticateAdminResponse authenticateUserResponse = new AuthenticateAdminResponse();

        try
        {
            admin = serviceUser.authenticateAdmin(authenticateUserRequest.getUsername(), authenticateUserRequest.getPassword());
            authenticateUserResponse.setUser(admin);
        }
        catch(Exception e)
        {
            admin = null;
            e.printStackTrace();
        }

        if(!authenticateUserResponse.isNull()){
            return Response.ok(authenticateUserResponse.toJson()).build();
        }else{
            return Response.status(500).entity("""
                    {
                        "status":"error"
                    }
                    """).build();
        }

    }
}
