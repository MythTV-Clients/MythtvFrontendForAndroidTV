package org.mythtv.androidtv.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by dmfrey on 11/2/14.
 */
public class ProgramListWrapper {

    @SerializedName( "ProgramList" )
    private ProgramList programList;

    public ProgramListWrapper() { }

    public ProgramList getProgramList() {
        return programList;
    }

    public void setProgramList( ProgramList programList ) {
        this.programList = programList;
    }

    @Override
    public String toString() {
        return "ProgramListWrapper{" +
                "programList=" + programList +
                '}';
    }

}
