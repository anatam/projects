package com.anabet.registery;

import com.anabet.database.ConnectionToDB;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class User {
    private int userId;
    private boolean isAdmin;
    private String name;
    private String pass;

    Connection connection;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public int getId(String name, String pass) {
        setName(name);
        setPass(pass);
        try {
            connection = ConnectionToDB.getConnectionToDB();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        String query = "SELECT user_id FROM ana_bet.user where name = '" + getName() + "' and pass = '" + getPass() + "'";
        int userId = 0;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery(query);
            if (!resultSet.next()) {
                throw new IllegalArgumentException("this user doesn't exist.");
            }
            userId = resultSet.getInt("user_id");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return userId;
    }
    public Boolean validate(String name , String pass){
        setName(name);
        setPass(pass);
//        Scanner scanner = new Scanner(System.in);
//        System.out.println("please enter your name: ");
//        String name = scanner.next();
//        setName(name);
//        System.out.println("please enter your password: ");
//        String pass = scanner.next();
//        setPass(pass);
        try {
            connection = ConnectionToDB.getConnectionToDB();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        String query = "SELECT is_admin FROM ana_bet.user where name = '" + getName() + "' and pass = '" + getPass() + "'";
        Boolean admin = false;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery(query);
            if (!resultSet.next()) {
                throw new IllegalArgumentException("this user doesn't exist.");
            }
            admin = resultSet.getBoolean("is_admin");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return admin;
    }
}
