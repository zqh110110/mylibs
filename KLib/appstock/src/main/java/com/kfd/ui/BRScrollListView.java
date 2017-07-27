package com.kfd.ui;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class BRScrollListView extends RelativeLayout {

	private Context mContext;
	/**
	 * 列表头的高和宽
	 */
	private int mTitleHeight = 30;
	private int mTitleWidth = 60;
	/**
	 * 可滚动和不可滚动列头的名称
	 */
	private String[] mTitleMovableStr = { "最新价", "涨跌额", "涨跌幅", "今开盘", "昨开盘",
			"最高价", "最低价", "总成交额(万元)", "总成交量(手)" };
	private String[] mTitleFixStr = { "名称" };

	private LinearLayout mLayoutTitleMovable;
	private LinearLayout mLayoutHeader;

	private LinearLayout mLayoutListMovable;

	private ListView listViewMovable;

	private ArrayList<View> mArrayList;

	public BRScrollListView(Context context) {
		super(context);
		mContext = context;
	}

	public BRScrollListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
		// ListView可移动区域
		mLayoutListMovable = new LinearLayout(mContext);
		mLayoutListMovable.setOrientation(LinearLayout.VERTICAL);
		LayoutParams scrollListLp = new LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.FILL_PARENT);
		this.addView(buidScrollListView(), scrollListLp);

	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

	/**
	 * 可滚动得列表头
	 * 
	 * @return
	 */
	private View buildMovableHead() {
		LinearLayout relativeLayout = new LinearLayout(mContext);
		for (int i = 0; i < mTitleMovableStr.length; i++) {
			TextView tx = new TextView(mContext);
			tx.setText(mTitleMovableStr[i]);
			tx.setGravity(Gravity.CENTER);
			tx.setBackgroundColor(Color.BLACK);
			relativeLayout.addView(tx, mTitleWidth, mTitleHeight);
		}
		mLayoutTitleMovable = relativeLayout;
		return relativeLayout;
	}

	/**
	 * 不可滚动得列表头
	 * 
	 * @return
	 */
	private View buildFixHead() {
		LinearLayout relativeLayout = new LinearLayout(mContext);
		for (int i = 0; i < mTitleFixStr.length; i++) {
			TextView tx = new TextView(mContext);
			tx.setText(mTitleFixStr[i]);
			tx.setGravity(Gravity.CENTER);
			tx.setBackgroundColor(Color.BLACK);
			relativeLayout.addView(tx, i, new LayoutParams(mTitleWidth,
					mTitleHeight));
		}
		return relativeLayout;
	}

	/**
	 * 合并列头
	 * 
	 * @return
	 */
	private View buildHeadLayout() {
		LinearLayout relativeLayout = new LinearLayout(mContext);
		relativeLayout.addView(buildFixHead());
		relativeLayout.addView(buildMovableHead());
		mLayoutHeader = relativeLayout;
		return relativeLayout;
	}

	/**
	 * ListView
	 * 
	 * @return
	 */
	private View buildMoveableListView() {
		LinearLayout relativeLayout = new LinearLayout(mContext);
		listViewMovable = new ListView(mContext);
		listViewMovable.setCacheColorHint(00000000);
		relativeLayout.addView(listViewMovable);
		return relativeLayout;

	}

	private View buidScrollListView() {
		LinearLayout relativeLayout = new LinearLayout(mContext);
		relativeLayout.setOrientation(LinearLayout.VERTICAL);
		relativeLayout.addView(buildHeadLayout());
		relativeLayout.addView(buildMoveableListView());
		return relativeLayout;
	}

	// 触摸开始时X的位置
	private float mStartX = 0;
	private float mStartY = 0;
	// X轴方向的偏移量
	private int mOffsetX = 0;

	private int fixX = 0;

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {

		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			mStartX = ev.getX();
			mStartY = ev.getY();
			Log.e("TEST", "中断按下x= " + ev.getX());
			break;
		case MotionEvent.ACTION_MOVE:
			Log.e("TEST", "中断移动x= " + ev.getX());
			int offsetX = (int) Math.abs(ev.getX() - mStartX);
			if (offsetX > 30) {
				return true;
			} else {
				return false;
			}

		case MotionEvent.ACTION_UP:
			Log.e("TEST", "中断抬起x= " + ev.getX());
			actionUP();
			break;
		default:
			break;
		}
		return super.onInterceptTouchEvent(ev);
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			Log.e("TEST", "移动按下x= " + ev.getX());
			return true;
		case MotionEvent.ACTION_MOVE:
			int offsetX = (int) Math.abs(ev.getX() - mStartX);
			if (offsetX > 30) {// 左右滑动的时候左边一行不可动
				Log.e("TEST", "移动偏移" + offsetX);
				mOffsetX = (int) (mStartX - ev.getX());
				mLayoutTitleMovable.scrollTo(mOffsetX, 0);
				for (int i = 0; i < mArrayList.size(); i++) {

					mArrayList.get(i).scrollTo(mOffsetX, 0);
				}
				Log.e("TEST", "List数量" + mArrayList.size());
				// mLayoutMovable.scrollTo(mOffsetX, 0);
			}

			// 上下滑动的时候标题栏不可以动

			break;
		case MotionEvent.ACTION_UP:
			Log.e("TEST", "移动抬起x= " + ev.getX());
			fixX = (int) ((int) ev.getX() - mStartX);
			actionUP();
			break;

		default:
			break;
		}

		return super.onTouchEvent(ev);
	}

	/**
	 * 触摸抬起
	 */
	private void actionUP() {
		if (fixX > 0) {
			mLayoutTitleMovable.scrollTo(0, 0);
			for (int i = 0; i < mArrayList.size(); i++) {
				mArrayList.get(i).scrollTo(0, 0);
			}
			// mLayoutMovable.scrollTo(0, 0);
		} else {
			if ((mLayoutTitleMovable.getWidth() + Math.abs(fixX)) > mTitleWidth
					* mTitleMovableStr.length) {
				mLayoutTitleMovable.scrollTo(
						mTitleWidth * mTitleMovableStr.length
								- mLayoutTitleMovable.getWidth(), 0);
				for (int i = 0; i < mArrayList.size(); i++) {
					mArrayList.get(i).scrollTo(
							mTitleWidth * mTitleMovableStr.length
									- mLayoutTitleMovable.getWidth(), 0);
				}
				// mLayoutMovable.scrollTo(0, 0);
			}

		}
	}

	/**
	 * 设置可变的列表头信息
	 * 
	 * @param str
	 *            列表头显示的名称
	 */
	public void setMovableHead(String[] str) {
		mTitleMovableStr = str;
	}

	/**
	 * 设置不可变的列表头信息
	 * 
	 * @param str
	 *            列表头显示的名称
	 * @param height
	 *            列表头的高度
	 * @param width
	 *            列表头的宽度
	 */
	public void setFixHead(String[] str, int height, int width) {
		mTitleHeight = height;
		mTitleWidth = width;
		mTitleFixStr = str;
	}

	public View getHeaderLayout() {
		return mLayoutHeader;
	}

	/**
	 * 设置ListView 适配器
	 * 
	 * @param adapter
	 */
	public void setScrollListViewAdapter(BaseAdapter movableAdapter) {
		// listViewFix.setAdapter(fixAdapter);
		listViewMovable.setAdapter(movableAdapter);

	}

	/**
	 * 可左右滑动View集合
	 * 
	 * @param movableView
	 */
	public void setMovabaleView(ArrayList<View> movableView) {
		mArrayList = movableView;
	}

	/**
	 * listView 点击
	 * 
	 * @param onItemClickedListener
	 */
	public void setOnItemClickedListener(OnItemClickListener onItemClickListener) {

		listViewMovable.setOnItemClickListener(onItemClickListener);
	}

	/**
	 * 列头点击事件
	 * 
	 * @param onHeaderClickedListener
	 */
	public void setOnHeaderClickedListener(
			OnHeaderClickedListener onHeaderClickedListener) {
		// onHeaderClickedListener.
		// mLayoutHeader.getChildAt(0).setOnClickListener(l);
	}

	/**
	 * 列头点击事件
	 * 
	 */
	public static interface OnHeaderClickedListener {
		public void onClick(int headerID, int direction);

	}
}
