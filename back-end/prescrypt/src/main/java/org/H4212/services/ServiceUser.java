package org.H4212.services;

import org.H4212.entities.*;

import jakarta.inject.Inject;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.sql.*;
import java.util.*;

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
    public Person authenticate(String username, String password) throws SQLException {
        String stringQuery =
                """
                    SELECT id, username, password from users WHERE username = ? AND password = ?;
                """;

        PreparedStatement preparedStatement = connection.prepareStatement(stringQuery) ;
        preparedStatement.setString(1, username);
        preparedStatement.setString(2, password);

        ResultSet resultSet;
        try {
            resultSet = preparedStatement.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return new Person();
    }
}
