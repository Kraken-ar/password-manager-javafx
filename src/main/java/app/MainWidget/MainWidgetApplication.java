package app.MainWidget;

import app.Classes.Authentication;
import app.Main;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainWidgetApplication extends Application {

    private Authentication authentication;
    public static void main(String[] args) {
        launch(args);
    }

    public MainWidgetApplication(Authentication authentication){
        this.authentication = authentication;
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("view2.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        MainWidgetController mainWidgetController = fxmlLoader.getController();
        primaryStage.setTitle(Main.ProgramName);
        Platform.runLater(()->{
            mainWidgetController.setAuthentication(authentication);

            mainWidgetController.loadGroupes();
            mainWidgetController.loadAllPasswords();
        });
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
