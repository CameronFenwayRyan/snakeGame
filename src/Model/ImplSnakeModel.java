package Model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import Controller.ImplSnakeController;
import Controller.ModelListener;
import View.ImplSnakeView;

/**
 * Model for the snake game.
 */
public class ImplSnakeModel implements SnakeModel {

  private int gameSize;
  private int apples;
  private int score = 0;
  private boolean running = false;
  // A set containing the coordinates of all the apples currently in the game
  private final Set<GridCoord> appleCoords = new HashSet<>();
  // Instance of the BasicSnake class, representing our snake
  private final Snake snake = new BasicSnake();
  private ModelListener listener;
  private int runFrameTracker = 0;

  /**
   * Initializes the game with the size of the grid (2-10) and the amount of apples
   * (1-10).
   */
  public ImplSnakeModel(int gameSize, int apples) {
    // Constructs the snake model
    this.gameSize = gameSize;
    this.apples = apples;
  }

  @Override
  public void progress() {
    running = true;
    if (willSnakeBeOutOfBounds()) {
      System.out.println("Out of bounds");
      running = false;
    }
    // Implement the behaviors that occur every interval
    snake.updateSnakePosition();
    // Check if the game has ended
    if (hasSnakeRunIntoItself()) {
      running = false;
    }
    // Check if the snake is eating a yummy apple!
    if (snake.getSnakeCoords().size() != 0) {
      GridCoord snakeHead = snake.getSnakeCoords().get(0);
      tryAppleCollect(snakeHead);
    }
    // Behavior for eating an apple
    if (running == false) {
      listener.snakeDied(score);
    }
    runFrameTracker++;
  }

  /**
   * Detects and eats apple and grows snake accordingly.
   *
   * @param snakeHead the coords of the snake head.
   */
  private void tryAppleCollect(GridCoord snakeHead) {
    if (appleCoords.contains(snakeHead)) {
      // Add to your score
      score++;
      // Grow the snake
      snake.growSnake();
      // Remove the apple at the given coordinate
      appleCoords.remove(snakeHead);
      // Change the location of the apple
      setApple();
    }
  }

  /**
   * Checks whether the snake has gone out of bounds.
   *
   * @return boolean indicating if the snake is out of bounds.
   */
  private boolean willSnakeBeOutOfBounds() {
    GridCoord snakeHead = snake.getSnakeCoords().get(0);
    // Check if the snake will go out of bounds
    if (snakeHead.getX() == 0) {
      if (snake.getDirection() == Direction.LEFT) {
        return true;
      }
    }
    if (snakeHead.getY() == 0) {
      if (snake.getDirection() == Direction.DOWN) {
        return true;
      }
    }
    if (snakeHead.getX() == gameSize - 1) {
      if (snake.getDirection() == Direction.RIGHT) {
        return true;
      }
    }
    if (snakeHead.getY() == gameSize - 1) {
      if (snake.getDirection() == Direction.UP) {
        return true;
      }
    }
    return false;
  }

  /**
   * Checks whether the snake has run into itself.
   *
   * @return boolean indicating if the snake has run into itself.
   */
  private boolean hasSnakeRunIntoItself() {
    // Convert the list to a set
    Set<GridCoord> set = new HashSet<>(snake.getSnakeCoords());
    // If the set is smaller than the list than two values are the same
    return set.size() != snake.getSnakeCoords().size();
  }

  @Override
  public void start(int gameSize, int apples) {
    if (running) {
      throw new IllegalStateException("Model is already running");
    }
    int totalCells = gameSize * gameSize;
    // If the amount of cells when the game is initiated (totalCells - 1) is less than the amount
    // of apples, throw an exception.
    if (totalCells - 1 < apples) {
      throw new IllegalArgumentException("Apples cannot be greater than the number of cells");
    }
    this.gameSize = gameSize;
    this.apples = apples;
    score = 0;
    for (int i = 0; i < apples; i++) {
      setApple();
    }
    running = true;
  }

  @Override
  public void move(Direction direction) {
    snake.setDirection(direction);
  }

  /**
   * Called as a part of play method, sets the apple to populate a random coordinate of the grid
   * with no coordinates being the same and no coordinates housing part of the snake.
   */
  private void setApple() {
    ArrayList<GridCoord> illegalCoords = getIllegalCoords();
    Random random = new Random();
    // Generate random coordinates
    boolean findingValidAppleCoord = true;
    // Generate a random coordinate where the apple can go, we know there exists at least one
    while (findingValidAppleCoord) {
      int ranX = random.nextInt(gameSize);
      int ranY = random.nextInt(gameSize);
      GridCoord appleCoord = new GridCoord(ranX, ranY);
      if (!illegalCoords.contains(appleCoord)) {
        // Add the found coordinate to the list of appleCoords
        appleCoords.add(appleCoord);
        findingValidAppleCoord = false;
      }
    }
  }

  /**
   * Gets the coords of the snake and apples.
   *
   * @return a list of all the occupied coords.
   */
  private ArrayList<GridCoord> getIllegalCoords() {
    // Add the coords that the snake occupies
    ArrayList<GridCoord> illegalCoords = new ArrayList<>(snake.getSnakeCoords());
    // Add the coords that the apples occupy
    illegalCoords.addAll(appleCoords);
    return illegalCoords;
  }

  @Override
  public void reset() {
    // Reset the game
    running = false;
    score = 0;
    snake.reset();
    appleCoords.clear();
  }

  @Override
  public void playAgain() {
    System.out.println("play again impl model");
    listener.playAgain();
  }

  public int getScore() {
    return score;
  }

  public int getGameSize() {
    return gameSize;
  }

  public List<GridCoord> getAppleCoords() {
    return new ArrayList<>(appleCoords);
  }

  public List<GridCoord> getSnakeCoords() {
    return new ArrayList<>(snake.getSnakeCoords());
  }

  public boolean isRunning() {
    System.out.println(running + "running");
    return running;
  }

  @Override
  public void setListener(ModelListener listener) {
    // Set the listener
    this.listener = listener;
  }

  @Override
  public Direction getSnakeDirection() {
    return snake.getDirection();
  }
}
