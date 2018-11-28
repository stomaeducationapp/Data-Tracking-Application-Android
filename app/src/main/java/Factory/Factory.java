package Factory;

import EncryptExport.Detector;
import EncryptExport.Encrypt;
import EncryptExport.Retrieval;
import MedicalReview.DailyReview;
import MedicalReview.ReviewData;
import MedicalReview.ReviewHandler;
import MedicalStates.StomaStateCalculator;
import Observers.Check_State;
import Observers.Daily_Review;
import Observers.Export_Data;
import Observers.Form_Change;
import Observers.Form_Change_Observer;
import Observers.State_Observer;
import Observers.Time_Observer;
import XML.Account_Reader;
import XML.Account_Writer;
import XML.Login_Reader;
import XML.Login_Writer;
import XML.Medical_Reader;
import XML.Medical_Writer;
import XML.XML_Reader;
import XML.XML_Writer;


/**
 * <h1>Form_Change</h1>
 * The Factory Java Class is used to construct classes removing dependence's between classes and packages
 * The Factory Class is a Singleton that has been written to throw a RuntimeException if Java Reflection APIs
 * are used to change the visibility of the constructor and try and use it.
 *
 * @author Patrick Crockford
 * @version 1.1
 * <h1>Changes</h1>
 * 03rd Sept
 * Created Factory Package and Class, Patrick Crockford
 * Added Singleton Construction and safety code for Java, Handles Java Reflection, Patrick Crockford
 * Added Basic 'Make' methods for all interfaces in the Design UML of the Data Tracking Application, Patrick Crockford
 * Added Comments for future modifications that are required when integrating Factory with other packages, Patrick Crockford
 * JavaDoc, Patrick Crockford
 * 17th Sept
 * Modified Code to comply with Requirements for Observer - Factory Integration, Patrick Crockford
 * 27th Nov
 * Updated factory methods to properly created all Encyrpt package objects and XML objects - Jeremy Dunnet
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
    private Factory(){
        if (factory != null) {
            throw new RuntimeException("Please Use Get_Factory() method instead of Java Reflection!");
        }
    }

    /**
     * Returns the instance of the factory, either by creating a new one during first call of this method
     * or returning the existing instance
     *
     * @return Factory factory
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
     * Constructs a Form_Change_Observer Object
     *
     * @return Form_Change_Observer Object
     */
    public Form_Change_Observer Make_Form_Change_Observer(){
        return new Form_Change(this);
    }


    /**
     * Constructs a State_Observer Object
     *
     * @return State_Observer Object
     */
    public State_Observer Make_State_Observer() {
        return new Check_State(this);
    }


    /**
     * Constructs a Time_Observer Object, with one of various concrete class implementations
     *
     * @param choice enum representing the choice of which concrete class to construct
     * @return Time_Observer Object
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

    public ReviewHandler Make_Stoma_Review_Handler(){
        return new ReviewHandler();
    }

    public DailyReview Make_Review_Dataset(){
        return new DailyReview();
    }

    //copy constructor implementation
    public DailyReview Make_Review_Dataset(DailyReview copy){
        return new DailyReview(copy);
    }


    public ReviewData Make_Review_Data_Reader(){
        return new ReviewData();
    }


    //MAIN MENU PACKAGE
    /*
    public Account_Main_Menu_Handler Make_Account_Main_Menu_Handler(){
        return new Account_Main_Menu_Handler();
    )
     */

    //MEDICAL_STATES PACKAGE
    public StomaStateCalculator Make_Stoma_State_Calculator(){
        return new StomaStateCalculator();
    }

    //MEDICAL_DATA_INPUT PACKAGE
    /*
    public Medical_Data_Input_Handler Make_Medical_Data_Input_Handler(){
        return new Medical_Data_Input_Handler();
    )
     */
    //May need to add Bag and Urine constructors here depending on if package private or not.

    //Factory methods for XML Package
    public XML_Reader Make_Reader(XML_Reader_Choice choice){
        XML_Reader reader = null;
        switch(choice){
            case Login:
                reader = new Login_Reader();
                break;
            case Account:
                reader = new Account_Reader();
                break;
            case Medical:
                reader = new Medical_Reader();
                break;
            default:
        }
        return reader;
    }


    public XML_Writer Make_Writer(XML_Writer_Choice choice){
        XML_Writer writer = null;
        switch(choice){
            case Login:
                writer = new Login_Writer();
                break;
            case Account:
                writer = new Account_Writer();
                break;
            case Medical:
                writer = new Medical_Writer();
                break;
            default:
        }
        return writer;
    }

    //Factory methods for EncryptExport Package
    public Retrieval makeRetrieval()
    {
        return new Retrieval();
    }

    public Encrypt makeEncrypt()
    {
        return new Encrypt();
    }

    public Detector makeDetector()
    {
        return new Detector();
    }
}