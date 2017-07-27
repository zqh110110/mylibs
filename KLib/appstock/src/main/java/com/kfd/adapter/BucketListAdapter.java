package com.kfd.adapter;




import com.kfd.activityfour.BaseActivity;
import com.kfd.activityfour.R;
import com.kfd.bean.Bucket;
import com.kfd.bean.ImageManager;
import com.kfd.common.CallbackImpl;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;



/**
 * 图片列表Adapter
 * @author xiaobo.lin
 *
 */
public class BucketListAdapter extends BaseAdapter{

	private Context context;
	
	public BucketListAdapter(Context context){
		this.context = context;
	}
	
	@Override
	public int getCount() {
		Log.v("tag", "ImageManager.bucketList.size()"+ImageManager.bucketList.size());
		return ImageManager.bucketList.size();
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {		
		Bucket bucket = ImageManager.bucketList.get(position); 
		if(convertView == null)
		{
			convertView = LayoutInflater.from(context).inflate(R.layout.lmall_micro_diary_catalogue_lv_items, null);
		}		
		ImageView photo = (ImageView) convertView.findViewById(R.id.photo);
		TextView name = (TextView)convertView.findViewById(R.id.name);
		TextView count = (TextView)convertView.findViewById(R.id.count);
//		TextView path = (TextView)convertView.findViewById(R.id.path);
		if(bucket!=null)
		{
			name.setText(bucket.getName());
			count.setText("("+bucket.getImageCount()+")");
//			path.setText(bucket.getPath());		
//			for(int i=0;i<bucket.getImages().size();i++){
				if(bucket.getImages().get(0)!=null){
					
					   CallbackImpl callbackImpl = new CallbackImpl(photo);
					   Bitmap cacheImage = BaseActivity.loaders.loadDrawable(bucket.getImages().get(0).get_data(), callbackImpl);
						if (cacheImage != null) {
							photo.setImageBitmap(cacheImage);
						}
				}
//				else{
//					
//					   CallbackImpl callbackImpl = new CallbackImpl(photo);
//					   Bitmap cacheImage = Constant.loaders.loadDrawable(bucket.getImages().get(0).get_data(), callbackImpl);
//						if (cacheImage != null) {
//							photo.setImageBitmap(cacheImage);
//						}
//				}
//			}
		
		 
		}	
		return convertView;
	}

}
