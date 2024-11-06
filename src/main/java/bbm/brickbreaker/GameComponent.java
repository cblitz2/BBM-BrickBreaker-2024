package bbm.brickbreaker;

import javax.swing.*;
import java.awt.*;

public class GameComponent extends JComponent {
    private final Brick brick;
    private final Ball ball;

    private final Panel panel;

    public GameComponent(Brick brick, Ball ball, Panel panel) {
        this.brick = brick;
        this.ball = ball;
        this.panel = panel;
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
        g.fillOval((int)ball.getX() - 10, (int)ball.getY()  - 10, 20, 20); // Draw ball at its current position


        g.setColor(Color.BLUE);
        g.fillRect(panel.getX(),  panel.getY(), panel.getWidth(), panel.getHeight());

    }
}