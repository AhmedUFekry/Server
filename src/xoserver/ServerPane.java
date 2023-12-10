package xoserver;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public  class ServerPane extends AnchorPane {

    protected final Text serverapp;
    protected final Button start;
    protected final Label onnum;
    protected final Label avnum;
    protected final Label online;
    protected final Label available;

    public ServerPane() {

        serverapp = new Text();
        start = new Button();
        onnum = new Label();
        avnum = new Label();
        online = new Label();
        available = new Label();

        setMaxHeight(USE_PREF_SIZE);
        setMaxWidth(USE_PREF_SIZE);
        setMinHeight(USE_PREF_SIZE);
        setMinWidth(USE_PREF_SIZE);
        setPrefHeight(280.0);
        setPrefWidth(494.0);

        serverapp.setFontSmoothingType(javafx.scene.text.FontSmoothingType.LCD);
        serverapp.setLayoutX(143.0);
        serverapp.setLayoutY(42.0);
        serverapp.setStrokeType(javafx.scene.shape.StrokeType.OUTSIDE);
        serverapp.setStrokeWidth(0.0);
        serverapp.setText("server application");
        serverapp.setWrappingWidth(231.4700927734375);
        serverapp.setFont(new Font(26.0));

        start.setLayoutX(191.0);
        start.setLayoutY(61.0);
        start.setMnemonicParsing(false);
        start.setPrefHeight(25.0);
        start.setPrefWidth(105.0);
        start.setText("start");

        onnum.setLayoutX(33.0);
        onnum.setLayoutY(114.0);
        onnum.setPrefHeight(33.0);
        onnum.setPrefWidth(152.0);
        onnum.setText("number of online users :");

        avnum.setLayoutX(33.0);
        avnum.setLayoutY(158.0);
        avnum.setPrefHeight(33.0);
        avnum.setPrefWidth(152.0);
        avnum.setText("number of available users :");

        online.setLayoutX(191.0);
        online.setLayoutY(118.0);
        online.setPrefHeight(25.0);
        online.setPrefWidth(36.0);
        online.setText("10");

        available.setLayoutX(195.0);
        available.setLayoutY(166.0);
        available.setPrefHeight(25.0);
        available.setPrefWidth(36.0);
        available.setText("5");

        getChildren().add(serverapp);
        getChildren().add(start);
        getChildren().add(onnum);
        getChildren().add(avnum);
        getChildren().add(online);
        getChildren().add(available);

    }
}
