package app.MainWidget.Settings;

import app.MainWidget.MainWidgetController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SettingsApplication extends Application {
    MainWidgetController mainWidgetController;

    public SettingsApplication(MainWidgetController mainWidgetController) {
        this.mainWidgetController = mainWidgetController;
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("settings-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        SettingsController settingsController = fxmlLoader.getController();
        primaryStage.setTitle("Settings");
        Platform.runLater(()->{
            settingsController.setMainWidgetController(mainWidgetController);
            settingsController.loadGroupes();
        });
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
