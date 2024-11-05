package bbm.brickbreaker;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import static com.sun.java.accessibility.util.AWTEventMonitor.addMouseListener;
import static com.sun.java.accessibility.util.AWTEventMonitor.addMouseMotionListener;

public class Panel {
    private int x;
    private int y;
//    private int offsetX;
//    private int offsetY;
//    private boolean isDragging;

    public void movePanel(MouseEvent e, Boolean isDragging, int offsetX, int offsetY) {
        if (isDragging) {
            // Calculate the new position of the panel
            int currentX = e.getXOnScreen(); // Get mouse position relative to screen
            int currentY = e.getYOnScreen();

            // Set the new location of the panel
            setLocation(currentX - offsetX, currentY - offsetY);
        }
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
}
