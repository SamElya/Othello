package g60731.atlir4.othello.controller;

import g60731.atlir4.othello.model.Game;

/**
 * Defines a strategy for playing moves in the game.
 */
public interface Strategy {
    void play(Game game);   //Plays a move in the game according to the implemented strategy.
}
