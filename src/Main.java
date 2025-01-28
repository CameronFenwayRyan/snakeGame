import java.io.IOException;
import java.io.InputStreamReader;

import Controller.ImplSnakeController;
import Controller.SnakeController;
import py4j.GatewayServer;

public class Main {
  public static void main(String[] args) throws IOException {
    SnakeController controller = new ImplSnakeController(10, 3);
    SnakeGame game = new SnakeGame(controller.getModel());
    GatewayServer server = new GatewayServer(game);
    server.start();
    controller.play();
  }
}