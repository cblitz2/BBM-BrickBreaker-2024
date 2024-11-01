package bbm.brickbreaker;

import java.awt.geom.Ellipse2D;

public class Ball extends Ellipse2D.Double {
    private static final int VELOCITY = 20;
    private int angle;
    private final int width;
    private final int height;

    public Ball (int angle, int width, int height) {
        this.angle = angle;
        this.width = width;
        this.height = height;
    }

    public double locationX() {
        return getX() + Math.cos(angle) * VELOCITY;
    }

    public double locationY() {
        return getY() + Math.sin(angle) * VELOCITY;
    }

    public int bounce(int direction) {
        angle = direction + 90;
        return angle;
    }

    public boolean inBounds(int x, int y) {
        return x < width && x > 0 && y < height && y > 0;
    }
}
