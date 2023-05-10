package org.H4212.services;

import org.json.JSONObject;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ServiceParser {
    public JSONObject parseTxtToJSON(String buffer){
        System.out.println(buffer);

        String result = buffer;

        JSONObject prescriptionJson = new JSONObject();
        /* Médecin */
        //Nom
        Pattern nomDocteurPattern = Pattern.compile("Nom docteur : (.+?);");
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
        Pattern qualificationPattern = Pattern.compile("Qualification : (.+?);");
        Matcher qualificationMatcher = qualificationPattern.matcher(result);
        String qualification = "";
        if (qualificationMatcher.find()) {
            qualification = qualificationMatcher.group(1);
        }
        prescriptionJson.put("Qualification", qualification);

        //RPPS
        Pattern rppsPattern = Pattern.compile("RPPS : (.+?);");
        Matcher rppsMatcher = rppsPattern.matcher(result);
        String rpps = "";
        if (rppsMatcher.find()) {
            rpps = rppsMatcher.group(1);
        }
        prescriptionJson.put("RPPS", rpps);

        //Adresse
        Pattern adressePattern = Pattern.compile("Adresse : (.+?);");
        Matcher adresseMatcher = adressePattern.matcher(result);
        String adresse = "";
        if (adresseMatcher.find()) {
            adresse = adresseMatcher.group(1);
        }
        prescriptionJson.put("Adresse", adresse);

        //Tel
        Pattern telPattern = Pattern.compile("Tel : (.+?);");
        Matcher telMatcher = telPattern.matcher(result);
        String tel = "";
        if (telMatcher.find()) {
            tel = telMatcher.group(1);
        }
        prescriptionJson.put("Tel", tel);

        /* Consultation */
        //Date
        Pattern datePattern = Pattern.compile("Fait le : (.+?);");
        Matcher dateMatcher = datePattern.matcher(result);
        String date = "";
        if (dateMatcher.find()) {
            date = dateMatcher.group(1);
        }
        prescriptionJson.put("Date", date);

        /* Patient */
        Pattern nomPatientPattern = Pattern.compile("Nom patient : (.+?);");
        Matcher nomPatientMatcher = nomPatientPattern.matcher(result);
        String nomPatient = "";
        if (nomPatientMatcher.find()) {
            nomPatient = nomPatientMatcher.group(1);
        }
        prescriptionJson.put("NomPatient", nomPatient);

        /* Prénom */
        Pattern prenomPatientPattern = Pattern.compile("Prénom patient : (.+?);");
        Matcher prenomPatientMatcher = prenomPatientPattern.matcher(result);
        String prenomPatient = "";
        if (prenomPatientMatcher.find()) {
            prenomPatient = prenomPatientMatcher.group(1);
        }
        prescriptionJson.put("PrenomPatient", prenomPatient);

        /* Age */
        Pattern agePattern = Pattern.compile("Age : (.+?);");
        Matcher ageMatcher = agePattern.matcher(result);
        String age = "";
        if (ageMatcher.find()) {
            age = ageMatcher.group(1);
        }
        prescriptionJson.put("Age", age);

        /* Sexe */
        Pattern sexePattern = Pattern.compile("Sexe : (.+?);");
        Matcher sexeMatcher = sexePattern.matcher(result);
        String sexe = "";
        if (sexeMatcher.find()) {
            sexe = sexeMatcher.group(1);
        }
        prescriptionJson.put("Sexe", sexe);

        /* Taille */
        Pattern taillePattern = Pattern.compile("Taille : (\\d+)");
        Matcher tailleMatcher = taillePattern.matcher(result);
        String taille = "";
        if (tailleMatcher.find()) {
            taille = tailleMatcher.group(1);
        }
        prescriptionJson.put("Taille", taille);

        /* Poids */
        Pattern poidsPattern = Pattern.compile("Poids : (\\d+)");
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

        /* Médicament(s) */
        Pattern medicamentPattern = Pattern.compile("""
                                                    Médicament1 : (.+?);
                                                    Posologie : (.+?)mg;
                                                    Période : (.+?)
                                                    Renouvelable : (.+?);
                                                    Remboursable : (.+?);
                                                    Indication : (.+?);
                                                    """);
        Matcher medicamentMatcher = medicamentPattern.matcher(result);
        while (medicamentMatcher.find())
        {
            JSONObject medicineJson = new JSONObject();
            String nomMedicament = medicamentMatcher.group(1);
            String posologie = medicamentMatcher.group(2);
            String periode = medicamentMatcher.group(3);
            String renouvelable = medicamentMatcher.group(4);
            String remboursable = medicamentMatcher.group(5);
            String indication = medicamentMatcher.group(6);
            medicineJson.put("NomMedicament", nomMedicament);
            medicineJson.put("Posologie", posologie);
            medicineJson.put("Periode", periode);
            medicineJson.put("Renouvelable", renouvelable);
            medicineJson.put("Remboursable", remboursable);
            medicineJson.put("Indication", indication);
            prescriptionJson.accumulate("Medicament", medicineJson);
        }

        return prescriptionJson;
    }
}
