package capstonegroup2.dataapp.MainMenu;

public class Account_Data_Container {

    private String account_Name;
    private String state;
    private String notifications;
    private String export_Settings;
    private String Gamification_Mode;

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
}
