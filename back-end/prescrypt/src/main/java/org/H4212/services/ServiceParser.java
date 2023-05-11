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

        /* Durée validité */
        Pattern dureeValiditePattern = Pattern.compile("Durée de validité\s*:\s*(\\d+)");
        Matcher dureeValiditeMatcher = dureeValiditePattern.matcher(result);
        if (dureeValiditeMatcher.find()) {
            String validite = dureeValiditeMatcher.group(1);
            prescriptionJson.put("Validite", validite);
        } else {
            prescriptionJson.put("Validite", "");
        }

        /* Médicament(s) */
        String[] medications = result.split("Médicament\s*:\s*");
        medications = Arrays.copyOfRange(medications, 1, medications.length);

        JSONArray medicationsArray = new JSONArray();

        for (String medication : medications)
        {
            JSONObject medicineJson = new JSONObject();
            /* Nom */
            Pattern nomMedicamentPattern = Pattern.compile("(.+?);");
            Matcher nomMedicamentMatcher = nomMedicamentPattern.matcher(medication);
            if (nomMedicamentMatcher.find()) {
                String nomMedicament = nomMedicamentMatcher.group(1);
                medicineJson.put("NomMedicament", nomMedicament);
            } else {
                medicineJson.put("NomMedicament", "");

            }

            /* Posologie */
            Pattern posologyPattern = Pattern.compile("Posologie\s*:\s*(.+?);");
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

            /* Periode text*/
            Pattern periodeTextPattern = Pattern.compile("Période\s*:\s*\\d+\s*(.+?)\s*;");
            Matcher periodeTextPatternMatcher = periodeTextPattern.matcher(medication);
            if (periodeTextPatternMatcher.find()) {
                String periodeText = periodeTextPatternMatcher.group(1);
                medicineJson.put("PeriodeTexte", periodeText);
            } else {
                medicineJson.put("PeriodeTexte", "");
            }

            /* Renouvelable */
            Pattern renouvelablePattern = Pattern.compile("Renouvelable\s*:\s*(\\d+)");
            Matcher renouvelableMatcher = renouvelablePattern.matcher(medication);

            Pattern renouvelableZeroPattern = Pattern.compile("Renouvelable\s*:\s*(.+?)");
            Matcher renouvelableZeroMatcher = renouvelableZeroPattern.matcher(medication);

            if (renouvelableMatcher.find())
            {
                String renouvelable = renouvelableMatcher.group(1);
                medicineJson.put("Renouvelable", renouvelable);
            }
            else if(renouvelableZeroMatcher.find())
            {
                medicineJson.put("Renouvelable", "0");
            }
            else
            {
                medicineJson.put("Renouvelable", "");
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
        prescriptionJson.put("Medicaments",medicationsArray);

        return prescriptionJson;
    }
}
