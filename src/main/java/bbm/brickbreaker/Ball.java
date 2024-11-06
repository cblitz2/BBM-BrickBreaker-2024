package bbm.brickbreaker;

import java.awt.geom.Ellipse2D;

public class Ball extends Ellipse2D.Double {
    private static final int VELOCITY = 20;
    private int angle;
    private final int width;
    private final int height;
    private double x;
    private double y;

    public Ball(int angle, int width, int height, double x, double y) {
        this.angle = angle;
        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;
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
            case LEFT, RIGHT ->
                    angle = (180 - angle) % 360;
            case TOP, BOTTOM ->
                    angle = (360 - angle) % 360;
            default -> {}
        }
        return angle;
    }

    public boolean hitsWall(double radius) {
        return (x - radius <= 0 || x + radius >= width
                || y - radius <= 0 || y + radius >= height);
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

}