package app.Modules;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class Group {
    private int id;
    private String name;

    public static HBox NodeGenrate(String name,String count){
        Label titleLabel = new Label(name);
        Label countLabel = new Label(count);

        // Setting fonts
        titleLabel.setFont(Font.font("System Bold", 12.0));
        countLabel.setFont(Font.font(10.0));

        // Setting text colors
        titleLabel.setTextFill(Color.WHITE);

        // Creating HBox
        HBox hbox = new HBox();
        hbox.setAlignment(Pos.CENTER);
        hbox.setPadding(new Insets(10));
        hbox.getStyleClass().add("choosed-groupe");

        // Setting maximum widths
        titleLabel.setMaxWidth(Double.MAX_VALUE);
        countLabel.setPrefWidth(33.0);

        // Adding children to HBox
        hbox.getChildren().addAll(titleLabel, countLabel);
        return hbox;

    }
}
