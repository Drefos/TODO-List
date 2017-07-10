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
    private Label lastDealineLabel;

    @FXML
    private TextArea lastNotesTextArea;

    public Label getLastTitleLabel() {
        return lastTitleLabel;
    }
    public void setLastTitleLabel(Label lastTitleLabel) {
        this.lastTitleLabel = lastTitleLabel;
    }

    public Label getLastDealineLabel() {
        return lastDealineLabel;
    }
    public void setLastDealineLabel(Label lastDealineLabel) {
        this.lastDealineLabel = lastDealineLabel;
    }

    public TextArea getLastNotesTextArea() {
        return lastNotesTextArea;
    }
    public void setLastNotesTextArea(TextArea lastNotesTextArea) {
        this.lastNotesTextArea = lastNotesTextArea;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
}
