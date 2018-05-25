package com.example.simeon.cyclistpowerv3;

/**
 * Created by Simeon on 24.12.2017.
 */
//Konkreten observer koj naleduva od observerW klasata koj naslusuva od weatherSubject za
    //promena na vremeto
public class WeatherObserver extends ObserverW {
     WeatherData weatherData;
     public WeatherObserver(){}
     public WeatherObserver(WeatherSubject weatherSubject){
        this.weatherSubject=weatherSubject;
weatherSubject.addObserver(this);
     }

    @Override
    public void update() {
     this.weatherData=weatherSubject.getWeatherData();
    }

    public WeatherData getWeatherData() {
        return this.weatherData;
    }
}
