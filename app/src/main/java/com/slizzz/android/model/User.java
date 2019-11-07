package com.slizzz.android.model;

import com.parse.ParseClassName;
import com.parse.ParseObject;

/**
 * Created by sjena on 24/10/18.
 */

@ParseClassName("User")

public class User extends ParseObject {

    public User() {
        super();
    }


    public String getLastName() {


        return getString("lastName");
    }

    public String getGender() {


        return getString("gender");
    }

    public Object getImage() {


        return getString("images");
    }

}
