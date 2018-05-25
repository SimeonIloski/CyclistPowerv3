package com.example.simeon.cyclistpowerv3;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by Simeon on 24.12.2017.
 */
//Klasa koja gi izvestuva observerite deka vremeto se promenilo
public class WeatherSubject  {
    protected Set<ObserverW> observers=new HashSet<>();
    WeatherData weatherData;
    public void  addObserver(ObserverW observer){
        observers.add(observer);
    }
    /*getting the weather Data*/
    public  WeatherData getWeatherData(){
        return this.weatherData;
    }
    /*setting the updated weatherData and notifies the observers*/
    public void  setWeatherData(WeatherData weatherData){
        this.weatherData=weatherData;
        notifyObservers();
    }
    public void removeObserver(ObserverW observer){
        this.observers.remove(observer);
    }
    /*the method for notifying the observers*/
    public void notifyObservers(){
        Iterator<ObserverW> observerIterator=observers.iterator();
        while(observerIterator.hasNext()){
            observerIterator.next().update();
        }
    }

}
