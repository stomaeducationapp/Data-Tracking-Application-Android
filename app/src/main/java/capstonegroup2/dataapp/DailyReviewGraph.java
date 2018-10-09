package capstonegroup2.dataapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import MedicalReview.DailyReview;

import MedicalReview.ReviewHandler.TYPE;

public class DailyReviewGraph extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_review_graph);
    }

    //create and display the chart
    public void displayGraph(DailyReview current, TYPE typeOfChart) {
        Intent graphIntent;

        switch (typeOfChart) {
            case STATELINE:
                graphIntent = current.displayStateGraph(this);
                break;
            case STATEPIE:
                graphIntent = current.displayStateChart(this);
                break;
            case VOLUMELINE:
                graphIntent = current.displayVolumeGraph(this);
                break;
            case BAGBAR:
                graphIntent = current.displayBagGraph(this);
                break;
            case WELLBEING:
                graphIntent = current.displayWellbeingChart(this);
                break;
            default:
                graphIntent = null;
        }
        startActivity(graphIntent);
    }
}
