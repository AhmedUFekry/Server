/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xoserver;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author Ahmed Fekry
 */
public class ServerController implements Initializable {
    
    @FXML
    private Label label;
    @FXML
    private AnchorPane onusers;
    @FXML
    private Label onnum;
    @FXML
    private Label avusers;
    @FXML
    private Button startBtn;
    @FXML
    private Label onNum;
    @FXML
    private Label avNum;
    
    private void handleButtonAction(ActionEvent event) {
        System.out.println("You clicked me!");
        label.setText("Hello World!");
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void handleStartBtnAction(ActionEvent event) {
    }
    
}
