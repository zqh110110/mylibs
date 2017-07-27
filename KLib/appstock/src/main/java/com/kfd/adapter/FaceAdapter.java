package com.kfd.adapter;

import java.util.ArrayList;

import com.kfd.activityfour.R;
import com.kfd.adapter.DynamicImageAdapter.ViewHolder;
import com.kfd.bean.InvestBean;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;


public class FaceAdapter   extends  BaseAdapter {
	private Context  context;
	private ArrayList<String>  arrayList;
	private ImageLoader imageLoader;

	public FaceAdapter(Context context, ArrayList<String> arrayList,ImageLoader imageLoader) {
		super();
		this.context = context;
		this.arrayList = arrayList;
		this.imageLoader = imageLoader;
	}

	@Override
	public int getCount() {
		return arrayList.size();
	}

	@Override
	public Object getItem(int position) {
		return arrayList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		final ViewHolder holder;
		if (convertView == null) {
			convertView = View.inflate(context,
					R.layout.dynamicimageitem, null);
			holder = new ViewHolder();
			holder.photoIV = (ImageView) convertView
					.findViewById(R.id.message_board_photo_iv);

			convertView.setTag(holder);
		} else {
			
			holder = (ViewHolder) convertView.getTag();
		}
		
	WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
	int width = wm.getDefaultDisplay().getWidth();
	convertView.setLayoutParams(new AbsListView.LayoutParams((width-20)/5,(width-20)/5));
		imageLoader.displayImage(arrayList.get(position), holder.photoIV, options);
		/*imageLoader.loadImage(arrayList.get(position).getPicture(), options, new ImageLoadingListener() {
			
			@Override
			public void onLoadingStarted(String arg0, View arg1) {
				
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onLoadingFailed(String arg0, View arg1, FailReason arg2) {
				
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onLoadingComplete(String arg0, View arg1, Bitmap arg2) {
				
				// TODO Auto-generated method stub
				holder.photoIV.setBackgroundDrawable(new BitmapDrawable(arg2));
			}
			
			@Override
			public void onLoadingCancelled(String arg0, View arg1) {
				
				// TODO Auto-generated method stub
				
			}
		});*/
		//imageLoader.displayImage(arrayList.get(position).getPicture(), holder.photoIV , options);
		return convertView;
}
	public static DisplayImageOptions options = new DisplayImageOptions.Builder() 
	.cacheInMemory(false) 
	.cacheOnDisk(true) 
	.build();
 class	ViewHolder{
	 ImageView photoIV;
 }	

}
	
