package com.example.java_pong.view;

import com.example.java_pong.controller.BallController;
import com.example.java_pong.model.BallModel;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

/**
 * represents the view for the ball object
 */
public class BallView extends ImageView {
    private ImageView ballImage;

    /**
     * Instantiates a new Ball view.
     *
     * @param image  the image
     * @param width  the width
     * @param height the height
     * @param scene  the scene
     */
    public BallView(Image image, double width, double height, Scene scene)
    {
        super(image);
        ballImage = this;
        setFitWidth(width);
        setFitHeight(height);
    }


    /**
     * Create ball size buttons h box.
     *
     * @param scene          the scene
     * @param ballController the ball controller
     * @return the h box
     */
    public static HBox createBallSizeButtons(Scene scene, BallController ballController) {
        Button smallButton = new Button("Small Ball");
        Button mediumButton = new Button("Medium Ball");
        Button largeButton = new Button("Large Ball");

        smallButton.setOnAction(event -> {
            ballController.setBallSize(20,20);
            //System.out.println("Small button clicked");
        });

        mediumButton.setOnAction(event -> {
            ballController.setBallSize(50,50);
        });

        largeButton.setOnAction(event -> {
            ballController.setBallSize(100,100);
        });

        HBox ballSizeButtons = new HBox(10);
        ballSizeButtons.getChildren().addAll(smallButton, mediumButton, largeButton);

        return ballSizeButtons;
    }

    /**
     * Create velocity buttons h box.
     *
     * @param scene          the scene
     * @param ballController the ball controller
     * @return the h box
     */
    public static HBox createVelocityButtons(Scene scene, BallController ballController) {
        Button increaseX = new Button("Increase X Velocity");
        Button decreaseX = new Button("Decrease X Velocity");
        Button increaseY = new Button("Increase Y Velocity");
        Button decreaseY = new Button("Decrease Y Velocity");

        increaseX.setOnAction(event -> {
            ballController.increaseVelocityX();
        });

        decreaseX.setOnAction(event -> {
            ballController.decreaseVelocityX();
        });

        increaseY.setOnAction(event -> {
            ballController.increaseVelocityY();
        });

        decreaseY.setOnAction(event -> {
            ballController.decreaseVelocityY();
        });

        HBox velocityButtons = new HBox(10);
        velocityButtons.getChildren().addAll(increaseX, decreaseX, increaseY, decreaseY);
        velocityButtons.setAlignment(Pos.CENTER);


        return velocityButtons;
    }

    /**
     * Create velocity increase buttons h box.
     *
     * @param scene          the scene
     * @param ballController the ball controller
     * @return the h box
     */
    public static HBox createVelocityIncreaseButtons(Scene scene, BallController ballController) {
        Label setIncrease = new Label("How many bounces until the ball speed increases?");
        Button five = new Button("5");
        Button ten = new Button("10");
        Button twenty = new Button("20");

        five.setOnAction(event -> {
            ballController.setBouncesUntilIncrease(5);
        });

        ten.setOnAction(event -> {
            ballController.setBouncesUntilIncrease(10);
        });

        twenty.setOnAction(event -> {
            ballController.setBouncesUntilIncrease(20);
        });

        HBox velocityIncreaseButtons = new HBox(10);
        velocityIncreaseButtons.getChildren().addAll(setIncrease, five, ten, twenty);
        velocityIncreaseButtons.setAlignment(Pos.CENTER);

        return velocityIncreaseButtons;
    }

    /**
     * Update ball position.
     *
     * @param newX the new x
     * @param newY the new y
     */
    public void updateBallPosition(double newX, double newY)
    {
        ballImage.layoutXProperty().unbind();
        ballImage.setLayoutX(newX);
        ballImage.layoutYProperty().unbind();
        ballImage.setLayoutY(newY);
    }

    /**
     * Gets ball image.
     *
     * @return the ball image
     */
    public ImageView getBallImage() {
        return ballImage;
    }

    public void setSize(double width, double height) {
        setFitWidth(width);
        setFitHeight(height);
    }
}

