package com.motivation.first.myapplication;

import android.view.View;

import java.util.ArrayList;

public class BasicAction {

    public static final String TITLE_EXTRA = "titleDetail";
    public static final String DESCRIPTION_EXTRA = "descriptionDetail";

    public void checkExistingItems(ArrayList list, View inform) {
        if (list.isEmpty()) {
            inform.setVisibility(View.VISIBLE);
        } else {
            inform.setVisibility(View.INVISIBLE);
        }
    }
}
