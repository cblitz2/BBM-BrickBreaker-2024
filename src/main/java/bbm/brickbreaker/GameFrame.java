package bbm.brickbreaker;

import basicneuralnetwork.NeuralNetwork;

import javax.swing.*;

public class GameFrame extends JFrame {
    private final GameComponent component;
    private Timer gameTimer;

    public GameFrame(NeuralNetwork topNetwork) {
        setSize(800, 600);
        setTitle("Brick Breaker");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);

        NeuralNetwork network = new NeuralNetwork(topNetwork);
        Ball ball = new Ball(20, 20, 390, 500);
        Paddle paddle = new Paddle(350, 520, 100, 20);

        component = new GameComponent(ball, paddle);
        component.setBounds(0, 0, getWidth(), getHeight());

        add(component);

        Simulation simulation = new Simulation(network, ball, paddle, getWidth(), getHeight());

        startGameLoop(simulation);
        setFocusable(true);
    }

    private void startGameLoop(Simulation simulation) {
        gameTimer = new Timer(4, e -> {
            simulation.advance();
            component.repaint();
            if (simulation.isGameOver()) {
                gameTimer.stop();
            }
        });
        gameTimer.start();
    }
}