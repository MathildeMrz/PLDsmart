package org.H4212.services;

import org.json.JSONArray;
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
        Pattern agePattern = Pattern.compile("Age : (.+?) ans;");
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
        JSONArray medicaments = new JSONArray();
        int nbMedicines = 0;
        Pattern nbMedicinesPattern = Pattern.compile("Médicament");
        Matcher nbMedicinesMatcher = nbMedicinesPattern.matcher(result);
        while(nbMedicinesMatcher.find())
        {
            nbMedicines++;
        }

        /* Médicament(s) */
        Pattern medicamentPattern = Pattern.compile("Médicament n°(\\d+) : (.+?);");
        Matcher medicamentMatcher = medicamentPattern.matcher(result);

        Pattern posologiePattern = Pattern.compile("Posologie : (.+?);");
        Matcher posologieMatcher = posologiePattern.matcher(result);

        Pattern periodePattern = Pattern.compile("Période : (.+?);");
        Matcher periodeMatcher = periodePattern.matcher(result);

        Pattern renouvelablePattern = Pattern.compile("Renouvelable : (.+?);");
        Matcher renouvelableMatcher = renouvelablePattern.matcher(result);

        Pattern remboursablePattern = Pattern.compile("Remboursable : (.+?);");
        Matcher remboursableMatcher = remboursablePattern.matcher(result);

        Pattern indicationPattern = Pattern.compile("Indication : (.+?);");
        Matcher indicationMatcher = indicationPattern.matcher(result);

        while(medicamentMatcher.find() && posologieMatcher.find() && periodeMatcher.find() && renouvelableMatcher.find() && remboursableMatcher.find() && indicationMatcher.find()){
            JSONObject medicineJson = new JSONObject();
            medicineJson.put("NomMedicament", medicamentMatcher.group(2));
            medicineJson.put("Posologie", posologieMatcher.group(1));
            medicineJson.put("Période", periodeMatcher.group(1));
            medicineJson.put("Renouvelable", renouvelableMatcher.group(1));
            medicineJson.put("Remboursable", remboursableMatcher.group(1));
            medicineJson.put("Indication", indicationMatcher.group(1));

            medicaments.put(medicineJson);
        }

        if(!medicaments.isEmpty()) prescriptionJson.put("Médicaments", medicaments);

        return prescriptionJson;
    }
}
