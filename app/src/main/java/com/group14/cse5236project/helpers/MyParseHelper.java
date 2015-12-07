package com.group14.cse5236project.helpers;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.parse.ParseObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by diblasid on 11/19/15.
 */
public class MyParseHelper {

    public static final int ALL_LOCATION = 0;
    public static final int FOOD_LOCATION = 1;
    public static final int SHOP_LOCATION = 2;
    public static final int EDUCATION_LOCATION = 3;
    public static final int SOCIAL_LOCATION = 4;


    /*
    *   Filters a list of locations using a search filter.
     */
    public static List<ParseObject> filterLocations(List<ParseObject> original, String filter){

        List<ParseObject> result = new ArrayList<>();

        for(ParseObject p : original){

            if(p.getString("name").toLowerCase().contains(filter)){
                result.add(p);
            }

        }
        return result;
    }


    /*
    *   Checks if the device has an internet connection.
     */
    public static boolean hasActiveInternetConnection(Context context) {

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        if (ni == null) {
            // There are no active networks.
            Toast.makeText(context, "You are not connected to the Internet.", Toast.LENGTH_LONG).show();
            return false;
        }else{
            return ni.isConnected();
        }
    }
}
