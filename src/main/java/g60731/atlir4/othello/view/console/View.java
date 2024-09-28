package g60731.atlir4.othello.view.console;

import g60731.atlir4.othello.model.ColorToken;
import g60731.atlir4.othello.model.Position;
import g60731.atlir4.othello.model.State;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Represents the console view of the Othello game.
 */
public class View implements PropertyChangeListener {
    private final Scanner input;
    private ColorTokenView[][] boardView;
    private boolean isInitialized;

    /**
     * Constructs a new View object.
     */
    public View() {
        input = new Scanner(System.in, StandardCharsets.UTF_8);
        this.boardView = new ColorTokenView[][] {};
        this.isInitialized = false;
    }

    /**
     * Initializes the board view with the specified size.
     * @param sizeBoard The size of the board.
     */
    public void initialize(int sizeBoard) {
        if (!isInitialized) {
            this.boardView = new ColorTokenView[sizeBoard][sizeBoard];
            for (int row = 0; row < boardView.length; row++) {
                for (int col = 0; col < boardView.length; col++) {
                    boardView[row][col] = ColorTokenView.EMPTY;
                }
            }
            isInitialized = true;
        }
    }

    /**
     * Displays the current state of the game board.
     */
    public void displayBoard() {
        for (int row = 0; row < boardView.length; row++) {
            System.out.printf("%02d| ", row);
            for (int col = 0; col < boardView.length; col++) {
                displayToken(boardView[row][col]);
            }
            System.out.println(" |");
        }
        System.out.print("   ");
        for (int col = 0; col < boardView.length; col++) {
            System.out.printf(" %02d", col);
        }
        System.out.println();
    }

    //Displays the token representation for the given color token view.
    private void displayToken(ColorTokenView colorToken) {
        switch (colorToken) {
            case WHITE -> System.out.print(" W ");
            case BLACK -> System.out.print(" B ");
            case EMPTY -> System.out.print(" . ");
            case POSSIBLE -> System.out.print(" X ");
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        State state = (State)evt.getNewValue();
        switch (evt.getPropertyName()) {
            case "BoardState":
                modifyBoard(state);
                break;
            case "InitState":
                initBoard(state);
                break;
            case "SetBoard":
                setBoard(state);
            default:
                break;
        }
    }

    //Sets the board state based on the provided game state.
    private void setBoard(State state) {
        ArrayList<Position> blackTokens = state.getBlackTokens();
        ArrayList<Position> whiteTokens = state.getWhiteTokens();
        ArrayList<Position> possibleMove = state.getPossibleMove();
        for (int row = 0; row < boardView.length; row++) {
            for (int col = 0; col < boardView.length; col++) {
                if (blackTokens.contains(new Position(col, row))) {
                    boardView[row][col] = ColorTokenView.BLACK;
                }
                else if (whiteTokens.contains(new Position(col, row))) {
                    boardView[row][col] = ColorTokenView.WHITE;
                }
                else if (possibleMove.contains(new Position(col, row))) {
                    boardView[row][col] = ColorTokenView.POSSIBLE;
                }
                else {
                    boardView[row][col] = ColorTokenView.EMPTY;
                }
            }
        }
        displayScore(state);
        displayBoard();
    }

    //Initializes the board based on the initial state of the game.
    private void initBoard(State state) {
        for (Position position : state.getBlackTokens()) {
            boardView[position.getY()][position.getX()] = ColorTokenView.BLACK;
        }
        for (Position position : state.getWhiteTokens()) {
            boardView[position.getY()][position.getX()] = ColorTokenView.WHITE;
        }
        for (Position position : state.getPossibleMove()) {
            boardView[position.getY()][position.getX()] = ColorTokenView.POSSIBLE;
        }
        displayBoard();
        for (Position position : state.getPossibleMove()) {
            boardView[position.getY()][position.getX()] = ColorTokenView.EMPTY;
        }
    }

    //Modifies the board based on the current state of the game.
    private void modifyBoard(State state) {
        switch (state.getGameState()) {
            case RUNNING:
                displayState(state);
                break;
            case ENDGAME:
            case SURRENDER:
                displayEndGame(state);
        }
    }

    //Displays the end game state.
    private void displayEndGame(State state) {
        displayScore(state);
        displayWinner(state);
    }

    //Displays the winner of the game.
    private void displayWinner(State state) {
        int scoreBlackPlayer = state.getScore()[0];
        int scoreWhitePlayer = state.getScore()[1];
        if (scoreBlackPlayer > scoreWhitePlayer) {
            System.out.println("Black player Won");
        }
        else if (scoreBlackPlayer < scoreWhitePlayer) {
            System.out.println("White player Won");
        }
        else {
            System.out.println("ex aequo");
        }
    }

    //Displays the current state of the game.
    private void displayState(State state) {
        for (Position position : state.getModifyBoard()) {
            addToken(position, state.getColorToken());
        }
        for (Position position : state.getPossibleMove()) {
            boardView[position.getY()][position.getX()] = ColorTokenView.POSSIBLE;
        }
        displayScore(state);
        displayBoard();
        for (Position position : state.getPossibleMove()) {
            boardView[position.getY()][position.getX()] = ColorTokenView.EMPTY;
        }
    }

    //Displays the score of the game.
    private void displayScore(State state) {
        String stringScore = "Score:\n";
        stringScore += "Black: " + state.getScore()[0] + "\n";
        stringScore += "White: " + state.getScore()[1] + "\n";
        System.out.print(stringScore);
    }

    //Adds a token to the board.
    private void addToken(Position position, ColorToken colorToken) {
        switch (colorToken) {
            case BLACK:
                boardView[position.getY()]
                        [position.getX()] = ColorTokenView.BLACK;
                break;
            case WHITE:
                boardView[position.getY()]
                        [position.getX()] = ColorTokenView.WHITE;
                break;
        }
    }

    //Reads an integer from the input with robustness.
    private static int robustReading(String message, int min, int max) {

        Scanner keyboard = new Scanner(System.in);
        System.out.print(message);

        while (!keyboard.hasNextInt()) {

            keyboard.nextLine();
            System.out.println("Please enter an int: ");
        }
        int value = keyboard.nextInt();
        if (value < min || value > max) {
            System.out.println("Incorrect size!");
            System.out.println("Enter a number between " + min + " and " + max);
            value = robustReading(message, min, max);
        }
        return value;
    }

    /**
     * Asks the user to input the size of the board.
     *
     * @param min The minimum size allowed.
     * @param max The maximum size allowed.
     * @return The size of the board.
     */
    public int askSizeBoard(int min, int max) {
        String message = "Enter the size of Board: ";
        return robustReading(message, min, max);
    }

    /**
     * Asks the user to input a command.
     *
     * @return The command entered by the user.
     */
    public String askCommand() {

        System.out.println("(x, y)? (help to see choice): ");

        return input.nextLine();
    }

    /**
     * Displays the help information.
     */
    public void help() {
        String help =
                """
                - <x> <y> to play
                - <s> to surrender
                - <u> to undo choice
                - <r> to redo choice
                """;
        System.out.println(help);
    }

    /**
     * Displays a message indicating an incorrect choice.
     */
    public void incorrectChoice() {
        System.out.println("incorrect");
    }
}
