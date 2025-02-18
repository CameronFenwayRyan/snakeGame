package Controller;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.Timer;

import Model.Direction;
import Model.ImplSnakeModel;
import Model.SnakeModel;
import View.ImplSnakeView;
import View.SnakeView;

public class ImplSnakeController implements SnakeController, ModelListener, ViewListener {

  private final SnakeModel model;
  private final SnakeView view;
  private final int gameArea;
  private final int apples;

  public ImplSnakeController(int gameArea, int apples) {
    this.model = new ImplSnakeModel(gameArea, apples);
    this.view = new ImplSnakeView(model);
    model.setListener(this);
    view.setListener(this);
    this.gameArea = gameArea;
    this.apples = apples;
  }

  @Override
  public void play() {
    Timer timer = new Timer();
    boolean running = true;
    model.start(gameArea, apples);
    while(running) {
      view.updateView(model.getSnakeCoords(), model.getAppleCoords(), model.getScore());
      Direction nextDirection = view.getNextDirection();
      if (nextDirection != null) {
        model.move(nextDirection);
      }
      model.progress();
      try {
        Thread.sleep(10);
      } catch (InterruptedException e) {
          System.err.println("Loop interrupted");
          running = false; // Exit loop on interruption
      }
    }
  }

  @Override
  public void SnakeMoved(Direction direction) {
    model.move(direction);
  }

  @Override
  public void snakeDied(int finalScore) {
    view.gameOver(finalScore);
  }

  @Override
  public void playAgain() {
    System.out.println("play again");
    view.closeGameOverDialog();
    model.reset();
    model.start(gameArea, apples);
  }

  @Override
  public SnakeModel getModel() {
    return model;
  }
}
