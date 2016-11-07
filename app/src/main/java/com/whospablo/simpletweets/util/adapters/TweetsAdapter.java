package com.whospablo.simpletweets.util.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.whospablo.simpletweets.R;
import com.whospablo.simpletweets.util.models.Tweet;
import com.whospablo.simpletweets.util.models.User;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

/**
 * Created by pablo_arango on 11/3/16.
 */

public class TweetsAdapter extends RecyclerView.Adapter<TweetsAdapter.ViewHolder> {

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_img)
        ImageView img;
        @BindView(R.id.item_name)
        TextView name;
        @BindView(R.id.item_handle)
        TextView handle;
        @BindView(R.id.item_body)
        TextView body;
        @BindView(R.id.item_hrs_since)
        TextView hrsSince;
        @BindView(R.id.item_img_progress)
        ProgressBar imgProgress;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    private List<Tweet> mTweets;
    // Store the context for easy access
    private Context mContext;

    // Pass in the contact array into the constructor
    public TweetsAdapter(Context context, List<Tweet> tweets) {
        mTweets = tweets;
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View tweetView = LayoutInflater
                            .from(parent.getContext())
                            .inflate(R.layout.item_tweet, parent, false);
        return new ViewHolder(tweetView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder tweetHolder, int position) {
        Tweet tweet = mTweets.get(position);
        User user = tweet.getUser();
        tweetHolder.body.setText(tweet.getBody());
        tweetHolder.name.setText(user.getName());
        tweetHolder.handle.setText(user.getHandle());
        tweetHolder.hrsSince.setText(getRelativeTimeAgo(tweet.getCreatedAt()));

        Glide
            .with(mContext)
            .load(user.getImgUrl())
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        tweetHolder.imgProgress.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        tweetHolder.imgProgress.setVisibility(View.GONE);
                        return false;
                    }
                })
                .bitmapTransform(new RoundedCornersTransformation(mContext, 10, 0))
                .into(tweetHolder.img);
    }

    @Override
    public int getItemCount() {
        return mTweets.size();
    }

    public String getRelativeTimeAgo(String rawJsonDate) {
        String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
        SimpleDateFormat sf = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
        sf.setLenient(true);

        String relativeDate = "";
        try {
            long dateMillis = sf.parse(rawJsonDate).getTime();
            relativeDate = DateUtils.getRelativeTimeSpanString(dateMillis,
                    System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return relativeDate;
    }



//    @Override
//    public TweetsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        Context context = parent.getContext();
//        LayoutInflater inflater = LayoutInflater.from(context);
//
//        // Inflate the custom layout
//        View contactView = inflater.inflate(R.layout.item_contact, parent, false);
//
//        // Return a new holder instance
//        ViewHolder viewHolder = new ViewHolder(contactView);
//        return viewHolder;
//    }
//
//    // Involves populating data into the item through holder
//    @Override
//    public void onBindViewHolder(TweetsAdapter.ViewHolder viewHolder, int position) {
//        // Get the data model based on position
//        Contact contact = mContacts.get(position);
//
//        // Set item views based on your views and data model
//        TextView textView = viewHolder.nameTextView;
//        textView.setText(contact.getName());
//        Button button = viewHolder.messageButton;
//        button.setText("Message");
//    }
//
//    // Returns the total count of items in the list
//    @Override
//    public int getItemCount() {
//        return mContacts.size();
//    }
}
