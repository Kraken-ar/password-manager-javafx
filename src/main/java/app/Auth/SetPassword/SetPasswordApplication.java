package app.Auth.SetPassword;

import app.MainWidget.MainWidgetController;
import app.MainWidget.Settings.SettingsController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SetPasswordApplication extends Application {
    MainWidgetController mainWidgetController;


    public SetPasswordApplication(MainWidgetController mainWidgetController) {
        this.mainWidgetController = mainWidgetController;

    }

    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("setPassword.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        primaryStage.setScene(scene);
        SetPasswordController passwordController = fxmlLoader.getController();
        primaryStage.setTitle("Set Password");

        Platform.runLater(()->{
            passwordController.setMainWidgetController(mainWidgetController);

        });
        primaryStage.show();
    }
}
