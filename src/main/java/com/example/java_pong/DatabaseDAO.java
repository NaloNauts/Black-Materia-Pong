package com.example.java_pong;

import com.example.java_pong.model.PlayerModel;

import java.sql.SQLException;

public interface DatabaseDAO {
    void saveGameToDatabase(String player1Name, String player2Name, int player1Score, int player2Score, int gameLimit);
    void loadGameFromDatabaseAndReplacePlayers(PlayerModel player1, PlayerModel player2) throws SQLException;
}