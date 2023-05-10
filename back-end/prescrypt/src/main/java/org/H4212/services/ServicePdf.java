package org.H4212.services;

import java.io.ByteArrayOutputStream;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;


public class ServicePdf {

    public static final String TAHOMA = "C:/Users/Malob/OneDrive/Documents/4IF/SMART/PLDsmart/back-end/prescrypt/src/main/resources/tahoma.ttf";
    private static BaseFont baseFont = null;

    static {
        try {
            baseFont = BaseFont.createFont(TAHOMA, BaseFont.WINANSI, BaseFont.EMBEDDED);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Font prescriptionFont = new Font(baseFont, 12);

    private static Font prescriptionBoldFont = new Font(baseFont, 12, Font.BOLD, BaseColor.BLACK);
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
        String patientSexe = json.get("patientSexe").toString();
        String patientWeight = json.get("patientWeight").toString();
        String patientHeight = json.get("patientHeight").toString();
        String consultationDate = json.get("prescriptionDate").toString();
        String consultationAddress = json.get("addressPrescription").toString();
        String consultationPhoneNumber = json.get("consultationPhoneNumber").toString();
        String rowsMedicamentsActs = json.get("prescriptions").toString();

        // Afficher la valeur de l'attribut "doctorName"
        
        Paragraph doctorPart = new Paragraph("Nom docteur : "+doctorName+ "; \nQualification : "+doctorJob+"; \nRPPS : "+RPPSNum+"; ", prescriptionBoldFont);
        addEmptyLine(doctorPart, 1);

        //Office
        Paragraph officePart = new Paragraph("Adresse : " + consultationAddress+"; \nTel : "+consultationPhoneNumber+"; ", prescriptionFont);
        addEmptyLine(officePart, 1);

        //Consultation
        Paragraph consultationPart = new Paragraph("Fait le : "+consultationDate+"; ", prescriptionFont);
        addEmptyLine(consultationPart, 1);

        //Patient
        //Check if age, weight, height empty
        String patientString = "Nom patient : "+patientName+";\nPrénom patient : "+patientFirstName+"; \n";
        if(!patientAge.isEmpty())
        {
            patientString += "Age : " +patientAge+" ans; ";
        }


        if(! patientSexe.isEmpty())
        {
            patientString += "\nSexe : " + patientSexe + "; ";
        }

        if(! patientHeight.isEmpty())
        {
            patientString += "\nTaille : "+patientHeight+" cm; ";
        }

        if(! patientWeight.isEmpty())
        {
            patientString += "\nPoids : "+patientWeight+" kg; ";
        }

        Paragraph patientPart = new Paragraph(patientString, prescriptionBoldFont);     
        addEmptyLine(patientPart, 1);   

        //Medicine
        List list = new List(List.UNORDERED);
        list.setListSymbol(bullet);

        JSONArray jsonArray = (JSONArray) parser.parse(rowsMedicamentsActs);

        for (Object element : jsonArray) 
        {
            String medicineAct = ((JSONObject)element).get("medicineAct").toString();
            String posology = ((JSONObject)element).get("posology").toString();
            String treatmentPeriod = ((JSONObject)element).get("treatmentPeriod").toString();
            String treatmentPeriodTexteObj = ((JSONObject)element).get("treatmentPeriodTexte").toString();
            String renewal = ((JSONObject)element).get("renewal").toString();
            String refundable = ((JSONObject)element).get("refundable").toString();
            String indication = ((JSONObject)element).get("indication").toString();
            String row = "Médicament : "+medicineAct+"; \nPosologie : " + posology +"; \nPériode : "+ treatmentPeriod + " " + treatmentPeriodTexteObj;            
            row+= "\nRenouvelable : ";
            boolean refundableBool = Boolean.parseBoolean(refundable);

            if(! renewal.isEmpty())
            {
                row = row +renewal+" fois; ";
            }          

            row+= "\nRemboursable : ";
            if(refundableBool){
                row += "Non; ";
            }
            else {
                row += "Oui; ";
            }
            if(! indication.isEmpty())
            {
                row+="\nIndication : "+indication+"; \n\n";
            } 

            ListItem item = new ListItem(row, prescriptionFont);
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
