package bbm.brickbreaker;

import javax.swing.Timer;
import java.awt.*;
import static bbm.brickbreaker.Bounds.*;

public class Controller {
    private final Ball ball;
    private final Bricks brick;
    private final Panel panel;
    private GameComponent view;

    private int radius;

    private Timer timer;

    public Controller(Ball ball, Bricks brick, int radius, Panel panel, GameComponent view) {
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
            breakBricks();
        });

        timer.start();
    }

    private void breakBricks() {
        int ballCenterX = (int) ball.getX();
        int ballCenterY = (int) ball.getY();

        for (int i = 0; i < brick.getCols(); i++) {
            for (int j = 0; j < brick.getRows(); j++) {
                if (brick.isBrick(i, j)) {
                    int brickX = i * 30;
                    int brickY = j * 18;

                    if (ballCenterX >= brickX && ballCenterX <= brickX + 30
                            && ballCenterY >= brickY && ballCenterY <= brickY + 18) {

                        brick.hitBrick(i, j);

                        ball.bounce(Bounds.TOP);

                        view.repaint();
                    }
                }
            }
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
