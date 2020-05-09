/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package abcsecuritycompanysystem;

import static java.awt.Frame.MAXIMIZED_BOTH;
import java.awt.HeadlessException;
import javax.swing.JOptionPane;

/**
 *
 * @author Sanele
 */
public class frmLogin extends javax.swing.JFrame {

    /**
     * Creates new form frmLogin
     */
    public frmLogin() {
        initComponents();
        txtUsername.requestFocusInWindow();
    }
    //Declaration of a public static String variable to store user ID
    private static String strUserID;
    
    //Declaration of String variables
    //to store user login credentials.
    String strUsername;
    String strUserPassword;
    String strUserLevel;
   
    //An instance of class clsSQLMethods to access methods
    //for querying the database.
    clsQueryingMethods clsSQLMethods = new clsQueryingMethods();
    
    public String mGetUserID()
    {
        return strUserID;
    }
    
    public void mSetUserID(String strValue)
    {
        frmLogin.strUserID = strValue;
    }
    
    //A method that accepts as an argument a query 
    //that fetches user from the database and
    //assign variables with values.
    private void mAssignVariables(String strQuery)
    {
        String[] arrLoginDetails = clsSQLMethods.mFetchRecordDetails(strQuery);      
        frmLogin.strUserID = arrLoginDetails[1];
        this.strUsername = arrLoginDetails[2];
        this.strUserPassword = arrLoginDetails[3];
        if(!(txtPassword.getText().toLowerCase().startsWith("admin"))){
            this.strUserLevel = arrLoginDetails[4];
        }
    }
    
    /*A method that logs in a user by checking if GUI textboxes
    * have been passed values, if so, check if the passed values
    * can be found on the database, if so, check if the account
    * trying to be authenticated is not deactivated, if not so,
    * check if the credentials provided are correct, if so, the
    * user is identified and directed to the inner window of
    * the system where they will be granted access to rights
    * suitable for their actions in the system.
    */
    private void mLogin()
    {
        try
        {
            if(txtUsername.getText().equals(""))
            {
                JOptionPane.showMessageDialog(null, "Username is required");
                txtUsername.requestFocusInWindow();
            }
            else if(txtPassword.getText().equals(""))
            {
                JOptionPane.showMessageDialog(null, "Password is required");
                txtPassword.requestFocusInWindow();
            }
            else
            {
                String strIdentifier;
                if(txtPassword.getText().equals("admin")){
                    strIdentifier = "admin";
                }else if(txtPassword.getText().toLowerCase().startsWith("admin")){
                    strIdentifier = "admin";
                }else if(txtPassword.getText().toLowerCase().startsWith("sec")){
                    strIdentifier = "security";
                }else{
                    strIdentifier = "client";
                }
                
                if(txtUsername.getText().toUpperCase().equals("admin") &&
                                txtPassword.getText().equals("admin"))
                {
                    frmMain frmMn = new frmMain();
                    frmMn.mSetStrUserIdentifier("admin");
                    frmMn.mABCUserAccessControl();
                    frmMn.setVisible(true);
                    this.setVisible(false);
                    frmMn.setExtendedState(MAXIMIZED_BOTH);
                }else{
                    boolean boolIsAccountDeactivated;
                    switch(strIdentifier){
                        case "admin":
                            boolIsAccountDeactivated = clsSQLMethods.mCheckIfAccountIsDeactivated("SELECT DeactivatedID FROM "
                                    + "tbldeactivatedadmin WHERE DeactivatedID='"+
                                    clsSQLMethods.mGetID("SELECT ID FROM tblAdmins WHERE Name ='"
                                        +txtUsername.getText().toLowerCase()+"' AND Password='"+
                                        clsCryptography.mEncryptPassword(txtPassword.getText().toLowerCase())
                                    +"'")+"'");
                            if(boolIsAccountDeactivated){
                                JOptionPane.showMessageDialog(null, "This account has been deactivated!");
                            }
                            else{
                                mAssignVariables("SELECT ID, Name, Password FROM tblAdmins WHERE Name ='"
                                                    +txtUsername.getText().toLowerCase()+"' AND Password='"
                                                    +clsCryptography.mEncryptPassword(
                                                            txtPassword.getText().toLowerCase())+"'");
                                
                                if(strUsername.equals(txtUsername.getText().toLowerCase()) && 
                                    clsCryptography.mDecryptPassword(strUserPassword).
                                        equals(txtPassword.getText().toLowerCase()))
                                {
                                    frmMain frmMn = new frmMain();
                                    frmMn.mSetStrUserIdentifier("admin");
                                    frmMn.mABCUserAccessControl();
                                    frmMn.setVisible(true);
                                    this.setVisible(false);
                                    frmMn.setExtendedState(MAXIMIZED_BOTH);
                                }
                            }
                            break;
                        case "client":
                            boolIsAccountDeactivated = clsSQLMethods.mCheckIfAccountIsDeactivated("SELECT DeactivatedID FROM "
                                    + "tbldeactivatedUser WHERE DeactivatedID='"+
                                    clsSQLMethods.mGetID("SELECT ID FROM tblUsers WHERE Name ='"
                                        +txtUsername.getText().toLowerCase()+"' AND Password='"+
                                        clsCryptography.mEncryptPassword(txtPassword.getText().toLowerCase())
                                        +"'")+"'");
                            if(boolIsAccountDeactivated){
                                JOptionPane.showMessageDialog(null, "This account has been deactivated!");
                            }
                            else{
                                mAssignVariables("SELECT ID, Name, Password, Level FROM tblUsers WHERE Name ='"
                                                +txtUsername.getText().toLowerCase()+"' AND Password='"
                                                +clsCryptography.mEncryptPassword(
                                                        txtPassword.getText().toLowerCase())+"'");
                                
                                if(strUsername.equals(txtUsername.getText().toLowerCase()) && 
                                    clsCryptography.mDecryptPassword(strUserPassword).
                                        equals(txtPassword.getText().toLowerCase()) && 
                                        strUserLevel.equals("client"))
                                {
                                    frmMain frmMn = new frmMain();
                                    frmMn.mSetStrUserIdentifier("client");
                                    frmMn.mABCUserAccessControl();
                                    frmMn.setVisible(true);
                                    this.setVisible(false);
                                    frmMn.setExtendedState(MAXIMIZED_BOTH);
                                }
                            }
                                break;
                        case "security":
                            boolIsAccountDeactivated = clsSQLMethods.mCheckIfAccountIsDeactivated("SELECT DeactivatedID FROM"
                                    + " tbldeactivatedUser WHERE DeactivatedID='"+clsSQLMethods.mGetID(
                                            "SELECT ID FROM tblUsers WHERE Name ='"
                                                +txtUsername.getText().toLowerCase()+"' AND Password='"+
                                                clsCryptography.mEncryptPassword(txtPassword.getText().toLowerCase())
                                    +"'")+"'");
                            if(boolIsAccountDeactivated){
                                JOptionPane.showMessageDialog(null, "This account has been blocked!");
                            }
                            else{
                                mAssignVariables("SELECT ID, Name, Password, Level FROM tblUsers WHERE Name ='"
                                                +txtUsername.getText().toLowerCase()+"' AND Password='"
                                                +clsCryptography.mEncryptPassword(
                                                        txtPassword.getText().toLowerCase())+"'");
                                
                                if(strUsername.equals(txtUsername.getText().toLowerCase()) && 
                                    clsCryptography.mDecryptPassword(strUserPassword).
                                        equals(txtPassword.getText().toLowerCase()) && 
                                        strUserLevel.equals("security"))
                                {
                                    frmMain frmMn = new frmMain();
                                    frmMn.mSetStrUserIdentifier("security");
                                    frmMn.mABCUserAccessControl();
                                    frmMn.setVisible(true);
                                    this.setVisible(false);
                                    frmMn.setExtendedState(MAXIMIZED_BOTH);
                                }
                            }
                            break;
                    }
                }
            }
        }
        catch(HeadlessException | NullPointerException eX)
        {
            JOptionPane.showMessageDialog(null, "Access Denied! Check your logging details");
        }
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        dsktopLogin = new javax.swing.JDesktopPane();
        lblUsername = new javax.swing.JLabel();
        txtUsername = new javax.swing.JTextField();
        lblPassword = new javax.swing.JLabel();
        txtPassword = new javax.swing.JPasswordField();
        lblIcon = new javax.swing.JLabel();
        btnLogin = new javax.swing.JButton();
        lblHeading = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(1616, 1010));

        dsktopLogin.setBackground(new java.awt.Color(153, 153, 255));
        dsktopLogin.setForeground(new java.awt.Color(255, 255, 255));
        dsktopLogin.setMaximumSize(new java.awt.Dimension(1616, 1010));
        dsktopLogin.setMinimumSize(new java.awt.Dimension(1616, 1010));
        dsktopLogin.setName(""); // NOI18N

        lblUsername.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblUsername.setText("Username");
        dsktopLogin.add(lblUsername);
        lblUsername.setBounds(230, 270, 190, 70);

        txtUsername.setToolTipText("");
        dsktopLogin.add(txtUsername);
        txtUsername.setBounds(820, 270, 210, 50);

        lblPassword.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblPassword.setText("Password");
        dsktopLogin.add(lblPassword);
        lblPassword.setBounds(230, 440, 110, 22);
        dsktopLogin.add(txtPassword);
        txtPassword.setBounds(820, 410, 210, 50);

        lblIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/abcsecuritycompanysystem/loginIcon.PNG"))); // NOI18N
        dsktopLogin.add(lblIcon);
        lblIcon.setBounds(540, 10, 240, 150);

        btnLogin.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btnLogin.setText("Login");
        btnLogin.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnLogin.setPreferredSize(new java.awt.Dimension(6, 20));
        btnLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLoginActionPerformed(evt);
            }
        });
        dsktopLogin.add(btnLogin);
        btnLogin.setBounds(520, 580, 240, 50);

        lblHeading.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        lblHeading.setText("             Login");
        dsktopLogin.add(lblHeading);
        lblHeading.setBounds(530, 170, 250, 30);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(dsktopLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 1637, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(dsktopLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 716, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLoginActionPerformed
        mLogin(); //This lime of code invokes the logging in method.
    }//GEN-LAST:event_btnLoginActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(frmLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frmLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frmLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frmLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new frmLogin().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnLogin;
    private javax.swing.JDesktopPane dsktopLogin;
    private javax.swing.JLabel lblHeading;
    private javax.swing.JLabel lblIcon;
    private javax.swing.JLabel lblPassword;
    private javax.swing.JLabel lblUsername;
    private javax.swing.JPasswordField txtPassword;
    private javax.swing.JTextField txtUsername;
    // End of variables declaration//GEN-END:variables
}
