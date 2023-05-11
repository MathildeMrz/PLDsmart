package org.H4212.services;

import org.H4212.api.jsonSerializers.*;
import org.H4212.entities.*;

import java.sql.*;
import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.*;

public class ServicePrescription {
    String URL = "jdbc:postgresql://localhost:5432/prescrypt";
    String USER = "prescrypt";
    String PASSWORD = "prescrypt";
    Connection connection;
    {
        try {
            DriverManager.registerDriver(new org.postgresql.Driver());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    {
        try {
            connection = DriverManager.getConnection(URL,USER,PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<GetPrescriptionResponse> getPrescriptions(long doctorId) throws SQLException {
        List<GetPrescriptionResponse> getPrescriptionResponseList = new ArrayList<GetPrescriptionResponse>();
        GetPrescriptionResponse getPrescriptionResponse;
        Doctor doctor;
        Patient patient;
        Medication medication;
        Prescription prescription;
        ServiceUser serviceUser = new ServiceUser();
        ServiceMedication serviceMedication = new ServiceMedication();
        String stringQuery =
                "SELECT * FROM prescription WHERE doctorId = "+doctorId+"; ";
        Statement statement =connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_READ_ONLY,
                ResultSet.HOLD_CURSORS_OVER_COMMIT);

        ResultSet resultSet;
        try {
            resultSet = statement.executeQuery(stringQuery);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        while(resultSet.next())
        {
            doctor = serviceUser.getDoctor(doctorId);
            patient = serviceUser.getPatient((long) resultSet.getLong(3));

            String stringQueryMedications =
                    """
                        SELECT medicationId FROM medicationPrescription WHERE medicationPrescriptionId = ?;
                    """;
            PreparedStatement preparedStatementMedications = connection.prepareStatement(stringQueryMedications);
            preparedStatementMedications.setLong(1, (long) resultSet.getLong(5));

            ResultSet resultSetMedications;
            try {
                resultSetMedications = preparedStatementMedications.executeQuery();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            List<Medication> medicationList = new ArrayList<Medication>();
            while(resultSetMedications.next())
            {
                medication = serviceMedication.getMedication((long) resultSetMedications.getLong(1));
                medicationList.add(medication);
            }
            prescription = new Prescription((long) resultSet.getLong(1), doctor, patient, OffsetDateTime.parse(resultSet.getString(4)), medicationList, resultSet.getInt(6), resultSet.getBoolean(7), resultSet.getString(8));
            getPrescriptionResponse = new GetPrescriptionResponse(prescription);
            getPrescriptionResponseList.add(getPrescriptionResponse);
        }

        if(getPrescriptionResponseList.isEmpty())
        {
            System.out.println("ResultSet for getting prescriptions is empty");
        }
        return getPrescriptionResponseList;
    }

    public void deletePrescription(long doctorId, long prescriptionId) throws SQLException{
        String stringQueryMedicationPrescription =
                """
                    DELETE FROM medicationPrescription WHERE prescriptionId = ?;        
                """;
        PreparedStatement preparedStatementMedicationPrescription = connection.prepareStatement(stringQueryMedicationPrescription);
        preparedStatementMedicationPrescription.setLong(1, prescriptionId);

        preparedStatementMedicationPrescription.executeUpdate();

        String stringQueryPrescription =
                """
                    DELETE FROM prescription WHERE doctorId = ? AND prescriptionId = ?;
                """;
        PreparedStatement preparedStatementPrescription = connection.prepareStatement(stringQueryPrescription);
        preparedStatementPrescription.setLong(1, doctorId);
        preparedStatementPrescription.setLong(2, prescriptionId);

        preparedStatementPrescription.executeUpdate();
    }

    public void createPrescription(CreatePrescriptionRequest createPrescriptionRequest) throws SQLException {
        long medicationPrescriptionId = (long) (Math.random() * (1000000L - 0L));
        long prescriptionId = (long) (Math.random() * (1000000L - 0L));
        long patientId = (long) (Math.random() * (1000000L - 0L));
        for(int i = 0; i < createPrescriptionRequest.getPrescription().getMedicationList().size(); i++)
        {
            long medicationId = (long) (Math.random() * (1000000L - 0L));
            String stringQueryMedication =
                    """
                        INSERT INTO medication VALUES (?,?,?,?,?);
                    """;
            PreparedStatement preparedStatementMedication = connection.prepareStatement(stringQueryMedication);
            preparedStatementMedication.setLong(1, medicationId);
            preparedStatementMedication.setString(2, createPrescriptionRequest.getPrescription().getMedicationList().get(i).getName());
            preparedStatementMedication.setInt(3, createPrescriptionRequest.getPrescription().getMedicationList().get(i).getDosage());
            preparedStatementMedication.setString(4, createPrescriptionRequest.getPrescription().getMedicationList().get(i).getInstructions());
            preparedStatementMedication.setString(5, createPrescriptionRequest.getPrescription().getMedicationList().get(i).getDuration().toString());

            preparedStatementMedication.executeUpdate();

            String stringQueryMedicationPrescription =
                """
                        INSERT INTO medicationPrescription VALUES (?,?,?);
                    """;
            PreparedStatement preparedStatementMedicationPrescription = connection.prepareStatement(stringQueryMedicationPrescription);
            preparedStatementMedicationPrescription.setLong(1, medicationPrescriptionId);
            preparedStatementMedicationPrescription.setLong(2, medicationId);
            preparedStatementMedicationPrescription.setLong(3, prescriptionId);
            preparedStatementMedicationPrescription.executeUpdate();
        }

        String stringQueryPatient =
                """
                    INSERT INTO patient VALUES (?,?,?,?,?,?,?);    
                """;
        PreparedStatement preparedStatementPatient = connection.prepareStatement(stringQueryPatient);
        preparedStatementPatient.setLong(1, patientId);
        preparedStatementPatient.setString(2, createPrescriptionRequest.getPrescription().getPatient().getLastName());
        preparedStatementPatient.setString(3, createPrescriptionRequest.getPrescription().getPatient().getFirstName());
        preparedStatementPatient.setInt(4, createPrescriptionRequest.getPrescription().getPatient().getAge());
        preparedStatementPatient.setInt(5, createPrescriptionRequest.getPrescription().getPatient().getWeight());
        preparedStatementPatient.setBoolean(6, createPrescriptionRequest.getPrescription().getPatient().isSex());
        preparedStatementPatient.setInt(7, createPrescriptionRequest.getPrescription().getPatient().getHeight());

        preparedStatementPatient.executeUpdate();

        String stringQueryPrescription =
                """
                    INSERT INTO prescription VALUES (?,?,?,?,?,?,?,?);        
                """;
        PreparedStatement preparedStatementPrescription = connection.prepareStatement(stringQueryPrescription);
        preparedStatementPrescription.setLong(1, prescriptionId);
        preparedStatementPrescription.setLong(2, createPrescriptionRequest.getDoctorId());
        preparedStatementPrescription.setLong(3, patientId);
        preparedStatementPrescription.setString(4, createPrescriptionRequest.getPrescription().getConsultationDate().toString());
        preparedStatementPrescription.setLong(5, medicationPrescriptionId);
        preparedStatementPrescription.setInt(6, createPrescriptionRequest.getPrescription().getNbRenewals());
        preparedStatementPrescription.setBoolean(7, createPrescriptionRequest.getPrescription().isNR());
        preparedStatementPrescription.setString(8, createPrescriptionRequest.getPrescription().getNotes());

        preparedStatementPrescription.executeUpdate();
    }
}
