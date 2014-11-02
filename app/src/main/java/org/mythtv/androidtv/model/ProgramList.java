package org.mythtv.androidtv.model;

import com.google.gson.annotations.SerializedName;

import java.util.Arrays;
import java.util.Date;

/**
 * Created by dmfrey on 11/2/14.
 */
public class ProgramList {

    @SerializedName( "AsOf" )
    private Date asOf;

    @SerializedName( "Count" )
    private String count;

    @SerializedName( "Programs" )
    private Program[] programs;

    public ProgramList() { }

    public Date getAsOf() {
        return asOf;
    }

    public void setAsOf( Date asOf ) {
        this.asOf = asOf;
    }

    public String getCount() {
        return count;
    }

    public void setCount( String count ) {
        this.count = count;
    }

    public Program[] getPrograms() {
        return programs;
    }

    public void setPrograms( Program[] programs ) {
        this.programs = programs;
    }

    @Override
    public String toString() {
        return "ProgramList{" +
                "asOf=" + asOf +
                ", count='" + count + '\'' +
                ", programs=" + Arrays.toString(programs) +
                '}';
    }

}
