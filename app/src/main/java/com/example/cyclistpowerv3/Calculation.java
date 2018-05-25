package com.example.simeon.cyclistpowerv3;

import android.location.Location;

/**
 * Created by Simeon on 15.12.2017.
 */
/*Calculation interface*/
public interface Calculation {
    double calculatePower(Cyclist cyclist, Location startLocation, Location endLocation, double combainedWight, int time, double rho);
    double getSpeed();
}
