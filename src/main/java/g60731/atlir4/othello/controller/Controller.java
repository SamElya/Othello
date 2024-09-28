package g60731.atlir4.othello.controller;

import g60731.atlir4.othello.model.ColorToken;
import g60731.atlir4.othello.model.Game;
import g60731.atlir4.othello.model.GameState;
import g60731.atlir4.othello.view.javaFx.MainPane;

/**
 * The controller class responsible for managing interactions between the model and the view.
 */
public class Controller {
    private Game model;
    private MainPane view;
    private Strategy strategy;

    public Controller() {
        //constructor garantit la solidité des attribut
        //c'est pour ca que les parametres doivent etre non NUll
        //Objects.requireNonNull(model, "model Null");
        //Objects.requireNonNull(view, "view Null");
        //this.model = model;
        //this.view = view;
    }

    /**
     * Initializes the controller with the specified view and board size.
     *
     * @param view The view associated with the controller.
     * @param size The size of the game board.
     */
    public void initialize(MainPane view, int size) {
        this.model = new Game(size);
        this.view = view;
        model.addObserver(view);
        model.initialize();
        view.initialize(this);
    }

    /**
     * Computes a move in the game.
     *
     * @param x        The x-coordinate of the move.
     * @param y        The y-coordinate of the move.
     * @param surrender Indicates if the move is a surrender.
     */
    public void compute(int x, int y, boolean surrender) {
        Command command;
        if (!surrender) {
            command = new OthelloCompute(model, view, x, y , false);
        }
        else {
            command = new OthelloCompute(model, true);
        }

        command.execute();
        if (model.getGameState() == GameState.RUNNING
                && strategy != null
                && model.getCurrentPlayer() == ColorToken.WHITE) {
            strategy.play(model);
        }
    }

    /**
     * Redoes the last move in the game.
     */
    public void redo() {
        Command command = new OthelloCompute(model);
        command.redo();
        if (model.getGameState() == GameState.RUNNING && strategy != null) {
            Command commandCPU = new OthelloCompute(model);
            commandCPU.redo();
        }
    }

    /**
     * Undoes the last move in the game.
     */
    public void undo() {
        Command command = new OthelloCompute(model);
        command.undo();
        if (model.getGameState() == GameState.RUNNING && strategy != null) {
            Command commandCPU = new OthelloCompute(model);
            commandCPU.undo();
        }
    }

    /**
     * Sets the strategy for the game.
     *
     * @param smart Indicates if the strategy is smart.
     */
    public void setStrategy(boolean smart) {
        System.out.println(smart);
        if (smart) {
            strategy = new PseudoSmartStrategy();
        }
        else {
            strategy = new DumbStrategy();
        }
    }
}
