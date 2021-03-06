package org.mythtv.androidtv.ui.recordings;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;

import org.mythtv.androidtv.R;
import org.mythtv.androidtv.core.MainApplication;
import org.mythtv.androidtv.ui.settings.SettingsActivity;

public class RecordingsActivity extends Activity {

    private static final String TAG = RecordingsActivity.class.getSimpleName();

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        Log.i( TAG, "onCreate : enter" );

        if( ( (MainApplication) getApplicationContext() ).isConnected() ) {
            Log.d(TAG, "onCreate : backend already connected");

            ( (MainApplication) getApplicationContext() ).resetBackend();

        } else {
            Log.d( TAG, "onCreate : backend NOT connected" );

            SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
            String backendUrlPref = sharedPref.getString( SettingsActivity.KEY_PREF_BACKEND_URL, "" );

            if( "".equals( backendUrlPref ) || getResources().getString( R.string.pref_backend_url ).equals( backendUrlPref ) ) {
                Log.d( TAG, "onCreate : backend not set, show settings" );

                Intent prefs = new Intent( this, SettingsActivity.class );
                startActivity( prefs );
            } else {

                ( (MainApplication) getApplicationContext() ).resetBackend();

            }

        }

        setContentView( R.layout.activity_recordings );

    }

}
