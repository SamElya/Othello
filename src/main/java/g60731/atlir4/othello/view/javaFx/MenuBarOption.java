package g60731.atlir4.othello.view.javaFx;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.MenuBar;

/**
 * The MenuBarOption class represents
 * the menu bar options for the Othello application.
 */
public class MenuBarOption extends MenuBar {

    private final OptionExit optionExit;
    private final EventHandler<ActionEvent> exitHandler;

    /**
     * Constructs a MenuBarOption object with exit option.
     */
    public MenuBarOption() {

        optionExit = new OptionExit();
        this.getMenus().add(optionExit);

        exitHandler = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

                Platform.exit();
            }
        };

        optionExit.setOnAction(exitHandler);
    }
}
