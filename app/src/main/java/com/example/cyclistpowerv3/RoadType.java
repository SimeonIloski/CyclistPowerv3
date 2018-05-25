package com.example.simeon.cyclistpowerv3;

/**
 * Created by Simeon on 24.11.2017.
 */
//vrakanje na gotovi kostanti za dragcoefficient spored podloga na vozenje
public class RoadType {
    private String typeOfRoad;
public double getDragCoefficient(String typeofRoad){
    double d=0.001;
    if(typeofRoad.equals("concrete")){
        d=0.002;
    }
    else if(typeofRoad.equals("asphalt")){
         d=0.004;
    }
    else if(typeofRoad.equals("dirt")){
        d=0.008;
    }

    return  d;
}
}
