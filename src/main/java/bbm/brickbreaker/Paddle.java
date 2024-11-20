package bbm.brickbreaker;

import java.awt.*;

public class Paddle extends Rectangle {
    private final int startX;

    public Paddle(int startX, int startY, int width, int height) {
        super(startX, startY, width, height);
        this.startX = startX;
    }

    public void setLocation(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getStartX()
    {
        return startX;
    }
}