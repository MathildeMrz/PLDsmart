package org.H4212.services;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
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

    private static String FILE = "newPdf.pdf";
    private static Font prescriptionFont = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL, BaseColor.BLACK);
    private static Font prescriptionBoldFont = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD, BaseColor.BLACK);
    private static Chunk bullet = new Chunk("\u2022", prescriptionFont);

    public byte[] generatePdf(String doctorName) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        
        try {
            Document document = new Document();
            PdfWriter.getInstance(document, outputStream);
            document.open();
            addMetaData(document);
            addContent(document, doctorName);
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

    private static void addContent(Document document, String doctorName) throws DocumentException {

        //Doctor
        String doctor = doctorName;
        Paragraph doctorPart = new Paragraph(doctor, prescriptionBoldFont);
        addEmptyLine(doctorPart, 2);

        //Office
        String office = "1, rue de la République \n 13 000 Marseille \n Tél cabinet : 04 01 94 58 98";
        Paragraph officePart = new Paragraph(office, prescriptionFont);
        addEmptyLine(officePart, 2);

        //Consultation
        String consultation = "Marseille, le 04 mai 2023";
        Paragraph consultationPart = new Paragraph(consultation, prescriptionFont);
        addEmptyLine(consultationPart, 2);

        //Patient
        String patient = "Madame DUPOND Nathalie \n 57 ans, 64kg";
        Paragraph patientPart = new Paragraph(patient, prescriptionBoldFont);
        addEmptyLine(patientPart, 2);

        //Medicine
        List list = new List(List.UNORDERED);
		list.setListSymbol(bullet);
        String medicine1 = "Médicament A, 1 gélule matin, midi et soir pendant 4 jours - Non substituable \n";
        String medicine2 = "Médicament B, 1 gélule matin, midi et soir pendant 4 jours - Non substituable";
        list.add(new ListItem(medicine1));
        list.add(new ListItem(medicine2));
                
        //Note
        String note = "Eviter les aliments salés, ne pas ajouter de sel dans la préparation des plats";
        Paragraph notePart = new Paragraph(note, prescriptionFont);

        document.add(doctorPart);
        document.add(officePart);
        document.add(consultationPart);
        document.add(patientPart);
        document.add(list);
        document.add(notePart);
    }

    private static void addEmptyLine(Paragraph paragraph, int number) {
        for (int i = 0; i < number; i++) {
            paragraph.add(new Paragraph(" "));
        }
    }
    
}
