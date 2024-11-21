package bbm.brickbreaker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameFrame extends JFrame {

    public GameFrame() {
        setSize(800, 600);
        setTitle("Brick Breaker");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);


        Paddle paddle = new Paddle(350, 520, 100, 20);
        Ball ball = new Ball(45, 800, 600, paddle.getX() + 40, paddle.getY() - 20);
        List<Brick> bricks = createBricks(20, 40, 20);

        GameComponent component = new GameComponent(bricks, ball, paddle);
        component.setBounds(0, 0, 800, 600);
        add(component);

        JLabel bar = new JLabel();
        bar.setOpaque(true);
        bar.setBackground(Color.BLUE);

        bar.setBounds((int) paddle.getX(), (int) paddle.getY(), (int) paddle.getWidth(), (int) paddle.getHeight());
        add(bar);

        Controller controller = new Controller(ball, bricks, 10, paddle, component);
       // NetworkController controller = new NetworkController(ball, 10, paddle);

        bar.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                controller.play();
              //  controller.generate();
            }
        });

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();
                switch (keyCode) {
                    case KeyEvent.VK_LEFT:
                        bar.setLocation(bar.getX() - 20, bar.getY());
                        break;
                    case KeyEvent.VK_RIGHT:
                        bar.setLocation(bar.getX() + 20, bar.getY());
                        break;
                    default:
                        break;
                }
                paddle.setLocation(bar.getX(), bar.getY());
                component.repaint();
            }
        });

        setFocusable(true);
        component.repaint();
    }

    private List<Brick> createBricks(int numBricks, int brickWidth, int brickHeight) {
        List<Brick> bricks = new ArrayList<>();
        Random rand = new Random();

        int maxX = getWidth() - brickWidth;
        int maxY = getHeight() / 2 - brickHeight;

        while (bricks.size() < numBricks) {
            int x = rand.nextInt(maxX);
            int y = rand.nextInt(maxY);

            boolean overlap = false;
            for (Brick b : bricks) {
                if (b.intersects(new Rectangle(x, y, brickWidth, brickHeight))) {
                    overlap = true;
                    break;
                }
            }

            if (!overlap) {
                bricks.add(new Brick(x, y, brickWidth, brickHeight));
            }
        }
        return bricks;
    }
}