package Model;

import java.util.Objects;

public class GridCoord {
  private final int x;
  private final int y;

  /**
   * Sets the coord with a defined x and y value (using base 0 index). (0,0) is the bottom left
   * of the game grid.
   *
   * @param x the row index.
   * @param y the column index.
   */
  public GridCoord(int x, int y) {
    // Invariant, x coord must be greater than 0.
    if (x >= 0) {
      this.x = x;
    } else {
      throw new IllegalArgumentException("x out of bounds");
    }
    // Invariant, y coord must be greater than 0.
    if (y >= 0) {
      this.y = y;
    } else {
      throw new IllegalArgumentException("y out of bounds");
    }
  }

  /**
   * Get the value x.
   *
   * @return x coord on a base 0 index.
   */
  public int getX() {
    return x;
  }

  /**
   * Get the value y.
   *
   * @return y coord on a base 0 index.
   */
  public int getY() {
    return y;
  }

  /**
   * Two coords are equal if they have the same x and y coords.
   */
  @Override
  public boolean equals(Object other) {
    if (other == null) {
      return false;
    }
    if (!(other instanceof GridCoord)) {
      return false;
    }
    GridCoord otherCoord = (GridCoord) other;
    return this.getX() == otherCoord.getX() && this.getY() == otherCoord.getY();
  }

  /**
   * Hashcode equivalence for coords
   */
  public int hashCode() {
    return Objects.hash(x, y);
  }

  public String toString() {
    return "(" + x + ", " + y + ")";
  }
}
