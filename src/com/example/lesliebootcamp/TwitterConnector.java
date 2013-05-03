package com.example.lesliebootcamp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

public class TwitterConnector {

	private final static String BASE_TWITTER_URL = "http://search.twitter.com/search.json";
	private final static String PARAMETER_FORMAT = "?q=%s&include_entities=true&result_type=mixed";
	
	private String parameterUrl;
	private String searchText;
	
	public TwitterConnector(String searchText) {
		setSearchText(searchText);
	}
	
	
	public void setSearchText(String searchText) {
		this.searchText = searchText;
		try {
			parameterUrl = String.format(PARAMETER_FORMAT, URLEncoder.encode(searchText, "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			Log.e("URL Encoding Error", e.getMessage());
		}
	}
	
	public String getSearchText() {
		return searchText;
	}
	
	public List<Tweet> getTweets() {
		List<Tweet> tweets = new ArrayList<Tweet>();
		JSONObject jsonObject = null;
		
		String url = BASE_TWITTER_URL + parameterUrl;
		
		Log.i("URL", url);
		
		try {
			jsonObject = new JSONObject(convertStreamToString(executeGetRequest(url)));
			
			JSONArray tweetsArray = jsonObject.getJSONArray("results");
			
			for(int i = 0; i < tweetsArray.length(); i++) {
				tweets.add(new Tweet(tweetsArray.getJSONObject(i), this));
			}
		} catch (JSONException e) {
			Log.e("JSON parse error", e.getMessage());
			return tweets;
		}
		
		return tweets;
	}
	
	
	public Bitmap getUserImage(String url) {
		return BitmapFactory.decodeStream(executeGetRequest(url));
	}
	
	
	private static InputStream executeGetRequest(String url) {
	    HttpClient httpclient = new DefaultHttpClient();

	    // Prepare a request object
	    HttpGet httpget = new HttpGet(url); 

	    // Execute the request
	    HttpResponse response;
	    try {
	        response = httpclient.execute(httpget);
	        // Examine the response status
	        Log.i("Praeda",response.getStatusLine().toString());

	        // Get hold of the response entity
	        HttpEntity entity = response.getEntity();
	        // If the response does not enclose an entity, there is no need
	        // to worry about connection release

	        if (entity != null) {

	            // A Simple JSON Response Read
	            return entity.getContent();
	        }
	    } catch (Exception e) {
	    	//e.printStackTrace();
	    	Log.e("Connection Error", e.toString());
	    }
	    
	    return null;
	}

	    private static String convertStreamToString(InputStream is) {
	    /*
	     * To convert the InputStream to String we use the BufferedReader.readLine()
	     * method. We iterate until the BufferedReader return null which means
	     * there's no more data to read. Each line will appended to a StringBuilder
	     * and returned as String.
	     */
	    BufferedReader reader = new BufferedReader(new InputStreamReader(is));
	    StringBuilder sb = new StringBuilder();

	    String line = null;
	    try {
	        while ((line = reader.readLine()) != null) {
	            sb.append(line + "\n");
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            is.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	    return sb.toString();
	}

}
