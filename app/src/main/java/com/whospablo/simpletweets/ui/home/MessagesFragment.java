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
import com.whospablo.simpletweets.util.adapters.MessagesAdapter;
import com.whospablo.simpletweets.util.fragments.RecyclerFragment;
import com.whospablo.simpletweets.util.models.Message;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;


public class MessagesFragment extends RecyclerFragment implements RefreshableFragment{
    private TwitterClient mClient;
    private List<Message> mMessages;
    private MessagesAdapter mMessagesAdapter;
    private HomeActivity mActivity;
    private ProgressBar mProgressBar;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mClient = SimpleTweetsApplication.getRestClient();
        mMessages = new ArrayList<>();
        mMessagesAdapter = new MessagesAdapter(getContext(), mMessages);
        if(getActivity() instanceof HomeActivity){
            mActivity = (HomeActivity) getActivity();
            mActivity.setCurrentFragment(this);
            mProgressBar = mActivity.getProgressBar();

        }
        populateMessages(1);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setAdapter(mMessagesAdapter);
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        setLayoutManager(llm);
        getRecyclerView().addOnScrollListener(new EndlessRecyclerViewScrollListener(llm) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                Message lastTweet = mMessages.get(mMessages.size()-1);
                populateMessages(lastTweet.id);
            }
        });

    }

    private void populateMessages(long since_id){
        mProgressBar.setVisibility(View.VISIBLE);
        mClient.getMessages(since_id, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                mMessages.addAll(Message.fromJSONArray(response));
                mMessagesAdapter.notifyDataSetChanged();
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
    public void refresh(SwipeRefreshLayout layout) {
        mSwipeRefreshLayout = layout;
        mMessages.clear();
        populateMessages(1);
    }
}