package org.mythtv.androidtv.model;

import com.google.gson.annotations.SerializedName;

import java.util.Arrays;

/**
 * Created by dmfrey on 11/2/14.
 */
public class ArtworkInfos {

    @SerializedName( "ArtworkInfos" )
    private ArtworkInfo[] artworkInfos;

    public ArtworkInfos() { }

    public ArtworkInfos( ArtworkInfo[] artworkInfos ) {
        this.artworkInfos = artworkInfos;
    }

    public ArtworkInfo[] getArtworkInfos() {
        return artworkInfos;
    }

    public void setArtworkInfos( ArtworkInfo[] artworkInfos ) {
        this.artworkInfos = artworkInfos;
    }

    @Override
    public String toString() {
        return "ArtworkInfos{" +
                "artworkInfos=" + Arrays.toString(artworkInfos) +
                '}';
    }

}
