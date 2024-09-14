package com.example.java_pong.model;
import java.io.Serializable;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleStringProperty;

import java.io.Serializable;

/**
 * Represents a player and associated info
 */
public class PlayerModel implements Serializable {
    private final StringProperty name;
    private final IntegerProperty score;
    private static int winningScore;
    private static boolean playerWon;

    /**
     * Instantiates a new Player model.
     *
     * @param name the name
     */
    public PlayerModel(String name) {
        this.name = new SimpleStringProperty(name);
        this.score = new SimpleIntegerProperty(0);
        playerWon = false;
        winningScore = 5;
    }

    /**
     * Name string property.
     *
     * @return the string property
     */
    public StringProperty nameProperty() {
        return name;
    }

    /**
     * Score integer property.
     *
     * @return the integer property
     */
    public IntegerProperty scoreProperty() {
        return score;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name.get();
    }

    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name.set(name);
    }

    /**
     * Gets score.
     *
     * @return the score
     */
    public int getScore() {
        return score.get();
    }

    /**
     * Sets winning score.
     *
     * @param newWinningScore the new winning score
     */
    public static void setWinningScore(int newWinningScore) {
        winningScore = newWinningScore;
    }

    /**
     * Get winning score.
     *
     * @return the int
     */
    public static int getWinningScore(){
        return winningScore;
    }

    /**
     * Increment score.
     */
    public void incrementScore() {
        if (score.get() < winningScore)
        {
            score.set(score.get() + 1); // Increment the score by 1
        }
    }

    /**
     * Reset score.
     */
    public void resetScore() {
        score.set(0);
    }

    /**
     * Gets player won.
     *
     * @return the player won
     */
    public static boolean getPlayerWon()
    {
        return playerWon;
    }

    public void setScore(int newScore) {
        score.set(newScore);
    }
}
