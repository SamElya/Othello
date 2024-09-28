package g60731.atlir4.othello.controller;

import g60731.atlir4.othello.model.Constants;
import g60731.atlir4.othello.model.Game;
import g60731.atlir4.othello.model.GameState;
import g60731.atlir4.othello.view.console.View;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The controller class responsible for managing interactions in the console-based version of the game.
 */
public class ControllerConsole {
    private Game model;
    private View view;

    /**
     * Constructs a new ControllerConsole object and initializes the game.
     */
    public ControllerConsole() {
        initialize();
    }

    /**
     * Initializes the game and starts the main game loop.
     */
    private void initialize() {
        this.view = new View();
        int sizeBoard = view.askSizeBoard(Constants.minSizeBoard
                , Constants.maxSizeBoard);
        view.initialize(sizeBoard);
        this.model = new Game(sizeBoard);
        model.addObserver(view);
        model.initialize();
    }

    /**
     * Starts the main game loop.
     */
    public void start() {
        while (model.getGameState() == GameState.RUNNING) {
            String request = view.askCommand();
            try {
                for (Pattern pattern : PatternControl.values()) {
                    Matcher matcher = pattern.matcher(request);
                    if (matcher.matches()) {
                        if (pattern.equals(PatternControl.TAKE_POSITION)) {
                            compute(matcher, false);
                        }
                        else if (pattern.equals(PatternControl.SURRENDER)) {
                            compute(matcher, true);
                        }
                        else if (pattern.equals(PatternControl.UNDO)) {
                            undo();
                        }
                        else if (pattern.equals(PatternControl.REDO)) {
                            redo();
                        }
                        else if (pattern.equals(PatternControl.HELP)) {
                            view.help();
                        }
                        else {
                            view.incorrectChoice();
                        }
                        break;
                    }
                }
            } catch (IllegalArgumentException i) {
                view.incorrectChoice();
            }
        }
    }

    /**
     * Executes the redo operation in the game.
     */
    private void redo() {
        Command command = new OthelloCompute(model);
        command.redo();
    }

    /**
     * Executes the undo operation in the game.
     */
    private void undo() {
        Command command = new OthelloCompute(model);
        command.undo();
    }

    /**
     * Computes a move in the game based on the provided matcher and surrender flag.
     *
     * @param matcher   The matcher object containing the command details.
     * @param surrender Indicates if the move is a surrender.
     */
    private void compute(Matcher matcher, boolean surrender) {
        Command command;
        if (!surrender) {
            int x = Integer.parseInt(matcher.group(1));
            int y = Integer.parseInt(matcher.group(2));

            command = new OthelloCompute(model, x, y , false);
        }
        else {
            command = new OthelloCompute(model, true);
        }

        command.execute();
    }

}
