/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package abcsecuritycompanysystem;

import static java.awt.Frame.MAXIMIZED_BOTH;
import java.awt.GridLayout;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.Period;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

/**
 *
 * @author Sanele
 */
public class frmAccountsManagement extends javax.swing.JInternalFrame {

    /**
     * Creates new form frmAccountsManagement
     */
    public frmAccountsManagement() {
        initComponents();
        mDefaultAccountManagementGUIControls();
    }
    //Declaration of a string to store
    //user identifying information
    private String strAccountManagingUser;
    
    public void mSetAccountManagingUser(String strValue)
    {
        this.strAccountManagingUser = strValue;
    }
    //A method to set default behaviour of this form's
    //GUI controls.
    private void mDefaultAccountManagementGUIControls()
    {
        txtName.setEditable(false);
        txtDateOfBirth.setEditable(false);
        txtPassword.setEditable(false);
        btnView.setEnabled(true);
        btnUpdate.setEnabled(true);
        btnDeactivate.setEnabled(true);
        btnClose.setEnabled(true);
    }
    
    //A method that set behaviour of the form's
    //GUI controls when the update button has 
    //been selected.
    private void mUpdateAccountGUIControls()
    {
        txtName.setEditable(true);
        txtDateOfBirth.setEditable(true);
        txtPassword.setEditable(true);
        btnView.setEnabled(false);
        btnUpdate.setEnabled(true);
        btnDeactivate.setEnabled(false);
        btnClose.setEnabled(true);
    }
    
    //Creations of instances of class clsSQLMethods, class frmRegisterAccounts
    // and class frmLogin
    clsQueryingMethods clsSQLMethods = new clsQueryingMethods();
    frmRegistrations frmRegisterAccount = new frmRegistrations("");
    frmLogin frmlogin = new frmLogin();
    
    //A method that returns as string a query that fetches 
    //account details from the database.
    private String mFetchAccountDetailsOfTheQueryingUser(String strTableName)
    {
        return "SELECT Name, DateOfBirth, Password FROM "+strTableName+" WHERE ID='"+frmlogin.mGetUserID()+"'";
    }
    
    //A method that fetches details from the database and
    //populate the GUI textboxes with those details.
    private void mSetGUIValues(String strTableName)
    {
        String[] arrAccountDetails = clsSQLMethods.mFetchRecordDetails(mFetchAccountDetailsOfTheQueryingUser(strTableName));
        txtName.setText(arrAccountDetails[1]);
        txtDateOfBirth.setText(arrAccountDetails[2]);
        txtPassword.setText(clsCryptography.mDecryptPassword(arrAccountDetails[3]));
        txtPassword.setSize(6, 20);
    }
    
    //A method that clears values in text boxes
    private void mClearGUIFields()
    {
        txtName.setText("");
        txtDateOfBirth.setText("");
        txtPassword.setText("");
    }
    
    //A method that returns as string a query that updates 
    //account details in the database.
    private String mUpdateAccountDetailsOfTheQueryingUser(String strTableName)
    {
        return "UPDATE "+strTableName+" SET Name ='"+txtName.getText().toLowerCase()+"', DateOfBirth='"+
                txtDateOfBirth.getText()+"', Password='"+
                clsCryptography.mEncryptPassword(txtPassword.getText().toLowerCase())+"' WHERE ID='"+frmlogin.mGetUserID()+"'";        
    }
    
    //A method that returns as string a query that inserts 
    //account ID to the table of deacctivated admin accounts.
    private String mDeactivateAdminAccountQuery()
    {
        return "INSERT INTO tblDeactivatedAdmin" + "(DeactivatedID)"+
                "VALUES('"+ frmlogin.mGetUserID()+ "')";
    }
    
    //A method that returns as string a query that inserts 
    //account ID to the table of deacctivated user accounts.
    private String mDeactivateClientOrSecurityAccountQuery()
    {
        return "INSERT INTO tblDeactivatedUser" + "(DeactivatedID)"+
                "VALUES('"+ frmlogin.mGetUserID()+ "')";
    }
     
    //A method that views acount details in a tabular format
    public void mViewAccountDetails(String strQuery)
    {
        clsDatabaseConnection clsConnect = new clsDatabaseConnection();
        Statement stSQLQuery = null;
        ResultSet rs = null;
        try
        {
            stSQLQuery = clsConnect.mConnectToDatabaseABCSecurity().prepareStatement(strQuery);
            rs = stSQLQuery.executeQuery(strQuery);
            ResultSetMetaData rsmt = rs.getMetaData();
            int intColumnCount = rsmt.getColumnCount();
            Vector vColumn = new Vector(intColumnCount);
            for(int i = 1; i <= intColumnCount; i++)
            {
                vColumn.add(rsmt.getColumnName(i));
            }
            Vector vData = new Vector();
            Vector vRow = new Vector();
            while(rs.next())
            {
                vRow = new Vector(intColumnCount);
                for(int i = 1; i <= intColumnCount; i++)
                {
                    vRow.add(rs.getObject(i));
                }
                vData.add(vRow);
            }
            mTable(vData, vColumn);
            stSQLQuery.close();
            rs.close();
        }
        catch(SQLException e)
        {
            JOptionPane.showMessageDialog(null, "Technical Error, table cannot be displayed\n"+e.getMessage());
        }
    }
    //A method that create a table and populate it with data from the database
    private void mTable(Vector vData, Vector vColumn)
    {
        
            JPanel pnlTable = new JPanel();
            JTable tblAccounts = new JTable(vData, vColumn);
            tblAccounts.setFillsViewportHeight(true);
            JScrollPane jspAccountsPane = new JScrollPane(tblAccounts);
            tblAccounts.setVisible(true);
            tblAccounts.validate();
            pnlTable.setLayout(new GridLayout(1,0));
            pnlTable.add(jspAccountsPane);
            this.setResizable(true);
            this.setContentPane(pnlTable);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        dskAccountManagement = new javax.swing.JDesktopPane();
        lblHeading = new javax.swing.JLabel();
        lblIcon = new javax.swing.JLabel();
        lblName = new javax.swing.JLabel();
        txtName = new javax.swing.JTextField();
        lblPassword = new javax.swing.JLabel();
        txtPassword = new javax.swing.JTextField();
        btnView = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        btnDeactivate = new javax.swing.JButton();
        lblDOB = new javax.swing.JLabel();
        btnClose = new javax.swing.JButton();
        txtDateOfBirth = new javax.swing.JTextField();

        dskAccountManagement.setBackground(new java.awt.Color(153, 153, 255));

        lblHeading.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblHeading.setText("Manage Your Account");

        lblIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/abcsecuritycompanysystem/Icon.jpeg"))); // NOI18N

        lblName.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblName.setText("Name(s)");

        lblPassword.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblPassword.setText("Password");

        btnView.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnView.setText("View Details");
        btnView.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnView.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnViewActionPerformed(evt);
            }
        });

        btnUpdate.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnUpdate.setText("Update Acc");
        btnUpdate.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });

        btnDeactivate.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnDeactivate.setText("Deactivate");
        btnDeactivate.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnDeactivate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeactivateActionPerformed(evt);
            }
        });

        lblDOB.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblDOB.setText("Date Of Birth");

        btnClose.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnClose.setText("Close");
        btnClose.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCloseActionPerformed(evt);
            }
        });

        dskAccountManagement.setLayer(lblHeading, javax.swing.JLayeredPane.DEFAULT_LAYER);
        dskAccountManagement.setLayer(lblIcon, javax.swing.JLayeredPane.DEFAULT_LAYER);
        dskAccountManagement.setLayer(lblName, javax.swing.JLayeredPane.DEFAULT_LAYER);
        dskAccountManagement.setLayer(txtName, javax.swing.JLayeredPane.DEFAULT_LAYER);
        dskAccountManagement.setLayer(lblPassword, javax.swing.JLayeredPane.DEFAULT_LAYER);
        dskAccountManagement.setLayer(txtPassword, javax.swing.JLayeredPane.DEFAULT_LAYER);
        dskAccountManagement.setLayer(btnView, javax.swing.JLayeredPane.DEFAULT_LAYER);
        dskAccountManagement.setLayer(btnUpdate, javax.swing.JLayeredPane.DEFAULT_LAYER);
        dskAccountManagement.setLayer(btnDeactivate, javax.swing.JLayeredPane.DEFAULT_LAYER);
        dskAccountManagement.setLayer(lblDOB, javax.swing.JLayeredPane.DEFAULT_LAYER);
        dskAccountManagement.setLayer(btnClose, javax.swing.JLayeredPane.DEFAULT_LAYER);
        dskAccountManagement.setLayer(txtDateOfBirth, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout dskAccountManagementLayout = new javax.swing.GroupLayout(dskAccountManagement);
        dskAccountManagement.setLayout(dskAccountManagementLayout);
        dskAccountManagementLayout.setHorizontalGroup(
            dskAccountManagementLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dskAccountManagementLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(dskAccountManagementLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(dskAccountManagementLayout.createSequentialGroup()
                        .addComponent(lblPassword)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(dskAccountManagementLayout.createSequentialGroup()
                        .addComponent(lblIcon)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblHeading)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, dskAccountManagementLayout.createSequentialGroup()
                        .addGroup(dskAccountManagementLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(btnView, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblDOB, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblName, javax.swing.GroupLayout.Alignment.LEADING))
                        .addGap(18, 18, Short.MAX_VALUE)
                        .addGroup(dskAccountManagementLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, dskAccountManagementLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(txtName)
                                .addComponent(txtPassword)
                                .addComponent(txtDateOfBirth, javax.swing.GroupLayout.DEFAULT_SIZE, 155, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, dskAccountManagementLayout.createSequentialGroup()
                                .addComponent(btnUpdate)
                                .addGap(18, 18, 18)
                                .addComponent(btnDeactivate, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnClose, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(30, 30, 30))))
        );
        dskAccountManagementLayout.setVerticalGroup(
            dskAccountManagementLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dskAccountManagementLayout.createSequentialGroup()
                .addGroup(dskAccountManagementLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(dskAccountManagementLayout.createSequentialGroup()
                        .addGap(76, 76, 76)
                        .addComponent(lblHeading, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(dskAccountManagementLayout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addComponent(lblIcon, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(56, 56, 56)
                .addGroup(dskAccountManagementLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblName)
                    .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(33, 33, 33)
                .addGroup(dskAccountManagementLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblDOB)
                    .addComponent(txtDateOfBirth, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addGroup(dskAccountManagementLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPassword)
                    .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 73, Short.MAX_VALUE)
                .addGroup(dskAccountManagementLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnView)
                    .addComponent(btnUpdate)
                    .addComponent(btnDeactivate)
                    .addComponent(btnClose))
                .addGap(73, 73, 73))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(dskAccountManagement)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(dskAccountManagement)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnViewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnViewActionPerformed
        switch(strAccountManagingUser){
            case "admin":
                mViewAccountDetails(mFetchAccountDetailsOfTheQueryingUser("tblAdmins"));
                break;
            case "security": case "client":
                mViewAccountDetails(mFetchAccountDetailsOfTheQueryingUser("tblUsers"));
                break;
        }
    }//GEN-LAST:event_btnViewActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        switch(btnUpdate.getText()){
            case "Update Acc":
                mUpdateAccountGUIControls();
                btnUpdate.setText("Save Update");
                btnClose.setText("Cancel");
                switch(strAccountManagingUser){
                    case "admin":
                        mSetGUIValues("tblAdmins");
                        break;
                    case "security": case "client":
                        mSetGUIValues("tblUsers");
                        break;
                }
                break;
            case "Save Update":
                switch(strAccountManagingUser){
                    case "admin":
                        if(txtName.getText().equals("")){
                            JOptionPane.showMessageDialog(null, "The Name(s) field cannot be left empty!");
                        }
                        else if(txtDateOfBirth.getText().equals("")){
                            JOptionPane.showMessageDialog(null, "The Date of birth field cannot be left empty!");
                        }
                        else if(Period.between((LocalDate.parse((txtDateOfBirth.getText()))),
                                LocalDate.parse(frmRegisterAccount.mTodayDate())).getYears() < 18){
                            JOptionPane.showMessageDialog(null, "Administrator account holders should be 18 years and above!");
                        }
                        else if((strAccountManagingUser.equals("admin") && !(txtPassword.getText().startsWith("admin")))){
                            JOptionPane.showMessageDialog(null, "Administrators accounts must be prefixed with 'admin'.");
                            txtPassword.setText(null);
                            txtPassword.setText("admin");
                            txtPassword.requestFocusInWindow();
                        }else{
                            clsSQLMethods.mUpdateRecordDetails(mUpdateAccountDetailsOfTheQueryingUser("tblAdmins"));
                            mSetGUIValues("tblAdmins");
                        }
                        break;
                    case "security": case "client":
                        if(txtName.getText().equals("")){
                            JOptionPane.showMessageDialog(null, "The Name(s) field cannot be left empty!");
                        }
                        else if(txtDateOfBirth.getText().equals("")){
                            JOptionPane.showMessageDialog(null, "The Date of birth field cannot be left empty!");
                        }
                        else if(Period.between((LocalDate.parse((txtDateOfBirth.getText()))),
                                LocalDate.parse(frmRegisterAccount.mTodayDate())).getYears() < 18){
                            switch(strAccountManagingUser){
                                case "security":
                                    JOptionPane.showMessageDialog(null, "Security account holders should be 18 years and above!");
                                    break;
                                case "client":
                                   JOptionPane.showMessageDialog(null, "Security account holders should be 18 years and above!");
                                    break;
                            }
                        }
                        else if((strAccountManagingUser.equals("security") && !(txtPassword.getText().startsWith("sec")))){
                            JOptionPane.showMessageDialog(null, "Security guards accounts must be prefixed with 'sec'.");
                            txtPassword.setText(null);
                            txtPassword.setText("sec");
                            txtPassword.requestFocusInWindow();
                        }else if(txtPassword.getText().equals("")){
                            JOptionPane.showMessageDialog(null, "Password field cannot be left empty");
                            txtPassword.requestFocusInWindow();
                        }else{
                            clsSQLMethods.mUpdateRecordDetails(mUpdateAccountDetailsOfTheQueryingUser("tblUsers"));
                            mSetGUIValues("tblUsers");
                        }
                        break;
                }
                btnUpdate.setText("Update Acc");
                btnClose.setText("Close");
                mClearGUIFields();
                mDefaultAccountManagementGUIControls();
                break;
        }
        
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnDeactivateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeactivateActionPerformed
        switch(strAccountManagingUser){
            case "admin":
                int intOption = JOptionPane.showConfirmDialog(null, "Do you want to Deactivate Your Account?",
                        "Deactivate Account", JOptionPane.YES_OPTION);
                if(intOption == 0){
                    clsSQLMethods.mCreateRecord(mDeactivateAdminAccountQuery(), btnDeactivate.getText());
                    this.hide();
                    this.getTopLevelAncestor().hide();
                    frmlogin.setTitle("ABC Security Login");
                    frmlogin.setVisible(true);
                    frmlogin.setExtendedState(MAXIMIZED_BOTH);
                }else{
                    mDefaultAccountManagementGUIControls();
                }
                
                break;
            case "security": case "client":
                clsSQLMethods.mCreateRecord(mDeactivateClientOrSecurityAccountQuery(), btnDeactivate.getText());
                this.hide();
                this.getTopLevelAncestor().hide();
                frmlogin.setTitle("ABC Security Login");
                frmlogin.setVisible(true);
                frmlogin.setExtendedState(MAXIMIZED_BOTH);
                break;
        }
    }//GEN-LAST:event_btnDeactivateActionPerformed

    private void btnCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCloseActionPerformed
        switch(btnClose.getText())
        {
            case "Close":
                this.hide();
                break;
            case "Cancel":
                btnUpdate.setText("Update Acc");
                btnClose.setText("Close");
                mClearGUIFields();
                mDefaultAccountManagementGUIControls();
                break;
        }
    }//GEN-LAST:event_btnCloseActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnClose;
    private javax.swing.JButton btnDeactivate;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JButton btnView;
    private javax.swing.JDesktopPane dskAccountManagement;
    private javax.swing.JLabel lblDOB;
    private javax.swing.JLabel lblHeading;
    private javax.swing.JLabel lblIcon;
    private javax.swing.JLabel lblName;
    private javax.swing.JLabel lblPassword;
    private javax.swing.JTextField txtDateOfBirth;
    private javax.swing.JTextField txtName;
    private javax.swing.JTextField txtPassword;
    // End of variables declaration//GEN-END:variables
}
