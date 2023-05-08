package org.H4212.api;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import org.H4212.services.ServicePrescription;
import org.H4212.services.ServiceUser;

import java.sql.SQLException;

@Path("/api/")
public class Deletion {

    @DELETE
    @Produces("application/json")
    @Path("/delete/doctor/{doctorId}")
    public Response deleteDoctor(@PathParam("doctorId") Long doctorId) throws SQLException {

        ServiceUser serviceUser = new ServiceUser();

        serviceUser.deleteDoctor(doctorId);

        return Response.ok().build();
    }

    @DELETE
    @Produces("application/json")
    @Path("/delete/pharmacist/{pharmacistId}")
    public Response deletePharmacist(@PathParam("pharmacistId") long pharmacistId) throws SQLException {

        ServiceUser serviceUser = new ServiceUser();

        serviceUser.deletePharmacist(pharmacistId);

        return Response.ok().build();
    }

    @DELETE
    @Produces("application/json")
    @Path("/delete/doctor/{doctorId}/prescription/{prescriptionId}")
    public Response deletePrescription(@PathParam("doctorId") long doctorId, @PathParam("prescriptionId") long prescriptionId) throws SQLException {
        ServicePrescription servicePrescription = new ServicePrescription();

        servicePrescription.deletePrescription(doctorId, prescriptionId);

        return Response.ok().build();
    }
}
