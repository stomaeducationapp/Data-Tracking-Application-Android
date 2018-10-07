/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xml_test;

import java.util.Scanner;

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
        try (Scanner reader = new Scanner(System.in)) {
            boolean finish = false;
            while (!finish) {
                total = 0;
                passed = 0;
                System.out.println("'1' for Reader, '2' for Writer, '3' for Exit");
                String input = reader.next();
                switch (input) {
                    case "1":
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
                                + "OVERALL TEST RESULTS FOR READER FILES");
                        if (total != 0) {
                            System.out.println("Tests Run = " + total);
                            System.out.println("Tests Passed = " + passed);
                            System.out.println("% Passed = " + ((double) passed / (double) total) * 100);
                        } else {
                            System.out.println("Tests Run = " + total);
                            System.out.println("Tests Passed = " + passed);
                            System.out.println("% Passed = NULL ");
                        }
                        break;
                    case "2":
                        
                        Account_Writer_Test accout_Writer_Test = new Account_Writer_Test();
                        accout_Writer_Test.Run_Tests();
                        Login_Writer_Test login_Writer_Test = new Login_Writer_Test();
                        login_Writer_Test.Run_Tests();
                        
                        Medical_Writer_Test medical_Writer_Test = new Medical_Writer_Test();
                        medical_Writer_Test.Run_Tests();
                        System.out.println("***************************************************"
                                + System.lineSeparator()
                                + "***************************************************"
                                + System.lineSeparator()
                                + "OVERALL TEST RESULTS FOR WRITER FILES");
                        if (total != 0) {
                            System.out.println("Tests Run = " + total);
                            System.out.println("Tests Passed = " + passed);
                            System.out.println("% Passed = " + ((double) passed / (double) total) * 100);
                        } else {
                            System.out.println("Tests Run = " + total);
                            System.out.println("Tests Passed = " + passed);
                            System.out.println("% Passed = NULL ");
                        }
                        break;
                    case "3":
                        finish = true;
                        break;
                    default:
                        System.out.println("Invalid Input");
                        break;
                }

            }
        }
    }

}
