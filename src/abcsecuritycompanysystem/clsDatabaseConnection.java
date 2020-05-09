/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package abcsecuritycompanysystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author Sanele
 */
public class clsDatabaseConnection {
    //This methods connect to the database and return
    //a connection for further use in the system.
    public Connection mConnectToDatabaseABCSecurity()
    {
        String strDBConnectionString = "jdbc:mysql://localhost:3306/ABCSecurity";
    	String strDBUser = "root";
        String strDBPassword = "password";
        java.sql.Connection conMySQLConnectionString = null;
        try
        {
            conMySQLConnectionString = DriverManager.getConnection(strDBConnectionString, strDBUser, strDBPassword);
        }
        catch(SQLException e)
        {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return conMySQLConnectionString;
    }
}
