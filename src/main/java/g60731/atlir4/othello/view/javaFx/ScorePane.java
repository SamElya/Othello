package g60731.atlir4.othello.view.javaFx;

import g60731.atlir4.othello.model.ColorToken;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

/**
 * The ScorePane class represents the graphical interface for displaying player scores.
 * It extends the JavaFX GridPane.
 */
public class ScorePane extends GridPane {
    private final Label score1Label;
    private final Label score2Label;
    private final TextField score1Text;
    private final TextField score2Text;
    private final Background red;
    private final Background white;

    /**
     * Constructs a ScorePane object, initializing labels, text fields, and backgrounds.
     */
    public ScorePane() {
        score1Label = new Label(ConstantsView.namePlayer1);
        score2Label = new Label(ConstantsView.namePlayer2);

        score1Text = new TextField();
        score1Text.setEditable(false);

        score2Text = new TextField();
        score2Text.setEditable(false);

        this.addColumn(0, score1Text, score1Label);
        this.addColumn(1, score2Text, score2Label);
        this.setAlignment(Pos.CENTER);
        red = new Background(new BackgroundFill(Color.rgb(255, 153, 153), CornerRadii.EMPTY, null));
        white = new Background(new BackgroundFill(Color.rgb(255, 255, 255), CornerRadii.EMPTY, null));

        score1Text.setAlignment(Pos.CENTER);
        score2Text.setAlignment(Pos.CENTER);
    }

    /**
     * Updates the score pane with the provided scores and indicates the turn of the current player.
     * @param score An array containing the scores of both players.
     * @param color The color of the current player.
     */
    public void update(int[] score, ColorToken color) {
        score1Text.setText(Integer.toString(score[0]));
        score2Text.setText(Integer.toString(score[1]));
        setTurn(color);
    }

    //Sets the background color of the text fields based on the turn of the current player.
    private void setTurn(ColorToken color) {
        switch (color) {
            case BLACK -> {score1Text.setBackground(red);
                score2Text.setBackground(white);}
            case WHITE -> {score2Text.setBackground(red);
                score1Text.setBackground(white);}
        }
    }
}
