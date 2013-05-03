package com.example.lesliebootcamp;

import java.util.List;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends Activity {
	
	private TwitterConnector tweetGetter;
	
	public MainActivity() {
		tweetGetter = new TwitterConnector(getString(R.string.default_search));
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tweet_list);
		
		new GetTweetsTask().execute();
		
		
	}

	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	private class GetTweetsTask extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			final List<Tweet> tweets = tweetGetter.getTweets();
			
			runOnUiThread(new Runnable() {
				
				@Override
				public void run() {
					ListView list = new ListView(MainActivity.this);
					ArrayAdapter<Tweet> adapter =
						new TweetAdapter(MainActivity.this, R.layout.tweet, tweets);
					list.setAdapter(adapter);
					setContentView(list);
					
				}
			});
			
			return null;
		}
		
	}

}
