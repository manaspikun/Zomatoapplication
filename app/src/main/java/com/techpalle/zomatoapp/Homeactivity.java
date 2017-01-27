package com.techpalle.zomatoapp;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Homeactivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homeactivity);
       //load resturandfrag
        ResturantFragment resturantFragment=new ResturantFragment();
        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.container,resturantFragment);
        fragmentTransaction.commit();



    }
    //go to check internet permission
    public  boolean checkInternet(){
        //CHK FOR INTERNET CONNECTION
        //A.GET NETWORK MANAGER OBJECT
        ConnectivityManager manager=(ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        //b.from network manager,get activity information
        NetworkInfo networkInfo=manager.getActiveNetworkInfo();
        //c.chk if network is conncted or not
        if(networkInfo==null || networkInfo.isConnected() == false){
            return false;
        }
        return  true;
    }

}
