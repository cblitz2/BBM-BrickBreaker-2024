package bbm.brickbreaker;

import javax.swing.Timer;
import java.awt.*;

import static bbm.brickbreaker.Bounds.*;

public class Controller {
    private final Ball ball;
    private final Bricks brick;
    private final Paddle paddle;
    private final GameComponent view;
    private final int radius;

    private Timer timer;

    public Controller(Ball ball, Bricks brick, int radius, Paddle paddle, GameComponent view) {
        this.ball = ball;
        this.brick = brick;
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
                } else if (newY + radius > ball.getHeight()) {
                    timer.stop();
                }

                if (hitDirection != NONE) {
                    ball.bounceWalls(hitDirection);
                }
            }

            checkPaddleCollision();
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

                        ball.bounceWalls(TOP);

                        view.repaint();
                    }
                }
            }
        }
    }

    private void checkPaddleCollision() {
        Rectangle paddleBounds = new Rectangle((int) paddle.getX(), (int) paddle.getY(),
                (int) paddle.getWidth(), (int) paddle.getHeight());
        Rectangle ballBounds = new Rectangle((int) ball.getX(), (int) ball.getY(),
                radius * 2, radius * 2);


        if (paddleBounds.intersects(ballBounds)) {
            int sectionWidth = (int) paddle.getWidth() / 5;
            int section = ((int) ball.getX() - (int) paddle.getX()) / sectionWidth;

            switch (section) {
                case 0:
                    ball.bouncePaddle(LEFT_EDGE);
                    break;
                case 1:
                    ball.bouncePaddle(LEFT);
                    break;
                case 2:
                    ball.bouncePaddle(MIDDLE);
                    break;
                case 3:
                    ball.bouncePaddle(RIGHT);
                    break;
                case 4:
                    ball.bouncePaddle(RIGHT_EDGE);
                    break;
            }
        }
    }
}