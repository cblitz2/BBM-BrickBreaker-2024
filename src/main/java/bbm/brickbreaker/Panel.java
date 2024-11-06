package bbm.brickbreaker;

public class Panel {
    private int x;
    private int y;

    private final int width = 100;
    private final int height = 20;

    public Panel (int startX, int startY) {
        this.x = startX;
        this.y = startY;
    }
    public void setLocation(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

}