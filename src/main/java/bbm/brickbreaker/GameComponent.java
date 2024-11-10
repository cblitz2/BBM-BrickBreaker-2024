package bbm.brickbreaker;

import javax.swing.*;
import java.awt.*;

public class GameComponent extends JComponent {
    private final Bricks brick;
    private final Ball ball;
    private final Paddle paddle;

    public GameComponent(Bricks brick, Ball ball, Paddle paddle) {
        this.brick = brick;
        this.ball = ball;
        this.paddle = paddle;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int i = 0; i < brick.getCols(); i++) {
            for (int j = 0; j < brick.getRows(); j++) {
                if (brick.isBrick(i, j)) {
                    g.setColor(Color.RED);
                    int xPos = i * 30;
                    int yPos = j * 18;
                    g.fillRect(xPos, yPos, 30, 18);

                    g.setColor(Color.BLACK);
                    g.drawRect(xPos, yPos, 30, 18);
                }
            }
        }

        g.setColor(Color.DARK_GRAY);
        g.fillOval((int) ball.getX() - 10, (int) ball.getY()  - 10, 20, 20);

        g.setColor(Color.BLUE);
        g.fillRect((int) paddle.getX(), (int) paddle.getY(), (int) paddle.getWidth(), (int) paddle.getHeight());
    }
}
