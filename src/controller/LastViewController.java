package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

import java.net.URL;
import java.util.ResourceBundle;

public class LastViewController implements Initializable{

    @FXML
    private Label lastTitleLabel;

    @FXML
    private Label lastDeadlineLabel;

    @FXML
    private TextArea lastNotesTextArea;

    public Label getLastTitleLabel() {
        return lastTitleLabel;
    }

    public Label getLastDeadlineLabel() {
        return lastDeadlineLabel;
    }

    public TextArea getLastNotesTextArea() {
        return lastNotesTextArea;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
