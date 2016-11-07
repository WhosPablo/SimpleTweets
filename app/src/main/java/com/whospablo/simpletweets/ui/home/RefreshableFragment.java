package com.whospablo.simpletweets.ui.home;

import android.support.v4.widget.SwipeRefreshLayout;

/**
 * Created by pablo_arango on 11/6/16.
 */

public interface RefreshableFragment {
    public void refresh(SwipeRefreshLayout layout);
}
