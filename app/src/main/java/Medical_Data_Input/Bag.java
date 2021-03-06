package Medical_Data_Input;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
/**
 * <h1>Bag</h1>
 * The Bag Java Class contains all data which will be contained by the bag. This data will be used to update other classes/observers.
 * This class is coupled with the other data calculating and tracking classes.
 * Owned by the StomaForm class
 * @author Casey Rogers
 * @version 1.1
 * <h1>Changes:</h1>
 * 2nd October
 * Created Bag Class which implements the Input_Handler Interface
 * <p>
 * 5th February
 * Updated class to have specific constructor and fixed setTime - Jeremy Dunnet
 * 6th February
 * Updated class to use more appropriate variable types for its field - Jeremy Dunnet
 * <p>
 * 8th February
 * Updated class methods after MedicalInput was changed - Jeremy Dunnet
 * <p>
 * 14th February
 * Added toString method - Jeremy Dunnet
 */


public class Bag {

    private int amount;
    private String consistency;
    private String time;


    public Bag(int inAmount, String inConsis, String inTime)
    {

        amount = 0; //If either set fails - other classes can check by getting the value and checking if null
        consistency = null;
        time = null;

        setAmount(inAmount); //Setters validate the input
        setConsistency(inConsis);
        setTime(inTime);

    }


    /**
     * @param amountInput Represents the amount input by the bag contents
     * @return True if the input data is valid, otherwise false.
     * @throws IndexOutOfBoundsException if the bag input is invalid
     */
    public boolean setAmount(int amountInput) throws IndexOutOfBoundsException
    {

        boolean dataValidation;

        if(amountInput < 0 || amountInput > 500) //500 is arbitrary limit for now - change when full range of bag sizes known
        {
            dataValidation = false;
        }
        else
        {
            amount = amountInput;
            dataValidation = true;
        }
        return dataValidation;
    }

    public boolean setConsistency(String inConsis)
    {
        boolean dataValidation = false;

        String[] consis = {"Watery", "Thick", "Toothpaste-like"};

        for(int ii = 0 ; ii < consis.length; ii++)
        {
            if (inConsis.equals(consis[ii])) {
                dataValidation = true;
            }
        }

        if(dataValidation == true)
        {
            consistency = inConsis;
        }

        return dataValidation;
    }

    /**
     * @param timeInput Represents the time which the bag was updated
     * @return True if the input data is valid, otherwise false.
     * @throws IndexOutOfBoundsException if the time input is invalid
     */
    public boolean setTime(String timeInput)
    {
        boolean dataValidation;
        int iHour, iMin, iDay,iYear,iMonth;

        //assuming the time string will be entered as HH-mm-DD-MM-YYYY (mm is minutes)
        String[] timeArray = timeInput.split("-");

        String hour = timeArray[0];
        String min = timeArray[1];
        String day = timeArray[2];
        String month = timeArray[3];
        String year = timeArray[4];

        iHour = Integer.parseInt(hour);
        iMin = Integer.parseInt(min);
        iDay = Integer.parseInt(day);
        iMonth = Integer.parseInt(month);
        iYear = Integer.parseInt(year);


        if((iHour >= 0 && iHour < 23) && (iMin >= 0 && iMin < 60) &&(iDay >= 0 && iDay <= 31) && (iMonth >= 0 && iMonth < 12) && (iYear >= 2018))
        {
            dataValidation = true;
            time = timeInput;
        }
        else {
             dataValidation = false;
        }


        return dataValidation;

    }

    public int getAmount()
    {
        return amount;
    }

    public String getConsistency()
    {
        return consistency;
    }

    public String getTime()
    {
        return time;
    }

    @Override
    public String toString()
    {
        String str = "";

        str = str + getAmount() + "," + getConsistency() + "," + getTime();

        return str;
    }

}
