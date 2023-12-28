/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xoserver;

import Database.DataAccessLayer;
import Model.DTO.DTOPlayerData;
import Model.DataOperation;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Geforce
 */
public class ServerHandler {

    private ServerSocket myServerSocket;
    private volatile boolean isServerRunning = true;
    public static Vector<ClientHandler> clientsVector;
    public static Vector<ClientHandler> onlineUser;

    public ServerHandler() {
        try {

            myServerSocket = new ServerSocket(5050);
            System.out.println("xoserver.ServerHandler.<init>()");
            clientsVector = new Vector<ClientHandler>();
            Vector<ClientHandler> onlineUser = new Vector<ClientHandler>();
        } catch (IOException ex) {
            Logger.getLogger(ServerHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        // Accept client connections in a separate thread
        Thread serverThread = new Thread(() -> {
            while (isServerRunning) {
                try {
                    Socket clientSocket = myServerSocket.accept();
                    ClientHandler clientHandler = new ClientHandler(clientSocket);
                    //  clientsVector.add(clientHandler);
                    System.out.println("clientsVector " + clientsVector.size());
                } catch (IOException ex) {
                    if (!isServerRunning) {
                        break;
                    }
                    ex.printStackTrace();
                }
            }
        });
        serverThread.setDaemon(true);
        serverThread.start();
    }

    public void stopServer() {
        isServerRunning = false; // Signal the server thread to stop
        for (ClientHandler client : clientsVector) {
            System.out.println("notify client in vector");
            client.notifyServerClosed();
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            Logger.getLogger(ServerHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            if (myServerSocket != null && !myServerSocket.isClosed()) {
                myServerSocket.close();
                System.out.println("Server closed");
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}

class ClientHandler extends Thread {


     private  Socket socket;
     private  DataInputStream dataInput ;
     private PrintStream dataOutput;
     private String clientName;
     private ObservableList<DTOPlayerData> availablePlayer = FXCollections.observableArrayList(new DTOPlayerData("aya", "aya", "", "", 1, 0, 2, true, true, true),new DTOPlayerData("rwan2", "aya", "", "", 1, 0, 2, true, true, true)) ;
  
    public ClientHandler(Socket s) { 
        try {
            socket = s;
            dataInput = new DataInputStream(s.getInputStream());
            dataOutput = new PrintStream(s.getOutputStream());
            //  clientsVector = new Vector<ClientHandler>();
            System.out.println("xoserver.ClientHandler.<init>()");
            // ClientHandler.clientsVector.add(this);
            // System.out.println("clientsVector "+ clientsVector.size());
            start();
        } catch (IOException ex) {
            Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void run() {
        try {
            String msg;
            while ((msg = dataInput.readLine()) != null) {
                System.out.println(msg);
                // check connection to the server at start screen
                if (msg.equals("start")) {
                    dataOutput.println("connected successfully");
                    addClientToVector(this);
                } else if (msg.equalsIgnoreCase("exit")) {
                    dataOutput.println("client exit");
                    System.out.println("client exit");
                    removeClientFromVector(this);

                }else if (msg.equals("availableUsers")) {
                    DTOPlayerData player = new DTOPlayerData("aya", "aya", "email", "1234", 0, 0, 0, true, true, true);
                    DTOPlayerData player2 = new DTOPlayerData("rwan2", "aya", "", "", 1, 0, 2, true, true, true);
                    List<DTOPlayerData> responseToClient =  DataAccessLayer.availableList();          //new ArrayList<>();  /// transfre this to string  xxxxxxxxxxxxxxxxxxxxxxxxxxxx    حولها في json و ابعته
                    //responseToClient.add(player);
                    //responseToClient.add(player2);
                    //System.out.println(responseToClient.get(2));
                    GsonBuilder builder = new GsonBuilder();
                    Gson gson = builder.create();
                    String json = gson.toJson(responseToClient);
                    System.out.println(json);
                    dataOutput.println(json);

                }/*else if(msg.equalsIgnoreCase("accepted")){
                    System.out.println("start the game");
                    dataOutput.println("start the game");
                }else if(msg.equalsIgnoreCase("rejected")){
                    System.out.println("rejected the game");
                    dataOutput.println("rejected the game");}*/
                else{
                    System.out.println("handleClientOperation "+msg);
                    handleClientOperation(msg);
                } 
            }
        } catch (IOException ex) {
            Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            closeResources();
        }
    }

    private void handleClientOperation(String msg) {
        DataOperation dataReceived = new Gson().fromJson(msg, DataOperation.class);
        if (dataReceived != null) {
            if (dataReceived.getOperation().equals("login")) {
                String responseToClient = DataAccessLayer.signInCheck(dataReceived.getPlayers().get(0).getUserName(), dataReceived.getPlayers().get(0).getPassword());
                System.out.println(dataReceived.getPlayers().get(0).getPassword());
                System.out.println(responseToClient);
                dataOutput.println(responseToClient);

                /*  if(!"error".equalsIgnoreCase(responseToClient))
                    ServerHandler.onlineUser.add(this);*/
              
                if(!"error".equalsIgnoreCase(responseToClient)){
                    System.out.println("add client name to the socket "+responseToClient);
                    this.setClientName(responseToClient.trim());
                }
            } else if (dataReceived.getOperation().equals("sign up")) {
                // Handle signup operation
                System.out.println(dataReceived.getPlayers());
                String responseToClient = DataAccessLayer.signUpCheck(dataReceived.getPlayers().get(0));
                dataOutput.println(responseToClient);
                /* if(!"error".equalsIgnoreCase(responseToClient))
                    ServerHandler.onlineUser.add(this);*/
            }// Add more operations here
             
      else if (dataReceived.getOperation().equals("profile")) {
            // Retrieve player data from the database using DataAccessLayer
            // Populate the 'player' object with the retrieved data
            System.out.println("Profile()");
            DTOPlayerData player = DataAccessLayer.profileCheck(dataReceived.getPlayers().get(0).getUserName().trim());
            System.out.println(player.getEmail());
            if(player==null){
                System.out.println("error");
                dataOutput.println("error");
            }else {
                GsonBuilder builder = new GsonBuilder();
                Gson gson = builder.create();
                String jsonResponse = gson.toJson(player);
                System.out.println("hii player"+jsonResponse);
        // Send the JSON response to the client
                dataOutput.println(jsonResponse);
            }
        }else if(dataReceived.getOperation().equals("request")){
           List<DTOPlayerData> requestPlayers = dataReceived.getPlayers();
           //loop on the available list to get the player to send the request to him 
           System.out.println("recieved data"+requestPlayers.get(0).getUserName());
           System.out.println("recieved data"+requestPlayers.get(1).getUserName());
           System.out.println("availablePlayer "+ServerHandler.clientsVector.size());
           notifyOtherPlayer(requestPlayers.get(1).getUserName().trim(),"user invited");
           System.out.println("wait for response from the player 2");
           responseForInvitation(requestPlayers.get(0).getUserName(),requestPlayers.get(1).getUserName());
           //dataOutput.println("recieved data");
            System.out.println("request Done ");
        }
           // Add more operations here
        }else{
             System.out.println("Received null data from client");
        }
    }

    public void closeResources() {
        try {
            if (dataOutput != null) {
                dataOutput.close();
            }
            if (dataInput != null) {
                dataInput.close();
            }
            if (socket != null && !socket.isClosed()) {
                socket.close();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    void forwardRequest() {

    }

    public void notifyServerClosed() {
        dataOutput.println("Server is closing");
        System.out.println("notify client to close the server");
    }

    private void removeClientFromVector(ClientHandler clientHandler) {
        if (ServerHandler.clientsVector != null) {
            ServerHandler.clientsVector.remove(clientHandler);
            System.out.println("Client removed from clientsVector");
            System.out.println("removeClientFromVector() " + ServerHandler.clientsVector.size());
        }
    }

    private void addClientToVector(ClientHandler clientHandler) {
        ServerHandler.clientsVector.add(clientHandler);
        System.out.println("Client added to clientsVector");
        System.out.println("addClientToVector() "+ ServerHandler.clientsVector.size());  
    }
    private synchronized void responseForInvitation(String playerWhoSendRequest , String playerResponseForRequest){
         try {
             String msg = dataInput.readLine();
             if(msg.equalsIgnoreCase("start the game")){
                 System.out.println("start the game for player1 "+playerWhoSendRequest+" and player2 "+playerResponseForRequest);
                 notifyOtherPlayer(playerWhoSendRequest, "start the game");
                // notifyOtherPlayer(playerResponseForRequest, "start the game");
                 //dataOutput.println("start the game");
                
                 
                 /// game session 
                 
             }else if(msg.equalsIgnoreCase("rejected")){
                 System.out.println("rejected the game");
                 System.out.println("rejected the game "+playerWhoSendRequest+" and player2 "+playerResponseForRequest);
                 notifyOtherPlayer(playerWhoSendRequest, "rejected the game");
                 //notifyOtherPlayer(playerResponseForRequest, "start the game");
             }
         } catch (IOException ex) {
             Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
         }
    } 
    private void notifyOtherPlayer(String playerWhoToNotify, String msgToSend){
        for(ClientHandler client :ServerHandler.clientsVector){
               System.out.println("get in loop to find the player to invite ");
               if(client.getClientName().equals(playerWhoToNotify)){
                   System.out.println("found player "+playerWhoToNotify);
                   System.out.println("send msg the msg is "+msgToSend);
                   client.dataOutput.println(msgToSend);
                   break;
               }
           }
    }
    public void setClientName(String playerName){
        this.clientName = playerName;
    }
    public String getClientName(){
        return clientName;

    }

}


