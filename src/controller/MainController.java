package controller;

import app.DataReader;
import app.Task;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.net.URL;
import java.time.LocalDate;
import java.util.Random;
import java.util.ResourceBundle;

public class MainController implements Initializable{

    @FXML
    private AddViewController addViewController;

    @FXML
    private LastViewController lastViewController;

    @FXML
    private TableView<Task> tableView;

    @FXML
    private MenuItem addTaskMenuItem;

    @FXML
    private MenuItem editTaskMenuItem;

    @FXML
    private MenuItem deleteTaskMenuItem;

    @FXML
    private BorderPane lastView;

    @FXML
    private VBox addView;

    public static DataReader dataReader;
    private Button add;
    private Button addCancel;
    private TextField addTitle;
    private DatePicker addDeadline;
    private TextArea addNote;
    private TextArea chooseNote;
    private Label chooseTitle;
    private Label chooseDeadline;
    private int index;
    private boolean edit;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        dataReader = new DataReader();
        lastView.setVisible(true);
        configMenu();
        configTable();
        configAddViewController();
        configLastViewController();
        dataReader.read();
        loadIndex(0);
    }

    // configure table view; set mouse click method at the table row
    private void configTable() {
        TableColumn<Task, String> titleColumn = new TableColumn<>("Title");
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        titleColumn.setSortable(false);

        TableColumn<Task, String> dateColumn = new TableColumn<>("Deadline");
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        dateColumn.setSortable(false);

        tableView.getColumns().add(titleColumn);
        tableView.getColumns().add(dateColumn);

        tableView.setItems(dataReader.getTasks());

        tableView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(!tableView.getItems().isEmpty()) {
                    index = tableView.getSelectionModel().getFocusedIndex();
                    loadIndex(index);
                }
            }
        });

        tableView.setRowFactory(row -> new TableRow<Task>(){
            @Override
            protected void updateItem(Task item, boolean empty) {
                super.updateItem(item, empty);
                if(!empty) {
                    if(threeOrLessDaysToDeadline(item.getDate())) {
                        for(int i=0; i<getChildren().size();i++){
                            ((Labeled) getChildren().get(i)).setTextFill(Color.RED);
                        }
                    } else {
                        for(int i=0; i<getChildren().size();i++){
                            ((Labeled) getChildren().get(i)).setTextFill(Color.BLACK);
                        }
                    }
                }
            }
        });
    }

    // set mouse click method at the menu item
    private void configMenu() {
        addTaskMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                edit = false;
                lastView.setVisible(false);
                addView.setVisible(true);
                tableView.setDisable(true);
                addTitle.setText("");
                addDeadline.setValue(LocalDate.now());
                addNote.setText("");
                lockMenu(true);
            }
        });

        editTaskMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(!tableView.getItems().isEmpty()) {
                    edit = true;
                    lastView.setVisible(false);
                    addView.setVisible(true);
                    tableView.setDisable(true);
                    editIndex(index);
                    addCancel.setVisible(false);
                    add.setText("Save");
                    lockMenu(true);
                }
            }
        });

        deleteTaskMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(!tableView.getItems().isEmpty()) {
                    dataReader.remove(tableView.getSelectionModel().getFocusedIndex());
                    chooseTitle.setText("");
                    chooseDeadline.setText("");
                    chooseNote.setText("");
                    chooseDeadline.setStyle("-fx-background-color: #dbdbdb; -fx-text-fill: #000000");
                }
            }
        });
    }

    // load fields from AddViewController
    private void configAddViewController() {
        addTitle = addViewController.getAddTitleTextField();
        add = addViewController.getAddButton();
        addDeadline = addViewController.getAddDeadline();
        addNote = addViewController.getAddNotesTextArea();
        addCancel = addViewController.getAddCancelButton();

        add.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(!addTitle.getText().isEmpty() && !addDeadline.getEditor().getText().isEmpty()) {
                    Task task = new Task(addTitle.getText(), addDeadline.getEditor().getText(), addNote.getText());
                    if(edit) {
                        dataReader.remove(index);
                        edit = false;
                    }
                    dataReader.add(task);
                    lastView.setVisible(true);
                    addView.setVisible(false);
                    tableView.setDisable(false);
                    addCancel.setVisible(true);
                    lockMenu(false);
                    add.setText("Add new task");
                    loadIndex(index=0);
                }
            }
        });

        addCancel.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                lastView.setVisible(true);
                addView.setVisible(false);
                tableView.setDisable(false);
                lockMenu(false);
            }
        });
    }

    // load fields from LastViewController
    private void configLastViewController() {
        chooseTitle = lastViewController.getLastTitleLabel();
        chooseDeadline = lastViewController.getLastDealineLabel();
        chooseNote = lastViewController.getLastNotesTextArea();

    }

    // load task view from selected table row
    private void loadIndex(int id) {
        if(!tableView.getItems().isEmpty()) {
            Task task = tableView.getItems().get(id);
            chooseTitle.setText(task.getTitle());
            chooseDeadline.setText(task.getDate());
            chooseNote.setText(task.getNote());
            if(threeOrLessDaysToDeadline(task.getDate())) {
                chooseDeadline.setStyle("-fx-background-color: #ff0000; -fx-text-fill: #ffffff");
            } else {
                chooseDeadline.setStyle("-fx-background-color: #dbdbdb; -fx-text-fill: #000000");
            }
        }
    }

    // load fields to edit from selected table row
    private void editIndex(int id) {
        Task task = tableView.getItems().get(id);
        String[] numbers = task.getDate().split("\\.");
        String date = numbers[2] + "-" + numbers[1] + "-" + numbers[0];
        addTitle.setText(task.getTitle());
        addDeadline.setValue(LocalDate.parse(date));
        addNote.setText(task.getNote());
    }

    // lock/unlock menu
    private void lockMenu(boolean state) {
        addTaskMenuItem.setDisable(state);
        editTaskMenuItem.setDisable(state);
        deleteTaskMenuItem.setDisable(state);
    }

    // return true if three or less days to 'deadline'
    private boolean threeOrLessDaysToDeadline(String taskData) {
        String[] task = taskData.split("\\.");
        LocalDate taskDate = LocalDate.parse(task[2] + "-" + task[1] + "-" + task[0]);
        LocalDate nowDate = LocalDate.now();
        nowDate = nowDate.plusDays(3);
        if(nowDate.isEqual(taskDate)) return true;
        return taskDate.isBefore(nowDate) ? true : false;
    }

    // save tasks list to file
    public static void save() {
        dataReader.write();
    }

}
