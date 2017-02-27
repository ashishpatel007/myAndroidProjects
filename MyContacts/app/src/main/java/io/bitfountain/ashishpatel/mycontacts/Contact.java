package io.bitfountain.ashishpatel.mycontacts;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by ashishpatel on 2016-01-03.
 */
public class Contact implements Serializable{

    private String mName;
    public ArrayList<String> emails;
    public ArrayList<String> phoneNumbers;

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }
}
