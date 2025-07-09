package app.MainWidget.CreatePassword;

import app.Classes.Cypher;
import app.Classes.DataBaseConnection;
import app.MainWidget.MainWidgetController;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Border;
import javafx.scene.paint.Paint;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CreatePasswordController {
    private String id;
    private MainWidgetController mainWidgetController;

    public TextField titleField;
    public TextField userNameField;
    public TextField PasswordField;
    public Label errorMsg;







    public void submit(){
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
        if (id.equals("-1"))
            id = "null";
        title = Cypher.encrypt(title,mainWidgetController.getAuthentication().getPassword());
        username = Cypher.encrypt(username,mainWidgetController.getAuthentication().getPassword());
        password = Cypher.encrypt(password,mainWidgetController.getAuthentication().getPassword());
        String query = "insert into password(name,username,pass,list_id) values ( ? , ? , ? , "+id+")";
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
        System.out.println("query =>"+ query);


        titleField.setText("");
        titleField.setBorder(Border.EMPTY);

        userNameField.setText("");
        userNameField.setBorder(Border.EMPTY);

        PasswordField.setText("");
        PasswordField.setBorder(Border.EMPTY);

        errorMsg.setText("Password Added Successfuly");
        errorMsg.setTextFill(Paint.valueOf("#90EE90"));

        mainWidgetController.loadGroupes();

        if (id.equals("null")){
            mainWidgetController.loadAllPasswords();
        }else{
            mainWidgetController.loadGroupPasswords(id);
        }

    }



    public void setId(String id) {
        this.id = id;
    }

    public void setMainWidgetController(MainWidgetController mainWidgetController) {
        this.mainWidgetController = mainWidgetController;
    }
}
