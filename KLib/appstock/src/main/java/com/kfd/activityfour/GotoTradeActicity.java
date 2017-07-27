package com.kfd.activityfour;


import com.kfd.common.Cache;
import com.kfd.market.MarketCenterActivity;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.TabHost;

/**
 * 
 * @author 跳转到Trade
 * 
 */
public class GotoTradeActicity extends HomeActivityGroup {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		TabHost host = (TabHost) getParent().findViewById(
				android.R.id.tabhost);
		Cache.put("host", host);
		
		startChildActivity("HomeActivity", new Intent(GotoTradeActicity.this,
				PlaceAnOrderActivity.class)
				.putExtra("type", "type")
				.putExtra("from", "0")
				);
	}

	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {
		// TODO Auto-generated method stub
		return super.dispatchKeyEvent(event);
	}
}

