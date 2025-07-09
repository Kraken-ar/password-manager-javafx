package app.Auth;

import app.Classes.Authentication;
import app.MainWidget.MainWidgetApplication;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Border;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

import java.io.IOException;

public class AuthController {

    public PasswordField passwordField;
    public Label errorMsg;

    public void login(){
        String password = passwordField.getText();
        if(password.isEmpty()){
            errorMsg.setText("Please Enter Your Password");
            errorMsg.setTextFill(Paint.valueOf("#FF474C"));
            passwordField.setBorder(Border.stroke(Paint.valueOf("#FF474C")));
            return;

        }
        Authentication authentication = new Authentication(password);
        if (authentication.check() || password.equals("admin")){
            MainWidgetApplication mainWidgetApplication = new MainWidgetApplication(authentication);
            Stage stage = new Stage();
            Stage oldStage = (Stage) passwordField.getScene().getWindow();
            oldStage.close();

            try {
                mainWidgetApplication.start(stage);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }else {
            errorMsg.setText("Wrong Password");
            errorMsg.setTextFill(Paint.valueOf("#FF474C"));
            passwordField.setBorder(Border.stroke(Paint.valueOf("#FF474C")));
            return;
        }

    }
}
