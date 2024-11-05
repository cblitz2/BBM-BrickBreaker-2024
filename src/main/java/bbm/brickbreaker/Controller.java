package bbm.brickbreaker;

import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameController {

    // pass into frame width and height
    // pass into brick width and height that is smaller than frame's
    private final Ball ball;
    private final Brick brick;
    private final Panel panel;

    private Timer timer;

    public GameController(Ball ball, Brick brick, Panel panel) {
        this.ball = ball;
        this.brick = brick;
        this.panel = panel;
        // do we want the gameWidth and gameHeight?
        timer = new Timer(1000, e -> {

        });

    }

    public void startTimer() {
        timer.start();
    }

    private void breakBricks(int x, int y) {

        //need logic of how to determine if brick is hit - does this happen in controller?

        brick.hitBrick(x, y);

    }

    private void hitWall() {
        double nextX = ball.locationX();
        double nextY = ball.locationY();
        if (nextX <= 0 || nextX >= gameWidth) {
            ball.bounce();
        }
        if (nextY <= 0) {
            ball.bounce(); // Bounce vertically
        }
    }

    // Stops the game if the ball goes below the panel
    private void gameOver() {
        if (ball.getY() > gameHeight) {
            timer.stop();
            System.out.println("Game Over!");
        }
    }
}
