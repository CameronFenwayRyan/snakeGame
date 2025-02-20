// /*import org.junit.Assert;
// import org.junit.Before;
// import org.junit.Test;

// import java.util.List;

// import Model.BasicSnake;
// import Model.Direction;
// import Model.GridCoord;
// import java.util.ArrayList;

// public class TestBasicSnake {
//   List<GridCoord> originalCoords;
//   /**
//    * Set up the original coords expected in the snakeCoords list
//    */
//   @Before
//   public void setUp() {
//     originalCoords = new ArrayList<>();
//     originalCoords.add(new GridCoord(0, 0));
//   }
//   /**
//    * Test functionality of the BasicSnake constructor. Produces correct default.
//    */
//   @Test
//   public void testBasicSnake() {
//     BasicSnake snake = new BasicSnake();
//     Assert.assertEquals(snake.getSnakeCoords(), originalCoords);
//   }

//   /**
//    * Test update behavior.
//    */
//   @Test
//   public void testUpdateAndSetDirection() {
//     BasicSnake snake = new BasicSnake();
//     snake.updateSnakePosition();
//     Assert.assertEquals(snake.getSnakeCoords(), new ArrayList<>(List.of
//             (new GridCoord(0, 1))));
//     snake.updateSnakePosition();
//     Assert.assertEquals(snake.getSnakeCoords(), new ArrayList<>(List.of
//             (new GridCoord(0, 2))));
//     snake.setDirection(Direction.RIGHT);
//     snake.updateSnakePosition();
//     Assert.assertEquals(snake.getSnakeCoords(), new ArrayList<>(List.of
//             (new GridCoord(1, 2))));
//     snake.setDirection(Direction.DOWN);
//     snake.updateSnakePosition();
//     Assert.assertEquals(snake.getSnakeCoords(), new ArrayList<>(List.of
//             (new GridCoord(1, 1))));
//     snake.setDirection(Direction.LEFT);
//     snake.updateSnakePosition();
//     Assert.assertEquals(snake.getSnakeCoords(), new ArrayList<>(List.of
//             (new GridCoord(0, 1))));
//   }

//   /**
//    * Test the growth functionality of the snake.
//    */
//   @Test
//   public void testSnakeGrowth() {
//     BasicSnake snake = new BasicSnake();
//     ArrayList<GridCoord> newCoords = (ArrayList<GridCoord>) originalCoords;
//     newCoords.add(0, new GridCoord(0, 1));

//     snake.updateSnakePosition();
//     snake.growSnake();

//     Assert.assertEquals(snake.getSnakeCoords(), newCoords);

//     snake.updateSnakePosition();
//     snake.growSnake();

//     newCoords.add(0, new GridCoord(0, 2));

//     Assert.assertEquals(snake.getSnakeCoords(), newCoords);

//     snake.setDirection(Direction.RIGHT);
//     snake.updateSnakePosition();

//     newCoords.remove(2);
//     newCoords.add(0, new GridCoord(1, 2));

//     Assert.assertEquals(snake.getSnakeCoords(), newCoords);

//     snake.updateSnakePosition();

//     newCoords.remove(2);
//     newCoords.add(0, new GridCoord(2, 2));

//     Assert.assertEquals(snake.getSnakeCoords(), newCoords);
//   }
// }
