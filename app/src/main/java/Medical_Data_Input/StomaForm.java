package Medical_Data_Input;

import java.util.*;

/**
 * <h1>StomaForm</h1>
 * The Urine Java Class which will be the container holding all data entered for urine
 * Owned by the StomaForm Class
 * @author Casey Rogers
 * @version 1.0
 * <h1>Changes:</h1>
 * 2nd October
 * Created Urine Class which is owned by he StomaForm class
 * <p>
 * Insert new changes here
 * <h>NOTE</h>
 */

public class StomaForm {

    private List<Bag> bags;
    private Dehydration dehydration;
    private Urine urine;
    private String wellbeing;
    private Date entryTime; // need to set DateTime java class
    //private Object location; //This class field may not be needed for the final demo, but can be implemented in the future

    /**
     * @param numBag Represents bag which will be selected in the list of Bags
     * @return Bag object selected from the list
     * @throws NullPointerException if the bag number is null
     */
    public Bag getBag(int numBag) throws NullPointerException
    {
        //TODO: Do Validation here
        return bags.get(numBag);
    }

    /**
     * @param newBag Represents bag which will be added to the list of Bags
     * @return True if Bag is valid and added, or false if otherwise
     * @throws NullPointerException if the bag number is null
     */
    public boolean addBag(Bag newBag)
    {

        boolean checkBag;
        checkBag = true;
        //TODO: Do validation here
        bags.add(newBag);
        return checkBag;

    }


    /**
     * @param numUrine Represents a value of Urine(returns color and amount)
     * @return Urine object (can gather color and amount)
     * @throws NullPointerException if the bag number is null
     */
    public Urine getUrine(int numUrine) throws NullPointerException
    {
        return urine;
    }

    /**
     * @param urine_value Represents a new urine description to be added to the list
     * @return True if Urine is valid and added, or false if otherwise
     * @throws NullPointerException if the bag number is null
     */
    public boolean addUrine(int urine_value)
    {
        // 1 for light
        // 2 for medium
        // 3 for dark
        boolean checkUrine;
        checkUrine = true;
        String urine_string = Integer.toString(urine_value);
        urine.setColour(urine_string);
        return checkUrine;
    }

    //TIMES URINATED ON AVERAGE
    public void addTimesUrinated(String times_urinated)
    {
        urine.setAmount(times_urinated);
    }

    /**
     * @param inDehydration Represents a new dehydration value which needs to be set
     * @return True if dehydration is valid and added, or false if otherwise
     * @throws NullPointerException if inDehydration is null
     */

    public boolean setDehydration(Dehydration inDehydration) throws NullPointerException
    {
        boolean checkDehydration;
        checkDehydration = true;
        dehydration = inDehydration;
        return checkDehydration;
    }

    public Dehydration getDehydration()
    {
        return dehydration;
    }

    /**
     * @param inWellbeing Represents a String whcih describes the user's well being(good/bad)
     * @return True if well being is valid, and false if otherwise
     * @throws NullPointerException if inDehydration is null
     */
    public boolean setWellbeing(String inWellbeing)
    {
        boolean checkWellbeing = true;
        wellbeing = inWellbeing;
        return checkWellbeing;
    }

    public String getWellbeing()
    {
        return wellbeing;
    }

    /**
     * @param inTime Represents a Date object of the time the entry was given(java object)
     * @return True if well being is valid, and false if otherwise
     * @throws NullPointerException if inDehydration is null
     */
    public boolean setTime(Date inTime)
    {
        boolean checkTime = true;
        entryTime = inTime;
        return checkTime;
    }

    public Date getTime()
    {
        return entryTime;
    }


    /* This method will need discussion, since we may not have time to implement location settings for the app
    public boolean setLocation(Object inLocation)
    {
        boolean checkLocation = true;
        location = inLocation;
        return checkLocation;
    }*/








}
