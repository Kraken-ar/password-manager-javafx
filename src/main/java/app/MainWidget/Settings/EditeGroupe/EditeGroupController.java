package app.MainWidget.Settings.EditeGroupe;

import app.Classes.Cypher;
import app.Classes.DataBaseConnection;
import app.MainWidget.Settings.SettingsController;
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

public class EditeGroupController {
    SettingsController settingsController;
    String id;

    public TextField titleField;

    public Label errorMsg;





    public void loadDate(){
        DataBaseConnection dataBaseConnection = new DataBaseConnection(DataBaseConnection.dbPath);
        List<Map<String,String>> list = dataBaseConnection.select("select * from list where id = "+id+";");
        if (!list.isEmpty()&&list != null) {
            Map<String, String> map = list.getFirst();
            titleField.setText( map.get("name"));

        }else {
            Stage stage = (Stage) errorMsg.getScene().getWindow();
            stage.close();

        }
    }


    public void edite(){

        String title = titleField.getText();


        if (title.isEmpty()){
            titleField.setBorder(Border.stroke(Paint.valueOf("#FF474C")));
            errorMsg.setText("Title Field Is Empty");
            errorMsg.setTextFill(Paint.valueOf("#FF474C"));
            return;
        }



        DataBaseConnection dataBaseConnection = new DataBaseConnection(DataBaseConnection.dbPath);

        title = title;

        String query = "update list set name = ? where id =  "+id+";";
        Connection connection = dataBaseConnection.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1,title);

            statement.execute();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        titleField.setBorder(Border.EMPTY);



        errorMsg.setText("Group  Edited Successfuly");
        errorMsg.setTextFill(Paint.valueOf("#90EE90"));
        loadDate();

        settingsController.loadGroupes();
        settingsController.getMainWidgetController().loadGroupes();

    }

    public void setSettingsController(SettingsController settingsController) {
        this.settingsController = settingsController;
    }

    public void setId(String id) {
        this.id = id;
    }
}
