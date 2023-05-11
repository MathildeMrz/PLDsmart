package org.H4212.services;

import org.H4212.api.jsonSerializers.*;
import org.H4212.entities.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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
            return new Doctor((long) resultSetDoctor.getLong(1), resultSetDoctor.getString(2), resultSetDoctor.getString(3), (long) resultSetDoctor.getLong(4), resultSetDoctor.getString(5), resultSetDoctor.getString(6), resultSetDoctor.getString(7), resultSetDoctor.getString(8));
        }else{
            System.out.println("ResultSet is empty");
            return new Doctor();
        }
    }

    public Doctor getDoctor(long doctorId) throws SQLException {
        String stringQuery =
                """
                    SELECT userId from users WHERE userId = ?;
                """;

        PreparedStatement preparedStatement = connection.prepareStatement(stringQuery) ;
        preparedStatement.setLong(1, doctorId);

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
            return new Doctor((long) resultSetDoctor.getLong(1), resultSetDoctor.getString(2), resultSetDoctor.getString(3), (long) resultSetDoctor.getLong(4), resultSetDoctor.getString(5), resultSetDoctor.getString(6), resultSetDoctor.getString(7), resultSetDoctor.getString(8));
        }else{
            System.out.println("ResultSetDoctor is empty");
            return new Doctor();
        }
    }

    public List<GetDoctorResponse> getDoctors() throws SQLException{
        List<GetDoctorResponse> getDoctorResponseList = new ArrayList<GetDoctorResponse>();
        String stringQueryDoctors =
                """
                    SELECT doctorId FROM doctor;
                """;
        PreparedStatement preparedStatementDoctors = connection.prepareStatement(stringQueryDoctors);
        ResultSet resultSetDoctors;
        try {
            resultSetDoctors = preparedStatementDoctors.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        while(resultSetDoctors.next()) {
            getDoctorResponseList.add(new GetDoctorResponse(getDoctor(resultSetDoctors.getLong(1))));
        }
        return getDoctorResponseList;
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
            return new Pharmacist((long) resultSetPharmacist.getLong(1), resultSetPharmacist.getString(2), resultSetPharmacist.getString(3));
        }else{
            System.out.println("ResultSet is empty");
            return new Pharmacist();
        }
    }

    public Pharmacist getPharmacist(long pharmacistId) throws SQLException {
        String stringQuery =
                """
                    SELECT userId from users WHERE userId = ?;
                """;

        PreparedStatement preparedStatement = connection.prepareStatement(stringQuery) ;
        preparedStatement.setLong(1, pharmacistId);

        ResultSet resultSet;
        try {
            resultSet = preparedStatement.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        PreparedStatement preparedStatementPharmacist;

        if(resultSet.next()) {
            String fetchDoctorQuery =
                    """
                SELECT * from pharmacist WHERE pharmacistId = ?;
                """;

            preparedStatementPharmacist = connection.prepareStatement(fetchDoctorQuery);
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
            return new Pharmacist((long) resultSetPharmacist.getLong(1), resultSetPharmacist.getString(2), resultSetPharmacist.getString(3));
        }else{
            System.out.println("resultSetPharmacist is empty");
            return new Pharmacist();
        }
    }

    public List<GetPharmacistResponse> getPharmacists() throws SQLException{
        List<GetPharmacistResponse> getPharmacistResponseList = new ArrayList<GetPharmacistResponse>();
        String stringQueryPharmacists =
                """
                    SELECT pharmacistId FROM pharmacist;
                """;
        PreparedStatement preparedStatementPharmacists = connection.prepareStatement(stringQueryPharmacists);
        ResultSet resultSetPharmacists;
        try {
            resultSetPharmacists = preparedStatementPharmacists.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        while(resultSetPharmacists.next()) {
            getPharmacistResponseList.add(new GetPharmacistResponse(getPharmacist(resultSetPharmacists.getLong(1))));
        }
        return getPharmacistResponseList;
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
            return new Person((long) resultSet.getLong(1), resultSet.getString(2), resultSet.getString(2));
        }else{
            System.out.println("ResultSet is empty");
            return new Person();
        }
    }
    public Patient getPatient(long patientId) throws SQLException {
        String stringQuery =
                """
                    SELECT * from patient WHERE patientId = ?;
                """;

        PreparedStatement preparedStatement = connection.prepareStatement(stringQuery) ;
        preparedStatement.setLong(1, patientId);

        ResultSet resultSet;
        try {
            resultSet = preparedStatement.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        if(resultSet.next())
        {
            return new Patient((long) resultSet.getLong(1), resultSet.getString(2), resultSet.getString(3), resultSet.getInt(4), resultSet.getInt(5), resultSet.getBoolean(6), resultSet.getInt(7));
        }else{
            System.out.println("ResultSet is empty");
            return new Patient();
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
                    INSERT INTO doctor VALUES (?,?,?,?,?,?,?,?);
                """;

        PreparedStatement preparedStatementDoctor = connection.prepareStatement(stringQueryDoctor);
        preparedStatementDoctor.setLong(1, id);
        preparedStatementDoctor.setString(2, registerDoctorRequest.getDoctor().getLastName());
        preparedStatementDoctor.setString(3, registerDoctorRequest.getDoctor().getFirstName());
        preparedStatementDoctor.setLong(4, registerDoctorRequest.getDoctor().getIdPSdoctor());
        preparedStatementDoctor.setString(5, registerDoctorRequest.getDoctor().getQualification());
        preparedStatementDoctor.setString(6, registerDoctorRequest.getDoctor().getOfficeAddress());
        preparedStatementDoctor.setString(7, registerDoctorRequest.getDoctor().getTelephone());
        preparedStatementDoctor.setString(8, registerDoctorRequest.getDoctor().getEthAddress());

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

    public void deleteDoctor(long doctorId) throws SQLException{

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

    public void deletePharmacist(long pharmacistId) throws SQLException{

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
