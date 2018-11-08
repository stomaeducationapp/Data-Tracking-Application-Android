package capstonegroup2.dataapp.MainMenu.Fragments;

public interface Information_Change {
    
    enum Field{Name, Gamification, Last_Daily_Review_Date}
    
    void update(Field field, String value);
}
