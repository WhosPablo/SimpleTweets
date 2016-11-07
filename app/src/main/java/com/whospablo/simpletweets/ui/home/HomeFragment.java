package com.whospablo.simpletweets.ui.home;


import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.whospablo.simpletweets.SimpleTweetsApplication;
import com.whospablo.simpletweets.services.TwitterClient;
import com.whospablo.simpletweets.util.EndlessRecyclerViewScrollListener;
import com.whospablo.simpletweets.util.adapters.TweetsAdapter;
import com.whospablo.simpletweets.util.fragments.RecyclerFragment;
import com.whospablo.simpletweets.util.models.Tweet;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * Created by pablo_arango on 11/2/16.
 */

public class HomeFragment extends RecyclerFragment implements RefreshableFragment {
    private TwitterClient mClient;
    private List<Tweet> mTweets;
    private TweetsAdapter mTweetsAdapter;
    private HomeActivity mActivity;
    private ProgressBar mProgressBar;
    private SwipeRefreshLayout mSwipeRefreshLayout;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mClient = SimpleTweetsApplication.getRestClient();
        mTweets = new ArrayList<>();
        mTweetsAdapter = new TweetsAdapter(getContext(), mTweets);
        if(getActivity() instanceof HomeActivity){
            mActivity = (HomeActivity) getActivity();
            mActivity.setCurrentFragment(this);
            mProgressBar = mActivity.getProgressBar();

        }
        populateTimeline(1);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setAdapter(mTweetsAdapter);
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        setLayoutManager(llm);
        getRecyclerView().addOnScrollListener(new EndlessRecyclerViewScrollListener(llm) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                Tweet lastTweet = mTweets.get(mTweets.size()-1);
                populateTimeline(lastTweet.id);
            }
        });
    }

    private void populateTimeline(long since_id){
        mProgressBar.setVisibility(View.VISIBLE);
        mClient.getHomeTimeline(since_id, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                mTweets.addAll(Tweet.fromJSONArray(response));
                mTweetsAdapter.notifyDataSetChanged();
                mProgressBar.setVisibility(View.GONE);
                if(mSwipeRefreshLayout != null){
                    mSwipeRefreshLayout.setRefreshing(false);
                }
            }


            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });
    }

    @Override
    public void refresh( SwipeRefreshLayout layout) {
        mSwipeRefreshLayout = layout;
        mTweets.clear();
        populateTimeline(1);
    }
}
