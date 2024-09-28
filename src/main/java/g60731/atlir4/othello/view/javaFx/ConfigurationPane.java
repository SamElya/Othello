package g60731.atlir4.othello.view.javaFx;

import g60731.atlir4.othello.controller.Controller;
import g60731.atlir4.othello.model.Constants;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 * The ConfigurationPane class represents the configuration window
 * where players can choose the game settings before starting the game.
 */
public class ConfigurationPane extends VBox{
    private MainPane mainPane;
    private Scene scene;
    private final Stage stage;
    private final SizePane sizePane;
    private final AlertBox alert;
    private final Label settingTitle;
    private final Button buttonCPU;
    private final Button buttonPlayer;
    private EventHandler<ActionEvent> cpuHandler;
    private EventHandler<ActionEvent> playerHandler;

    /**
     * Constructs a ConfigurationPane object with the specified stage and controller.
     *
     * @param stage The primary stage for the application.
     * @param controller The controller managing the game logic.
     */
    public ConfigurationPane(Stage stage, Controller controller) {
        this.stage = stage;
        this.alert = new AlertBox("Enter a value between 3 and 15!"
                , "Error Size", "Error Input");

        this.sizePane = new SizePane();
        this.settingTitle = new Label("Setting");
        this.buttonCPU = new Button("Play with CPU");
        this.buttonPlayer = new Button("Play with Player");
        this.getChildren().addAll(settingTitle,sizePane, buttonCPU, buttonPlayer);

        this.settingTitle.setFont(new Font(25));
        this.setAlignment(Pos.CENTER);
        this.setSpacing(10);
        this.setPadding(new Insets(10)); //objet qui fait le padding

        buttonCPU.minWidthProperty().bind(this.widthProperty().subtract(10));
        buttonCPU.maxWidthProperty().bind(this.widthProperty().subtract(10));
        buttonPlayer.minWidthProperty().bind(this.widthProperty().subtract(10));
        buttonPlayer.maxWidthProperty().bind(this.widthProperty().subtract(10));

        initialize(controller);
    }

    /**
     * Initializes the controller and sets up event handlers for buttons.
     *
     * @param controller The controller managing the game logic.
     */
    public void initialize(Controller controller) {

        cpuHandler = new EventHandler<>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (checkSize()) {
                    InfoBoxCPU info = new InfoBoxCPU();
                    if (!info.isCancel()) {
                        controller.setStrategy(info.isSmart());
                        launchGame(controller, sizePane.getTextSize());
                    }

                }
            }
        };

        playerHandler = new EventHandler<>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (checkSize()) {
                    launchGame(controller, sizePane.getTextSize());
                }
            }
        };

        buttonCPU.setOnAction(cpuHandler);
        buttonPlayer.setOnAction(playerHandler);
    }

    //Launches the game with the specified controller and board size.
    private void launchGame(Controller controller, String sSize) {
        final int size = Integer.parseInt(sSize);
        this.mainPane = new MainPane(size);
        controller.initialize(mainPane, size);
        this.scene = new Scene(mainPane);
        this.stage.setScene(scene);
    }

    //Checks if the entered board size is valid.
    private boolean checkSize() {
        String sSize = sizePane.getTextSize();
        boolean isNum = checkOnlyNum(sSize);
        if (sSize.isEmpty() || !isNum) {
            alert.errorShow();
            return false;
        }
        int size = Integer.parseInt(sSize);
        if (size < Constants.minSizeBoard || size > Constants.maxSizeBoard) {
            alert.errorShow();
            return false;
        }
        return true;
    }

    //Checks if the entered board size consists only of numeric characters.
    private boolean checkOnlyNum(String sSize) {
        for (char character : sSize.toCharArray()) {
            if (character < '0' || character > '9') {
                return false;
            }
        }
        return true;
    }
}
