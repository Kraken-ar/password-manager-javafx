module app {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;


    opens app to javafx.fxml;
    opens app.MainWidget to javafx.fxml;
    opens app.Auth to javafx.fxml;
    opens app.Auth.SetPassword to javafx.fxml;
    opens app.MainWidget.CreatePassword to javafx.fxml;
    opens app.MainWidget.EditePassword to javafx.fxml;
    opens app.MainWidget.CreateGroup to javafx.fxml;
    opens app.MainWidget.Settings to javafx.fxml;
    opens app.MainWidget.Settings.EditeGroupe to javafx.fxml;

    exports app;
    exports app.MainWidget;
    exports app.Auth;
    exports app.Auth.SetPassword;
    exports app.MainWidget.CreatePassword;
    exports app.MainWidget.EditePassword;
    exports app.MainWidget.CreateGroup;
    exports app.MainWidget.Settings;
    exports app.MainWidget.Settings.EditeGroupe;

}