package org.mythtv.androidtv.ui.videos;

import android.support.v17.leanback.widget.AbstractDetailsDescriptionPresenter;

import org.mythtv.androidtv.core.domain.dvr.Program;
import org.mythtv.androidtv.core.domain.video.Video;

public class VideoDetailsDescriptionPresenter extends AbstractDetailsDescriptionPresenter {

    @Override
    protected void onBindDescription( ViewHolder viewHolder, Object item ) {

        Video video = (Video) item;

        if( null != video ) {
            viewHolder.getTitle().setText( video.getTitle() );
            viewHolder.getSubtitle().setText( video.getSubTitle() );
            viewHolder.getBody().setText( video.getDescription() );
        }

    }

}
