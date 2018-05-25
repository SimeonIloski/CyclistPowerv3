package com.example.simeon.cyclistpowerv3;

import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

import static java.lang.System.exit;

public class PowerMeter extends AppCompatActivity {
    public  double temperature;
    public double pressure;
    public double dewpoint;
    TextView tempView;
    TextView altitudeTextView;
    String calculationType;
    ArrayList<SingleMeasurment> listOfPowerMeasurments;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_power_meter);
        final Cyclist cyclist=this.getIntent().getParcelableExtra("Cyclist");
        final EditText editTextPower= findViewById(R.id.editTextPower);
        final EditText editTextSpeed= findViewById(R.id.editTextSpeed);
        Button buttonStart= findViewById(R.id.buttonStart);
        final Button buttonStop= findViewById(R.id.buttonStop);
        listOfPowerMeasurments=new ArrayList<>();
        final GPSTracker gpsTracker=new GPSTracker(getApplicationContext());
        tempView=(TextView) findViewById(R.id.textViewT);
        altitudeTextView=(TextView) findViewById(R.id.textViewDE);
        Spinner spinnerStretegy=(Spinner) findViewById(R.id.spinnerStrategy);
        final ArrayList<String> strategiesList=new ArrayList<>();
        strategiesList.add("Simulated Strategy");
        strategiesList.add("Real Calculation Strategy");
        spinnerStretegy.setAdapter(new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,strategiesList));
        spinnerStretegy.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                calculationType=strategiesList.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        Location startLocation=new Location("");
           /*this is our first location so we don't neeed to update it so we are going to use the
           Locationobserver only once. Since we are gettin the location from the phone not from some
           web service we must find the location from some activity class(that's why we call gpsTracker.getLocation()
           in here althought
            */
        if (gpsTracker.canGetLocation()) {
            LocationSubject locationSubject=new LocationSubject();
            locationSubject.setLocation(gpsTracker.getLocation());
            LocationObserver observer= new LocationObserver(locationSubject);
            locationSubject.notifyObservers();
            startLocation = observer.getLocation();
            locationSubject.removeObserver(observer);
        }

        final Location finalStartLocation = startLocation;
        final Handler timerHandler=new Handler();
        final int i[]=new int [1];
        i[0]=0;

        final  WeatherData[] weatherData=new WeatherData[1];
          /*timer android os handler*/
        final Runnable timmerRunnable=new Runnable() {
            @Override
            public void run() {
                /*here we are going to get updated locations from the locationobserver*/
                if(gpsTracker.canGetLocation()&& finalStartLocation!=null){
                    LocationSubject locationSubject=new LocationSubject();
                    Location location=finalStartLocation;
                    if(calculationType.equals("Real Calculation Strategy")) {
                        gpsTracker.onLocationChanged(location);
                    }
                    else{
                        location=gpsTracker.getLocation();
                    }
                    locationSubject.setLocation(location);
                    LocationObserver observer=new LocationObserver(locationSubject);
                    locationSubject.notifyObservers();
                    final Location currentLocation=observer.getLocation();
                    if(currentLocation!=null) {
                        ++i[0];
                        final CalculationData[] calculation = new CalculationData[1];
                        calculation[0] = new CalculationData(cyclist, finalStartLocation, currentLocation, i[0], weatherData[0],calculationType);
                        double p1=calculation[0].getPower();
                        if(p1<0){Math.abs(p1);}
                        double p2=calculation[0].getSpeed();
                       //logika za boenje na vrednostite spored toa kolkav opseg dostignale*/
                        if(p1>=30) {editTextPower.setTextColor(Color.RED);}
                        else if (p1<18) {editTextPower.setTextColor(Color.GREEN);}
                        else { editTextPower.setTextColor(Color.YELLOW);}
                        if(p2>8) {editTextSpeed.setTextColor(Color.RED);}
                        else if (p2<6) {editTextSpeed.setTextColor(Color.GREEN);}
                        else { editTextSpeed.setTextColor(Color.YELLOW);}

                        editTextPower.setText(String.format("%.2f  W", p1));
                        editTextSpeed.setText(String.format("%.2f  Km/h", p2));
                        listOfPowerMeasurments.add(new SingleMeasurment(p1, cyclist.getUsername(), new Date(), p2));
                        altitudeTextView.setText(String.format("%.2f",currentLocation.getAltitude()));
                        timerHandler.postDelayed(this, 2000);
                    }
                }
                else{
                    Toast.makeText(getApplicationContext(),"Can't get location. Please try again",Toast.LENGTH_SHORT).show();
                }
            }
        };
        /* start  measurment button logic*/
        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"Calculating please be patitent",Toast.LENGTH_SHORT).show();
                //baranje za promena na vremeto
                updateWeatherData("Skopje, MK");
                weatherData[0]=new WeatherData(temperature,pressure,dewpoint);
                //povikubanje na soodvetniot observer za promena na vremeto
                tempView.setText(weatherData[0].getTemperature()+"C");
                if(weatherData[0].getTemperature()<0){
                    double temp1=weatherData[0].getTemperature()*(-1);
                    weatherData[0].setTemperature(temp1);
                }
                WeatherSubject weatherSubject=new WeatherSubject();
                weatherSubject.setWeatherData(weatherData[0]);
                WeatherObserver observerW=new WeatherObserver(weatherSubject);
                weatherSubject.notifyObservers();
                weatherData[0]=observerW.getWeatherData();
                timerHandler.postDelayed(timmerRunnable,2000);
            }
        });
        //kopce za stop na merenjeto*/
        buttonStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timerHandler.removeCallbacks(timmerRunnable);
                i[0]=0;
              //  FirebaseDatabase database=FirebaseDatabase.getInstance();
                //DatabaseReference databaseReference= database.getReference();

            }
        });
        //startuvanje na staistikata*/
        Button buttonStatistics= findViewById(R.id.buttonStatistics);
        buttonStatistics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
/*                for(int i=0;i<listOfPowerMeasurments.size();i++) {
                    databaseReference.child("Measurments").child(cyclist.getUsername()).setValue(listOfPowerMeasurments.get(i).getName());
                    databaseReference.child("Measurments").child(cyclist.getUsername()).setValue(listOfPowerMeasurments.get(i).getPower());
                    databaseReference.child("Measurments").child(cyclist.getUsername()).setValue(listOfPowerMeasurments.get(i).getSpeed());
                    databaseReference.child("Measurments").child(cyclist.getUsername()).setValue(listOfPowerMeasurments.get(i).getDate());
                    databaseReference.push();

                }*/
                Intent intent=new Intent(getApplicationContext(),StatisticsActivity.class);
                intent.putExtra("Cyclist",cyclist);
                intent.putParcelableArrayListExtra("listata",listOfPowerMeasurments);
                startActivity(intent);
            }
        });
        //getting the weater data via WeatherObserver*/
        updateWeatherData("Skopje, MK");




        //Connecting to the weather api methods
    }
    public void updateWeatherData(final String city){
        final Handler handler=new Handler();
        new Thread(){
            public void run(){
                final JSONObject json = RemoteFetch.getJSON(getApplicationContext(), city);
                if(json == null){
                    handler.post(new Runnable(){
                        public void run(){
                            Toast.makeText(getApplicationContext(),
                                    "City not found",Toast.LENGTH_LONG).show();
                        }
                    });
                } else {
                    handler.post(new Runnable(){
                        public void run(){
                            try {
                                renderWeather(json);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            }
        }.start();
    }
// metodi za povrzuvanje so apito za vremenska prognoza
    public void renderWeather(JSONObject json) throws JSONException {
        JSONObject main=json.getJSONObject("main");
        temperature=main.getDouble("temp");
        pressure=Double.parseDouble(main.getString("pressure"));
        dewpoint= main.getDouble("dew_point");
    }
    public WeatherData renderweather(JSONObject json) throws JSONException {
        JSONObject main=json.getJSONObject("main");
        temperature=main.getDouble("temperature");
        pressure=Double.parseDouble(main.getString("pressure"));
        dewpoint= main.getDouble("dew_point");
        return new WeatherData(temperature,pressure,dewpoint);
    }

}
