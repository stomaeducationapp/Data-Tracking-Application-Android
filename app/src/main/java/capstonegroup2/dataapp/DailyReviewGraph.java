package capstonegroup2.dataapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import MedicalReview.DailyReview;

import MedicalReview.ReviewHandler.TYPE;

public class DailyReviewGraph extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_review_graph);

        spinner = findViewById(R.id.graphSpinner);

        spinner.setOnItemSelectedListener(this);

        //create set of spinner elements
        List<String> options = new ArrayList<>();
        options.add("State Graph");
        options.add("State Pie Chart");
        options.add("Volume Graph");
        options.add("Output Graph");
        options.add("Wellbeing Pie Chart");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, options);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);
    }

    //create and display the chart
    public void displayGraph(DailyReview current, String typeOfChart) {
        Intent graphIntent;

        switch (typeOfChart) {
            case "State Graph":
                graphIntent = current.displayStateGraph(this);
                break;
            case "State Pie Chart":
                graphIntent = current.displayStateChart(this);
                break;
            case "Volume Graph":
                graphIntent = current.displayVolumeGraph(this);
                break;
            case "Output Graph":
                graphIntent = current.displayBagGraph(this);
                break;
            case "Wellbeing Pie Chart":
                graphIntent = current.displayWellbeingChart(this);
                break;
            default:
                graphIntent = null;
        }
        startActivity(graphIntent);

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
        String selected = adapterView.getItemAtPosition(position).toString();

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        //auto generated inherited method
    }
}
