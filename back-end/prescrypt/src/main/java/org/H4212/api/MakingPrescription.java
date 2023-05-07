package org.H4212.api;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import org.H4212.api.jsonSerializers.*;
import org.H4212.services.*;
import java.sql.SQLException;
import java.util.*;

@Path("/api/")
public class MakingPrescription {

    @GET
    @Produces("application/json")
    @Path("/prescription/doctor/{doctorId}")
    public Response getPrescriptions(@PathParam("doctorId") Long doctorId) throws SQLException {

        ServicePrescription servicePrescription = new ServicePrescription();

        List<GetPrescriptionResponse> getPrescriptionsResponseList;

        return Response.ok().build();
    }
}
