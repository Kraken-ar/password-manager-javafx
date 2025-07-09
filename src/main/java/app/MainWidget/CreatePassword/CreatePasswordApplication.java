package app.MainWidget.CreatePassword;

import app.Classes.Authentication;
import app.MainWidget.MainWidgetController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class CreatePasswordApplication extends Application {
    String id;
    MainWidgetController mainWidgetController;

    public CreatePasswordApplication(String id, MainWidgetController mainWidgetController){
        this.id = id;
        this.mainWidgetController = mainWidgetController;
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        primaryStage.setScene(scene);
        CreatePasswordController createPasswordController = fxmlLoader.getController();
        primaryStage.setTitle("Create Password");
        Platform.runLater(()->{
            createPasswordController.setId(id);
            createPasswordController.setMainWidgetController(mainWidgetController);
        });

        primaryStage.show();
    }
}
