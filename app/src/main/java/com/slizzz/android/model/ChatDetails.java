package com.slizzz.android.model;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;

import java.util.Date;

/**
 * Created by sjena on 26/10/18.
 */

@ParseClassName("ChatDetails")
public class ChatDetails extends ParseObject {




    public ChatDetails() {
        super();
    }

    public int getUnreadCount()
    {
        return getInt("unreadCount");
    }


    public String getMessageType() {


        return getString("messageType");
    }

    public String getIntiatedBy() {


        return getString("intiatedBy");
    }

    public ParseFile getAttachment() {


        return getParseFile("attachment");
    }

    public String getReceiver() {


        return getString("receiver");
    }

    public String getLastMessage() {


        return getString("lastMessage");
    }

    public String getSender() {


        return getString("sender");
    }

    public Date getTimeStamp() {


        return getDate("timeStamp");
    }

    public String groupID() {


        return getString("groupID");
    }

}
