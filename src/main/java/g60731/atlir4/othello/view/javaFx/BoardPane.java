package g60731.atlir4.othello.view.javaFx;

import g60731.atlir4.othello.model.Position;
import g60731.atlir4.othello.model.State;
import javafx.application.Platform;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

/**
 * The BoardPane class represents the graphical user interface component
 * responsible for displaying the game board.
 */
public class BoardPane extends GridPane {
    private final int size;
    private final Circle[][] circles;
    private ArrayList<Position> previousPossibleMove;

    /**
     * Constructs a BoardPane object with the specified size.
     *
     * @param size The size of the game board.
     */
    public BoardPane(int size) {
        this.size = size;
        this.circles = new Circle[size][size];
        this.previousPossibleMove = new ArrayList<>();

        createBoard();

        this.setGridLinesVisible(true);
        this.setAlignment(Pos.CENTER);
        this.setPadding(new Insets(0,10,0,10));
    }

    /* Creates the initial game board by adding rectangles and circles to represent each cell.
     * Each cell is represented by a rectangle and a circle.
     * The circles are added on top of the rectangles to allow interaction.
     * The size of the grid is determined by the 'size' parameter of the class.
     */
    private void createBoard() {
        for (int row = 0; row < size; row++) {
            RowConstraints rowGrid = new RowConstraints (ConstantsView.sizeGrid);
            rowGrid.setValignment(VPos.CENTER);
            this.getRowConstraints().add(rowGrid);
            ColumnConstraints colGrid = new ColumnConstraints(ConstantsView.sizeGrid);
            colGrid.setHalignment(HPos.CENTER);
            this.getColumnConstraints().add(colGrid);
            for (int col = 0; col < size; col++) {
                Rectangle rectangle = new Rectangle(ConstantsView.sizeGrid
                        , ConstantsView.sizeGrid, ConstantsView.colorGrid);
                Circle circle = new Circle(ConstantsView.radiusCircle, ConstantsView.colorGrid);
                circles[row][col] = circle;
                this.add(rectangle, row, col);
                this.add(circle, row, col);
            }
        }
    }

    /**
     * Initializes the game board view based on the given game state.
     *
     * @param state The initial state of the game.
     */
    void initView(State state) {

        for (Position position : state.getBlackTokens()) {
            circles[position.getY()][position.getX()]
                    .setFill(ConstantsView.colorPlayer1);
        }
        for (Position position : state.getWhiteTokens()) {
            circles[position.getY()][position.getX()]
                    .setFill(ConstantsView.colorPlayer2);
        }
        for (Position position : state.getPossibleMove()) {
            switch (state.getColorToken()) {
                case BLACK -> circles[position.getY()][position.getX()]
                        .setStroke(ConstantsView.colorPlayer1);
                case WHITE -> circles[position.getY()][position.getX()]
                        .setStroke(ConstantsView.colorPlayer2);
            }
        }
        previousPossibleMove = state.getPossibleMove();
    }

    /**
     * Modifies the game board based on the given game state.
     *
     * @param state The modified state of the game.
     */
    void modify(State state) {
        switch (state.getGameState()) {
            case RUNNING:
                System.out.println("Running");
                modifyState(state);
                break;
            case ENDGAME:
                modifyState(state);
                System.out.println("End");
            case SURRENDER:
                System.out.println("Surr");
                displayEndGame(state);
        }
    }

    //Displays the end game dialog with information about the winner and exits the application.
    private void displayEndGame(State state) {
        InfoBoxWinner info = new InfoBoxWinner(state.getScore());
        Platform.exit();
    }

    //Modifies the state of the game by updating the board based on the current game state.
    private void modifyState(State state) {
        for (Position position : previousPossibleMove) {
            circles[position.getY()][position.getX()]
                    .setStroke(ConstantsView.colorGrid);
        }

        for (Position position : state.getModifyBoard()) {
            switch (state.getColorToken()) {
                case BLACK -> circles[position.getY()][position.getX()]
                        .setFill(ConstantsView.colorPlayer1);
                case WHITE -> circles[position.getY()][position.getX()]
                        .setFill(ConstantsView.colorPlayer2);
            }
        }
        for (Position position : state.getPossibleMove()) {
            switch (state.getNextColor()) {
                case BLACK -> circles[position.getY()][position.getX()]
                        .setStroke(ConstantsView.colorPlayer1);
                case WHITE -> circles[position.getY()][position.getX()]
                        .setStroke(ConstantsView.colorPlayer2);
            }        }
        previousPossibleMove = state.getPossibleMove();
    }

    /**
     * Sets the game board based on the given game state.
     *
     * @param state The state of the game.
     */
    void setBoard(State state) {
        ArrayList<Position> blackTokens = state.getBlackTokens();
        ArrayList<Position> whiteTokens = state.getWhiteTokens();
        ArrayList<Position> possibleMove = state.getPossibleMove();
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                if (blackTokens.contains(new Position(col, row))) {
                    circles[row][col]
                            .setFill(ConstantsView.colorPlayer1);
                    circles[row][col]
                            .setStroke(ConstantsView.colorGrid);
                }
                else if (whiteTokens.contains(new Position(col, row))) {
                    circles[row][col]
                            .setFill(ConstantsView.colorPlayer2);
                    circles[row][col]
                            .setStroke(ConstantsView.colorGrid);
                }
                else if (possibleMove.contains(new Position(col, row))) {
                    circles[row][col].setFill(ConstantsView.colorGrid);
                    switch (state.getColorToken()) {
                        case BLACK -> circles[row][col]
                                .setStroke(ConstantsView.colorPlayer1);
                        case WHITE -> circles[row][col]
                                .setStroke(ConstantsView.colorPlayer2);
                    }
                }
                else {
                    circles[row][col].setFill(ConstantsView.colorGrid);
                    circles[row][col].setStroke(ConstantsView.colorGrid);
                }
            }
        }
        previousPossibleMove = state.getPossibleMove();
    }
}
