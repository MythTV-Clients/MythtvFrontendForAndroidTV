package org.mythtv.androidtv;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

import org.mythtv.androidtv.settings.SettingsActivity;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences( this );
        String backendUrlPref = sharedPref.getString( SettingsActivity.KEY_PREF_BACKEND_URL, "" );
        if( "".equals( backendUrlPref ) || getResources().getString( R.string.pref_default_display_name ).equals( backendUrlPref ) ) {
            Intent prefs = new Intent( this, SettingsActivity.class );
            startActivity( prefs );
        } else {
            setContentView( R.layout.activity_main );
        }

    }
}
