package g60731.atlir4.othello;

import g60731.atlir4.othello.controller.Controller;
import g60731.atlir4.othello.view.javaFx.ConfigurationPane;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

//observer = listener (vue)
//observable = support (model)
public class OthelloLauncherJavaFX extends Application {
    @Override
    public void start(Stage stage) {
        stage.setTitle("Othello");
        Controller controller = new Controller();
        ConfigurationPane configuration = new ConfigurationPane(stage, controller);
        Scene scene = new Scene(configuration);
        stage.setScene(scene);
        stage.show();
        configuration.requestFocus();
        stage.setResizable(false);
    }

    public static void exec(String[] args){

        Application.launch(args);
    }
}
