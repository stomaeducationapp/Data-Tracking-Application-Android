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

    public static int total = 0;
    public static int passed = 0;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        Medical_Reader_Test medical_Reader_Test = new Medical_Reader_Test();
        medical_Reader_Test.Run_Tests();

        Account_Reader_Test account_Reader_Test = new Account_Reader_Test();
        account_Reader_Test.Run_Tests();

        Login_Reader_Test login_Reader_Test = new Login_Reader_Test();
        login_Reader_Test.Run_Tests();
        System.out.println("***************************************************"
                + System.lineSeparator()
                + "***************************************************"
                + System.lineSeparator()
                + "TOTAL TEST RESULTS");
        if (total != 0) {
            System.out.println("Tests Run = " + total);
            System.out.println("Tests Passed = " + passed);
            System.out.println("% Passed = " + ((double) passed / (double) total) * 100);
        } else {
            System.out.println("Tests Run = " + total);
            System.out.println("Tests Passed = " + passed);
            System.out.println("% Passed = NULL ");
        }
    }
}
