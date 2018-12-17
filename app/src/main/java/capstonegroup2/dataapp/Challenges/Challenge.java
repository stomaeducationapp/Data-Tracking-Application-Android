package capstonegroup2.dataapp.Challenges;

/* AUTHOR INFORMATION
 * CREATOR - Jeremy Dunnet 05/11/2018
 * LAST MODIFIED BY - Jeremy Dunnet 06/11/2018
 */

/* CLASS/FILE DESCRIPTION
 * This is a class we will use to contain all relevant information to a challenge so we can access certain info easily
 */

/* VERSION HISTORY
 * 05/11/2018 - Created class and added first draft code
 * 06/11/2018 - Separated into it's own class
 */

/* REFERENCES
 * Idea sourced from https://www.androidhive.info/2016/05/android-working-with-card-view-and-recycler-view/
 * And many more from https://developer.android.com/
 */
public class Challenge
{

    //Classfields
    private int mode;
    private String challengeTitle;
    private String challengeType;
    private String rewardValue;
    private String description;
    private String imageName;
    private boolean complete;

    public Challenge(int gameMode, String title, String type, String value, String des, String image)
    {
        mode = gameMode;
        challengeTitle = title;
        challengeType = type;
        rewardValue = value;
        description = des;
        imageName = image;
        complete = false; //Should always be incomplete on construction

    }

    //Getters
    public int getMode() {
        return mode;
    }

    public String getChallengeTitle() {
        return challengeTitle;
    }

    public String getChallengeType() {
        return challengeType;
    }

    public String getRewardValue() {
        return rewardValue;
    }

    public String getDes() {
        return description;
    }

    public String getImageName() { return imageName; }

    public void setComplete(boolean complete) { this.complete = complete; }
    public boolean isComplete() { return complete; }
}
