package com.example.simeon.cyclistpowerv3;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Simeon on 29.11.2017.
 */
//klasa za kreiranje na velosipedist (Cyclist) koja sluzi za organiziranje na podatoci i polesno prefrlanje na niv vo drugi activities
public class Cyclist  implements Parcelable{
double BycicleWeight;
double Userweight;
double frontalArea;
String username;
double dragCoefficient;
public  Cyclist(double weightB,double weightU,double fa,String usname,double rd){
   this.BycicleWeight=weightB;
   this.Userweight=weightU;
   this.username=usname;
   this.frontalArea=fa;
   this.dragCoefficient=rd;
}
public Cyclist(){}

    protected Cyclist(Parcel in) {
        BycicleWeight = in.readDouble();
        Userweight = in.readDouble();
        frontalArea = in.readDouble();
        username = in.readString();
        dragCoefficient = in.readDouble();
    }

    public static final Creator<Cyclist> CREATOR = new Creator<Cyclist>() {
        @Override
        public Cyclist createFromParcel(Parcel in) {
            return new Cyclist(in);
        }

        @Override
        public Cyclist[] newArray(int size) {
            return new Cyclist[size];
        }
    };

    public double getBycicleWeight() {
        return BycicleWeight;
    }

    public void setBycicleWeight(double bycicleWeight) {
        BycicleWeight = bycicleWeight;
    }

    public double getUserweight() {
        return Userweight;
    }

    public void setUserweight(double userweight) {
        Userweight = userweight;
    }

    public double getFrontalArea() {
        return frontalArea;
    }

    public void setFrontalArea(double frontalArea) {
        this.frontalArea = frontalArea;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public double getDragCoefficient() {
        return dragCoefficient;
    }

    public void setDragCoefficient(double dragCoefficient) {
        this.dragCoefficient = dragCoefficient;
    }

    @Override
    public String toString() {
        return String.format("bycicle weight: %f  userweight: %f  username: %s frontal area: %f and dragcoefficient: %f",getBycicleWeight(),getUserweight(),getUsername(),getFrontalArea(),getDragCoefficient());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
            parcel.writeDouble(getBycicleWeight());
            parcel.writeDouble(getUserweight());
            parcel.writeDouble(getFrontalArea());
            parcel.writeString(getUsername());
            parcel.writeDouble(getDragCoefficient());
    }
}
