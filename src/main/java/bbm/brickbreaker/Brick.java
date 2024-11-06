package bbm.brickbreaker;

import java.util.Random;

public class Brick {
    Random rand = new Random();
    int [][] bricks = new int[8][5];

    public void populateBricks() {
        for (int i = 0; i < bricks.length; i++) {
            for (int j = 0; j < bricks[i].length; j++) {
                if (rand.nextInt(0, 2) == 1) {
                    bricks[i][j] = 1;
                } else {
                    bricks[i][j] = 0;
                }
            }
        }
    }

    public void hitBrick(int x, int y) {
        bricks[x][y] = 0;
    }
}
