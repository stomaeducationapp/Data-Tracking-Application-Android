package Observers;

import java.io.FileInputStream;

/**
 * The type Daily review.
 */
class Daily_Review implements Time_Observer {
    // TODO: 27-Aug-18 Remove comments around Factory when class has been created 
    //private Factory factory;

    /**
     * Instantiates a new Daily review.
     */
    public Daily_Review(/*Factory factory*/) {
        //this.factory = factory;
    }

    /**
     * @param input_Stream
     * @return
     * @throws NullPointerException
     */
    @Override
    public boolean Notify(FileInputStream input_Stream) throws NullPointerException{
        if (input_Stream != null) {
            boolean valid = false;
            // TODO: 27-Aug-18 When Daily Review Package is created uncomment below 
        //Daily_Review_Calculator daily_review_calculator = factory.Create_Daily_Review_Calculator();
        //valid = daily_review_calculator.Generate_New_Review(input_Stream);
        return valid;
        } else{
            throw new NullPointerException("Input Stream Object is Null");
        }
    }
}
