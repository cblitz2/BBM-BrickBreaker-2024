package bbm.brickbreaker;

import basicneuralnetwork.NeuralNetwork;
import java.util.Random;

public class Simulation implements Comparable<Simulation> {
    private final NeuralNetwork network;
    private final Ball ball;
    private final BrickFactory brickFactory;
    private Brick brick;
    private final Paddle paddle;
    private int score;
    private boolean gameOver;

    private boolean hitPaddle;
    private boolean hitBrick;

    public Simulation(NeuralNetwork network, Ball ball, Paddle paddle,
                      BrickFactory brickFactory) {
        this.network = network;
        this.ball = ball;
        this.paddle = paddle;
        this.gameOver = false;
        this.brickFactory = brickFactory;
        Random rand = new Random();
        ball.setPosition(rand.nextInt(0, 750), 225);
        brick = brickFactory.newBrick();
        hitPaddle = false;
        hitBrick = false;
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
            hitPaddle = true;
            if (hitBrick) {
               score++;
            }
        }

        if (ball.collidesBrick(brick)) {
            hitsBrick();
            hitBrick = true;
            if (hitPaddle) {
                score++;
            }
        }

        if (ball.getY() >= 600) {
            gameOver = true;
        }
        return gameOver;
    }

    private void movePaddle() {
        double[] input = new double[4];
        input[0] = ball.getCenterX();
        input[1] = paddle.getCenterX();
        input[2] = brick.getCenterX();
        input[3] = brick.getCenterY();

        double[] output = network.guess(input);

        if (output[0] > output[1] && paddle.getX() > 0) {
            paddle.setLocation((int) (paddle.getX() - 3), (int) paddle.getY());
        } else if (output[1] > output[0] && paddle.getX() < 780) {
            paddle.setLocation((int) (paddle.getX() + 3), (int) paddle.getY());
        }
    }

    public Brick getBrick() {
        return brick;
    }

    private void hitsBrick() {
        brick.setHit(true);
        this.brick = brickFactory.newBrick();
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
