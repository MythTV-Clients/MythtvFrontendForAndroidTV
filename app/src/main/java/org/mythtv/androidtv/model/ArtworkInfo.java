package org.mythtv.androidtv.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by dmfrey on 11/2/14.
 */
public class ArtworkInfo {

    @SerializedName( "URL" )
    private String url;

    @SerializedName( "FileName" )
    private String filename;

    @SerializedName( "StorageGroup" )
    private String storageGroup;

    @SerializedName( "Type" )
    private String type;

    public ArtworkInfo() { }

    public ArtworkInfo( String url, String filename, String storageGroup, String type ) {

        this.url = url;
        this.filename = filename;
        this.storageGroup = storageGroup;
        this.type = type;

    }

    public String getUrl() {
        return url;
    }

    public void setUrl( String url ) {
        this.url = url;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename( String filename ) {
        this.filename = filename;
    }

    public String getStorageGroup() {
        return storageGroup;
    }

    public void setStorageGroup( String storageGroup ) {
        this.storageGroup = storageGroup;
    }

    public String getType() {
        return type;
    }

    public void setType( String type ) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "ArtworkInfo{" +
                "url='" + url + '\'' +
                ", filename='" + filename + '\'' +
                ", storageGroup='" + storageGroup + '\'' +
                ", type='" + type + '\'' +
                '}';
    }

}
