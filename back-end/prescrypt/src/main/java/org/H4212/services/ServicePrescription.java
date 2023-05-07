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
        List<Medication> medicationList = new ArrayList<Medication>();
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
            patient = serviceUser.getPatient((long) resultSet.getInt(3));

            String stringQueryMedications =
                    """
                        SELECT medicationId FROM medicationPrescription WHERE medicationPrescriptionId = ?;
                    """;
            PreparedStatement preparedStatementMedications = connection.prepareStatement(stringQueryMedications) ;
            preparedStatementMedications.setLong(1, (long) resultSet.getInt(5));

            ResultSet resultSetMedications;
            try {
                resultSetMedications = preparedStatementMedications.executeQuery();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            while(resultSetMedications.next())
            {
                medication = serviceMedication.getMedication((long) resultSetMedications.getInt(1));
                medicationList.add(medication);
            }
            prescription = new Prescription((long) resultSet.getInt(1), doctor, patient, OffsetDateTime.parse(resultSet.getString(4)), medicationList, resultSet.getInt(6), resultSet.getBoolean(7), resultSet.getString(8));
            getPrescriptionResponse = new GetPrescriptionResponse(prescription);
            getPrescriptionResponseList.add(getPrescriptionResponse);
        }

        if(getPrescriptionResponseList.isEmpty())
        {
            System.out.println("ResultSet for getting prescriptions is empty");
        }
        return getPrescriptionResponseList;

    }
}
