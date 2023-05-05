package org.H4212.services;

import org.H4212.api.jsonSerializers.RegisterDoctorRequest;
import org.H4212.entities.*;


import java.sql.*;
import java.util.*;

import static org.H4212.util.HashingUtil.hashString;

public class ServiceUser {

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

    public Doctor authenticateDoctor(String username, String password) throws SQLException {

        String hashedPassword = hashString(password);

        String stringQuery =
                """
                    SELECT userId from users WHERE username = ? AND password = ?;
                """;

        PreparedStatement preparedStatement = connection.prepareStatement(stringQuery) ;
        preparedStatement.setString(1, username);
        preparedStatement.setString(2, hashedPassword);

        ResultSet resultSet;
        try {
            resultSet = preparedStatement.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        resultSet.next();

        String fetchDoctorQuery =
                """
                SELECT * from doctor WHERE doctorId = ?;
                """;

        PreparedStatement preparedStatementDoctor = connection.prepareStatement(fetchDoctorQuery);
        preparedStatementDoctor.setInt(1, resultSet.getInt(1));

        ResultSet resultSetDoctor;
        try {
            resultSetDoctor = preparedStatementDoctor.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        resultSetDoctor.next();

        return new Doctor(resultSetDoctor.getString(2), resultSetDoctor.getString(3), (long) resultSetDoctor.getInt(4), resultSetDoctor.getString(5), resultSetDoctor.getString(6), resultSetDoctor.getString(7));
    }

    public Pharmacist authenticatePharmacist(String username, String password) throws SQLException {

        String hashedPassword = hashString(password);

        String stringQuery =
                """
                    SELECT userId from users WHERE username = ? AND password = ?;
                """;

        PreparedStatement preparedStatement = connection.prepareStatement(stringQuery) ;
        preparedStatement.setString(1, username);
        preparedStatement.setString(2, hashedPassword);

        ResultSet resultSet;
        try {
            resultSet = preparedStatement.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        resultSet.next();

        String fetchPharmacistQuery =
                """
                SELECT * from pharmacist WHERE pharmacistId = ?;
                """;

        PreparedStatement preparedStatementPharmacist = connection.prepareStatement(fetchPharmacistQuery);
        preparedStatementPharmacist.setInt(1, resultSet.getInt(1));

        ResultSet resultSetPharmacist;
        try {
            resultSetPharmacist = preparedStatementPharmacist.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        resultSetPharmacist.next();

        return new Pharmacist(resultSetPharmacist.getString(2), resultSetPharmacist.getString(3));
    }

    public void registerDoctor(RegisterDoctorRequest registerDoctorRequest) throws SQLException {

        String hashedPassword = hashString(registerDoctorRequest.getDoctor().getPassword());

        Long id = (long) (Math.random() * ( Long.MAX_VALUE - 0L ));

        String stringQueryUsers =
                """
                    INSERT INTO users VALUES (?,?,?);
                """;

        PreparedStatement preparedStatementUsers = connection.prepareStatement(stringQueryUsers);
        preparedStatementUsers.setLong(1, id);
        preparedStatementUsers.setString(2, registerDoctorRequest.getDoctor().getUsername());
        preparedStatementUsers.setString(3, hashedPassword);

        preparedStatementUsers.executeUpdate();

        String stringQueryDoctor =
                """
                    INSERT INTO doctor VALUES (?,?,?,?,?,?,?);
                """;

        PreparedStatement preparedStatementDoctor = connection.prepareStatement(stringQueryDoctor);
        preparedStatementDoctor.setLong(1, id);
        preparedStatementDoctor.setString(2, registerDoctorRequest.getDoctor().getLastName());
        preparedStatementDoctor.setString(3, registerDoctorRequest.getDoctor().getFirstName());
        preparedStatementDoctor.setLong(4, registerDoctorRequest.getDoctor().getIdPSdoctor());
        preparedStatementDoctor.setString(5, registerDoctorRequest.getDoctor().getQualification());
        preparedStatementDoctor.setString(6, registerDoctorRequest.getDoctor().getOfficeAddress());
        preparedStatementDoctor.setString(7, registerDoctorRequest.getDoctor().getTelephone());

        preparedStatementDoctor.executeUpdate();
    }
}
