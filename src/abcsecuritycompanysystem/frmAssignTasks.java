/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package abcsecuritycompanysystem;

import java.awt.GridLayout;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

/**
 *
 * @author Sanele
 */
public class frmAssignTasks extends javax.swing.JInternalFrame {

    /**
     * Creates new form frmTasks
     */
    public frmAssignTasks() {
        initComponents();
        frmRegistrations frmGetCurrentDate = new frmRegistrations("");
        txtTaskDate.setText(frmGetCurrentDate.mTodayDate());
        mDefaultFrmAssignTasksGUIControls();
        mLoadToComboBox(mLoadSecurityNamesQuery(), cboSecurityNames);
    }
    
    //A method that determines the behaviour of
    //this form's default GUI controls.
    private void mDefaultFrmAssignTasksGUIControls()
    {
        cboTasksDescriptions.setEnabled(false);
        txtTaskDescription.setEditable(false);
        txtTaskDate.setEditable(false);
        cboSecurityNames.setEditable(false);
        btnLoadTasks.setEnabled(true);
        btnAssignTask.setEnabled(true);
        btnUpdateTask.setEnabled(false);
        btnDeleteTask.setEnabled(false);
    }
    
    //A method that determines the behaviour of this form's
    //GUI controls when the Load Tasks button is selected.
    private void mLoadTasksGUIControls()
    {
        cboTasksDescriptions.setEnabled(true);
        txtTaskDescription.setEditable(false);
        txtTaskDate.setEditable(false);
        cboSecurityNames.setEnabled(false);
        btnLoadTasks.setEnabled(false);
        btnAssignTask.setEnabled(false);
        btnUpdateTask.setEnabled(true);
        btnDeleteTask.setEnabled(true);
    }
    
    //A method that determines the behaviour of this form's
    //GUI controls when the Assign Task button is selected.
    private void mAssignTaskGUIControls()
    {
        cboTasksDescriptions.setEnabled(false);
        txtTaskDescription.setEditable(true);
        txtTaskDate.setEditable(false);
        cboSecurityNames.setEnabled(true);
        btnLoadTasks.setEnabled(false);
        btnAssignTask.setEnabled(true);
        btnUpdateTask.setEnabled(false);
        btnDeleteTask.setEnabled(false);
    }
    
    //A method that determines the behaviour of this form's
    //GUI controls when the Update Task button is selected.
    private void mUpdateTaskGUIControls()
    {
        cboTasksDescriptions.setEnabled(false);
        txtTaskDescription.setEditable(true);
        txtTaskDate.setEditable(false);
        cboSecurityNames.setEnabled(true);
        btnLoadTasks.setEnabled(false);
        btnAssignTask.setEnabled(false);
        btnUpdateTask.setEnabled(true);
        btnDeleteTask.setEnabled(false);
    }
    
    //A method that determines the behaviour of this form's
    //GUI controls when the Delete Task button is selected.
    private void mDeleteTaskGUIControls()
    {
        mDefaultFrmAssignTasksGUIControls();
    }
    
    //Declaration of String variables to hold task details.
    String strDescription, strDate, strSecurityGuard;
    //Declaration of an integer variable to hold task ID.
    int id;
    
    /*Instantiation of object of class
    * clsDatabaseConnection, class clsSQLMethods,
    * and class frmRegisterAccounts.
    */
    clsDatabaseConnection clsConnect = new clsDatabaseConnection();
    clsQueryingMethods clsSQLMethods = new clsQueryingMethods();
    frmRegistrations frmRegisterAccount = new frmRegistrations("");
    frmLogin frmlogin = new frmLogin();
        
    //A method that gets values passed to the
    //GUI input fields.
    private void mGetGUIValues()
    {
        strDescription = txtTaskDescription.getText();
        strDate = txtTaskDate.getText();
        strSecurityGuard = cboSecurityNames.getSelectedItem().toString();
    }
    
    //A method that fetches data/values from the
    //databse and set values to the GUI input fields.
    private void mSetGUIValues()
    {
        String[] arrTaskDetails = clsSQLMethods.mFetchRecordDetails(mFetchCboSelectedTaskDetails());
        txtTaskDescription.setText(arrTaskDetails[1]);
        txtTaskDate.setText(arrTaskDetails[2]);
        String[] arrNameToSelect = clsSQLMethods.mFetchRecordDetails("SELECT Name FROM tblUsers WHERE ID='"+arrTaskDetails[3]+"'");
        cboSecurityNames.setSelectedItem(arrNameToSelect[1]);
    }
    
    //A method to clear combo box values.
    private void mClearComboBox()
    {
        String[] arrArray = new String[0];
        javax.swing.DefaultComboBoxModel model = new javax.swing.DefaultComboBoxModel(arrArray);
        cboTasksDescriptions.setModel(model);
    }
    
    //A method that clears GUI input fields.
    private void mClearGUIControls()
    {
        txtTaskDescription.setText(null);
        mClearComboBox();
    }
    
    //A method that returns as string a query that get ID
    //from the database of a combo box selected Security.
    private String mGetCboSelectedSecurityGuardID()
    {
        return "SELECT ID FROM tblUsers WHERE Name ='"+cboSecurityNames.getSelectedItem().toString()+"'";
    }
    
    //A method that returns as string a query to insert to the database
    //a task assignment.
    private String mAddTaskDetailsQuery()
    {
        return "INSERT INTO tblTasks" + "(Description, DateIssued, IssuedTo, IssuedBy)"+
                "VALUES('"+ strDescription+ "','" + strDate + "','" + clsSQLMethods.mGetID(mGetCboSelectedSecurityGuardID()) +
                "','"+frmlogin.mGetUserID()+"')";
    }
    
    //A method that returns as string a query that
    //gets from the database all task descriptions.
    private String mLoadTasksDescriptionsQuery()
    {
        return "SELECT Description FROM tblTasks";
    }
    
    //A method that returns as string a query that
    //gets from the database all names of security personnel.
    private String mLoadSecurityNamesQuery()
    {
        return "SELECT Name FROM tblUsers WHERE Level='security'";
    }
    
    //A method that connects to the database and load data to a 
    //combo box as values.
    private void mLoadToComboBox(String strQuery, JComboBox cbo)
    {
        try
        {
            try (Statement stStatement = clsConnect.mConnectToDatabaseABCSecurity().prepareStatement(strQuery)) {
                stStatement.execute(strQuery);
                try (ResultSet rs = stStatement.getResultSet()) {
                    while(rs.next())
                    {
                        cbo.addItem(rs.getString(1));
                    }
                    stStatement.close();
                    rs.close();
                }
            }
        }
        catch(SQLException e)
        {
            JOptionPane.showMessageDialog(null,"A technical error has been encountered\n"+e.getMessage());
        }
    }
    
    //A method that returns as string a query that gets
    //from the database an ID of a selected description.
    private String mGetCboSelectedTaskID()
    {
        return "SELECT ID FROM tblTasks WHERE Description ='"
                +cboTasksDescriptions.getSelectedItem().toString()+"'";
    }
    
    //A method that returns as string a query that gets from
    //the database details of a combo selected task description.
    private String mFetchCboSelectedTaskDetails()
    {
        return "SELECT Description, DateIssued, IssuedTo FROM tblTasks WHERE Description='"
                +cboTasksDescriptions.getSelectedItem().toString()+"'";
    }
    
    //A method that returns as a string a query that updates data in the database.
    private String mUpdateCboSelectedSecurityTaskQuery()
    {
        return "UPDATE tblTasks SET Description ='"+strDescription+"', DateIssued='"+
                strDate+"', IssuedTo='"+clsSQLMethods.mGetID(mGetCboSelectedSecurityGuardID())+
                "' WHERE ID='"+clsSQLMethods.mGetID(mGetCboSelectedTaskID())+"'";        
    }
    
    //A method that returns as string a query that
    //Delete a combo selected task.
    private String mDeleteCboSelectedTaskQuery()
    {
        return "DELETE FROM tblTasks WHERE ID='"+clsSQLMethods.mGetID(mGetCboSelectedTaskID())+"'";
    }
    
    //A method that returns as string a query that 
    //allows security personnel to view tasks 
    //assigned to them for the day.
    private String mViewIssuedTasksSecurityQuery()
    {
        return "SELECT Description, DateIssued FROM tblTasks WHERE IssuedTo='"+
                frmlogin.mGetUserID()+"' AND DateIssued='"+frmRegisterAccount.mTodayDate()+"'";
    }
    
    //A method that returns as string a query that 
    //allows administrative personnel to view all tasks 
    //ever issued.
    private String mViewIssuedTasksAdminQuery()
    {
        return "SELECT Description, DateIssued, IssuedTo, IssuedBy FROM tblTasks";
    }
    
    //A method that allows viewing of tasks in tabular format.
    private void mViewTasks(String strQuery)
    {
        try
        {
            try (Statement stSQLQuery = clsConnect.mConnectToDatabaseABCSecurity().prepareStatement(strQuery)) {
                ResultSet rs = stSQLQuery.executeQuery(strQuery);
                ResultSetMetaData rsmt = rs.getMetaData();
                int intColumnCount = rsmt.getColumnCount();
                Vector vColumn = new Vector(intColumnCount);
                for(int i = 1; i <= intColumnCount; i++)
                {
                    vColumn.add(rsmt.getColumnName(i));
                }   Vector vData = new Vector();
                Vector vRow = new Vector();
                while(rs.next())
                {
                    vRow = new Vector(intColumnCount);
                    for(int i = 1; i <= intColumnCount; i++)
                    {
                        vRow.add(rs.getObject(i));
                    }
                    vData.add(vRow);
                }   JPanel pnlTable = new JPanel();
                JTable tblMembers = new JTable(vData, vColumn);
                tblMembers.setFillsViewportHeight(true);
                JScrollPane jspMemberPane = new JScrollPane(tblMembers);
                tblMembers.setVisible(true);
                tblMembers.validate();
                pnlTable.setLayout(new GridLayout(1,0));
                pnlTable.add(jspMemberPane);
                this.setResizable(true);
                this.setContentPane(pnlTable);
                stSQLQuery.close();
                rs.close();
            }
        }
        catch(SQLException e)
        {
            JOptionPane.showMessageDialog(null, "Technical Error, table cannot be displayed\n"+e.getMessage());
        }
    }
    
    //A method that invokes methods that make it possible
    //for administrative personnel to view tasks.
    public void mAdminViewTasks()
    {
        mViewTasks(mViewIssuedTasksAdminQuery());
    }
    
    //A method that invokes methods that make it possible
    //for security personnel to view tasks allocated to them.
    public void mSecurityViewTasks()
    {
        mViewTasks(mViewIssuedTasksSecurityQuery());
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        dsktpTasks = new javax.swing.JDesktopPane();
        lblHeading = new javax.swing.JLabel();
        lblIcon = new javax.swing.JLabel();
        lblTaskDescription = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtTaskDescription = new javax.swing.JTextPane();
        lblDate = new javax.swing.JLabel();
        txtTaskDate = new javax.swing.JTextField();
        lblTaskFor = new javax.swing.JLabel();
        cboSecurityNames = new javax.swing.JComboBox<>();
        btnAssignTask = new javax.swing.JButton();
        btnLoadTasks = new javax.swing.JButton();
        btnUpdateTask = new javax.swing.JButton();
        btnDeleteTask = new javax.swing.JButton();
        cboTasksDescriptions = new javax.swing.JComboBox<>();

        dsktpTasks.setBackground(new java.awt.Color(153, 153, 255));

        lblHeading.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblHeading.setText("Tasks Assignment");

        lblIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/abcsecuritycompanysystem/Icon.jpeg"))); // NOI18N

        lblTaskDescription.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblTaskDescription.setText("Description");

        jScrollPane1.setViewportView(txtTaskDescription);

        lblDate.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblDate.setText("Date");

        lblTaskFor.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblTaskFor.setText("Task For");

        btnAssignTask.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnAssignTask.setText("Assign Task");
        btnAssignTask.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnAssignTask.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAssignTaskActionPerformed(evt);
            }
        });

        btnLoadTasks.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnLoadTasks.setText("Load Tasks");
        btnLoadTasks.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnLoadTasks.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLoadTasksActionPerformed(evt);
            }
        });

        btnUpdateTask.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnUpdateTask.setText("Update Task");
        btnUpdateTask.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnUpdateTask.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateTaskActionPerformed(evt);
            }
        });

        btnDeleteTask.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnDeleteTask.setText("Delete Task");
        btnDeleteTask.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnDeleteTask.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteTaskActionPerformed(evt);
            }
        });

        dsktpTasks.setLayer(lblHeading, javax.swing.JLayeredPane.DEFAULT_LAYER);
        dsktpTasks.setLayer(lblIcon, javax.swing.JLayeredPane.DEFAULT_LAYER);
        dsktpTasks.setLayer(lblTaskDescription, javax.swing.JLayeredPane.DEFAULT_LAYER);
        dsktpTasks.setLayer(jScrollPane1, javax.swing.JLayeredPane.DEFAULT_LAYER);
        dsktpTasks.setLayer(lblDate, javax.swing.JLayeredPane.DEFAULT_LAYER);
        dsktpTasks.setLayer(txtTaskDate, javax.swing.JLayeredPane.DEFAULT_LAYER);
        dsktpTasks.setLayer(lblTaskFor, javax.swing.JLayeredPane.DEFAULT_LAYER);
        dsktpTasks.setLayer(cboSecurityNames, javax.swing.JLayeredPane.DEFAULT_LAYER);
        dsktpTasks.setLayer(btnAssignTask, javax.swing.JLayeredPane.DEFAULT_LAYER);
        dsktpTasks.setLayer(btnLoadTasks, javax.swing.JLayeredPane.DEFAULT_LAYER);
        dsktpTasks.setLayer(btnUpdateTask, javax.swing.JLayeredPane.DEFAULT_LAYER);
        dsktpTasks.setLayer(btnDeleteTask, javax.swing.JLayeredPane.DEFAULT_LAYER);
        dsktpTasks.setLayer(cboTasksDescriptions, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout dsktpTasksLayout = new javax.swing.GroupLayout(dsktpTasks);
        dsktpTasks.setLayout(dsktpTasksLayout);
        dsktpTasksLayout.setHorizontalGroup(
            dsktpTasksLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dsktpTasksLayout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addGroup(dsktpTasksLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(cboTasksDescriptions, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, dsktpTasksLayout.createSequentialGroup()
                        .addComponent(lblTaskFor)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(cboSecurityNames, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(dsktpTasksLayout.createSequentialGroup()
                        .addGroup(dsktpTasksLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblTaskDescription)
                            .addComponent(lblDate))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(dsktpTasksLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(txtTaskDate)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)))
                    .addGroup(dsktpTasksLayout.createSequentialGroup()
                        .addComponent(btnLoadTasks)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 31, Short.MAX_VALUE)
                        .addComponent(btnAssignTask)
                        .addGap(44, 44, 44)
                        .addComponent(btnUpdateTask)
                        .addGap(31, 31, 31)
                        .addComponent(btnDeleteTask))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, dsktpTasksLayout.createSequentialGroup()
                        .addComponent(lblIcon, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(27, 27, 27)
                        .addComponent(lblHeading)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(45, 45, 45))
        );
        dsktpTasksLayout.setVerticalGroup(
            dsktpTasksLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dsktpTasksLayout.createSequentialGroup()
                .addGroup(dsktpTasksLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(dsktpTasksLayout.createSequentialGroup()
                        .addGap(62, 62, 62)
                        .addComponent(lblHeading))
                    .addGroup(dsktpTasksLayout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(lblIcon, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(29, 29, 29)
                .addComponent(cboTasksDescriptions, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addGroup(dsktpTasksLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblTaskDescription)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(40, 40, 40)
                .addGroup(dsktpTasksLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblDate)
                    .addComponent(txtTaskDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(54, 54, 54)
                .addGroup(dsktpTasksLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTaskFor)
                    .addComponent(cboSecurityNames, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 49, Short.MAX_VALUE)
                .addGroup(dsktpTasksLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAssignTask)
                    .addComponent(btnUpdateTask)
                    .addComponent(btnDeleteTask)
                    .addComponent(btnLoadTasks))
                .addGap(30, 30, 30))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(dsktpTasks)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(dsktpTasks)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnLoadTasksActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLoadTasksActionPerformed
        mLoadTasksGUIControls();
        mLoadToComboBox(mLoadTasksDescriptionsQuery(), cboTasksDescriptions);
    }//GEN-LAST:event_btnLoadTasksActionPerformed

    private void btnAssignTaskActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAssignTaskActionPerformed
        if(btnAssignTask.getText().equals("Assign Task")){
            mAssignTaskGUIControls();
            btnAssignTask.setText("Save Task");
            btnUpdateTask.setText("Cancel Task");
            btnUpdateTask.setEnabled(true);
        }else if(btnAssignTask.getText().equals("Save Task")){
            if(txtTaskDescription.getText().equals("")){
                JOptionPane.showMessageDialog(null, "A task must have a description.");
                txtTaskDescription.requestFocusInWindow();
            }else{
                mGetGUIValues();
                clsSQLMethods.mCreateRecord(mAddTaskDetailsQuery(), btnAssignTask.getText());
                btnAssignTask.setText("Assign Task");
                btnUpdateTask.setText("Update Task");
                mClearGUIControls();
                mDefaultFrmAssignTasksGUIControls();
            }
        }else if(btnAssignTask.getText().equals("Cancel Task")){
            txtTaskDescription.setText("");
            btnAssignTask.setText("Assign Task");
            btnUpdateTask.setText("Update Task");
            mClearGUIControls();
            mDefaultFrmAssignTasksGUIControls();
        }
    }//GEN-LAST:event_btnAssignTaskActionPerformed

    private void btnUpdateTaskActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateTaskActionPerformed
        if(btnUpdateTask.getText().equals("Update Task")){
            mUpdateTaskGUIControls();
            mSetGUIValues();
            btnUpdateTask.setText("Save Update");
            btnAssignTask.setText("Cancel Task");
            btnAssignTask.setEnabled(true);
        }else if(btnUpdateTask.getText().equals("Save Update")){
            if(txtTaskDescription.getText().equals("")){
                JOptionPane.showMessageDialog(null, "A task must have a description.");
                txtTaskDescription.requestFocusInWindow();
            }else{
                mGetGUIValues();
                clsSQLMethods.mUpdateRecordDetails(mUpdateCboSelectedSecurityTaskQuery());
                btnUpdateTask.setText("Update Task");
                btnAssignTask.setText("Assign Task");
                mClearGUIControls();
                mDefaultFrmAssignTasksGUIControls();
            }
        }else if(btnUpdateTask.getText().equals("Cancel Task")){
            btnUpdateTask.setText("Update Task");
            btnAssignTask.setText("Assign Task");
            mClearGUIControls();
            mDefaultFrmAssignTasksGUIControls();
            txtTaskDescription.setText("");
        }
    }//GEN-LAST:event_btnUpdateTaskActionPerformed

    private void btnDeleteTaskActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteTaskActionPerformed
        int intOption = JOptionPane.showConfirmDialog(null, "Do you want to Delete Task?", "Delete Task", JOptionPane.YES_OPTION);
        if(intOption == 0){
            clsSQLMethods.mDeleteRecordDetails(mDeleteCboSelectedTaskQuery());
        }
        mClearGUIControls();
        mDeleteTaskGUIControls();
    }//GEN-LAST:event_btnDeleteTaskActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAssignTask;
    private javax.swing.JButton btnDeleteTask;
    private javax.swing.JButton btnLoadTasks;
    private javax.swing.JButton btnUpdateTask;
    private javax.swing.JComboBox<String> cboSecurityNames;
    private javax.swing.JComboBox<String> cboTasksDescriptions;
    private javax.swing.JDesktopPane dsktpTasks;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblDate;
    private javax.swing.JLabel lblHeading;
    private javax.swing.JLabel lblIcon;
    private javax.swing.JLabel lblTaskDescription;
    private javax.swing.JLabel lblTaskFor;
    private javax.swing.JTextField txtTaskDate;
    private javax.swing.JTextPane txtTaskDescription;
    // End of variables declaration//GEN-END:variables
}
