package View;

import java.util.List;

import Controller.ViewListener;
import Model.Direction;
import Model.GridCoord;

/**
 * Interface for the snake view
 */
public interface SnakeView {
  /**
   * Sets the ViewListener.
   *
   * @param listener the ViewListener.
   */
  public void setListener(ViewListener listener);

  /**
   * Called on every interval to update the view to match the model.
   * 
   * @param snakeCoords the coordinates of the snake (head at index 0).
   * @param appleCoords the coordinates of the apples on the board.
   * @param score the player's current score. 
   */
  public void updateView(List<GridCoord> snakeCoords, List<GridCoord> appleCoords, int score);

  /**
   * Called when the game is over.
   * 
   * @param score the player's final score.
   */
  public void gameOver(int score);

  /**
   * Called when there are multiple directions in the queue.
   */
  public Direction getNextDirection();
}