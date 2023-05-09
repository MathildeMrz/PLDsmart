
package org.H4212.api;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Response;
import org.H4212.services.ServicePdf;
import org.jboss.logging.annotations.Param;

@Path("/generate-pdf")
public class GeneratePdfApi {
    
    private final ServicePdf servicePdf = new ServicePdf();

    @GET
    @Produces("application/pdf")
    public Response generatePdf(@QueryParam("jsonPdf") String jsonPdf) 
    {
        
        // Utilisez les paramètres récupérés pour générer votre PDF
        try {
            byte[] pdfBytes = servicePdf.generatePdf(jsonPdf);
            return Response.ok(pdfBytes,"application/pdf").build();

        } catch (Exception e) {
            return Response.serverError().build();
        }
    }
}