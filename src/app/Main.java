package app;

import controller.MainController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application{
    private static final String APP_NAME = "TODO list v1.0";

    @Override
    public void start(Stage stage) throws Exception {
        Parent parent = FXMLLoader.load(getClass().getResource("/view/MainView.fxml"));
        Scene scene = new Scene(parent);
        stage.setScene(scene);
        stage.setTitle(APP_NAME);
        stage.setResizable(false);
        stage.show();
        MainController.dataReader.read();
    }

    @Override
    public void stop() throws Exception {
        MainController.save();
        super.stop();
    }
}
