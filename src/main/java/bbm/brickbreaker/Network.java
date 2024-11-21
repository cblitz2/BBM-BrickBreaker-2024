package bbm.brickbreaker;

import basicneuralnetwork.NeuralNetwork;

import java.util.ArrayList;
import java.util.Random;

import static bbm.brickbreaker.Bounds.*;


public class Network implements Comparable<Network> {
    private final NeuralNetwork network;
    private int score;

    public Network(NeuralNetwork network) {
        this.network = network;
    }

    public Bounds movePaddle() {
        double[] input = new double[1];
        input[0] = 270;
        double[] answer = network.guess(input);
        Bounds direction;

        if (answer[0] > answer[1]) {
            direction = LEFT;
        } else  {
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