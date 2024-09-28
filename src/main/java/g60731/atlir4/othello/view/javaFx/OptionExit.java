package g60731.atlir4.othello.view.javaFx;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;

/**
 * The OptionExit class represents the exit option in the menu bar for the Othello application.
 */
public class OptionExit extends Menu {

    private final MenuItem exitLabel;

    /**
     * Constructs an OptionExit object with the exit option.
     */
    public OptionExit(){
        super("Menu");
        exitLabel = new MenuItem("Exit");
        this.getItems().add(exitLabel);
    }
}

