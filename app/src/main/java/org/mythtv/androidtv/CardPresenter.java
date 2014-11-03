package org.mythtv.androidtv;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v17.leanback.widget.ImageCardView;
import android.support.v17.leanback.widget.Presenter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.mythtv.androidtv.model.Program;
import org.mythtv.androidtv.settings.SettingsActivity;

import java.net.URI;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class CardPresenter extends Presenter {

    private static final String TAG = "CardPresenter";

    private static final DateFormat formatter = new SimpleDateFormat( "yyyy-MM-dd'T'HH:mm:ss" );

    private static Context mContext;
    private static int CARD_WIDTH = 313;
    private static int CARD_HEIGHT = 176;

    private String mBackendUrl;

    static class ViewHolder extends Presenter.ViewHolder {
        private Program mProgram;
        private ImageCardView mCardView;
        private Drawable mDefaultCardImage;
        private PicassoImageCardViewTarget mImageCardViewTarget;

        public ViewHolder(View view) {
            super(view);
            mCardView = (ImageCardView) view;
            mImageCardViewTarget = new PicassoImageCardViewTarget(mCardView);
            mDefaultCardImage = mContext.getResources().getDrawable(R.drawable.movie);
        }

        public void setProgram(Program p) {
            mProgram = p;
        }

        public Program getProgram() {
            return mProgram;
        }

        public ImageCardView getCardView() {
            return mCardView;
        }

        protected void updateCardViewImage( String uri ) {
            Picasso.with( mContext )
                    .load( uri.toString() )
                    .resize( CARD_WIDTH, CARD_HEIGHT )
                    .centerCrop()
                    .error( mDefaultCardImage )
                    .into( mImageCardViewTarget );
        }

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent) {
        Log.d(TAG, "onCreateViewHolder");

        mContext = parent.getContext();

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences( mContext );
        mBackendUrl = sharedPref.getString( SettingsActivity.KEY_PREF_BACKEND_URL, "" );

        ImageCardView cardView = new ImageCardView(mContext);
        cardView.setFocusable(true);
        cardView.setFocusableInTouchMode(true);
        cardView.setBackgroundColor(mContext.getResources().getColor(R.color.fastlane_background));
        return new ViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder( Presenter.ViewHolder viewHolder, Object item ) {
        Program program = (Program) item;
        ((ViewHolder) viewHolder).setProgram(program);

        Log.d( TAG, "onBindViewHolder" );
//        if ( program.getCardImageUrl() != null) {
            ((ViewHolder) viewHolder).mCardView.setTitleText( program.getSubTitle() );
            ((ViewHolder) viewHolder).mCardView.setContentText( program.getDescription() );
            ((ViewHolder) viewHolder).mCardView.setMainImageDimensions( CARD_WIDTH, CARD_HEIGHT );
            //((ViewHolder) viewHolder).mCardView.setBadgeImage(mContext.getResources().getDrawable(
            //        R.drawable.videos_by_google_icon));
        Log.i( TAG, program.toString() );

        DateTime start = new DateTime( program.getRecording().getStart() ).withZone( DateTimeZone.UTC );
        Log.i( TAG, mBackendUrl + "/Content/GetPreviewImage?ChanId=" + program.getChannel().getId() + "&StartTime=" + start.toString( "yyyy-MM-dd'T'HH:mm:ss" ) + "&Width=" + CARD_WIDTH );
            ((ViewHolder) viewHolder).updateCardViewImage(mBackendUrl + "/Content/GetPreviewImage?ChanId=" + program.getChannel().getId() + "&StartTime=" + start.toString( "yyyy-MM-dd'T'HH:mm:ss" ) + "&Width=" + CARD_WIDTH);
//        }
    }

    @Override
    public void onUnbindViewHolder(Presenter.ViewHolder viewHolder) {
        Log.d(TAG, "onUnbindViewHolder");
    }

    @Override
    public void onViewAttachedToWindow(Presenter.ViewHolder viewHolder) {
        Log.d(TAG, "onViewAttachedToWindow");
    }

    public static class PicassoImageCardViewTarget implements Target {
        private ImageCardView mImageCardView;

        public PicassoImageCardViewTarget(ImageCardView mImageCardView) {
            this.mImageCardView = mImageCardView;
        }

        @Override
        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom loadedFrom) {
            Drawable bitmapDrawable = new BitmapDrawable(mContext.getResources(), bitmap);
            mImageCardView.setMainImage(bitmapDrawable);
        }

        @Override
        public void onBitmapFailed(Drawable drawable) {
            mImageCardView.setMainImage(drawable);
        }

        @Override
        public void onPrepareLoad(Drawable drawable) {
            // Do nothing, default_background manager has its own transitions
        }
    }

    private String get_UTC_Datetime_from_timestamp( long timeStamp ) {

        try {

            Calendar cal = Calendar.getInstance();
            TimeZone tz = cal.getTimeZone();

            int tzt = tz.getOffset( System.currentTimeMillis() );

            timeStamp -= tzt;

            // DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",Locale.getDefault());
            DateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd'T'HH:mm:ss" );
            Date netDate = new Date( timeStamp );

            return sdf.format(netDate);

        } catch( Exception ex ) {
            return "";
        }

    }

}
