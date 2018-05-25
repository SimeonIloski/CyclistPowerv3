package com.example.simeon.cyclistpowerv3;

import android.location.Location;

/**
 * Created by Simeon on 20.12.2017.
 */
/*this class inherit the Observer class and getting updated Location
 from  the LocationSubject class
 */
public class LocationObserver extends Observer {
    Location location;
    public LocationObserver(){}
    public LocationObserver(LocationSubject subject){
        this.locationSubject=subject;
        this.locationSubject.addObserver(this);
    }
    /*the update method called by Locationsubect notifyObserver method*/
    @Override
    public void update() {
        this.location=this.locationSubject.getLocation();
    }

    public Location getLocation() {
        return location;
    }
}
