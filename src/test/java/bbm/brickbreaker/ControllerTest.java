package bbm.brickbreaker;

import static org.mockito.Mockito.*;
import org.junit.jupiter.api.Test;

import java.awt.Rectangle;
import java.util.List;

public class ControllerTest {

    @Test
    public void breakBricks() {
        Ball ball = mock(Ball.class);
        GameComponent view = mock(GameComponent.class);
        Brick brick = mock(Brick.class);
        Paddle paddle = mock(Paddle.class);

        double ballX = 50.0;
        double ballY = 50.0;
        double radius = 10.0;

        when(ball.getX()).thenReturn(ballX);
        when(ball.getY()).thenReturn(ballY);

        Rectangle brickBounds = new Rectangle(45, 45, 20, 20);
        when(brick.getBounds()).thenReturn(brickBounds);
        when(brick.isHit()).thenReturn(false);

        Controller controller = new Controller(ball, List.of(brick), (int) radius, paddle, view);

        controller.breakBricks();
        verify(brick).setHit(true);
        verify(ball).bounceWalls(Bounds.TOP);
        verify(view).repaint();
    }

    @Test
    public void checkPaddleCollision() {
        Paddle paddle = mock(Paddle.class);
        Ball ball = mock(Ball.class);

        when(paddle.getX()).thenReturn(100.0);
        when(paddle.getY()).thenReturn(400.0);
        when(paddle.getWidth()).thenReturn(100.0);
        when(paddle.getHeight()).thenReturn(20.0);
        when(ball.getY()).thenReturn(400.0);
        when(ball.getX()).thenReturn(110.0);

        GameComponent view = mock(GameComponent.class);
        Controller controller = new Controller(ball, List.of(), 10, paddle, view);

        controller.checkPaddleCollision();
        verify(ball).bouncePaddle(Bounds.LEFT_EDGE);

        when(ball.getX()).thenReturn(130.0);
        controller.checkPaddleCollision();
        verify(ball).bouncePaddle(Bounds.LEFT);

        when(ball.getX()).thenReturn(150.0);
        controller.checkPaddleCollision();
        verify(ball).bouncePaddle(Bounds.MIDDLE);

        when(ball.getX()).thenReturn(170.0);
        controller.checkPaddleCollision();
        verify(ball).bouncePaddle(Bounds.RIGHT);

        when(ball.getX()).thenReturn(190.0);
        controller.checkPaddleCollision();
        verify(ball).bouncePaddle(Bounds.RIGHT_EDGE);
    }
}