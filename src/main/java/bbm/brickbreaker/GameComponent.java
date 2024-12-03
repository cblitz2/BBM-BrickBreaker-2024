package bbm.brickbreaker;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class GameComponent extends JComponent {
    private final Ball ball;
    private final Paddle paddle;

    public GameComponent(Ball ball, Paddle paddle) {
        this.ball = ball;
        this.paddle = paddle;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.DARK_GRAY);
        g.fillOval((int) ball.getX(), (int) ball.getY(), 20, 20);

        g.setColor(Color.BLUE);
        g.fillRect((int) paddle.getX(), (int) paddle.getY(), (int) paddle.getWidth(), (int) paddle.getHeight());
    }
}
