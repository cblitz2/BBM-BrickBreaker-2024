package bbm.brickbreaker;

import java.awt.*;
import java.util.Random;

public class Brick extends Rectangle {
    private boolean hit; // Flag to check if the brick has been hit

    public Brick(int x, int y, int width, int height) {
        super(x, y, width, height); // Inherit from Rectangle
        this.hit = false; // Initially, brick is not hit
    }

    // Check if this brick has been hit
    public boolean isHit() {
        return hit;
    }

    // Set the brick as hit
    public void setHit(boolean hit) {
        this.hit = hit;
    }

    // Check if a ball's center is within the bounds of this brick
    public boolean contains(int ballCenterX, int ballCenterY) {
        return ballCenterX >= x && ballCenterX <= x + width
                && ballCenterY >= y && ballCenterY <= y + height;
    }
}