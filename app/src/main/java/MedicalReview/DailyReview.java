package MedicalReview;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Parcel;
import android.os.Parcelable;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.BarChart;
import org.achartengine.chart.LineChart;
import org.achartengine.chart.PieChart;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.CategorySeries;
import org.achartengine.model.TimeSeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import java.io.CharArrayReader;
import java.util.Date;
import java.util.Map;


public class DailyReview implements Parcelable {
    private TimeSeries stateGraphSeries;
    private CategorySeries statePieSeries;
    private TimeSeries volumeGraphSeries;
    private TimeSeries bagGraphSeries;
    private CategorySeries wellbeingPieSeries;

    public DailyReview() {
        stateGraphSeries = null;
        statePieSeries = null;
        volumeGraphSeries = null;
        bagGraphSeries = null;
        wellbeingPieSeries = null;
    }

    public DailyReview(DailyReview review) {
        stateGraphSeries = review.stateGraphSeries;
        statePieSeries = review.statePieSeries;
        volumeGraphSeries = review.volumeGraphSeries;
        bagGraphSeries = review.bagGraphSeries;
        wellbeingPieSeries = review.wellbeingPieSeries;
    }

    //PARCELABLE INTERFACE IMPLEMENTATION - FACILITATES PASSING TO ACTIVITY
    /*
     * The methods for parcelable so this object can be sent to an activity via intent
     */
    protected DailyReview(Parcel in) {
        //Retrieve in FIFO from the way the values were added
        stateGraphSeries = (TimeSeries) in.readValue(TimeSeries.class.getClassLoader());
        statePieSeries = (CategorySeries) in.readValue(CategorySeries.class.getClassLoader());
        volumeGraphSeries = (TimeSeries) in.readValue(TimeSeries.class.getClassLoader());
        bagGraphSeries = (TimeSeries) in.readValue(TimeSeries.class.getClassLoader());
        wellbeingPieSeries = (CategorySeries) in.readValue(CategorySeries.class.getClassLoader());
    }

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
    STATE STUFF
    */
    //state line graph - plots values against date
    //expects map to have date and value pairs of state data
    public void calcStateGraph (Map<Date, Integer> data) {
        stateGraphSeries = new TimeSeries("State Progression");

        //create the data set
        Date[] attributes = data.keySet().toArray(new Date[0]);
        for (Date key: attributes) {
            stateGraphSeries.add(key, data.get(key));
        }
    }

    //display the state graph
    public GraphicalView displayStateGraph(Context context) {
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
        mRenderer.setYAxisMax(10);  //y axis max 10
        mRenderer.addYTextLabel(0, "Green");
        mRenderer.addYTextLabel(5, "Yellow");
        mRenderer.addYTextLabel(8, "Red");
        mRenderer.setShowGrid(true);    //display grid lines

        XYMultipleSeriesDataset dataSet = new XYMultipleSeriesDataset();
        dataSet.addSeries(stateGraphSeries);


        //Intent stateGraphIntent = ChartFactory.getLineChartIntent(context, dataSet, mRenderer, "State Line Graph");

        GraphicalView stateGraphView = ChartFactory.getLineChartView(context, dataSet, mRenderer);

        return stateGraphView;
    }


    //state pie chart
    //Map<DateTime of input, state value at that time>
    public void calcStateChart(Map<Date, Integer> data) {
        //first find the percentage of each day in each state
        Date[] attributes = data.keySet().toArray(new Date[0]);
        long greenTime = 0, yellowTime = 0, redTime = 0;
        Date currTime, compTime = attributes[0];

        //record elapsed time in millis between inputs for each state
        for (Date key: attributes) {
            if (data.get(key) > 0 && data.get(key) < 5) {   //green time
                currTime = key;
                greenTime += currTime.getTime() - compTime.getTime();
                compTime = key; //update the time to compare to the most recent record
            }
            else if (data.get(key) > 4 && data.get(key) < 8) {  //yellow time
                currTime = key;
                yellowTime += currTime.getTime() - compTime.getTime();
                compTime = key; //update the time to compare to the most recent record
            }
            else if (data.get(key) > 7 && data.get(key) < 11) { //red time
                currTime = key;
                redTime += currTime.getTime() - compTime.getTime();
                compTime = key; //update the time to compare to the most recent record
            }
        }

        //add percentage of day to the data series
        statePieSeries = new CategorySeries("");
        statePieSeries.add("Green", ((double)greenTime)/((double)(greenTime + yellowTime + redTime)));
        statePieSeries.add("Yellow",((double)yellowTime)/((double)(greenTime + yellowTime + redTime)));
        statePieSeries.add("Red", ((double)redTime)/((double)(greenTime + yellowTime + redTime)));
    }

    public GraphicalView displayStateChart(Context context) {
        //create the renderer
        DefaultRenderer mRenderer = new DefaultRenderer();
        mRenderer.setStartAngle(90);
        mRenderer.setDisplayValues(false);

        //create and return the chart intent
        //Intent stateChartIntent = ChartFactory.getPieChartIntent(context, statePieSeries, mRenderer, "State Pie Chart");

        GraphicalView stateChartView = ChartFactory.getPieChartView(context, statePieSeries, mRenderer);

        return stateChartView;
    }


    /*
    OUTPUT STUFF
     */
    //total output volume as line graph
    //Map<DateTime of input, Volume of that input>
    public void calcVolumeGraph(Map<Date, Integer> data) {
        volumeGraphSeries = new TimeSeries("Stoma Output Volume");
        int volume = 0;

        //create the data set
        Date[] attributes = data.keySet().toArray(new Date[0]);
        for (Date key: attributes) {
            volume += data.get(key);    //increasing output volume total
            volumeGraphSeries.add(key, volume);
        }
    }

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
        mRenderer.setYAxisMax(10);  //y axis max 10
        mRenderer.setShowGrid(true);    //display grid lines

        XYMultipleSeriesDataset dataSet = new XYMultipleSeriesDataset();
        dataSet.addSeries(volumeGraphSeries);

        //Intent volumeGraphIntent = ChartFactory.getLineChartIntent(context, dataSet, mRenderer, "Output Volume Graph");

        GraphicalView volumeGraphView = ChartFactory.getLineChartView(context, dataSet, mRenderer);

        return volumeGraphView;
    }


    //individual bag volume as bar graph
    //Map<DateTime of input, Volume of that input>
    public void calcBagGraph(Map<Date, Integer> data) {
        bagGraphSeries = new TimeSeries("Individual Bag Output Volume");

        //create the data set
        Date[] attributes = data.keySet().toArray(new Date[0]);
        for (Date key: attributes) {
            bagGraphSeries.add(key, data.get(key));
        }
    }

    public GraphicalView displayBagGraph(Context context) {
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
        mRenderer.setYAxisMax(10);  //y axis max 10
        mRenderer.setShowGrid(true);    //display grid lines

        XYMultipleSeriesDataset dataSet = new XYMultipleSeriesDataset();
        dataSet.addSeries(bagGraphSeries);

        //Intent bagGraphIntent = ChartFactory.getBarChartIntent(context, dataSet, mRenderer, BarChart.Type.DEFAULT, "Bag Output Volume");

        GraphicalView bagGraphView = ChartFactory.getBarChartView(context, dataSet, mRenderer, BarChart.Type.DEFAULT);

        return bagGraphView;
    }


    /*
    WELL BEING STUFF
     */
    //amount of day categorised as 'good' in pie chart
    //Map<DateTime of input, Well being string>
    public void calcWellbeingChart(Map<Date, Integer> data) {
        //first find the percentage of each day in each state
        Date[] attributes = data.keySet().toArray(new Date[0]);
        long goodTime = 0, badTime = 0;
        Date currTime, compTime = attributes[0];

        //record elapsed time in millis between inputs for each state
        for (Date key: attributes) {
            if (data.get(key) == 1) {   //good time
                currTime = key;
                goodTime += currTime.getTime() - compTime.getTime();
                compTime = key; //update the time to compare to the most recent record
            }
            else if (data.get(key) == 0) {   //bad time
                currTime = key;
                badTime += currTime.getTime() - compTime.getTime();
                compTime = key; //update the time to compare to the most recent record
            }
        }

        //add percentage of day to the data series
        wellbeingPieSeries = new CategorySeries("");
        wellbeingPieSeries.add("Good", ((double)goodTime)/((double)(goodTime + badTime)));
        wellbeingPieSeries.add("Bad", ((double)badTime)/((double)(goodTime + badTime)));
    }

    public GraphicalView displayWellbeingChart(Context context) {
        //create renderer
        DefaultRenderer mRenderer = new DefaultRenderer();
        mRenderer.setStartAngle(90);
        mRenderer.setDisplayValues(false);

        //Intent wellbeingIntent = ChartFactory.getPieChartIntent(context, wellbeingPieSeries, mRenderer, "Wellbeing Percentage");

        GraphicalView wellbeingView = ChartFactory.getPieChartView(context, wellbeingPieSeries, mRenderer);

        return wellbeingView;
    }
}