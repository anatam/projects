package com.anabet.match;

import com.anabet.database.ConnectionToDB;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Match {

    private int idMatch;
    private String firstTeam;
    private String secondTeam;
    private int firstTeamResult;
    private int secondTeamResult;
    private String result;

    Connection connection;


    public int getIdMatch() {
        return idMatch;
    }

    public void setIdMatch(int idMatch) {
        this.idMatch = idMatch;
    }

    public String getFirstTeam() {
        return firstTeam;
    }

    public void setFirstTeam(String firstTeam) {
        this.firstTeam = firstTeam;
    }

    public String getSecondTeam() {
        return secondTeam;
    }

    public void setSecondTeam(String secondTeam) {
        this.secondTeam = secondTeam;
    }

    public int getFirstTeamResult() {
        return firstTeamResult;
    }

    public void setFirstTeamResult(int firstTeamResult) {
        this.firstTeamResult = firstTeamResult;
    }

    public int getSecondTeamResult() {
        return secondTeamResult;
    }

    public void setSecondTeamResult(int secondTeamResult) {
        this.secondTeamResult = secondTeamResult;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }


    public List<String> showNotHeldMatch() {
        List<String> notHeldMatches = new ArrayList<>();
        try {
            connection = ConnectionToDB.getConnectionToDB();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        String query = "SELECT id_match,first_team,second_team FROM ana_bet.match WHERE first_team_result is null and second_team_result is null";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery(query);
            while (resultSet.next()) {
                String matchInfo = "there gonna be a match with id: "+ resultSet.getString("id_match") +
                        " is between: " + resultSet.getString("first_team") + " - "
                        + resultSet.getString("second_team");
                notHeldMatches.add(matchInfo);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        System.out.println(notHeldMatches);
        return notHeldMatches;
    }

    public List<String> showHeldMatch() {
        List<String> heldMatches = new ArrayList<>();
        try {
            connection = ConnectionToDB.getConnectionToDB();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        String query = "SELECT id_match,first_team,second_team,first_team_result,second_team_result FROM ana_bet.match WHERE first_team_result is not null and second_team_result is not null";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery(query);
            while (resultSet.next()) {
                String matchInfo = "there was a match with id: "+ resultSet.getString("id_match") +
                        " is between: " + resultSet.getString("first_team") + " - "
                        + resultSet.getString("second_team")  + " and result was : " + resultSet.getString("first_team_result")
                        + " - " + resultSet.getString("second_team_result")  ;
                heldMatches.add(matchInfo);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        System.out.println(heldMatches);
        return heldMatches;
    }

    public void addMatch() {
        try {
            connection = ConnectionToDB.getConnectionToDB();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        Scanner scanner = new Scanner(System.in);
        System.out.println("please enter first team name: ");
        firstTeam = scanner.next();
        if (firstTeam instanceof String && firstTeam != null) {
            setFirstTeam(firstTeam);
        } else {
            throw new InputMismatchException("you can not enter anything but string");
        }

        System.out.println("please enter the name of the second team: ");
        secondTeam = scanner.next();
        if (secondTeam instanceof String && secondTeam != null) {
            setSecondTeam(secondTeam);
        } else {
            throw new InputMismatchException("you can not enter anything but string");
        }

        String query = "INSERT INTO ana_bet.match (first_team ,second_team) VALUES ('" + getFirstTeam() + "','" + getSecondTeam() + "')";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

        public void addMatchResult() {
            System.out.println("please enter id of the match you wanna set the result for: ");
            showNotHeldMatch();
            try {
                connection = ConnectionToDB.getConnectionToDB();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            Scanner scanner = new Scanner(System.in);
            idMatch = scanner.nextInt();
            if (idMatch == (int) idMatch) {
                setIdMatch(idMatch);
            }
            else {
                throw new InputMismatchException("you can not enter anything but integer");
            }
            System.out.println("please enter first team result: ");
            firstTeamResult = scanner.nextInt();
            if (firstTeamResult == (int) firstTeamResult) {
                setFirstTeamResult(firstTeamResult);
            }
            else {
                throw new InputMismatchException("you can not enter anything but integer");
            }

            System.out.println("please enter second team result: ");
            secondTeamResult = scanner.nextInt();
            if (secondTeamResult == (int) secondTeamResult) {
                setSecondTeamResult(secondTeamResult);
            }
            else {
                throw new InputMismatchException("you can not enter anything but integer");
            }

            String query = "UPDATE ana_bet.match set first_team_result = " + getFirstTeamResult() + " ,second_team_result = " + getSecondTeamResult() + " where id_match = " + getIdMatch() ;
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.executeUpdate();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
    }
}
