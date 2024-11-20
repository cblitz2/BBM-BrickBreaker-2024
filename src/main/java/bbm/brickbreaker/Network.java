package bbm.brickbreaker;

import basicneuralnetwork.NeuralNetwork;

import java.util.ArrayList;
import java.util.Random;

import static bbm.brickbreaker.Bounds.*;


public class Network {
    private final NeuralNetwork network;

    public Network(NeuralNetwork network) {
        this.network = network;
    }

    public Bounds movePaddle() {
        double[] input = new double[1];
        input[0] = 90;
        double[] answer = network.guess(input);
        Bounds direction;

        if (answer[0] > answer[1]) {
            direction = LEFT;
        } else  {
            direction = RIGHT;
        }
        return direction;
    }
}