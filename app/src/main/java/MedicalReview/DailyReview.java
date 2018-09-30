package MedicalReview;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.R;

import java.sql.Date;
import java.util.Map;

public class DailyReview {
    private static String[] HOURS = {
            "9:00", "10:00", "11:00", "12:00", "1:00", "2:00",
            "3:00", "4:00", "5:00", "6:00", "7:00", "8:00",
            "9:00", "10:00", "11:00", "12:00", "1:00", "2:00",
            "3:00", "4:00", "5:00", "6:00", "7:00", "8:00"
    };


    public DailyReview() {

    }

    public boolean generateGraph(String[] data) {
        boolean success = true;



        return success;
    }


    /*
        Map<Double, Integer> stateData
        Key should be the time as a Long (number of milliseconds since epoch)
        Value should be the corresponding stoma state value (Integer from 1-10)
     */
    public boolean calcStateGraph(Map<Long, Integer> stateData) {
        boolean success = false;
        Long[] attributes = stateData.keySet().toArray(new Long[stateData.size()]);
        DataPoint[] points = new DataPoint[attributes.length];
        int ii = 0;

        for (Long key: attributes) {
            points[ii] = new DataPoint(new Date(attributes[ii]), stateData.get(key));   //data point with datetime and value
        }

        GraphView graph = (GraphView) findViewById(R.id.graph);
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(points);

        graph.addSeries(series);

        //use static labels to keep graph bounds constant
        StaticLabelsFormatter formatter = new StaticLabelsFormatter(graph);
        formatter.setHorizontalLabels(HOURS);
        formatter.setVerticalLabels(new String[]{"1","2","3","4","5","6","7","8","9","10"});

        //add the state names to the other y axis
        StaticLabelsFormatter formatter2 = new StaticLabelsFormatter(graph);
        formatter2.setVerticalLabels(new String[]{"Green", "Yellow", "Red"});
        graph.getSecondScale().addSeries(series);
        graph.getSecondScale().setLabelFormatter(formatter2);

        return success;
    }
}
