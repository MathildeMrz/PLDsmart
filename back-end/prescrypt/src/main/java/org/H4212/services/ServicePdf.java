package org.H4212.services;

import java.io.ByteArrayOutputStream;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.List;
import com.itextpdf.text.ListItem;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

public class ServicePdf {

    private static Font prescriptionFont = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL, BaseColor.BLACK);
    private static Font prescriptionBoldFont = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD, BaseColor.BLACK);
    private static Chunk bullet = new Chunk("\u2022", prescriptionFont);

    public byte[] generatePdf(String jsonPdf) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        
        try {
            Document document = new Document();
            PdfWriter.getInstance(document, outputStream);
            document.open();
            addMetaData(document);
            addContent(document, jsonPdf);
            document.close();            
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return outputStream.toByteArray();
    }

    private static void addMetaData(Document document) {
        document.addTitle("Ordonnance Prescrypt");
        document.addCreator("Prescrypt");
    }

    private static void addContent(Document document, String jsonPdf) throws DocumentException, ParseException {

        // Convertir la chaîne JSON en objet JsonObject
        JSONParser parser = new JSONParser(); 
        JSONObject json = (JSONObject) parser.parse(jsonPdf);


        // Récupérer la valeur de l'attribut "doctorName"
        String doctorName = json.get("doctorName").toString();
        String doctorJob = json.get("doctorJob").toString();
        String RPPSNum = json.get("RPPSNum").toString();
        String patientName = json.get("patientName").toString();
        String patientFirstName = json.get("patientFirstName").toString();
        String patientAge = json.get("patientAge").toString();
        String patientWeight = json.get("patientWeight").toString();
        String patientHeight = json.get("patientHeight").toString();
        String consultationDate = json.get("prescriptionDate").toString();
        String consultationAddress = json.get("addressPrescription").toString();
        String consultationPhoneNumber = json.get("consultationPhoneNumber").toString();
        String rowsMedicamentsActs = json.get("prescriptions").toString();

        // Afficher la valeur de l'attribut "doctorName"
        
        Paragraph doctorPart = new Paragraph(doctorName+ "\n"+doctorJob+"\nRPPS : "+RPPSNum, prescriptionBoldFont);
        addEmptyLine(doctorPart, 2);

        //Office
        Paragraph officePart = new Paragraph(consultationAddress+"\nTel : "+consultationPhoneNumber, prescriptionFont);
        addEmptyLine(officePart, 2);

        //Consultation
        Paragraph consultationPart = new Paragraph("Fait le : "+consultationDate, prescriptionFont);
        addEmptyLine(consultationPart, 2);

        //Patient
        //Check if age, weight, height empty
        String patientString = patientName+" "+patientFirstName+"\n";
        if(! patientAge.isEmpty())
        {
            patientString += patientAge+" ans ";
        }

        if(! patientHeight.isEmpty())
        {
            patientString += patientHeight+" cm ";
        }

        if(! patientWeight.isEmpty())
        {
            patientString += patientWeight+" kg ";
        }

        Paragraph patientPart = new Paragraph(patientString, prescriptionBoldFont);
        

        //Medicine
        List list = new List(List.UNORDERED);
        list.setListSymbol(bullet);

        
        JSONArray jsonArray = (JSONArray) parser.parse(rowsMedicamentsActs);

        for (Object element : jsonArray) 
        {
            String medicineAct = ((JSONObject)element).get("medicineAct").toString();
            String posology = ((JSONObject)element).get("posology").toString();
            String treatmentPeriod = ((JSONObject)element).get("treatmentPeriod").toString();
            String renewal = ((JSONObject)element).get("renewal").toString();
            String refundable = ((JSONObject)element).get("refundable").toString();
            String indication = ((JSONObject)element).get("indication").toString();            
            String row = medicineAct+"\n" + posology +" pour une période de "+ treatmentPeriod;
            int renewalInt = Integer.parseInt(renewal);
            boolean refundableBool = Boolean.parseBoolean(refundable);
            if(renewalInt == 0){
                row +=" non renouvelable, ";
            }
            else{
                row = row +" renouvelable "+renewal+" fois,";

            }
            if(refundableBool){
                row += "non remboursable.";
            }
            else {
                row += "remboursable.";
            }
            row+="\nIndication: "+indication;

            ListItem item = new ListItem(row, prescriptionBoldFont);
            list.add(item);
        }
                
        document.add(doctorPart);
        document.add(officePart);
        document.add(consultationPart);
        document.add(patientPart);
        document.add(list);

    }

    private static void addEmptyLine(Paragraph paragraph, int number) {
        for (int i = 0; i < number; i++) {
            paragraph.add(new Paragraph(" "));
        }
    }
    
}
