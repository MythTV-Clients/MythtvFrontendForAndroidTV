package org.mythtv.androidtv.core.service;

import org.mythtv.androidtv.events.video.AllVideosEvent;
import org.mythtv.androidtv.events.video.RequestAllVideosEvent;

/**
 * Created by dmfrey on 11/24/14.
 */
public interface VideoService {

    AllVideosEvent getVideoList( RequestAllVideosEvent event );

}
