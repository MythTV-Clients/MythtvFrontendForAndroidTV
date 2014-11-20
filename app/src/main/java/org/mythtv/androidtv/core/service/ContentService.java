package org.mythtv.androidtv.core.service;

import org.mythtv.androidtv.events.content.AddLiveStreamEvent;
import org.mythtv.androidtv.events.content.AddRecordingLiveStreamEvent;
import org.mythtv.androidtv.events.content.AddVideoLiveStreamEvent;
import org.mythtv.androidtv.events.content.LiveStreamAddedEvent;
import org.mythtv.androidtv.events.content.LiveStreamDetails;
import org.mythtv.androidtv.events.content.LiveStreamDetailsEvent;
import org.mythtv.androidtv.events.content.LiveStreamRemovedEvent;
import org.mythtv.androidtv.events.content.RemoveLiveStreamEvent;
import org.mythtv.androidtv.events.content.RequestLiveStreamDetailsEvent;

/**
 * Created by dmfrey on 11/18/14.
 */
public interface ContentService {

    LiveStreamDetailsEvent getLiveStream( RequestLiveStreamDetailsEvent event );

    LiveStreamAddedEvent addLiveStream( AddLiveStreamEvent event );

    LiveStreamAddedEvent addRecordingLiveStream( AddRecordingLiveStreamEvent event );

    LiveStreamAddedEvent addVideoLiveStream( AddVideoLiveStreamEvent event );

    LiveStreamRemovedEvent removeLiveStream( RemoveLiveStreamEvent event );

}
