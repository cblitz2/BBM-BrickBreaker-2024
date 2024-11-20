package bbm.brickbreaker;

import javax.swing.Timer;
import java.awt.*;
import java.util.List;

import static bbm.brickbreaker.Bounds.*;

public class Controller {
    private final Ball ball;
    private final List<Brick> bricks;
    private final Paddle paddle;
    private final GameComponent view;
    private final int radius;
    private Timer timer;

    public Controller(Ball ball, List<Brick> bricks, int radius, Paddle paddle, GameComponent view) {
        this.ball = ball;
        this.bricks = bricks;
        this.paddle = paddle;
        this.radius = radius;
        this.view = view;
    }

    public void play() {
        timer = new Timer(1000 / 60, e -> {
            double newX = ball.locationX();
            double newY = ball.locationY();

            ball.setPosition(newX, newY);
            view.repaint();

            Bounds hitDirection = NONE;

            if (ball.hitsWall(radius)) {
                if (newX - radius <= 0) {
                    hitDirection = LEFT;
                } else if (newX + radius >= ball.getWidth()) {
                    hitDirection = RIGHT;
                } else if (newY - radius <= 0) {
                    hitDirection = TOP;
                } else if (newY + radius > 600) {
                    System.out.println(newY);
                    timer.stop();
                }

                if (hitDirection != NONE) {
                    ball.bounceWalls(hitDirection);
                }
            }
            checkPaddleCollision();
            breakBricks();
        });

        timer.start();
    }

    public void breakBricks() {
        Rectangle ballBounds = new Rectangle((int) ball.getX() - radius,
                (int) ball.getY() - radius,
                radius * 2, radius * 2);
        for (Brick brick : bricks) {
            if (!brick.isHit() && brick.getBounds().intersects(ballBounds)) {
                brick.setHit(true);
                ball.bounceWalls(Bounds.TOP);
                view.repaint();
                break;
            }
        }
    }

    public void checkPaddleCollision() {
        Rectangle paddleBounds = new Rectangle((int) paddle.getX(), (int) paddle.getY(),
                (int) paddle.getWidth(), (int) paddle.getHeight());
        Rectangle ballBounds = new Rectangle((int) ball.getX(), (int) ball.getY(),
                radius * 2, radius * 2);

        if (paddleBounds.intersects(ballBounds)) {
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


//package bbm.brickbreaker;
//
//import java.awt.*;
//import java.util.ArrayList;
//import java.util.Collections;
//import static bbm.brickbreaker.Bounds.*;
//import java.util.List;
//
//public class Controller {
//    private final Ball ball;
//    private final List<Brick> bricks;
//    private final Paddle paddle;
//    private final int radius;
//    private List<Network> networks;
//    private List<Network> topNetworks;
//    private static final int GENERATION_SIZE = 1000;
//    private static final int TOP_N = 10;
//    private static final int MAX_GENERATIONS = 50;
//    private int currentGeneration = 0;
//
//    public Controller(Ball ball, List<Brick> bricks, int radius, Paddle paddle) {
//        this.ball = ball;
//        this.bricks = bricks;
//        this.paddle = paddle;
//        this.radius = radius;
//        this.networks = new ArrayList<>();
//        this.topNetworks = new ArrayList<>();
//    }
//
//    public void play() {
//        initializeNetworks();
//        for (currentGeneration = 0; currentGeneration < MAX_GENERATIONS; currentGeneration++) {
//            System.out.println("Starting Generation " + currentGeneration);
//
//            for (Network network : networks) {
//                resetSimulation();
//
//                while (true) {
//                    if (!simulateStep(network)) break;
//                }
//            }
//
//            evaluateAndCreateNextGeneration();
//            System.out.println("Ending Generation " + currentGeneration + "\n");
//        }
//
//        logFinalResults();
//    }
//
//    private void initializeNetworks() {
//        for (int i = 0; i < GENERATION_SIZE; i++) {
//            networks.add(new Network(paddle));
//        }
//    }
//
//    private int simulateStep(Network network) {
//        double newX = ball.locationX();
//        double newY = ball.locationY();
//
//        // Update ball position
//        ball.setPosition(newX, newY);
//
//        // Check wall collisions
//        Bounds hitDirection = NONE;
//        if (newX - radius <= 0) {
//            hitDirection = LEFT;
//        } else if (newX + radius >= ball.getWidth()) {
//            hitDirection = RIGHT;
//        } else if (newY - radius <= 0) {
//            hitDirection = TOP;
//        } else if (newY + radius > ball.getHeight()) {
//            hitDirection = NONE;
//            // Ball falls below the paddle, end this network's lifespan
//            //return false;
//        }
//
//        if (hitDirection != NONE) {
//            ball.bounceWalls(hitDirection);
//        }
//
//        // Check paddle collision
//        checkPaddleCollision();
//
//        // Check for brick collisions
//        breakBricks();
//
//        // Network controls the paddle
//        double ballX = ball.getX();
//        double ballY = ball.getY();
//        double paddleX = paddle.getX();
//        double paddleWidth = paddle.getWidth();
//
//        double angleToPaddle = Math.toDegrees(Math.atan2(paddle.getY() - ballY, paddle.getX() - ballX));
//
//        // Pass inputs to the network (ball and paddle positions)
//        double decision = network.movePaddle(angleToPaddle);
//
//        // Move the paddle based on the network's decision
//        if (decision == 1) {
//            hitDirection = LEFT;
//        } else if (decision == 2) {
//            hitDirection = RIGHT;
//        }
//
//        // Increment the network's lifespan
//        network.incrementLifespan();
//
//        return hitDirection; // Continue simulation
//    }
//
//    public void breakBricks() {
//        Rectangle ballBounds = new Rectangle((int) ball.getX() - radius,
//                (int) ball.getY() - radius,
//                radius * 2, radius * 2);
//        for (Brick brick : bricks) {
//            if (!brick.isHit() && brick.getBounds().intersects(ballBounds)) {
//                brick.setHit(true);
//                ball.bounceWalls(Bounds.TOP);
//                break;
//            }
//        }
//    }
//
//    public void checkPaddleCollision() {
//        Rectangle paddleBounds = new Rectangle((int) paddle.getX(), (int) paddle.getY(),
//                (int) paddle.getWidth(), (int) paddle.getHeight());
//        Rectangle ballBounds = new Rectangle((int) ball.getX(), (int) ball.getY(),
//                radius * 2, radius * 2);
//
//        if (paddleBounds.intersects(ballBounds)) {
//            ball.bouncePaddle(MIDDLE); // Simple bounce logic for now
//        }
//    }
//
//    private void evaluateAndCreateNextGeneration() {
//        // Sort networks by lifespan (descending)
//        networks.sort((n1, n2) -> Integer.compare(n2.getLifespan(), n1.getLifespan()));
//
//        // Log the best network's performance
//        System.out.printf("Best Network of Generation %d: Lifespan = %d%n", currentGeneration, networks.get(0).getLifespan());
//
//        // Save the top networks
//        topNetworks.clear();
//        for (int i = 0; i < TOP_N; i++) {
//            topNetworks.add(networks.get(i));
//        }
//
//        // Create the next generation
//        List<Network> nextGeneration = new ArrayList<>();
//        for (int i = 0; i < GENERATION_SIZE; i++) {
//            // Select two parents randomly from the top networks
//            Network parent1 = topNetworks.get((int) (Math.random() * TOP_N));
//            Network parent2 = topNetworks.get((int) (Math.random() * TOP_N));
//
//            // Create a child by merging and mutating the parents
//            Network child = parent1.merge(parent2);
//            child.mutate(0.1); // Mutation rate of 10%
//
//            nextGeneration.add(child);
//        }
//
//        // Replace the current generation with the next generation
//        networks = nextGeneration;
//    }
//
//    private void resetSimulation() {
//        ball.setPosition(ball.getWidth() / 2, ball.getHeight() - 50);
//        paddle.setLocation((int) (ball.getWidth() / 2 - paddle.getWidth() / 2), (int) paddle.getX());
//        for (Brick brick : bricks) {
//            brick.setHit(false);
//        }
//    }
//
//    private void logFinalResults() {
//        System.out.println("Simulation Complete!");
//        System.out.println("Top Networks:");
//        for (int i = 0; i < TOP_N; i++) {
//            System.out.printf("Network %d: Lifespan = %d%n", i + 1, topNetworks.get(i).getLifespan());
//        }
//    }
//}


//second chat version
//import javax.swing.Timer;
//import java.awt.*;
//import java.util.ArrayList;
//import java.util.List;
//
//import static bbm.brickbreaker.Bounds.*;
//import java.util.Collections;
//
//public class Controller {
//    private final Ball ball;
//    private final List<Brick> bricks;
//    private final Paddle paddle;
//    private final GameComponent view;
//    private final int radius;
//    private Timer timer;
//    private List<Network> networks;
//    private List<Network> topNetworks;
//    private static final int GENERATION_SIZE = 1000;
//    private static final int TOP_N = 10;
//    private static final int MAX_GENERATIONS = 50;
//    private int currentGeneration = 0;
//
//    public Controller(Ball ball, List<Brick> bricks, int radius, Paddle paddle, GameComponent view) {
//        this.ball = ball;
//        this.bricks = bricks;
//        this.paddle = paddle;
//        this.radius = radius;
//        this.view = view;
//        this.networks = new ArrayList<>();
//        this.topNetworks = new ArrayList<>();
//    }
//
//    public void play() {
//        // Initialize networks for the first generation
//        if (currentGeneration == 0) {
//            for (int i = 0; i < GENERATION_SIZE; i++) {
//                networks.add(new Network(paddle));
//            }
//        }
//
//        System.out.println("Starting Generation " + currentGeneration);
//
//        timer = new Timer(1000 / 60, e -> {
//            double newX = ball.locationX();
//            double newY = ball.locationY();
//
//            ball.setPosition(newX, newY);
//            view.repaint();
//
//            Bounds hitDirection = NONE;
//
//            if (ball.hitsWall(radius)) {
//                if (newX - radius <= 0) {
//                    hitDirection = LEFT;
//                } else if (newX + radius >= ball.getWidth()) {
//                    hitDirection = RIGHT;
//                } else if (newY - radius <= 0) {
//                    hitDirection = TOP;
//                } else if (newY + radius > ball.getHeight()) {
//                    endGeneration();
//                }
//
//                if (hitDirection != NONE) {
//                    ball.bounceWalls(hitDirection);
//                }
//            }
//
//            checkPaddleCollision();
//            breakBricks();
//
//            for (Network network : networks) {
//                double ballX = ball.getX();
//                double ballY = ball.getY();
//
//                double angleToPaddle = Math.toDegrees(Math.atan2(paddle.getY() - ballY, paddle.getX() - ballX));
//
//                String decision = String.valueOf(network.movePaddle(angleToPaddle));
//                network.incrementLifespan();
//
//                // Log each network's decision
//                System.out.printf("Network %d: Ball at (%.2f, %.2f), Decision: %s%n",
//                        networks.indexOf(network), ballX, ballY, decision);
//            }
//        });
//
//        timer.start();
//    }
//
//    public void breakBricks() {
//        Rectangle ballBounds = new Rectangle((int) ball.getX() - radius,
//                (int) ball.getY() - radius,
//                radius * 2, radius * 2);
//        for (Brick brick : bricks) {
//            if (!brick.isHit() && brick.getBounds().intersects(ballBounds)) {
//                brick.setHit(true);
//                ball.bounceWalls(Bounds.TOP);
//                view.repaint();
//                break;
//            }
//        }
//    }
//
//    public void checkPaddleCollision() {
//        Rectangle paddleBounds = new Rectangle((int) paddle.getX(), (int) paddle.getY(),
//                (int) paddle.getWidth(), (int) paddle.getHeight());
//        Rectangle ballBounds = new Rectangle((int) ball.getX(), (int) ball.getY(),
//                radius * 2, radius * 2);
//
//        if (paddleBounds.intersects(ballBounds)) {
//            double sectionWidth = paddle.getWidth() / 5;
//            double section = (ball.getX() - paddle.getX()) / sectionWidth;
//
//            switch ((int) section) {
//                case 0:
//                    ball.bouncePaddle(LEFT_EDGE);
//                    break;
//                case 1:
//                    ball.bouncePaddle(LEFT);
//                    break;
//                case 2:
//                    ball.bouncePaddle(MIDDLE);
//                    break;
//                case 3:
//                    ball.bouncePaddle(RIGHT);
//                    break;
//                case 4:
//                    ball.bouncePaddle(RIGHT_EDGE);
//                    break;
//                default:
//                    break;
//            }
//        }
//    }
//
//    private void endGeneration() {
//        timer.stop();
//        testNetwork();
//
//        currentGeneration++;
//        if (currentGeneration < MAX_GENERATIONS) {
//            System.out.println("Ending Generation " + currentGeneration + "\n");
//            play();
//        } else {
//            System.out.println("Simulation Complete!");
//            logFinalResults();
//        }
//    }
//
//    private void testNetwork() {
//        Collections.sort(networks, (n1, n2) -> Integer.compare(n2.getLifespan(), n1.getLifespan()));
//
//        Network bestNetwork = networks.get(0);
//        System.out.printf("Best Network of Generation %d: Lifespan = %d%n", currentGeneration, bestNetwork.getLifespan());
//
//        topNetworks.clear();
//        for (int i = 0; i < TOP_N; i++) {
//            topNetworks.add(networks.get(i));
//        }
//
//        List<Network> nextGeneration = new ArrayList<>();
//        for (int i = 0; i < GENERATION_SIZE; i++) {
//            Network parent1 = topNetworks.get((int) (Math.random() * TOP_N));
//            Network parent2 = topNetworks.get((int) (Math.random() * TOP_N));
//
//            Network child = parent1.merge(parent2);
//            child.mutate(0.1);
//
//            nextGeneration.add(child);
//        }
//
//        networks = nextGeneration;
//        ball.setPosition(ball.getWidth() / 2, ball.getHeight() - 50);
//
//        for (Network network : networks) {
//            network.resetLifespan();
//        }
//    }
//
//    private void logFinalResults() {
//        System.out.println("Final Results:");
//        for (int i = 0; i < TOP_N; i++) {
//            System.out.printf("Top Network %d: Lifespan = %d%n", i + 1, topNetworks.get(i).getLifespan());
//        }
//    }
//}


//first try from chat today
//import javax.swing.Timer;
//import java.awt.*;
//import java.util.ArrayList;
//import java.util.List;
//
//import static bbm.brickbreaker.Bounds.*;
//import java.util.Collections;
//
//public class Controller {
//    private final Ball ball;
//    private final List<Brick> bricks;
//    private final Paddle paddle;
//    private final GameComponent view;
//    private final int radius;
//    private Timer timer;
//    private List<Network> networks;
//    private List<Network> topNetworks;
//    private static final int GENERATION_SIZE = 1000;
//    private static final int TOP_N = 10;
//    private static final int MAX_GENERATIONS = 50; // Number of generations to simulate
//    private int currentGeneration = 0; // Tracks the current generation
//
//    public Controller(Ball ball, List<Brick> bricks, int radius, Paddle paddle, GameComponent view) {
//        this.ball = ball;
//        this.bricks = bricks;
//        this.paddle = paddle;
//        this.radius = radius;
//        this.view = view;
//        this.networks = new ArrayList<>();
//        this.topNetworks = new ArrayList<>();
//    }
//
//    public void play() {
//        System.out.println("in play");
//        // Initialize networks for the first generation
//        for (int i = 0; i < GENERATION_SIZE; i++) {
//            networks.add(new Network(paddle));
//        }
//
//        timer = new Timer(1000 / 60, e -> {
//            double newX = ball.locationX();
//            double newY = ball.locationY();
//
//            ball.setPosition(newX, newY);
//            view.repaint();
//
//            Bounds hitDirection = NONE;
//
//            if (ball.hitsWall(radius)) {
//                if (newX - radius <= 0) {
//                    System.out.println("1");
//                    hitDirection = LEFT;
//                } else if (newX + radius >= ball.getWidth()) {
//                    System.out.println("2");
//                    hitDirection = RIGHT;
//                } else if (newY - radius <= 0) {
//                    System.out.println("3");
//                    hitDirection = TOP;
//                } else if (newY + radius > ball.getHeight()) {
//                    System.out.println("4");
//                    endGeneration();
//                }
//
//                if (hitDirection != NONE) {
//                    ball.bounceWalls(hitDirection);
//                }
//            }
//
//            checkPaddleCollision();
//            breakBricks();
//
//            for (Network network : networks) {
//                double ballX = ball.getX();
//                double ballY = ball.getY();
//
//                double angleToPaddle = Math.toDegrees(Math.atan2(paddle.getY() - ballY, paddle.getX() - ballX));
//
//                network.movePaddle(angleToPaddle);
//                network.incrementLifespan();
//            }
//        });
//
//        timer.start();
//    }
//
//    public void breakBricks() {
//        Rectangle ballBounds = new Rectangle((int) ball.getX() - radius,
//                (int) ball.getY() - radius,
//                radius * 2, radius * 2);
//        for (Brick brick : bricks) {
//            if (!brick.isHit() && brick.getBounds().intersects(ballBounds)) {
//                brick.setHit(true);
//                ball.bounceWalls(Bounds.TOP);
//                view.repaint();
//                break;
//            }
//        }
//    }
//
//    public void checkPaddleCollision() {
//        Rectangle paddleBounds = new Rectangle((int) paddle.getX(), (int) paddle.getY(),
//                (int) paddle.getWidth(), (int) paddle.getHeight());
//        Rectangle ballBounds = new Rectangle((int) ball.getX(), (int) ball.getY(),
//                radius * 2, radius * 2);
//
//        if (paddleBounds.intersects(ballBounds)) {
//            double sectionWidth = paddle.getWidth() / 5;
//            double section = (ball.getX() - paddle.getX()) / sectionWidth;
//
//            switch ((int) section) {
//                case 0:
//                    ball.bouncePaddle(LEFT_EDGE);
//                    break;
//                case 1:
//                    ball.bouncePaddle(LEFT);
//                    break;
//                case 2:
//                    ball.bouncePaddle(MIDDLE);
//                    break;
//                case 3:
//                    ball.bouncePaddle(RIGHT);
//                    break;
//                case 4:
//                    ball.bouncePaddle(RIGHT_EDGE);
//                    break;
//                default:
//                    break;
//            }
//        }
//    }
//
//    private void endGeneration() {
//        System.out.println("in end");
//        timer.stop();
//        testNetwork();
//
//        currentGeneration++;
//        if (currentGeneration < MAX_GENERATIONS) {
//            System.out.println("Generation: " + currentGeneration);
//            play();
//        } else {
//            System.out.println("Simulation Complete!");
//        }
//    }
//
//    private void testNetwork() {
//        System.out.println("in test");
//        Collections.sort(networks, (n1, n2) -> Integer.compare(n2.getLifespan(), n1.getLifespan()));
//
//        topNetworks.clear();
//        for (int i = 0; i < TOP_N; i++) {
//            topNetworks.add(networks.get(i));
//        }
//
//        List<Network> nextGeneration = new ArrayList<>();
//        for (int i = 0; i < GENERATION_SIZE; i++) {
//            Network parent1 = topNetworks.get((int) (Math.random() * TOP_N));
//            Network parent2 = topNetworks.get((int) (Math.random() * TOP_N));
//
//            Network child = parent1.merge(parent2);
//            child.mutate(0.1);
//
//            nextGeneration.add(child);
//        }
//
//        networks = nextGeneration;
//        ball.setPosition(ball.getWidth() / 2, ball.getHeight() - 50);
//
//        for (Network network : networks) {
//            network.resetLifespan();
//        }
//    }
//}


// Code from beginning of today
//import javax.swing.Timer;
//import java.awt.*;
//import java.util.ArrayList;
//import java.util.List;
//
//import static bbm.brickbreaker.Bounds.*;
//import java.util.Collections;
//
//public class Controller {
//    private final Ball ball;
//    private final List<Brick> bricks;
//    private final Paddle paddle;
//    private final GameComponent view;
//    private final int radius;
//    private Timer timer;
//    private List<Network> networks; // List of all networks
//    private List<Network> topNetworks; // List of top 10 networks
//    private static final int GENERATION_SIZE = 1000; // Size of the population per generation
//    private static final int TOP_N = 10; // Number of top networks to save for next generation
//
//    public Controller(Ball ball, List<Brick> bricks, int radius, Paddle paddle, GameComponent view) {
//        this.ball = ball;
//        this.bricks = bricks;
//        this.paddle = paddle;
//        this.radius = radius;
//        this.view = view;
//        this.networks = new ArrayList<>();
//        this.topNetworks = new ArrayList<>();
//    }
//
//    public void play() {
//        // Initialize networks for the current generation
//        for (int i = 0; i < GENERATION_SIZE; i++) {
//            networks.add(new Network(paddle));
//        }
//
//        timer = new Timer(1000 / 60, e -> {
//            double newX = ball.locationX();
//            double newY = ball.locationY();
//
//            ball.setPosition(newX, newY);
//            view.repaint();
//
//            Bounds hitDirection = NONE;
//
//            if (ball.hitsWall(radius)) {
//                if (newX - radius <= 0) {
//                    hitDirection = LEFT;
//                } else if (newX + radius >= ball.getWidth()) {
//                    hitDirection = RIGHT;
//                } else if (newY - radius <= 0) {
//                    hitDirection = TOP;
//                } else if (newY + radius > ball.getHeight()) {
//                    timer.stop();  // Game over for this generation
//                }
//
//                if (hitDirection != NONE) {
//                    ball.bounceWalls(hitDirection);
//                }
//            }
//
//            checkPaddleCollision();
//            breakBricks();
//
//            // Evaluate each network by making them control the paddle
//            for (Network network : networks) {
//                double ballX = ball.getX();
//                double ballY = ball.getY();
//
//                // Calculate the angle from the ball to the paddle
//                double angleToPaddle = Math.toDegrees(Math.atan2(paddle.getY() - ballY, paddle.getX() - ballX));
//
//                // Move paddle and increment lifespan if the network survives
//                network.movePaddle(angleToPaddle);
//                network.incrementLifespan();
//            }
//
//            // After every frame, check if the ball has fallen out
//            if (ball.getY() > ball.getHeight()) {
//                testNetwork();
//            }
//        });
//
//        timer.start();
//    }
//
//    public void breakBricks() {
//        Rectangle ballBounds = new Rectangle((int) ball.getX() - radius,
//                (int) ball.getY() - radius,
//                radius * 2, radius * 2);
//        for (Brick brick : bricks) {
//            if (!brick.isHit() && brick.getBounds().intersects(ballBounds)) {
//                brick.setHit(true);
//                ball.bounceWalls(Bounds.TOP);
//                view.repaint();
//                break;
//            }
//        }
//    }
//
//    public void checkPaddleCollision() {
//        Rectangle paddleBounds = new Rectangle((int) paddle.getX(), (int) paddle.getY(),
//                (int) paddle.getWidth(), (int) paddle.getHeight());
//        Rectangle ballBounds = new Rectangle((int) ball.getX(), (int) ball.getY(),
//                radius * 2, radius * 2);
//
//        if (paddleBounds.intersects(ballBounds)) {
//            double sectionWidth = paddle.getWidth() / 5;
//            double section = (ball.getX() - paddle.getX()) / sectionWidth;
//
//            switch ((int) section) {
//                case 0:
//                    ball.bouncePaddle(LEFT_EDGE);
//                    break;
//                case 1:
//                    ball.bouncePaddle(LEFT);
//                    break;
//                case 2:
//                    ball.bouncePaddle(MIDDLE);
//                    break;
//                case 3:
//                    ball.bouncePaddle(RIGHT);
//                    break;
//                case 4:
//                    ball.bouncePaddle(RIGHT_EDGE);
//                    break;
//                default:
//                    break;
//            }
//        }
//    }
//
//    // When the generation ends, save the top networks and create the next generation
//    private void testNetwork() {
//        // Sort the networks by their lifespan (longer lifespan is better)
//        Collections.sort(networks, (n1, n2) -> Integer.compare(n2.getLifespan(), n1.getLifespan()));
//
//        // Save the top 10 networks based on lifespan
//        topNetworks.clear();
//        for (int i = 0; i < TOP_N; i++) {
//            topNetworks.add(networks.get(i));
//        }
//
//        // Create the next generation by merging and mutating the top networks
//        List<Network> nextGeneration = new ArrayList<>();
//
//        // Generate new networks by merging and mutating the top 10 networks
//        for (int i = 0; i < GENERATION_SIZE; i++) {
//            // Randomly select two networks from the top 10
//            Network parent1 = topNetworks.get((int) (Math.random() * TOP_N));
//            Network parent2 = topNetworks.get((int) (Math.random() * TOP_N));
//
//            // Merge the parents and create a new network
//            Network child = parent1.merge(parent2);
//
//            // Mutate the child network
//            child.mutate(0.1); // Mutation rate of 10%
//
//            // Add the child to the next generation
//            nextGeneration.add(child);
//        }
//
//        // Replace the current generation with the next generation
//        networks = nextGeneration;
//
//        // Reset the ball position and the networks' lifespans
//        ball.setPosition(ball.getWidth() / 2, ball.getHeight() - 50);
//        for (Network network : networks) {
//            network.resetLifespan();
//        }
//    }
//}

//public class Controller {
//    private final Ball ball;
//    private final List<Brick> bricks;
//    private final Paddle paddle;
//    private final GameComponent view;
//    private final int radius;
//    private Timer timer;
//    private List<Network> networks; // List of all networks
//    private List<Network> topNetworks; // List of top 10 networks
//    private static final int GENERATION_SIZE = 1000; // Size of the population per generation
//    private static final int TOP_N = 10; // Number of top networks to save for next generation
//
//    public Controller(Ball ball, List<Brick> bricks, int radius, Paddle paddle, GameComponent view) {
//        this.ball = ball;
//        this.bricks = bricks;
//        this.paddle = paddle;
//        this.radius = radius;
//        this.view = view;
//        this.networks = new ArrayList<>();
//        this.topNetworks = new ArrayList<>();
//    }
//
//    public void play() {
//        // Initialize networks for the current generation
//        for (int i = 0; i < GENERATION_SIZE; i++) {
//            networks.add(new Network(paddle));
//        }
//
//        timer = new Timer(1000 / 60, e -> {
//            double ballX = ball.locationX();
//            double ballY = ball.locationY();
//            double ballVelocityX = Math.cos(Math.toRadians(ball.getAngle())) * ball.getVelocity();
//            double ballVelocityY = Math.sin(Math.toRadians(ball.getAngle())) * ball.getVelocity();
//
//            ball.setPosition(ballX, ballY);
//            view.repaint();
//
//            Bounds hitDirection = Bounds.NONE;
//
//            // Ball hits wall logic
//            if (ball.hitsWall(radius)) {
//                if (ballX - radius <= 0) {
//                    hitDirection = Bounds.LEFT;
//                } else if (ballX + radius >= ball.getWidth()) {
//                    hitDirection = Bounds.RIGHT;
//                } else if (ballY - radius <= 0) {
//                    hitDirection = Bounds.TOP;
//                } else if (ballY + radius > ball.getHeight()) {
//                    timer.stop();  // Game over for this generation
//                }
//
//                if (hitDirection != Bounds.NONE) {
//                    ball.bounceWalls(hitDirection);
//                }
//            }
//
//            // Move the paddle using the neural networks
//            for (Network network : networks) {
//                network.movePaddle(ballX, ballY, ballVelocityX, ballVelocityY);
//                network.incrementLifespan();
//            }
//
//            checkPaddleCollision();
//            breakBricks();
//
//            // After every frame, check if the ball has fallen out
//            if (ball.getY() > ball.getHeight()) {
//                testNetwork();
//            }
//        });
//
//        timer.start();
//    }
//
//
//    public void breakBricks() {
//        Rectangle ballBounds = new Rectangle((int) ball.getX() - radius,
//                (int) ball.getY() - radius,
//                radius * 2, radius * 2);
//        for (Brick brick : bricks) {
//            if (!brick.isHit() && brick.getBounds().intersects(ballBounds)) {
//                brick.setHit(true);
//                ball.bounceWalls(Bounds.TOP);
//                view.repaint();
//                break;
//            }
//        }
//    }
//
//    public void checkPaddleCollision() {
//        Rectangle paddleBounds = new Rectangle((int) paddle.getX(), (int) paddle.getY(),
//                (int) paddle.getWidth(), (int) paddle.getHeight());
//        Rectangle ballBounds = new Rectangle((int) ball.getX(), (int) ball.getY(),
//                radius * 2, radius * 2);
//
//        if (paddleBounds.intersects(ballBounds)) {
//            double sectionWidth = paddle.getWidth() / 5;
//            double section = (ball.getX() - paddle.getX()) / sectionWidth;
//
//            switch ((int) section) {
//                case 0:
//                    ball.bouncePaddle(Bounds.LEFT_EDGE);
//                    break;
//                case 1:
//                    ball.bouncePaddle(Bounds.LEFT);
//                    break;
//                case 2:
//                    ball.bouncePaddle(Bounds.MIDDLE);
//                    break;
//                case 3:
//                    ball.bouncePaddle(Bounds.RIGHT);
//                    break;
//                case 4:
//                    ball.bouncePaddle(Bounds.RIGHT_EDGE);
//                    break;
//                default:
//                    break;
//            }
//        }
//    }
//
//    // When the generation ends, save the top networks and create the next generation
//    private void testNetwork() {
//        // Sort the networks by their lifespan (longer lifespan is better)
//        Collections.sort(networks, (n1, n2) -> Integer.compare(n2.getLifespan(), n1.getLifespan()));
//
//        // Save the top 10 networks based on lifespan
//        topNetworks.clear();
//        for (int i = 0; i < TOP_N; i++) {
//            topNetworks.add(networks.get(i));
//        }
//
//        // Create the next generation by merging and mutating the top 10 networks
//        List<Network> nextGeneration = new ArrayList<>();
//
//        // Generate new networks by merging and mutating the top 10 networks
//        for (int i = 0; i < GENERATION_SIZE; i++) {
//            // Randomly select two networks from the top 10
//            Network parent1 = topNetworks.get((int) (Math.random() * TOP_N));
//            Network parent2 = topNetworks.get((int) (Math.random() * TOP_N));
//
//            // Merge the parents and create a new network
//            Network child = parent1.merge(parent2);
//
//            // Mutate the child network (apply some random changes)
//            child.mutate(0.1); // Mutation rate of 10%
//
//            // Add the child to the next generation
//            nextGeneration.add(child);
//        }
//
//        // Replace the current generation with the next generation
//        networks = nextGeneration;
//
//        // Reset the ball position and the networks' lifespans
//        ball.setPosition(ball.getWidth() / 2, ball.getHeight() - 50);  // Center the ball
//        for (Network network : networks) {
//            network.resetLifespan();
//        }
//    }
//}


//public class Controller {
//    private final Ball ball;
//    private final List<Brick> bricks;
//    private final Paddle paddle;
//    private final GameComponent view;
//    private final int radius;
//    private Timer timer;
//    private List<Network> networks; // List of all networks
//    private List<Network> topNetworks; // List of top 10 networks
//    private static final int GENERATION_SIZE = 1000; // Size of the population per generation
//    private static final int TOP_N = 10; // Number of top networks to save for next generation
//
//    public Controller(Ball ball, List<Brick> bricks, int radius, Paddle paddle, GameComponent view) {
//        this.ball = ball;
//        this.bricks = bricks;
//        this.paddle = paddle;
//        this.radius = radius;
//        this.view = view;
//        this.networks = new ArrayList<>();
//        this.topNetworks = new ArrayList<>();
//    }
//
//    public void play() {
//        // Initialize networks for the current generation
//        for (int i = 0; i < GENERATION_SIZE; i++) {
//            networks.add(new Network(paddle));
//        }
//
//        timer = new Timer(1000 / 60, e -> {
//            double newX = ball.locationX();
//            double newY = ball.locationY();
//
//            ball.setPosition(newX, newY);
//            view.repaint();
//
//            Bounds hitDirection = NONE;
//
//            if (ball.hitsWall(radius)) {
//                if (newX - radius <= 0) {
//                    hitDirection = LEFT;
//                } else if (newX + radius >= ball.getWidth()) {
//                    hitDirection = RIGHT;
//                } else if (newY - radius <= 0) {
//                    hitDirection = TOP;
//                } else if (newY + radius > ball.getHeight()) {
//                    timer.stop();  // Game over for this generation
//                }
//
//                if (hitDirection != NONE) {
//                    ball.bounceWalls(hitDirection);
//                }
//            }
//
//            checkPaddleCollision();
//            breakBricks();
//
//            // Evaluate each network by making them control the paddle
//            for (Network network : networks) {
//                double ballX = ball.getX();
//                double ballY = ball.getY();
//
//                // Calculate the angle from the ball to the paddle
//                double angleToPaddle = Math.toDegrees(Math.atan2(paddle.getY() - ballY, paddle.getX() - ballX));
//
//                // Move paddle and increment lifespan if the network survives
//                network.movePaddle(angleToPaddle);
//                network.incrementLifespan();
//            }
//
//            // After every frame, check if the ball has fallen out
//            if (ball.getY() > ball.getHeight()) {
//                testNetwork();
//            }
//        });
//
//        timer.start();
//    }
//
//    public void breakBricks() {
//        Rectangle ballBounds = new Rectangle((int) ball.getX() - radius,
//                (int) ball.getY() - radius,
//                radius * 2, radius * 2);
//        for (Brick brick : bricks) {
//            if (!brick.isHit() && brick.getBounds().intersects(ballBounds)) {
//                brick.setHit(true);
//                ball.bounceWalls(Bounds.TOP);
//                view.repaint();
//                break;
//            }
//        }
//    }
//
//    public void checkPaddleCollision() {
//        Rectangle paddleBounds = new Rectangle((int) paddle.getX(), (int) paddle.getY(),
//                (int) paddle.getWidth(), (int) paddle.getHeight());
//        Rectangle ballBounds = new Rectangle((int) ball.getX(), (int) ball.getY(),
//                radius * 2, radius * 2);
//
//        if (paddleBounds.intersects(ballBounds)) {
//            double sectionWidth = paddle.getWidth() / 5;
//            double section = (ball.getX() - paddle.getX()) / sectionWidth;
//
//            switch ((int) section) {
//                case 0:
//                    ball.bouncePaddle(LEFT_EDGE);
//                    break;
//                case 1:
//                    ball.bouncePaddle(LEFT);
//                    break;
//                case 2:
//                    ball.bouncePaddle(MIDDLE);
//                    break;
//                case 3:
//                    ball.bouncePaddle(RIGHT);
//                    break;
//                case 4:
//                    ball.bouncePaddle(RIGHT_EDGE);
//                    break;
//                default:
//                    break;
//            }
//        }
//    }
//
//    // When the generation ends, save the top networks and create the next generation
//    private void testNetwork() {
//        // Sort the networks by their lifespan (longer lifespan is better)
//        Collections.sort(networks, (n1, n2) -> Integer.compare(n2.getLifespan(), n1.getLifespan()));
//
//        // Save the top 10 networks based on lifespan
//        topNetworks.clear();
//        for (int i = 0; i < TOP_N; i++) {
//            topNetworks.add(networks.get(i));
//        }
//
//        // Create the next generation by merging and mutating the top networks
//        List<Network> nextGeneration = new ArrayList<>();
//
//        // Generate new networks by merging and mutating the top 10 networks
//        for (int i = 0; i < GENERATION_SIZE; i++) {
//            // Randomly select two networks from the top 10
//            Network parent1 = topNetworks.get((int) (Math.random() * TOP_N));
//            Network parent2 = topNetworks.get((int) (Math.random() * TOP_N));
//
//            // Merge the parents and create a new network
//            Network child = parent1.merge(parent2);
//
//            // Mutate the child network
//            child.mutate(0.1); // Mutation rate of 10%
//
//            // Add the child to the next generation
//            nextGeneration.add(child);
//        }
//
//        // Replace the current generation with the next generation
//        networks = nextGeneration;
//
//        // Reset the ball position and the networks' lifespans
//        ball.setPosition(ball.getWidth() / 2, ball.getHeight() - 50);
//        for (Network network : networks) {
//            network.resetLifespan();
//        }
//    }
//}
//

//
//import javax.swing.Timer;
//import java.awt.*;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//
//import static bbm.brickbreaker.Bounds.*;
//
//public class Controller {
//    private final Ball ball;
//    private final List<Brick> bricks;
//    private final Paddle paddle;
//    private final GameComponent view;
//    private final int radius;
//    private Timer timer;
//
//    public Controller(Ball ball, List<Brick> bricks, int radius, Paddle paddle, GameComponent view) {
//        this.ball = ball;
//        this.bricks = bricks;
//        this.paddle = paddle;
//        this.radius = radius;
//        this.view = view;
//    }
//
//    public void play() {
//
//        timer = new Timer(1000 / 60, e -> {
//            double newX = ball.locationX();
//            double newY = ball.locationY();
//
//            ball.setPosition(newX, newY);
//            view.repaint();
//
//            Bounds hitDirection = NONE;
//
//            if (ball.hitsWall(radius)) {
//                if (newX - radius <= 0) {
//                    hitDirection = LEFT;
//                } else if (newX + radius >= ball.getWidth()) {
//                    hitDirection = RIGHT;
//                } else if (newY - radius <= 0) {
//                    hitDirection = TOP;
//                } else if (newY + radius > ball.getHeight()) {
//                    timer.stop();
//                }
//
//                if (hitDirection != NONE) {
//                    ball.bounceWalls(hitDirection);
//                }
//            }
//
//            checkPaddleCollision();
//            breakBricks();
//        });
//
//        timer.start();
//    }
//
//    public void breakBricks() {
//        Rectangle ballBounds = new Rectangle((int) ball.getX() - radius,
//                (int) ball.getY() - radius,
//                radius * 2, radius * 2);
//        for (Brick brick : bricks) {
//            if (!brick.isHit() && brick.getBounds().intersects(ballBounds)) {
//                brick.setHit(true);
//                ball.bounceWalls(Bounds.TOP);
//                view.repaint();
//                break;
//            }
//        }
//    }
//
//    public void checkPaddleCollision() {
//        Rectangle paddleBounds = new Rectangle((int) paddle.getX(), (int) paddle.getY(),
//                (int) paddle.getWidth(), (int) paddle.getHeight());
//        Rectangle ballBounds = new Rectangle((int) ball.getX(), (int) ball.getY(),
//                radius * 2, radius * 2);
//
//        if (paddleBounds.intersects(ballBounds)) {
//            double sectionWidth = paddle.getWidth() / 5;
//            double section = (ball.getX() - paddle.getX()) / sectionWidth;
//
//            switch ((int) section) {
//                case 0:
//                    ball.bouncePaddle(LEFT_EDGE);
//                    break;
//                case 1:
//                    ball.bouncePaddle(LEFT);
//                    break;
//                case 2:
//                    ball.bouncePaddle(MIDDLE);
//                    break;
//                case 3:
//                    ball.bouncePaddle(RIGHT);
//                    break;
//                case 4:
//                    ball.bouncePaddle(RIGHT_EDGE);
//                    break;
//                default:
//                    break;
//            }
//        }
//    }
//
//    public void testNetwork() {
//        List<Network> networks = new ArrayList<>();
//        List<Network> good = new ArrayList<>();
//
//        for (int i = 0; i < 1000; i++) {
//            Network n = new Network(paddle);
//            networks.add(n);
//        }
//
//        for (Network network : networks) {
//            if (network.movePaddle() == 1) {
//                good.add(network);
//            }
//        }
//
//        for (int i = 0; i < good.size(); i++) {
//            System.out.println(i + ": " + good.get(i).toString());
//        }
//    }
//}