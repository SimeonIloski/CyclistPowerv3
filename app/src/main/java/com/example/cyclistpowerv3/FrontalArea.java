package com.example.simeon.cyclistpowerv3;

import android.location.LocationManager;

/**
 * Created by Simeon on 24.11.2017.
 */
//presmetka na frontal area spored pozicija na vozenje
public class FrontalArea {
    private String position;
    public double  getFArea(String position){
        double d=1;
        if(position.equals("Tops")){
            d=0.632 * 1.15;
        }
        else if(position.equals("Hoods")){
            d=0.4*1;
        }
        else if(position.equals("Drops")){
            d=0.32*0.88;
        }
        return  d;
    }
}
