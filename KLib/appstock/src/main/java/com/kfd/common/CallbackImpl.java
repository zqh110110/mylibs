package com.kfd.common;

import android.graphics.Bitmap;
import android.widget.ImageView;



public class CallbackImpl implements AsyncImageLoaders.ImageCallback{
	private ImageView imageView ;
	
	public CallbackImpl(ImageView imageView) {
		super();
		this.imageView = imageView;
	}
	
	@Override
	public void imageLoaded(Bitmap imageDrawable) {
		
		imageView.setImageBitmap(imageDrawable);
		
	}

}

