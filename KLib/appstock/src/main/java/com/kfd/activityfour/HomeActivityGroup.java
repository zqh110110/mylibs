package com.kfd.activityfour;

import java.util.ArrayList;

import android.app.Activity;
import android.app.ActivityGroup;
import android.app.AlertDialog;
import android.app.LocalActivityManager;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.TabHost;



import com.kfd.common.Cache;
import com.kfd.common.LogUtils;

public class HomeActivityGroup extends ActivityGroup {
	private ArrayList<String> mIdList;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (mIdList == null)
			mIdList = new ArrayList<String>();
		
		
		ActivityManager.push(this);
		TabHost host = (TabHost) getParent().findViewById(
				android.R.id.tabhost);
		Cache.put("host", host);
	}

	/**
	 * This is called when a child activity of this one calls its finish method.
	 * This implementation calls {@link LocalActivityManager#destroyActivity} on
	 * the child activity and starts the previous activity. If the last child
	 * activity just called finish(),this activity (the parent), calls finish to
	 * finish the entire group.
	 */
	@Override
	public void finishFromChild(Activity child) {
		LocalActivityManager manager = getLocalActivityManager();
		int index = mIdList.size() - 1;
		if (index < 1) {
			// finish();
			return;
		}
		if (manager!=null) {
			
			manager.destroyActivity(mIdList.get(index), true);
		}
		
		mIdList.remove(index);
		index--;
		String lastId = mIdList.get(index);
		if (manager==null || manager.getActivity(lastId)==null ) {
			LogUtils.v("ss", "=============================");
			System.exit(0);
			return;
		}
		Intent lastIntent = manager.getActivity(lastId).getIntent();
		if (lastIntent!=null) {
			lastIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
			Window newWindow = manager.startActivity(lastId, lastIntent);
			if (newWindow!=null) {
				
				setContentView(newWindow.getDecorView());
			}
		}
		
	}

	/**
	 * Starts an Activity as a child Activity to this.
	 * 
	 * @param Id
	 *            Unique identifier of the activity to be started.
	 * @param intent
	 *            The Intent describing the activity to be started.
	 * @throws android.content.ActivityNotFoundException.
	 */
	public void startChildActivity(String Id, Intent intent) {
		
		if (intent!=null) {
			
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			Window window = getLocalActivityManager().startActivity(Id, intent);
			if (window != null) {
				View tmp = window.getDecorView();
				mIdList.add(Id);
				setContentView(tmp);
			}
		}
		
		//for (String tmp1 : mIdList) {
		//}
		System.out.println("-------------------------");
	}

	

	/**
	 * The primary purpose is to prevent systems before
	 * android.os.Build.VERSION_CODES.ECLAIR from calling their default
	 * KeyEvent.KEYCODE_BACK during onKeyDown.
	 */
	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
		
			return true;
		}
		return super.onKeyUp(keyCode, event);
	}

	/**
	 * Overrides the default implementation for KeyEvent.KEYCODE_BACK so that
	 * all systems call onBackPressed().
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			int length = mIdList.size();
			if (mIdList.size() == 1 || mIdList.get(length - 1).equals("MarketCenterActivity")
					|| mIdList.get(length - 1).equals("FuturesMarketActivity")) {
				return false;
			}
			onBackPressed();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	public void goBack() {
		if (mIdList.size() != 1) {
			onBackPressed();
		}
	}

	/**
	 * If a Child Activity handles KeyEvent.KEYCODE_BACK. Simply override and
	 * add this method.
	 */
	@Override
	public void onBackPressed() {
		int length = mIdList.size();
		if (length > 1) {
			Activity current = getLocalActivityManager().getActivity(
					mIdList.get(length - 1));
			if (current!=null) {
				
				current.finish();
			}
		}
		for (String tmp : mIdList) {
		}
		System.out.println("-------------------------");
	}

}
