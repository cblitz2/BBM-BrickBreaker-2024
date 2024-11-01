package bbm.brickbreaker;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import static com.sun.java.accessibility.util.AWTEventMonitor.addMouseListener;
import static com.sun.java.accessibility.util.AWTEventMonitor.addMouseMotionListener;

public class Panel {
    private int x;
    private int y;
    private int offsetX;
    private int offsetY;
    private boolean isDragging;

    public void movePanel() {
        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (isDragging) {
                    // Calculate the new position of the panel
                    int currentX = e.getXOnScreen(); // Get mouse position relative to screen
                    int currentY = e.getYOnScreen();

                    // Set the new location of the panel
                    setLocation(currentX - offsetX, currentY - offsetY);
                }
            }
        });

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                // Store the initial position of the mouse relative to the panel
                offsetX = e.getX();
                offsetY = e.getY();
                isDragging = true; // Set dragging flag
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                isDragging = false; // Reset dragging flag
            }
        });
    }

    public void setLocation(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
