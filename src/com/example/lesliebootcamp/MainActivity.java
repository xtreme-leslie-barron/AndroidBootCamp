package com.example.lesliebootcamp;

import java.util.LinkedList;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.View.OnTouchListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;

public class MainActivity extends Activity {
	
	private TwitterConnector tweetGetter;
	private final LinkedList<Tweet> tweets = new LinkedList<Tweet>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tweet_list);
		
		((RelativeLayout)findViewById(R.id.loadingLayout)).setVisibility(View.GONE);
		
		tweetGetter = new TwitterConnector("");
				
		final EditText editText = (EditText) findViewById(R.id.searchTweets);
		editText.setOnKeyListener(new OnKeyListener() {
		    public boolean onKey(View v, int keyCode, KeyEvent event) {
		        // If the event is a key-down event on the "enter" button
		        if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
		            (keyCode == KeyEvent.KEYCODE_ENTER) && !"".equals(editText.getText().toString()) && 
		            !tweetGetter.getSearchText().equals(editText.getText().toString())) {
		          // Perform action on key press
		          
		          ((RelativeLayout)findViewById(R.id.loadingLayout)).setVisibility(View.VISIBLE);
		        	
		          tweetGetter.setSearchText(editText.getText().toString());
		          
		          new GetTweetsTask().execute();
		          return true;
		        }
		        return false;
		    }
		});
		
		
		final ListView listView = (ListView) findViewById(R.id.tweetList);
		listView.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				
				if(event.getAction() == MotionEvent.ACTION_UP && listView.getFirstVisiblePosition() == 0) {
					((RelativeLayout)findViewById(R.id.loadingLayout)).setVisibility(View.VISIBLE);
					new LoadMoreTweets().execute();
				}
				
				return v.onTouchEvent(event);
			}                  
	    });
	}

	
	
	private class GetTweetsTask extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			tweets.clear();
			tweets.addAll(tweetGetter.getTweets());
			
			runOnUiThread(new Runnable() {
				
				@Override
				public void run() {
					ListView list = (ListView) findViewById(R.id.tweetList);
					ArrayAdapter<Tweet> adapter =
						new TweetAdapter(MainActivity.this, R.layout.tweet, tweets);
					list.setAdapter(adapter);
					
					((RelativeLayout)findViewById(R.id.loadingLayout)).setVisibility(View.GONE);
				}
			});
			
			
			return null;
		}
		
	}
	
	private class LoadMoreTweets extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			tweets.addAll(0, tweetGetter.getTweets());

			runOnUiThread(new Runnable() {
				
				@Override
				public void run() {
					ListView list = (ListView) findViewById(R.id.tweetList);
					ArrayAdapter<Tweet> adapter =
						new TweetAdapter(MainActivity.this, R.layout.tweet, tweets);
					list.setAdapter(adapter);
					
					((RelativeLayout)findViewById(R.id.loadingLayout)).setVisibility(View.GONE);
				}
			});
			
			
			return null;
		}
		
	}

}
