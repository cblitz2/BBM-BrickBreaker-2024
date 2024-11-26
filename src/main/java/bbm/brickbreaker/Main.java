package bbm.brickbreaker;

import basicneuralnetwork.NeuralNetwork;

import java.io.IOException;

import static basicneuralnetwork.NeuralNetwork.readFromFile;

public class Main {
    public static void main(String[] args) throws IOException {
        Paddle paddle = new Paddle(350, 520, 100, 20);
        Ball ball = new Ball(45, 800, 600, paddle.getX() + 40, paddle.getY() - 20);
        NeuralNetwork topNetwork = readFromFile("ai.json");
        Network network = new Network(topNetwork, paddle, ball);
        NetworkController networkController = new NetworkController(ball, 10, paddle);

//        new GameFrame().setVisible(true);

        int count = 0;
        while (count < 1000000) {
            networkController.play(network);
            count++;
            System.out.println("game over: " + networkController.isGameOver());
        }
    }
}
