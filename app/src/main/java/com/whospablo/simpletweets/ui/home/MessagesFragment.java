package com.whospablo.simpletweets.ui.home;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

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


public class MessagesFragment extends RecyclerFragment implements HomeActivity.RefreshableFragment{
    private TwitterClient mClient;
    private List<Message> mMessages;
    private MessagesAdapter mMessagesAdapter;
    private HomeActivity.OnRefreshDoneListener mListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mClient = SimpleTweetsApplication.getRestClient();
        mMessages = new ArrayList<>();
        mMessagesAdapter = new MessagesAdapter(getContext(), mMessages);
        loadRecent();

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
                loadMore();
            }
        });

    }

    private void loadMore(){
        long lastMessageid = Long.MAX_VALUE;

        if(mMessages.size()>0){
            lastMessageid = mMessages.get(mMessages.size()-1).id-1;
        }
        mClient.getMessagesBefore(lastMessageid, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                mMessages.addAll(Message.fromJSONArray(response));
                mMessagesAdapter.notifyDataSetChanged();
            }


            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);

            }
        });
    }

    private void loadRecent(){
        long mostRecentId = 1;

        if(mMessages.size()>0){
            mostRecentId = mMessages.get(0).id;
        }

        mClient.getMessagesSince(mostRecentId, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                List<Message> newMessages = Message.fromJSONArray(response);
                for(int i = newMessages.size()-1; i>=0; i--){
                    mMessages.add(0,newMessages.get(i));
                }
                mMessagesAdapter.notifyDataSetChanged();
                doneRefreshing();
            }


            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                doneRefreshing();
            }
        });
    }

    private void doneRefreshing(){
        if(mListener!=null)
            mListener.refreshDone();
    }

    @Override
    public void refresh(HomeActivity.OnRefreshDoneListener listener) {
        mListener = listener;
        loadRecent();
    }
}