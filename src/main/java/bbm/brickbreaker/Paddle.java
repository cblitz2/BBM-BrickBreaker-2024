package bbm.brickbreaker;

import java.awt.*;

public class Paddle extends Rectangle {
//    private int x;
//    private int y;
//    private final int width = 100;
//    private final int height = 20;

    public Paddle(int startX, int startY, int width, int height) {
        super(startX, startY, width, height);
//        this.x = startX;
//        this.y = startY;
    }
    public void setLocation(int x, int y) {
        this.x = x;
        this.y = y;
    }

//    public int getX() {
//        return x;
//    }
//
//    public int getY() {
//        return y;
//    }

//    public int getWidth() {
//        return width;
//    }
//
//    public int getHeight() {
//        return height;
//    }

}