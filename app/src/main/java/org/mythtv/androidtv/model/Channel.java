package org.mythtv.androidtv.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by dmfrey on 11/2/14.
 */
public class Channel {

    @SerializedName( "ChanId" )
    private String id;

    @SerializedName( "ChannelNum" )
    private String number;

    @SerializedName( "CallSign" )
    private String callSign;

    @SerializedName( "ChannelName" )
    private String name;

    @SerializedName( "IconURL" )
    private String iconUrl;

    public Channel() { }

    public Channel( String id, String number, String callSign, String name, String iconUrl ) {

        this.id = id;
        this.number = number;
        this.callSign = callSign;
        this.name = name;
        this.iconUrl = iconUrl;

    }

    public String getId() {
        return id;
    }

    public void setId( String id ) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber( String number ) {
        this.number = number;
    }

    public String getCallSign() {
        return callSign;
    }

    public void setCallSign( String callSign ) {
        this.callSign = callSign;
    }

    public String getName() {
        return name;
    }

    public void setName( String name ) {
        this.name = name;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl( String iconUrl ) {
        this.iconUrl = iconUrl;
    }

    @Override
    public String toString() {
        return "Channel{" +
                "id='" + id + '\'' +
                ", number='" + number + '\'' +
                ", callSign='" + callSign + '\'' +
                ", name='" + name + '\'' +
                ", iconUrl='" + iconUrl + '\'' +
                '}';
    }

}
