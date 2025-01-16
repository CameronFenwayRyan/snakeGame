package Controller;

import Model.Direction;

public interface ViewListener {
    /**
     * Called when the snake moves.
     *
     * @param direction indicates the direction that the snake moved in.
     */
    public void SnakeMoved(Direction direction);

    /**
     * Called when the player wants to play again.
     */
    public void playAgain();

    /**
     * Called when the player makes their first move.
     */
    /*public void firstMove();*/
}
