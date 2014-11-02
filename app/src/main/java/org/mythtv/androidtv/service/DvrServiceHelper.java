package org.mythtv.androidtv.service;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.Cache;
import com.squareup.okhttp.OkHttpClient;

import org.mythtv.androidtv.model.Program;
import org.mythtv.androidtv.model.ProgramList;
import org.mythtv.androidtv.model.ProgramListWrapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.android.AndroidLog;
import retrofit.client.OkClient;
import retrofit.converter.GsonConverter;

/**
 * Created by dmfrey on 11/2/14.
 */
public class DvrServiceHelper {

    private static final String TAG = DvrServiceHelper.class.getSimpleName();

    public static final String ACTION_COMPLETE = "org.mythtv.androidtv.service.DvrServiceHelper.ACTION_COMPLETE";

    private final Context mContext;
    private final String mBaseUrl;

    private DvrService mDvrService;

    private Map<String, List<Program>> mPrograms = new TreeMap<String, List<Program>>();
    private Map<String, String> mCategories = new TreeMap<String, String>();

    private List<Program> programs = new ArrayList<Program>();

    public DvrServiceHelper( final Context context, final String baseUrl ) {

        mContext = context;
        mBaseUrl = baseUrl;

        initializeClient();

        new ProgramsLoaderAsyncTask().execute();

    }

    public Map<String, List<Program>> getPrograms() {
        return mPrograms;
    }

    public Map<String, String> getCategories() {
        return mCategories;
    }

    private void preparePrograms() {

        for( Program program : programs ) {

            String cleanedTitle = program.getTitle(); //cleanArticles( program.getTitle() );
            if( !mPrograms.containsKey( cleanedTitle ) ) {

                List<Program> categoryPrograms = new ArrayList<Program>();
                categoryPrograms.add( program );
                mPrograms.put( cleanedTitle, categoryPrograms );

                mCategories.put( cleanedTitle, program.getTitle() );

            } else {

                mPrograms.get( cleanedTitle ).add( program );

            }

        }

    }

    private void initializeClient() {

        OkHttpClient client = new OkHttpClient();

//        int cacheSize = 10 * 1024 * 1024; // 10 MiB
//        File cacheDirectory = new File( mContext.getCacheDir().getAbsolutePath(), "HttpCache" );
//        try {
//            Cache cache = new Cache( cacheDirectory, cacheSize );
//            client.setCache( cache );
//        } catch( IOException e ) { }

        Gson dvrGson = new GsonBuilder()
                .setDateFormat( "yyyy-MM-dd'T'HH:mm:ssz" )
                .create();

        RestAdapter dvrRestAdapter = new RestAdapter.Builder()
                .setLogLevel( RestAdapter.LogLevel.FULL )
//                .setLog( new AndroidLog( TAG ) )
                .setEndpoint( mBaseUrl )
                .setClient( new OkClient( client ) )
                .setConverter( new GsonConverter( dvrGson ) )
                .setRequestInterceptor( new RequestInterceptor() {

                    @Override
                    public void intercept( RequestFacade request ) {
                        request.addHeader( "Accept", "application/json" );
                    }

                })
                .build();

        mDvrService = dvrRestAdapter.create( DvrService.class );

    }

    private String cleanArticles( String value ) {

        String cleaned = value;

        if( cleaned.startsWith( "The " ) || cleaned.startsWith( "the " ) ) {
            cleaned = cleaned.substring( 0, 4 );
        }

        if( cleaned.startsWith( "An " ) || cleaned.startsWith( "an " ) ) {
            cleaned = cleaned.substring( 0, 3 );
        }

        if( cleaned.startsWith( "A " ) || cleaned.startsWith( "a " ) ) {
            cleaned = cleaned.substring( 0, 2 );
        }

        return cleaned;
    }

    private class ProgramsLoaderAsyncTask extends AsyncTask<Void, Void, ProgramListWrapper> {

        @Override
        protected ProgramListWrapper doInBackground( Void... params ) {

            return mDvrService.getRecordedList( 1, -1, false );
        }

        @Override
        protected void onPostExecute( ProgramListWrapper wrapper ) {
            Log.i( TAG, "onPostExecute : enter" );

            Log.i(TAG, "onPostExecute : wrapper=" + wrapper.toString());
            if( null != wrapper.getProgramList() && null != wrapper.getProgramList().getPrograms() ) {

                for( Program program : wrapper.getProgramList().getPrograms() ) {
                    Log.i( TAG, "program = " + program.toString() );

                    if( !"LiveTV".equals( program.getRecording().getStorageGroup() ) ) {
                        programs.add(program);
                    }
                }

                preparePrograms();

            }

            Intent complete = new Intent( ACTION_COMPLETE );
            mContext.sendBroadcast( complete );

            Log.i( TAG, "onPostExecute : exit" );
        }

    }

}
