package Factory;

import Observers.Check_State;
import Observers.Daily_Review;
import Observers.Export_Data;
import Observers.Form_Change;
import Observers.Form_Change_Observer;
import Observers.State_Observer;
import Observers.Time_Observer;



/**
 * <h1>Form_Change</h1>
 * The Factory Java Class is used to construct classes removing dependence's between classes and packages
 * The Factory Class is a Singleton that has been written to throw a RuntimeException if Java Reflection APIs
 * are used to change the visibility of the constructor and try and use it.
 *
 * @author Patrick Crockford
 * @version 1.0
 * <h1>Changes</h1>
 * 03rd Sept
 * Created Factory Package and Class, Patrick Crockford
 * Added Singleton Construction and safety code for Java, Handles Java Reflection, Patrick Crockford
 * Added Basic 'Make' methods for all interfaces in the Design UML of the Data Tracking Application, Patrick Crockford
 * Added Comments for future modifications that are required when integrating Factory with other packages, Patrick Crockford
 * JavaDoc, Patrick Crockford
 * 17th Sept
 * Modified Code to comply with Requirements for Observer - Factory Integration, Patrick Crockford
 */
public class Factory {
    private static Factory factory;

    public enum Time_Observer_Choice {Daily_Review, Export_Data}

    public enum Validate_Choice {Credentials, Account, Answer, UserName, Password}

    public enum XML_Reader_Choice {Login, Account, Medical}

    public enum XML_Writer_Choice {Login, Account, Medical}

    /**
     * Private Constructor, due to singleton Pattern
     */
    private Factory() {
        if (factory != null) {
            throw new RuntimeException("Please Use Get_Factory() method instead of Java Reflection!");
        }
    }

    /**
     * Returns the instance of the factory, either by creating a new one during first call of this method
     * or returning the existing instance
     *
     * @return Factory
     */
    public static Factory Get_Factory() {
        if (factory == null) {
            factory = new Factory();
        }
        return factory;
    }

    /*
     * Below Constructor Methods are Based off the Data Tracking Full UML. Method Names and parameters
     * will need to be updated as project is developed to reflect any changes made in the packages/classes
     *********************************
     * NOTE
     *********************************
     * Methods will be commented out until the Package/Class is merged/integrated into the project and is apart of the
     * integration Coding Tasks.
     * Code within, mainly secondary classes that are required for the main class to be constructed
     * need to be added as well when integrating
     */

    //OBSERVER PACKAGE

    /**
     * Make form change observer form change observer.
     *
     * @return the form change observer
     */
    public Form_Change_Observer Make_Form_Change_Observer(){
        return new Form_Change(this);
    }


    /**
     * Make state observer state observer.
     *
     * @return the state observer
     */
    public State_Observer Make_State_Observer() {
        return new Check_State(this);
    }


    /**
     * Make time observer time observer.
     *
     * @param choice the choice
     * @return the time observer
     */
    public Time_Observer Make_Time_Observer(Time_Observer_Choice choice){
        Time_Observer time_Observer = null;
        switch(choice){
            case Daily_Review:
                time_Observer = new Daily_Review(this);
                break;
            case Export_Data:
                time_Observer = new Export_Data(this);
                break;
            default:
                throw new RuntimeException("Invalid Enum given for Time_Observer_Choice");
        }
        return time_Observer;
    }


    //LOG_IN PACKAGE
    /*
    public Login_Screen_Handler Make_Login_Screen_Handler(){
        return new Login_Screen_Handler();
    }
    */

    //PASSWORD_RECOVERY PACKAGE
    /*
    public Password_Recovery_Screen_Event_Handler Make_Password_Recovery_Screen_Event_Handler(){
        return new Password_Recovery_Screen_Event_Handler();
    }
     */

    //ACCOUNT_CREATION PACKAGE
    /*
    public Account_Creation_Screen_Event_Handler Make_Account_Creation_Screen_Event_Handler(){
        return new Account_Creation_Screen_Event_Handler();
    }
     */

    //VALIDATION PACKAGE
    /*
    public Validate Make_Validate(Validate_Choice choice){
        Validate validate = null;
        switch(choice){
            case(Validate_Choice.Credentials):
                validate = new Validate_Credentials();
                break;
            case(Validate_Choice.Account):
                validate = new Validate_Account();
                break;
            case(Validate_Choice.Answer):
                validate = new Validate_Answer();
                break;
            case(Validate_Choice.UserName):
                validate = new Validate_UserName();
                break;
            case(Validate_Choice.Password):
                validate = new Validate_Password();
                break;
            default:
                throw new RuntimeException("Invalid Enum given for Validate_Choice")
        }
        return validate;
     */

    //24 HOUR REVIEW PACKAGE
    /*
    public Stoma_Review_Handler Make_Stoma_Review_Handler(){
        return new Stoma_Review_Handler();
    }
     */
    /*
    public 24_Hour_Data_Calculator Make_24_Hour_Data_Calculator(){
        return new 24_Hour_Data_Calculator();
    }
     */
    /*
    public Review_Data Make_Review_Data(){
        return new Review_Data();
    }
     */

    //MAIN MENU PACKAGE
    /*
    public Account_Main_Menu_Handler Make_Account_Main_Menu_Handler(){
        return new Account_Main_Menu_Handler();
    )
     */

    //MEDICAL_STATES PACKAGE
    /*
    public Stoma_State_Calculator Make_Stoma_State_Calculator(){
        return new Stoma_State_Calculator();
    }
     */

    //MEDICAL_DATA_INPUT PACKAGE
    /*
    public Medical_Data_Input_Handler Make_Medical_Data_Input_Handler(){
        return new Medical_Data_Input_Handler();
    )
     */
    //May need to add Bag and Urine constructors here depending on if package private or not.

    //XML PACKAGE
    /*
    public Reader Make_Reader( XML_Reader_Choice choice){
        Reader reader = null
        switch(choice){
            case(XML_Reader_Choice.Login):
                reader = new Login_Information();
                break;
            case(XML_Reader_Choice.Account):
                reader = new Account_Information();
                break;
            case(XML_Reader_Choice.Medical):
                reader = new Medical_Information();
                break;
            default:
        }
        return reader;
    }
     */

    /*
    public Reader Make_Reader( XML_Writer_Choice choice){
        Reader writer = null
        switch(choice){
            case(XML_Reader_Choice.Login):
                writer = new Login_Information();
                break;
            case(XML_Reader_Choice.Account):
                writer = new Account_Information();
                break;
            case(XML_Reader_Choice.Medical):
                writer = new Medical_Information();
                break;
            default:
        }
        return writer;
    }
     */
}