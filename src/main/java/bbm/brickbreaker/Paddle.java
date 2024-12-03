package bbm.brickbreaker;

import java.awt.*;

public class Paddle extends Rectangle {
    public Paddle(int startX, int startY, int width, int height) {
        super(startX, startY, width, height);
    }

    public void setLocation(int x, int y) {
        this.x = x;
        this.y = y;
    }
}