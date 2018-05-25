package com.example.simeon.cyclistpowerv3;

/**
 * Created by Simeon on 24.12.2017.
 */
//klasa za prestavuvanje na podatocite za vremeto
public class WeatherData {
    double temperature;
    double airpresure;
    double dewPoint;

    public WeatherData(double temperature, double humidity, double dewPoint){
        this.dewPoint=dewPoint;
        this.airpresure=humidity;
        this.temperature=temperature;

            }
     public  WeatherData(){}
    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public double getAirpresure() {
        return airpresure;
    }

    public void setAirpresure(double airpresure) {
        this.airpresure = airpresure;
    }

    public double getDewPoint() {
        return dewPoint;
    }

    public void setDewPoint(double dewPoint) {
        this.dewPoint = dewPoint;
    }
    @Override
    public  String toString(){
        return String.format("Temperature" + temperature +"C" + "\n" + "Air Pressure:"+airpresure +"hpa"+"\n"+"Dew Point");
    }
}
