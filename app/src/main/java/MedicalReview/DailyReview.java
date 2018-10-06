package MedicalReview;


import android.content.Context;
import android.graphics.Color;

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

import java.util.Date;
import java.util.Map;

public class DailyReview {
    private PieChart stateChart;
    private PieChart wellbeingChart;
    private LineChart stateGraph;
    private LineChart volumeGraph;
    private BarChart bagGraph;

    public DailyReview() {
        stateChart = null;
        wellbeingChart = null;
        stateGraph = null;
        volumeGraph = null;
        bagGraph = null;
    }

    public DailyReview(DailyReview review) {
        stateChart = review.stateChart;
        wellbeingChart = review.wellbeingChart;
        stateGraph = review.stateGraph;
        volumeGraph = review.volumeGraph;
        bagGraph = review.bagGraph;
    }

    /*
    STATE STUFF
     */
    //state line graph - plots values against date
    //Map<DateTime of input, State at that time>
    public void calcStateGraph (Map<Date, Integer> data) {
        TimeSeries series = new TimeSeries("State Progression");

        //create the data set
        Date[] attributes = data.keySet().toArray(new Date[0]);
        for (Date key: attributes) {
            series.add(key, data.get(key));
        }

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
        dataSet.addSeries(series);

        stateGraph = new LineChart(dataSet, mRenderer);
    }

    //display the state graph
    public void displayStateGraph(Context context) {
        //need to do the chartfactory stuff here
        GraphicalView chartView = new GraphicalView(context, stateGraph);
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
        CategorySeries series = new CategorySeries("");
        series.add("Green", ((double)greenTime)/((double)(greenTime + yellowTime + redTime)));
        series.add("Yellow",((double)yellowTime)/((double)(greenTime + yellowTime + redTime)));
        series.add("Red", ((double)redTime)/((double)(greenTime + yellowTime + redTime)));

        DefaultRenderer mRenderer = new DefaultRenderer();
        mRenderer.setStartAngle(90);
        mRenderer.setDisplayValues(false);

        stateChart = new PieChart(series, mRenderer);
    }

    public void displayStateChart(Context context) {
        //need to do the chartfactory stuff here
        GraphicalView chartView = new GraphicalView(context, stateChart);
    }

    /*
    OUTPUT STUFF
     */
    //total output volume as line graph
    //Map<DateTime of input, Volume of that input>
    public void calcVolumeGraph(Map<Date, Integer> data) {
        TimeSeries series = new TimeSeries("Stoma Output Volume");
        int volume = 0;

        //create the data set
        Date[] attributes = data.keySet().toArray(new Date[0]);
        for (Date key: attributes) {
            volume += data.get(key);    //increasing output volume total
            series.add(key, volume);
        }

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
        dataSet.addSeries(series);

        volumeGraph = new LineChart(dataSet, mRenderer);
    }

    public void displayVolumeGraph(Context context) {
        //need to do the chartfactory stuff here
        GraphicalView chartView = new GraphicalView(context, volumeGraph);
    }

    //individual bag volume as bar graph
    //Map<DateTime of input, Volume of that input>
    public void calcBagGraph(Map<Date, Integer> data) {
        TimeSeries series = new TimeSeries("Individual Bag Output Volume");

        //create the data set
        Date[] attributes = data.keySet().toArray(new Date[0]);
        for (Date key: attributes) {
            series.add(key, data.get(key));
        }

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
        dataSet.addSeries(series);

        bagGraph = new BarChart(dataSet, mRenderer, BarChart.Type.DEFAULT);
    }

    public void displayBagGraph(Context context) {
        //need to do the chartfactory stuff here
        GraphicalView chartView = new GraphicalView(context, bagGraph);
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
        CategorySeries series = new CategorySeries("");
        series.add("Good", ((double)goodTime)/((double)(goodTime + badTime)));
        series.add("Bad", ((double)badTime)/((double)(goodTime + badTime)));

        DefaultRenderer mRenderer = new DefaultRenderer();
        mRenderer.setStartAngle(90);
        mRenderer.setDisplayValues(false);

        wellbeingChart = new PieChart(series, mRenderer);
    }

    public void displayWellbeingChart(Context context) {
        //need to do the chartfactory stuff here
        GraphicalView chartView = new GraphicalView(context, wellbeingChart);
    }
}