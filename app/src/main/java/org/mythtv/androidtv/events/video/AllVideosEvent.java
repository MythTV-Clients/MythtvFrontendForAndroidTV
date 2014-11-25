package org.mythtv.androidtv.events.video;

import org.mythtv.androidtv.events.ReadEvent;
import org.mythtv.androidtv.events.dvr.ProgramDetails;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by dmfrey on 11/12/14.
 */
public class AllVideosEvent extends ReadEvent {

    private final List<VideoDetails> details;

    public AllVideosEvent( final List<VideoDetails> details ) {

        this.details = Collections.unmodifiableList( details );

    }

    public List<VideoDetails> getDetails() {
        return details;
    }

}
