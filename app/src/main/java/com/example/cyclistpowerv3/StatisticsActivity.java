package com.example.simeon.cyclistpowerv3;

import android.graphics.Color;
import android.provider.CalendarContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;
import java.util.Collections;

public class StatisticsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);
        Cyclist cyclist=this.getIntent().getParcelableExtra("Cyclist");
        ArrayList<SingleMeasurment> singleMeasurmentArrayList=new ArrayList<>();
        singleMeasurmentArrayList=this.getIntent().getParcelableArrayListExtra("listata");
        final ArrayList<SingleMeasurment> singleMeasurmentArrayList1=this.getIntent().getParcelableArrayListExtra("listata");
        Collections.sort(singleMeasurmentArrayList);
        //finding min, max values
        final SingleMeasurment minValue=singleMeasurmentArrayList.get(0);
        final SingleMeasurment maxValue=singleMeasurmentArrayList.get(singleMeasurmentArrayList.size()-1);
        double sumPower=0; double sumSpeed=0;
        for(SingleMeasurment s:singleMeasurmentArrayList){
            sumPower+=s.getPower();
            sumSpeed+=s.getSpeed();
        }
        //finding average values
        sumPower/=singleMeasurmentArrayList.size();
        sumSpeed/=singleMeasurmentArrayList.size();

        //filling the spinner and geting max/min/average by choise of the user
        Spinner spinner=(Spinner) findViewById(R.id.spinnerMinMax);
        ArrayList<String> list=new ArrayList<>();
        list.add("MIN"); list.add("MAX"); list.add("AVERAGE");
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,list);
        spinner.setAdapter(adapter);
        final TextView showMMA=(TextView) findViewById(R.id.textViewMMA);
        final double finalSumPower = sumPower;
        final double finalSumSpeed = sumSpeed;
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
                if(pos ==0){
                    showMMA.setText(String.format("Minimum power is: %.2f \n Minimum speed is %.2f",minValue.getPower(),minValue.getSpeed()));
                }
                else if(pos==1){
                    showMMA.setText(String.format("Maximum power is: %.2f \n Maximum speed is %.2f",maxValue.getPower(),maxValue.getSpeed()));
                }
                else{
                    showMMA.setText(String.format("Average power is: %.2f \n Average speed is %.2f",finalSumPower,finalSumSpeed));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        final double  maxValue1=maxValue.getPower();
        //Buttom for showing graph of the statistics of the meassurments
        Button buttonStatisticsGraph=(Button) findViewById(R.id.buttonGraph);
        buttonStatisticsGraph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GraphView graphView=(GraphView) findViewById(R.id.graph);
                DataPoint []dataPoints=new DataPoint[singleMeasurmentArrayList1.size()];
                int i=0;
                for(SingleMeasurment sm: singleMeasurmentArrayList1) {
                dataPoints[i]=new DataPoint(i,sm.getPower());
                i++;
                }
                BarGraphSeries<DataPoint> barGraphSeries=new BarGraphSeries<>(dataPoints);
                barGraphSeries.setAnimated(true);
                barGraphSeries.setColor(Color.RED);
                graphView.addSeries(barGraphSeries);
                graphView.setMinimumHeight((int) maxValue1);
                graphView.setMinimumWidth(singleMeasurmentArrayList1.size());

            }
        });

    }



}
