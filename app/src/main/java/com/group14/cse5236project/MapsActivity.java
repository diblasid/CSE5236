package com.group14.cse5236project;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

public class MapsActivity extends FragmentActivity {

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        setUpMapIfNeeded();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p/>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p/>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    private void setUpMap() {
        mMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker"));

        final Bitmap foodImage = BitmapFactory.decodeResource(getResources(), R.mipmap.foodicon);
        final Bitmap shopImage = BitmapFactory.decodeResource(getResources(), R.mipmap.shop_icon);
        final Bitmap educationImage = BitmapFactory.decodeResource(getResources(), R.mipmap.education_icon);
        final Bitmap socialImage = BitmapFactory.decodeResource(getResources(), R.mipmap.social_icon);



        mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {


            @Override
            public View getInfoWindow(Marker marker) {
                return null;
            }

            @Override
            public View getInfoContents(Marker marker) {
                Context context = getApplicationContext();
                LinearLayout info = new LinearLayout(context);
                info.setOrientation(LinearLayout.VERTICAL);

                TextView title = new TextView(context);
                title.setTextColor(Color.BLACK);
                title.setGravity(Gravity.CENTER);
                title.setTypeface(null, Typeface.BOLD);
                title.setText(marker.getTitle());

                TextView details = new TextView(context);
                details.setTextColor(Color.GRAY);
                details.setText(marker.getSnippet());

                info.addView(title);
                info.addView(details);

                return info;
            }
        });
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Location");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if(e == null){
                    for(int i = 0; i < objects.size(); i++){
                        ParseObject location = objects.get(i);
                        try {
                            location.fetchIfNeeded();
                            Bitmap iconPointer = foodImage;
                            switch(location.getString("type")){
                                case "food":
                                    iconPointer = foodImage;
                                    break;
                                case "shop":
                                    iconPointer = shopImage;
                                    break;
                                case "education":
                                    iconPointer = educationImage;
                                    break;
                                case "social":
                                    iconPointer = socialImage;
                                    break;
                            }


                            Marker tempMarker = mMap.addMarker(new MarkerOptions()
                                            .position(new LatLng(location.getDouble("Lat"), location.getDouble("Lng")))
                                            .title(location.getString("name"))
                                            .icon(BitmapDescriptorFactory.fromBitmap(iconPointer))
                                            .snippet("Address: " + location.getString("address") + "\n"
                                                    + "Phone: " + location.getString("phone") + "\n"
                                                    + "Hours: " + location.getString("open") + " - " + location.getString("close"))

                            );


                            if(location.getObjectId().equals(getIntent().getStringExtra("id"))){
                                tempMarker.showInfoWindow();
                            }


                        }catch(ParseException e1){
                            Toast.makeText(getApplicationContext(), e1.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                }else{
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();

                }
            }
        });
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(getIntent().getDoubleExtra("lat", 39.999315), getIntent().getDoubleExtra("lng", -83.011923)), 15.0f));

    }
}
