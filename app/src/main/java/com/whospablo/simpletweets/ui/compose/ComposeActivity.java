package com.whospablo.simpletweets.ui.compose;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.whospablo.simpletweets.R;
import com.whospablo.simpletweets.SimpleTweetsApplication;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ComposeActivity extends AppCompatActivity {

    @BindView(R.id.compose_body)
    EditText bodyET;

    @BindView(R.id.compose_send)
    Button sendButton;

    @BindView(R.id.compose_character_count)
    TextView characterCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);

        bodyET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                characterCount.setText(String.valueOf(140-s.toString().length()));
            }
        });
    }


    @OnClick(R.id.compose_send)
    public void onSendTweet(){
        SimpleTweetsApplication.getRestClient().postTweet(bodyET.getText().toString(),new JsonHttpResponseHandler(){

        });
        finish();
    }



}
