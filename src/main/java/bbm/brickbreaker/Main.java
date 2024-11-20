package bbm.brickbreaker;

import java.awt.font.GlyphMetrics;

public class Main {
    public static void main(String[] args) {
        Paddle paddle = new Paddle(350, 520, 100, 20);
        Ball ball = new Ball(45, 800, 600, paddle.getX() + 40, paddle.getY() - 20);
        NetworkController controller = new NetworkController(ball, 10, paddle);
        controller.generate();
    }
}
