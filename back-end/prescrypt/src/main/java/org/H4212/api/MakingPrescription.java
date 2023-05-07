package org.H4212.api;

import jakarta.json.Json;
import jakarta.json.*;
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

        getPrescriptionsResponseList = servicePrescription.getPrescriptions(doctorId);

        if(!getPrescriptionsResponseList.isEmpty()){
            JsonArrayBuilder jsonBuilder = Json.createBuilderFactory(null).createArrayBuilder();
            for(GetPrescriptionResponse getPrescriptionResponse : getPrescriptionsResponseList)
            {
                jsonBuilder.add(getPrescriptionResponse.getJsonObjectBuilder());
            }
            return Response.ok(jsonBuilder.build()).build();
        }else{
            return Response.serverError().build();
        }
    }
}
