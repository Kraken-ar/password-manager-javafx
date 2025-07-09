package app.MainWidget.Settings;

import app.Auth.SetPassword.SetPasswordApplication;
import app.Classes.DataBaseConnection;
import app.MainWidget.MainWidgetController;
import app.MainWidget.Settings.EditeGroupe.EditeGroupApplication;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.effect.Glow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class SettingsController {
    MainWidgetController mainWidgetController;

    public VBox GroupsContainer;


    public void ChangePassword(){

        SetPasswordApplication setPasswordApplication = new SetPasswordApplication(mainWidgetController);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(GroupsContainer.getScene().getWindow());
        stage.setResizable(false);
        try {
            setPasswordApplication.start(stage);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void clearGroups(){
        GroupsContainer.getChildren().clear();
    }
    public void loadGroupes(){
        DataBaseConnection dataBaseConnection = new DataBaseConnection(DataBaseConnection.dbPath);
        String query = "select * from list;";
        List<Map<String,String>> list = dataBaseConnection.select(query);
        clearGroups();
        if (list != null && !list.isEmpty()){
            for (Map<String,String> map:list){

                Button editBtn = new Button("Edit");
                editBtn.setMnemonicParsing(false);
                editBtn.getStyleClass().add("edite-button");
                editBtn.setEffect(new Glow(0.15));

                editBtn.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        EditeGroupApplication editeGroupApplication = new EditeGroupApplication(map.get("id"),getThis());
                        Stage stage = new Stage();
                        stage.initModality(Modality.APPLICATION_MODAL);
                        stage.initOwner(GroupsContainer.getScene().getWindow());
                        stage.setTitle("Edite Group");
                        stage.setResizable(false);
                        try {
                            editeGroupApplication.start(stage);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                });

                Button deleteBtn = new Button("Delete");
                deleteBtn.setMnemonicParsing(false);
                deleteBtn.getStyleClass().add("delete-button");
                deleteBtn.setEffect(new Glow(0.73));  // Adding Glow effect in Java code

                deleteBtn.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        deleteGroup(map.get("id"));
                    }
                });

                Label titleLabel = new Label(map.get("name"));

                // Setting fonts
                titleLabel.setFont(Font.font("System", FontWeight.BOLD, 12.0));


                // Setting text colors and background colors
                titleLabel.setTextFill(Color.WHITE);
             
                // Creating HBox
                HBox hbox = new HBox();
                hbox.setAlignment(Pos.CENTER_LEFT);
                hbox.setPadding(new Insets(10));
                hbox.setStyle("-fx-padding: 10;");
                hbox.getStyleClass().add("item-hover");
                hbox.setSpacing(10);

                // Ensure titleLabel grows to fill available space
                HBox.setHgrow(titleLabel, Priority.ALWAYS);

                // Adding a spacer to push countLabel to the right
                HBox spacer = new HBox();
                HBox.setHgrow(spacer, Priority.ALWAYS);

                // Adding children to HBox
                hbox.getChildren().addAll(titleLabel, spacer,editBtn,deleteBtn);

                GroupsContainer.getChildren().add(hbox);
            }
        }


    }

    public void deleteGroup(String id){
        ButtonType okButton = new ButtonType("OK");
        ButtonType cancelButton = new ButtonType("Cancel");

        Alert alert = new Alert(Alert.AlertType.WARNING, "Are you sure you want to delete that Group (It won't delete the passwords)?", okButton, cancelButton);
        alert.setHeaderText(null);

        // Apply inline styles to the alert dialog
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.setStyle("-fx-background-color: #2b2b2b;");

        // Set styles for the content label
        dialogPane.lookup(".content.label").setStyle("-fx-text-fill: white;");

        // Set styles for the buttons
        dialogPane.lookupButton(okButton).setStyle("-fx-background-color: #3c3f41; -fx-text-fill: white; -fx-border-color: #555555; -fx-border-width: 1px;");
        dialogPane.lookupButton(cancelButton).setStyle("-fx-background-color: #3c3f41; -fx-text-fill: white; -fx-border-color: #555555; -fx-border-width: 1px;");

        // Set styles for the header panel if it exists
        Region headerPanel = (Region) dialogPane.lookup(".header-panel");
        if (headerPanel != null) {
            headerPanel.setStyle("-fx-background-color: #2b2b2b;");
        }


        // Set styles for the header label if it exists
        Region headerLabel = (Region) dialogPane.lookup(".header-panel .label");
        if (headerLabel != null) {
            headerLabel.setStyle("-fx-text-fill: white;");
        }

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == okButton) {
            DataBaseConnection dataBaseConnection = new DataBaseConnection(DataBaseConnection.dbPath);
            List<Map<String, String>> list = dataBaseConnection.select("select * from list where id = " + id + ";");
            if (!list.isEmpty() && list != null) {
                Map<String, String> map = list.getFirst();
                dataBaseConnection.excute("update password set list_id = null where list_id=" + map.get("id") + ";");
                dataBaseConnection.excute("delete from list where id=" + map.get("id") + ";");



            }
            loadGroupes();
            mainWidgetController.loadGroupes();
            mainWidgetController.loadAllPasswords();
        }
    }
    public void setMainWidgetController(MainWidgetController mainWidgetController) {
        this.mainWidgetController = mainWidgetController;
    }

    public MainWidgetController getMainWidgetController() {
        return mainWidgetController;
    }

    public SettingsController getThis(){
        return this;
    }
}
