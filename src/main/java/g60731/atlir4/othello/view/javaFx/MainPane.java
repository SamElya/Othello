package g60731.atlir4.othello.view.javaFx;

import g60731.atlir4.othello.controller.Controller;
import g60731.atlir4.othello.model.Position;
import g60731.atlir4.othello.model.State;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * The MainPane class represents the main graphical user interface
 * pane for the Othello game.
 */
public class MainPane extends VBox implements PropertyChangeListener {
    private final BoardPane boardPane;
    private final ScorePane scorePane;
    private final ButtonPane buttonPane;
    private final AlertBox invalidMove;
    private final MenuBarOption menuBarOption;

    /**
     * Constructs a MainPane object with the specified size.
     *
     * @param size The size of the game board.
     */
    public MainPane(int size) {
        menuBarOption = new MenuBarOption();
        boardPane = new BoardPane(size);
        scorePane = new ScorePane();
        buttonPane = new ButtonPane();
        this.getChildren().addAll(menuBarOption, scorePane, boardPane, buttonPane);

        this.setSpacing(10);

        invalidMove = new AlertBox("Invalid Position! " +
                "Select a position from the empty circles."
                , "Warning", "Invalid Move selected");

        menuBarOption.minWidthProperty().bind(this.widthProperty());
        menuBarOption.maxWidthProperty().bind(this.widthProperty());
    }

    /**
     * Initializes the controller and sets up event handlers for user interactions.
     *
     * @param controller The controller managing the game logic.
     */
    public void initialize(Controller controller) {
        this.boardPane.addEventHandler(MouseEvent.MOUSE_CLICKED
                , e -> {
                    Node node = (Node) e.getTarget();
                    int row = GridPane.getRowIndex(node);
                    int column = GridPane.getColumnIndex(node);
                    Position position = new Position(row, column);
                    System.out.println(position);
                    controller.compute(row, column, false);
                }
        );

        this.buttonPane.init(controller);
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

    //Sets the game board and updates the score display.
    private void setBoard(State state) {
        boardPane.setBoard(state);
        scorePane.update(state.getScore(), state.getColorToken());
    }

    //Initializes the game board and updates the score display.
    private void initBoard(State state) {
        boardPane.initView(state);
        scorePane.update(state.getScore(), state.getColorToken());
    }

    //Modifies the game board and updates the score display.
    private void modifyBoard(State state) {
        boardPane.modify(state);
        scorePane.update(state.getScore(), state.getNextColor());
    }

    /**
     * Displays an alert box for an invalid move.
     */
    public void showInvalidMove() {
        invalidMove.errorShow();
    }
}
