package bbm.brickbreaker;

import java.awt.*;

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
}