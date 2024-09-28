package g60731.atlir4.othello.view.javaFx;

import g60731.atlir4.othello.controller.Controller;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

/**
 * The ButtonPane class represents a pane containing buttons for game actions such as undo, redo, and surrender.
 */
public class ButtonPane extends HBox {
    private final Button undo;
    private final Button redo;
    private final Button surrender;
    private EventHandler<ActionEvent> undoHandler;
    private EventHandler<ActionEvent> redoHandler;
    private EventHandler<ActionEvent> surrenderHandler;

    /**
     * Constructs a ButtonPane object with buttons for undo, redo, and surrender actions.
     */
    public ButtonPane() {
        undo = new Button("Undo");
        redo = new Button("Redo");
        surrender = new Button("Surrender");

        this.getChildren().addAll(undo, redo, surrender);
    }

    /**
     * Initializes the button pane with event handlers for each button.
     *
     * @param controller The game controller to handle button actions.
     *
     * @implNote This method sets up event handlers for each button to perform corresponding actions in the game.
     *
     * @see Controller
     */
    public void init(Controller controller) {

        undoHandler = new EventHandler<>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                controller.undo();
            }
        };

        redoHandler = new EventHandler<>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                controller.redo();
            }
        };

        surrenderHandler = new EventHandler<>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                controller.compute(0, 0, true);

            }
        };

        surrender.setOnAction(surrenderHandler);
        undo.setOnAction(undoHandler);
        redo.setOnAction(redoHandler);

        this.setSpacing(10);
        this.setAlignment(Pos.CENTER);
        this.setPadding(new Insets(0, 0, 10, 0));
    }
}
