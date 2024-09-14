package com.example.java_pong.controller;

import com.example.java_pong.model.PlayerModel;
import com.example.java_pong.view.PlayerView;
import javafx.scene.paint.Color;

/**
 * The type Player controller.
 */
public class PlayerController {
    private static PlayerModel model;
    private final PlayerView view;

    /**
     * Instantiates a new Player controller.
     *
     * @param model        the model
     * @param promptText   the prompt text
     * @param playerColour the player colour
     */
    public PlayerController(PlayerModel model, String promptText, Color playerColour) {
        this.view = new PlayerView(model, promptText, playerColour);
    }


    /**
     * Gets view.
     *
     * @return the view
     */
    public PlayerView getView() {
        return view;
    }

    /**
     * Sets winning score.
     *
     * @param score the winning score
     */
    public static void setWinningScore(int score) {
        PlayerModel.setWinningScore(score);
        System.out.println("Setting winning score to " + score);
    }
}
