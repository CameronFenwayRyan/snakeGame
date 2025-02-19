import java.io.IOException;
import py4j.GatewayServer;

import Controller.ImplSnakeController;
import Controller.SnakeController;

public class Main {
  public static void main(String[] args) throws IOException {
    SnakeController controller = new ImplSnakeController(30, 1);
    MySnakeGame game = new MySnakeGame(controller.getModel());
    GatewayServer server = new GatewayServer(game);
    server.start();
    System.out.println("Gateway Server Started");
    controller.play();
  }
}