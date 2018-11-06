package capstonegroup2.dataapp.MainMenu.Fragments;

// TODO: 06-Nov-18 enums for parsing values 
public interface Information_Change {
    
    enum Field{Name, Gamification, Last_Export_Date, Last_Daily_Review_Date}
    
    void update(Field field, String value);
}
