package org.mythtv.androidtv.ui;

import android.support.v17.leanback.widget.AbstractDetailsDescriptionPresenter;

import org.mythtv.androidtv.core.domain.dvr.Program;

public class DetailsDescriptionPresenter extends AbstractDetailsDescriptionPresenter {

    @Override
    protected void onBindDescription(ViewHolder viewHolder, Object item) {

        Program program = (Program) item;

        if (program != null) {
            viewHolder.getTitle().setText(program.getTitle());
            viewHolder.getSubtitle().setText(program.getSubTitle());
            viewHolder.getBody().setText(program.getDescription());
        }

    }

}
