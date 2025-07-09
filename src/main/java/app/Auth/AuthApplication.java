package app.Auth;

import app.Auth.SetPassword.SetPasswordApplication;
import app.Classes.Authentication;
import app.Main;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class AuthApplication extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        Authentication authentication = new Authentication();
        if (!authentication.passwordSeted()){
            SetPasswordApplication setPasswordApplication = new SetPasswordApplication(null);
            setPasswordApplication.start(primaryStage);
            return;
        }
        primaryStage.setTitle(Main.ProgramName);

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("login-form.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        primaryStage.setScene(scene);
        primaryStage.show();
    }
//    app/Auth/login-form.fxml
//    app/Auth/AuthApplication.java
}
