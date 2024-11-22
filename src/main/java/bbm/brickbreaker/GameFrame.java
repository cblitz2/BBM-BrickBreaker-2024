package bbm.brickbreaker;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import basicneuralnetwork.NeuralNetwork;
import static basicneuralnetwork.NeuralNetwork.readFromFile;

public class GameFrame extends JFrame {
    private final int width = 800;
    private final int height = 600;

    public GameFrame() throws IOException {
        setSize(width, height);
        setTitle("Brick Breaker");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);


        Paddle paddle = new Paddle(350, 520, 100, 20);
        Ball ball = new Ball(45, width, height, paddle.getX() + 40, paddle.getY() - 20);
        List<Brick> bricks = createBricks(20, 40, 20);

        GameComponent component = new GameComponent(bricks, ball, paddle);
        component.setBounds(0, 0, width, height);
        add(component);

        JLabel bar = new JLabel();
        bar.setOpaque(true);
        bar.setBackground(Color.BLUE);

        bar.setBounds((int) paddle.getX(), (int) paddle.getY(), (int) paddle.getWidth(), (int) paddle.getHeight());
        add(bar);

        // Initialize NetworkController
        NetworkController controller = new NetworkController(ball, 10, paddle);
        Network preTrainedAI = new Network(readFromFile("ai.json"));

        // Load the trained AI from JSON file
        //controller.setTopNetwork(preTrainedAI);

        // Timer to update the game state
        Timer gameLoop = new Timer(16, e -> {
            // Update ball position
            //ball.update();
            controller.play(preTrainedAI);
            //preTrainedAI.movePaddle();
            // Repaint components (UI refresh)
            bar.setLocation((int) paddle.getX(), (int) paddle.getY());
            component.repaint();
        });

        gameLoop.start();
    }


//        Controller controller = new Controller(ball, bricks, 10, paddle, component);
//
//        bar.addMouseListener(new MouseAdapter() {
//            @Override
//            public void mousePressed(MouseEvent e) {
//                controller.play();
//            }
//        });
//
//        addKeyListener(new KeyAdapter() {
//            @Override
//            public void keyPressed(KeyEvent e) {
//                int keyCode = e.getKeyCode();
//                switch (keyCode) {
//                    case KeyEvent.VK_LEFT:
//                        bar.setLocation(bar.getX() - 20, bar.getY());
//                        break;
//                    case KeyEvent.VK_RIGHT:
//                        bar.setLocation(bar.getX() + 20, bar.getY());
//                        break;
//                    default:
//                        break;
//                }
//                paddle.setLocation(bar.getX(), bar.getY());
//                component.repaint();
//            }
//        });
//
//        setFocusable(true);
//        component.repaint();
//    }

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