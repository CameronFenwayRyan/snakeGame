package Controller;

import java.io.IOException;

import Model.SnakeModel;

/**
 * A controller for the snake game.
 */
public interface SnakeController {
  /**
   * Runs the main loop and listens to user command, either (WASD) or (arrow keys).
   *
   * @param gameSize sets the size of the grid for the game.
   * @param apples sets the max amount of apples.
   */
  public void play();
}
