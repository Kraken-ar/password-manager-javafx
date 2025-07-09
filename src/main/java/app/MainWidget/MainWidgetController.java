package app.MainWidget;

import app.Auth.SetPassword.SetPasswordApplication;
import app.Classes.Authentication;
import app.Classes.Cypher;
import app.Classes.DataBaseConnection;
import app.MainWidget.CreateGroup.CreateGroupApplication;
import app.MainWidget.CreatePassword.CreatePasswordApplication;
import app.MainWidget.EditePassword.EditePasswordApplication;
import app.MainWidget.Settings.SettingsApplication;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.swing.text.html.ImageView;
import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.util.Optional;


public class MainWidgetController {
    Authentication authentication;
    public VBox GroupsContainer;
    public VBox PasswordsContainer;
    public HBox allPasswordsLable;
    public Label allCount;
    public TextField searchField;
    public Button addPasswordBtn;

    public VBox DataContainer;
    public Label PasswordTitle;
    public Label PasswordUserName;
    public Label Password;
    public Label     PasswordCopy;
    public Button EditePasswordBtn;
    public Button DeletePasswordBtn;


    public void clearGroups(){
        GroupsContainer.getChildren().clear();
    }
    public void clearPasswordsContainer(){
        PasswordsContainer.getChildren().clear();
    }
    public void loadGroupes(){
        DataBaseConnection dataBaseConnection = new DataBaseConnection(DataBaseConnection.dbPath);
        String query = "select * from list;";
        List<Map<String,String>> list = dataBaseConnection.select(query);
        clearGroups();
        if (list != null && !list.isEmpty()){
            for (Map<String,String> map:list){
                Label titleLabel = new Label(map.get("name"));
                int count =  dataBaseConnection.select("select * from password where list_id = "+map.get("id")+";").size();
                Label countLabel = new Label(count+"");
                // Setting fonts
                titleLabel.setFont(Font.font("System", FontWeight.BOLD, 12.0));
                countLabel.setFont(Font.font(10.0));

                // Setting text colors and background colors
                titleLabel.setTextFill(Color.WHITE);
                countLabel.setStyle("-fx-background-color: white; -fx-padding: 5 10;");

                // Creating HBox
                HBox hbox = new HBox();
                hbox.setAlignment(Pos.CENTER_LEFT);
                hbox.setPadding(new Insets(10));
                hbox.setStyle("-fx-padding: 10;");
                hbox.getStyleClass().add("item-hover");

                // Ensure titleLabel grows to fill available space
                HBox.setHgrow(titleLabel, Priority.ALWAYS);

                // Adding a spacer to push countLabel to the right
                HBox spacer = new HBox();
                HBox.setHgrow(spacer, Priority.ALWAYS);

                // Adding children to HBox
                hbox.getChildren().addAll(titleLabel, spacer, countLabel);
                hbox.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        loadGroupPasswords(map.get("id"));
                        for (Node node :GroupsContainer.getChildren()){
                            if (node instanceof HBox){
                                ((HBox) node ).getStyleClass().clear();
                                ((HBox) node ).getStyleClass().add("item-hover");
                                allPasswordsLable.getStyleClass().clear();
                                allPasswordsLable.getStyleClass().add("item-hover");
                            }
                        }
                        hbox.getStyleClass().add("choosed");
                        searchField.setUserData(map.get("id"));
                        addPasswordBtn.setUserData(map.get("id"));

                    }
                });
                GroupsContainer.getChildren().add(hbox);
            }
        }
        allCount.setText(dataBaseConnection.select("select id from password;").size()+"");

    }
    public void previewPassword(String id){
        DataBaseConnection dataBaseConnection = new DataBaseConnection(DataBaseConnection.dbPath);
        List<Map<String,String>> list = dataBaseConnection.select("select * from password where id = "+id+";");
        if (!list.isEmpty()&&list != null){
            Map<String,String> map = list.getFirst();
            DataContainer.setVisible(true);
            PasswordTitle.setText(Cypher.decrypt( map.get("name") ,authentication.getPassword()));
            PasswordUserName.setText(Cypher.decrypt( map.get("username") ,authentication.getPassword()));
            String PassValue = "";
            for (int i = 0;i<map.get("pass").length();i++)
                PassValue+="*";
            Password.setText(PassValue);
            PasswordCopy.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    copyToClipboard(Cypher.decrypt( map.get("pass") ,authentication.getPassword()));
                }
            });

            EditePasswordBtn.setUserData(map.get("id"));
            DeletePasswordBtn.setUserData(map.get("id"));


        }
    }
    public void EditePassword(){
        EditePasswordApplication editePasswordApplication = new EditePasswordApplication(this,(String) EditePasswordBtn.getUserData());
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(DataContainer.getScene().getWindow());
        stage.setResizable(false);
        try {
            editePasswordApplication.start(stage);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void DeletePassword(){
        ButtonType okButton = new ButtonType("OK");
        ButtonType cancelButton = new ButtonType("Cancel");

        Alert alert = new Alert(Alert.AlertType.WARNING, "Are you sure you want to delete that Password?", okButton, cancelButton);
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
            String id = (String) DeletePasswordBtn.getUserData();
            DataBaseConnection dataBaseConnection = new DataBaseConnection(DataBaseConnection.dbPath);
            dataBaseConnection.excute("DELETE FROM password WHERE id = " + id + ";");
            DataContainer.setVisible(false);
            loadGroupes();
            if (searchField.getUserData().equals("-1"))
                loadAllPasswords();
            else
                loadGroupPasswords((String) searchField.getUserData());
        }

    }
    public void loadPasswordCards(List<Map<String,String>> list){
        for (Map<String,String> map:list){
            Label titleLabel = new Label(Cypher.decrypt( map.get("name") ,authentication.getPassword()));
//            Label titleLabel = new Label(map.get("name") );

            Label dateLabel = new Label(map.get("created_at"));

            // Setting fonts
            titleLabel.setFont(Font.font("System", javafx.scene.text.FontWeight.BOLD, 15.0));
            dateLabel.setFont(Font.font("System", 12.0));

            // Setting text colors
            titleLabel.setTextFill(Color.WHITE);
            dateLabel.setTextFill(Color.WHITE);

            // Creating VBox
            VBox vbox = new VBox(5); // Spacing between children is 5
            vbox.setPadding(new Insets(10));
            vbox.setPrefWidth(100);
            vbox.setStyle("-fx-padding: 10;");
            vbox.getStyleClass().add("item-hover");

            // Adding children to VBox
            vbox.getChildren().addAll(titleLabel, dateLabel);

            vbox.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                      previewPassword(map.get("id"));
                    for (Node node:PasswordsContainer.getChildren()){
                        if (node instanceof VBox){
                            ((VBox) node).getStyleClass().clear();
                            ((VBox) node).getStyleClass().add("item-hover");

                        }
                    }
                    vbox.getStyleClass().add("choosed");




                }
            });

            PasswordsContainer.getChildren().add(vbox);
        }

    }
    @FXML
    public void loadAllPasswords(){
        clearPasswordsContainer();
        DataBaseConnection dataBaseConnection = new DataBaseConnection(DataBaseConnection.dbPath);
        String query = "select * from password  ORDER BY created_at DESC;";
        List<Map<String,String>> list = dataBaseConnection.select(query);


        for (Node node :GroupsContainer.getChildren()){
            if (node instanceof HBox){
                ((HBox) node ).getStyleClass().clear();
                ((HBox) node ).getStyleClass().add("item-hover");

            }
        }
        allPasswordsLable.getStyleClass().add("choosed");
        searchField.setUserData("-1");
        addPasswordBtn.setUserData("-1");

        loadPasswordCards(list);

    }

    public void loadAllPasswords(String search){
        clearPasswordsContainer();
        DataBaseConnection dataBaseConnection = new DataBaseConnection(DataBaseConnection.dbPath);
        String query = "SELECT * FROM password WHERE name LIKE '" + Cypher.encrypt(search,authentication.getPassword()) + "%' ORDER BY created_at DESC;";
        List<Map<String,String>> list = dataBaseConnection.select(query);
        System.out.println(query);

        for (Node node :GroupsContainer.getChildren()){
            if (node instanceof HBox){
                ((HBox) node ).getStyleClass().clear();
                ((HBox) node ).getStyleClass().add("item-hover");

            }
        }
        allPasswordsLable.getStyleClass().add("choosed");


     loadPasswordCards(list);

    }
    public void loadGroupPasswords(String id) {
        clearPasswordsContainer();
        DataBaseConnection dataBaseConnection = new DataBaseConnection(DataBaseConnection.dbPath);
        String query = "select * from password where list_id = " + id + "  ORDER BY created_at DESC;;";
        List<Map<String, String>> list = dataBaseConnection.select(query);
        loadPasswordCards(list);
    }
    public void loadGroupPasswords(String id,String search) {
        clearPasswordsContainer();
        DataBaseConnection dataBaseConnection = new DataBaseConnection(DataBaseConnection.dbPath);
        String query = "select * from password where list_id = " + id + " and name like '"+Cypher.encrypt(search,authentication.getPassword())+"%'  ORDER BY created_at DESC;;";
        List<Map<String, String>> list = dataBaseConnection.select(query);
        loadPasswordCards(list);
    }

    public void search(){
        String id = (String) searchField.getUserData();
        String value = searchField.getText();
        System.out.println("id => "+id);

        if (id.equals("-1")){
            loadAllPasswords(value);
        }else{
            loadGroupPasswords(id,value);
        }
    }

    public void createPassword(){

        CreatePasswordApplication createPasswordApplication = new CreatePasswordApplication((String) addPasswordBtn.getUserData(),this);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(DataContainer.getScene().getWindow());

        stage.setResizable(false);
        try {
            createPasswordApplication.start(stage);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public void createGroup(){

        CreateGroupApplication createGroupApplication = new CreateGroupApplication(this);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(DataContainer.getScene().getWindow());
        stage.setResizable(false);
        try {
            createGroupApplication.start(stage);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public void Settings(){

        SettingsApplication settingsApplication = new SettingsApplication(this);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(DataContainer.getScene().getWindow());
//        stage.showAndWait();
        stage.setResizable(false);
        try {
            settingsApplication.start(stage);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void copyToClipboard(String text) {
        StringSelection stringSelection = new StringSelection(text);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, null);
    }

    public void setAuthentication(Authentication authentication) {
        this.authentication = authentication;
    }

    public Authentication getAuthentication() {
        return authentication;
    }
}
