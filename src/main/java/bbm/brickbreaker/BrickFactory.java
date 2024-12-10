package bbm.brickbreaker;

import java.util.Random;

public class BrickFactory {
    private final int screenWidth;
    private final int screenHeight;
    private final int brickWidth;
    private final int brickHeight;

    public BrickFactory(int screenWidth, int screenHeight, int brickWidth, int brickHeight) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight / 2;
        this.brickWidth = brickWidth;
        this.brickHeight = brickHeight;
    }

    public Brick newBrick() {
        Random rand = new Random();
        int x = rand.nextInt(screenWidth - brickWidth);
        int y = rand.nextInt(screenHeight - brickHeight);
        return new Brick(x, y, brickWidth, brickHeight);
    }
}
