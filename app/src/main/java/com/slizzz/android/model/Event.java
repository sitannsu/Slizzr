package com.slizzz.android.model;


import com.google.android.gms.maps.model.LatLng;
import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;

import java.util.Date;


/**
 * Created by sjena on 03/10/18.
 */

@ParseClassName("Event")
public class Event extends ParseObject {




    public Event() {
        super();
    }



    public String getFeeType() {


        return getString("feeType");
    }

    public void setFeeType(String feeType) {

        put("feeType", feeType);
    }

    public ParseFile getCoverImage() {

        return getParseFile("coverImage");

    }

    public void setCoverImage(ParseFile coverImage) {
        this.coverImage = coverImage;
    }

    public int getFee() {

        return getInt("fee");
    }

    public void setFee(int fee) {
        this.fee = fee;
    }

    public String getDescriptionText() {

        return getString("descriptionText");
    }


    public Date getDate() {

        return getDate("date");
    }



    public void setDescriptionText(String descriptionText) {
        this.descriptionText = descriptionText;
    }

    public String getName() {
        return getString("name");

    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isPublic() {


        return getBoolean("isPublic");
    }

    public void setPublic(boolean aPublic) {
        isPublic = aPublic;
    }

    public int getAttendeeCount() {

        return getInt("attendeeCount");

    }

    public void setAttendeeCount(int attendeeCount) {
        this.attendeeCount = attendeeCount;
    }

    public ParseGeoPoint getPlaces() {

        return getParseGeoPoint("places");

    }

    public void setPlaces(ParseGeoPoint places) {
        this.places = places;
    }

    private ParseFile coverImage;
    private int fee;
    private String descriptionText;
    private String name;
    private boolean isPublic;
    private int attendeeCount;
    private ParseGeoPoint places;
}
