package org.H4212.services;

import java.io.File;
import java.io.InputStream;

import javax.imageio.ImageIO;

import net.sourceforge.tess4j.Tesseract;

public class ServiceOCR {
    public String generateJSON(InputStream imageStream)
    {
        try {
            // Charger l'image
            Tesseract tesseract = new Tesseract();
            File imageFile = File.createTempFile("temp", null);
            ImageIO.write(ImageIO.read(imageStream), "png", imageFile);

            // Extraire du texte à partir de l'image
            String result = tesseract.doOCR(imageFile);      
            System.out.println("Très important : "+result);  

            // Supprimer le fichier temporaire
            imageFile.delete();

            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}
