package org.mythtv.androidtv.events.content;

import org.mythtv.androidtv.events.DeleteEvent;

/**
 * Created by dmfrey on 11/18/14.
 */
public class RemoveLiveStreamEvent extends DeleteEvent {

    private final int key;

    public RemoveLiveStreamEvent( final int key ) {

        this.key = key;
    }

    public int getKey() {
        return key;
    }

}
