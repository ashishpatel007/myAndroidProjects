package io.bitfountain.ashishpatel.doggydogworld;

import android.util.Log;

/**
 * Created by ashishpatel on 2015-12-08.
 */
public class Dog {
    private String name;
    private int age;
    private int numberOfLegs = 4;
    private String breed;

    public void bark(){
        String bark = name + " barked";
        Log.d("Dog", bark) ;
    }

    Dog(){
        Log.d("Dog", "Constructing...");

    }

    Dog(String dogName){
        name = dogName;
    }

    public void barkAt(String victim){
        String bark = name + " barked at " + victim;
        Log.d("Dog", bark) ;
    }


}
