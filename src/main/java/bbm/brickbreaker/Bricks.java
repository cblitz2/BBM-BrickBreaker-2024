package bbm.brickbreaker;

import java.awt.*;
import java.util.Random;

public class Bricks extends Rectangle {
    private final Random rand = new Random();
    private final int width;
    private final int height;
    private final int rows = 12;
    private final int cols = 20;
    private static final int NUM_BRICKS = 20;
    private final int [][] bricks;

    public Bricks(int width, int height) {
        this.width = width;
        this.height = height;
        this.bricks = new int[width][height];
    }

    public void populateBricks() {
        int placedBricks = 0;

        while (placedBricks < NUM_BRICKS) {
            int x = rand.nextInt(cols);
            int y = rand.nextInt(rows);

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

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }
}