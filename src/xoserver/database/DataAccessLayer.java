/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xoserver.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.apache.derby.jdbc.ClientDriver;
import xoserver.dto.DTOPlayerData;

/**
 *
 * @author DELL
 */
public class DataAccessLayer {
    public static void testConnection() throws SQLException{
        //when error occour throw it and close
           
        
    }
        
              public static int insertContact(DTOPlayerData player) throws SQLException{ //the only class will deals with DB
             int result;
            DriverManager.registerDriver(new ClientDriver());
            Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/xodatabase", "root", "root");
            PreparedStatement stmt= con.prepareStatement ( "INSERT INTO ROOT.PLAYER (USERNAME,PASSWORD,GENDER) VALUES (?, ?, ?)");
            stmt.setString(1, player.getUserName());
            stmt.setString(2, player.getPassword());
            stmt.setBoolean(3, player.isIsMale());
                result=stmt.executeUpdate();
              stmt.close();
              con.close();
              return result;
              
         
    }
}
