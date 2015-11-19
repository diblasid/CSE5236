package com.group14.cse5236project;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.group14.cse5236project.helpers.TypeHelper;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;


public class LocationListActivity extends Activity {

    private TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_list);

        title = (TextView)findViewById(R.id.list_title);

        switch(getIntent().getIntExtra("type", TypeHelper.ALL_LOCATION)){
            case TypeHelper.FOOD_LOCATION:
                title.setText("Where to find Food");
                List<ParseObject> foodLocations = queryLocationType("food");
                addLocationsToView(foodLocations);
                break;
            case TypeHelper.SHOP_LOCATION:
                title.setText("Where to go Shopping");
                List<ParseObject> shopLocations = queryLocationType("shop");
                addLocationsToView(shopLocations);
                break;
            case TypeHelper.EDUCATION_LOCATION:
                title.setText("Where to go Study");
                List<ParseObject> educationLocations = queryLocationType("education");
                addLocationsToView(educationLocations);
                break;
            case TypeHelper.SOCIAL_LOCATION:
                title.setText("Where to Meet People");
                List<ParseObject> socialLocations = queryLocationType("social");
                addLocationsToView(socialLocations);
                break;
            case TypeHelper.ALL_LOCATION:
            default:
                title.setText("All Locations");
                List<ParseObject> allLocations = queryLocationType("all");
                addLocationsToView(allLocations);
                break;


        }




    }

    private List<ParseObject> queryLocationType(String type){

        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Location");
        if(!type.equals("all")) {
            query.whereEqualTo("type", type);
        }
        try {
            return query.find();
        }catch(ParseException e){
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();

            return null;
        }
    }

    private void addLocationsToView(List<ParseObject> locations) {

        LinearLayout listOfLocations = (LinearLayout)findViewById(R.id.location_list);

        for (final ParseObject location : locations){

            LinearLayout listLocationDetails = new LinearLayout(this);
            listLocationDetails.setOrientation(LinearLayout.HORIZONTAL);

            TextView tempName = new TextView(this);
            tempName.setText(location.getString("name"));
            tempName.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.MATCH_PARENT));

            TextView tempAddress = new TextView(this);
            tempAddress.setText(location.getString("address"));
            tempAddress.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.MATCH_PARENT));
            TextView tempHours = new TextView(this);
            tempHours.setText(location.getString("open") + " - " + location.getString("close"));
            tempHours.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.MATCH_PARENT));
            TextView tempPhone = new TextView(this);
            tempPhone.setText(location.getString("phone"));
            tempPhone.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.MATCH_PARENT));

            Button goToMap = new Button(this);
            goToMap.setText("Show on Map");
            goToMap.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));

            goToMap.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(LocationListActivity.this, MapsActivity.class);
                    i.putExtra("lat", location.getDouble("Lat"));
                    i.putExtra("lng", location.getDouble("Lng"));
                    i.putExtra("id", location.getObjectId());
                    startActivity(i);
                }
            });


            listLocationDetails.addView(tempName);
            //listLocationDetails.addView(tempAddress);
            listLocationDetails.addView(tempHours);
            listLocationDetails.addView(tempPhone);
            listLocationDetails.addView(goToMap);

            listOfLocations.addView(listLocationDetails);

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_location_list, menu);
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
}
