package MedicalReview;


import android.content.Context;
import android.graphics.Color;
import android.os.Parcel;
import android.os.Parcelable;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.BarChart;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.CategorySeries;
import org.achartengine.model.TimeSeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import java.util.Date;
import java.util.Map;

/**
 * Class: DailyReview
 * Purpose: Contains the data sets for each graph for each day and facilitates creating graph views
 * Implements: Parcelable. The reason for this is so the DailyReview can passed via an intent
 * @author Ethan
 */
public class DailyReview implements Parcelable {
    private TimeSeries stateGraphSeries;
    private CategorySeries statePieSeries;
    private TimeSeries volumeGraphSeries;
    private TimeSeries bagGraphSeries;
    private CategorySeries wellbeingPieSeries;

    /**
     * DailyReview default constructor to create the object with all series set to null.
     */
    public DailyReview() {
        stateGraphSeries = null;
        statePieSeries = null;
        volumeGraphSeries = null;
        bagGraphSeries = null;
        wellbeingPieSeries = null;
    }

    /**
     * DailyReview copy constructor. This facilitates creating an object with the same values as
     * an imported object of the same class.
     * @param review The object to copy values from.
     */
    public DailyReview(DailyReview review) {
        stateGraphSeries = review.stateGraphSeries;
        statePieSeries = review.statePieSeries;
        volumeGraphSeries = review.volumeGraphSeries;
        bagGraphSeries = review.bagGraphSeries;
        wellbeingPieSeries = review.wellbeingPieSeries;
    }

    //PARCELABLE INTERFACE METHODS IMPLEMENTATION
    /**
     * DailyReview constructor using an imported parcel object.
     * @param in the parcel containing values for the class fields.
     */
    protected DailyReview(Parcel in) {
        //Retrieve in FIFO from the way the values were added
        stateGraphSeries = (TimeSeries) in.readValue(TimeSeries.class.getClassLoader());
        statePieSeries = (CategorySeries) in.readValue(CategorySeries.class.getClassLoader());
        volumeGraphSeries = (TimeSeries) in.readValue(TimeSeries.class.getClassLoader());
        bagGraphSeries = (TimeSeries) in.readValue(TimeSeries.class.getClassLoader());
        wellbeingPieSeries = (CategorySeries) in.readValue(CategorySeries.class.getClassLoader());
    }

    /**
     * Standard variables/methods required for the parcelable class
     */
    public static final Creator<DailyReview> CREATOR = new Creator<DailyReview>() {
        @Override
        public DailyReview createFromParcel(Parcel in) {
            return new DailyReview(in);
        }

        @Override
        public DailyReview[] newArray(int size) {
            return new DailyReview[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * The writeToParcel method adds the values for the current instance of DailyReview to a parcel object.
     * @param parcel the parcel to write to
     * @param i control flags, we don't need any for our implementation
     */
    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeValue(stateGraphSeries);
        parcel.writeValue(statePieSeries);
        parcel.writeValue(volumeGraphSeries);
        parcel.writeValue(bagGraphSeries);
        parcel.writeValue(wellbeingPieSeries);
    }

    //END PARCEL IMPLEMENTATION

    /*
     * STATE STUFF
     */
    /**
     * Calculates the data series to be used for the state line graph
     * @param data the raw data to be put into the series.
     *             Expects map to hold (Time of entry, Value of entry)
     */
    public boolean calcStateGraph (Map<Date, Integer> data) {
        boolean success = true;
        stateGraphSeries = new TimeSeries("State Progression");

        try {
            //create the data set
            Date[] attributes = data.keySet().toArray(new Date[0]);
            for (Date key : attributes) {
                stateGraphSeries.add(key, data.get(key));
            }
        }
        catch (IndexOutOfBoundsException e) {success = false;}
        return success;
    }

    /**
     * Creates a renderer to be used with the data series to display the graph.
     * Then uses the renderer, data set and calling context to create a graph view (the visual
     * representation of the graph)
     * @param context The context of the calling activity
     * @return stateGraphView, the GraphicalView implementation of the state line graph
     */
     public GraphicalView displayStateGraph(Context context) {
        //create the renderer
        XYSeriesRenderer renderer = new XYSeriesRenderer();
        renderer.setLineWidth(2);
        renderer.setColor(Color.BLACK);
        renderer.setPointStyle(PointStyle.CIRCLE);
        renderer.setDisplayBoundingPoints(true);
        renderer.setPointStrokeWidth(3);
        renderer.setChartValuesTextSize(500);

        //add this renderer to the display renderer
        XYMultipleSeriesRenderer mRenderer = new XYMultipleSeriesRenderer();
        mRenderer.addSeriesRenderer(renderer);

        //change the graph display parameters
        mRenderer.setMarginsColor(Color.argb(0x00, 0xff, 0x00, 0x00));  //transparent borders
        mRenderer.setPanEnabled(false, false);  //disable panning
        mRenderer.setYAxisMin(0);   //y axis min 0
        mRenderer.setYAxisMax(10);  //y axis max 10
        mRenderer.addYTextLabel(0, "Green");
        mRenderer.addYTextLabel(5, "Yellow");
        mRenderer.addYTextLabel(8, "Red");
        mRenderer.setShowGrid(true);    //display grid lines

        XYMultipleSeriesDataset dataSet = new XYMultipleSeriesDataset();
        dataSet.addSeries(stateGraphSeries);

        return ChartFactory.getTimeChartView(context, dataSet, mRenderer, "SHORT");
    }

    /**
     * Calculates the data series to be used for the state pie chart
     * @param data the raw data to be put into the pie chart series.
     *             Expects map to hold (Time of entry, Value of entry)
     */
    public boolean calcStateChart(Map<Date, Integer> data) {
        boolean success = true;
        try {
            //first find the percentage of each day in each state
            Date[] attributes = data.keySet().toArray(new Date[0]);
            int greenTime = 0, yellowTime = 0, redTime = 0;

            for (Date key : attributes) {
                if (data.get(key) > 0 && data.get(key) < 5) {   //green time
                    greenTime++;
                } else if (data.get(key) > 4 && data.get(key) < 8) {  //yellow time
                    yellowTime++;
                } else if (data.get(key) > 7 && data.get(key) < 11) { //red time
                    redTime++;
                }
            }

            //add percentage of day to the data series
            statePieSeries = new CategorySeries("");
            statePieSeries.add("Green", ((double) greenTime) / ((double) (greenTime + yellowTime + redTime)));
            statePieSeries.add("Yellow", ((double) yellowTime) / ((double) (greenTime + yellowTime + redTime)));
            statePieSeries.add("Red", ((double) redTime) / ((double) (greenTime + yellowTime + redTime)));
        }
        catch (IndexOutOfBoundsException e) {success = false;}
        return success;
    }

    /**
     * Creates a renderer to be used with the data series to display the chart.
     * Then uses the renderer, dataset and calling context to create a chart view (the visual
     * representation of the chart)
     * @param context The context of the calling activity
     * @return stateChartView, the GraphicalView implementation of the state pie chart
     */
      public GraphicalView displayStateChart(Context context) {
        //create the renderer
        DefaultRenderer mRenderer = new DefaultRenderer();
        mRenderer.setStartAngle(180);
        mRenderer.setDisplayValues(true);
        mRenderer.setPanEnabled(false);

        int[] colours = {Color.GREEN, Color.YELLOW, Color.RED};
        SimpleSeriesRenderer rend = null;

        for (int i = 0; i < 3; i++) {
            rend = new SimpleSeriesRenderer();
            rend.setColor(colours[i]);
            mRenderer.addSeriesRenderer(rend);
        }

        return ChartFactory.getPieChartView(context, statePieSeries, mRenderer);
    }


    /*
     * OUTPUT STUFF
     */
    /**
     * Calculates the data series to be used for the output volume line graph
     * @param data the raw data to be put into the series.
     *             Expects map to hold (Time of entry, Value of entry)
     */
    public boolean calcVolumeGraph(Map<Date, Integer> data) {
        volumeGraphSeries = new TimeSeries("Stoma Output Volume");
        int volume = 0;
        boolean success = true;

        try {
            //create the data set
            Date[] attributes = data.keySet().toArray(new Date[0]);
            for (Date key : attributes) {
                volume += data.get(key);    //increasing output volume total
                volumeGraphSeries.add(key, volume);
            }
        }
        catch (IndexOutOfBoundsException e) {success = false;}
        return success;
    }

    /**
     * Creates a renderer to be used with the data series to display the graph.
     * Then uses the renderer, dataset and calling context to create a graph view (the visual
     * representation of the graph)
     * @param context The context of the calling activity
     * @return volumeGraphView, the GraphicalView implementation of the volume line graph
     */
     public GraphicalView displayVolumeGraph(Context context) {
        //create the renderer
        XYSeriesRenderer renderer = new XYSeriesRenderer();
        renderer.setLineWidth(2);
        renderer.setColor(Color.BLACK);
        renderer.setPointStyle(PointStyle.CIRCLE);
        renderer.setDisplayBoundingPoints(true);
        renderer.setPointStrokeWidth(3);

        //add this renderer to the display renderer
        XYMultipleSeriesRenderer mRenderer = new XYMultipleSeriesRenderer();
        mRenderer.addSeriesRenderer(renderer);

        //change the graph display parameters
        mRenderer.setMarginsColor(Color.argb(0x00, 0xff, 0x00, 0x00));  //transparent borders
        mRenderer.setPanEnabled(false, false);  //disable panning
        mRenderer.setYAxisMin(0);   //y axis min 0
        mRenderer.setYAxisMax(2000);  //y axis max 2000
        mRenderer.addYTextLabel(500, "500 mL");
        mRenderer.addYTextLabel(1000, "1000 mL");
        mRenderer.addYTextLabel(1500, "1500 mL");
        mRenderer.addYTextLabel(2000, "2000 mL");
        mRenderer.setShowGrid(true);    //display grid lines

        XYMultipleSeriesDataset dataSet = new XYMultipleSeriesDataset();
        dataSet.addSeries(volumeGraphSeries);

        return ChartFactory.getTimeChartView(context, dataSet, mRenderer, "SHORT");
    }


    /**
     * Calculates the data series to be used for the individual volume bar graph
     * @param data the raw data to be put into the series.
     *             Expects map to hold (Time of entry, Value of entry)
     */
    public boolean calcBagGraph(Map<Date, Integer> data) {
        bagGraphSeries = new TimeSeries("Individual Bag Output Volume");
        boolean success = true;

        try {
            //create the data set
            Date[] attributes = data.keySet().toArray(new Date[0]);
            for (Date key : attributes) {
                bagGraphSeries.add(key, data.get(key));
            }
        }
        catch (IndexOutOfBoundsException e) {success = false;}
        return success;
    }

    /**
     * Creates a renderer to be used with the data series to display the graph.
     * Then uses the renderer, dataset and calling context to create a graph view (the visual
     * representation of the graph)
     * @param context The context of the calling activity
     * @return bagGraphView, the GraphicalView implementation of the volume bar graph
     */
    public GraphicalView displayBagGraph(Context context) {
        //create the renderer
        XYSeriesRenderer renderer = new XYSeriesRenderer();
        renderer.setColor(Color.BLUE);
        renderer.setDisplayChartValues(true);

        //add this renderer to the display renderer
        XYMultipleSeriesRenderer mRenderer = new XYMultipleSeriesRenderer();
        mRenderer.addSeriesRenderer(renderer);

        //change the graph display parameters
        mRenderer.setMarginsColor(Color.argb(0x00, 0xff, 0x00, 0x00));  //transparent borders
        mRenderer.setPanEnabled(false, false);  //disable panning
        mRenderer.setYAxisMin(0);   //y axis min 0
        mRenderer.setYAxisMax(1000);  //y axis max 10
        mRenderer.addYTextLabel(250, "250 mL");
        mRenderer.addYTextLabel(500, "500 mL");
        mRenderer.addYTextLabel(750, "750 mL");
        mRenderer.setShowGrid(true);    //display grid lines
        mRenderer.setBarSpacing(0.5);
        mRenderer.setBarWidth(100);

        XYMultipleSeriesDataset dataSet = new XYMultipleSeriesDataset();
        dataSet.addSeries(bagGraphSeries);

        //return ChartFactory.getBarChartView(context, dataSet, mRenderer, BarChart.Type.DEFAULT);
        return ChartFactory.getBarChartView(context, dataSet, mRenderer, BarChart.Type.DEFAULT);
    }


    /*
     * WELL BEING STUFF
     */
    /**
     * Calculates the data series to be used for the well being pie chart
     * @param data the raw data to be put into the series.
     *             Expects map to hold (Time of entry, Value of entry)
     */
    public boolean calcWellbeingChart(Map<Date, Integer> data) {
        boolean success = true;

        try {
            //first find the percentage of each day in each state
            Date[] attributes = data.keySet().toArray(new Date[0]);
            int goodTime = 0, badTime = 0;

            //record elapsed time in millis between inputs for each state
            for (Date key : attributes) {
                if (data.get(key) == 1) {   //good time
                    goodTime++;
                } else if (data.get(key) == 0) {   //bad time
                    badTime++;
                }
            }

            //add percentage of day to the data series
            wellbeingPieSeries = new CategorySeries("");
            wellbeingPieSeries.add("Good", ((double) goodTime) / ((double) (goodTime + badTime)));
            wellbeingPieSeries.add("Bad", ((double) badTime) / ((double) (goodTime + badTime)));
        }
        catch (IndexOutOfBoundsException e) {success = false;}
        return success;
    }

    /**
     * Creates a renderer to be used with the data series to display the chart.
     * Then uses the renderer, dataset and calling context to create a chart view (the visual
     * representation of the chart)
     * @param context The context of the calling activity
     * @return wellbeingView, the GraphicalView implementation of the wellbeing pie chart
     */
     public GraphicalView displayWellbeingChart(Context context) {
        //create renderer
        DefaultRenderer mRenderer = new DefaultRenderer();
        mRenderer.setStartAngle(180);
        mRenderer.setDisplayValues(false);
        mRenderer.setPanEnabled(false);

        int[] colours = {Color.GREEN, Color.RED};
        SimpleSeriesRenderer rend = null;

        for (int i = 0; i < 2; i++) {
            rend = new SimpleSeriesRenderer();
            rend.setColor(colours[i]);
            mRenderer.addSeriesRenderer(rend);
        }

        return ChartFactory.getPieChartView(context, wellbeingPieSeries, mRenderer);
    }
}