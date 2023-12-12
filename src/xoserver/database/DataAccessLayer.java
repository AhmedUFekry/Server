/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xoserver.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.apache.derby.jdbc.ClientDriver;

/**
 *
 * @author DELL
 */
public class DataAccessLayer {
    public static void testConnection() throws SQLException{
            DriverManager.registerDriver(new ClientDriver()); //when error occour throw it and close
            Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/XODATABASE", "root", "root");
    }
}
