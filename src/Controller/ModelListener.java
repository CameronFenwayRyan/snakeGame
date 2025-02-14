package Controller;

public interface ModelListener {
    /**
     * Called when the snake dies.
     */
    public void snakeDied(int finalScore);

    public void playAgain();
}
