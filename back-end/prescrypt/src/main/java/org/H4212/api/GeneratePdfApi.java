package org.H4212.api;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Response;
import org.H4212.services.ServicePdf;

@Path("/generate-pdf")
public class GeneratePdfApi {
    
    private final ServicePdf servicePdf = new ServicePdf();

    @GET
    @Produces("application/pdf")
    public Response generatePdf(@QueryParam("doctorName") String doctorName) {
        
        try {
            byte[] pdfBytes = servicePdf.generatePdf(doctorName);
            return Response.ok(pdfBytes,"application/pdf").build();

        } catch (Exception e) {
            return Response.serverError().build();
        }
    }
}
