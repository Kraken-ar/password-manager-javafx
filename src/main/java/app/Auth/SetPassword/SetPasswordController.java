package app.Auth.SetPassword;

import app.Classes.Authentication;
import app.Classes.Cypher;
import app.Classes.DataBaseConnection;
import app.MainWidget.MainWidgetApplication;
import app.MainWidget.MainWidgetController;
import app.MainWidget.Settings.SettingsController;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

public class SetPasswordController {

    private Authentication authentication;
    private MainWidgetController mainWidgetController;


    public PasswordField passwordField;
    public Label errorMsg;
    public Label minCheck;
    public Label maxCheck;
    public Label loweCheck;
    public Label upperCheck;
    public Label numbersCheck;
    public Label simpoleCheck;




    public void validation(){
        String password = passwordField.getText();

        if (password.length() >= 8){
            minCheck.setTextFill(Paint.valueOf("#18d118"));
        }else{
            minCheck.setTextFill(Paint.valueOf("#9e9e9e"));
        }

        if (password.length() >= 8 && password.length() <= 16){
            maxCheck.setTextFill(Paint.valueOf("#18d118"));
        }else{
            maxCheck.setTextFill(Paint.valueOf("#9e9e9e"));
        }

        if (hasLowerCase(password)){
            loweCheck.setTextFill(Paint.valueOf("#18d118"));
        }else{
            loweCheck.setTextFill(Paint.valueOf("#9e9e9e"));
        }

        if (hasUpperCase(password)){
            upperCheck.setTextFill(Paint.valueOf("#18d118"));
        }else{
            upperCheck.setTextFill(Paint.valueOf("#9e9e9e"));
        }

        if (hasNumber(password)){
            numbersCheck.setTextFill(Paint.valueOf("#18d118"));
        }else{
            numbersCheck.setTextFill(Paint.valueOf("#9e9e9e"));
        }

        if (hasSpecialCharacters(password)){
            simpoleCheck.setTextFill(Paint.valueOf("#18d118"));
        }else{
            simpoleCheck.setTextFill(Paint.valueOf("#9e9e9e"));
        }
    }

    public void sava(){
        String password = passwordField.getText();



        if (password.length() < 8 || password.length() > 16){
            errorMsg.setText("Password Must be Lower That 16 Characters and More Than 8 Characters");
            errorMsg.setTextFill(Paint.valueOf("#FF474C"));

            return;
        }
        if (!hasLowerCase(password)){
            errorMsg.setText("Password Must Contain Lower Case Letters");
            errorMsg.setTextFill(Paint.valueOf("#FF474C"));

            return;

        }

        if (!hasUpperCase(password)){
            errorMsg.setText("Password Must Contain Upper Case Letters");
            errorMsg.setTextFill(Paint.valueOf("#FF474C"));

            return;

        }

        if (!hasNumber(password)){
            errorMsg.setText("Password Must Contain Numeric Characters");
            errorMsg.setTextFill(Paint.valueOf("#FF474C"));

            return;

        }

        if (!hasSpecialCharacters(password)){
            errorMsg.setText("Password Must Contain Spacial Characters [*,_,-,$,/,\\,!]");
            errorMsg.setTextFill(Paint.valueOf("#FF474C"));

            return;

        }

        authentication  = new Authentication(password);
        authentication.setPass(password);
        updateData(password);
        if (mainWidgetController != null){
            mainWidgetController.setAuthentication(authentication);
            mainWidgetController.loadAllPasswords();


            errorMsg.setText("Password Setted successfully ");
            errorMsg.setTextFill(Paint.valueOf("#90EE90"));
            passwordField.setText("");
            minCheck.setTextFill(Paint.valueOf("#9e9e9e"));
            maxCheck.setTextFill(Paint.valueOf("#9e9e9e"));
            upperCheck.setTextFill(Paint.valueOf("#9e9e9e"));
            loweCheck.setTextFill(Paint.valueOf("#9e9e9e"));
            simpoleCheck.setTextFill(Paint.valueOf("#9e9e9e"));
            numbersCheck.setTextFill(Paint.valueOf("#9e9e9e"));


        }else{
            DataBaseConnection dataBaseConnection = new DataBaseConnection(DataBaseConnection.dbPath);
            dataBaseConnection.excute("delete from password;");
            MainWidgetApplication mainWidgetApplication = new MainWidgetApplication(authentication);

            Stage oldStage = (Stage) passwordField.getScene().getWindow();
            oldStage.close();
            Stage stage = new Stage();
            try {
                mainWidgetApplication.start(stage);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }





    }

    public void updateData(String newPassword){
        DataBaseConnection dataBaseConnection = new DataBaseConnection(DataBaseConnection.dbPath);
        List<Map<String,String>> list = dataBaseConnection.select("select * from password;");
        for (Map<String,String> map:list){
            String newPass = Cypher.encrypt(Cypher.decrypt(map.get("pass"),authentication.getPassword()),newPassword);
            String newTitle = Cypher.encrypt(Cypher.decrypt(map.get("name"),authentication.getPassword()),newPassword);
            String newUsername = Cypher.encrypt(Cypher.decrypt(map.get("username"),authentication.getPassword()),newPassword);
            String query = "update password set name = ? , username = ? , pass = ? where id = "+map.get("id")+";";
            Connection connection = dataBaseConnection.getConnection();
            try {
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setString(1,newTitle);
                statement.setString(2,newUsername);
                statement.setString(3,newPass);

                statement.execute();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }


    public static boolean hasLowerCase(String input) {
        if (input == null || input.isEmpty()) {
            return false;
        }

        for (char c : input.toCharArray()) {
            if (Character.isLowerCase(c)) {
                return true;
            }
        }
        return false;
    }

    public static boolean hasUpperCase(String input) {
        if (input == null || input.isEmpty()) {
            return false;
        }

        for (char c : input.toCharArray()) {
            if (Character.isUpperCase(c)) {
                return true;
            }
        }
        return false;
    }
    public static boolean hasNumber(String input) {
        if (input == null || input.isEmpty()) {
            return false;
        }

        for (char c : input.toCharArray()) {
            if (Character.isDigit(c)) {
                return true;
            }
        }
        return false;
    }

    public static boolean hasSpecialCharacters(String input) {
        if (input == null || input.isEmpty()) {
            return false;
        }

        for (char c : input.toCharArray()) {
            if (!Character.isLetterOrDigit(c)) {
                return true;
            }
        }
        return false;
    }

    public void setMainWidgetController(MainWidgetController mainWidgetController) {
        this.mainWidgetController = mainWidgetController;
    }


}
