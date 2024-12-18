package bbm.brickbreaker;

import basicneuralnetwork.NeuralNetwork;
import java.util.Random;

public class Simulation implements Comparable<Simulation> {
    private final NeuralNetwork network;
    private final Ball ball;
    private final Paddle paddle;
    private final BrickFactory brickFactory;
    private Brick brick;
    private int score;
    private boolean gameOver;
    private boolean hitPaddle;
    private boolean hitBrick;
    private static final int PADDLE_STEP = 4;

    public Simulation(NeuralNetwork network, Ball ball, Paddle paddle,
                      BrickFactory brickFactory) {
        this.network = network;
        this.ball = ball;
        this.paddle = paddle;
        this.brickFactory = brickFactory;
        brick = brickFactory.newBrick();
        this.gameOver = false;
        hitPaddle = false;
        hitBrick = false;
        Random rand = new Random();
        ball.setPosition(rand.nextInt(0, 750), 225);
    }

    public boolean advance() {
        ball.move();
        movePaddle();

        if (ball.hitsWall()) {
            ball.bounceWalls();
        }

        if (ball.hitsTop()) {
            ball.bounceTop();
        }

        if (ball.collidesPaddle(paddle)) {
            ball.bouncePaddle(paddle);
            hitPaddle = true;
            if (hitBrick) {
                score++;
                hitBrick = false;
            }
        }

        if (ball.collidesBrick(brick)) {
            hitsBrick();
            hitBrick = true;
            if (hitPaddle) {
                score++;
                hitPaddle = false;
            }
        }

        if (ball.falls()) {
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
            paddle.setLocation((int) (paddle.getX() - PADDLE_STEP), (int) paddle.getY());
        } else if (output[1] > output[0] && paddle.getX() < 780) {
            paddle.setLocation((int) (paddle.getX() + PADDLE_STEP), (int) paddle.getY());
        }
    }

    public Brick getBrick() {
        return brick;
    }

    private void hitsBrick() {
        ball.hitBrick();
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
