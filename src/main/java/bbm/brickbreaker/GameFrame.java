package bbm.brickbreaker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

public class GameFrame extends JFrame {

    public GameFrame() {
        setSize(800, 600);
        setTitle("Brick Breaker");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);

        Panel panel = new Panel(350, 520);
        Ball ball = new Ball(45, 800, 600, panel.getX() + 48, panel.getY() - 20);
        Bricks brick = new Bricks(30, 18);
        brick.populateBricks();

        GameComponent component = new GameComponent(brick, ball, panel);
        component.setBounds(0, 0, 800, 600);
        add(component);

        JLabel bar = new JLabel();
        bar.setOpaque(true);
        bar.setBackground(Color.BLUE);
        bar.setBounds(panel.getX(), panel.getY(), panel.getWidth(), panel.getHeight());
        add(bar);

        int[] offsetX = {0};
        int[] offsetY = {0};

        Controller controller = new Controller(ball, brick, 10, panel, component);

        bar.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                controller.play();
            }
        });

        bar.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                offsetX[0] = e.getX();
                offsetY[0] = e.getY();
            }
        });

        bar.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                int newX = e.getXOnScreen() - offsetX[0];
                int newY = bar.getY();
                bar.setLocation(newX, newY);
                panel.setLocation(newX, newY);
            }
        });

        component.repaint();
    }
}