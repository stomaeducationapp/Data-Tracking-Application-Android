/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xml_test;

/**
 *
 * @author Patrick
 */
public class Xml_Test {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        Medical_Reader_Test medical_Reader_Test = new Medical_Reader_Test();
        medical_Reader_Test.Run_Tests();
        
        /* COMMENTED OUT DUE TO ONLY TESTING MEDICAL READER CURRENTLY
        Account_Reader_Test account_Reader_Test = new Account_Reader_Test();
        account_Reader_Test.Run_Tests();
        
        Login_Reader_Test login_Reader_Test = new Login_Reader_Test();
        login_Reader_Test.Run_Tests();*/
    }
}
