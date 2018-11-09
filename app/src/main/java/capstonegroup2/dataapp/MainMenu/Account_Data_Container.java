package capstonegroup2.dataapp.MainMenu;

public class Account_Data_Container {

    private String account_Name;
    private String state;
    private String notifications;
    private String export_Settings;
    private String Gamification_Mode;
    private String Last_Export_Date;
    private String Last_Daily_Review_Date;

    public String getAccount_Name() {
        return account_Name;
    }

    public void setAccount_Name(String account_Name) {
        this.account_Name = account_Name;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getNotifications() {
        return notifications;
    }

    public void setNotifications(String notifications) {
        this.notifications = notifications;
    }

    public String getExport_Settings() {
        return export_Settings;
    }

    public void setExport_Settings(String export_Settings) {
        this.export_Settings = export_Settings;
    }

    public String getGamification_Mode() {
        return Gamification_Mode;
    }

    public void setGamification_Mode(String gamification_Mode) {
        Gamification_Mode = gamification_Mode;
    }

    public String getLast_Daily_Review_Date() {
        return Last_Daily_Review_Date;
    }

    public void setLast_Daily_Review_Date(String last_Daily_Review_Date) {
        Last_Daily_Review_Date = last_Daily_Review_Date;
    }

    public String getLast_Export_Date() {
        return Last_Export_Date;
    }

    public void setLast_Export_Date(String last_Export_Date) {
        Last_Export_Date = last_Export_Date;
    }
}
