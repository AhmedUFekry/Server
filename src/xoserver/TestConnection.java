package xoserver;

import java.lang.*;
import java.sql.SQLException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import xoserver.database.DataAccessLayer;

public class TestConnection extends AnchorPane {

    protected final Button button;
    protected final Label label;

    public TestConnection() {

        button = new Button();
        label = new Label();

        setId("AnchorPane");
        setPrefHeight(200);
        setPrefWidth(320);

        button.setLayoutX(126);
        button.setLayoutY(90);
        button.setText("Click Me!");

        label.setLayoutX(126);
        label.setLayoutY(120);
        label.setMinHeight(16);
        label.setMinWidth(69);
        button.addEventHandler(ActionEvent.ACTION, (ActionEvent event)->{
            try {
                DataAccessLayer.testConnection();
            } catch (SQLException ex) {
                Logger.getLogger(TestConnection.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        getChildren().add(button);
        getChildren().add(label);

    }


}
