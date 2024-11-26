package bbm.brickbreaker;

import basicneuralnetwork.NeuralNetwork;
import static bbm.brickbreaker.Bounds.*;


public class Network implements Comparable<Network> {
    private final NeuralNetwork network;
    private int score;
    private final Paddle paddle;
    private final Ball ball;

    public Network(NeuralNetwork network, Paddle paddle, Ball ball) {
        this.network = network;
        this.paddle = paddle;
        this.ball = ball;
    }

    public Bounds movePaddle() {
        double[] input = new double[1];
        input[0] = Math.atan2(paddle.getY() - (ball.getY() + ball.getHeight()),
                paddle.getX() + paddle.getCenterX() - ball.getCenterX());
        double[] answer = network.guess(input);
        Bounds direction;

        if (answer[0] > answer[1]) {
            direction = LEFT;
        } else {
            direction = RIGHT;
        }
        return direction;
    }

    public void incrementScore() {
        score++;
    }

    public int getScore()
    {
        return score;
    }

    public NeuralNetwork neuralNetwork() {
        return network;
    }

    @Override
    public int compareTo(Network o) {
        return Integer.compare(o.score, this.score);
    }
}