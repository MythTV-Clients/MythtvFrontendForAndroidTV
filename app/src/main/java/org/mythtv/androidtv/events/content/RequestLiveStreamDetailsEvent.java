package org.mythtv.androidtv.events.content;

import org.mythtv.androidtv.events.RequestReadEvent;

/**
 * Created by dmfrey on 11/18/14.
 */
public class RequestLiveStreamDetailsEvent extends RequestReadEvent {

    private final Integer key;

    public RequestLiveStreamDetailsEvent( final Integer key ) {

        this.key = key;

    }

    public Integer getKey() {
        return key;
    }

}
