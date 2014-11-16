package org.mythtv.androidtv.core;

import android.app.Application;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;

import org.mythtv.androidtv.core.service.DvrService;
import org.mythtv.androidtv.core.service.v027.DvrServiceV27EventHandler;
import org.mythtv.androidtv.core.service.v028.DvrServiceV28EventHandler;
import org.mythtv.androidtv.ui.settings.SettingsActivity;
import org.mythtv.services.api.ApiVersion;
import org.mythtv.services.api.MythTvApiContext;
import org.mythtv.services.api.ServerVersionQuery;

import java.io.IOException;

/**
 * Created by dmfrey on 11/15/14.
 */
public class MainApplication extends Application {

    private static final String TAG = MainApplication.class.getSimpleName();

    public static final String ACTION_CONNECTED = "org.mythtv.androidtv.core.service.ACTION_CONNECTED";
    public static final String ACTION_NOT_CONNECTED = "org.mythtv.androidtv.core.service.ACTION_NOT_CONNECTED";

    boolean mConnected = false;
    String mBackendUrl;
    int mBackendPort;

    private ApiVersion mApiVersion;

    private MythTvApiContext mMythTvApiContext;

    private DvrService mDvrService;

    @Override
    public void onCreate() {
        super.onCreate();

    }

    public void resetBackend() {

        initializeApi();

    }

    public MythTvApiContext getMythTvApiContext() {
        return mMythTvApiContext;
    }

    public DvrService getDvrService() {
        return mDvrService;
    }

    public boolean isConnected() { return mConnected; }

    public String getMasterBackendUrl() {
        return "http://" + mBackendUrl + ":" + mBackendPort + "/";
    }

    private void initializeApi() {
        Log.d( TAG, "initializeApi : enter" );

        new ServerVersionAsyncTask().execute();

        Log.d( TAG, "initializeApi : exit" );
    }

    private class ServerVersionAsyncTask extends AsyncTask<Void, Void, ApiVersion> {

        String backendUrl;
        int backendPort;

        @Override
        protected ApiVersion doInBackground(Void... params) {

            SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences( MainApplication.this );
            backendUrl = sharedPref.getString( SettingsActivity.KEY_PREF_BACKEND_URL, "" );
            backendPort = Integer.parseInt( sharedPref.getString( SettingsActivity.KEY_PREF_BACKEND_PORT, "6544" ) );

            try {
                return ServerVersionQuery.getMythVersion("http://" + backendUrl + ":" + backendPort + "/");
            } catch( IOException e ) {
                Log.e( TAG, "error creating MythTvApiContext, could not reach '" + getMasterBackendUrl() + "'", e );

                return null;
            }

        }

        @Override
        protected void onPostExecute(ApiVersion apiVersion) {

            if( null != apiVersion ) {

                mApiVersion = apiVersion;
                mBackendUrl = backendUrl;
                mBackendPort = backendPort;

                mMythTvApiContext = MythTvApiContext.newBuilder().setHostName( mBackendUrl ).setPort( mBackendPort ).setVersion( mApiVersion ).build();

                switch( mApiVersion ) {

                    case v027:

                        mDvrService = new DvrServiceV27EventHandler( MainApplication.this, mMythTvApiContext );

                        break;

                    case v028:

                        mDvrService = new DvrServiceV28EventHandler( MainApplication.this, mMythTvApiContext );

                        break;
                }

                mConnected = true;

                Intent connectedIntent = new Intent( ACTION_CONNECTED );
                sendBroadcast( connectedIntent );

            } else {

                mConnected = false;

                Intent connectedIntent = new Intent( ACTION_NOT_CONNECTED );
                sendBroadcast( connectedIntent );

            }

        }

    }

}
