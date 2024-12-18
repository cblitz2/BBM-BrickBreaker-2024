package bbm.brickbreaker;

import basicneuralnetwork.NeuralNetwork;

import java.io.IOException;

import static basicneuralnetwork.NeuralNetwork.readFromFile;

public class PlayAi {
    public static void main(String[] args) throws IOException {
        NeuralNetwork topNetwork = readFromFile("src/main/resources/ai.json");
        new GameFrame(topNetwork).setVisible(true);
    }
}
