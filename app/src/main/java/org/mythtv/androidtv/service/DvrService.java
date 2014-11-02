package org.mythtv.androidtv.service;

import org.mythtv.androidtv.model.Program;
import org.mythtv.androidtv.model.ProgramList;
import org.mythtv.androidtv.model.ProgramListWrapper;

import java.util.Date;

import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by dmfrey on 11/2/14.
 */
public interface DvrService {

    @GET( "/Dvr/GetRecordedList" )
    ProgramListWrapper getRecordedList( @Query( "StartIndex" ) int startIndex, @Query( "Count" ) int count, @Query( "Descending" ) boolean descending );

    @GET( "/Dvr/GetRecorded" )
    Program getRecorded( @Query( "StartTime" ) Date startTime, @Query( "ChanId" ) String channelId );

}
