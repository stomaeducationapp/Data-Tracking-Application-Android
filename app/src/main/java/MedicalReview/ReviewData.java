package MedicalReview;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Factory.Factory;
import Observers.Time_Observer;
import XML.Login_Reader;
import XML.Medical_Reader;
import XML.XML_Reader;
import XML.XML_Reader_Exception;

/**
 * Class: ReviewData
 * Purpose: Handles methods for reading in data to be used by the medical review package
 * @author Ethan
 */
public class ReviewData {
    private Factory factory;

    /**
     * Default constructor for the object.
     * Initialises the factory.
     */
    public ReviewData() {
        factory = Factory.Get_Factory();
    }

    /**
     * Handles the call to the file reader to get the data to calculate the DailyReview.
     * Expects to receive data in specific format "TIME:attribute1-value1,attribute2-value2,etc"
     * Parses the data returned from file into the expected map.
     * File that is being read from should only contain the entries to be used in this review.
     * @return data, the map containing the most recent 24 hours data.
     */
    public Map<String, String> loadData(Map<Time_Observer.Files, File> fileMap) {

        //read in the data - should have 1 entry per line
        File medicalFile = fileMap.get(Time_Observer.Files.Medical);
        Medical_Reader reader = (Medical_Reader) factory.Make_Reader(Factory.XML_Reader_Choice.Medical);
        Map<String, String> data;
        List<XML_Reader.Tags_To_Read> list = new ArrayList<>(Arrays.asList(XML_Reader.Tags_To_Read.Daily_Data, XML_Reader.Tags_To_Read.Volume, XML_Reader.Tags_To_Read.Medical_State,
                                                            XML_Reader.Tags_To_Read.Wellbeing, XML_Reader.Tags_To_Read.Entries_Retrieved, XML_Reader.Tags_To_Read.Entry_Time));

        try{
            data = reader.Read_File(medicalFile, list, "Bob");
        }
        catch (XML_Reader_Exception e)
        {
            throw new RuntimeException("Failed to read the login file" + e.getMessage());
        }

        return data;
    }


    /*
     * THESE METHODS CURRENTLY HAVE NO PURPOSE, ARE HERE FOR FUTURE IMPLEMENTATION
     */

    /**
     * NOTE: This method is currently unused. Can be implemented further in future if loading is
     * deemed necessary.
     * Loads today and yesterday DailyReview object state from file.
     * @return sets, array containing the two DailyReview objects.
     */
    public DailyReview[] loadReviews() {
        DailyReview[] sets = new DailyReview[2];

        //load the reviews from the file system
        //sets[0] = new DailyReview(reader.getTodayDataset());
        //sets[1] = new DailyReview(reader.getYesterdayDataset());

        return sets;
    }

    /**
     * NOTE: This method is currently unused. Can be implemented further in future if saving is
     * deemed necessary.
     * Saves today and yesterday DailyReview object state to file.
     * @param toSave, array containing the DailyReview objects to save.
     */
    public void saveReviews(DailyReview[] toSave) {
        //XML_WRITER_FROM_FACTORY writer = factory.Get_File_Writer();
        //writer.saveDataset("today", toSave[0]);
        //writer.saveDataset("yesterday", toSave[1]);
    }
}
