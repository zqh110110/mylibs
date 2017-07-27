/*
 * Copyright (C) 2011 The Android Open Source Project
 * Copyright (C) 2011 Jake Wharton
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.kfd.adapter;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

import com.kfd.activityfour.R;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * This widget implements the dynamic action bar tab behavior that can change
 * across different configurations or circumstances.
 */
public class TabPageIndicator extends HorizontalScrollView implements PageIndicator {
    /** Title text used when no title is provided by the adapter. */
    private static final CharSequence EMPTY_TITLE = "";
    
    private int mCurrentPage ;
    private float mPositionOffset ;
    private int mScrollState ;
    private SlideLineIndicator mSlideLineIndicator ;
    
    /**
     * tab textview 
     */
    private int mTextColor; 
    private int mTabSelectedColor ;
    private int mTabUnSelectedColor ;
    private float mTextSize ;
    private float mPaddingLeft ;
    private float mPaddingRight ;
    private float mPaddingTop ;
    private float mPaddingBottom ;
    private float mTabSelectedHeight ;
    private float mTabUnSelectedHeight ;
    

    /**
     * Interface for a callback when the selected tab has been reselected.
     */
    public interface OnTabReselectedListener {
        /**
         * Callback when the selected tab has been reselected.
         *
         * @param position Position of the current center item.
         */
        void onTabReselected(int position);
    }

    private Runnable mTabSelector;

    private final OnClickListener mTabClickListener = new OnClickListener() {
        public void onClick(View view) {
            TabView tabView = (TabView)view;
            final int oldSelected = mViewPager.getCurrentItem();
            final int newSelected = tabView.getIndex();
            mViewPager.setCurrentItem(newSelected);
            if (oldSelected == newSelected && mTabReselectedListener != null) {
                mTabReselectedListener.onTabReselected(newSelected);
            }
        }
    };

    private final LinearLayout mTabLayout;
    private final LinearLayout mTabAndIndicator ;

    private ViewPager mViewPager;
    private ViewPager.OnPageChangeListener mListener;

    private int mMaxTabWidth;
    private int mSelectedTabIndex;

    private OnTabReselectedListener mTabReselectedListener;

    public TabPageIndicator(Context context) {
        this(context, null);
    }

    public TabPageIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        
        final Resources res = getResources();
//        final int textColor = res.getres(R.color.default_tab_indicator_text_color);
        final int tabSelectedColor = res.getColor(R.color.default_tab_indicator_selected_color);
        final int tabUnSelectedColor = res.getColor(R.color.default_tab_indicator_unselected_color);
        final float textSize = res.getDimension(R.dimen.default_tab_indicator_textsize);
        final float paddingLeft = res.getDimension(R.dimen.default_tab_indicator_paddingleft);
        final float paddingRight = res.getDimension(R.dimen.default_tab_indicator_paddingright);
        final float paddingTop = res.getDimension(R.dimen.default_tab_indicator_paddingtop);
        final float paddingBottom = res.getDimension(R.dimen.default_tab_indicator_paddingbottom);
        final float tabSelectedHeight = res.getDimension(R.dimen.default_tab_indicator_selected_height);
        final float tabUnSelectedHeight = res.getDimension(R.dimen.default_tab_indicator_unselected_height);
        
        
        TypedArray a  = context.obtainStyledAttributes(attrs, R.styleable.TabPageIndicator);
        mTextColor = a.getResourceId(R.styleable.TabPageIndicator_textColor,R.drawable.selector_tabtextcolor);
        mTabSelectedColor =  a.getColor(R.styleable.TabPageIndicator_selectedColor, tabSelectedColor);
        mTabUnSelectedColor = a.getColor(R.styleable.TabPageIndicator_unselectedColor, tabUnSelectedColor);
        mTextSize =  a.getDimension(R.styleable.TabPageIndicator_textSize, textSize);
        mPaddingLeft = a.getDimension(R.styleable.TabPageIndicator_paddingLeft, paddingLeft);
        mPaddingRight = a.getDimension(R.styleable.TabPageIndicator_paddingRight, paddingRight);
        mPaddingTop = a.getDimension(R.styleable.TabPageIndicator_paddingTop, paddingTop);
        mPaddingBottom = a.getDimension(R.styleable.TabPageIndicator_paddingBottom, paddingBottom);
        mTabSelectedHeight = a.getDimension(R.styleable.TabPageIndicator_selectedHeight, tabSelectedHeight);
        mTabUnSelectedHeight = a.getDimension(R.styleable.TabPageIndicator_unSelectedHeight, tabUnSelectedHeight);
        
        setHorizontalScrollBarEnabled(false);

        mTabAndIndicator = new LinearLayout(context);
        mTabAndIndicator.setOrientation(LinearLayout.VERTICAL);
        mTabLayout = new LinearLayout(context);
        mTabAndIndicator.addView(mTabLayout, new LinearLayout.LayoutParams(WRAP_CONTENT, 0, 1));
        mSlideLineIndicator = new SlideLineIndicator(context);
        mTabAndIndicator.addView(mSlideLineIndicator , new LinearLayout.LayoutParams(MATCH_PARENT, (int)mTabSelectedHeight, 0));
        addView(mTabAndIndicator, new ViewGroup.LayoutParams(WRAP_CONTENT, MATCH_PARENT));
        
        a.recycle() ;
    }

    public void setOnTabReselectedListener(OnTabReselectedListener listener) {
        mTabReselectedListener = listener;
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        final int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        final boolean lockedExpanded = widthMode == MeasureSpec.EXACTLY;
        setFillViewport(lockedExpanded);

        final int childCount = mTabLayout.getChildCount();
        if (childCount > 1 && (widthMode == MeasureSpec.EXACTLY || widthMode == MeasureSpec.AT_MOST)) {
            if (childCount > 2) {
                mMaxTabWidth = (int)(MeasureSpec.getSize(widthMeasureSpec) / childCount);
                Log.i("mMaxTabWidth",mMaxTabWidth +"" );
            } else {
                mMaxTabWidth = MeasureSpec.getSize(widthMeasureSpec) / 2;
            }
        } else {
            mMaxTabWidth = -1;
        }

        final int oldWidth = getMeasuredWidth();
        Log.i("oldWidth",oldWidth +"" );
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        final int newWidth = getMeasuredWidth();
        Log.i("newWidth",newWidth +"" );

        if (lockedExpanded && oldWidth != newWidth) {
            // Recenter the tab display if we're at a new (scrollable) size.
            setCurrentItem(mSelectedTabIndex);
        }
    }

    private void animateToTab(final int position) {
        final View tabView = mTabLayout.getChildAt(position);
        if (mTabSelector != null) {
            removeCallbacks(mTabSelector);
        }
        mTabSelector = new Runnable() {
            public void run() {
                final int scrollPos = tabView.getLeft() - (getWidth() - tabView.getWidth()) / 2;
                Log.i("scrollPos = ", scrollPos+"");
                smoothScrollTo(scrollPos, 0);
                mTabSelector = null;
            }
        };
        post(mTabSelector);
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (mTabSelector != null) {
            // Re-post the selector we saved
            post(mTabSelector);
        }
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (mTabSelector != null) {
            removeCallbacks(mTabSelector);
        }
    }

    private void addTab(int index, CharSequence text) {
        final TabView tabView = new TabView(getContext());
        tabView.mIndex = index;
        tabView.setFocusable(true);
        tabView.setOnClickListener(mTabClickListener);
        tabView.setText(text);
        mTabLayout.addView(tabView, new LinearLayout.LayoutParams(0, MATCH_PARENT, 1));
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    	mScrollState = state ;
        if (mListener != null) {
            mListener.onPageScrollStateChanged(state);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    	mCurrentPage = position ;
    	mPositionOffset = positionOffset ;
        if (mListener != null) {
            mListener.onPageScrolled(position, positionOffset, positionOffsetPixels);
        }
        mSlideLineIndicator.invalidate();
    }

    @Override
    public void onPageSelected(int position) {
    	if(mScrollState == ViewPager.SCROLL_STATE_IDLE){
    		mCurrentPage = position;
            mPositionOffset = 0;
    	}
    	setCurrentItem(position);
        if (mListener != null) {
            mListener.onPageSelected(position);
        }
        
    }

    @Override
    public void setViewPager(ViewPager view) {
        if (mViewPager == view) {
            return;
        }
        if (mViewPager != null) {
            mViewPager.setOnPageChangeListener(null);
        }
        final PagerAdapter adapter = view.getAdapter();
        if (adapter == null) {
            throw new IllegalStateException("ViewPager does not have adapter instance.");
        }
        mViewPager = view;
        view.setOnPageChangeListener(this);
        notifyDataSetChanged();
    }

    public void notifyDataSetChanged() {
        mTabLayout.removeAllViews();
        PagerAdapter adapter = mViewPager.getAdapter();
        final int count = adapter.getCount();
        for (int i = 0; i < count; i++) {
            CharSequence title = adapter.getPageTitle(i);
            if (title == null) {
                title = EMPTY_TITLE;
            }
            addTab(i, title);
        }
        if (mSelectedTabIndex > count) {
            mSelectedTabIndex = count - 1;
        }
        setCurrentItem(mSelectedTabIndex);
        requestLayout();
    }

    @Override
    public void setViewPager(ViewPager view, int initialPosition) {
        setViewPager(view);
        setCurrentItem(initialPosition);
    }

    @Override
    public void setCurrentItem(int item) {
        if (mViewPager == null) {
           return ;
        }
        mSelectedTabIndex = item;
        mViewPager.setCurrentItem(item);

        final int tabCount = mTabLayout.getChildCount();
        for (int i = 0; i < tabCount; i++) {
            final View child = mTabLayout.getChildAt(i);
            final boolean isSelected = (i == item);
            child.setSelected(isSelected);
            if (isSelected) {
                animateToTab(item);
            }
        }
    }

    @Override
    public void setOnPageChangeListener(OnPageChangeListener listener) {
        mListener = listener;
    }

    private class TabView extends TextView {
    	
		private int mIndex;

        public TabView(Context context) {
            super(context);
            ColorStateList csl=(ColorStateList)getResources().getColorStateList(mTextColor); 
            this.setPaintFlags(Paint.ANTI_ALIAS_FLAG);
            this.setTextColor(csl);
            this.setGravity(Gravity.CENTER);
            this.setPadding((int)mPaddingLeft, (int)mPaddingTop, (int)mPaddingRight, (int)mPaddingBottom);
            this.setTextSize(mTextSize);
        }

        
        public int getIndex() {
            return mIndex;
        }
        
        @Override
        public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        		super.onMeasure(MeasureSpec.makeMeasureSpec(mMaxTabWidth, MeasureSpec.EXACTLY),
        				heightMeasureSpec);
        }
    }
        
    
    private class SlideLineIndicator extends View {
    	
    	 private Paint mTabSelectDividerPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
         private Paint mTabUnSelectDividerPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
         
		public SlideLineIndicator(Context context) {
			super(context);
			mTabSelectDividerPaint.setColor(mTabSelectedColor);
			mTabUnSelectDividerPaint.setColor(mTabUnSelectedColor);
		}
		
		@Override
		protected void onDraw(Canvas canvas) {
			super.onDraw(canvas);
			int dy = Math.abs((int)mTabSelectedHeight - (int)mTabUnSelectedHeight );
			canvas.drawRect(0, dy , getWidth(),(int)mTabSelectedHeight, mTabUnSelectDividerPaint);
			
			//scroll 
			float left = mMaxTabWidth * ( mCurrentPage + mPositionOffset ) ;
			float right = left + mMaxTabWidth ;
			canvas.drawRect(left, 0, right, (int)mTabSelectedHeight, mTabSelectDividerPaint);
		}
    }
}
