package bbm.brickbreaker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import basicneuralnetwork.NeuralNetwork;
import static basicneuralnetwork.utilities.FileReaderAndWriter.writeToFile;

public class TrainAi {
    private static final int NUM_GENERATIONS = 5;
    private static final int NUM_TOP_NETWORKS = 10;
    private static final int GENERATION_SIZE = 1000;
    private static final int NUM_ROUNDS = 10000;
    private final Ball ball;
    private final Paddle paddle;
    private NeuralNetwork topNetwork;
    private final ArrayList<NeuralNetwork> allNetworks = new ArrayList<>();
    private final List<NeuralNetwork> top10 = new ArrayList<>();

    public TrainAi(Ball ball, Paddle paddle) {
        this.ball = ball;
        this.paddle = paddle;
    }

    public void generate() {
        for (int i = 0; i < GENERATION_SIZE; i++) {
            NeuralNetwork network = new NeuralNetwork(2, 2, 4, 2);
            allNetworks.add(network);
        }

        for (int i = 0; i < NUM_GENERATIONS; i++) {
            System.out.println("Curr gen: " + (i + 1));
            getTop10();
            updateNetwork();
        }
        topNetwork = top10.get(0);
    }

    public void getTop10() {
        top10.clear();
        List<Simulation> allSimulations = new ArrayList<>();

        for (NeuralNetwork neuralNetwork : allNetworks) {
            Simulation simulation = new Simulation(neuralNetwork, ball, paddle, 800, 600);
            for (int i = 0; i < NUM_ROUNDS; i++) {
                boolean gameOver = simulation.advance();
                if (gameOver) {
                    break;
                }
            }
            allSimulations.add(simulation);
        }
        Collections.sort(allSimulations);
        for (int i = 0; i < NUM_TOP_NETWORKS; i++) {
            System.out.println("Score: " + allSimulations.get(i).getScore());
            top10.add(allSimulations.get(i).getNetwork());
        }
    }

    public void updateNetwork() {
        Random rand = new Random();
        allNetworks.clear();
        for (int i = 0; i < 1000; i++) {
            NeuralNetwork p1 = top10.get(rand.nextInt(10));
            NeuralNetwork p2 = top10.get(rand.nextInt(10));
            NeuralNetwork child = p1.merge(p2);
            child.mutate(0.1);
            allNetworks.add(child);
        }
    }

    public NeuralNetwork getTopNetwork() {
        return topNetwork;
    }

    public static void main(String[] args) {
        Paddle paddle = new Paddle(350, 520, 100, 20);
        Ball ball = new Ball(10, 10, paddle.getX() + 40, paddle.getY() - 20);
        TrainAi train = new TrainAi(ball, paddle);
        train.generate();
        writeToFile(train.getTopNetwork(), "ai");
    }
}
