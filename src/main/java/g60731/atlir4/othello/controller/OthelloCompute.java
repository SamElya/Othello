package g60731.atlir4.othello.controller;

import g60731.atlir4.othello.model.Game;
import g60731.atlir4.othello.model.Position;
import g60731.atlir4.othello.view.javaFx.MainPane;

/**
 * Represents a command to compute moves in the Othello game.
 */
public class OthelloCompute implements Command{

    private final Game model;
    private final Position position;
    private final boolean surrender;
    private final MainPane view;

    /**
     * Constructs an OthelloCompute command with the specified parameters.
     *
     * @param model     The game model.
     * @param view      The main view of the game.
     * @param x         The x-coordinate of the move.
     * @param y         The y-coordinate of the move.
     * @param surrender Indicates whether the move is a surrender.
     */
    public OthelloCompute(Game model, MainPane view, int x, int y, boolean surrender) {
        this.model = model;
        this.position = new Position(x, y);
        this.surrender = surrender;
        this.view = view;
    }

    /**
     * Constructs an OthelloCompute command with the specified parameters.
     *
     * @param model     The game model.
     * @param x         The x-coordinate of the move.
     * @param y         The y-coordinate of the move.
     * @param surrender Indicates whether the move is a surrender.
     */
    public OthelloCompute(Game model, int x, int y, boolean surrender) {
        this(model, null, x, y, surrender);
    }

    /**
     * Constructs an OthelloCompute command with the specified parameters.
     *
     * @param model     The game model.
     * @param surrender Indicates whether the move is a surrender.
     */
    public OthelloCompute(Game model, boolean surrender) {
        this(model, null, 0, 0, surrender);
    }

    /**
     * Constructs an OthelloCompute command with the specified parameters.
     *
     * @param model The game model.
     */
    public OthelloCompute(Game model) {
        this(model, null, 0, 0, false);
    }

    @Override
    public void execute() {
        try {
            model.addPiece(position, surrender);
        } catch (IllegalArgumentException i) {
            if (view != null) {
                view.showInvalidMove();
            }
        }
    }

    @Override
    public void undo() {
        model.undoGame();
    }

    @Override
    public void redo() {
        model.redoGame();
    }
}
