package bbm.brickbreaker;

import basicneuralnetwork.NeuralNetwork;

import java.awt.*;
import java.util.*;
import java.util.List;

import static bbm.brickbreaker.Bounds.*;

public class NetworkController {
    private final Ball ball;
    private final Paddle paddle;
    private final int radius;
    private static final int NUM_GENERATIONS = 10;
    private static final int GENERATION_SIZE = 1000;
    private static final int NUM_ROUNDS = 1000;
    private ArrayList<NeuralNetwork> newGeneration = new ArrayList<>();
    private final List<NeuralNetwork> top10 = new ArrayList<>();

    public NetworkController(Ball ball, int radius, Paddle paddle) {
        this.ball = ball;
        this.paddle = paddle;
        this.radius = radius;
    }

    public void generate() {
        Random rand = new Random();
        for (int i = 0; i < GENERATION_SIZE; i++) {
            NeuralNetwork network = new NeuralNetwork(1, 4, 2, 2);
            newGeneration.add(network);
        }

        for (int i = 1; i <= NUM_GENERATIONS; i++) {
            System.out.println("Curr gen: " + i);
            paddle.setLocation(rand.nextInt(0, 600), rand.nextInt(0, 600));
            ball.setPosition(rand.nextInt(0, 600), rand.nextInt(0, 600));
            getTop10();
            updateNetwork();
        }
    }

    public void updateNetwork() {
        newGeneration = new ArrayList<>();
        for (int i = 0; i < top10.size(); i++) {
            for (NeuralNetwork network : top10) {
                NeuralNetwork child = top10.get(i).merge(network);
                for (int k = 0; k < 10; k++) {
                    child.mutate(0.1);
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
                double newX = ball.updateX();
                double newY = ball.updateY();

                ball.setPosition(newX, newY);

                Bounds direction = network.movePaddle();

                if (direction == LEFT && paddle.getX() > 0) {
                    paddle.setLocation((int) paddle.getX() - 1, (int) paddle.getY());
                } else if (direction == RIGHT && paddle.getX() < 780) {
                    paddle.setLocation((int) paddle.getX() + 1, (int) paddle.getY());
                }

                Bounds hitDirection = NONE;

                if (ball.hitsWall(radius)) {
                    if (newX - radius <= 0) {
                        hitDirection = LEFT;
                    } else if (newX + radius >= ball.getWidth()) {
                        hitDirection = RIGHT;
                    } else if (newY < 650) {
                        hitDirection = TOP;
                    } else if (newY + radius > 600) {
                        i = NUM_ROUNDS;
                    }
                    if (hitDirection != NONE) {
                        ball.bounceWalls(hitDirection);
                    }
                }
                checkPaddleCollision(network);
            }
            allNetworks.add(network);
        }
        Collections.sort(allNetworks);
        for (int i = 0; i < 10; i++) {
            System.out.println("Score: " + allNetworks.get(i).getScore());
            top10.add(allNetworks.get(i).neuralNetwork());
        }
    }

    public void checkPaddleCollision(Network net) {
        Rectangle paddleBounds = new Rectangle((int) paddle.getX(), (int) paddle.getY(),
                (int) paddle.getWidth(), (int) paddle.getHeight());
        Rectangle ballBounds = new Rectangle((int) ball.getX(), (int) ball.getY(),
                radius * 2, radius * 2);

        if (paddleBounds.intersects(ballBounds)) {
            net.incrementScore();
            double sectionWidth = paddle.getWidth() / 5;
            double section = (ball.getX() - paddle.getX()) / sectionWidth;

            switch ((int) section) {
                case 0:
                    ball.bouncePaddle(LEFT_EDGE);
                    break;
                case 1:
                    ball.bouncePaddle(LEFT);
                    break;
                case 2:
                    ball.bouncePaddle(MIDDLE);
                    break;
                case 3:
                    ball.bouncePaddle(RIGHT);
                    break;
                case 4:
                    ball.bouncePaddle(RIGHT_EDGE);
                    break;
                default:
                    break;
            }
        }
    }
}