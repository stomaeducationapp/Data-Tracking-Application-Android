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
 * <p>
 * 8th February
 * Updated class methods after MedicalInput was changed - Jeremy Dunnet
 */

public class StomaForm {

    private List<Bag> bags;
    private Dehydration dehydration;
    private Urine urine;
    private String wellbeing;
    private String entryTime; // need to set DateTime java class
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
     * @throws NullPointerException if the bag number is null
     */
    public void addBag(Bag newBag)
    {
        bags.add(newBag);
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
     * @param inAmount Represents the urine amount we are adding to the form
     * @param inColour Represents the urine amount we are adding to the form
     * @throws NullPointerException if any of the imported values are null
     */
    public void setUrine(int inAmount, String inColour)
    {

        urine = new Urine(inAmount, inColour);

    }

    /**
     * @param inSymptoms Represents a new dehydration value which needs to be set
     * @throws NullPointerException if inDehydration is null
     */

    public void setDehydration(String[] inSymptoms) throws NullPointerException
    {
        dehydration = new Dehydration(inSymptoms);
    }

    public Dehydration getDehydration()
    {
        return dehydration;
    }

    /**
     * @param inWellbeing Represents a String which describes the user's well being(good/bad)
     * @throws NullPointerException if inWellbeing is null
     */
    public void setWellbeing(String inWellbeing)
    {

        wellbeing = inWellbeing;

    }

    public String getWellbeing()
    {
        return wellbeing;
    }

    /**
     * @param inTime Represents the time the entry was given
     * @throws NullPointerException if inTime is null
     */
    public void setTime(String inTime)
    {
        entryTime = inTime;
    }

    public String getTime()
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
