package com.android.BE;

import java.io.Serializable;

/**
 * Created by Balram on 7/8/2015.
 */
public class MyAccountBE implements Serializable {

    String category;
    String states;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getStates() {
        return states;
    }

    public void setStates(String states) {
        this.states = states;
    }
}
