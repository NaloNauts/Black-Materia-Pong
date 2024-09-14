package com.example.java_pong;

import com.example.java_pong.model.BallModel;
import com.example.java_pong.model.PaddleModel;
import com.example.java_pong.model.PlayerModel;
import javafx.scene.control.Alert;

import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class GameSaver {
    private static final GameSaver instance = new GameSaver();

    private GameSaver() {
        // Private constructor
    }

    public static GameSaver getInstance() {
        return instance;
    }

    // Method to save the game state using serialization
    public void saveGame(BallModel ballModel, PaddleModel paddleModel1, PaddleModel paddleModel2,
                         PlayerModel playerModel1, PlayerModel playerModel2) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("gameState.ser"))) {
            // Write the game objects we need
            oos.writeObject(ballModel);
            oos.writeObject(paddleModel1);
            oos.writeObject(paddleModel2);
            // write the playermodel properties because colour isn't serialisable
            oos.writeObject(playerModel1.getName());
            oos.writeInt(playerModel1.getScore());
            oos.writeObject(playerModel2.getName());
            oos.writeInt(playerModel2.getScore());
            oos.writeInt(PlayerModel.getWinningScore());


            showAlert(Alert.AlertType.INFORMATION, "Save Successful", null, "Game saved successfully!");
        }
        catch (IOException e)
        {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", null, "Failed to save game state!");
        }
    }


    // Method to load the game state from the serialized file
    public Object[] loadGame() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("gameState.ser"))) {
            // Read the game state objects
            BallModel ballModel = (BallModel) ois.readObject();
            PaddleModel paddleModel1 = (PaddleModel) ois.readObject();
            PaddleModel paddleModel2 = (PaddleModel) ois.readObject();
            String player1Name = (String) ois.readObject();
            int player1Score = ois.readInt();
            String player2Name = (String) ois.readObject();
            int player2Score = ois.readInt();
            int winningScore = ois.readInt();


            // array for the objects
            Object[] gameState = new Object[8];
            gameState[0] = ballModel;
            gameState[1] = paddleModel1;
            gameState[2] = paddleModel2;
            gameState[3] = player1Name;
            gameState[4] = player1Score;
            gameState[5] = player2Name;
            gameState[6] = player2Score;
            gameState[7] = winningScore;

            return gameState;
        }
        catch (IOException | ClassNotFoundException e)
        {
            e.printStackTrace();
            return null;
        }
    }
    


    // Method to show an alert popup window
    private void showAlert(Alert.AlertType alertType, String title, String headerText, String contentText) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.showAndWait();
    }
}
