package org.mythtv.androidtv;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v17.leanback.app.BackgroundManager;
import android.support.v17.leanback.app.BrowseFragment;
import android.support.v17.leanback.widget.ArrayObjectAdapter;
import android.support.v17.leanback.widget.HeaderItem;
import android.support.v17.leanback.widget.ListRow;
import android.support.v17.leanback.widget.ListRowPresenter;
import android.support.v17.leanback.widget.OnItemClickedListener;
import android.support.v17.leanback.widget.OnItemSelectedListener;
import android.support.v17.leanback.widget.OnItemViewSelectedListener;
import android.support.v17.leanback.widget.Presenter;
import android.support.v17.leanback.widget.Row;
import android.support.v17.leanback.widget.RowPresenter;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.mythtv.androidtv.model.Program;
import org.mythtv.androidtv.service.DvrServiceHelper;
import org.mythtv.androidtv.settings.SettingsActivity;


public class MainFragment extends BrowseFragment {

    private static final String TAG = "MainFragment";

    private ArrayObjectAdapter mRowsAdapter;
    private Drawable mDefaultBackground;
    private Target mBackgroundTarget;
    private DisplayMetrics mMetrics;
    private Timer mBackgroundTimer;
    private final Handler mHandler = new Handler();
    private URI mBackgroundURI;
    Movie mMovie;
    CardPresenter mCardPresenter;

    DvrServiceHelper mDvrServiceHelper;

    private ProgramLoaderCompleteReceiver mProgramLoaderCompleteReceiver = new ProgramLoaderCompleteReceiver();

    private String mBackendUrl;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        Log.i(TAG, "onCreate");
        super.onActivityCreated(savedInstanceState);

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences( getActivity() );
        mBackendUrl = sharedPref.getString( SettingsActivity.KEY_PREF_BACKEND_URL, "" );

        mDvrServiceHelper = new DvrServiceHelper( getActivity(), mBackendUrl );

        prepareBackgroundManager();

        setupUIElements();

//        loadRows();

        setupEventListeners();
    }

    @Override
    public void onResume() {
        super.onResume();

        IntentFilter programLoaderCompleteIntentFilter = new IntentFilter( DvrServiceHelper.ACTION_COMPLETE );
        getActivity().registerReceiver( mProgramLoaderCompleteReceiver, programLoaderCompleteIntentFilter );

    }

    @Override
    public void onPause() {
        super.onPause();

        if( null != mProgramLoaderCompleteReceiver ) {
            getActivity().unregisterReceiver( mProgramLoaderCompleteReceiver );
        }
    }

    private void loadRows() {

        Map<String, String> categories = mDvrServiceHelper.getCategories();
        Map<String, List<Program>> programs = mDvrServiceHelper.getPrograms();

        mRowsAdapter = new ArrayObjectAdapter(new ListRowPresenter());
        mCardPresenter = new CardPresenter();

        int i = 0;
        for( String category : categories.keySet() ) {

            ArrayObjectAdapter listRowAdapter = new ArrayObjectAdapter( mCardPresenter );
            for( Program program : programs.get( category ) ) {
                listRowAdapter.add( program );
            }

            HeaderItem header = new HeaderItem( i, categories.get( category ), null );
            mRowsAdapter.add(new ListRow( header, listRowAdapter ) );

            i++;
        }

        HeaderItem gridHeader = new HeaderItem( i, "PREFERENCES", null );

        GridItemPresenter mGridPresenter = new GridItemPresenter();
        ArrayObjectAdapter gridRowAdapter = new ArrayObjectAdapter( mGridPresenter );
        gridRowAdapter.add(getResources().getString( R.string.personal_settings ) );
        mRowsAdapter.add( new ListRow( gridHeader, gridRowAdapter ) );

        setAdapter( mRowsAdapter );

    }

    private void prepareBackgroundManager() {

        BackgroundManager backgroundManager = BackgroundManager.getInstance(getActivity());
        backgroundManager.attach(getActivity().getWindow());
        mBackgroundTarget = new PicassoBackgroundManagerTarget(backgroundManager);

        mDefaultBackground = getResources().getDrawable(R.drawable.default_background);

        mMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(mMetrics);
    }

    private void setupUIElements() {
        // setBadgeDrawable(getActivity().getResources().getDrawable(
        // R.drawable.videos_by_google_banner));
        setTitle(getString(R.string.browse_title)); // Badge, when set, takes precedent
        // over title
        setHeadersState(HEADERS_ENABLED);
        setHeadersTransitionOnBackEnabled(true);

        // set fastLane (or headers) background color
        setBrandColor(getResources().getColor(R.color.fastlane_background));
        // set search icon color
        setSearchAffordanceColor(getResources().getColor(R.color.search_opaque));
    }

    private void setupEventListeners() {
        setOnItemSelectedListener(getDefaultItemSelectedListener());
        setOnItemClickedListener(getDefaultItemClickedListener());
        setOnItemViewSelectedListener( getDefaultItemViewSelectedListener() );
        setOnSearchClickedListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Implement your own in-app search", Toast.LENGTH_LONG)
                        .show();
            }
        });
    }

    protected OnItemSelectedListener getDefaultItemSelectedListener() {
        return new OnItemSelectedListener() {
            @Override
            public void onItemSelected(Object item, Row row) {
                if (item instanceof Movie) {
                    mBackgroundURI = ((Movie) item).getBackgroundImageURI();
                    startBackgroundTimer();
                }
            }
        };
    }

    protected OnItemClickedListener getDefaultItemClickedListener() {
        return new OnItemClickedListener() {
            @Override
            public void onItemClicked(Object item, Row row) {
                if (item instanceof Program) {
                    Program program = (Program) item;
                    Log.d(TAG, "Program: " + item.toString());
                    Intent intent = new Intent(getActivity(), DetailsActivity.class);
                    intent.putExtra(getString(R.string.program), program);
                    startActivity(intent);
                } else if (item instanceof String) {

                    if( item.equals( getResources().getString( R.string.personal_settings ) ) ) {
                        Intent intent = new Intent(getActivity(), SettingsActivity.class);
                        startActivity(intent);
                    }

                    Toast.makeText(getActivity(), (String) item, Toast.LENGTH_SHORT)
                            .show();
                }
            }
        };
    }

    protected OnItemViewSelectedListener getDefaultItemViewSelectedListener() {
        return new OnItemViewSelectedListener() {

            @Override
            public void onItemSelected(Presenter.ViewHolder viewHolder, Object item, RowPresenter.ViewHolder viewHolder2, Row row) {
                if (item instanceof Program ) {
                    String url = mBackendUrl + "/Content/GetRecordingArtwork?Inetref=" + ((Program) item).getInetref();
                    try {
                        URI uri = new URI( url );
                        updateBackground(uri);
                    } catch (URISyntaxException e) {
                        Log.e( TAG, "error parsing url", e );
                    }
                } else {
                    clearBackground();
                }
            }
        };
    }

    protected void setDefaultBackground(Drawable background) {
        mDefaultBackground = background;
    }

    protected void setDefaultBackground(int resourceId) {
        mDefaultBackground = getResources().getDrawable(resourceId);
    }

    protected void updateBackground(URI uri) {
        Picasso.with(getActivity())
                .load(uri.toString())
                .resize(mMetrics.widthPixels, mMetrics.heightPixels)
                .centerCrop()
                .error(mDefaultBackground)
                .into(mBackgroundTarget);
    }

    protected void updateBackground(Drawable drawable) {
        BackgroundManager.getInstance(getActivity()).setDrawable(drawable);
    }

    protected void clearBackground() {
        BackgroundManager.getInstance(getActivity()).setDrawable(mDefaultBackground);
    }

    private void startBackgroundTimer() {
        if (null != mBackgroundTimer) {
            mBackgroundTimer.cancel();
        }
        mBackgroundTimer = new Timer();
        mBackgroundTimer.schedule(new UpdateBackgroundTask(), 300);
    }

    private class UpdateBackgroundTask extends TimerTask {

        @Override
        public void run() {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    if (mBackgroundURI != null) {
                        updateBackground(mBackgroundURI);
                    }
                }
            });

        }
    }

    private class GridItemPresenter extends Presenter {
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent) {
            TextView view = new TextView(parent.getContext());
            view.setLayoutParams(new ViewGroup.LayoutParams(200, 200));
            view.setFocusable(true);
            view.setFocusableInTouchMode(true);
            view.setBackgroundColor(getResources().getColor(R.color.default_background));
            view.setTextColor(Color.WHITE);
            view.setGravity(Gravity.CENTER);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder viewHolder, Object item) {
            ((TextView) viewHolder.view).setText((String) item);
        }

        @Override
        public void onUnbindViewHolder(ViewHolder viewHolder) {
        }
    }

    private class ProgramLoaderCompleteReceiver extends BroadcastReceiver {

        @Override
        public void onReceive( Context context, Intent intent ) {

            // when we receive a syc complete action reset the loader so it can refresh the content
            if( intent.getAction().equals( DvrServiceHelper.ACTION_COMPLETE ) ) {
                loadRows();
            }

        }

    }

}
