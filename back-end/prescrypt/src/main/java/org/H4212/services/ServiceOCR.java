package org.H4212.services;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.imageio.ImageIO;

import org.json.JSONArray;
import org.json.JSONObject;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import java.awt.image.BufferedImage;


public class ServiceOCR {
    public JSONObject generateJSON(byte[] image) throws IOException, TesseractException
    {
        String result = null;
        Tesseract tesseract = new Tesseract();
        tesseract.setDatapath("C:/Users/33660/Documents/PLD_SMART/PLDsmart/back-end/prescrypt/src/main/resources/tessdata");//datapath chez Mathilde
        //tesseract.setDatapath("C:/Users/zhang/Documents/GitHub/PLDsmart/back-end/prescrypt/src/main/resources/tessdata");//datapath chez Yi
        tesseract.setLanguage("fra");
        tesseract.setVariable("user_words_suffix", ".user-words");
        //tesseract.setVariable("user_words_file", "C:/Users/33660/Documents/PLD_SMART/PLDsmart/back-end/prescrypt/src/main/resources/tessdata/dictionaries/fra.user-words");
        JSONObject prescriptionJson = new JSONObject();
        

        try (InputStream inputStream = new ByteArrayInputStream(image)) {
            BufferedImage bufferedImage = ImageIO.read(inputStream);
            result = tesseract.doOCR(bufferedImage);
            System.out.println("result : "+result);

            result = result.replaceAll("\n", "");

            /* Médecin */            
            //Nom
            Pattern nomDocteurPattern = Pattern.compile("Nom docteur\s*:\s*(.+?);");
            Matcher nomDocteurMatcher = nomDocteurPattern.matcher(result);
            if (nomDocteurMatcher.find()) {
                String nomDocteur = nomDocteurMatcher.group(1);
                prescriptionJson.put("NomDocteur", nomDocteur);
            }
            else
            {
                prescriptionJson.put("NomDocteur", "");
            }

            //Qualification
            Pattern qualificationPattern = Pattern.compile("Qualification\s*:\s*(.+?);");
            Matcher qualificationMatcher = qualificationPattern.matcher(result);
            String qualification = "";
            if (qualificationMatcher.find()) {
                qualification = qualificationMatcher.group(1);
            }
            prescriptionJson.put("Qualification", qualification);
            
            //RPPS
            Pattern rppsPattern = Pattern.compile("RPPS\s*:\s*(.+?);");
            Matcher rppsMatcher = rppsPattern.matcher(result);
            String rpps = "";
            if (rppsMatcher.find()) {
                rpps = rppsMatcher.group(1);
            }
            prescriptionJson.put("RPPS", rpps);

            //Adresse
            Pattern adressePattern = Pattern.compile("Adresse\s*:\s*(.+?);");
            Matcher adresseMatcher = adressePattern.matcher(result);
            String adresse = "";
            if (adresseMatcher.find()) {
                adresse = adresseMatcher.group(1);
            }
            prescriptionJson.put("Adresse", adresse);

            //Tel
            Pattern telPattern = Pattern.compile("Tel\s*:\s*(.+?);");
            Matcher telMatcher = telPattern.matcher(result);
            String tel = "";
            if (telMatcher.find()) {
                tel = telMatcher.group(1);
            }
            prescriptionJson.put("Tel", tel);

            /* Consultation */
            //Date
            Pattern datePattern = Pattern.compile("Fait le\s*:\s*(.+?);");
            Matcher dateMatcher = datePattern.matcher(result);
            String date = "";
            if (dateMatcher.find()) {
                date = dateMatcher.group(1);
            }
            prescriptionJson.put("Date", date);

            /* Patient */
            Pattern nomPatientPattern = Pattern.compile("Nom patient\s*:\s*(.+?);");
            Matcher nomPatientMatcher = nomPatientPattern.matcher(result);
            String nomPatient = "";
            if (nomPatientMatcher.find()) {
                nomPatient = nomPatientMatcher.group(1);
            }
            prescriptionJson.put("NomPatient", nomPatient);

            /* Prénom */
            Pattern prenomPatientPattern = Pattern.compile("Prénom patient\s*:\s*(.+?);");
            Matcher prenomPatientMatcher = prenomPatientPattern.matcher(result);
            String prenomPatient = "";
            if (prenomPatientMatcher.find()) {
                prenomPatient = prenomPatientMatcher.group(1);
            }
            prescriptionJson.put("PrenomPatient", prenomPatient);

            /* Age */
            Pattern agePattern = Pattern.compile("Age\s*:\s*(\\d+)");
            Matcher ageMatcher = agePattern.matcher(result);
            String age = "";
            if (ageMatcher.find()) {
                age = ageMatcher.group(1);
            }
            prescriptionJson.put("Age", age);

            /* Sexe */
            Pattern sexePattern = Pattern.compile("Sexe\s*:\s*(.+?);");
            Matcher sexeMatcher = sexePattern.matcher(result);
            String sexe = "";
            if (sexeMatcher.find()) {
                sexe = sexeMatcher.group(1);
            }
            prescriptionJson.put("Sexe", sexe);

            /* Taille */
            Pattern taillePattern = Pattern.compile("Taille\s*:\s*(\\d+)");
            Matcher tailleMatcher = taillePattern.matcher(result);
            String taille = "";
            if (tailleMatcher.find()) {
                taille = tailleMatcher.group(1);
            }
            prescriptionJson.put("Taille", taille);

            /* Poids */        
            Pattern poidsPattern = Pattern.compile("Poids\s*:\s*(\\d+)");
            Matcher poidsMatcher = poidsPattern.matcher(result);
            if (poidsMatcher.find()) {
                String poids = poidsMatcher.group(1);
                prescriptionJson.put("Poids", poids);
            } else {
                prescriptionJson.put("Poids", "");
            }

            /* Médicament(s) */
            String[] medications = result.split("Médicament");
            medications = Arrays.copyOfRange(medications, 1, medications.length);

            System.out.println("Liste des médicaments : "+medications);
            JSONArray medicationsArray = new JSONArray();

            for (String medication : medications)
            {
                JSONObject medicineJson = new JSONObject();

                /* Nom */
                Pattern nomMedicamentPattern = Pattern.compile("Medicament\s*:\s*(.+?);");
                Matcher nomMedicamentMatcher = nomMedicamentPattern.matcher(medication);
                if (nomMedicamentMatcher.find()) {
                    String nomMedicament = nomMedicamentMatcher.group(1);
                    medicineJson.put("NomMedicament", nomMedicament);
                } else {
                    medicineJson.put("NomMedicament", "");
                }

                /* Posologie */
                Pattern posologyPattern = Pattern.compile("Posologie\s*:\s*(\\d+)");
                Matcher posologyMatcher = posologyPattern.matcher(medication);
                if (posologyMatcher.find()) {
                    String posologie = posologyMatcher.group(1);
                    medicineJson.put("Posologie", posologie);
                } else {
                    medicineJson.put("Posologie", "");
                }

                /* Periode */
                Pattern periodePattern = Pattern.compile("Période\s*:\s*(\\d+)");
                Matcher periodeMatcher = periodePattern.matcher(medication);
                if (periodeMatcher.find()) {
                    String periode = periodeMatcher.group(1);
                    medicineJson.put("Periode", periode);
                } else {
                    medicineJson.put("Periode", "");
                }
                /* Renouvelable */
                Pattern renouvelablePattern = Pattern.compile("Renouvelable\s*:\s*(\\d+)");
                Matcher renouvelableMatcher = renouvelablePattern.matcher(medication);
                if (renouvelableMatcher.find()) {
                    String renouvelable = renouvelableMatcher.group(1);
                    medicineJson.put("Renouvelable", renouvelable);
                } else {
                    medicineJson.put("Periode", "");
                }
                /* Remboursable */
                Pattern remboursablePattern = Pattern.compile("Remboursable\s*:\s*(.+?);");
                Matcher remboursableMatcher = remboursablePattern.matcher(medication);
                if (remboursableMatcher.find()) {
                    String remboursable = remboursableMatcher.group(1);
                    medicineJson.put("Remboursable", remboursable);
                } else {
                    medicineJson.put("Remboursable", "");

                }

                /* Indication */
                Pattern indicationPattern = Pattern.compile("Indication\s*:\s*(.+?);");
                Matcher indicationMatcher = indicationPattern.matcher(medication);
                if (indicationMatcher.find()) {
                    String indication = indicationMatcher.group(1);
                    medicineJson.put("Indication", indication);
                } else {
                    medicineJson.put("Indication", "");
                }
                medicationsArray.put(medicineJson);
            }
            System.out.println("SERVICEOCRRRRRRRRRRRRRRRRRRRRRRRRRRRrR : "+prescriptionJson);
            prescriptionJson.put("Medicaments",medicationsArray);
        } 
        catch (IOException e) {
        }
        catch (TesseractException e) {
            System.err.println("Erreur lors de la reconnaissance optique de caractères : " + e.getMessage());
        }
        return prescriptionJson;
    }
}
