package com.example.simeon.cyclistpowerv3;

/**
 * Created by Simeon on 29.11.2017.
 */
//poseben tip na velosiped koj nasleduva od bicycle klasata
public class StandardBicycle extends  Bycicle {
 double weight;
    public StandardBicycle(){
        weight=12;
    }

    @Override
    public double getWeigth() {
        return weight;
    }
}

