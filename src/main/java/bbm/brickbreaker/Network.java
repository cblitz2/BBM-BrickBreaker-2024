package bbm.brickbreaker;

import basicneuralnetwork.NeuralNetwork;

import java.util.ArrayList;
import java.util.Random;

import static bbm.brickbreaker.Bounds.*;


public class Network {
    private final NeuralNetwork network;
    private final Paddle paddle;
    private final Ball ball;

    public Network(NeuralNetwork network, Paddle paddle, Ball ball) {
        this.network = network;
        this.paddle = paddle;
        this.ball = ball;
    }

    public Bounds movePaddle() {
        double[] inputs = new double[2];
        inputs[0] = ball.getX() - paddle.getX();  // Relative position of ball to paddle
        inputs[1] = ball.getY() - paddle.getY();  // Vertical position difference

        double[] outputs = network.guess(inputs);

        if (outputs[0] > outputs[1]) {
            return LEFT;
        } else {
            return RIGHT;
        }
    }
}