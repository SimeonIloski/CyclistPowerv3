package com.example.simeon.cyclistpowerv3;

/**
 * Created by Simeon on 15.12.2017.
 */
//poseben tip na velosiped koj nasleduva od bicycle klasata
public class SportBycicle extends  Bycicle {
    double weigth;
    public  SportBycicle(){
        this.weigth=8;
    }

    @Override
    public double getWeigth() {
        return weigth;
    }
}
