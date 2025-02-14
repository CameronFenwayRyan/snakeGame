import Model.SnakeModel;
import py4j.GatewayServer;
import Model.Direction;
import java.util.ArrayList;

public class MySnakeGame {
    private boolean snakeAlive; // Your game state class
    private SnakeModel snakeModel; // Your model class

    public MySnakeGame(SnakeModel snakeModel) {
        this.snakeModel = snakeModel; // Initialize your model
        this.snakeAlive = true; // Initialize your game
    }

    // Method to get the current game state
    public boolean isGameOver() {
        return snakeModel.isRunning();
    }

    // Method to execute an action
    public void executeAction(Direction direction) {
        snakeModel.move(direction); // Update the game based on the action
    }

    // Method to reset the game
    public void reset() {
        snakeModel.reset();
    }

    public int getScore() {
        return snakeModel.getScore();
    }

    /* Returns an array containing:
            Snake X coord
            Snake Y coord
            Length of snake
            X coord of food position
            Y coord of food position
            Size of the board
            Current snake direction        
    */
    public int[] getState() {
        return new int[]{
            snakeModel.getSnakeCoords().get(0).getX(),  // Snake head X
            snakeModel.getSnakeCoords().get(0).getY(),  // Snake head Y
            snakeModel.getSnakeCoords().size(),         // Snake length
            snakeModel.getAppleCoords().get(0).getX(),  // Food X
            snakeModel.getAppleCoords().get(0).getY(),  // Food Y
            snakeModel.getGameSize(),                   // Board size
            getDirectionValue(snakeModel.getSnakeDirection()) // Encoded direction
        };
    }

    // Helper method for encoding direction as an integer
    private int getDirectionValue(Direction dir) {
        switch (dir) {
            case UP: return 0;
            case RIGHT: return 1;
            case LEFT: return 2;
            case DOWN: return 3;
            default: return -1;  // Error case (shouldn't happen)
        }
    }
}
