package app.MainWidget.CreateGroup;

import app.MainWidget.MainWidgetController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class CreateGroupApplication extends Application {


    MainWidgetController mainWidgetController;
    public CreateGroupApplication(MainWidgetController mainWidgetController){

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
        primaryStage.setTitle("Create Group");
        CreateGroupController createGroupController = fxmlLoader.getController();
        Platform.runLater(()->{
            createGroupController.setMainWidgetController(mainWidgetController);
        });
        primaryStage.show();
    }
}
