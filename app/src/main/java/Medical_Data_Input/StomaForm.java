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
 * 6th February
 * Updated class to contain objects with validation - Jeremy Dunnet
 */

public class StomaForm {

    private List<Bag> bags;
    private Dehydration dehydration;
    private Urine urine;
    private String wellbeing;
    private Date entryTime; // need to set DateTime java class
    //private Object location; //This class field may not be needed for the final demo, but can be implemented in the future

    public StomaForm()
    {
        bags = new ArrayList<Bag>();
        dehydration = null;
        urine = null;
        wellbeing = null;
        entryTime = null;
    }

    /**
     * @param numBag Represents bag which will be selected in the list of Bags
     * @return Bag object selected from the list
     * @throws NullPointerException if the bag number is null
     */
    public Bag getBag(int numBag) throws NullPointerException
    {
        Bag bag = null;

        if(numBag < bags.size()) //List is 0-indexed
        {
            bag = bags.get(numBag);
        }

        return bag;
    }

    /**
     * @param newBag Represents bag which will be added to the list of Bags
     * @return True if Bag is valid and added, or false if otherwise
     * @throws NullPointerException if the bag number is null
     */
    public boolean addBag(Bag newBag)
    {

        boolean checkBag = false;

        if((newBag.getAmount() > 0) && (newBag.getTime() != null)) // A valid bag has information tied to it
        {
            checkBag = true;
            bags.add(newBag);
        }

        return checkBag;

    }


    /**
     * @return Urine object (can gather color and amount)
     * @throws NullPointerException if the urine object is null
     */
    public Urine getUrine() throws NullPointerException
    {
        return urine;
    }

    /**
     * @param u Represents the urine object we are validating to add to the form
     * @return True if Urine is valid and added, or false if otherwise
     * @throws NullPointerException if any of the imported values are null
     */
    public boolean addUrine(Urine u)
    {
        boolean checkUrine = false;

        if((u.getAmount() >= 0) && (u.getColour() != null))
        {
            checkUrine = true;
            urine = u;
        }

        return checkUrine;
    }

    /**
     * @param inDehydration Represents a new dehydration value which needs to be set
     * @return True if dehydration is valid and added, or false if otherwise
     * @throws NullPointerException if inDehydration is null
     */

    public boolean setDehydration(Dehydration inDehydration) throws NullPointerException
    {
        boolean checkDehydration = false;

        if(inDehydration.getSymptoms() != null)
        {
            checkDehydration = true;
            dehydration = inDehydration;
        }

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
        boolean checkWellbeing = false;

        if((wellbeing != null) && (!wellbeing.equals("")))
        {
            wellbeing = inWellbeing;
            checkWellbeing = true;
        }

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
        boolean checkTime = false;

        if(inTime != null)
        {
            entryTime = inTime;
            checkTime = true;
        }

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
