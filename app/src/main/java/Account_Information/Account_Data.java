package Account_Information;
//Container class
public class Account_Data {
//Gamification, Notification, State, Name, Last_Daily_Review_Date, Last_Export_Date
    private String account_Name;
    private String Gamification;
    private String Notification;
    private String Export_Settings;


    public String getExport_Settings() {
        return Export_Settings;
    }

    public void setExport_Settings(String export_Settings) {
        Export_Settings = export_Settings;
    }

    public String getAccount_Name() {
        return account_Name;
    }

    public void setAccount_Name(String account_Name) {
        this.account_Name = account_Name;
    }

    public String getGamification() {
        return Gamification;
    }

    public void setGamification(String gamification) {
        Gamification = gamification;
    }

    public String getNotification() {
        return Notification;
    }

    public void setNotification(String notification) {
        Notification = notification;
    }
}
