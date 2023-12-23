package xoserver;

import com.google.gson.Gson;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import xoserver.dto.DTOPlayerData;

public  class ServerPane extends AnchorPane {

    protected final Label label;
    protected final Text text;
    protected final Button startBtn;
    protected final Label numOfOnline;
    protected final Label avusers;
    protected final Label numOfOnlineData;
    protected final Label avnum;
    
        ServerSocket myServerSocket;
        Socket s;
        DataInputStream dis ;
        PrintStream ps;
        

    public ServerPane() {

        label = new Label();
        text = new Text();
        startBtn = new Button();
        numOfOnline = new Label();
        avusers = new Label();
        numOfOnlineData = new Label();
        avnum = new Label();
        //SERVERLOGIC***************
            try
            {
                myServerSocket = new ServerSocket(5050);
                s = myServerSocket.accept();
                dis = new DataInputStream(s.getInputStream ());
                ps = new PrintStream(s.getOutputStream ());
                String msg = dis.readLine();
                System.out.println(msg);
                System.out.println("server starts");                
                ps.println("Data Received");
                 Gson gson = new Gson();
                DTOPlayerData player = gson.fromJson(msg, DTOPlayerData.class);
               
            }
            catch(IOException ex)
            {
                ex.printStackTrace();
            }
            finally
            {
                try
                {
                    ps.close();
                    dis.close();
                    s.close();
                    myServerSocket.close();
                    System.out.println("server closed");
                }
                catch(Exception ex)
                {
                    ex.printStackTrace();
                } 
            }
        
        //**************************

        setId("AnchorPane");
        setPrefHeight(200);
        setPrefWidth(320);

        label.setLayoutX(126);
        label.setLayoutY(120);
        label.setMinHeight(16);
        label.setMinWidth(69);

        text.setLayoutX(74.0);
        text.setLayoutY(30.0);
        text.setStrokeType(javafx.scene.shape.StrokeType.OUTSIDE);
        text.setStrokeWidth(0.0);
        text.setText("Server Application");
        text.setWrappingWidth(172.13677978515625);
        text.setFont(new Font(21.0));

        startBtn.setLayoutX(126.0);
        startBtn.setLayoutY(56.0);
        startBtn.setMnemonicParsing(false);
        startBtn.setPrefHeight(25.0);
        startBtn.setPrefWidth(56.0);
        startBtn.setText("Start");

        numOfOnline.setLayoutX(10.0);
        numOfOnline.setLayoutY(100.0);
        numOfOnline.setPrefHeight(24.0);
        numOfOnline.setPrefWidth(163.0);
        numOfOnline.setText("online users :");
        numOfOnline.setFont(new Font(16.0));

        avusers.setLayoutX(12.0);
        avusers.setLayoutY(137.0);
        avusers.setPrefHeight(25.0);
        avusers.setPrefWidth(119.0);
        avusers.setText("available users :");
        avusers.setFont(new Font(16.0));

        numOfOnlineData.setLayoutX(147.0);
        numOfOnlineData.setLayoutY(104.0);
        numOfOnlineData.setText("10");
        numOfOnlineData.setFont(new Font(15.0));

        avnum.setLayoutX(143.0);
        avnum.setLayoutY(141.0);
        avnum.setPrefHeight(17.0);
        avnum.setPrefWidth(35.0);
        avnum.setText(" 5");
        avnum.setFont(new Font(15.0));

        getChildren().add(label);
        getChildren().add(text);
        getChildren().add(startBtn);
        getChildren().add(numOfOnline);
        getChildren().add(avusers);
        getChildren().add(numOfOnlineData);
        getChildren().add(avnum);
       /* 
        startBtn.AddActionListener(e ->(){
        Server server = new Server();
        server.StartServer(8000);
    });
*/

    }
}
