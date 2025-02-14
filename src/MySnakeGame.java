import Model.SnakeModel;
import py4j.GatewayServer;
import Model.Direction;
import java.util.ArrayList;
import Model.GridCoord;

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
    public void executeAction(String direction) {
        if (direction.equals("UP")) {
            snakeModel.move(Direction.UP);
        } else if (direction.equals("DOWN")) {
            snakeModel.move(Direction.DOWN);
        } else if (direction.equals("RIGHT")) {
            snakeModel.move(Direction.RIGHT);
        } else {
            snakeModel.move(Direction.LEFT);
        }
    }

    public String getCurrentSnakeDirection() {
        Direction snakeDir = snakeModel.getSnakeDirection();
        switch(snakeDir) {
            case UP:
                return "UP";
            case DOWN:
                return "DOWN";
            case RIGHT:
                return "RIGHT";
            default:
                return "LEFT";
        }
    }

    // Method to reset the game
    public void reset() {
        System.out.println("HEY");
        snakeModel.playAgain();
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
    public ArrayList<Integer> getState() {
        ArrayList<Integer> stateList = new ArrayList<Integer>();
        int headX = snakeModel.getSnakeCoords().get(0).getX();
        int headY = snakeModel.getSnakeCoords().get(0).getY();
        // Danger out of bounds
        stateList.add(headX == snakeModel.getGameSize() ? 0 : 1);
        stateList.add(headX == 0 ? 0 : 1);
        stateList.add(headY == snakeModel.getGameSize() ? 0 : 1);
        stateList.add(headY == 0 ? 0 : 1);
        // Danger run into self
        if (headX != snakeModel.getGameSize() && headX != 0) {
            stateList.add(snakeModel.getSnakeCoords().contains(new GridCoord(headX + 1, headY)) ? 0 : 1);
            stateList.add(snakeModel.getSnakeCoords().contains(new GridCoord(headX - 1, headY)) ? 0 : 1);
        } else {
            stateList.add(0);
            stateList.add(0);
        }
        if (headY != snakeModel.getGameSize() && headY != 0) {
            stateList.add(snakeModel.getSnakeCoords().contains(new GridCoord(headX, headY + 1)) ? 0 : 1);
            stateList.add(snakeModel.getSnakeCoords().contains(new GridCoord(headX, headY - 1)) ? 0 : 1);
        } else {
            stateList.add(0);
            stateList.add(0);
        }
        // Snake current direction
        stateList.add(snakeModel.getSnakeDirection() == Direction.UP ? 0 : 1);
        stateList.add(snakeModel.getSnakeDirection() == Direction.DOWN ? 0 : 1);
        stateList.add(snakeModel.getSnakeDirection() == Direction.RIGHT ? 0 : 1);
        stateList.add(snakeModel.getSnakeDirection() == Direction.LEFT ? 0 : 1);
        // Apple direction
        int appleX = snakeModel.getAppleCoords().get(0).getX();
        int appleY = snakeModel.getAppleCoords().get(0).getY();

        stateList.add(appleX > headX ? 0 : 1);
        stateList.add(appleX <= headX ? 0: 1);
        stateList.add(appleY > headY ? 0 : 1);
        stateList.add(appleY <= headY ? 0 : 1);

        return stateList;
    }
}
