package com.example.simeon.cyclistpowerv3;


/**
 * Created by Simeon on 20.12.2017.
 */
/*our abstract Observer class for the location */
public abstract class Observer {
    LocationSubject locationSubject;
    public   abstract void update();
}
