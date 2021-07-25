package com.softserve.edu.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.softserve.edu.entity.User;

public class UserDao {
    private static List<UserDao> instances = null;
    //
    private final String CONNECTION_ERROR = "Connection ERROR, message %s";
    private final String QUERY_ERROR = "Query ERROR, message %s";
    //
    private Connection connection;
    private Statement statement;
    private String schemaName;

    public UserDao(String schemaName) {
        instances = new ArrayList<>();
        instances.add(this);
        this.schemaName = schemaName;
        init();
    }

    private void init() {
        try {
            DriverManager.registerDriver(new org.h2.Driver());
            connection = DriverManager.getConnection("jdbc:h2:mem:test", "", "");
            statement = connection.createStatement();
        } catch (SQLException e) {
            System.out.println(String.format(CONNECTION_ERROR, e.toString()));
            System.exit(1);
        }
        createSchema();
        useSchema();
        createTableUsers();
    }

    public static void closeAll() {
        if (instances != null) {
            for (UserDao instance : instances) {
                if (instance != null) {
                    try {
                        instance.statement.execute("DROP Table Users;");
                        instance.statement.execute("DROP SCHEMA " + instance.schemaName + ";");
                        instance.statement.close();
                        instance.connection.close();
                    } catch (SQLException e) {
                        System.out.println(String.format(instance.CONNECTION_ERROR, e.toString()));
                    }
                }
            }
        }
    }

    private void runQuery(String query) {
        try {
            statement.execute(query);
        } catch (SQLException e) {
            System.out.println(String.format(QUERY_ERROR, e.toString()));
        }
    }

    private List<User> runRequest(String query) {
        List<User> result = new ArrayList<>();
        try {
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                result.add(new User(resultSet.getString(2),
                        resultSet.getString(3), resultSet.getString(4),
                        resultSet.getString(5)).setId(Long.valueOf(resultSet.getString(1))));
            }
            resultSet.close();
        } catch (SQLException e) {
            System.out.println(String.format(QUERY_ERROR, e.toString()));
        }
        return result;
    }

    public void createSchema() {
        runQuery("CREATE SCHEMA " + schemaName + ";");
    }

    public void useSchema() {
        runQuery("USE " + schemaName + ";");
    }

    public void createTableUsers() {
        runQuery("CREATE TABLE Users (" + " `id` INT NOT NULL AUTO_INCREMENT,"
                + " `firstName` VARCHAR(20) NOT NULL,"
                + " `lastName` VARCHAR(20) NOT NULL," 
                + " `companyName` VARCHAR(20) NOT NULL,"
                + " `roleName` VARCHAR(20) NOT NULL," 
                + " PRIMARY KEY (`id`),"
                + " UNIQUE INDEX `id_ROLES` (`id` ASC));");
    }

    public List<User> getByRole(String roleName) {
        return runRequest("SELECT * FROM Users WHERE roleName='" + roleName + "';");
    }

    public void add(User user) {
        runQuery("INSERT INTO Users (firstName, lastName, companyName, roleName) VALUES ('" 
                + user.getFirstName() + "', '" 
                + user.getLastName() + "', '" 
                + user.getCompanyName() + "', '" 
                + user.getRoleName() + "');");
    }

    public void delete(User user) {
        if (user.getId() > 0) {
            runQuery("DELETE FROM Users WHERE id=" + user.getId() + ";");
        } else {
            runQuery("DELETE FROM Users WHERE firstName='" + user.getFirstName()
            + "' AND lastName='" + user.getLastName()
            + "' AND companyName='" + user.getCompanyName()
            + "' AND roleName='" + user.getRoleName()
            + "';");
        }
    }

    public List<User> getAll() {
        return runRequest("SELECT * FROM Users;");
    }

}
