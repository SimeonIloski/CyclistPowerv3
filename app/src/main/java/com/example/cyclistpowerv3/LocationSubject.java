package com.example.simeon.cyclistpowerv3;

import android.location.Location;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by Simeon on 20.12.2017.
 */
/* this is our Subject class for LocationObserver*/
public class LocationSubject {
    private Set<Observer> observerSet=new HashSet<>();
    Location location;
    /*adding observers*/
    public void  addObserver(Observer observer){
        observerSet.add(observer);
    }
    /*getting the location*/
    public  Location getLocation(){
        return this.location;
    }
    /*setting the updated location and notifies the observers*/
    public void  setLocation(Location location){
        this.location=location;
        notifyObservers();
    }
    public void removeObserver(Observer observer){
        this.observerSet.remove(observer);
    }
    /*the method for notifying the observers*/
    public void notifyObservers(){
        Iterator<Observer> observerIterator=observerSet.iterator();
        while(observerIterator.hasNext()){
            observerIterator.next().update();
        }
    }

}
