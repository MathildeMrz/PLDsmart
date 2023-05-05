package org.H4212.services;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import java.awt.image.BufferedImage;

public class ServiceOCR {
    public String generateJSON(InputStream imageStream)
    {
        File image = new File("C:/Users/LENOVO/Desktop/pld_smart/PLDsmart/back-end/prescrypt/src/main/java/org/H4212/services/a.png");
        Tesseract tesseract = new Tesseract();
        tesseract.setDatapath("C:/Users/LENOVO/Desktop/pld_smart/PLDsmart/back-end/prescrypt/src/main/resources/tessdata");
        System.out.println("chemin courant de Fatma la diva: "+System.getProperty("user.dir"));
        //tesseract.setLanguage("eng");
        tesseract.setPageSegMode(1);
        tesseract.setOcrEngineMode(1);

        // Chargement de l'image à l'aide de Java Image IO
        ImageIO.setUseCache(false);
        BufferedImage bufferedImage;
        String result = "initialized";

        try {
            bufferedImage = ImageIO.read(image);
            result = tesseract.doOCR(bufferedImage);

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (TesseractException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        System.out.println("resulttttttttt "+result);
    
        return "";
    }
}
