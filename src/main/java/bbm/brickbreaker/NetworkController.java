package bbm.brickbreaker;

import basicneuralnetwork.NeuralNetwork;

import java.awt.*;
import java.util.*;
import static bbm.brickbreaker.Bounds.*;

public class NetworkController {
    private final Ball ball;
    private final Paddle paddle;
    private final int radius;
    private static final int NUM_GENERATIONS = 5;
    private static final int GENERATION_SIZE = 1000;
    private static final int NUM_ROUNDS = 10000;
    private ArrayList<NeuralNetwork> newGeneration = new ArrayList<>();
    private ArrayList<NeuralNetwork> top10 = new ArrayList<>();

    public NetworkController(Ball ball, int radius, Paddle paddle) {
        this.ball = ball;
        this.paddle = paddle;
        this.radius = radius;
    }

    public void generate() {
        for (int i = 0; i < GENERATION_SIZE; i++) {
            NeuralNetwork network = new NeuralNetwork(1, 4, 2, 2);
            newGeneration.add(network);
        }

        for (int i = 1; i < NUM_GENERATIONS; i++) {
            System.out.println("Curr gen: " + i);
            paddle.setLocation(paddle.getStartX(), (int) paddle.getY());
            ball.setPosition(ball.getStartX(), ball.getY());
            getTop10();
            updateNetwork();
        }
    }

    public void updateNetwork() {
        newGeneration = new ArrayList<>();
        for (int i = 0; i < GENERATION_SIZE; i++) {
            NeuralNetwork child = null;
            for (int j = 0; j < 10; j++) {
                NeuralNetwork parent1 = top10.get(j);
                if (j == 0) {
                    NeuralNetwork parent2 = top10.get(j++);
                    child = parent1.merge(parent2);
                } else {
                    child = child.merge(parent1);
                }
            }

            child.mutate(0.1);
            newGeneration.add(child);
        }
    }

    public void getTop10() {
        HashMap<NeuralNetwork, Integer> genTop10 = new HashMap<>();

        for (NeuralNetwork neuralNetwork : newGeneration) {
            Network network = new Network(neuralNetwork);
            int score = 0;

            for (int i = 0; i < NUM_ROUNDS; i++) {
                System.out.println("ball before update: " + ball.getX() + ", " + ball.getY());
                double newX = ball.updateX();
                double newY = ball.updateY();

                ball.setPosition(newX, newY);
                System.out.println("ball after update: " + ball.getX() + ", " + ball.getY());

                System.out.println("paddle before update: " + paddle.getX() + ", " + paddle.getY());
                Bounds direction = network.movePaddle();

                if (direction == LEFT) {
                    paddle.setLocation((int) paddle.getX() - 20, (int) paddle.getY());
                    System.out.println("left");
                } else if (direction == RIGHT) {
                    paddle.setLocation((int) paddle.getX() + 20, (int) paddle.getY());
                    System.out.println("right");
                }
                System.out.println("paddle after update: " + paddle.getX() + ", " + paddle.getY());


                Bounds hitDirection = NONE;

                if (ball.hitsWall(radius)) {
                    if (newX - radius <= 0) {
                        hitDirection = LEFT;
                    } else if (newX + radius >= ball.getWidth()) {
                        hitDirection = RIGHT;
                    } else if (newY - radius <= 0) {
                        hitDirection = TOP;
                    } else if (newY + radius > 600) {
                        i = 10000;
                    }
                    if (hitDirection != NONE) {
                        ball.bounceWalls(hitDirection);
                    }
                }

                score = checkPaddleCollision(score);

                if (genTop10.size() < 10) {
                    genTop10.put(neuralNetwork, score);
                } else {
                    Map.Entry<NeuralNetwork, Integer> minEntry = null;
                    for (Map.Entry<NeuralNetwork, Integer> entry : genTop10.entrySet()) {
                        if (minEntry == null || entry.getValue() < minEntry.getValue()) {
                            minEntry = entry;
                        }
                    }

                    if (score > minEntry.getValue()) {
                        genTop10.remove(minEntry.getKey());
                        genTop10.put(neuralNetwork, score);
                    }
                }
            }
            for (Map.Entry<NeuralNetwork, Integer> entry : genTop10.entrySet()) {
                System.out.println(entry.getKey() + " is alive " + entry.getValue() + " times");
            }
            top10 = new ArrayList<>(genTop10.keySet());
        }
    }

    public int checkPaddleCollision(int score) {
        Rectangle paddleBounds = new Rectangle((int) paddle.getX(), (int) paddle.getY(),
                (int) paddle.getWidth(), (int) paddle.getHeight());
        Rectangle ballBounds = new Rectangle((int) ball.getX(), (int) ball.getY(),
                radius * 2, radius * 2);

        if (paddleBounds.intersects(ballBounds)) {
            score++;
            System.out.println("collided");
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
        return score;
    }
}