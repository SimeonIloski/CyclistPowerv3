package com.example.simeon.cyclistpowerv3;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import java.util.Date;

/**
 * Created by Simeon on 30.11.2017.
 */

public class SingleMeasurment  implements  Comparable<SingleMeasurment>,Parcelable{
    double power;
    String name;
    Date date;
    double speed;
    public SingleMeasurment(double power,String name,Date date, double speed){
        this.power=power;
        this.date=date;
        this.name=name;
        this.speed=speed;
    }
    public SingleMeasurment(){}

    protected SingleMeasurment(Parcel in) {
        power = in.readDouble();
        name = in.readString();
        date=new Date(in.readString());
        speed = in.readDouble();
    }

    public double getPower() {
        return power;
    }

    public void setPower(double power) {
        this.power = power;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public static final Creator<SingleMeasurment> CREATOR = new Creator<SingleMeasurment>() {
        @Override
        public SingleMeasurment createFromParcel(Parcel in) {
            return new SingleMeasurment(in);
        }

        @Override
        public SingleMeasurment[] newArray(int size) {
            return new SingleMeasurment[size];
        }
    };

    @Override
    public String toString() {
        return String.format("CyclistPower: %.2f  W\n CyclistSpeed: %.2f Km/h",power,speed);
    }

    @Override
    public int compareTo(@NonNull SingleMeasurment singleMeasurmentPower) {
        return Double.compare(this.power,singleMeasurmentPower.power);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
         parcel.writeDouble(power);
         parcel.writeString(name);
         parcel.writeString(date.toString());
         parcel.writeDouble(speed);
    }
}
