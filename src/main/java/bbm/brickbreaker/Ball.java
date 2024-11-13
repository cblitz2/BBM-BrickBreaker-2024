package bbm.brickbreaker;

import java.awt.geom.Ellipse2D;

public class Ball extends Ellipse2D.Double {
    private static final int VELOCITY = 10;
    private int angle;

    public Ball(int angle, int width, int height, double x, double y) {
        super(x, y, width, height);
        this.angle = angle;
    }

    public void setPosition(double newX, double newY) {
        this.x = newX;
        this.y = newY;
    }

    public double locationX() {
        return getX() + Math.cos(Math.toRadians(angle)) * VELOCITY;
    }

    public double locationY() {
        return getY() + Math.sin(Math.toRadians(angle)) * VELOCITY;
    }

    public void bounceWalls(Bounds direction) {
        switch (direction) {
            case LEFT, RIGHT ->
                    angle = (180 - angle) % 360;
            case TOP ->
                    angle = (360 - angle) % 360;
            default -> {
            }
        }
    }

    public void bouncePaddle(Bounds direction) {
        switch (direction) {
            case LEFT ->
                    angle = 315;
            case RIGHT ->
                    angle = 225;
            case LEFT_EDGE ->
                    angle = 200;
            case RIGHT_EDGE ->
                    angle = 340;
            case MIDDLE ->
                    angle = 270;
            default -> {
            }
        }
    }

    public boolean hitsWall(double radius) {
        return (x - radius <= 0 || x + radius >= width
                || y - radius <= 0 || y + radius >= height);
    }

    public int getAngle() {
        return angle;
    }
}