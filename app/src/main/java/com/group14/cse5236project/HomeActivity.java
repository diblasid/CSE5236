package com.group14.cse5236project;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.group14.cse5236project.helpers.MyParseHelper;
import com.parse.ParseUser;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;


public class HomeActivity extends Activity implements View.OnClickListener {

    private ParseUser user;
    private TextView greeting;
    private Button goMap, goFood, goShop, goEducation, goSocial;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        greeting = (TextView)findViewById(R.id.greeting);
        user = ParseUser.getCurrentUser();
        greeting.setText("Hello, " + user.getString("first"));
        goMap = (Button)findViewById(R.id.go_to_map);
        goMap.setOnClickListener(this);
        goFood = (Button)findViewById(R.id.food_list);
        goFood.setOnClickListener(this);
        goShop = (Button)findViewById(R.id.shop_list);
        goShop.setOnClickListener(this);
        goEducation = (Button)findViewById(R.id.education_list);
        goEducation.setOnClickListener(this);
        goSocial = (Button)findViewById(R.id.social_list);
        goSocial.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v){

        switch (v.getId()){
            case R.id.go_to_map:
                Intent i = new Intent(HomeActivity.this, MapsActivity.class);
                startActivity(i);
                break;
            case R.id.food_list:
                Intent i1 = new Intent(HomeActivity.this, LocationListActivity.class);
                i1.putExtra("type", MyParseHelper.FOOD_LOCATION);
                startActivity(i1);
                break;
            case R.id.shop_list:
                Intent i2 = new Intent(HomeActivity.this, LocationListActivity.class);
                i2.putExtra("type", MyParseHelper.SHOP_LOCATION);
                startActivity(i2);
                break;
            case R.id.education_list:
                Intent i3 = new Intent(HomeActivity.this, LocationListActivity.class);
                i3.putExtra("type", MyParseHelper.EDUCATION_LOCATION);
                startActivity(i3);
                break;
            case R.id.social_list:
                Intent i4 = new Intent(HomeActivity.this, LocationListActivity.class);
                i4.putExtra("type", MyParseHelper.SOCIAL_LOCATION);
                startActivity(i4);
                break;

        }

    }

    public static boolean hasActiveInternetConnection(Context context) {
            try {
                HttpURLConnection urlc = (HttpURLConnection) (new URL("http://www.google.com").openConnection());
                urlc.setRequestProperty("User-Agent", "Test");
                urlc.setRequestProperty("Connection", "close");
                urlc.setConnectTimeout(1500);
                urlc.connect();
                return (urlc.getResponseCode() == 200);
            } catch (IOException e) {
                Toast.makeText(context, "You must have an internet connection.", Toast.LENGTH_LONG).show();
            }
        return false;
    }
}
