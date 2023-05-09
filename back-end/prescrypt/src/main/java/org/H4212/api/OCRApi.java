package org.H4212.api;

import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import net.sourceforge.tess4j.TesseractException;

import java.io.IOException;
import org.H4212.services.ServiceOCR;

@Path("/OCR-api")
public class OCRApi {

 private final ServiceOCR serviceOCR = new ServiceOCR();

    @POST
    @Path("/")
    public Response generateJsonOCR(byte[] image) throws IOException, TesseractException 
    {   
      
        String resultOCRJson = serviceOCR.generateJSON(image);

        return Response.ok(resultOCRJson).build();
        /*
        System.out.println("Ok");
        System.out.println("imageStream est de taille : "+imageStream.available() + " octets");
        System.out.println("imagestream est null : "+(imageStream == null));

        ImageReader imageReader = ImageIO.getImageReadersByFormatName("png").next();

        ImageInputStream stream = ImageIO.createImageInputStream(imageStream);
        imageReader.setInput(stream);

        System.out.println("imageReader == null : "+(imageReader == null));
        System.out.println("Nombre d'images dans le flux : " + imageReader.getNumImages(true));
        
        try {
            BufferedImage bufferedImage = imageReader.read(0);
            System.out.println("bufferedImage == null : " + (bufferedImage == null));
        } catch (IOException e) 
        {
            System.out.println("Erreur lors de la lecture de l'image : " + e.getMessage());
        }
        */
        //BufferedImage bufferedImage = imageReader.read(0);

        //System.out.println("bufferedImage == null : "+(bufferedImage == null));
       // System.out.println("bufferedImage.getWidth() : "+  bufferedImage.getWidth());


        /*ByteArrayOutputStream buffer = new ByteArrayOutputStream();

        int nRead;
        byte[] data = new byte[1024];

        while ((nRead = imageStream.read(data, 0, data.length)) != -1) {
            buffer.write(data, 0, nRead);
        }

        buffer.flush();

        System.out.println("buffer.size() : "+  buffer.size());
        System.out.println("buffer == null: "+ buffer == null);

        byte[] imageBytes = buffer.toByteArray();

        System.out.println("imageBytes == null: "+ imageBytes == null);
        System.out.println("imageBytes est de taille : "+  imageBytes.length + " octets");

        ByteArrayInputStream bais = new ByteArrayInputStream(imageBytes);

        System.out.println("bais == null: "+ bais == null);
        System.out.println("bais est de taille : "+bais.available() + " octets");

        BufferedImage bufferedImage = ImageIO.read(bais);
        System.out.println("bufferedImage == null : "+(bufferedImage == null));
        System.out.println(" bufferedImage.getWidth() : "+  bufferedImage.getWidth());

        

       

        if (bufferedImage == null || bufferedImage.getWidth() == 0 || bufferedImage.getHeight() == 0) {
            throw new IllegalArgumentException("Image vide ou null !");
        }







       BufferedInputStream bufferedInputStream = new BufferedInputStream(imageStream);
        System.out.println("bufferedInputStream de taille : " + bufferedInputStream.available() + " octets");
        System.out.println("bufferedInputStream est null : " + (bufferedInputStream == null));*/


        //serviceOCR.generateJSON(bufferedInputStream);        


        //System.out.println("Type de contenu : " + URLConnection.guessContentTypeFromStream(bufferedInputStream));


        // Utilisez les paramètres récupérés pour générer votre PDF
        //try {
           //String contents = IOUtils.toString(imageStream, StandardCharsets.UTF_8);
           // System.out.println(contents);
           // System.out.println("Avant On est là");
           // String resultOCRJson = serviceOCR.generateJSON(imageStream);
           // return Response.ok(resultOCRJson).build();

        /*} catch (Exception e) {
            return Response.serverError().build();
        }*/
    }
}