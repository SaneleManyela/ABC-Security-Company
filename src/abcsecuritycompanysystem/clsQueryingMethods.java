/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package abcsecuritycompanysystem;

import java.awt.HeadlessException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

/**
 *
 * @author Sanele
 */
public class clsQueryingMethods {
    //A declaration of an instance of class clsDatabaseConnection
    clsDatabaseConnection clsConnect = new clsDatabaseConnection();
    
    //A method that checks if an account has been blocked and
    //return a boolean value.
    public boolean mCheckIfAccountIsDeactivated(String strQuery)
    {
        boolean boolIsDeactivated = false;
        try(Statement stStatement = clsConnect.mConnectToDatabaseABCSecurity().prepareStatement(strQuery)){
            stStatement.execute(strQuery);
            try (ResultSet rs = stStatement.getResultSet()) {
                boolIsDeactivated = rs.next();
                stStatement.close();
                rs.close();
            }
        }
        catch(SQLException e){
            JOptionPane.showMessageDialog(null, "The application has encountered a technical error.\n"+ e.getMessage());
        }
        return boolIsDeactivated;
    }
    
    //A method that checks if a record exists in the database
    //then return a boolean value.
    public boolean mCheckIfRecordExists(String strQuery)
    {
        boolean boolAccountExists = false;
        ResultSet rs;
        try
        {
            try (Statement stStatement = clsConnect.mConnectToDatabaseABCSecurity().prepareStatement(strQuery)) {
                stStatement.execute(strQuery);
                rs = stStatement.getResultSet();
                boolAccountExists = rs.next();
                stStatement.close();
            }
            rs.close();
        }
        catch(SQLException e){
            JOptionPane.showMessageDialog(null, "The application has encountered a technical error.\n"+e.getMessage());
        }
        return boolAccountExists;
    }
    
    //A method that insert data and create records in the database
    public void mCreateRecord(String strQuery, String strButtonOfAction)
    {
        try
        {
            try (Statement stStatement = clsConnect.mConnectToDatabaseABCSecurity().prepareStatement(strQuery)) {
                stStatement.executeUpdate(strQuery);
                stStatement.close();
                switch(strButtonOfAction){
                    case "Register":
                        JOptionPane.showMessageDialog(null, "Account created.");
                        break;
                    case "Deactivate":
                        JOptionPane.showMessageDialog(null, "Account deactivated.");
                        break;
                    case "Save Task":
                        JOptionPane.showMessageDialog(null, "Task Assigned.");
                        break;
                    case "Save":
                        JOptionPane.showMessageDialog(null, "Complaint has been registered.");
                        break;
                }
            }
	}
        catch(SQLException e){
            JOptionPane.showMessageDialog(null, " The record could not be created\n"+e.getMessage());
        }
    }
    
    //A method that gets an ID from the database (and other numeric data)
    //and return it as integer value.
    public int mGetID(String strQuery)
    {
        try
        {
            try (Statement stStatement = clsConnect.mConnectToDatabaseABCSecurity().prepareStatement(strQuery)) {
                stStatement.execute(strQuery);
                try (ResultSet rs = stStatement.getResultSet()) {
                    while(rs.next()){
                        return rs.getInt(1);
                    }
                    stStatement.close();
                    rs.close();
                }
            }
	}
        catch(SQLException | NullPointerException e){
            JOptionPane.showMessageDialog(null, "Technical error has been encounterd\n"+e.getMessage());
        }
       return 0;
    }
    
    static String[] arrRecordDetails = null; //A declaration of an array of type string.
    
    //A method that fetches data from the database and 
    //populate a string array and return that array
    public String[] mFetchRecordDetails(String strQuery)
    {
        try
        {
            try (Statement stStatement = clsConnect.mConnectToDatabaseABCSecurity().prepareStatement(strQuery)) {
                stStatement.execute(strQuery);
                try (ResultSet rs = stStatement.getResultSet()) {
                    ResultSetMetaData rsmt = rs.getMetaData();
                    arrRecordDetails = new String[rsmt.getColumnCount()+1];
                    while(rs.next())
                    {
                        for(int i = 1; i < arrRecordDetails.length; i++){
                            arrRecordDetails[i] = rs.getString(i);                    
                        }
                    }
                    stStatement.close();
                    rs.close();
                }
                return arrRecordDetails;
            }
	}
        catch(SQLException e){
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return null;
    }
    
    //A method that updates data in the database
    public void mUpdateRecordDetails(String strQuery)
    {
        try
        {
            try (Statement stStatement = clsConnect.mConnectToDatabaseABCSecurity().prepareStatement(strQuery)) {
                stStatement.executeUpdate(strQuery);
                stStatement.close();
                JOptionPane.showMessageDialog(null, "Record updated successfully.");
            }
        }
        catch(HeadlessException | SQLException e){
            JOptionPane.showMessageDialog(null, "Technical error, updated transaction could not be finished\n."+e.getMessage());
        }
    }
    
    //A method that deletes data in the database
    public void mDeleteRecordDetails(String strQuery)
    {
        try
        {
            try (Statement stStatement = clsConnect.mConnectToDatabaseABCSecurity().prepareStatement(strQuery)) {
                stStatement.execute(strQuery);
                stStatement.close();
                JOptionPane.showMessageDialog(null, "Record was deleted.");
            }
        }
        catch(HeadlessException | SQLException e){
            JOptionPane.showMessageDialog(null, "Technical error, deleted transaction could not be finished.\n"+e.getMessage());
        }
    }
}