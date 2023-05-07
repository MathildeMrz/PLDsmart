package org.H4212.services;

import org.H4212.api.jsonSerializers.*;
import org.H4212.entities.*;
import java.sql.*;

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

        PreparedStatement preparedStatementDoctor;

        if(resultSet.next()) {
            String fetchDoctorQuery =
                    """
                SELECT * from doctor WHERE doctorId = ?;
                """;

            preparedStatementDoctor = connection.prepareStatement(fetchDoctorQuery);
            preparedStatementDoctor.setLong(1, resultSet.getLong(1));
        }else{
            System.out.println("ResultSet is empty");
            return new Doctor();
        }

        ResultSet resultSetDoctor;
        try {
            resultSetDoctor = preparedStatementDoctor.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        if(resultSetDoctor.next())
        {
            return new Doctor((long) resultSetDoctor.getInt(1), resultSetDoctor.getString(2), resultSetDoctor.getString(3), (long) resultSetDoctor.getInt(4), resultSetDoctor.getString(5), resultSetDoctor.getString(6), resultSetDoctor.getString(7));
        }else{
            System.out.println("ResultSet is empty");
            return new Doctor();
        }
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

        PreparedStatement preparedStatementPharmacist;

        if(resultSet.next()) {
            String fetchPharmacistQuery =
                    """
                SELECT * from pharmacist WHERE pharmacistId = ?;
                """;

        preparedStatementPharmacist = connection.prepareStatement(fetchPharmacistQuery);
        preparedStatementPharmacist.setLong(1, resultSet.getLong(1));
        }else{
            System.out.println("ResultSet is empty");
            return new Pharmacist();
        }

        ResultSet resultSetPharmacist;
        try {
            resultSetPharmacist = preparedStatementPharmacist.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        if(resultSetPharmacist.next())
        {
            return new Pharmacist((long) resultSetPharmacist.getInt(1), resultSetPharmacist.getString(2), resultSetPharmacist.getString(3));
        }else{
            System.out.println("ResultSet is empty");
            return new Pharmacist();
        }
    }

    public Person authenticateAdmin(String username, String password) throws SQLException{
        String hashedPassword = hashString(password);

        String stringQuery =
                """
                    SELECT * from users WHERE username = ? AND password = ?;
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

        if(resultSet.next())
        {
            return new Person((long) resultSet.getInt(1), resultSet.getString(2), resultSet.getString(2));
        }else{
            System.out.println("ResultSet is empty");
            return new Person();
        }
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

    public void registerPharmacist(RegisterPharmacistRequest registerPharmacistRequest) throws SQLException {

        String hashedPassword = hashString(registerPharmacistRequest.getPharmacist().getPassword());

        Long id = (long) (Math.random() * ( Long.MAX_VALUE - 0L ));

        String stringQueryUsers =
                """
                    INSERT INTO users VALUES (?,?,?);
                """;

        PreparedStatement preparedStatementUsers = connection.prepareStatement(stringQueryUsers);
        preparedStatementUsers.setLong(1, id);
        preparedStatementUsers.setString(2, registerPharmacistRequest.getPharmacist().getUsername());
        preparedStatementUsers.setString(3, hashedPassword);

        preparedStatementUsers.executeUpdate();

        String stringQueryPharmacist =
                """
                    INSERT INTO pharmacist VALUES (?,?,?);
                """;

        PreparedStatement preparedStatementPharmacist = connection.prepareStatement(stringQueryPharmacist);
        preparedStatementPharmacist.setLong(1, id);
        preparedStatementPharmacist.setString(2, registerPharmacistRequest.getPharmacist().getLastName());
        preparedStatementPharmacist.setString(3, registerPharmacistRequest.getPharmacist().getFirstName());

        preparedStatementPharmacist.executeUpdate();
    }

    public void deleteDoctor(Long doctorId) throws SQLException{

        String stringQueryDoctor =
                """
                    DELETE FROM doctor WHERE doctorId = ?;
                """;

        PreparedStatement preparedStatementDoctor = connection.prepareStatement(stringQueryDoctor);
        preparedStatementDoctor.setLong(1, doctorId);

        preparedStatementDoctor.executeUpdate();

        String stringQueryUsers =
                """
                    DELETE FROM users WHERE userId = ?;
                """;

        PreparedStatement preparedStatementUsers = connection.prepareStatement(stringQueryUsers);
        preparedStatementUsers.setLong(1, doctorId);

        preparedStatementUsers.executeUpdate();

    }

    public void deletePharmacist(Long pharmacistId) throws SQLException{

        String stringQueryPharmacist =
                """
                    DELETE FROM pharmacist WHERE pharmacistId = ?;
                """;

        PreparedStatement preparedStatementPharmacist = connection.prepareStatement(stringQueryPharmacist);
        preparedStatementPharmacist.setLong(1, pharmacistId);

        preparedStatementPharmacist.executeUpdate();

        String stringQueryUsers =
                """
                    DELETE FROM users WHERE userId = ?;
                """;

        PreparedStatement preparedStatementUsers = connection.prepareStatement(stringQueryUsers);
        preparedStatementUsers.setLong(1, pharmacistId);

        preparedStatementUsers.executeUpdate();

    }
}
