package Observers;

import android.content.Intent;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Patrick on 23-Aug-18.
 * Last Edited by Patrick on 23-Aug-18 2pm
 */

/**
 * This Test set is about testing the control structure of the Observer FOrm_Change_Concrete Class,
 * so the Factory Calls are removed, as there is a dependency between them. This will be tested
 * in integration test tasks at a later date
 */

/**
 * Changes:
 * 23rd Aug
 * Created Class 'Form_Change_Concrete_Unit_Test'
 * Written all test methods (11)
 */

public class Form_Change_Concrete_Unit_Test {
    private Intent intent;
    private Form_Change_Concrete form_change_concrete;

    @Before
    public void initialize(){
        intent = new Intent();
        form_change_concrete = new Form_Change_Concrete();
    }
    //Testing all Correct Enum Values, should not throw exceptions
    //Test Account_Creation enum value
    @Test
    public void Account_Creation_Test() {
        assertEquals(form_change_concrete.Change_Form(Form_Change.Form_Control.Account_Creation, intent),true);
    }
    //Test Medical_Data_Input enum value
    @Test
    public void Medical_Data_Input_Test() {
        assertEquals(form_change_concrete.Change_Form(Form_Change.Form_Control.Medical_Data_Input, intent),true);
    }
    //Test Account_Main_Menu enum value
    @Test
    public void Account_Main_Menu_Test() {
        assertEquals(form_change_concrete.Change_Form(Form_Change.Form_Control.Account_Main_Menu, intent),true);
    }
    //Test Password_Recovery enum value
    @Test
    public void Password_Recovery_Test() {
        assertEquals(form_change_concrete.Change_Form(Form_Change.Form_Control.Password_Recovery, intent),true);
    }
    //Test Review enum value
    @Test
    public void Review_Test() {
        assertEquals(form_change_concrete.Change_Form(Form_Change.Form_Control.Review, intent),true);
    }
    //Test Account_Information enum value
    @Test
    public void Account_Information_Test() {
        assertEquals(form_change_concrete.Change_Form(Form_Change.Form_Control.Account_Information, intent),true);
    }
    //Test Encrypt_and_Export enum value
    @Test
    public void Encrypt_and_Export_Test() {
        assertEquals(form_change_concrete.Change_Form(Form_Change.Form_Control.Encrypt_and_Export, intent),true);
    }
    //Test Gamification enum value
    @Test
    public void Gamification_Test() {
        assertEquals(form_change_concrete.Change_Form(Form_Change.Form_Control.Gamification, intent),true);
    }

    //Testing null inputs should all throw errors
    //Test when Enum provided = null
    @Test(expected = NullPointerException.class)
    public void Enum_Null_Test(){
        //Testing Enum = null
        assertEquals(form_change_concrete.Change_Form(null, intent),true);

    }
    //Test when intent provided = null
    @Test(expected = NullPointerException.class)
    public void Intent_Null_Test(){
        //Testing Intent = null
        assertEquals(form_change_concrete.Change_Form(Form_Change.Form_Control.Account_Creation, null),true);

    }
    //Test when both enum and intent value = null
    @Test(expected = NullPointerException.class)
    public void Enum_And_Intent_Null_Test(){
        //Testing Enum and Intent = null
        assertEquals(form_change_concrete.Change_Form(null, null), true);
    }

}