package com.whospablo.simpletweets.services;

import android.content.Context;

import com.codepath.oauth.OAuthBaseClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.scribe.builder.api.Api;
import org.scribe.builder.api.TwitterApi;

/*
 * 
 * This is the object responsible for communicating with a REST API. 
 * Specify the constants below to change the API being communicated with.
 * See a full list of supported API classes: 
 *   https://github.com/fernandezpablo85/scribe-java/tree/master/src/main/java/org/scribe/builder/api
 * Key and Secret are provided by the developer site for the given API i.e dev.twitter.com
 * Add methods for each relevant endpoint in the API.
 * 
 * NOTE: You may want to rename this object based on the service i.e TwitterClient or FlickrClient
 * 
 */
public class TwitterClient extends OAuthBaseClient {
	private static final Class<? extends Api> REST_API_CLASS = TwitterApi.class; // Change this
    private static final String REST_URL = "https://api.twitter.com/1.1"; // Change this, base API URL
    private static final String REST_CONSUMER_KEY = "rSRJ41qtrWpPK521fekx7BKqc";       // Change this
    private static final String REST_CONSUMER_SECRET = "IT6feRWrDLdo4udi8RmNPfAZfxar6NxeW030tpVkqATKzNPChB"; // Change this
    private static final String REST_CALLBACK_URL = "oauth://simpletweets"; // Change this (here and in manifest)

	public TwitterClient(Context context) {
		super(context, REST_API_CLASS, REST_URL, REST_CONSUMER_KEY, REST_CONSUMER_SECRET, REST_CALLBACK_URL);
	}

	// CHANGE THIS
	// DEFINE METHODS for different API endpoints here
//	public void getInterestingnessList(AsyncHttpResponseHandler handler) {
//		String apiUrl = getApiUrl("?nojsoncallback=1&method=flickr.interestingness.getList");
//		// Can specify query string params directly or through RequestParams.
//		RequestParams params = new RequestParams();
//		params.put("format", "json");
//		client.get(apiUrl, params, handler);
//	}

    public void getHomeTimelineSince(long since_id, AsyncHttpResponseHandler handler) {
        String apiUrl = getApiUrl("/statuses/home_timeline.json");
        // Can specify query string params directly or through RequestParams.
        RequestParams params = new RequestParams();
        params.put("count", 50);
        params.put("since_id", since_id);
        client.get(apiUrl, params, handler);
    }

    public void getHomeTimelineBefore(long max_id, AsyncHttpResponseHandler handler) {
        String apiUrl = getApiUrl("/statuses/home_timeline.json");
        // Can specify query string params directly or through RequestParams.
        RequestParams params = new RequestParams();
        params.put("count", 50);
        params.put("max_id", max_id);
        client.get(apiUrl, params, handler);
    }

    public void getMentionsTimelineSince(long since_id, AsyncHttpResponseHandler handler) {
        String apiUrl = getApiUrl("/statuses/mentions_timeline.json");
        // Can specify query string params directly or through RequestParams.
        RequestParams params = new RequestParams();
        params.put("count", 50);
        params.put("since_id", since_id);
        client.get(apiUrl, params, handler);
    }

    public void getMentionsTimelineBefore(long max_id, AsyncHttpResponseHandler handler) {
        String apiUrl = getApiUrl("/statuses/mentions_timeline.json");
        // Can specify query string params directly or through RequestParams.
        RequestParams params = new RequestParams();
        params.put("count", 50);
        params.put("max_id", max_id);
        client.get(apiUrl, params, handler);
    }

    public void getMessagesSince(long since_id, AsyncHttpResponseHandler handler) {
        String apiUrl = getApiUrl("/direct_messages.json");
        // Can specify query string params directly or through RequestParams.
        RequestParams params = new RequestParams();
        params.put("count", 50);
        params.put("since_id", since_id);
        client.get(apiUrl, params, handler);
    }

    public void getMessagesBefore(long max_id, AsyncHttpResponseHandler handler) {
        String apiUrl = getApiUrl("/direct_messages.json");
        // Can specify query string params directly or through RequestParams.
        RequestParams params = new RequestParams();
        params.put("count", 50);
        params.put("max_id", max_id);
        client.get(apiUrl, params, handler);
    }

    public void getUserTimelineSince(String screen_name, boolean exclude_replies, long since_id, AsyncHttpResponseHandler handler) {
        String apiUrl = getApiUrl("/statuses/user_timeline.json");
        // Can specify query string params directly or through RequestParams.
        RequestParams params = new RequestParams();
        params.put("count", 50);
        params.put("screen_name", screen_name);
        params.put("since_id", since_id);
        params.put("trim_user", false);
        params.put("exclude_replies", exclude_replies);
        client.get(apiUrl, params, handler);
    }

    public void getUserTimelineBefore(String screen_name, boolean exclude_replies, long max_id, AsyncHttpResponseHandler handler) {
        String apiUrl = getApiUrl("/statuses/user_timeline.json");
        // Can specify query string params directly or through RequestParams.
        RequestParams params = new RequestParams();
        params.put("count", 50);
        params.put("screen_name", screen_name);
        params.put("max_id", max_id);
        params.put("trim_user", false);
        params.put("exclude_replies", exclude_replies);
        client.get(apiUrl, params, handler);
    }

    public void getCurrentUser(AsyncHttpResponseHandler handler) {
        String apiUrl = getApiUrl("/account/verify_credentials.json");
        // Can specify query string params directly or through RequestParams.
        client.get(apiUrl, handler);
    }

    public void postTweet(String tweet, AsyncHttpResponseHandler handler){
        String apiUrl = getApiUrl("/statuses/update.json");
        // Can specify query string params directly or through RequestParams.
        RequestParams params = new RequestParams();
        params.put("status", tweet);
        client.post(apiUrl, params, handler);
    }

	/* 1. Define the endpoint URL with getApiUrl and pass a relative path to the endpoint
	 * 	  i.e getApiUrl("statuses/home_timeline.json");
	 * 2. Define the parameters to pass to the request (query or body)
	 *    i.e RequestParams params = new RequestParams("foo", "bar");
	 * 3. Define the request method and make a call to the client
	 *    i.e client.get(apiUrl, params, handler);
	 *    i.e client.post(apiUrl, params, handler);
	 */
}
