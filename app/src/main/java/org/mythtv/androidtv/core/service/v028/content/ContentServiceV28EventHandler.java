package org.mythtv.androidtv.core.service.v028.content;

import android.content.Context;

import org.mythtv.androidtv.core.service.ContentService;
import org.mythtv.androidtv.events.content.AddLiveStreamEvent;
import org.mythtv.androidtv.events.content.AddRecordingLiveStreamEvent;
import org.mythtv.androidtv.events.content.AddVideoLiveStreamEvent;
import org.mythtv.androidtv.events.content.LiveStreamAddedEvent;
import org.mythtv.androidtv.events.content.LiveStreamDetailsEvent;
import org.mythtv.androidtv.events.content.LiveStreamRemovedEvent;
import org.mythtv.androidtv.events.content.RemoveLiveStreamEvent;
import org.mythtv.androidtv.events.content.RequestLiveStreamDetailsEvent;
import org.mythtv.services.api.Bool;
import org.mythtv.services.api.ETagInfo;
import org.mythtv.services.api.MythTvApi028Context;
import org.mythtv.services.api.MythTvApiContext;
import org.mythtv.services.api.v028.beans.LiveStreamInfo;

import retrofit.RetrofitError;

/**
 * Created by dmfrey on 11/18/14.
 */
public class ContentServiceV28EventHandler implements ContentService {

    private static final String LIVE_STREAM_REQ_ID = "LIVE_STREAM_REQ_ID";

    MythTvApi028Context mMythTvApiContext;

    public ContentServiceV28EventHandler( MythTvApiContext mythTvApiContext ) {

        mMythTvApiContext = (MythTvApi028Context) mythTvApiContext;

    }

    @Override
    public LiveStreamDetailsEvent getLiveStream( RequestLiveStreamDetailsEvent event ) {

        ETagInfo eTagInfo = mMythTvApiContext.getEtag( LIVE_STREAM_REQ_ID, true );
        try {
            LiveStreamInfo liveStreamInfo = mMythTvApiContext.getContentService().getLiveStream(event.getKey(), eTagInfo, LIVE_STREAM_REQ_ID);
            if (null != liveStreamInfo) {
                return new LiveStreamDetailsEvent(liveStreamInfo.getId(), LiveStreamInfoHelper.toDetails(liveStreamInfo));
            }
        } catch( RetrofitError e ) {
            if( e.getResponse().getStatus() == 304 ) {
                return LiveStreamDetailsEvent.notModified( event.getKey() );
            }
        }

        return LiveStreamDetailsEvent.notFound( event.getKey() );
    }

    @Override
    public LiveStreamAddedEvent addLiveStream( AddLiveStreamEvent event ) {

        ETagInfo eTagInfo = mMythTvApiContext.getEtag( LIVE_STREAM_REQ_ID, false );
        LiveStreamInfo liveStreamInfo = mMythTvApiContext.getContentService().addLiveStream( event.getStorageGroup(), event.getFileName(), event.getHostName(), event.getMaxSegments(), event.getWidth(), event.getHeight(), event.getBitrate(), event.getAudioBitrate(), event.getSampleRate(), eTagInfo, LIVE_STREAM_REQ_ID );
        if( null != liveStreamInfo ) {
            return new LiveStreamAddedEvent(liveStreamInfo.getId(), LiveStreamInfoHelper.toDetails(liveStreamInfo));
        }

        return LiveStreamAddedEvent.notAdded();
    }

    @Override
    public LiveStreamAddedEvent addRecordingLiveStream( AddRecordingLiveStreamEvent event ) {

        ETagInfo eTagInfo = mMythTvApiContext.getEtag( LIVE_STREAM_REQ_ID, false );
        LiveStreamInfo liveStreamInfo = mMythTvApiContext.getContentService().addRecordingLiveStream( event.getRecordedId(), event.getChanId(), event.getStartTime(), event.getMaxSegments(), event.getWidth(), event.getHeight(), event.getBitrate(), event.getAudioBitrate(), event.getSampleRate(), eTagInfo, LIVE_STREAM_REQ_ID );
        if( null != liveStreamInfo ) {
            return new LiveStreamAddedEvent(liveStreamInfo.getId(), LiveStreamInfoHelper.toDetails(liveStreamInfo));
        }

        return LiveStreamAddedEvent.notAdded();
    }

    @Override
    public LiveStreamAddedEvent addVideoLiveStream( AddVideoLiveStreamEvent event ) {

        ETagInfo eTagInfo = mMythTvApiContext.getEtag( LIVE_STREAM_REQ_ID, false );
        LiveStreamInfo liveStreamInfo = mMythTvApiContext.getContentService().addVideoLiveStream( event.getId(), event.getMaxSegments(), event.getWidth(), event.getHeight(), event.getBitrate(), event.getAudioBitrate(), event.getSampleRate(), eTagInfo, LIVE_STREAM_REQ_ID );
        if( null != liveStreamInfo ) {
            return new LiveStreamAddedEvent(liveStreamInfo.getId(), LiveStreamInfoHelper.toDetails(liveStreamInfo));
        }

        return LiveStreamAddedEvent.notAdded();
    }

    @Override
    public LiveStreamRemovedEvent removeLiveStream( RemoveLiveStreamEvent event ) {

        ETagInfo eTagInfo = mMythTvApiContext.getEtag( LIVE_STREAM_REQ_ID, false );
        Bool deleted = mMythTvApiContext.getContentService().removeLiveStream( event.getKey(), eTagInfo, LIVE_STREAM_REQ_ID );
        if( deleted.getValue() ) {
            return new LiveStreamRemovedEvent( event.getKey() );
        }

        return LiveStreamRemovedEvent.deletionFailed( event.getKey() );
    }

}
