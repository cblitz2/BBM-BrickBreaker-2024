package bbm.brickbreaker;

import java.awt.*;
import java.util.Random;

public class Brick extends Rectangle {
    private final Random rand = new Random();
    private final int width;
    private final int height;
    private static final int NUM_BRICKS = 20;
    private final int [][] bricks;

    public Brick(int width, int height) {
        this.width = width;
        this.height = height;
        this.bricks = new int[width][height];
    }

    public void populateBricks() {
        int placedBricks = 0;

        while (placedBricks < NUM_BRICKS) {
            int x = rand.nextInt(width);
            int y = rand.nextInt(height);

            if (bricks[x][y] == 0) {
                bricks[x][y] = 1;
                placedBricks++;
            }
        }
    }

    public void hitBrick(int x, int y) {
        bricks[x][y] = 0;
    }

    public boolean isBrick(int x, int y) {
        return bricks[x][y] == 1;
    }
}
