package org.H4212.services;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import javax.imageio.ImageIO;

import org.apache.commons.io.IOUtils;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import java.awt.image.BufferedImage;


public class ServiceOCR {
    public String generateJSON(byte[] image) throws IOException, TesseractException
    {
        Tesseract tesseract = new Tesseract();
        tesseract.setDatapath("C:/Users/zhang/Documents/GitHub/PLDsmart/back-end/prescrypt/src/main/resources/tessdata");
        tesseract.setLanguage("fra");
        try (InputStream inputStream = new ByteArrayInputStream(image)) {
            BufferedImage bufferedImage = ImageIO.read(inputStream);
            System.out.println("is buffered image null? "+(bufferedImage == null));
        if ((bufferedImage.getWidth() == 0) && (bufferedImage.getHeight() == 0)) {
            System.out.println("BufferedImage is empty");}
            String result = tesseract.doOCR(bufferedImage);
            System.out.println("Résultat OCR : "+result);
            // Use the BufferedImage instance as needed...
        } catch (IOException e) {
            // Handle any exceptions that may occur...
        }
        catch (TesseractException e) {
            // capturez l'exception ici, par exemple :
            System.err.println("Erreur lors de la reconnaissance optique de caractères : " + e.getMessage());
        }
        /*try {
            System.out.println("IMAGE STREAM: "+IOUtils.toString(imageStream, StandardCharsets.UTF_8));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
       // File image = new File("./back-end/prescrypt/src/main/java/org/H4212/services/a.png");
        Tesseract tesseract = new Tesseract();
        tesseract.setDatapath("./back-end/prescrypt/src/main/resources/tessdata");
        //System.out.println("chemin courant de Fatma la diva: "+System.getProperty("user.dir"));
        //tesseract.setLanguage("eng");
        tesseract.setPageSegMode(1);
        tesseract.setOcrEngineMode(1);

        // Chargement de l'image à l'aide de Java Image IO
        ImageIO.setUseCache(false);
        BufferedImage bufferedImage;
        String result = "initialized";

        try {
            bufferedImage = ImageIO.read(imageStream);
            result = tesseract.doOCR(bufferedImage);

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (TesseractException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        System.out.println("resulttttttttt "+result);*/
    
        return "";
    }
}
