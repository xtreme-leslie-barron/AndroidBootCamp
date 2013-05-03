package com.example.lesliebootcamp;

import org.json.JSONException;
import org.json.JSONObject;

import android.graphics.Bitmap;
import android.util.Log;

public class Tweet {

	private final static String TEXT_KEY = "text";
	private final static String CREATION_TIME_KEY = "created_at";
	private final static String USER_KEY = "from_user";
	private final static String USER_IMAGE_KEY = "profile_image_url";
	
	private String text;
	private String createTime;
	private String user;
	private Bitmap userImage;
	
	public Tweet(JSONObject jsonObject, TwitterConnector tweeter) {

		try {
			text = jsonObject.getString(TEXT_KEY);
			createTime = jsonObject.getString(CREATION_TIME_KEY).substring(0, jsonObject.getString(CREATION_TIME_KEY).length() - 5);
			user = jsonObject.getString(USER_KEY);
			userImage = tweeter.getUserImage(jsonObject.getString(USER_IMAGE_KEY));
		} catch (JSONException e) {
			Log.e("JSON Tweet error", e.getMessage());
		}
		
	}
	
	public String toString() {
		return user + ": " + text;
	}
	
	public String getText() {
		return text;
	}
	
	public String getCreateTime() {
		return createTime;
	}
	
	public String getUser() {
		return user;
	}
	
	public Bitmap getUserImage() {
		return userImage;
	}

}
