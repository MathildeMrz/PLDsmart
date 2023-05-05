package org.H4212.api;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Response;
import java.io.InputStream;
import org.H4212.services.ServiceOCR;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

@Path("/OCR-api")
public class OCRApi {

    private final ServiceOCR serviceOCR = new ServiceOCR();

    @POST
    @Path("/")
    public Response generateJsonOCR(@FormDataParam("image") InputStream imageStream) 
    {
        //System.out.println("imageStreammmmm : "+imageStream.toString());
        
        // Utilisez les paramètres récupérés pour générer votre PDF
        try {
            System.out.println("Avant On est là");
            String resultOCRJson = serviceOCR.generateJSON(imageStream);
            return Response.ok(resultOCRJson).build();

        } catch (Exception e) {
            return Response.serverError().build();
        }
    }
}