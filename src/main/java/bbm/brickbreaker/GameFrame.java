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

//        Paddle paddle = new Paddle(350, 520);
        Paddle paddle = new Paddle(350, 520, 100, 20);
        Ball ball = new Ball(45, 800, 600, paddle.getX() + 40, paddle.getY() - 20);
        Bricks brick = new Bricks(30, 18);
        brick.populateBricks();

        GameComponent component = new GameComponent(brick, ball, paddle);
        component.setBounds(0, 0, 800, 600);
        add(component);

        JLabel bar = new JLabel();
        bar.setOpaque(true);
        bar.setBackground(Color.BLUE);
        bar.setBounds((int) paddle.getX(), (int) paddle.getY(), (int) paddle.getWidth(), (int) paddle.getHeight());
        add(bar);

        int[] offsetX = {0};
        int[] offsetY = {0};

        Controller controller = new Controller(ball, brick, 10, paddle, component);

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
                paddle.setLocation(newX, newY);
            }
        });

        component.repaint();
    }
}