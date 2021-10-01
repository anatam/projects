package com.anabet.match;

import com.anabet.database.ConnectionToDB;
import com.anabet.registery.User;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class PredictMatch {
    private int matchId;
    private int userId;
    private int firstTeamGoal;
    private int secondTeamGoal;

    Match match = new Match();
    Connection connection;

    public int getMatchId() {
        return matchId;
    }

    public void setMatchId(int matchId) {
        this.matchId = matchId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getFirstTeamGoal() {
        return firstTeamGoal;
    }

    public void setFirstTeamGoal(int firstTeamGoal) {
        this.firstTeamGoal = firstTeamGoal;
    }

    public int getSecondTeamGoal() {
        return secondTeamGoal;
    }

    public void setSecondTeamGoal(int secondTeamGoal) {
        this.secondTeamGoal = secondTeamGoal;
    }

    public void predict(int userId) {
        try {
            connection = ConnectionToDB.getConnectionToDB();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        Scanner scanner = new Scanner(System.in);
        System.out.println("please enter id of the match you wanna predict the result: ");
        match.showNotHeldMatch();
        int matchId = scanner.nextInt();
        if (matchId == (int) matchId) {
            setMatchId(matchId);
        }
        else {
            throw new InputMismatchException("you can not enter anything but integer");
        }
        System.out.println("please enter your predict about first team goals: ");
        int firstTeamGoal = scanner.nextInt();
        if (firstTeamGoal == (int) firstTeamGoal) {
            setFirstTeamGoal(firstTeamGoal);
        }
        else {
            throw new InputMismatchException("you can not enter anything but integer");
        }
        System.out.println("please enter your predict about second team goals: ");
        int secondTeamGoal = scanner.nextInt();
        if (secondTeamGoal == (int) secondTeamGoal) {
            setSecondTeamGoal(secondTeamGoal);
        }
        else {
            throw new InputMismatchException("you can not enter anything but integer");
        }
        String query = "INSERT INTO ana_bet.predict_match (match_id, user_id, first_team_goal, second_team_goal) VALUES ('" +getMatchId() + "','" + userId + "','" + getFirstTeamGoal() + "','" + getSecondTeamGoal() + "')";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    public void givePoint(int userId) {
        try {
            connection = ConnectionToDB.getConnectionToDB();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        String query = "SELECT sum(case when pm.first_team_goal=m.first_team_result and  pm.second_team_goal=m.second_team_result then 3  when pm.first_team_goal=m.first_team_result and  pm.second_team_goal!=m.second_team_result then 1 when pm.first_team_goal!=m.first_team_result and  pm.second_team_goal=m.second_team_result then 1  else 0 end ) point FROM ana_bet.predict_match pm join ana_bet.match m on m.id_match=pm.match_id where user_id= "+userId;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery(query);
            if (resultSet.next()) {
                int point = resultSet.getInt("point");
                System.out.println("your point is: " + point);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }
}
