package org.H4212.api;

import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import net.sourceforge.tess4j.TesseractException;

import java.io.IOException;
import org.H4212.services.ServiceOCR;
import org.json.JSONObject;

@Path("/OCR-api")
public class OCRApi {

 private final ServiceOCR serviceOCR = new ServiceOCR();

    @POST
    @Produces("application/json")
    @Path("/")
    public Response generateJsonOCR(byte[] image) throws IOException, TesseractException 
    {   
        JSONObject resultOCRJson = serviceOCR.generateJSON(image);
        System.out.println("API-OCR : "+resultOCRJson);
        if(!resultOCRJson.isEmpty()){
            return Response.ok(resultOCRJson.toString()).build();
        }else{
            return Response.status(500).entity(resultOCRJson).build();
        }
    }
}