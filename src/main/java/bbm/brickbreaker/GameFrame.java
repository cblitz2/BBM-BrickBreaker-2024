package bbm.brickbreaker;

import basicneuralnetwork.NeuralNetwork;

import javax.swing.*;

public class GameFrame extends JFrame {
    private final GameComponent component;
    private Timer gameTimer;
    private JLabel score;

    public GameFrame(NeuralNetwork topNetwork) {
        setSize(800, 600);
        setTitle("Brick Breaker");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);

        Ball ball = new Ball(20, 20, 390, 500);
        Paddle paddle = new Paddle(350, 520, 100, 20);
        BrickFactory brickFactory = new BrickFactory(getWidth(), getHeight(), 40, 25);
        NeuralNetwork network = new NeuralNetwork(topNetwork);

        score = new JLabel();
        score.setText("Score: 0");
        score.setBounds(getWidth() - 110, 10, 100, 30);
        add(score);

        Simulation simulation = new Simulation(network, ball, paddle, brickFactory);
        component = new GameComponent(ball, paddle, simulation);
        component.setBounds(0, 0, getWidth(), getHeight());

        add(component);


        startGameLoop(simulation);
        setFocusable(true);
    }

    private void startGameLoop(Simulation simulation) {
        gameTimer = new Timer(4, e -> {
            simulation.advance();
            score.setText("Score: " + simulation.getScore());
            component.repaint();
            if (simulation.isGameOver()) {
                gameTimer.stop();
            }
        });
        gameTimer.start();
    }
}