package com.kfd.activityfour;



import com.kfd.activityfour.R;
import com.kfd.common.Cache;
import com.kfd.common.LogUtils;
import com.kfd.ui.MyAnimations;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.StaticLayout;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class TranslucentActivity extends Activity {
	
	private boolean areButtonsShowing=false;
	private RelativeLayout composerButtonsWrapper;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		// Make us non-modal, so that others can receive touch events.
	    getWindow().setFlags(LayoutParams.FLAG_NOT_TOUCH_MODAL, LayoutParams.FLAG_NOT_TOUCH_MODAL);

	    // ...but notify us that it happened.
	    getWindow().setFlags(LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH, LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH);

		
		setContentView(R.layout.float_activity);
		
		/*WindowManager m = getWindowManager();
		Display d = m.getDefaultDisplay();  //为获取屏幕宽、高
		LayoutParams p = getWindow().getAttributes();  //获取对话框当前的参数值
		p.height = (int) (d.getHeight() * 0.7);   //高度设置为屏幕的1.0
		p.width = (int) (d.getWidth());    //宽度设置为屏幕的0.8
		p.alpha = 1.0f;      //设置本身透明度
		p.dimAmount = 0.0f;      //设置黑暗度
		
		getWindow().setAttributes(p);
*/		
		//getWindow().setGravity(Gravity.RIGHT);
		getWindow().setGravity(Gravity.BOTTOM);
		 composerButtonsWrapper = (RelativeLayout) findViewById(R.id.comm);
		 Display display = getWindowManager().getDefaultDisplay(); 
		 Log.i("view" , "height:" +display.getHeight()); 
		 Log.i("view" , "width:" +display.getWidth());
		// composerButtonsWrapper.setLayoutParams(
		//		 new RelativeLayout.LayoutParams(display.getWidth(), display.getHeight()-210));
		 MyAnimations.startAnimationsIn(composerButtonsWrapper, 300);
		//areButtonsShowing = !areButtonsShowing;
		 
		 ImageView imageView1 = (ImageView) findViewById(R.id.imageView1);
		 imageView1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Cache.put("fromR", "yes");
				startActivity(new Intent(TranslucentActivity.this,TradeCenterActivity.class));
			}
		});
		 
		 ImageView imageView2 = (ImageView) findViewById(R.id.imageView2);
		 imageView2.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					startActivity(new Intent(TranslucentActivity.this,FuturesCenterActivity.class));
				}
			});
		
	}
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();	
		finish();
	}
	
	@Override
	  public boolean onTouchEvent(MotionEvent event) {
	    // If we've received a touch notification that the user has touched
	    // outside the app, finish the activity.
		    if (MotionEvent.ACTION_OUTSIDE == event.getAction()) {
			    
					MyAnimations.startAnimationsOut(TranslucentActivity.this,composerButtonsWrapper, 300);
			     // finish();
			      return true;
			
		    }
	    // Delegate everything else to Activity.
	    return super.onTouchEvent(event);
	  }

	public void finshActivity(){
		finish();
	}
	
	@Override
    public void onAttachedToWindow() {
        // 设置本Activity在父窗口的位置
        super.onAttachedToWindow();
        View view = getWindow().getDecorView();
        WindowManager m = getWindowManager();
        Display d = m.getDefaultDisplay(); 
        WindowManager.LayoutParams lp = (WindowManager.LayoutParams) view
                .getLayoutParams();
        lp.gravity = Gravity.BOTTOM;
        lp.y = getResources().getDimensionPixelSize(
                R.dimen.playqueue_dialog_marginbottom);
        lp.width = d.getWidth(); 
        lp.height = getResources().getDimensionPixelSize(
                R.dimen.playqueue_dialog_height);
        //lp.height = (int) (d.getHeight() * 0.78);
        getWindowManager().updateViewLayout(view, lp);
    }
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			MyAnimations.startAnimationsOut(TranslucentActivity.this,composerButtonsWrapper, 300);
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
	
}
