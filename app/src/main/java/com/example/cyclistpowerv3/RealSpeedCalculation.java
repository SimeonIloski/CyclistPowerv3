package com.example.simeon.cyclistpowerv3;

import android.location.Location;
import android.widget.Toast;

/**
 * Created by Simeon on 15.12.2017.
 */

public class RealSpeedCalculation implements Calculation {
    Cyclist cyclist;
    Location endLocation, startLocation;
    double companiedWeight;
    double speed;
    int time;
    private double rho;

    public RealSpeedCalculation() {

    }


    /*calculate the real speed */
    public double getSpeed(int time) {
    return  (double) endLocation.getSpeed();
    }


    /*calculate the dragForce*/
    private double Fdrag() {
        return 0.5 * this.cyclist.getFrontalArea() * 1 * Math.pow(speed, 2)*rho;
    }

    /*calculate the distance*/
    public double getDistance(Location start, Location end) {
        return start.distanceTo(end);
    }

    /*calculate the rollForce*/
    private double Froll() {
        return 9.0867 * Math.cos(Math.atan(endLocation.getAltitude() / 2500)) * companiedWeight * this.cyclist.getDragCoefficient();
    }

    /*calculate the fravitationForce*/
    private double Fgravity() {
        return 9.0867 * Math.sin(Math.atan(endLocation.getAltitude() / 2500)) * companiedWeight;
    }

    /*calculate the resistForce*/
    private double Fresist() {
        return Froll() + Fdrag() + Fgravity();
    }

    private double Work() {
        return Fresist() * getDistance(this.startLocation, this.endLocation);
    }

    private double PWheel() {
        return Fresist() * speed;
    }

    /*the cyclist power*/
    public double getCyclistPower() {
        return Math.pow((1 - (5 / 100)), -1) * Fresist() * speed;
    }

    /*the cyclist speed*/
    public double getCyclistSpeed() {
        return speed;
    }

    /*the altitude*/
    public double getAltitude() {
        return endLocation.getAltitude();
    }


    @Override
    public double calculatePower(Cyclist cyclist, Location startLocation, Location endLocation, double combainedWight, int time,double rho) {
        this.cyclist = cyclist;
        this.endLocation = endLocation;
        this.startLocation = startLocation;
        this.companiedWeight = combainedWight;
        this.time = time;
        this.speed = getSpeed(time);
        this.rho=rho;
        return getCyclistPower();
    }

    @Override
    public double getSpeed() {
        return this.speed;
    }
}
