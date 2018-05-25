package com.example.simeon.cyclistpowerv3;

import android.location.Location;
import android.widget.Toast;

import java.util.Random;

/**
 * Created by Simeon on 15.12.2017.
 */

public class SimulatedSpeedCalculation implements Calculation {

    Cyclist cyclist;
    Location endLocation,startLocation;
    double companiedWeight;
    double speed;
    int time;
    double rho;

    public  SimulatedSpeedCalculation(){

    }

    Random random = new Random();
    boolean first=true;
    boolean isPlus=true;
    /*this method simulate speed */
    @Override
    public String toString() {
        return time+""+ speed+""+companiedWeight+"";
    }

    public  double getSimSpeed() {
        double mSpeed=0;
        if (first) {
            mSpeed = random.nextInt(10);
            first = false;
        } else {
            if (isPlus) {
                int temp = random.nextInt(4);
                if (mSpeed + (temp) < 15) {
                    mSpeed += temp;
                } else {
                    mSpeed += 0;
                }
                isPlus = true;
            }
            if (!isPlus) {
                int temp = random.nextInt(2);
                if (mSpeed - (temp) > 0) {
                    mSpeed -= temp;
                } else {
                    mSpeed += 0;
                }
                isPlus = true;
            }
        }
        return mSpeed;

    }

    /*calculate the dragForce*/
    private double Fdrag(){
        return 0.5 * this.cyclist.getFrontalArea()*1* Math.pow(this.speed,2)*rho;
    }
    /*calculate the distance*/
    public  double getDistance(Location start, Location end){
        return  start.distanceTo(end);
    }
    /*calculate the rollForce*/
    private double Froll(){
        return 9.0867*Math.cos(Math.atan(getAltitude()/2500))*this.companiedWeight*this.cyclist.getDragCoefficient();
    }
    /*calculate the fravitationForce*/
    private   double Fgravity(){
        return  9.0867*Math.sin(Math.atan(getAltitude()/2500))*this.companiedWeight;
    }
    /*calculate the resistForce*/
    private double Fresist(){
        return  Froll()+Fdrag()+Fgravity();
    }
    private   double Work(){
        return  Fresist()*getDistance(this.startLocation,this.endLocation);
    }
    private   double PWheel(){
        return Fresist()*this.speed;
    }
    /*the cyclist power*/
    public double getCyclistPower(){
        return  Math.pow((1-(5/100)),-1)*Fresist()*this.speed;
    }
    /*the cyclist speed*/
    public double getCyclistSpeed(){
        return getSimSpeed();
    }
    /*the altitude*/
    public  double getAltitude(){
        double d=endLocation.getAltitude();
        if(d==0)
        {d=200;}
        return d;
    }


    @Override
    public double calculatePower(Cyclist cyclist, Location startLocation, Location endLocation, double combainedWight,int time,double rho) {
        this.cyclist=cyclist;
        this.endLocation=endLocation;
        this.startLocation=startLocation;
        this.companiedWeight=combainedWight;
        this.time=time;
        this.speed=getSimSpeed();
        this.rho=rho;
        return getCyclistPower();
    }

    @Override
    public double getSpeed() {
        return  this.speed;
    }
}
