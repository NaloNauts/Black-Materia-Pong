package com.example.java_pong;

import com.example.java_pong.controller.PlayerController;
import com.example.java_pong.model.PlayerModel;
import javafx.scene.control.Alert;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseSaverLoader implements DatabaseDAO {

    // Singleton instance
    private static DatabaseSaverLoader instance;

    // Private constructor
    private DatabaseSaverLoader() {
    }

    // make sure we only have one instance
    public static DatabaseSaverLoader getInstance() {
        if (instance == null) {
            instance = new DatabaseSaverLoader();
        }
        return instance;
    }

    // save game to database
    @Override
    public void saveGameToDatabase(String player1Name, String player2Name,
                                   int player1Score, int player2Score, int gameLimit) {
        try (Connection connection = DatabaseConnector.getConnection())
        {
            String sql = "UPDATE Game SET player1_name = ?, player2_name = ?, player1_score = ?, player2_score = ?, game_limit = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, player1Name);
            statement.setString(2, player2Name);
            statement.setInt(3, player1Score);
            statement.setInt(4, player2Score);
            statement.setInt(5, gameLimit);

            statement.executeUpdate();
            showAlert(Alert.AlertType.INFORMATION, "Save Successful", null, "Game saved successfully!");
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }


    // Method to load game data from the database and replace player objects
    @Override
    public void loadGameFromDatabaseAndReplacePlayers(PlayerModel player1, PlayerModel player2) throws SQLException {
        try (Connection connection = DatabaseConnector.getConnection())
        {
            String sql = "SELECT player1_name, player2_name, player1_score, player2_score, game_limit FROM Game";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            //get record and set data to variables
            if (resultSet.next()) {
                String loadedPlayer1Name = resultSet.getString(1);
                String loadedPlayer2Name = resultSet.getString(2);
                int loadedPlayer1Score = resultSet.getInt(3);
                int loadedPlayer2Score = resultSet.getInt(4);
                int loadedGameLimit = resultSet.getInt(5);

                // Use PlayerModelBuilder to construct new players
                PlayerModelBuilder playerBuilder = new PlayerModelBuilder();
                PlayerModel loadedPlayer1 = playerBuilder.setName(loadedPlayer1Name).setScore(loadedPlayer1Score).build();
                PlayerModel loadedPlayer2 = playerBuilder.setName(loadedPlayer2Name).setScore(loadedPlayer2Score).build();

                // Set the loaded player names and scores
                player1.setName(loadedPlayer1.getName());
                player2.setName(loadedPlayer2.getName());
                player1.setScore(loadedPlayer1.getScore());
                player2.setScore(loadedPlayer2.getScore());

                // Set the loaded game score limit
                PlayerController.setWinningScore(loadedGameLimit);

                showAlert(Alert.AlertType.INFORMATION, "Load Successful", null, "Game data loaded successfully!");
            }
            else
            {
                showAlert(Alert.AlertType.INFORMATION, "No Game Data", null, "No game data found in the database.");
            }
        } catch (SQLException e)
        {
            System.out.println("Error loading game data: " + e.getMessage());
        }
    }

    //Popup Alert Method
    private void showAlert(Alert.AlertType alertType, String title, String headerText, String contentText) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.showAndWait();
    }
}
