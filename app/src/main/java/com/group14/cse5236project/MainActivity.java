package com.group14.cse5236project;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Debug;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


public class MainActivity extends FragmentActivity{

    private final String TAG = "MainActivity:";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Debug.startMethodTracing("Main Create");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Debug.stopMethodTracing();

        Log.d(TAG, "Created.");
    }

    @Override
    protected void onStart(){
        super.onStart();
        Log.d(TAG, "Started.");

    }

    @Override
    protected void onResume(){
        super.onResume();
        Log.d(TAG, "Resumed.");

    }

    @Override
    protected void onPause(){
        super.onPause();
        Log.d(TAG, "Paused.");
    }

    @Override
    protected void onStop(){
        super.onStop();
        Log.d(TAG, "Stopped.");

    }

    @Override
    protected void onDestroy(){
        super.onStart();
        Log.d(TAG, "Destroyed.");

    }

    public void getPauseDialog(View v){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Main Activity Paused.");
        builder.setPositiveButton("Unpause", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                onResume();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
        onPause();
    }

    public void getOtherActivityAndStop(View v){

        Intent i = new Intent(this, OtherActivity.class);
        i.putExtra("Destroyed", false);
        startActivity(i);

    }

    public void getOtherActivityAndDestroy(View v){

        Intent i = new Intent(this, OtherActivity.class);
        i.putExtra("Destroyed", true);
        startActivity(i);
        this.finish();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
