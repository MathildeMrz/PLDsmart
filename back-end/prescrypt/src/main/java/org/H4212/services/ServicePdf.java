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

    public static final String ROBOTO = "src/main/resources/tahoma.ttf";

    private static BaseFont baseFont = null;

    static {
        try {
            baseFont = BaseFont.createFont(ROBOTO, BaseFont.WINANSI, BaseFont.EMBEDDED);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Font prescriptionFont = new Font(baseFont, 14);
    private static Font prescriptionBoldFont = new Font(baseFont, 14, Font.BOLD, BaseColor.BLACK);
    //private static Chunk bullet = new Chunk("\u2022", prescriptionFont);

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
        String validityPrescriptionDays = json.get("validityPrescriptionDays").toString();

        // Afficher la valeur de l'attribut "doctorName"
        
        Paragraph doctorPart = new Paragraph("Nom docteur : "+doctorName+ "; \nQualification : "+doctorJob+"; \nRPPS : "+RPPSNum+"; ", prescriptionBoldFont);
        addEmptyLine(doctorPart, 1);

        //Office
        Paragraph officePart = new Paragraph("Adresse : " + consultationAddress+"; \nTel : "+consultationPhoneNumber+"; ", prescriptionFont);
        addEmptyLine(officePart, 1);

        //Consultation
        Paragraph consultationPart = new Paragraph("Fait le : "+consultationDate+";\nDurée de validité : "+validityPrescriptionDays+" jours;", prescriptionFont);
        addEmptyLine(consultationPart, 1);

        //Patient
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

        JSONArray jsonArray = (JSONArray) parser.parse(rowsMedicamentsActs);

        document.add(doctorPart);
        document.add(officePart);
        document.add(consultationPart);
        document.add(patientPart);

        for (Object element : jsonArray) 
        {
            String medicineAct = ((JSONObject)element).get("medicineAct").toString();
            String posology = ((JSONObject)element).get("posology").toString();
            String treatmentPeriod = ((JSONObject)element).get("treatmentPeriod").toString();
            String treatmentPeriodTexteObj = ((JSONObject)element).get("treatmentPeriodTexte").toString();
            String renewal = ((JSONObject)element).get("renewal").toString();
            String refundable = ((JSONObject)element).get("refundable").toString();
            String indication = ((JSONObject)element).get("indication").toString();
            String row = "Médicament : "+medicineAct+"; \nPosologie : " + posology +"; \nPériode : "+ treatmentPeriod + " " + treatmentPeriodTexteObj+";";            
            row+= "\nRenouvelable : ";
            boolean refundableBool = Boolean.parseBoolean(refundable);

            if((! renewal.isEmpty()) && (renewal.equals("0")))
            {
                row = row +"Aucune fois;";
            } 
            else if(! renewal.isEmpty())
            {
                row = row +renewal+" fois;";
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

            Paragraph medicinePart = new Paragraph(row, prescriptionFont);     
            addEmptyLine(patientPart, 1);   
            document.add(medicinePart);
        }
    }

    private static void addEmptyLine(Paragraph paragraph, int number) {
        for (int i = 0; i < number; i++) {
            paragraph.add(new Paragraph(" "));
        }
    }
    
}
