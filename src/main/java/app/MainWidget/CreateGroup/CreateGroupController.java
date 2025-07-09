package app.MainWidget.CreateGroup;

import app.Classes.DataBaseConnection;
import app.MainWidget.MainWidgetController;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Border;
import javafx.scene.paint.Paint;

public class CreateGroupController {
    private MainWidgetController mainWidgetController;
    public TextField titleField;
    public Label errorMsg;


    public void submit(){
        String title = titleField.getText();

        if (title.isEmpty()){
            titleField.setBorder(Border.stroke(Paint.valueOf("#FF474C")));
            errorMsg.setText("Title Field Is Empty");
            errorMsg.setTextFill(Paint.valueOf("#FF474C"));
            return;
        }





        DataBaseConnection dataBaseConnection = new DataBaseConnection(DataBaseConnection.dbPath);

        String query = "insert into list(name) values ('"+title+"');";
        dataBaseConnection.excute(query);
        titleField.setText("");
        titleField.setBorder(Border.EMPTY);

        errorMsg.setText("Group Added Successfuly");
        errorMsg.setTextFill(Paint.valueOf("#90EE90"));

        mainWidgetController.loadGroupes();


    }
    public void setMainWidgetController(MainWidgetController mainWidgetController) {
        this.mainWidgetController = mainWidgetController;
    }
}
