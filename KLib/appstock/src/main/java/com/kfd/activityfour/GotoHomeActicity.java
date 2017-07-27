package com.kfd.activityfour;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;

/**
 * 
 * @author 跳转到home
 * 
 */
public class GotoHomeActicity extends HomeActivityGroup {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		startChildActivity("HomeActivity", new Intent(GotoHomeActicity.this,
				HomeActivity.class));
	}

	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {
		// TODO Auto-generated method stub
		return super.dispatchKeyEvent(event);
	}
}
