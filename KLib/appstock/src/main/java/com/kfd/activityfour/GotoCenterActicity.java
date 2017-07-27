package com.kfd.activityfour;

import com.kfd.common.Cache;
import com.kfd.market.FuturesMarketActivity;
import com.kfd.market.MarketCenterActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.TabHost;

/**
 * 
 * @author 跳转到Center
 * 
 */
public class GotoCenterActicity extends HomeActivityGroup {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		TabHost host = (TabHost) getParent().findViewById(
				android.R.id.tabhost);
		
		ActivityManager.push(this);
		Cache.put("host", host);
		if (Cache.getCache("futures")!=null) {
			startChildActivity("FuturesMarketActivity", new Intent(GotoCenterActicity.this,
					FuturesMarketActivity.class));
			Cache.put("futures", null);
		}/*else if (Cache.getCache("gold")!=null) {
			startChildActivity("GoldCenterActivity", new Intent(GotoCenterActicity.this,
					GoldCenterActivity.class));
			
			Cache.put("gold", null);
		}*/ else {
			startChildActivity("MarketCenterActivity", new Intent(GotoCenterActicity.this,
					MarketCenterActivity.class));

		}
	}

	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {
		// TODO Auto-generated method stub
		return super.dispatchKeyEvent(event);
	}
}

