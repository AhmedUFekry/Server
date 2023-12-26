/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xoserver;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author Ahmed Fekry
 */
public class ServerController implements Initializable {
    private boolean isServerRunning = false;
    private  ServerHandler serverHandler;
    @FXML
    private Label label;
    @FXML
    private AnchorPane onusers;
    @FXML
    private Button startBtn;
    @FXML
    private PieChart pieChart;
    
 
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        ObservableList<PieChart.Data> allPlayerChart = FXCollections.observableArrayList(
                            new PieChart.Data("Online", 10),
                            new PieChart.Data("Offline", 12),
                            new PieChart.Data("In Game", 4));
        allPlayerChart.forEach(data -> data.nameProperty().bind(Bindings.concat(data.getName()," user: ", data.pieValueProperty())));
        pieChart.getData().addAll(allPlayerChart);
    }    

    @FXML
    private void handleStartBtnAction(ActionEvent event) {
        if(isServerRunning){
            stopServer();
        }else{
            startServer();
        }
    }

    private void stopServer() {
        if(serverHandler != null){
            serverHandler.stopServer();
        }
        isServerRunning = false;
        startBtn.setText("Start Server");
    }

    private void startServer() {
        serverHandler = new ServerHandler();
        // Add additional logic as needed
        isServerRunning = true;
        startBtn.setText("Stop Server");
    }
    
}
