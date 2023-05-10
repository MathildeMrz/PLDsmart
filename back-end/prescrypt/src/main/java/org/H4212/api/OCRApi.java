package org.H4212.api;

import jakarta.json.JsonObject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import net.sourceforge.tess4j.TesseractException;
import java.io.*;
import org.H4212.services.*;
import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.io.RandomAccessBuffer;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.json.JSONObject;

@Path("/OCR-api")
public class OCRApi {

 private final ServiceOCR serviceOCR = new ServiceOCR();
 private final ServiceParser serviceParser = new ServiceParser();

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

    @POST
    @Produces("application/json")
    @Path("/pdf")
    public Response parsePdfToJson(byte[] pdf) throws IOException, TesseractException
    {
        //File f = new File("C:/Users/Malob/OneDrive/Documents/4IF/SMART/PLDsmart/back-end/prescrypt/src/main/resources/input/file.pdf");
        String parsedText;
        PDFParser parser = new PDFParser(new RandomAccessBuffer(pdf));
        parser.parse();
        COSDocument cosDoc = parser.getDocument();
        PDFTextStripper pdfStripper = new PDFTextStripper();
        PDDocument pdDoc = new PDDocument(cosDoc);
        parsedText = pdfStripper.getText(pdDoc);
        PrintWriter pw = new PrintWriter("C:/Users/Malob/OneDrive/Documents/4IF/SMART/PLDsmart/back-end/prescrypt/src/main/resources/output/file.txt");
        pw.print(parsedText);
        pw.close();
        
        JSONObject resultOCRJson = serviceParser.parseTxtToJSON(parsedText);
        System.out.println("API-OCR : "+resultOCRJson);
        if(!resultOCRJson.isEmpty()){
            return Response.ok(resultOCRJson.toString()).build();
        }else{
            return Response.status(500).entity(resultOCRJson).build();
        }
    }
}