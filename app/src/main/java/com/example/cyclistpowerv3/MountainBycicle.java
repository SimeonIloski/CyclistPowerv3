package com.example.simeon.cyclistpowerv3;

/**
 * Created by Simeon on 15.12.2017.
 */
//poseben tip na velosiped koj nasleduva od bicycle klasata
public class MountainBycicle extends  Bycicle {
    double weight;
    public MountainBycicle(){
        this.weight=9;
    }


    @Override
    public double getWeigth() {
        return weight;
    }
}
