package com.group14.cse5236project;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParseUser;

/**
 * Created by diblasid on 11/8/15. testing vcs
 */
public class App extends Application {

    @Override public void onCreate() {
        super.onCreate();

        Parse.enableLocalDatastore(this);

        Parse.initialize(this, "eZvNar7srkOHBPmKTlwAobcpedl8RlFxCNHP8tKI", "fS8Kgfp3NGt6qkoIrVhGZydBLwjPrTqcPV81g4df");
        ParseInstallation.getCurrentInstallation().saveInBackground();
        ParseUser.registerSubclass(ParseUser.class);


    }
}
