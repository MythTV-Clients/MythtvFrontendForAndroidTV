package org.mythtv.androidtv.core.service;

import org.mythtv.androidtv.events.dvr.AllProgramsEvent;
import org.mythtv.androidtv.events.dvr.RequestAllRecordedProgramsEvent;

/**
 * Created by dmfrey on 11/13/14.
 */
public interface DvrService {

    AllProgramsEvent getRecordedPrograms( RequestAllRecordedProgramsEvent event );

}
