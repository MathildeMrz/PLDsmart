package org.H4212.api;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Response;

import org.H4212.services.ServiceOCR;
import org.springframework.web.multipart.MultipartFile;
@Path("/OCRApi")
public class OCRApi {

    private final ServiceOCR serviceOCR = new ServiceOCR();

    @GET
    @Produces("application/json")
    public Response generateJsonOCR(@QueryParam("scanFile") MultipartFile file) 
    {
        
        // Utilisez les paramètres récupérés pour générer votre PDF
        try {
            String resultOCRJson = serviceOCR.generateJSON(file);
            return Response.ok(resultOCRJson,"application/json").build();

        } catch (Exception e) {
            return Response.serverError().build();
        }
    }
}