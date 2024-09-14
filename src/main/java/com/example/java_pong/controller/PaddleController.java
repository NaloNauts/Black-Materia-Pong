package com.example.java_pong.controller;

import com.example.java_pong.model.PaddleModel;
import com.example.java_pong.view.PaddleView;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

/**
 * Handles Paddle logic such as movement
 */
public class PaddleController {
    private PaddleModel model;
    private PaddleView view;

    /**
     * Instantiates a new Paddle controller.
     *
     * @param model the model
     * @param view  the view
     */
    public PaddleController(PaddleModel model, PaddleView view) {
        this.model = model;
        this.view = view;
    }

    /**
     * Move paddle up.
     */
    public void movePaddleUp() {
        double newY = model.getStartY() - model.getSpeed();

        // Check if new position is within bounds
        if (newY >= 0)
        {
            double deltaY = model.getStartY() - newY;
            model.setStartY(newY);
            model.setEndY(model.getEndY() - deltaY); // Move the end Y position up by deltaY
        }
    }


    /**
     * Move paddle down.
     *
     * @param sceneHeight the scene height
     */
    public void movePaddleDown(double sceneHeight) {
        double newY = model.getStartY() + model.getSpeed();

        // Check if new position is within bounds
        if (newY + model.getHeight() <= sceneHeight)
        {
            double deltaY = newY - model.getStartY();
            model.setStartY(newY);
            model.setEndY(model.getEndY() + deltaY); // Move the end Y position down by deltaY
        }
    }

    public void setPaddleModel(PaddleModel model) {
        this.model = model;
    }

    /**
     * Gets paddle model.
     *
     * @return the paddle model
     */
    public PaddleModel getPaddleModel() {
        return model;
    }


    /**
     * Gets paddle y position.
     *
     * @return the paddle y position
     */
    public double getPaddleYPosition() {
        return model.getEndY();
    }

}
