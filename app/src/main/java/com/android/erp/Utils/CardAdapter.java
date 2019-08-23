package com.android.erp.Utils;

import android.support.v7.widget.CardView;

public interface CardAdapter {
    int MAX_ELEVATION_FACTOR = 1;

    float getBaseElevation();

    CardView getCardViewAt(int position);

    int getCount();
}
