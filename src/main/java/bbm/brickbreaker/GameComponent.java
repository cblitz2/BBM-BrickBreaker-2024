package bbm.brickbreaker;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class GameComponent extends JComponent {
    private final List<Brick> bricks;
    private final Ball ball;
    private final Paddle paddle;

    public GameComponent(List<Brick> bricks, Ball ball, Paddle paddle) {
        this.bricks = bricks;
        this.ball = ball;
        this.paddle = paddle;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (Brick brick : bricks) {
            if (!brick.isHit()) {
                g.setColor(Color.RED);
                g.fillRect(brick.x, brick.y, brick.width, brick.height);

                g.setColor(Color.BLACK);
                g.drawRect(brick.x, brick.y, brick.width, brick.height);
            }
        }

        g.setColor(Color.DARK_GRAY);
        g.fillOval((int) ball.getX() - 10, (int) ball.getY()  - 10, 20, 20);

        g.setColor(Color.BLUE);
        g.fillRect((int) paddle.getX(), (int) paddle.getY(), (int) paddle.getWidth(), (int) paddle.getHeight());
    }
}
