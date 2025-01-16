package View;

import javax.swing.*;
import Controller.ViewListener;
import Model.Direction;
import Model.GridCoord;
import Model.SnakeModel;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.List;
import java.util.Queue;
import java.util.LinkedList;

// Represents the game view
public class ImplSnakeView extends JPanel implements SnakeView {
    private final int TILE_SIZE = 20; // Size of each grid tile
    private final int gameSize;
    private List<GridCoord> snake;
    private List<GridCoord> apples;
    private Queue<Direction> inputQueue = new LinkedList<>();
    private ViewListener listener;
    private boolean firstInputMade = false;

    public ImplSnakeView(SnakeModel model) {
        this.gameSize = model.getGameSize();
        setPreferredSize(new Dimension(gameSize * TILE_SIZE, gameSize * TILE_SIZE));
        setBackground(Color.BLACK);

        // Create and set up the JFrame
        JFrame frame = new JFrame("Snake Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(this); // Add the view (this panel) to the frame
        frame.pack(); // Adjust the frame size to fit the preferred size
        frame.setLocationRelativeTo(null); // Center the window on the screen
        frame.setVisible(true); // Make it visible

        // Make the panel focusable to capture key events
        setFocusable(true);

        // Add a KeyListener
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                Direction newDirection = null;
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_W:
                    case KeyEvent.VK_UP:
                        newDirection = Direction.UP;
                        break;
                    case KeyEvent.VK_S:
                    case KeyEvent.VK_DOWN:
                        newDirection = Direction.DOWN;
                        break;
                    case KeyEvent.VK_A:
                    case KeyEvent.VK_LEFT:
                        newDirection = Direction.LEFT;
                        break;
                    case KeyEvent.VK_D:
                    case KeyEvent.VK_RIGHT:
                        newDirection = Direction.RIGHT;
                        break;
                    case KeyEvent.VK_Q:
                        return;
                    }
            if (newDirection != null) {
                if (inputQueue.size() >= 2) {
                    inputQueue.poll(); // Remove the oldest direction
                }
                inputQueue.add(newDirection);
            }
        }
    });
    }

    @Override
    public Direction getNextDirection() {
        return inputQueue.poll();
    }

    @Override
    public void updateView(List<GridCoord> snakeCoords, List<GridCoord> appleCoords, int score) {
        this.snake = snakeCoords;
        this.apples = appleCoords;
        repaint(); // Trigger a repaint to update the view
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Draw grid
        g.setColor(Color.DARK_GRAY);
        for (int x = 0; x < gameSize; x++) {
            for (int y = 0; y < gameSize; y++) {
                g.drawRect(x * TILE_SIZE, (gameSize - y - 1) * TILE_SIZE, TILE_SIZE, TILE_SIZE);
            }
        }
        // Draw apples
        if (apples != null) {
            for (GridCoord apple : apples) {
                g.setColor(Color.RED);
                g.fillOval(apple.getX() * TILE_SIZE, (gameSize - apple.getY() - 1) * TILE_SIZE, TILE_SIZE, TILE_SIZE);
            }
        }
        // Draw snake
        if (snake != null) {
            g.setColor(Color.GREEN);
            for (GridCoord segment : snake) {
                g.fillRect(segment.getX() * TILE_SIZE, (gameSize - segment.getY() - 1) * TILE_SIZE, TILE_SIZE, TILE_SIZE);
            }
        }
    }


    @Override
    public void setListener(ViewListener listener) {
        this.listener = listener;
    }

    @Override
    public void gameOver(int score) {
        // Create a dialog to show the game over message and score
        int option = JOptionPane.showOptionDialog(this,
                "Game Over! Your score: " + score + "\nWould you like to play again?",
                "Game Over",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                new String[]{"Yes", "No"},
                "Yes");

        // Notify the listener based on the user's choice
        if (option == JOptionPane.YES_OPTION) {
            listener.playAgain();
        } else {
            System.exit(0);
        }
    }
}
