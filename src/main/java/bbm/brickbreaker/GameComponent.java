package bbm.brickbreaker;

import javax.swing.*;
import java.awt.*;

public class GameComponent extends JComponent {
    Brick brick;

    public GameComponent(Brick brick) {
        this.brick = brick;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.RED);
        for (int i = 0; i < brick.getWidth(); i++) {
            for (int j = 0; j < brick.getHeight(); j++) {
                if (brick.isBrick(i, j)) {
                    g.fillRect(i, j, 25, 15);
                }
            }
        }
        //g.setColor(Color.BLUE);
       // g.fillRect(getWidth() / 2 - 15, getHeight() - 30, 40, 10);
    }
}
