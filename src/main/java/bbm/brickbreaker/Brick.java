package bbm.brickbreaker;

import java.awt.*;
import java.util.Random;

public class Brick extends Rectangle {
    private boolean hit;

    public Brick(int x, int y, int width, int height) {
        super(x, y, width, height);
        this.hit = false;
    }

    public boolean isHit() {
        return hit;
    }

    public void setHit(boolean hit) {
        this.hit = hit;
    }

    public boolean contains(int ballCenterX, int ballCenterY) {
        return ballCenterX >= x && ballCenterX <= x + width
                && ballCenterY >= y && ballCenterY <= y + height;
    }
}