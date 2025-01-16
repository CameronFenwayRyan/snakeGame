package Model;

import java.util.ArrayList;
import java.util.List;

public class BasicSnake implements Snake {
  // The coordinates of the grid that the snake currently occupies
  private final ArrayList<GridCoord> snakeCoords;
  private Direction snakeDirection = Direction.UP;
  private GridCoord newTailCoord;

  public BasicSnake() {
    snakeCoords = new ArrayList<>();
    snakeCoords.add(new GridCoord(0, 0));
  }

  @Override
  public void updateSnakePosition() {
    int currentHead = snakeCoords.get(0).getX();
    int currentTail = snakeCoords.get(0).getY();
    switch (snakeDirection) {
      case UP:
        snakeCoords.add(0, new GridCoord(currentHead, currentTail + 1));
        break;
      case DOWN:
        snakeCoords.add(0, new GridCoord(currentHead, currentTail - 1));
        break;
      case LEFT:
        snakeCoords.add(0, new GridCoord(currentHead - 1, currentTail));
        break;
      case RIGHT:
        snakeCoords.add(0, new GridCoord(currentHead + 1, currentTail));
        break;
      default:
        break;
    }
    // New tail coord equals the current tail
    newTailCoord = snakeCoords.get(snakeCoords.size() - 1);
    // Delete the current tail
    snakeCoords.remove(snakeCoords.size() - 1);
  }

  @Override
  public void growSnake() {
    snakeCoords.add(newTailCoord);
  }

  @Override
  public List<GridCoord> getSnakeCoords() {
    // Returns a shallow copy of the snake's coordinates
    return new ArrayList<>(snakeCoords);
  }

  @Override
  public void setDirection(Direction direction) {
    if (direction == null) {
      throw new IllegalArgumentException("Direction cannot be null");
    }
    if (snakeDirection == Direction.UP && direction != Direction.DOWN 
    || snakeDirection == Direction.DOWN && direction != Direction.UP 
    || snakeDirection == Direction.LEFT && direction != Direction.RIGHT 
    || snakeDirection == Direction.RIGHT && direction != Direction.LEFT) {
      this.snakeDirection = direction;
    }
  }

  @Override
  public void reset() {
    snakeCoords.clear();
    snakeCoords.add(new GridCoord(0, 0));
    snakeDirection = Direction.UP;
  }
}
