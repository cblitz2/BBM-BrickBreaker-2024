package bbm.brickbreaker;

import basicneuralnetwork.NeuralNetwork;
import java.util.Random;

public class Simulation implements Comparable<Simulation> {
    private NeuralNetwork network;
    private Ball ball;
    private Paddle paddle;
    private int width;
    private int height;
    private int score;
    private boolean gameOver;
    private Random rand = new Random();

    public Simulation(NeuralNetwork network, Ball ball, Paddle paddle,
                      int width, int height) {
        this.network = network;
        this.ball = ball;
        this.paddle = paddle;
        this.width = width;
        this.height = height;
        this.gameOver = false;
        ball.setPosition(rand.nextInt(0, 750), 225);
    }

    public boolean advance() {
        ball.move();
        movePaddle();

        if (ball.hitsWall(800)) {
            ball.bounceWalls();
        }

        if (ball.hitsTop()) {
            ball.bounceTop();
        }

        if (ball.collidesPaddle(paddle)) {
            ball.bouncePaddle();
            score++;
        }

        if (ball.getY() >= 600) {
            gameOver = true;
        }
        return gameOver;
    }

    private void movePaddle() {
        double[] input = new double[2];
        input[0] = ball.getCenterX();
        input[1] = paddle.getCenterX();

        double[] output = network.guess(input);

        if (output[0] > output[1] && paddle.getX() > 0) {
            paddle.setLocation((int) (paddle.getX() - 3), (int) paddle.getY());
        } else if (output[1] > output[0] && paddle.getX() < 780) {
            paddle.setLocation((int) (paddle.getX() + 3), (int) paddle.getY());
        }
    }

    public int getScore() {
        return score;
    }

    public NeuralNetwork getNetwork() {
        return network;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    @Override
    public int compareTo(Simulation o) {
        return Integer.compare(o.score, this.score);
    }
}
