package Model;

import java.util.List;

import Controller.ModelListener;

/**
 * Interface for the main implementation of the snake game.
 */
public interface SnakeModel {

  /**
   * progress the game on a constant interval and makes calls to appropriate
   * helper methods on each interval.
   */
  public void progress();

  /**
   * Play, this will run the program the moment that the player makes an input.
   *
   * @throws IllegalStateException if game is already being played.
   */
  public void start(int gameSize, int apples);

  /**
   * Turns the snake in the specified direction.
   *
   * @param direction an enum that is either left, right, up, or down
   *
   * @throws IllegalArgumentException if Direction is null.
   */
  public void move(Direction direction);

  /**
   * Returns a copy of the score.
   *
   * @return a copy of the score.
   */
  public int getScore();

  /**
   * Returns a copy of the gameSize.
   *
   * @return a copy of the gameSize.
   */
  public int getGameSize();

  /**
   * Returns a copy of the coords of the apples.
   *
   * @return a copy of the coords of the apples.
   */
  public List<GridCoord> getAppleCoords();

  /**
   * Returns a copy of the coords of the snake.
   *
   * @return a copy of the coords of the snake.
   */
  public List<GridCoord> getSnakeCoords();

  /**
   * State that indicates that the game has been started correctly. Will return false when game
   * is ended.
   */
  public boolean isActive();

  /**
   * State that indicates that the main loop of the game is currently in progress.
   */
  public boolean isRunning();

  /**
   * Sets the listener for the model.
   */
  public void setListener(ModelListener listener);

  /**
   * Resets the game to its initial state.
   */
  public void reset();

  /**
   * Returns the current direction of the snake.
   */
  public Direction getSnakeDirection();

  public void playAgain();
}
