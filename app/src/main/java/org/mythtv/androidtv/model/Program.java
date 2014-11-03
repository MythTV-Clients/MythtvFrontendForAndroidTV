package org.mythtv.androidtv.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by dmfrey on 11/2/14.
 */
public class Program implements Serializable {

    @SerializedName( "ProgramId" )
    private String id;

    @SerializedName( "SeriesId" )
    private String series;

    @SerializedName( "Inetref" )
    private String inetref;

    @SerializedName( "Airdate" )
    private String airDate;

    @SerializedName( "Season" )
    private String season;

    @SerializedName( "Title" )
    private String title;

    @SerializedName( "SubTitle" )
    private String subTitle;

    @SerializedName( "Description" )
    private String description;

    @SerializedName( "StartTime" )
    private Date start;

    @SerializedName( "EndTime" )
    private Date end;

    @SerializedName( "Category" )
    private String category;

    @SerializedName( "HostName" )
    private String hostname;

    @SerializedName( "FileName" )
    private String filename;

    @SerializedName( "Channel" )
    private Channel channel;

    @SerializedName( "Recording" )
    private Recording recording;

    @SerializedName( "Artwork" )
    private ArtworkInfos artworkInfos;

    public Program() { }

    public String getId() {
        return id;
    }

    public void setId( String id ) {
        this.id = id;
    }

    public String getSeries() {
        return series;
    }

    public void setSeries( String series ) {
        this.series = series;
    }

    public String getInetref() {
        return inetref;
    }

    public void setInetref( String inetref ) {
        this.inetref = inetref;
    }

    public String getAirDate() {
        return airDate;
    }

    public void setAirDate( String airDate ) {
        this.airDate = airDate;
    }

    public String getSeason() {
        return season;
    }

    public void setSeason( String season ) {
        this.season = season;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle( String title ) {
        this.title = title;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle( String subTitle ) {
        this.subTitle = subTitle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription( String description ) {
        this.description = description;
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

    public String getCategory() {
        return category;
    }

    public void setCategory( String category ) {
        this.category = category;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname( String hostname ) {
        this.hostname = hostname;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename( String filename ) {
        this.filename = filename;
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel( Channel channel ) {
        this.channel = channel;
    }

    public Recording getRecording() {
        return recording;
    }

    public void setRecording( Recording recording ) {
        this.recording = recording;
    }

    public ArtworkInfos getArtworkInfos() {
        return artworkInfos;
    }

    public void setArtworkInfos( ArtworkInfos artworkInfos ) {
        this.artworkInfos = artworkInfos;
    }

    @Override
    public String toString() {
        return "Program{" +
                "id='" + id + '\'' +
                ", series='" + series + '\'' +
                ", inetref='" + inetref + '\'' +
                ", airDate='" + airDate + '\'' +
                ", season='" + season + '\'' +
                ", title='" + title + '\'' +
                ", subTitle='" + subTitle + '\'' +
                ", start=" + start +
                ", end=" + end +
                ", category='" + category + '\'' +
                ", hostname='" + hostname + '\'' +
                ", filename='" + filename + '\'' +
                ", channel=" + channel +
                ", recording=" + recording +
                ", artworkInfos=" + artworkInfos +
                '}';
    }

}
