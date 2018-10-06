package capstonegroup2.dataapp;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.achartengine.GraphicalView;
import org.achartengine.chart.AbstractChart;
import org.achartengine.chart.LineChart;

public class DailyReviewGraph extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_review_graph);
    }

    //create and display the chart
    public void displayGraph(AbstractChart chart) {
        GraphicalView view = new GraphicalView(this, chart);
        view.draw();
    }
}
