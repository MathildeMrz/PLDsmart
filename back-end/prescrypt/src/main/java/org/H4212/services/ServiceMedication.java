package org.H4212.services;

import org.H4212.entities.Medication;
import java.sql.*;
import java.time.Duration;

public class ServiceMedication {
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
    public Medication getMedication(long medicationId) throws SQLException {
        String stringQuery =
                """
                    SELECT * from medication WHERE medicationId = ?;
                """;

        PreparedStatement preparedStatement = connection.prepareStatement(stringQuery) ;
        preparedStatement.setLong(1, medicationId);

        ResultSet resultSet;
        try {
            resultSet = preparedStatement.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        if(resultSet.next())
        {
            return new Medication((long) resultSet.getLong(1), resultSet.getString(2), resultSet.getInt(3), resultSet.getString(4), Duration.parse(resultSet.getString(5)));
        }else{
            System.out.println("ResultSet for getting a medication is empty");
            return new Medication();
        }
    }
}
