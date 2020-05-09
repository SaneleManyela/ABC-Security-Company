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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

/**
 *
 * @author Sanele
 */
public class frmComplaintReport extends javax.swing.JInternalFrame {

    /**
     * Creates new form frmRegisterComplaints
     */
    public frmComplaintReport() {
        initComponents();
        mSetCboNumberOfPeopleInvolved();
        mDefaultGUIControls();
    }
    
    //A method that defines the behaviour of
    //this form's default GUI.
    private void mDefaultGUIControls()
    {
        txtDescription.setEditable(false);
        txtAreaOfIncident.setEditable(false);
        cboNumberOfPeopleInvolved.setEnabled(false);
        btnRegisterComplaint.setEnabled(true);
        btnUpdateLastComplaint.setEnabled(true);
        btnClose.setEnabled(true);
    }
    
    //A method that defines the behaviour of this
    //form's GUI when the button Register is selected.
    private void mRegisterComplaintGUIControls()
    {
        txtDescription.setEditable(true);
        txtAreaOfIncident.setEditable(true);
        cboNumberOfPeopleInvolved.setEnabled(true);
        btnRegisterComplaint.setEnabled(true);
        btnUpdateLastComplaint.setEnabled(false);
        btnClose.setEnabled(true);
    }
    
    //A method that defines the behaviour of this form's
    //GUI when the Edit Last Complaint button is selected.
    private void mUpdateLastComplaintGUIControls()
    {
        txtDescription.setEditable(true);
        txtAreaOfIncident.setEditable(true);
        cboNumberOfPeopleInvolved.setEnabled(true);
        btnRegisterComplaint.setEnabled(false);
        btnUpdateLastComplaint.setEnabled(true);
        btnClose.setEnabled(true);
    }
    
    //Instantiaton of object of class clsSQLMethods and class
    //clsDatabaseConnection.
    clsQueryingMethods clsQueryingMethods = new clsQueryingMethods();
    clsDatabaseConnection clsConnect = new clsDatabaseConnection();
    frmLogin frmlogin = new frmLogin();
    
    //Declaration of string variables to hold complaint registeration details.
    String strComplaintDescription, strAreaOfIncident, strNumberOfPeopleInvolved, strComplaintID;
    
    //A method that queries the database to fetch data that will be
    //assigned as values for variables declared above.
    private void mAssignVariables()
    {
        String[] arrComplaintsByUserIDs = clsQueryingMethods.mFetchRecordDetails(
                "SELECT ID FROM tblComplaints WHERE ReportedBy='"+frmlogin.mGetUserID()+"'");
        strComplaintID = arrComplaintsByUserIDs[arrComplaintsByUserIDs.length - 1];
        
        String[] arrLastComplaintsDetails = clsQueryingMethods.mFetchRecordDetails(
                "SELECT Description, AreaOfIncident, NumberOfPeopleInvolved FROM tblComplaints WHERE ID ='"
                +strComplaintID+"'");
        strComplaintDescription = arrLastComplaintsDetails[1];
        strAreaOfIncident = arrLastComplaintsDetails[2];
        strNumberOfPeopleInvolved = arrLastComplaintsDetails[3];
    }
    
    //A method that set values to GUI input fields.
    private void mSetValuesToGUI()
    {
        txtDescription.setText(strComplaintDescription);
        txtAreaOfIncident.setText(strAreaOfIncident);
        cboNumberOfPeopleInvolved.setSelectedItem(strNumberOfPeopleInvolved);
    }
    
    //A method that clears combo box values.
    private void mClearCboNumberOfPeopleInvolved()
    {
        String[] arrArray = new String[0];
        javax.swing.DefaultComboBoxModel model = new javax.swing.DefaultComboBoxModel(arrArray);
        cboNumberOfPeopleInvolved.setModel(model);
    }
    
    //A method that clears GUI input fields.
    private void mClearGUIControls()
    {
        txtDescription.setText(null);
        txtAreaOfIncident.setText(null);
        mClearCboNumberOfPeopleInvolved();
    }
    
    //A method that populates a combo box with values.
    private void mSetCboNumberOfPeopleInvolved()
    {
        for(int i = 1; i <= 50; i++){
            cboNumberOfPeopleInvolved.addItem(String.valueOf(i));
        }
    }
    
    //A method that returns as string a value of
    //current date and time queried from the database.
    private String mGetDateTime()
    {
        String[] arrDateTime = clsQueryingMethods.mFetchRecordDetails("SELECT NOW()");
        return arrDateTime[1];
    }
    
    //A method that returns as string a query that
    //records complaint details to the database.
    private String mRegisterAComplaintQuery()
    {
        return "INSERT INTO tblComplaints" + "(Description, DateAndTime, AreaOfIncident, NumberOfPeopleInvolved, ReportedBy)"+
                "VALUES('"+ txtDescription.getText() + "','"+mGetDateTime()+"','" + txtAreaOfIncident.getText() +"','"+
                cboNumberOfPeopleInvolved.getSelectedItem().toString()+"','"+frmlogin.mGetUserID()+"')";
    }
    
    //A method that returns as string a query that updates
    //details of the last complaint recorded by the current user.
    private String mUpdateLastRegisteredComplaintQuery()
    {
        return "UPDATE tblComplaints SET Description ='"+strComplaintDescription+"', AreaOfIncident='"+
                strAreaOfIncident+"', NumberOfPeopleInvolved='"+strNumberOfPeopleInvolved+"' WHERE ID ='"+strComplaintID+"'";        
    }
    
    //A method that returns as string a query that views and
    //used to display complaints registered in less than 1 day
    //from the current date and time.
    private String mViewRegisteredComplaintsQuery()
    {
        return "SELECT Description, AreaOfIncident, NumberOfPeopleInvolved, ReportedBy FROM tblComplaints "
                + "WHERE datediff(NOW(), DateAndTime) < 1";
    }
    
    //A method that displays registered complaints in a tabular format.
    private void mViewRegisteredComplaints(String strQuery)
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
        catch(SQLException eX)
        {
            JOptionPane.showMessageDialog(null, "Technical Error, table cannot be displayed"+""+eX);
        }
    }
    
    //A public method that invokes methods necessary
    //for the viewing of the complaints registered by clients.
    public void mViewClientsComplaints()
    {
        mViewRegisteredComplaints(mViewRegisteredComplaintsQuery());
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        dskComplaintReport = new javax.swing.JDesktopPane();
        lblIcon = new javax.swing.JLabel();
        lblHeading = new javax.swing.JLabel();
        lblComplaintTypeAndDescription = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtDescription = new javax.swing.JTextArea();
        lblAreaOfIncident = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtAreaOfIncident = new javax.swing.JTextArea();
        lblNumberOfPeopleInvolved = new javax.swing.JLabel();
        cboNumberOfPeopleInvolved = new javax.swing.JComboBox<>();
        btnRegisterComplaint = new javax.swing.JButton();
        btnUpdateLastComplaint = new javax.swing.JButton();
        btnClose = new javax.swing.JButton();

        dskComplaintReport.setBackground(new java.awt.Color(153, 153, 255));

        lblIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/abcsecuritycompanysystem/Icon.jpeg"))); // NOI18N

        lblHeading.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblHeading.setText("Register A Complaint");

        lblComplaintTypeAndDescription.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblComplaintTypeAndDescription.setText("Complaint Type and Description");

        txtDescription.setColumns(20);
        txtDescription.setRows(5);
        jScrollPane1.setViewportView(txtDescription);

        lblAreaOfIncident.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblAreaOfIncident.setText("Area Of Incident");

        txtAreaOfIncident.setColumns(20);
        txtAreaOfIncident.setRows(5);
        jScrollPane2.setViewportView(txtAreaOfIncident);

        lblNumberOfPeopleInvolved.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblNumberOfPeopleInvolved.setText("Number Of People Involved");

        btnRegisterComplaint.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnRegisterComplaint.setText("Register");
        btnRegisterComplaint.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnRegisterComplaint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegisterComplaintActionPerformed(evt);
            }
        });

        btnUpdateLastComplaint.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnUpdateLastComplaint.setText("Edit Last Complaint");
        btnUpdateLastComplaint.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnUpdateLastComplaint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateLastComplaintActionPerformed(evt);
            }
        });

        btnClose.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnClose.setText("Close");
        btnClose.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCloseActionPerformed(evt);
            }
        });

        dskComplaintReport.setLayer(lblIcon, javax.swing.JLayeredPane.DEFAULT_LAYER);
        dskComplaintReport.setLayer(lblHeading, javax.swing.JLayeredPane.DEFAULT_LAYER);
        dskComplaintReport.setLayer(lblComplaintTypeAndDescription, javax.swing.JLayeredPane.DEFAULT_LAYER);
        dskComplaintReport.setLayer(jScrollPane1, javax.swing.JLayeredPane.DEFAULT_LAYER);
        dskComplaintReport.setLayer(lblAreaOfIncident, javax.swing.JLayeredPane.DEFAULT_LAYER);
        dskComplaintReport.setLayer(jScrollPane2, javax.swing.JLayeredPane.DEFAULT_LAYER);
        dskComplaintReport.setLayer(lblNumberOfPeopleInvolved, javax.swing.JLayeredPane.DEFAULT_LAYER);
        dskComplaintReport.setLayer(cboNumberOfPeopleInvolved, javax.swing.JLayeredPane.DEFAULT_LAYER);
        dskComplaintReport.setLayer(btnRegisterComplaint, javax.swing.JLayeredPane.DEFAULT_LAYER);
        dskComplaintReport.setLayer(btnUpdateLastComplaint, javax.swing.JLayeredPane.DEFAULT_LAYER);
        dskComplaintReport.setLayer(btnClose, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout dskComplaintReportLayout = new javax.swing.GroupLayout(dskComplaintReport);
        dskComplaintReport.setLayout(dskComplaintReportLayout);
        dskComplaintReportLayout.setHorizontalGroup(
            dskComplaintReportLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dskComplaintReportLayout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addGroup(dskComplaintReportLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(dskComplaintReportLayout.createSequentialGroup()
                        .addComponent(lblIcon)
                        .addGap(53, 53, 53)
                        .addComponent(lblHeading)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(dskComplaintReportLayout.createSequentialGroup()
                        .addGroup(dskComplaintReportLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(dskComplaintReportLayout.createSequentialGroup()
                                .addComponent(btnRegisterComplaint, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(71, 71, 71)
                                .addComponent(btnUpdateLastComplaint)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 73, Short.MAX_VALUE)
                                .addComponent(btnClose, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, dskComplaintReportLayout.createSequentialGroup()
                                .addComponent(lblComplaintTypeAndDescription)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(dskComplaintReportLayout.createSequentialGroup()
                                .addGroup(dskComplaintReportLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblAreaOfIncident)
                                    .addComponent(lblNumberOfPeopleInvolved))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(dskComplaintReportLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
                                    .addComponent(cboNumberOfPeopleInvolved, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                        .addGap(42, 42, 42))))
        );
        dskComplaintReportLayout.setVerticalGroup(
            dskComplaintReportLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dskComplaintReportLayout.createSequentialGroup()
                .addGroup(dskComplaintReportLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(dskComplaintReportLayout.createSequentialGroup()
                        .addGap(72, 72, 72)
                        .addComponent(lblHeading))
                    .addGroup(dskComplaintReportLayout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addComponent(lblIcon, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(49, 49, 49)
                .addGroup(dskComplaintReportLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblComplaintTypeAndDescription)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(dskComplaintReportLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblAreaOfIncident)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 22, Short.MAX_VALUE)
                .addGroup(dskComplaintReportLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblNumberOfPeopleInvolved)
                    .addComponent(cboNumberOfPeopleInvolved, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(43, 43, 43)
                .addGroup(dskComplaintReportLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnRegisterComplaint)
                    .addComponent(btnUpdateLastComplaint)
                    .addComponent(btnClose))
                .addGap(26, 26, 26))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(dskComplaintReport, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(dskComplaintReport)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnRegisterComplaintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegisterComplaintActionPerformed
        switch(btnRegisterComplaint.getText()){
            case "Register":
                mRegisterComplaintGUIControls();
                txtDescription.requestFocusInWindow();
                btnRegisterComplaint.setText("Save");
                btnClose.setText("Cancel");
                break;
            case "Save":
                if(txtDescription.getText().equals("")){
                    JOptionPane.showMessageDialog(null, "Every complaint must have a description.");
                    txtDescription.requestFocusInWindow();
                }
                else if(txtAreaOfIncident.getText().equals("")){
                    JOptionPane.showMessageDialog(null, "Please provide an area of incident.");
                    txtAreaOfIncident.requestFocusInWindow();
                }
                else{
                    clsQueryingMethods.mCreateRecord(mRegisterAComplaintQuery(), "Save");
                    btnRegisterComplaint.setText("Register");
                    btnClose.setText("Close");
                    mClearGUIControls();
                    mSetCboNumberOfPeopleInvolved();
                    mDefaultGUIControls();
                }
                break;
        }
    }//GEN-LAST:event_btnRegisterComplaintActionPerformed

    private void btnUpdateLastComplaintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateLastComplaintActionPerformed
        switch(btnUpdateLastComplaint.getText()){
            case "Edit Last Complaint":
                mUpdateLastComplaintGUIControls();
                mAssignVariables();
                mSetValuesToGUI();
                txtDescription.requestFocusInWindow();
                btnUpdateLastComplaint.setText("Save Edited Complaint");
                btnClose.setText("Cancel");
                break;
            case "Save Edited Complaint":
                if(txtDescription.getText().equals("")){
                    JOptionPane.showMessageDialog(null, "Every complaint must have a description.");
                    txtDescription.requestFocusInWindow();
                }
                else if(txtAreaOfIncident.getText().equals("")){
                    JOptionPane.showMessageDialog(null, "Please provide an area of incident.");
                    txtAreaOfIncident.requestFocusInWindow();
                }
                else{
                    clsQueryingMethods.mUpdateRecordDetails(mUpdateLastRegisteredComplaintQuery());
                    btnUpdateLastComplaint.setText("Edit Last Complaint");
                    btnClose.setText("Close");
                    mClearGUIControls();
                    mSetCboNumberOfPeopleInvolved();
                    mDefaultGUIControls();
                }
                break;
        }
    }//GEN-LAST:event_btnUpdateLastComplaintActionPerformed

    private void btnCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCloseActionPerformed
        switch(btnClose.getText()){
            case "Close":
                this.hide();
                break;
            case "Cancel":
                mClearGUIControls();
                mSetCboNumberOfPeopleInvolved();
                btnClose.setText("Close");
                btnRegisterComplaint.setText("Register");
                btnUpdateLastComplaint.setText("Edit Last Complaint");
                mDefaultGUIControls();
                break;
        }
    }//GEN-LAST:event_btnCloseActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnClose;
    private javax.swing.JButton btnRegisterComplaint;
    private javax.swing.JButton btnUpdateLastComplaint;
    private javax.swing.JComboBox<String> cboNumberOfPeopleInvolved;
    private javax.swing.JDesktopPane dskComplaintReport;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblAreaOfIncident;
    private javax.swing.JLabel lblComplaintTypeAndDescription;
    private javax.swing.JLabel lblHeading;
    private javax.swing.JLabel lblIcon;
    private javax.swing.JLabel lblNumberOfPeopleInvolved;
    private javax.swing.JTextArea txtAreaOfIncident;
    private javax.swing.JTextArea txtDescription;
    // End of variables declaration//GEN-END:variables
}
