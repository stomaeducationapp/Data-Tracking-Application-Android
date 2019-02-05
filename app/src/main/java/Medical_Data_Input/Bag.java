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
 */


public class Bag {

    private String amount;
    private Date time;


    public Bag(String inAmount, Date inTime)
    {

        amount = null; //If either set fails - other classes can check by getting the value and checking if null
        time = null;

        setAmount(inAmount); //Setters validate the input
        setTime(inTime);

    }


    /**
     * @param amountInput Represents the amount input by the bag contents
     * @return True if the input data is valid, otherwise false.
     * @throws IndexOutOfBoundsException if the bag input is invalid
     */
    public boolean setAmount(String amountInput) throws IndexOutOfBoundsException
    {

        boolean dataValidation;
        int iAmount;

        iAmount = Integer.parseInt(amountInput);

        if(iAmount < 0 || iAmount > 500)
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

    /**
     * @param timeInput Represents the time which the bag was updated
     * @return True if the input data is valid, otherwise false.
     * @throws IndexOutOfBoundsException if the time input is invalid
     */
    public boolean setTime(Date timeInput)
    {
        boolean dataValidation;
        int iDay,iYear,iMonth;

        DateFormat df = new SimpleDateFormat("dd/MM/yy"); //Need to format the Date object to the correct string style we need
        String date = df.format(timeInput);

        //assuming the time string will be entered as xx/xx/xx
        String[] timeArray = date.split("/");

        String day = timeArray[0];
        String month = timeArray[1];
        String year = timeArray[2];

        iDay = Integer.parseInt(day);
        iMonth = Integer.parseInt(month);
        iYear = Integer.parseInt(year);


        if((iDay >= 0 && iDay <= 31) && (iMonth >= 0 && iMonth <= 12) && (iYear >= 2018))
        {
            dataValidation = true;
            time = timeInput;
        }
        else {
             dataValidation = false;
        }


        return dataValidation;

    }

    public String getAmount()
    {
        return amount;
    }

    public Date getTime()
    {
        return time;
    }

}
