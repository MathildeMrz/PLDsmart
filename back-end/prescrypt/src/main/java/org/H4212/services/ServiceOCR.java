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
        String result = null;
        Tesseract tesseract = new Tesseract();
        //tesseract.setDatapath("C:/Users/33660/Documents/PLD_SMART/PLDsmart/back-end/prescrypt/src/main/resources/tessdata");//datapath chez Mathilde
        tesseract.setDatapath("C:/Users/zhang/Documents/GitHub/PLDsmart/back-end/prescrypt/src/main/resources/tessdata");//datapath chez Yi
        tesseract.setLanguage("fra");
        try (InputStream inputStream = new ByteArrayInputStream(image)) {
            BufferedImage bufferedImage = ImageIO.read(inputStream);
            result = tesseract.doOCR(bufferedImage);
            System.out.println("Résultat OCR : "+result);
            // Use the BufferedImage instance as needed...
        } catch (IOException e) {
            // Handle any exceptions that may occur...
        }
        catch (TesseractException e) {
            // capturez l'exception ici, par exemple :
            System.err.println("Erreur lors de la reconnaissance optique de caractères : " + e.getMessage());
        }
   
        return result;
    }
}
