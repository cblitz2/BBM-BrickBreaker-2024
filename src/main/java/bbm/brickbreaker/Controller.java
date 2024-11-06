package bbm.brickbreaker;

import javax.swing.Timer;

import java.awt.*;

import static bbm.brickbreaker.Bounds.*;

public class Controller {

    // pass into frame width and height
    // pass into brick width and height that is smaller than frame's
    private final Ball ball;
    private final Brick brick;
    private final Panel panel;
//    private final Panel panel;
    private GameComponent view;

    private int radius;

    private Timer timer;

    public Controller(Ball ball, Brick brick, int radius, Panel panel, GameComponent view) {
        this.ball = ball;
        this.brick = brick;
        this.panel = panel;
        this.radius = radius;
        this.view = view;

        // do we want the gameWidth and gameHeight

    }

    public void play() {
        timer = new Timer(1000 / 60, e -> { // Adjust for smoother movement
            double newX = ball.locationX();
            double newY = ball.locationY();

            ball.setPosition(newX, newY); // Update ball position
            view.repaint();

            // Determine which wall the ball hits
            Bounds hitDirection = NONE; // 0 means no hit

            if (ball.hitsWall(radius)) { // Check if ball hits any wall
                if (newX - radius <= 0) { // Left wall
                    hitDirection = LEFT;
                } else if (newX + radius >= ball.getWidth()) { // Right wall
                    hitDirection = RIGHT;
                } else if (newY - radius <= 0) { // Top wall
                    hitDirection = TOP;
                } else if (newY + radius >= ball.getHeight()) { // Bottom wall
                   // hitDirection = BOTTOM;
                    timer.stop();
                }

                if (hitDirection != NONE) {
                    ball.bounce(hitDirection); // Bounce the ball
                }
            }

            checkPanelCollision();

//            if (!ball.inBounds(newX, newY)) {
//                timer.stop(); // Stop timer if ball goes out of bounds
//            }
        });

        timer.start();
    }

    private void breakBricks(int x, int y) {
        if (brick.isBrick(x, y)) {
            brick.hitBrick(x, y);
            ball.bounce(Bounds.TOP); // Example bounce direction
        }
    }

    private void checkPanelCollision() {
        Rectangle panelBounds = new Rectangle(panel.getX(), panel.getY(), panel.getWidth(), panel.getHeight());
        Rectangle ballBounds = new Rectangle((int) ball.getX(), (int) ball.getY(), radius * 2, radius * 2);

        if (panelBounds.intersects(ballBounds)) {
            ball.bounce(Bounds.TOP); // Example bounce direction, adjust based on angle needed
//            ball.setPosition(ball.getX(), panel.getY() - radius);
        }
    }
}
