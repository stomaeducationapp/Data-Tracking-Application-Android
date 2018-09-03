package Observers;

import android.content.Intent;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Patrick on 23-Aug-18.
 * Last Edited by Patrick on 23-Aug-18 2pm
 * <p>
 * This Test set is about testing the control structure of the Observer FOrm_Change_Concrete Class,
 * so the Factory Calls are removed, as there is a dependency between them. This will be tested
 * in integration test tasks at a later date
 *
 * Changes:
 * 23rd Aug
 * Created Class 'Form_Change__Unit_Test'
 * Written all test methods (11)
 */

public class Form_Change__Unit_Test {
    private Intent intent;
    private Form_Change_Observer form_Change_Observer;

    @Before
    public void initialize() {
        intent = new Intent();
        form_Change_Observer = new Form_Change();
    }

    //Testing all Correct Enum Values, should not throw exceptions
    //Test Account_Creation enum value
    @Test
    public void Account_Creation_Test() {
        try {
            assertEquals(form_Change_Observer.Change_Form(Form_Change_Observer.Activity_Control.Account_Creation, intent), true);
        } catch (Invalid_Enum_Exception e) {
            e.printStackTrace();
        }
    }

    //Test Medical_Data_Input enum value
    @Test
    public void Medical_Data_Input_Test() {
        try {
            assertEquals(form_Change_Observer.Change_Form(Form_Change_Observer.Activity_Control.Medical_Data_Input, intent), true);
        } catch (Invalid_Enum_Exception e) {
            e.printStackTrace();
        }
    }

    //Test Account_Main_Menu enum value
    @Test
    public void Account_Main_Menu_Test() {
        try {
            assertEquals(form_Change_Observer.Change_Form(Form_Change_Observer.Activity_Control.Account_Main_Menu, intent), true);
        } catch (Invalid_Enum_Exception e) {
            e.printStackTrace();
        }
    }

    //Test Password_Recovery enum value
    @Test
    public void Password_Recovery_Test() {
        try {
            assertEquals(form_Change_Observer.Change_Form(Form_Change_Observer.Activity_Control.Password_Recovery, intent), true);
        } catch (Invalid_Enum_Exception e) {
            e.printStackTrace();
        }
    }

    //Test Review enum value
    @Test
    public void Review_Test() {
        try {
            assertEquals(form_Change_Observer.Change_Form(Form_Change_Observer.Activity_Control.Review, intent), true);
        } catch (Invalid_Enum_Exception e) {
            e.printStackTrace();
        }
    }

    //Test Account_Information enum value
    @Test
    public void Account_Information_Test() {
        try {
            assertEquals(form_Change_Observer.Change_Form(Form_Change_Observer.Activity_Control.Account_Information, intent), true);
        } catch (Invalid_Enum_Exception e) {
            e.printStackTrace();
        }
    }

    //Test Encrypt_and_Export enum value
    @Test
    public void Encrypt_and_Export_Test() {
        try {
            assertEquals(form_Change_Observer.Change_Form(Form_Change_Observer.Activity_Control.Encrypt_and_Export, intent), true);
        } catch (Invalid_Enum_Exception e) {
            e.printStackTrace();
        }
    }

    //Test Gamification enum value
    @Test
    public void Gamification_Test() {
        try {
            assertEquals(form_Change_Observer.Change_Form(Form_Change_Observer.Activity_Control.Gamification, intent), true);
        } catch (Invalid_Enum_Exception e) {
            e.printStackTrace();
        }
    }

    //Testing null inputs should all throw errors
    //Test when Enum provided = null
    @Test(expected = NullPointerException.class)
    public void Enum_Null_Test() {
        //Testing Enum = null
        try {
            assertEquals(form_Change_Observer.Change_Form(null, intent), true);
        } catch (Invalid_Enum_Exception e) {
            e.printStackTrace();
        }

    }

    //Test when intent provided = null
    @Test(expected = NullPointerException.class)
    public void Intent_Null_Test() {
        //Testing Intent = null
        try {
            assertEquals(form_Change_Observer.Change_Form(Form_Change_Observer.Activity_Control.Account_Creation, null), true);
        } catch (Invalid_Enum_Exception e) {
            e.printStackTrace();
        }

    }

    //Test when both enum and intent value = null
    @Test(expected = NullPointerException.class)
    public void Enum_And_Intent_Null_Test() {
        //Testing Enum and Intent = null
        try {
            assertEquals(form_Change_Observer.Change_Form(null, null), true);
        } catch (Invalid_Enum_Exception e) {
            e.printStackTrace();
        }
    }

}