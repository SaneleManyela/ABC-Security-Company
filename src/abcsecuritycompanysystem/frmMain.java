/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package abcsecuritycompanysystem;

/**
 *
 * @author Sanele
 */
public class frmMain extends javax.swing.JFrame {

    /**
     * Creates new form frmABCDesktop
     */
    public frmMain() {
        initComponents();
    }
    //A declaration of a string variable that 
    //is used to identify a logged in user
    private String strUserIdentifier;
    frmLogin frmlogin = new frmLogin();
    
    public void mSetStrUserIdentifier(String strValue)
    {
        this.strUserIdentifier = strValue;
    }
    
    //A method that assigns user rights to a logged in user
    public void mABCUserAccessControl()
    {
        switch(strUserIdentifier)
        {
            case "client":
                mnuFile.setEnabled(true);
                mnuComplaints.setEnabled(true);
                mnuAdministrativeOptions.setVisible(false);
                mnuAccount.setEnabled(true);
                mnuTasksAndAccountManagement.setVisible(false);
                mnuReports.setVisible(false);
                break;
            case "admin":
                mnuFile.setEnabled(true);
                mnuComplaints.setVisible(false);
                if(frmlogin.mGetUserID() == null){
                    mnuItemRegisterAdministrator.setEnabled(true);
                    mnuItemRegisterClient.setEnabled(false);
                    mnuItemRegisterSecurityGuard.setEnabled(false);
                    mnuItemAssignTasks.setEnabled(false);
                    mnuItemAccountManagement.setEnabled(false);
                }
                else{
                    mnuAdministrativeOptions.setVisible(true);
                    mnuAccount.setEnabled(true);
                }
                mnuTasksAndAccountManagement.setVisible(false);
                mnuReports.setEnabled(true);
                break;
            case "security":
                mnuFile.setEnabled(true);
                mnuItemComplaintRegisteration.setVisible(false);
                mnuComplaints.setVisible(false);
                mnuAdministrativeOptions.setVisible(false);
                mnuAccount.setVisible(false);
                mnuTasksAndAccountManagement.setEnabled(true);
                mnuReports.setVisible(false);
                break;
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

        dsktpMain = new javax.swing.JDesktopPane();
        mbMainMenu = new javax.swing.JMenuBar();
        mnuFile = new javax.swing.JMenu();
        mnuItemLogout = new javax.swing.JMenuItem();
        mnuItemExit = new javax.swing.JMenuItem();
        mnuAdministrativeOptions = new javax.swing.JMenu();
        mnuItemRegisterAdministrator = new javax.swing.JMenuItem();
        mnuItemRegisterSecurityGuard = new javax.swing.JMenuItem();
        mnuItemRegisterClient = new javax.swing.JMenuItem();
        mnuItemAssignTasks = new javax.swing.JMenuItem();
        mnuItemAdminViewTasks = new javax.swing.JMenuItem();
        mnuAccount = new javax.swing.JMenu();
        mnuItemAccountManagement = new javax.swing.JMenuItem();
        mnuTasksAndAccountManagement = new javax.swing.JMenu();
        mnuItemViewTasks = new javax.swing.JMenuItem();
        mnuItemSecurityAccountManagement = new javax.swing.JMenuItem();
        mnuItemClientComplaints = new javax.swing.JMenuItem();
        mnuComplaints = new javax.swing.JMenu();
        mnuItemComplaintRegisteration = new javax.swing.JMenuItem();
        mnuReports = new javax.swing.JMenu();
        mnuItemStuffDetailsReport = new javax.swing.JMenuItem();
        mnuItemComplaintsReport = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        dsktpMain.setBackground(new java.awt.Color(153, 153, 255));

        javax.swing.GroupLayout dsktpMainLayout = new javax.swing.GroupLayout(dsktpMain);
        dsktpMain.setLayout(dsktpMainLayout);
        dsktpMainLayout.setHorizontalGroup(
            dsktpMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1323, Short.MAX_VALUE)
        );
        dsktpMainLayout.setVerticalGroup(
            dsktpMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 546, Short.MAX_VALUE)
        );

        mbMainMenu.setBackground(new java.awt.Color(0, 0, 0));

        mnuFile.setBackground(new java.awt.Color(0, 0, 0));
        mnuFile.setForeground(new java.awt.Color(255, 255, 0));
        mnuFile.setText("File");

        mnuItemLogout.setText("Logout");
        mnuItemLogout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuItemLogoutActionPerformed(evt);
            }
        });
        mnuFile.add(mnuItemLogout);

        mnuItemExit.setText("Exit");
        mnuItemExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuItemExitActionPerformed(evt);
            }
        });
        mnuFile.add(mnuItemExit);

        mbMainMenu.add(mnuFile);

        mnuAdministrativeOptions.setBackground(new java.awt.Color(0, 0, 0));
        mnuAdministrativeOptions.setForeground(new java.awt.Color(255, 255, 0));
        mnuAdministrativeOptions.setText("Administrative Options");

        mnuItemRegisterAdministrator.setText("Register an Admin");
        mnuItemRegisterAdministrator.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuItemRegisterAdministratorActionPerformed(evt);
            }
        });
        mnuAdministrativeOptions.add(mnuItemRegisterAdministrator);

        mnuItemRegisterSecurityGuard.setText("Register a Security Guard");
        mnuItemRegisterSecurityGuard.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuItemRegisterSecurityGuardActionPerformed(evt);
            }
        });
        mnuAdministrativeOptions.add(mnuItemRegisterSecurityGuard);

        mnuItemRegisterClient.setText("Register a New Client");
        mnuItemRegisterClient.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuItemRegisterClientActionPerformed(evt);
            }
        });
        mnuAdministrativeOptions.add(mnuItemRegisterClient);

        mnuItemAssignTasks.setText("Assign Tasks");
        mnuItemAssignTasks.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuItemAssignTasksActionPerformed(evt);
            }
        });
        mnuAdministrativeOptions.add(mnuItemAssignTasks);

        mnuItemAdminViewTasks.setText("View Tasks");
        mnuItemAdminViewTasks.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuItemAdminViewTasksActionPerformed(evt);
            }
        });
        mnuAdministrativeOptions.add(mnuItemAdminViewTasks);

        mbMainMenu.add(mnuAdministrativeOptions);

        mnuAccount.setBackground(new java.awt.Color(0, 0, 0));
        mnuAccount.setForeground(new java.awt.Color(255, 255, 0));
        mnuAccount.setText("Account");

        mnuItemAccountManagement.setText("Account Management");
        mnuItemAccountManagement.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuItemAccountManagementActionPerformed(evt);
            }
        });
        mnuAccount.add(mnuItemAccountManagement);

        mbMainMenu.add(mnuAccount);

        mnuTasksAndAccountManagement.setBackground(new java.awt.Color(0, 0, 0));
        mnuTasksAndAccountManagement.setForeground(new java.awt.Color(255, 255, 0));
        mnuTasksAndAccountManagement.setText("Tasks And Account Management");

        mnuItemViewTasks.setText("View Tasks");
        mnuItemViewTasks.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuItemViewTasksActionPerformed(evt);
            }
        });
        mnuTasksAndAccountManagement.add(mnuItemViewTasks);

        mnuItemSecurityAccountManagement.setText("Account Management");
        mnuItemSecurityAccountManagement.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuItemSecurityAccountManagementActionPerformed(evt);
            }
        });
        mnuTasksAndAccountManagement.add(mnuItemSecurityAccountManagement);

        mnuItemClientComplaints.setText("View Complaints of Clients");
        mnuItemClientComplaints.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuItemClientComplaintsActionPerformed(evt);
            }
        });
        mnuTasksAndAccountManagement.add(mnuItemClientComplaints);

        mbMainMenu.add(mnuTasksAndAccountManagement);

        mnuComplaints.setBackground(new java.awt.Color(0, 0, 0));
        mnuComplaints.setForeground(new java.awt.Color(255, 255, 0));
        mnuComplaints.setText("Complaints");

        mnuItemComplaintRegisteration.setText("Register a Complaint");
        mnuItemComplaintRegisteration.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuItemComplaintRegisterationActionPerformed(evt);
            }
        });
        mnuComplaints.add(mnuItemComplaintRegisteration);

        mbMainMenu.add(mnuComplaints);

        mnuReports.setBackground(new java.awt.Color(0, 0, 0));
        mnuReports.setForeground(new java.awt.Color(255, 255, 0));
        mnuReports.setText("Reports");

        mnuItemStuffDetailsReport.setText("Staff Details Report");
        mnuItemStuffDetailsReport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuItemStuffDetailsReportActionPerformed(evt);
            }
        });
        mnuReports.add(mnuItemStuffDetailsReport);

        mnuItemComplaintsReport.setText("Complaints Report");
        mnuItemComplaintsReport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuItemComplaintsReportActionPerformed(evt);
            }
        });
        mnuReports.add(mnuItemComplaintsReport);

        mbMainMenu.add(mnuReports);

        setJMenuBar(mbMainMenu);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(dsktpMain)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(dsktpMain)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void mnuItemLogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuItemLogoutActionPerformed
        frmlogin.setExtendedState(MAXIMIZED_BOTH);
        frmlogin.setTitle("ABC Security Company Login");
        frmlogin.mSetUserID(null);
        frmlogin.show();
        this.hide();
    }//GEN-LAST:event_mnuItemLogoutActionPerformed

    private void mnuItemExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuItemExitActionPerformed
        System.exit(0);
    }//GEN-LAST:event_mnuItemExitActionPerformed

    private void mnuItemRegisterAdministratorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuItemRegisterAdministratorActionPerformed
        frmRegistrations frmRegisterAdmin = new frmRegistrations("admin");
        frmRegisterAdmin.mSetUserLevel("admin");
        frmRegisterAdmin.setTitle("Register Administrator Account");
        dsktpMain.add(frmRegisterAdmin);
        frmRegisterAdmin.setLocation(400, 100);
        frmRegisterAdmin.setClosable(true);
        frmRegisterAdmin.setVisible(true);
    }//GEN-LAST:event_mnuItemRegisterAdministratorActionPerformed

    private void mnuItemRegisterSecurityGuardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuItemRegisterSecurityGuardActionPerformed
        frmRegistrations frmRegisterSecurity = new frmRegistrations("security");
        frmRegisterSecurity.mSetUserLevel("security");
        frmRegisterSecurity.setTitle("Register Security Guard Account");
        dsktpMain.add(frmRegisterSecurity);
        frmRegisterSecurity.setLocation(400, 100);
        frmRegisterSecurity.setClosable(true);
        frmRegisterSecurity.setVisible(true);
    }//GEN-LAST:event_mnuItemRegisterSecurityGuardActionPerformed

    private void mnuItemRegisterClientActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuItemRegisterClientActionPerformed
        frmRegistrations frmRegisterClient = new frmRegistrations("client");
        frmRegisterClient.mSetUserLevel("client");
        frmRegisterClient.setTitle("Register Client Account");
        dsktpMain.add(frmRegisterClient);
        frmRegisterClient.setLocation(400, 100);
        frmRegisterClient.setClosable(true);
        frmRegisterClient.setVisible(true);
    }//GEN-LAST:event_mnuItemRegisterClientActionPerformed

    private void mnuItemAssignTasksActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuItemAssignTasksActionPerformed
        frmAssignTasks frmTasks = new frmAssignTasks();
        dsktpMain.add(frmTasks);
        frmTasks.setTitle("Assign Tasks");
        frmTasks.setLocation(400, 50);
        frmTasks.setClosable(true);
        frmTasks.setVisible(true);
    }//GEN-LAST:event_mnuItemAssignTasksActionPerformed

    private void mnuItemAdminViewTasksActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuItemAdminViewTasksActionPerformed
        frmAssignTasks frmViewTasks = new frmAssignTasks();
        dsktpMain.add(frmViewTasks);
        frmViewTasks.setTitle("All Assigned Tasks");
        frmViewTasks.setLocation(400, 50);
        frmViewTasks.setClosable(true);
        frmViewTasks.mAdminViewTasks();
        frmViewTasks.setVisible(true);
    }//GEN-LAST:event_mnuItemAdminViewTasksActionPerformed

    private void mnuItemViewTasksActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuItemViewTasksActionPerformed
        frmAssignTasks frmViewAssignedTasks = new frmAssignTasks();
        dsktpMain.add(frmViewAssignedTasks);
        frmViewAssignedTasks.setTitle("Your Assigned Tasks for Today");
        frmViewAssignedTasks.setLocation(400, 50);
        frmViewAssignedTasks.setClosable(true);
        frmViewAssignedTasks.mSecurityViewTasks();
        frmViewAssignedTasks.setVisible(true);
    }//GEN-LAST:event_mnuItemViewTasksActionPerformed

    private void mnuItemAccountManagementActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuItemAccountManagementActionPerformed
        frmAccountsManagement frmManageAccount = new frmAccountsManagement();
        dsktpMain.add(frmManageAccount);
        frmManageAccount.setTitle("Manage your account");
        frmManageAccount.setLocation(400, 50);
        frmManageAccount.setClosable(true);
        switch(strUserIdentifier)
        {
            case "admin":
                frmManageAccount.mSetAccountManagingUser("admin");
                break;
            case "client":
                frmManageAccount.mSetAccountManagingUser("client");
                break;
        }
        frmManageAccount.setVisible(true);
    }//GEN-LAST:event_mnuItemAccountManagementActionPerformed

    private void mnuItemSecurityAccountManagementActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuItemSecurityAccountManagementActionPerformed
        frmAccountsManagement frmManageSecurityGuardAccount = new frmAccountsManagement();
        dsktpMain.add(frmManageSecurityGuardAccount);
        frmManageSecurityGuardAccount.setTitle("Manage your account");
        frmManageSecurityGuardAccount.setLocation(400, 50);
        frmManageSecurityGuardAccount.setClosable(true);
        frmManageSecurityGuardAccount.mSetAccountManagingUser("security");
        frmManageSecurityGuardAccount.setVisible(true);
    }//GEN-LAST:event_mnuItemSecurityAccountManagementActionPerformed

    private void mnuItemComplaintRegisterationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuItemComplaintRegisterationActionPerformed
        frmComplaintReport frmReportComplaint = new frmComplaintReport();
        dsktpMain.add(frmReportComplaint);
        frmReportComplaint.setTitle("Report a Complaint");
        frmReportComplaint.setLocation(400, 50);
        frmReportComplaint.setClosable(true);
        frmReportComplaint.setVisible(true);
    }//GEN-LAST:event_mnuItemComplaintRegisterationActionPerformed

    private void mnuItemClientComplaintsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuItemClientComplaintsActionPerformed
        frmComplaintReport frmViewClientsComplaints = new frmComplaintReport();
        dsktpMain.add(frmViewClientsComplaints);
        frmViewClientsComplaints.setTitle("View Clients Complaints");
        frmViewClientsComplaints.mViewClientsComplaints();
        frmViewClientsComplaints.setLocation(400, 50);
        frmViewClientsComplaints.setClosable(true);
        frmViewClientsComplaints.setVisible(true);
    }//GEN-LAST:event_mnuItemClientComplaintsActionPerformed

    private void mnuItemStuffDetailsReportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuItemStuffDetailsReportActionPerformed
        frmReports frmViewStuffDetailsReport = new frmReports();
        dsktpMain.add(frmViewStuffDetailsReport);
        frmViewStuffDetailsReport.setTitle("Staff Report");
        frmViewStuffDetailsReport.mViewStaffReport();
        frmViewStuffDetailsReport.setLocation(400, 50);
        frmViewStuffDetailsReport.setClosable(true);
        frmViewStuffDetailsReport.setVisible(true);
    }//GEN-LAST:event_mnuItemStuffDetailsReportActionPerformed

    private void mnuItemComplaintsReportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuItemComplaintsReportActionPerformed
        frmReports frmViewComplaintsReport = new frmReports();
        dsktpMain.add(frmViewComplaintsReport);
        frmViewComplaintsReport.setTitle("Complaints Report");
        frmViewComplaintsReport.mViewComplaintsReport();
        frmViewComplaintsReport.setLocation(400, 50);
        frmViewComplaintsReport.setClosable(true);
        frmViewComplaintsReport.setVisible(true);
    }//GEN-LAST:event_mnuItemComplaintsReportActionPerformed

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
            java.util.logging.Logger.getLogger(frmMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frmMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frmMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frmMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new frmMain().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JDesktopPane dsktpMain;
    private javax.swing.JMenuBar mbMainMenu;
    private javax.swing.JMenu mnuAccount;
    private javax.swing.JMenu mnuAdministrativeOptions;
    private javax.swing.JMenu mnuComplaints;
    private javax.swing.JMenu mnuFile;
    private javax.swing.JMenuItem mnuItemAccountManagement;
    private javax.swing.JMenuItem mnuItemAdminViewTasks;
    private javax.swing.JMenuItem mnuItemAssignTasks;
    private javax.swing.JMenuItem mnuItemClientComplaints;
    private javax.swing.JMenuItem mnuItemComplaintRegisteration;
    private javax.swing.JMenuItem mnuItemComplaintsReport;
    private javax.swing.JMenuItem mnuItemExit;
    private javax.swing.JMenuItem mnuItemLogout;
    private javax.swing.JMenuItem mnuItemRegisterAdministrator;
    private javax.swing.JMenuItem mnuItemRegisterClient;
    private javax.swing.JMenuItem mnuItemRegisterSecurityGuard;
    private javax.swing.JMenuItem mnuItemSecurityAccountManagement;
    private javax.swing.JMenuItem mnuItemStuffDetailsReport;
    private javax.swing.JMenuItem mnuItemViewTasks;
    private javax.swing.JMenu mnuReports;
    private javax.swing.JMenu mnuTasksAndAccountManagement;
    // End of variables declaration//GEN-END:variables
}
