package org.H4212.api.jsonSerializers;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import org.H4212.services.ServiceUser;

import java.sql.SQLException;

@Path("/api/")
public class Deletion {

    @DELETE
    @Consumes("application/json")
    @Produces("application/json")
    @Path("/delete/doctor/{doctorId}")
    public Response deleteDoctor(@PathParam("doctorId") Long doctorId) throws SQLException {

        ServiceUser serviceUser = new ServiceUser();

        serviceUser.deleteDoctor(doctorId);

        return Response.ok().build();
    }

    @DELETE
    @Consumes("application/json")
    @Produces("application/json")
    @Path("/delete/pharmacist/{pharmacistId}")
    public Response deletePharmacist(@PathParam("pharmacistId") Long pharmacistId) throws SQLException {

        ServiceUser serviceUser = new ServiceUser();

        serviceUser.deletePharmacist(pharmacistId);

        return Response.ok().build();
    }
}
