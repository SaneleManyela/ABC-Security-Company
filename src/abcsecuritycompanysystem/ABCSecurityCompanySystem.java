/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package abcsecuritycompanysystem;

import static java.awt.Frame.MAXIMIZED_BOTH;

/**
 *
 * @author Sanele
 */
public class ABCSecurityCompanySystem {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //These lines of code create an instance
        //of frmLogin then display that instance
        //in extended state/full screen
        frmLogin frmlogin = new frmLogin();
        frmlogin.setExtendedState(MAXIMIZED_BOTH);
        frmlogin.setTitle("ABC Security Company Login");
        frmlogin.show();
    }
    
}
