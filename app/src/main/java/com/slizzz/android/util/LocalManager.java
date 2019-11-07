package com.slizzz.android.util;

import com.parse.ParseUser;
import com.slizzz.android.model.Event;

/**
 * Created by sjena on 24/10/18.
 */

public class LocalManager {

    private static LocalManager localManager;

    public static LocalManager getInstance()
    {
        if(localManager == null)
        {
            localManager = new LocalManager();
        }

        return  localManager;

    }

    public Event getSelectedEvent() {
        return selectedEvent;
    }

    public void setSelectedEvent(Event selectedEvent) {
        this.selectedEvent = selectedEvent;
    }

    public Event selectedEvent;


    public ParseUser getParseUser() {
        return parseUser;
    }

    public void setParseUser(ParseUser parseUser) {
        this.parseUser = parseUser;
    }

    public ParseUser parseUser;

}
