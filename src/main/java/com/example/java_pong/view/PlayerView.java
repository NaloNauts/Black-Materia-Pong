package com.example.java_pong.view;


import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import com.example.java_pong.controller.PlayerController;
import com.example.java_pong.model.PlayerModel;

/**
 * Represents the view for the PLayer objects
 */
public class PlayerView extends VBox {

    private TextField name;
    private Label nameLabel;
    private Label scoreLabel;
    private Color playerColour;
    private PlayerModel model;


    /**
     * Instantiates a new Player view.
     *
     * @param model        the model
     * @param promptText   the prompt text
     * @param playerColour the player colour
     */
    public PlayerView(PlayerModel model, String promptText, Color playerColour) {
        this.playerColour = playerColour;
        this.model = model;

        name = new TextField();
        name.setPromptText(promptText);
        model.nameProperty().bindBidirectional(name.textProperty());
        this.getChildren().add(name);

        nameLabel = new Label("");
        nameLabel.textProperty().bind(model.nameProperty());
        nameLabel.setTextFill(playerColour);
        nameLabel.setFont(Font.font(20));
        this.getChildren().add(nameLabel);

        scoreLabel = new Label();
        scoreLabel.textProperty().bind(model.scoreProperty().asString());
        scoreLabel.setTextFill(playerColour);
        scoreLabel.setFont(Font.font(50));
        this.getChildren().add(scoreLabel);



    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name.getText();
    }

    /**
     * Gets name label.
     *
     * @return the name label
     */
    public Label getNameLabel() {
        return nameLabel;
    }

    /**
     * Create winning score buttons h box.
     *
     * @param scene the scene
     * @return the h box
     */
// Method to create winning score buttons
    public static HBox createWinningScoreButtons(Scene scene) {
        Label setIncrease = new Label("Play to how many goals?");
        setIncrease.setTextFill(Color.BLACK);
        Button five = new Button("5");
        Button ten = new Button("10");
        Button twenty = new Button("20");

        five.setOnAction(event -> {
            // Set the winning score to 5
            PlayerController.setWinningScore(5);
        });

        ten.setOnAction(event -> {
            // Set the winning score to 10
            PlayerController.setWinningScore(10);
        });

        twenty.setOnAction(event -> {
            // Set the winning score to 20
            PlayerController.setWinningScore(20);
        });

        HBox winningScoreButtons = new HBox(10);
        winningScoreButtons.getChildren().addAll(setIncrease, five, ten, twenty);
        winningScoreButtons.setAlignment(Pos.CENTER);
        return winningScoreButtons;
    }

    /**
     * Hide input field.
     */
    public void hideInputField() {
        name.setVisible(false);
        name.setManaged(false);
    }

    /**
     * Gets model.
     *
     * @return the model
     */
    public PlayerModel getModel() {
        return model;
    }

    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name.setText(name);
    }

    public void setModel(PlayerModel newPlayerModel) {
        // Update the model reference
        this.model = newPlayerModel;
    }
}
