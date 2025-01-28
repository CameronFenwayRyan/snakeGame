import Model.SnakeModel;
import py4j.GatewayServer;
import Model.Direction;

public class SnakeGame {
    private boolean snakeAlive; // Your game state class
    private SnakeModel snakeModel; // Your model class

    public SnakeGame(SnakeModel snakeModel) {
        this.snakeModel = snakeModel; // Initialize your model
        this.snakeAlive = true; // Initialize your game
    }

    // Method to get the current game state
    public boolean getState() {
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
}
