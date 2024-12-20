package bbm.brickbreaker;

import java.awt.geom.Ellipse2D;

public class Ball extends Ellipse2D.Double {
    private double dx = 2.5;
    private double dy = -2;

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

    public boolean collidesBrick(Brick brick) {
        return brick.getBounds().intersects(this.getBounds());
    }

    public boolean hitsWall() {
        return x <= 0 || x + width >= 800;
    }

    public boolean hitsTop() {
        return y <= 0;
    }

    public boolean falls() {
        return y >= 600;
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

    public void hitBrick() {
        dy = -dy;
    }
}