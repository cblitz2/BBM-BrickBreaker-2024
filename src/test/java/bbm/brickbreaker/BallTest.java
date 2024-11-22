package bbm.brickbreaker;

import org.junit.jupiter.api.Test;

import static bbm.brickbreaker.Bounds.*;
import static org.junit.jupiter.api.Assertions.*;


class BallTest {
    @Test
    void bounceWalls() {
        Ball ball = new Ball(45, 800, 600,  100, 100);

        ball.bounceWalls(RIGHT);
        assertEquals(ball.getAngle(), 135);

        ball.bounceWalls(LEFT);
        assertEquals(ball.getAngle(), 45);

        ball.bounceWalls(TOP);
        assertEquals(ball.getAngle(), 315);

    }

    @Test
    void bouncePaddle() {
        Ball ball = new Ball(45, 800, 600, 100, 100);

        ball.bouncePaddle(LEFT);
        assertEquals(ball.getAngle(), 315);

        ball.bouncePaddle(RIGHT);
        assertEquals(ball.getAngle(), 225);

        ball.bouncePaddle(LEFT_EDGE);
        assertEquals(ball.getAngle(), 200);

        ball.bouncePaddle(RIGHT_EDGE);
        assertEquals(ball.getAngle(), 340);

        ball.bouncePaddle(MIDDLE);
        assertEquals(ball.getAngle(), 270);
    }
}