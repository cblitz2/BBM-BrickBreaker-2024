package bbm.brickbreaker;

import java.awt.geom.Ellipse2D;

public class Ball extends Ellipse2D.Double {
    private double dx = 1.5;
    private double dy = -1;

    public Ball(int width, int height, double x, double y) {
        super(x, y, width, height);
    }

    public void move() {
        x += dx;
        y += dy;
    }

    public void setPosition(double newX, double newY) {
        this.x = newX;
        this.y = newY;
    }

    public boolean collidesPaddle(Paddle paddle) {
        return paddle.getBounds().intersects(this.getBounds());
    }

    public boolean collidesBrick (Brick brick) {
        return brick.getBounds().intersects(this.getBounds());
    }

    public boolean hitsWall(int frameWidth) {
        return x <= 0 || x + width >= frameWidth;
    }

    public boolean hitsTop() {
        return y <= 0;
    }

    public void bouncePaddle(Paddle paddle) {
        dy = -dy;
        dx = (paddle.getCenterX() - this.getCenterX()) / (paddle.getWidth() / 2);
    }

    public void bounceWalls() {
        dx = -dx;
    }

    public void bounceTop() {
        dy = -dy;
    }
}