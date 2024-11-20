package bbm.brickbreaker;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
       // new GameFrame().setVisible(true);

        Paddle paddle = new Paddle(350, 520, 100, 20);
        Ball ball = new Ball(45, 20, 10, paddle.getX() + 40, paddle.getY() + 20);
        NetworkController controller = new NetworkController(ball, 10, paddle);
        controller.generate();
    }
}
