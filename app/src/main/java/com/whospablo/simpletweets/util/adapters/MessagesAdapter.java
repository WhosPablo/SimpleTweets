package com.whospablo.simpletweets.util.adapters;

import android.content.Context;
import android.content.Intent;
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
import com.whospablo.simpletweets.ui.profile.ProfileActivity;
import com.whospablo.simpletweets.util.models.Message;
import com.whospablo.simpletweets.util.models.User;

import org.parceler.Parcels;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Created by pablo_arango on 11/3/16.
 */

public class MessagesAdapter extends RecyclerView.Adapter<MessagesAdapter.ViewHolder> {

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

    private List<Message> mMessages;
    // Store the context for easy access
    private Context mContext;

    // Pass in the contact array into the constructor
    public MessagesAdapter(Context context, List<Message> messages) {
        mMessages = messages;
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View tweetView = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.item_message, parent, false);
        return new ViewHolder(tweetView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder messageHolder, int position) {
        Message message = mMessages.get(position);
        final User user = message.getSender();
        messageHolder.body.setText(message.getBody());
        messageHolder.name.setText(user.getName());
        messageHolder.handle.setText(user.getHandle());
        messageHolder.hrsSince.setText(getRelativeTimeAgo(message.getCreatedAt()));

        Glide
                .with(mContext)
                .load(user.getImgUrl())
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        messageHolder.imgProgress.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        messageHolder.imgProgress.setVisibility(View.GONE);
                        return false;
                    }
                })
                .bitmapTransform(new CropCircleTransformation(mContext))
                .into(messageHolder.img);

        messageHolder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mContext, ProfileActivity.class);
                i.putExtra(ProfileActivity.PROFILE_USER, Parcels.wrap(user));
                mContext.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mMessages.size();
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
}
