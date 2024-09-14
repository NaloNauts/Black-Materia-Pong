package com.example.java_pong.controller;

import com.example.java_pong.model.BallModel;
import com.example.java_pong.model.PaddleModel;
import com.example.java_pong.model.PlayerModel;
import com.example.java_pong.view.BallView;
import javafx.scene.Scene;
import javafx.scene.paint.Color;

/**
 * Handles ball logic such as movement
 */
public class BallController {
    private BallModel model;
    private BallView view;

    /**
     * Instantiates a new Ball controller.
     *
     * @param model the model
     * @param view  the view
     */
    public BallController(BallModel model, BallView view) {
        this.model = model;
        this.view = view;
    }

    /**
     * Sets ball size.
     *
     * @param width  the width
     * @param height the height
     */
    public void setBallSize(double width, double height)
    {
        model.setWidth(width);
        model.setHeight(height);
        view.setFitWidth(width);
        view.setFitHeight(height);
        view.layoutXProperty().bind(view.getScene().widthProperty().subtract(width).divide(2));
        view.layoutYProperty().bind(view.getScene().heightProperty().subtract(height).divide(2));
    }

    /**
     * Increase velocity x.
     */
    public void increaseVelocityX()
    {
        model.setVelocityX(model.getVelocityX() + 1);
    }

    /**
     * Decrease velocity x.
     */
    public void decreaseVelocityX() {
        model.setVelocityX(model.getVelocityX() - 1);
    }

    /**
     * Increase velocity y.
     */
    public void increaseVelocityY() {
        model.setVelocityY(model.getVelocityY() + 1);
    }

    /**
     * Decrease velocity y.
     */
    public void decreaseVelocityY() {
        model.setVelocityY(model.getVelocityY() - 1);
    }

    /**
     * Sets bounces until increase.
     *
     * @param bouncesUntilIncrease the bounces until increase
     */
    public void setBouncesUntilIncrease(int bouncesUntilIncrease)
    {
        model.setBouncesUntilIncrease(bouncesUntilIncrease);
    }

    /**
     * Move ball.
     *
     * @param paddleModel1 the paddle model 1
     * @param paddleModel2 the paddle model 2
     * @param playerModel1 the player model 1
     * @param playerModel2 the player model 2
     */
    public void moveBall(PaddleModel paddleModel1, PaddleModel paddleModel2, PlayerModel playerModel1, PlayerModel playerModel2) {
        // Get the current position of the ball
        double currentX = model.getX();
        double currentY = model.getY();

        // Get the current velocity of the ball
        double velocityX = model.getVelocityX();
        double velocityY = model.getVelocityY();

        // Update the position based on the velocity
        double newX = currentX + velocityX;
        double newY = currentY + velocityY;

        if (model.getBounces() >=model.getBouncesUntilIncrease()) {
            adjustBallVelocity();
            model.resetBouncesCount();// Reset the bounce count
        }

        // Check if the ball collides with paddle 1
        if (isCollidingWithPaddle(newX, newY, paddleModel1))
        {
            handlePaddleCollision(paddleModel1);
            //System.out.println("Ball collided with paddle 1");
        }

        // Check if the ball collides with paddle 2
        if (isCollidingWithPaddle(newX, newY, paddleModel2))
        {
            handlePaddleCollision(paddleModel2);
            //System.out.println("Ball collided with paddle 2");
        }

        // Check if the ball leaves the screen horizontally
        if (newX <= -150 || newX >= view.getScene().getWidth() + 150 - view.getBoundsInLocal().getWidth()) {
            if (newX <= 0) {
                // Player 2 scores when the ball leaves from the left side
                System.out.println("Player 2 scores!");
                playerModel2.incrementScore();
            }
            else
            {
                // Player 1 scores when the ball leaves from the right side
                System.out.println("Player 1 scores!");
                playerModel1.incrementScore();
            }

            // Reset the ball's position to the center of the screen
            resetBallPosition();
            resetBallVelocity();
            return;
        }

        // Check if the ball leaves the screen vertically
        if (newY <= 0 || newY >= view.getScene().getHeight() - view.getBoundsInLocal().getHeight()) {
            // Reverse the ball's vertical velocity
            model.setVelocityY(-velocityY);
        }

        // Update the ball's position
        model.setX(newX);
        model.setY(newY);
        view.updateBallPosition(newX, newY);
    }

    /**
     * Adjusts the velocity of the ball based on direction
     */
    private void adjustBallVelocity() {
        // Check the sign of the velocity and adjust it accordingly
        model.setVelocityX(model.getVelocityX() > 0 ? model.getVelocityX() + 1 : model.getVelocityX() - 1);
        model.setVelocityY(model.getVelocityY() > 0 ? model.getVelocityY() + 1 : model.getVelocityY() - 1);
    }

    /**
     * Reset ball velocity.
     */
    public void resetBallVelocity() {
        // Reset ball velocity to initial values
        model.setVelocityX(model.getVelocityX() / 2 + 1);
        model.setVelocityY(model.getVelocityX() / 2 + 1);
    }

    /**
     * checks if the ball is colliding with either paddles
     *
     * @param newX x position of ball
     * @param newY y position of ball
     * @param paddleModel the paddle model
     * @return returns a boolean for if the ball has hit a paddle
     */
    private boolean isCollidingWithPaddle(double newX, double newY, PaddleModel paddleModel) {
        // Calculate the edges of the ball
        double ballRight = newX + model.getWidth();
        double ballBottom = newY + model.getHeight();

        // Calculate the edges of the paddle
        double paddleLeft = paddleModel.getStartX();
        double paddleRight = paddleModel.getEndX();
        double paddleTop = paddleModel.getStartY();
        double paddleBottom = paddleModel.getEndY();

        // Check if the ball is within the visible screen area
        if (ballRight < 0 || newX > view.getScene().getWidth() ||
                ballBottom < 0 || newY > view.getScene().getHeight())
        {
            return false;
        }

        // Check for collision with the paddle within the visible screen area
        if (paddleModel.getColor().equals(Color.RED))
        {
            // Check for collision with paddle 1
            if (newX <= paddleRight && ballRight >= paddleRight &&
                    ballBottom >= paddleTop && newY <= paddleBottom) {
                return true; // Collision detected
            }
        } else if (paddleModel.getColor().equals(Color.BLUE)) {
            // Check for collision with paddle 2
            if (ballRight >= paddleLeft && newX <= paddleLeft &&
                    ballBottom >= paddleTop && newY <= paddleBottom) {
                return true; // Collision detected
            }
        }

        return false;
    }


    /**
     * handles the collision for if a ball does collide with a racket
     *
     * @param paddleModel the model of the paddle the ball collided with
     */
    private void handlePaddleCollision(PaddleModel paddleModel)
    {
        // Reverse the ball's horizontal velocity and increment number of ball bounces
        model.setVelocityX(-model.getVelocityX());
        model.countBounces();
    }

    /**
     * Resets the position of the Ball
     *
     */
    private void resetBallPosition() {
        // Reset the ball's position to the center of the screen after a goal is scored
        double centerX = view.getScene().getWidth() / 2;
        double centerY = view.getScene().getHeight() / 2;
        model.setX(centerX);
        model.setY(centerY);
    }

    public void setBallModel(BallModel loadedBallModel) {
        this.model = loadedBallModel;
    }
}
