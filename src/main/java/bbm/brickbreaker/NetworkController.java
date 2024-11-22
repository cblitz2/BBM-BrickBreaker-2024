package bbm.brickbreaker;

import basicneuralnetwork.NeuralNetwork;

import java.awt.*;
import java.util.*;
import java.util.List;

import static basicneuralnetwork.utilities.FileReaderAndWriter.writeToFile;
import static bbm.brickbreaker.Bounds.*;

public class NetworkController {
    private final Ball ball;
    private final Paddle paddle;
    private final int radius;
    private static final int NUM_GENERATIONS = 10;
    private static final int NUM_MUTATIONS = 10;
    private static final int TOP_NETWORKS = 10;
    private static final int GENERATION_SIZE = 1000;
    private static final int NUM_ROUNDS = 1000;
    private static final int PADDLE_MAX_X = 780;
    private static final int BALL_MAX_Y = 650;
    private final int diameter;
    private static final int PADDLE_STEP = 1;
    private static final double MUTATION_RATE = 0.1;
    private final int maxBound = 800;
    private NeuralNetwork topNetwork;


    private ArrayList<NeuralNetwork> newGeneration = new ArrayList<>();
    private final List<NeuralNetwork> top10 = new ArrayList<>();

    public NetworkController(Ball ball, int radius, Paddle paddle) {
        this.ball = ball;
        this.paddle = paddle;
        this.radius = radius;
        diameter = radius * 2;
    }

    public void generate() {
        Random rand = new Random();
        for (int i = 0; i < GENERATION_SIZE; i++) {
            NeuralNetwork network = new NeuralNetwork(1, 4, 2, 2);
            newGeneration.add(network);
        }

        for (int i = 1; i <= NUM_GENERATIONS; i++) {
            System.out.println("Curr gen: " + i);
            paddle.setLocation(rand.nextInt(0, maxBound), rand.nextInt(0, maxBound));
            ball.setPosition(rand.nextInt(0, maxBound), rand.nextInt(0, maxBound));
            getTop10();
            updateNetwork();
        }

        topNetwork = top10.get(0);
    }

    public void updateNetwork() {
        newGeneration = new ArrayList<>();
        for (int i = 0; i < top10.size(); i++) {
            for (NeuralNetwork network : top10) {
                NeuralNetwork child = top10.get(i).merge(network);
                for (int k = 0; k < NUM_MUTATIONS; k++) {
                    child.mutate(MUTATION_RATE);
                    newGeneration.add(child);
                }
            }
        }
    }

    public void getTop10() {
        ArrayList<Network> allNetworks = new ArrayList<>();

        for (NeuralNetwork neuralNetwork : newGeneration) {
            Network network = new Network(neuralNetwork);
            for (int i = 0; i < NUM_ROUNDS; i++) {
                play(network);
            }
            allNetworks.add(network);
        }
        Collections.sort(allNetworks);
        for (int i = 0; i < TOP_NETWORKS; i++) {
            System.out.println("Score: " + allNetworks.get(i).getScore());
            top10.add(allNetworks.get(i).neuralNetwork());
        }
    }

    public void checkPaddleCollision(Network net) {
        Rectangle paddleBounds = new Rectangle((int) paddle.getX(), (int) paddle.getY(),
                (int) paddle.getWidth(), (int) paddle.getHeight());
        Rectangle ballBounds = new Rectangle((int) ball.getX(), (int) ball.getY(),
                diameter, diameter);

        if (paddleBounds.intersects(ballBounds)) {
            net.incrementScore();
            double sectionWidth = paddle.getWidth() / paddle.getPaddleSections();
            double section = (ball.getX() - paddle.getX()) / sectionWidth;

            switch ((int) section) {
                case 0 -> ball.bouncePaddle(LEFT_EDGE);
                case 1 -> ball.bouncePaddle(LEFT);
                case 2 -> ball.bouncePaddle(MIDDLE);
                case 3 -> ball.bouncePaddle(RIGHT);
                case 4 -> ball.bouncePaddle(RIGHT_EDGE);
                default -> {
                }
            }
        }
    }

    public NeuralNetwork getTopNetwork() {
        return topNetwork;
    }

    public void setTopNetwork(NeuralNetwork network) {
        this.topNetwork = network;
    }

    public void play(Network network) {
        double newX = ball.updateX();
        double newY = ball.updateY();

        ball.setPosition(newX, newY);

        Bounds direction = network.movePaddle();

        if (direction == LEFT && paddle.getX() > 0) {
            paddle.setLocation((int) paddle.getX() - PADDLE_STEP, (int) paddle.getY());
        } else if (direction == RIGHT && paddle.getX() < PADDLE_MAX_X) {
            paddle.setLocation((int) paddle.getX() + PADDLE_STEP, (int) paddle.getY());
        }

        Bounds hitDirection = NONE;

        if (ball.hitsWall(radius)) {
            if (newX - radius <= 0) {
                hitDirection = LEFT;
            } else if (newX + radius >= ball.getWidth()) {
                hitDirection = RIGHT;
            } else if (newY < BALL_MAX_Y) {
                hitDirection = TOP;
            } else if (newY + radius > maxBound) {
                hitDirection = NONE;
            }
            if (hitDirection != NONE) {
                ball.bounceWalls(hitDirection);
            }
        }
        checkPaddleCollision(network);
    }

}
