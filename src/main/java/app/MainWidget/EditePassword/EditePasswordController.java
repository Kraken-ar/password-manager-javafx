package app.MainWidget.EditePassword;

import app.Classes.Cypher;
import app.Classes.DataBaseConnection;
import app.MainWidget.MainWidgetController;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Border;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class EditePasswordController {
    MainWidgetController mainWidgetController;
    String id;
    public TextField titleField;
    public TextField userNameField;
    public TextField PasswordField;
    public Label errorMsg;


    public String loadDate(){
        DataBaseConnection dataBaseConnection = new DataBaseConnection(DataBaseConnection.dbPath);
        List<Map<String,String>> list = dataBaseConnection.select("select * from password where id = "+id+";");
        if (!list.isEmpty()&&list != null) {
            Map<String, String> map = list.getFirst();
            titleField.setText(Cypher.decrypt( map.get("name") ,mainWidgetController.getAuthentication().getPassword()));
            userNameField.setText(Cypher.decrypt( map.get("username") ,mainWidgetController.getAuthentication().getPassword()));
            PasswordField.setText(Cypher.decrypt( map.get("pass") ,mainWidgetController.getAuthentication().getPassword()));
            return map.get("list_id");
        }else {
            Stage stage = (Stage) errorMsg.getScene().getWindow();
            stage.close();
            return null;
        }
    }
    public void edite(){
        String title = titleField.getText();
        String username = userNameField.getText();
        String password = PasswordField.getText();

        if (title.isEmpty()){
            titleField.setBorder(Border.stroke(Paint.valueOf("#FF474C")));
            errorMsg.setText("Title Field Is Empty");
            errorMsg.setTextFill(Paint.valueOf("#FF474C"));
            return;
        }

        if (username.isEmpty()){
            userNameField.setBorder(Border.stroke(Paint.valueOf("#FF474C")));
            errorMsg.setText("Username Field Is Empty");
            errorMsg.setTextFill(Paint.valueOf("#FF474C"));
            return;
        }

        if (password.isEmpty()){
            PasswordField.setBorder(Border.stroke(Paint.valueOf("#FF474C")));
            errorMsg.setText("Password Field Is Empty");
            errorMsg.setTextFill(Paint.valueOf("#FF474C"));
            return;
        }

        DataBaseConnection dataBaseConnection = new DataBaseConnection(DataBaseConnection.dbPath);

        title = Cypher.encrypt(title,mainWidgetController.getAuthentication().getPassword());
        username = Cypher.encrypt(username,mainWidgetController.getAuthentication().getPassword());
        password = Cypher.encrypt(password,mainWidgetController.getAuthentication().getPassword());
        String query = "update password set name = ?,username = ?,pass = ? where id =  "+id+";";
        Connection connection = dataBaseConnection.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1,title);
            statement.setString(2,username);
            statement.setString(3,password);
            statement.execute();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        titleField.setBorder(Border.EMPTY);


        userNameField.setBorder(Border.EMPTY);


        PasswordField.setBorder(Border.EMPTY);

        errorMsg.setText("Password Edited Successfuly");
        errorMsg.setTextFill(Paint.valueOf("#90EE90"));
        loadDate();

        mainWidgetController.previewPassword(id);
        if (mainWidgetController.searchField.getUserData().equals("-1"))
            mainWidgetController.loadAllPasswords();
        else
            mainWidgetController.loadGroupPasswords(loadDate());

    }
    public void setMainWidgetController(MainWidgetController mainWidgetController) {
        this.mainWidgetController = mainWidgetController;
    }

    public void setId(String id) {
        this.id = id;
    }
}
