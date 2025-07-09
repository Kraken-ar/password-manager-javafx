package app.MainWidget.Settings.EditeGroupe;

import app.MainWidget.Settings.SettingsController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class EditeGroupApplication extends Application {

    SettingsController settingsController;
    String id;

    public EditeGroupApplication(String id, SettingsController settingsController) {
        this.settingsController = settingsController;
        this.id = id;
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("edite-from.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        EditeGroupController editeGroupController = fxmlLoader.getController();
        Platform.runLater(()->{
            editeGroupController.setSettingsController(settingsController);
            editeGroupController.setId(id);
            editeGroupController.loadDate();
        });
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
