package org.mythtv.androidtv.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by dmfrey on 11/2/14.
 */
public class Recording {

    @SerializedName( "RecordId" )
    private String id;

    @SerializedName( "StartTs" )
    private Date start;

    @SerializedName( "EndTs" )
    private Date end;

    @SerializedName( "StorageGroup" )
    private String storageGroup;

    public Recording() { }

    public Recording( String id, Date start, Date end ) {

        this.id = id;
        this.start = start;
        this.end = end;

    }

    public String getId() {
        return id;
    }

    public void setId( String id ) {
        this.id = id;
    }

    public Date getStart() {
        return start;
    }

    public void setStart( Date start ) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd( Date end ) {
        this.end = end;
    }

    public String getStorageGroup() {
        return storageGroup;
    }

    public void setStorageGroup( String storageGroup ) {
        this.storageGroup = storageGroup;
    }

    @Override
    public String toString() {
        return "Recording{" +
                "id='" + id + '\'' +
                ", start=" + start +
                ", end=" + end +
                '}';
    }

}
