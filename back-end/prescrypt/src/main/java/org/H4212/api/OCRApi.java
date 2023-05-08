package org.H4212.api;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import java.awt.Image;
import java.awt.image.BufferedImage;

import org.H4212.services.ServiceOCR;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.glassfish.jersey.media.multipart.file.FileDataBodyPart;
import org.hibernate.boot.Metadata;

@Path("/OCR-api")
public class OCRApi {

    //private final ServiceOCR serviceOCR = new ServiceOCR();

    @POST
    @Consumes({MediaType.MULTIPART_FORM_DATA})
    public Response generateJsonOCR(@FormDataParam("file") FileDataBodyPart imageStream) throws IOException 
    {
        //Image img = ImageIO.read(imageStream);
        System.out.println(imageStream.toString());
        
        // Utilisez les paramètres récupérés pour générer votre PDF
        try {
            return Response.ok("").build();

        } catch (Exception e) {
            return Response.serverError().build();
        }
    }
}