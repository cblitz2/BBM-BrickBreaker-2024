package bbm.brickbreaker;

import javax.swing.Timer;

import java.awt.*;

import static bbm.brickbreaker.Bounds.*;

public class Controller {
    private final Ball ball;
    private final Brick brick;
    private final Panel panel;
    private GameComponent view;

    private int radius;

    private Timer timer;

    public Controller(Ball ball, Brick brick, int radius, Panel panel, GameComponent view) {
        this.ball = ball;
        this.brick = brick;
        this.panel = panel;
        this.radius = radius;
        this.view = view;
    }

    public void play() {
        timer = new Timer(1000 / 60, e -> {
            double newX = ball.locationX();
            double newY = ball.locationY();

            ball.setPosition(newX, newY);
            view.repaint();

            Bounds hitDirection = NONE;

            if (ball.hitsWall(radius)) {
                if (newX - radius <= 0) {
                    hitDirection = LEFT;
                } else if (newX + radius >= ball.getWidth()) {
                    hitDirection = RIGHT;
                } else if (newY - radius <= 0) {
                    hitDirection = TOP;
                } else if (newY + radius >= ball.getHeight()) {
                    timer.stop();
                }

                if (hitDirection != NONE) {
                    ball.bounce(hitDirection);
                }
            }

            checkPanelCollision();
        });

        timer.start();
    }

    private void breakBricks(int x, int y) {
        if (brick.isBrick(x, y)) {
            brick.hitBrick(x, y);
            ball.bounce(Bounds.TOP);
        }
    }

    private void checkPanelCollision() {
        Rectangle panelBounds = new Rectangle(panel.getX(), panel.getY(), panel.getWidth(), panel.getHeight());
        Rectangle ballBounds = new Rectangle((int) ball.getX(), (int) ball.getY(), radius * 2, radius * 2);

        if (panelBounds.intersects(ballBounds)) {
            ball.bounce(Bounds.TOP);
        }
    }
}
