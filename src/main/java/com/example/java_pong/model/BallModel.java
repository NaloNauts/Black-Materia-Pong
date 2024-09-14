package com.example.java_pong.model;
import java.io.Serializable;

/**
 * Represents the model of the pong BAll
 */
public class BallModel implements Serializable {
    private double x;
    private double y;
    private double velocityX;
    private double velocityY;
    private double height;
    private double width;
    private int bouncesUntilIncrease;
    private int bounces;

    /**
     * Instantiates a new Ball model.
     *
     * @param width  the width
     * @param height the height
     */
    public BallModel(double width, double height) {
        this.width = width;
        this.height = height;
        bouncesUntilIncrease = 5;
    }

    /**
     * Gets velocity x.
     *
     * @return the velocity x
     */
    public double getVelocityX() {
        return velocityX;
    }

    /**
     * Gets velocity y.
     *
     * @return the velocity y
     */
    public double getVelocityY() {
        return velocityY;
    }

    /**
     * Sets velocity x.
     *
     * @param velocityX the velocity x
     */
    public void setVelocityX(double velocityX) {
        this.velocityX = velocityX;
    }

    /**
     * Sets velocity y.
     *
     * @param velocityY the velocity y
     */
    public void setVelocityY(double velocityY) {
        this.velocityY = velocityY;
    }

    /**
     * Gets width.
     *
     * @return the width
     */
    public double getWidth() {
        return width;
    }

    /**
     * Sets width.
     *
     * @param width the width
     */
    public void setWidth(double width) {
        this.width = width;
    }

    /**
     * Gets height.
     *
     * @return the height
     */
    public double getHeight() {
        return height;
    }

    /**
     * Sets height.
     *
     * @param height the height
     */
    public void setHeight(double height) {
        this.height = height;
    }

    /**
     * Gets bounces until increase.
     *
     * @return the bounces until increase
     */
    public int getBouncesUntilIncrease()
    {
        return bouncesUntilIncrease;
    }

    /**
     * Sets bounces until increase.
     *
     * @param bouncesUntilIncrease the bounces until increase
     */
    public void setBouncesUntilIncrease(int bouncesUntilIncrease)
    {
        this.bouncesUntilIncrease = bouncesUntilIncrease;
    }

    /**
     * Gets x.
     *
     * @return the x
     */
    public double getX() {
        return x;
    }

    /**
     * Gets y.
     *
     * @return the y
     */
    public double getY() {
        return y;
    }

    /**
     * Sets x.
     *
     * @param x the x
     */
    public void setX(double x) {
        this.x = x;
    }

    /**
     * Sets y.
     *
     * @param y the y
     */
    public void setY(double y) {
        this.y = y;
    }

    /**
     * Reverse x velocity.
     */
    public void reverseXVelocity() {
        velocityX = -velocityX;
    }

    /**
     * Reverse y velocity.
     */
    public void reverseYVelocity() {
        velocityY = -velocityY;
    }

    /**
     * Count bounces.
     */
    public void countBounces()
    {
        bounces++;
    }

    /**
     * Gets bounces.
     *
     * @return the bounces
     */
    public int getBounces()
    {
        return this.bounces;
    }

    /**
     * Reset bounces count.
     */
    public void resetBouncesCount()
    {
        this.bounces = 0;
    }

}
