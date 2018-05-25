package com.example.simeon.cyclistpowerv3;

/**
 * Created by Simeon on 24.12.2017.
 */
/* abstract class for observer of weather data */
public abstract class ObserverW {
    WeatherSubject weatherSubject;
    public abstract void update();
}
