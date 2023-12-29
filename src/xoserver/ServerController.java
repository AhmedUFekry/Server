package xoserver;

import Database.DataAccessLayer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.util.Duration;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class ServerController implements Initializable {
    private boolean isServerRunning = false;
    private ServerHandler serverHandler;
    private Timeline updateChartTimeline;

    @FXML
    private Button startBtn;

    @FXML
    private PieChart pieChart;

    // Map to store references to original PieChart.Data objects
    private final Map<String, PieChart.Data> chartDataMap = new HashMap<>();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Set up the initial data for the PieChart
        ObservableList<PieChart.Data> initialData = FXCollections.observableArrayList(
                new PieChart.Data("Online", 0),
                new PieChart.Data("Offline", 0),
                new PieChart.Data("In Game", 0)
        );

        // Bind the nameProperty for each PieChart.Data
        initialData.forEach(data -> {
            chartDataMap.put(data.getName(), data);
            data.nameProperty().bind(Bindings.concat(data.getName(), " user: ", data.pieValueProperty()));
        });

        pieChart.setData(initialData);

        // Create a Timeline to periodically update the PieChart
        updateChartTimeline = new Timeline(
                new KeyFrame(Duration.seconds(0.1), event -> updateChartData())
        );
        updateChartTimeline.setCycleCount(Timeline.INDEFINITE);
    }

    @FXML
    private void handleStartBtnAction(ActionEvent event) {
        if (isServerRunning) {
            stopServer();
        } else {
            startServer();
        }
    }

    private void stopServer() {
        if (serverHandler != null) {
            serverHandler.stopServer();
        }
        isServerRunning = false;
        startBtn.setText("Start Server");

        // Stop the timeline when the server is stopped
        updateChartTimeline.stop();
    }

    private void startServer() {
        serverHandler = new ServerHandler();
        // Add additional logic as needed
        isServerRunning = true;
        startBtn.setText("Stop Server");

        // Start the timeline when the server is started
        updateChartTimeline.play();
    }

    private void updateChartData() {
        // Get live data from DataAccessLayer
        int onlineUsers = DataAccessLayer.onlineUsers().size();
        int offlineUsers = DataAccessLayer.offlineUsers().size();
        int inGameUsers = DataAccessLayer.inGameUsers().size();

        // Update the existing PieChart.Data objects
        chartDataMap.get("Online").setPieValue(onlineUsers);
        chartDataMap.get("Offline").setPieValue(offlineUsers);
        chartDataMap.get("In Game").setPieValue(inGameUsers);
    }
}

