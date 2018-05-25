package com.example.simeon.cyclistpowerv3;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Simeon on 23.11.2017.
 */

public class User
{
    String name;
    String email;
    String password;
    public  User(){}

    public void setPassword(String password) {
        this.password = password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public boolean equals(Object obj) {
        User u=(User) obj;
        if(u.getEmail().equals(this.getEmail()) && u.getPassword().equals(this.getPassword())){
            return  true;
        }
        return false;
    }

    @Override
    public String toString() {
        return String.format("%s %s %s",getName(),getEmail(),getPassword());
    }

}
