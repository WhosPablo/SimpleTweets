package com.whospablo.simpletweets.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.View;

import com.codepath.oauth.OAuthLoginActionBarActivity;
import com.whospablo.simpletweets.R;
import com.whospablo.simpletweets.services.TwitterClient;
import com.whospablo.simpletweets.ui.home.HomeActivity;

public class LoginActivity extends OAuthLoginActionBarActivity<TwitterClient> {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
	}

	// OAuth authenticated successfully, launch primary authenticated activity
	// i.e Display application "homepage"
	@Override
	public void onLoginSuccess() {
        startHomeActivity();
	}

    public void startHomeActivity(){
        Intent i = new Intent(this, HomeActivity.class);
        startActivity(i);
    }

	// OAuth authentication flow failed, handle the error
	// i.e Display an error dialog or toast
	@Override
	public void onLoginFailure(Exception e) {
        Snackbar
            .make(findViewById(R.id.activity_login_root),"Unable to log in", Snackbar.LENGTH_LONG)
            .setAction("Retry", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    loginToRest(v);
                }
            })
            .show();
        e.printStackTrace();
	}

	// Click handler method for the button used to start OAuth flow
	// Uses the client to initiate OAuth authorization
	// This should be tied to a button used to login
	public void loginToRest(View view) {
		getClient().connect();
	}

}
