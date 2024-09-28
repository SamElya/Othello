package g60731.atlir4.othello.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.*;

/**
 * Represents the game logic for Othello.
 */
public class Game {
    private final PropertyChangeSupport ob;
    private final Board board;
    private final ColorToken[] players;
    private ArrayList<Position> possibleMove;
    private int[] scores;
    private int playerTurn;
    private int nbPass;
    private GameState gameState;
    private final LinkedList<ArrayList<Position>[]> undoHistory;
    private final LinkedList<ArrayList<Position>[]> redoHistory;


    /**
     * Constructs a new game with the specified board size.
     *
     * @param sizeBoard The size of the game board.
     */
    public Game(int sizeBoard) {
        ob = new PropertyChangeSupport(this);

        this.players = new ColorToken[] {ColorToken.BLACK, ColorToken.WHITE};
        this.playerTurn = 0;

        this.board = new Board(sizeBoard);
        board.searchPossibleMove(players[playerTurn]);
        this.possibleMove = board.getMovePossible();

        this.scores = board.getScore();
        this.nbPass = 0;
        gameState = GameState.RUNNING;

        undoHistory = new LinkedList<>();
        redoHistory = new LinkedList<>();
        saveData();
    }

    /**
     * Adds a property change listener to the game.
     *
     * @param listener The listener to add.
     */
    public void addObserver(PropertyChangeListener listener) {
        ob.addPropertyChangeListener(listener);
    }

    /**
     * Adds a piece to the board at the specified position.
     *
     * @param position  The position to add the piece to.
     * @param surrender A boolean indicating if the move is a surrender.
     */
    public void addPiece(Position position, boolean surrender) {
        if (!redoHistory.isEmpty()) {
            redoHistory.clear();
        }
        ColorToken currentPlayer = players[playerTurn];
        ArrayList<Position> modifyBoard;
        if (!surrender) {
            modifyBoard = board.add(position, currentPlayer);
        }
        else {
            modifyBoard = new ArrayList<>();
        }
        gameState = StateOfGame(surrender);
        scores = board.getScore();
        State state = new State(modifyBoard, possibleMove
                , currentPlayer, players[playerTurn], scores, gameState);
        saveData();

        ob.firePropertyChange("BoardState", null, state);
    }

    //Determines the state of the game based on whether a surrender has occurred or players cannot play.
    private GameState StateOfGame(boolean surrender) {
        if (!surrender) {
            ColorToken nextPlayer;
            do {
                playerTurn = (playerTurn + 1) % 2;
                nextPlayer = players[playerTurn];
                board.searchPossibleMove(nextPlayer);
                possibleMove = board.getMovePossible();
                nbPass++;
            } while (possibleMove.isEmpty() && (nbPass <= 2));
            if (nbPass > 2) {
                return GameState.ENDGAME;
            }
            nbPass = 0;
            return GameState.RUNNING;
        }
        return GameState.SURRENDER;
    }

    /**
     * Initializes the game board and fires a property change event to notify observers.
     */
    public void initialize() {
        ArrayList<Position> initGameB = board.getPositionsColor(players[0]);
        ArrayList<Position> initGameW = board.getPositionsColor(players[1]);
        State state = new State(initGameB, initGameW
                , ColorToken.BLACK, ColorToken.WHITE, possibleMove, scores);
        ob.firePropertyChange("InitState", null, state);
    }

    public GameState getGameState() {
        return gameState;
    }

    /**
     * Saves the current state of the game (positions of black and white tokens) into the undo history.
     */
    private void saveData() {
        ArrayList<Position> blackPosition =
                board.getPositionsColor(ColorToken.BLACK);
        ArrayList<Position> whitePosition =
                board.getPositionsColor(ColorToken.WHITE);

        @SuppressWarnings("unchecked")
        ArrayList<Position>[] saveState = new ArrayList[2];
        saveState[0] = blackPosition;
        saveState[1] = whitePosition;
        undoHistory.add(saveState);
    }

    /**
     * Undoes the last move made in the game.
     * If there are moves to undo, it removes the last saved state from the undo history,
     * adds it to the redo history, updates the player turn, and sets the board to the state before the last move.
     * If there are no moves to undo, it triggers the redoGame method.
     */
    public  void undoGame() {
        if (!undoHistory.isEmpty()){
            var saveState = undoHistory.removeLast();
            redoHistory.add(saveState);
            playerTurn = (Math.abs(playerTurn - 1)) % 2;
            if (!undoHistory.isEmpty()){
                saveState = undoHistory.peekLast();
                setBoard(saveState);
            }
            else {
                redoGame();
            }
        }
    }

    /**
     * Redoes the last undone move in the game.
     * If there are moves to redo, it removes the last saved state from the redo history,
     * adds it back to the undo history, updates the player's turn, and restores the board to the state of the last undone move.
     */
    public  void redoGame() {
        if (!redoHistory.isEmpty()) {
            var saveState = redoHistory.removeLast();
            undoHistory.add(saveState);
            playerTurn = (playerTurn + 1) % 2;
            setBoard(saveState);
        }
    }

    //Sets the game board to the specified state.
    private void setBoard(ArrayList<Position>[] saveState) {
        ColorToken currentPlayer = players[playerTurn];
        ColorToken nextColor = players[(playerTurn + 1) % 2];

        board.setBoard(saveState);
        board.searchPossibleMove(currentPlayer);
        scores = board.getScore();
        possibleMove = board.getMovePossible();
        State state = new State(board.getPositionsColor(ColorToken.BLACK)
                , board.getPositionsColor(ColorToken.WHITE)
                , currentPlayer, nextColor, possibleMove, scores);
        ob.firePropertyChange("SetBoard", null, state);
    }

    Board getBoard() {
        return board;
    }

    public ColorToken getCurrentPlayer() {
        return switch (players[playerTurn]) {
            case BLACK -> ColorToken.BLACK;
            case WHITE -> ColorToken.WHITE;
        };
    }

    /**
     * Retrieves a random valid move on the board.
     *
     * @return A randomly selected valid move.
     */
    public Position getRandomMove() {
        return board.getRandomMove();
    }

    /**
     * Retrieves the position with the maximum number of possible moves.
     *
     * @return The position with the maximum number of possible moves.
     */
    public Position getMaxMove() {
        return board.getMaxMove();
    }
}
