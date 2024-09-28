package g60731.atlir4.othello.view.javaFx;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;

import java.util.Optional;

/**
 * The InfoBoxCPU class creates a dialog box
 * for selecting the difficulty level of CPU.
 */
public class InfoBoxCPU {

    private final Alert alert;
    private boolean isSmart;
    private boolean isCancel;

    /**
     * Constructs an InfoBoxCPU object and initializes
     * the dialog box content, title, and buttons.
     */

    public InfoBoxCPU(){
        alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Difficulty");
        alert.setHeaderText("Select the difficulty");
        alert.setContentText("Choose your option.");

        ButtonType buttonDumb = new ButtonType("Dumb");
        ButtonType buttonSmart = new ButtonType("Smart");
        ButtonType buttonCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(buttonDumb, buttonSmart, buttonCancel);
        isCancel = false;
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent()) {
            if (result.get() == buttonDumb){
                isSmart = false;
            } else if (result.get() == buttonSmart) {
                isSmart = true;
            } else {
                isCancel = true;
            }
        }
    }

    /**
     * Checks if the selected difficulty level is smart.
     *
     * @return {@code true} if smart level is selected, {@code false} otherwise.
     */
    public boolean isSmart() {
        return isSmart;
    }

    /**
     * Checks if the cancel button is clicked.
     *
     * @return {@code true} if cancel button is clicked, {@code false} otherwise.
     */
    public boolean isCancel() {
        return isCancel;
    }
}
