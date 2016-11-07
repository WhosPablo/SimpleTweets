package com.whospablo.simpletweets;

import android.app.Application;
import android.content.Context;

import com.raizlabs.android.dbflow.config.FlowConfig;
import com.raizlabs.android.dbflow.config.FlowLog;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.whospablo.simpletweets.services.TwitterClient;
import com.whospablo.simpletweets.util.models.User;

/*
 * This is the Android application itself and is used to configure various settings
 * including the image cache in memory and on disk. This also adds a singleton
 * for accessing the relevant rest client.
 *
 *     TwitterClient client = SimpleTweetsApplication.getRestClient();
 *     // use client to send requests to API
 *
 */
public class SimpleTweetsApplication extends Application {
	private static Context context;
    private static User currentUser;

	@Override
	public void onCreate() {
		super.onCreate();

		FlowManager.init(new FlowConfig.Builder(this).build());
		FlowLog.setMinimumLoggingLevel(FlowLog.Level.V);

		SimpleTweetsApplication.context = this;
	}

	public static TwitterClient getRestClient() {
		return (TwitterClient) TwitterClient.getInstance(TwitterClient.class, SimpleTweetsApplication.context);
	}

	public static void setCurrentUser(User user) {
        currentUser = user;
	}

    public static User getCurrentUser(){
        return currentUser;
    }


}