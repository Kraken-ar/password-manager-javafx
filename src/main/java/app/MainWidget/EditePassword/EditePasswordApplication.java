package app.MainWidget.EditePassword;

import app.Classes.Authentication;
import app.MainWidget.MainWidgetController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class EditePasswordApplication extends Application {
    MainWidgetController mainWidgetController;
    String id;

    public EditePasswordApplication(MainWidgetController mainWidgetController, String id) {
        this.mainWidgetController = mainWidgetController;
        this.id = id;
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("edite-form.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        EditePasswordController editePasswordController = fxmlLoader.getController();
        primaryStage.setTitle("Edite Group");

        Platform.runLater(()->{
            editePasswordController.setId(id);
            editePasswordController.setMainWidgetController(mainWidgetController);
            editePasswordController.loadDate();
        });
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
