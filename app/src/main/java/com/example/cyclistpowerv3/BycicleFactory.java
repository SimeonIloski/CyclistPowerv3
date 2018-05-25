package com.example.simeon.cyclistpowerv3;

/**
 * Created by Simeon on 15.12.2017.
 */
/* this is our Factory class. In here we choose what kind of bicycle to create. This allow us to
 implement to interface not to a concrete implementation and will give us easy way of extending the
  aplication with new bikes in future*/

public class BycicleFactory {
    Bycicle b;
    public Bycicle   createBycicle(String type){
      if(type.equals("Standard Bycicle")){
      b= new StandardBicycle();
      }
      else if(type.equals("Sport Bycicle")){
          b=  new SportBycicle();
      }
      else{
          if(type.equals("Mountain Bycicle"))
          b=new MountainBycicle();
      }
    return b;
    }
}
