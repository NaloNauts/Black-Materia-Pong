package com.example.java_pong.model;
import java.io.Serializable;
import javafx.scene.paint.Color;

/**
 * Represents a Paddle
 */
public class PaddleModel implements Serializable {
    private double startX;
    private double startY;
    private double endX;
    private double endY;
    private transient Color color;
    private double speed;

    /**
     * Instantiates a new Paddle model.
     *
     * @param startX the start x
     * @param startY the start y
     * @param endX   the end x
     * @param endY   the end y
     * @param color  the color
     */
    public PaddleModel(double startX, double startY, double endX, double endY, Color color) {
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
        this.color = color;
        this.speed = 10;
    }

    /**
     * Gets start x.
     *
     * @return the start x
     */
    public double getStartX() {
        return startX;
    }

    /**
     * Sets start x.
     *
     * @param startX the start x
     */
    public void setStartX(double startX) {
        this.startX = startX;
    }


    /**
     * Gets start y.
     *
     * @return the start y
     */
    public double getStartY() {
        return startY;
    }

    /**
     * Sets start y.
     *
     * @param startY the start y
     */
    public void setStartY(double startY) {
        this.startY = startY;
    }

    /**
     * Gets end x.
     *
     * @return the end x
     */
    public double getEndX() {
        return endX;
    }


    /**
     * Gets end y.
     *
     * @return the end y
     */
    public double getEndY() {
        return endY;
    }

    /**
     * Sets end y.
     *
     * @param endY the end y
     */
    public void setEndY(double endY) {
        this.endY = endY;
    }

    /**
     * Sets end x.
     *
     * @param endX the end x
     */
    public void setEndX(double endX) {
        this.endX = endX;
    }


    /**
     * Gets color.
     *
     * @return the color
     */
    public Color getColor() {
        return color;
    }

    /**
     * Sets color.
     *
     * @param color the color
     */
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * Gets height.
     *
     * @return the height
     */
    public double getHeight() {
        return endY - startY;
    }

    /**
     * Gets speed.
     *
     * @return the speed
     */
    public double getSpeed() {
        return speed;
    }
}
