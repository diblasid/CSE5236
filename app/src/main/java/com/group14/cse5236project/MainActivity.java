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
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SignUpCallback;


public class MainActivity extends Activity implements View.OnClickListener{

    private final String TAG = "MainActivity:";
    private ParseUser user;
    RelativeLayout firstName, lastName, email;
    Button login, createAccount, register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(TAG, "Created.");


        login = (Button)findViewById(R.id.login_button);
        createAccount = (Button)findViewById(R.id.create_button);
        login.setOnClickListener(this);
        createAccount.setOnClickListener(this);
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

    @Override
    public void onClick(View v){

        switch(v.getId()){
            case R.id.login_button:
                user.logInInBackground(((EditText) findViewById(R.id.username_edit)).getText().toString(), ((EditText) findViewById(R.id.password_edit)).getText().toString(), new LogInCallback() {
                    @Override
                    public void done(ParseUser user, ParseException e) {
                        if (e == null) {
                            Intent i = new Intent(MainActivity.this, HomeActivity.class);
                            startActivity(i);
                        } else {
                            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();

                        }
                    }
                });

                break;
            case R.id.create_button:
                firstName = (RelativeLayout)findViewById(R.id.first_name);
                lastName = (RelativeLayout)findViewById(R.id.last_name);
                email = (RelativeLayout)findViewById(R.id.email);
                register = (Button)findViewById(R.id.register_button);

                firstName.setVisibility(View.VISIBLE);
                lastName.setVisibility(View.VISIBLE);
                email.setVisibility(View.VISIBLE);
                register.setVisibility(View.VISIBLE);
                login.setVisibility(View.GONE);
                createAccount.setVisibility(View.GONE);

                register.setOnClickListener(this);

                break;
            case R.id.register_button:
                user = new ParseUser();
                user.setUsername(((EditText) findViewById(R.id.username_edit)).getText().toString());
                user.setPassword(((EditText) findViewById(R.id.password_edit)).getText().toString());
                user.setEmail(((EditText) findViewById(R.id.email_edit)).getText().toString());
                user.put("first", ((EditText) findViewById(R.id.first_name_edit)).getText().toString());
                user.put("last", ((EditText)findViewById(R.id.last_name_edit)).getText().toString());
                user.signUpInBackground(new SignUpCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e == null) {
                            Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                            startActivity(intent);
                        }else{
                            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });

                break;

        }

    }

}
