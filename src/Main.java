import java.io.IOException;
import java.io.InputStreamReader;

import Controller.ImplSnakeController;
import Controller.SnakeController;

public class Main {
  public static void main(String[] args) throws IOException {
    SnakeController controller = new ImplSnakeController(10, 3);
    controller.play();
  }
}