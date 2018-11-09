package capstonegroup2.dataapp.MainMenu.News_Package;

/**
 * This class is here as a placeholder for the new section. This section will need to ask for access to the internet and download news from a repository provided by the client at a later date.
 * The placeholder section in the Fragments (green, Yellow, and red) all are named 'News'. This is a text view for now but should probably be changed to a webview so a news page can be loaded from server
 * For now it will have a default set of new it will return to show
 */
public class News_PlaceHolder {
    private String mockNews;

    public News_PlaceHolder(){
        mockNews = "News information will go here, Probably use a webpage container and\n" + "display that or work out a format";
    }
    public String getMockNews(){
        return mockNews;
    }

}
