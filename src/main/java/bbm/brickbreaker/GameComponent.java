package bbm.brickbreaker;

import javax.swing.*;
import java.awt.*;

public class GameComponent extends JComponent {
    private final Ball ball;
    private final Paddle paddle;
    private final Simulation simulation;


    public GameComponent(Ball ball, Paddle paddle, Simulation simulation) {
        this.ball = ball;
        this.paddle = paddle;
        this.simulation = simulation;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Brick brick = simulation.getBrick();
        if (!brick.isHit()) {
            g.setColor(Color.RED);
            g.fillRect(brick.x, brick.y, brick.width, brick.height);

            g.setColor(Color.BLACK);
            g.drawRect(brick.x, brick.y, brick.width, brick.height);
        }

        g.setColor(Color.DARK_GRAY);
        g.fillOval((int) ball.getX(), (int) ball.getY(), 20, 20);

        g.setColor(Color.BLUE);
        g.fillRect((int) paddle.getX(), (int) paddle.getY(), (int) paddle.getWidth(), (int) paddle.getHeight());
    }
}
