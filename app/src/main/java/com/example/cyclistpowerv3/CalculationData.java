package com.example.simeon.cyclistpowerv3;

import android.location.Location;

import java.util.Random;

/**
 * Created by Simeon on 30.11.2017.
 */

/*this is the class which use a specific strategy for  calculation of the  power
* it also combined the strategy pattern wiht the template pattern beacuse no maater of the way of
* calculating the speed the calculation of the air density is same*/
public class CalculationData {
    Cyclist cyclist;
    Location startLocation;
    Location endLocation;
    int time;
    double simulatedSpeed;
    Random random = new Random();
    WeatherData weatherData;
    double rho;
    Calculation calculation;
    public CalculationData(Cyclist cyclist, Location start, Location end, int time, WeatherData renderweather,String type){
        this.cyclist=cyclist;
        this.startLocation=start;
        this.endLocation=end;
        this.time=time;
        this.weatherData=renderweather;
                /*here we choose one of the 2 possible strategies for calculation of the power*/
        if(type.equals("Simulated Strategy")){
            this.calculation=new SimulatedSpeedCalculation();
        }
        else if(type.equals("Real Calculation Strategy")){
            this.calculation=new RealSpeedCalculation();
        }
        else{
            this.calculation=new SimulatedSpeedCalculation();
        }
        rho=calculateAirDensity(weatherData);
    }
    /*returns the combine weight of cyclist and bicycle*/
    private   double getCompaniedWeight(Cyclist cyclist){
        return  cyclist.getBycicleWeight()+cyclist.getUserweight();
    }


    //Presmetka na gustinata na vozduhot(air density)
    //Ovoj metod presmetuva rho za temperaturata spored dadena formula i koeficienti
    public  double getPh(WeatherData weatherData){
        return 6.1078/( 0.99999683+weatherData.getTemperature()* (-0.90826951 *Math.pow(10,-2)+(weatherData.getTemperature()*( 0.78736169 *Math.pow(10,-4)
        +weatherData.getTemperature()*( -0.61117958*Math.pow(10,-6)+weatherData.getTemperature()*( 0.43884187*Math.pow(10,-8)+
        weatherData.getTemperature()*(-0.29883885 *Math.pow(10,-10)+weatherData.getTemperature()*(0.21874425 *Math.pow(10,-12)+
        weatherData.getTemperature()*(-0.17892321*Math.pow(10,-14)+weatherData.getTemperature()*(0.11112018 *Math.pow(10,-16)+
        weatherData.getTemperature()*( -0.30994571*Math.pow(10,-19))))))))))));
    }
    //ovoj metod premetuva rho za dewpoint temperaturi po dadena formula i koeficienti
    public double getPV(WeatherData weatherData){
        double d=( 0.99999683+weatherData.getDewPoint()* (-0.90826951 *Math.pow(10,-2)+(weatherData.getDewPoint()*( 0.78736169 *Math.pow(10,-4)
                +weatherData.getDewPoint()*( -0.61117958*Math.pow(10,-6)+weatherData.getDewPoint()*( 0.43884187*Math.pow(10,-8)+
                weatherData.getDewPoint()*(-0.29883885 *Math.pow(10,-10)+weatherData.getDewPoint()*(0.21874425 *Math.pow(10,-12)+
                        weatherData.getDewPoint()*(-0.17892321*Math.pow(10,-14)+weatherData.getDewPoint()*(0.11112018 *Math.pow(10,-16)+
                                weatherData.getDewPoint()*( -0.30994571*Math.pow(10,-19))))))))))));
        double d1=1;
        if(d!=0){}d1=d;
        return 6.1078/d1;
    }
    //razlika na predhodnite 2 formuli(metodi) za rho
    public  double getPd(WeatherData weatherData){
        return getPh(weatherData)-getPV(weatherData);
    }
   /*ovoj metod ja vraka gustinata na vozduhot*/
    public  double calculateAirDensity(WeatherData weatherData){
         double d=(getPd(weatherData)/(287.0531)*(weatherData.temperature+273.15))+(getPV(weatherData)/(461.4694*(weatherData.getTemperature()+273.15)));
        return  d;
    }
    //getPower  i getSpeed metodi
    public  double getPower(){
        return  this.calculation.calculatePower(cyclist,startLocation,endLocation,getCompaniedWeight(this.cyclist),time,rho);
    }
    public double getSpeed(){
        return  this.calculation.getSpeed();}

}
