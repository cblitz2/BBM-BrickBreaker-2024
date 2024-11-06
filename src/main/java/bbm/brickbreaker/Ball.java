package bbm.brickbreaker;

import java.awt.geom.Ellipse2D;

public class Ball extends Ellipse2D.Double {
    private static final int VELOCITY = 20;
    private int angle;
    private final int width;
    private final int height;
    private double x;
    private double y;

    public Ball(int angle, int width, int height) {
        this.angle = angle;
        this.width = width;
        this.height = height;
    }

    public void setPosition(double newX, double newY) {
        this.x = newX;
        this.y = newY;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double locationX() {
        return getX() + Math.cos(Math.toRadians(angle)) * VELOCITY;
    }

    public double locationY() {
        return getY() + Math.sin(Math.toRadians(angle)) * VELOCITY;
    }

    public int bounce(Bounds direction) {
        switch (direction) {
            case LEFT, RIGHT -> {
                angle = (180 - angle) % 360;
            }
            case TOP, BOTTOM -> {
                angle = (360 - angle) % 360;
            }
            default -> {
                angle = NONE;
            }
        }
        return angle;
    }



    public boolean inBounds(double x, double y) {
        return x < width && x > 0 && y < height && y > 0;
    }

    public boolean hitsWall(double radius) {
        return (x - radius <= 0 || x + radius >= width
                || y - radius <= 0 || y + radius >= height);
    }

}