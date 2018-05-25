package com.example.simeon.cyclistpowerv3;

import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import java.util.ArrayList;
//Aktiviti za postavki na korisnik
public class CyclistSettingsActivity extends AppCompatActivity {
        double temperature;
        double pressure;
        double dewpoint;
        String url;
    final Cyclist[] cyclist = new Cyclist[1];
    //Izbor na soodvetno kopce Home za vrakanje kon login activity i PowerMeter za start na mereneje(prefrla na drugo activity)
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    return true;
                case R.id.navigation_PowerMeter:
                   Intent intent=new Intent(getApplicationContext(),PowerMeter.class);
                    intent.putExtra("Cyclist",cyclist[0]);
                   startActivity(intent);
                    }
            return false;
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cyclist_settings);
        final int []possP=new int[3];
        final int [] possR=new int[3];
        final BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        EditText userNameText= findViewById(R.id.editTextUserName);
        final String userName=this.getIntent().getStringExtra("username");
        userNameText.setText(userName);
        Spinner spinner= findViewById(R.id.spinnerRoad);
        final ArrayList<String> listroads=new ArrayList<>();
        listroads.add("concrete");
        listroads.add("asphalt");
        listroads.add("dirt");
         cyclist[0]=new Cyclist();
        ArrayAdapter<String> adapter1=new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,listroads);
        spinner.setAdapter(adapter1);
        final double[] dragCoefficient = new double[1];
        dragCoefficient[0]=1;
        //Postavka na soodveten dragCoefficient spored soodveten izbor od dropdown menu(spinner)
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                possR[0]=i;
                RoadType rd=new RoadType();
                dragCoefficient[0] =rd.getDragCoefficient(listroads.get(possR[0]));
                cyclist[0].setDragCoefficient(dragCoefficient[0]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        Spinner spinnerPosition= findViewById(R.id.spinnerPosition);
        final ArrayList<String> positionsfa=new ArrayList<>();
        positionsfa.add("Tops");
        positionsfa.add("Hoods");
        positionsfa.add("Drops");
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,positionsfa);
        spinnerPosition.setAdapter(adapter);
        final double[] cdA = new double[1];
        cdA[0]=1;
        cyclist[0].setUsername(userName);
        cyclist[0].setUserweight(10);
        //Postavka na frontalArea  spored soodveten izbor od dropdown menu(spinner) spored pozicija na vozenje
        spinnerPosition.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                possP[0]=position;
                FrontalArea fa=new FrontalArea();
                cdA[0] =fa.getFArea(positionsfa.get(possP[0]));
                cyclist[0].setFrontalArea(cdA[0]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        Spinner spinnerB= findViewById(R.id.spinnerBycicle);
        final ArrayList<String> types=new ArrayList<>();
        types.add("Standard Bycicle"); types.add("Mountain Bycicle"); types.add("Sport Bycicle");
        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,types);
        spinnerB.setAdapter(arrayAdapter);
        final int sBind[]=new int[1];
        final EditText bycicleWeidhtText= findViewById(R.id.editTextBycicleWeight);
        //Dropdown menu za izbor na soodveten  tip na velosiped so koj se odreduva tezinata na velosipedot
        spinnerB.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                sBind[0]=position;
                BycicleFactory factory=new BycicleFactory();
                /* here we call our BycicleFactory to create our bicycle for us*/
                Bycicle bycicle=factory.createBycicle(types.get(sBind[0]));
                bycicleWeidhtText.setText(bycicle.getWeigth()+"");
                cyclist[0].setBycicleWeight(bycicle.getWeigth());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        final EditText userWeightText= findViewById(R.id.editTextUserWeight);
        cyclist[0].setBycicleWeight(Double.parseDouble(bycicleWeidhtText.getText().toString()));
        cyclist[0].setUserweight(Double.parseDouble(userWeightText.getText().toString()));
        //Soodvetni text views za postavuvanje na korisnicka tezina i manuelno postavuvanje na tezina na velosipedot
        bycicleWeidhtText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                cyclist[0].setBycicleWeight(Double.parseDouble(bycicleWeidhtText.getText().toString()));
            }
        });
        userWeightText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                cyclist[0].setUserweight(Double.parseDouble(userWeightText.getText().toString()));
            }
        });

    }



}
