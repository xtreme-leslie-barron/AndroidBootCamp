package com.example.lesliebootcamp;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class TweetAdapter extends ArrayAdapter<Tweet>{

    private Context context; 
    private int layoutResourceId;    
    private List<Tweet> data = null;
    
    public TweetAdapter(Context context, int layoutResourceId, List<Tweet> data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        TweetHolder holder = null;
        
        if(row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            
            holder = new TweetHolder();
            holder.imgIcon = (ImageView)row.findViewById(R.id.userImage);
            holder.tweetText = (TextView)row.findViewById(R.id.tweetText);
            holder.user = (TextView)row.findViewById(R.id.user);
            holder.createTime = (TextView)row.findViewById(R.id.createTime);
            
            row.setTag(holder);
        }
        else
        {
            holder = (TweetHolder)row.getTag();
        }
        
        Tweet tweet = data.get(position);
        holder.imgIcon.setImageBitmap(tweet.getUserImage());
        holder.tweetText.setText(tweet.getText());
        holder.user.setText(tweet.getUser());
        holder.createTime.setText(tweet.getCreateTime());
        
        return row;
    }
    
    static class TweetHolder
    {
        ImageView imgIcon;
        TextView tweetText;
        TextView user;
        TextView createTime;
    }
}