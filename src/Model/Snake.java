package Model;

import java.util.List;

/**
 * Interface for a Snake class
 */
public interface Snake {

  /**
   * growSnake grows the snake by one gridspace.
   */
  public void growSnake();

  /**
   * Returns a list of all the coordinates currently taken up by the snake.
   *
   * @return a List of GridCoords
   */
  public List<GridCoord> getSnakeCoords();

  /**
   * setDirection sets the current direction of the snake to the given direction.
   *
   * @param direction an enum Direction either left, right, up, or down.
   *
   * @throws IllegalArgumentException if Direction is null.
   */
  public void setDirection(Direction direction);

  /**
   * Using the current direction of the snake, the coordinates of the snake will change to match
   * the snake's current state.
   */
  public void updateSnakePosition();

  /**
   * Returns the snake to its initial state.
   */
  public void reset();

  /**
   * Returns the direction of the snake.
   */
  public Direction getDirection();
}
