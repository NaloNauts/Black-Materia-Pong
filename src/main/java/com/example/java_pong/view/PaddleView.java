package com.example.java_pong.view;

import com.example.java_pong.model.PaddleModel;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Line;

/**
 * Represents the view for the paddle object
 */
public class PaddleView extends Line {
    private PaddleModel model;

    /**
     * Instantiates a new Paddle view.
     *
     * @param model the model
     */
    public PaddleView(PaddleModel model)
    {
        super(model.getStartX(), model.getStartY(), model.getEndX(), model.getEndY());
        setStrokeWidth(10);
        setStroke(model.getColor());
        this.model = model;
    }

    /**
     * Gets node.
     *
     * @return the node
     */
    public Line getNode() {
        return this;
    }

    /**
     * Create paddle size buttons h box.
     *
     * @param scene        the scene
     * @param paddleModel1 the paddle model 1
     * @param paddleModel2 the paddle model 2
     * @param paddleView1  the paddle view 1
     * @param paddleView2  the paddle view 2
     * @return the h box
     */
    public static HBox createPaddleSizeButtons(Scene scene, PaddleModel paddleModel1, PaddleModel paddleModel2, PaddleView paddleView1, PaddleView paddleView2) {
        Button smallButton = new Button("Small Racket");
        Button mediumButton = new Button("Medium Racket");
        Button largeButton = new Button("Large Racket");

        // Define actions for small, medium, and large buttons to update paddle size
        smallButton.setOnAction(event -> {
            paddleModel1.setEndY(100);
            paddleModel2.setEndY(100);
            paddleView1.setEndY(100);
            paddleView2.setEndY(100);
        });

        mediumButton.setOnAction(event -> {
            paddleModel1.setEndY(150);
            paddleModel2.setEndY(150);
            paddleView1.setEndY(150);
            paddleView2.setEndY(150);
        });

        largeButton.setOnAction(event -> {
            paddleModel1.setEndY(200);
            paddleModel2.setEndY(200);
            paddleView1.setEndY(200);
            paddleView2.setEndY(200);
        });

        HBox paddleSizeButtons = new HBox(10);
        paddleSizeButtons.getChildren().addAll(smallButton, mediumButton, largeButton);
        paddleSizeButtons.setAlignment(Pos.CENTER);

        return paddleSizeButtons;
    }

    /**
     * Gets height.
     *
     * @return the height
     */
    public double getHeight()
    {
        return Math.abs(model.getEndY() - model.getStartY());
    }

    /**
     * Update paddle.
     */
    public void updatePaddle()
    {
        setEndX(model.getEndX());
        setStartX(model.getStartX());
        setStartY(model.getStartY());
        setEndY(model.getEndY());
    }


    public void setModel(PaddleModel model) {
        this.model = model;
    }
}
