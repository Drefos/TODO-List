package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class AddViewController implements Initializable{

    @FXML
    private TextField addTitleTextField;

    @FXML
    private TextArea addNotesTextArea;

    @FXML
    private Button addButton;

    @FXML
    private Button addCancelButton;

    @FXML
    private DatePicker addDeadline;

    public DatePicker getAddDeadline() {
        return addDeadline;
    }

    public Button getAddCancelButton() {
        return addCancelButton;
    }

    public Button getAddButton() {
        return addButton;
    }

    public TextField getAddTitleTextField() {
        return addTitleTextField;
    }

    public TextArea getAddNotesTextArea() {
        return addNotesTextArea;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
