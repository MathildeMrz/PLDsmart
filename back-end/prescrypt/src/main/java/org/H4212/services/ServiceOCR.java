package org.H4212.services;

import javax.imageio.ImageIO;

import org.json.JSONObject;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.*;
import net.sourceforge.tess4j.*;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.*;
import java.io.*;
  


public class ServiceOCR {
    public String generateJSON(MultipartFile file)
    {
        try {
            BufferedImage bufferedImage = ImageIO.read(file.getInputStream());
            
            ITesseract tesseract = new Tesseract();
            tesseract.setDatapath("/usr/share/tesseract-ocr/4.00/tessdata");
            String result = tesseract.doOCR(bufferedImage);
            
            System.out.println("result = "+result);
            //String to json
            return result;
        } 
        catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}
