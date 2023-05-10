package org.H4212.api;

import org.H4212.entities.Doctor;
import org.H4212.services.ServiceUser;
import jakarta.ws.rs.core.Response;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;

import java.sql.SQLException;

@Path("/Doctor-api")
public class DoctorApi {
    private final ServiceUser serviceUser = new ServiceUser();

    @GET
    @Path("/{doctorId}")
    public Response generateJsonOCR(@PathParam("doctorId") long doctorId) throws SQLException
    {   
        Doctor doc = serviceUser.getDoctor(doctorId);
        
        // créer un objet JSON contenant les informations de l'objet Doctor
        JsonObject jsonResponse = Json.createObjectBuilder()
            .add("firstName", doc.getFirstName())
            .add("lastName", doc.getLastName())
            .add("telephone", doc.getTelephone())
            .add("qualification", doc.getQualification())
            .add("officeAddress", doc.getOfficeAddress())
            .add("idPSdoctor", doc.getIdPSdoctor())
            .build();

        System.out.println(jsonResponse);

        // renvoyer la réponse JSON
        return Response.ok(jsonResponse).build();
    }
}
