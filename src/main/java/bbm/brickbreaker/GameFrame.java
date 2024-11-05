//package bbm.brickbreaker;
//
//import javax.swing.*;
//import java.awt.*;
//import java.awt.event.MouseAdapter;
//import java.awt.event.MouseEvent;
//import java.awt.event.MouseMotionAdapter;
//
//import static com.sun.java.accessibility.util.AWTEventMonitor.addMouseListener;
//import static com.sun.java.accessibility.util.AWTEventMonitor.addMouseMotionListener;
//
//public class GameFrame extends JFrame {
//
//    public GameFrame() {
//        setSize(800, 600);
//        setTitle("Brick Breaker");
//        setDefaultCloseOperation(EXIT_ON_CLOSE);
//
//        Brick brick = new Brick(500, 200);
//        brick.populateBricks();
//
//        GameComponent component = new GameComponent(brick);
//        setLayout(new BorderLayout());
//        add(component, BorderLayout.CENTER);
//
//        JLabel bar = new JLabel();
//
////
////
////        //Panel panel = new Panel();
////        //panel.movePanel();
////        int offsetX;
////        int offsetY;
////        boolean isDragging = false;
////
////        bar.addMouseMotionListener(new MouseMotionAdapter() {
////            @Override
////            public void mouseDragged(MouseEvent e) {
////                panel.movePanel(e, isDragging, offsetX, offsetY);
//////                if (isDragging) {
//////                    // Calculate the new position of the panel
//////                    int currentX = e.getXOnScreen(); // Get mouse position relative to screen
//////                    int currentY = e.getYOnScreen();
//////
//////                    // Set the new location of the panel
//////                    setLocation(currentX - offsetX, currentY - offsetY);
//////                }
////            }
////        });
////
////        bar.addMouseListener(new MouseAdapter() {
////
////            @Override
////            public void mousePressed(MouseEvent e) {
////                // Store the initial position of the mouse relative to the panel
////                e.getX();
////                e.getY();
////                isDragging = true; // Set dragging flag
////            }
////
////            @Override
////            public void mouseReleased(MouseEvent e) {
////                isDragging = false; // Reset dragging flag
////            }
////        });
////    }
//
//        // Offset variables to track drag offset
//        final int[] offsetX = {0};
//        final int[] offsetY = {0};
//
//        // Mouse listeners for dragging
//        bar.addMouseListener(new MouseAdapter() {
//            @Override
//            public void mousePressed(MouseEvent e) {
//                // Capture the offset at the start of the drag
//                offsetX[0] = e.getX();
//                offsetY[0] = e.getY();
//            }
//        });
//
//        bar.addMouseMotionListener(new MouseMotionAdapter() {
//            @Override
//            public void mouseDragged(MouseEvent e) {
//                // Calculate new position based on mouse location and offset
//                int newX = e.getXOnScreen() - offsetX[0];
//                int newY = e.getYOnScreen() - offsetY[0];
//
//                // Set the new location of the JLabel
//                bar.setLocation(newX, newY);
//            }
//        });
//        add(bar, BorderLayout.SOUTH);
//    }
//}


package bbm.brickbreaker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

public class GameFrame extends JFrame {

    public GameFrame() {
        setSize(800, 600);
        setTitle("Brick Breaker");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null); // Use null layout for absolute positioning

        // Create and populate bricks
        Brick brick = new Brick(500, 200);
        brick.populateBricks();

        // Add the game component for bricks
        GameComponent component = new GameComponent(brick);
        component.setBounds(0, 0, 800, 600); // Full size for component
        add(component);

        // Create and configure the bar
        JLabel bar = new JLabel();
        bar.setOpaque(true);
        bar.setBackground(Color.BLUE);
        bar.setBounds(350, 520, 100, 20); // Position bar near the bottom of the screen
        add(bar);

        // Offset variables for tracking drag
        int[] offsetX = {0};
        int[] offsetY = {0};

        // Mouse listeners for dragging the bar
        bar.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                offsetX[0] = e.getX();
                offsetY[0] = e.getY();
            }
        });

        bar.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                int newX = e.getXOnScreen() - offsetX[0];
                int newY = bar.getY(); // Lock the Y position so it only moves horizontally
                bar.setLocation(newX, newY);
            }
        });

        component.repaint();
    }
}
