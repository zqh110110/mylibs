package com.kfd.bean;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import com.kfd.common.Logcat;
import com.kfd.common.StringUtils;


import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;
import android.provider.MediaStore.Images.ImageColumns;
import android.util.Log;



/**
 * 图片的加载类
 * @author xiaobo.lin
 *
 */
public class ImageManager {
	
	private static final String TAG = ImageManager.class.getSimpleName();
	public static List<Bucket> bucketList = new ArrayList<Bucket>();
	
	//获取所有文件夹信息
	public static List<Bucket> loadAllBucketList(Context context){
		List<Bucket> tempBucketList = new ArrayList<Bucket>();	
		try{
		String[] projection = new String[]{ImageColumns._ID,ImageColumns.DATA,ImageColumns.SIZE,ImageColumns.DISPLAY_NAME
				,ImageColumns.SIZE,ImageColumns.MIME_TYPE,ImageColumns.TITLE,
				ImageColumns.DATE_ADDED,ImageColumns.DATE_MODIFIED,ImageColumns.DESCRIPTION,
				ImageColumns.PICASA_ID,ImageColumns.IS_PRIVATE,
				ImageColumns.LATITUDE,ImageColumns.LONGITUDE,ImageColumns.DATE_TAKEN,
				ImageColumns.ORIENTATION,ImageColumns.MINI_THUMB_MAGIC,ImageColumns.BUCKET_ID,
				ImageColumns.BUCKET_DISPLAY_NAME};
		
		Cursor cursor = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
				projection, null, null, null);

		if (cursor != null) {
			int idColumn = cursor.getColumnIndex(ImageColumns._ID);
			int dateColumn = cursor.getColumnIndex(ImageColumns.DATA);
			int sizeColumn = cursor.getColumnIndex(ImageColumns.SIZE);
			int displayNameColumn = cursor.getColumnIndex(ImageColumns.DISPLAY_NAME);
			int mineTypeColumn = cursor.getColumnIndex(ImageColumns.MIME_TYPE);
			int titleColumn = cursor.getColumnIndex(ImageColumns.TITLE);
			int dateAddedColumn = cursor.getColumnIndex(ImageColumns.DATE_ADDED);
			int dateModifiedColumn = cursor.getColumnIndex(ImageColumns.DATE_MODIFIED);
			int descriptionColumn = cursor.getColumnIndex(ImageColumns.DESCRIPTION);
			int picasaIdColumn = cursor.getColumnIndex(ImageColumns.PICASA_ID);
			int isprivateColumn = cursor.getColumnIndex(ImageColumns.IS_PRIVATE);
			int latitudeColumn = cursor.getColumnIndex(ImageColumns.LATITUDE);
			int longitudeColumn = cursor.getColumnIndex(ImageColumns.LONGITUDE);
			int datetakenColumn = cursor.getColumnIndex(ImageColumns.DATE_TAKEN);
			int orientationColumn = cursor.getColumnIndex(ImageColumns.ORIENTATION);
			int miniColumn = cursor.getColumnIndex(ImageColumns.MINI_THUMB_MAGIC);
			int bucketIdColumn = cursor.getColumnIndex(ImageColumns.BUCKET_ID);
			int bucketDisplayNameColumn = cursor.getColumnIndex(ImageColumns.BUCKET_DISPLAY_NAME);		
			List<Images> imageList = new ArrayList<Images>();
			while (cursor.moveToNext()) {
				Images image = new Images();				
				image.set_id(cursor.getInt(idColumn));
				image.set_data(cursor.getString(dateColumn));
				image.set_size(cursor.getInt(sizeColumn));
				image.set_display_name(cursor.getString(displayNameColumn));
				image.setMime_type(cursor.getString(mineTypeColumn));
				image.setTitle(cursor.getString(titleColumn));
				image.setDate_added(cursor.getLong(dateAddedColumn));
				image.setDate_modified(cursor.getLong(dateModifiedColumn));
				image.setDescription(cursor.getString(descriptionColumn));
				image.setPicasa_id(cursor.getString(picasaIdColumn));
				image.setIsprivate(cursor.getInt(isprivateColumn));
				image.setLatitude(cursor.getFloat(latitudeColumn));
				image.setLongitude(cursor.getFloat(longitudeColumn));
				image.setDatetaken(cursor.getLong(datetakenColumn));
				image.setOrientation(cursor.getInt(orientationColumn));
				image.setMini_thumb_magic(cursor.getInt(miniColumn));
				image.setBucket_id(cursor.getString(bucketIdColumn));
				image.setBucket_display_name(cursor.getString(bucketDisplayNameColumn));
//				Logcat.v(image.get_data()+image.get_display_name()+image.get_id()+image.get_size()+image.getBucket_display_name()
//						+image.getBucket_id()+image.getDate_added()+image.getDate_modified()+image.getDatetaken()+image.getDescription()+image.getIsprivate()+image.getLatitude()+image.getLongitude()+image.getMime_type()+image.getTitle()+++++++++++++);
			
//				Thumbnails thumbnails = new Thumbnails();		
//				thumbnails.set_id(cursor.getInt(idColumn));
//				thumbnails.set_data(cursor.getString(dateColumn));
//				thumbnails.setImage_id(cursor.getInt(imageIdColumn));
//				thumbnails.setKind(cursor.getInt(kindColumn));
//				thumbnails.setWidth(cursor.getInt(widthColumn));
//				thumbnails.setHeight(cursor.getInt(heightColumn));
//				tempThumbnailsSet.put(String.valueOf(cursor.getInt(imageIdColumn)),
//						thumbnails);
				
				if(!StringUtils.isEmpty(image.get_data()))
				imageList.add(image);
//				Logcat.v("loadAllBucketList"+image.toString());
			}
			//4.0以上的版本会自动关闭 (4.0--14;; 4.0.3--15)  
			//if (Build.VERSION.SDK_INT < 14) {
				cursor.close();
			//}
			
			Hashtable<String, Thumbnails> tempThumbnailsSet = loadAllThumbnailsSet(context);	
			Hashtable<String, Bucket> tempBucketSet = new Hashtable<String, Bucket>();
			
			for (int i = 0; i < imageList.size(); i++)
			{
				Images tempImage = imageList.get(i);	
				Thumbnails tempThumbnails = (Thumbnails) tempThumbnailsSet.get(String.valueOf(tempImage.get_id()));
				if(tempThumbnails!=null)
				{
					tempImage.setThumbnails(tempThumbnails);
				}
//					tempImage.set_display_name(tempImage.get_data().substring(tempImage.get_data().lastIndexOf("/")+1));
				Bucket tempBucke = (Bucket)tempBucketSet.get(StringUtils.getBucketPath(tempImage.get_data(), tempImage.get_display_name()));
				if(tempBucke!=null)
				{
					tempBucke.addImages(tempImage);
				}
				else
				{
					tempBucke = new Bucket();
					tempBucke.setName(tempImage.getBucket_display_name());
					Logcat.v("tempImage.get_data()"+tempImage.get_data());
					Logcat.v("tempImage.get_display_name()"+tempImage.get_display_name());
					if("".equals(StringUtils.getBucketPath(tempImage.get_data(), tempImage.get_display_name()))){
						Logcat.v("空");
					}
				
					tempBucke.setPath(StringUtils.getBucketPath(tempImage.get_data(), tempImage.get_display_name()));
					tempBucke.addImages(tempImage);
//						Log.i("loadAllBucketList", StringUtils.getBucketPath(tempImage.get_data(), tempImage.get_display_name()));
//						Log.i("loadAllBucketList", tempBucke.toString());
					tempBucketSet.put(StringUtils.getBucketPath(tempImage.get_data(), tempImage.get_display_name()), tempBucke);
				}		
			}
				
			tempBucketList.addAll(tempBucketSet.values());
		}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		Log.i(TAG, tempBucketList.size()+"");
//		Logcat.v("tempBucketList"+tempBucketList.toString());
		return tempBucketList;
	}
	
	//获取所有缩略图信息
	public static Hashtable<String, Thumbnails> loadAllThumbnailsSet(Context context){	
		Hashtable<String, Thumbnails> tempThumbnailsSet = new Hashtable<String, Thumbnails>();
		String[] projection = new String[] {MediaStore.Images.Thumbnails._ID,MediaStore.Images.Thumbnails.DATA,MediaStore.Images.Thumbnails.IMAGE_ID,
				MediaStore.Images.Thumbnails.KIND,MediaStore.Images.Thumbnails.WIDTH,MediaStore.Images.Thumbnails.HEIGHT};
		Cursor cursor = context.getContentResolver().query(MediaStore.Images.Thumbnails.EXTERNAL_CONTENT_URI,
				projection, null, null, null);
		
		if (cursor != null) {
			int idColumn = cursor.getColumnIndex(MediaStore.Images.Thumbnails._ID);
			int dateColumn = cursor.getColumnIndex(MediaStore.Images.Thumbnails.DATA);
			int imageIdColumn = cursor.getColumnIndex(MediaStore.Images.Thumbnails.IMAGE_ID);
			int kindColumn = cursor.getColumnIndex(MediaStore.Images.Thumbnails.KIND);
			int widthColumn = cursor.getColumnIndex(MediaStore.Images.Thumbnails.WIDTH);
			int heightColumn = cursor.getColumnIndex(MediaStore.Images.Thumbnails.HEIGHT);
			
			while (cursor.moveToNext()) {
				Thumbnails thumbnails = new Thumbnails();		
				thumbnails.set_id(cursor.getInt(idColumn));
				thumbnails.set_data(cursor.getString(dateColumn));
				thumbnails.setImage_id(cursor.getInt(imageIdColumn));
				thumbnails.setKind(cursor.getInt(kindColumn));
				thumbnails.setWidth(cursor.getInt(widthColumn));
				thumbnails.setHeight(cursor.getInt(heightColumn));
				tempThumbnailsSet.put(String.valueOf(cursor.getInt(imageIdColumn)),
						thumbnails);
				Log.i(TAG, thumbnails.toString());
//				Logcat.v("thumbnails.toString()"+thumbnails.toString());
//				Logcat.v("tempThumbnailsSet.toString()"+tempThumbnailsSet.toString());
			}
			//4.0以上的版本会自动关闭 (4.0--14;; 4.0.3--15)  
			//if (Build.VERSION.SDK_INT < 14)
			//{
				cursor.close();
			//}
		}
		return tempThumbnailsSet;
	}
	
}
