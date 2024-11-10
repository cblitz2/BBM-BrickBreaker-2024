package bbm.brickbreaker;

import javax.swing.Timer;
import java.awt.*;
import java.util.List;
import static bbm.brickbreaker.Bounds.*;

public class Controller {
    private final Ball ball;
    private final List<Brick> bricks;
    private final Paddle paddle;
    private GameComponent view;

    private int radius;

    private Timer timer;

    public Controller(Ball ball, List<Brick> bricks, int radius, Paddle paddle, GameComponent view) {
        this.ball = ball;
        this.bricks = bricks;
        this.paddle = paddle;
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
            breakBricks();
        });

        timer.start();
    }

    private void breakBricks() {
        Rectangle ballBounds = new Rectangle((int) ball.getX() - radius,
                (int) ball.getY() - radius,
                radius * 2, radius * 2);

        for (Brick brick : bricks) {
            if (!brick.isHit() && brick.getBounds().intersects(ballBounds)) {
                brick.setHit(true);
                ball.bounce(Bounds.TOP);
                view.repaint();
                break;
            }
        }
    }

    private void checkPanelCollision() {
        Rectangle panelBounds = new Rectangle(paddle.getX(), paddle.getY(), paddle.getWidth(), paddle.getHeight());
        Rectangle ballBounds = new Rectangle((int) ball.getX(), (int) ball.getY(), radius * 2, radius * 2);

        if (panelBounds.intersects(ballBounds)) {
            ball.bounce(Bounds.TOP);
        }
    }
}
