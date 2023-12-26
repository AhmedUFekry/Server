/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xoserver;

import Database.DataAccessLayer;
import Model.DataOperation;
import com.google.gson.Gson;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Geforce
 */
public class ServerHandler{
       
    private ServerSocket myServerSocket; 
    private volatile boolean isServerRunning = true;
    public static Vector<ClientHandler> clientsVector ; 
    public static Vector<ClientHandler> onlineUser  ; 
    public ServerHandler() {
        try {

            myServerSocket = new ServerSocket(5050);
            System.out.println("xoserver.ServerHandler.<init>()");
            clientsVector = new Vector<ClientHandler>();
            Vector<ClientHandler> onlineUser =  new Vector<ClientHandler>();
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
                     System.out.println("clientsVector "+ clientsVector.size());
                 } catch (IOException ex) {
                     if(!isServerRunning){
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
        for(ClientHandler client : clientsVector ){
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
    // private static Vector<ClientHandler> clientsVector ; 
    public ClientHandler(Socket s) { 
        try {
            socket = s;
            dataInput = new DataInputStream(s.getInputStream ());
            dataOutput = new PrintStream(s.getOutputStream ());
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
    public void run(){
        try {
            String msg ;
           while((msg = dataInput.readLine()) != null){
               System.out.println(msg);
                // check connection to the server at start screen
                if(msg.equals("start")){
                    dataOutput.println("connected successfully"); 
                    addClientToVector(this);
                }else if(msg.equalsIgnoreCase("exit")){
                    dataOutput.println("client exit"); 
                    System.out.println("client exit");
                    removeClientFromVector(this);
                }
                else{
                    handleClientOperation(msg);
                }
            } 
       }catch (IOException ex) {
                Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
            }finally{
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
            } else if (dataReceived.getOperation().equals("sign up")) {
                // Handle signup operation
                System.out.println(dataReceived.getPlayers());
                String responseToClient = DataAccessLayer.signUpCheck(dataReceived.getPlayers().get(0));
                dataOutput.println(responseToClient);
               /* if(!"error".equalsIgnoreCase(responseToClient))
                    ServerHandler.onlineUser.add(this);*/
            }
            // Add more operations here
        }else{
             System.out.println("Received null data from client");
        }
    }
    public void closeResources() {
        try {
            if (dataOutput != null) dataOutput.close();
            if (dataInput != null) dataInput.close();
            if (socket != null && !socket.isClosed()) socket.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    void forwardRequest(){
     
     }
   public void notifyServerClosed(){
       dataOutput.println("Server is closing");
       System.out.println("notify client to close the server");
   }
   private void removeClientFromVector(ClientHandler clientHandler) {
        if (ServerHandler.clientsVector != null) {
            ServerHandler.clientsVector.remove(clientHandler);
            System.out.println("Client removed from clientsVector");
            System.out.println("removeClientFromVector() "+ ServerHandler.clientsVector.size()); 
        }
    }
    private void addClientToVector(ClientHandler clientHandler) {
        
            ServerHandler.clientsVector.add(clientHandler);
            System.out.println("Client removed from clientsVector");
            System.out.println("addClientToVector() "+ ServerHandler.clientsVector.size()); 
        
    }
}