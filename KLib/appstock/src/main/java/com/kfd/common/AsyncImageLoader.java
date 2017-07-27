package com.kfd.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


import com.kfd.api.MD5;
import com.kfd.api.Tools;

import android.app.ActivityManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.StatFs;
import android.preference.PreferenceManager;
import android.support.v4.util.LruCache;
import android.util.Log;




public class AsyncImageLoader {
	// private static final long mTimeDiff = 7 * 24 * 60 * 60 * 1000;
	private static final long mTimeDiff = 60 * 60 * 1000;
	private static final int FREE_SD_SPACE_NEEDED_TO_CACHE = 10;
	private ThreadPoolExecutor executor;
	private BlockingQueue queue;
	private  Context mContext;
	private static TCLruCache imageCache;

	private class TCLruCache extends LruCache<String, Bitmap> {

		public TCLruCache(int maxSize) {
			super(maxSize);
		}

		@Override
		public int sizeOf(String key, Bitmap value) {
			return value.getRowBytes() * value.getHeight();
		}

		@Override
		protected void entryRemoved(boolean evicted, String key,
				Bitmap oldValue, Bitmap newValue) {
		}
	}

	private void putBitmap(String url, Bitmap bitmap) {
		imageCache.put(url, bitmap);
	}

	private Bitmap getBitmap(String url) {
		return imageCache.get(url);
	}

	private ArrayList<String> tag_al;

	// private ArrayList<Future<?>> future_al;
	public void cleanQueen() {
		if (queue != null) {
			queue.clear();
		}
		if (tag_al != null) {
			tag_al.clear();
		}
	}

	// public static void removeAndReleaseDrawable(String path){
	// SoftReference<Bitmap> softReference = imageCache.get(path);
	// if(softReference!=null){
	// Bitmap drawable = softReference.get();
	// if(drawable!=null && !drawable.isRecycled()){
	// drawable.recycle();
	// }
	// imageCache.remove(path);
	// }
	// }
	//
	public AsyncImageLoader(Context context) {
		mContext = context;
		ActivityManager am = (ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);
		int memoryClass = am.getMemoryClass() * 1024 * 1024;
		imageCache = new TCLruCache(memoryClass / 6);
		queue = new LinkedBlockingQueue();
		tag_al = new ArrayList<String>();
		// future_al = new ArrayList<Future<?>>();
		executor = new ThreadPoolExecutor(10, 50, 180, TimeUnit.SECONDS, queue);
		// 清理文件缓存
		new Thread(new Runnable() {
			@Override
			public void run() {
				removeCache(getDirectory());
			}
		}).start();
	}

	public Boolean containTag(String tag) {
		return tag_al.contains(tag);
	}

	/**
	 * 计算存储目录下的文件大小，
	 * 当文件总大小大于规定的CACHE_SIZE或者sdcard剩余空间小于FREE_SD_SPACE_NEEDED_TO_CACHE的规定
	 * 那么删除40%最近没有被使用的文件
	 * 
	 * @param dirPath
	 * @param filename
	 */
	private static final int CACHE_SIZE = 50;

	private boolean removeCache(String dirPath) {
		File dir = new File(dirPath);
		File[] files = dir.listFiles();
		if (files == null) {
			return true;
		}
		ArrayList<MyFile> array = new ArrayList<MyFile>();
		for (int i = 0; i < files.length; i++) {
			MyFile myFile = new MyFile(files[i], files[i].lastModified());
			array.add(myFile);
		}
		if (!android.os.Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED)) {
			return false;
		}
		int dirSize = 0;
		for (int i = 0; i < files.length; i++) {
			dirSize += files[i].length();
		}
		if (dirSize > CACHE_SIZE * MB
				|| FREE_SD_SPACE_NEEDED_TO_CACHE > freeSpaceOnSd()) {
			int removeFactor = (int) ((0.4 * files.length) + 1);
			System.setProperty("java.util.Arrays.useLegacyMergeSort", "true");
			Collections.sort(array, new FileLastModifSort());
			Log.i("ImageFileCache", "清理缓存文件");
			if (array != null && array.size() > 0) {
				for (int i = 0; i < removeFactor; i++) {
					MyFile myFile = array.get(i);
					File file = myFile.getFile();
					file.delete();
				}
			}
		}
		if (freeSpaceOnSd() <= CACHE_SIZE) {
			return false;
		}
		return true;
	}

	/**
	 * 计算sdcard上的剩余空间
	 * 
	 * @return
	 */
	private int MB = 1024 * 1024;

	private int freeSpaceOnSd() {
		StatFs stat = new StatFs(Environment.getExternalStorageDirectory()
				.getPath());
		double sdFreeMB = ((double) stat.getAvailableBlocks() * (double) stat
				.getBlockSize()) / MB;
		return (int) sdFreeMB;
	}
	public static String pic_path = "/kfd/pic/";
	/** 获得缓存目录 **/
	private String getDirectory() {
		String dir = getSDPath() + pic_path;
		String substr = dir.substring(0, 4);
		if (substr.equals("/mnt")) {
			dir = dir.replace("/mnt", "");
		}
		return dir;
	}

	/**** 取SD卡路径不带/ ****/
	public String getSDPath() {
		File sdDir = null;
		boolean sdCardExist = Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED); // 判断sd卡是否存在
		if (sdCardExist) {
			sdDir = Environment.getExternalStorageDirectory();// 获取跟目录
		}
		if (sdDir != null) {
			return sdDir.toString();
		} else {
			return "";
		}
	}

	public class MyFile {
		File file;
		long lastModify;

		public MyFile(File file, long lastModify) {
			this.file = file;
			this.lastModify = lastModify;

		}

		public File getFile() {
			return file;
		}

		public void setFile(File file) {
			this.file = file;
		}

		public long getLastModify() {
			return lastModify;
		}

		public void setName(long lastModify) {
			this.lastModify = lastModify;
		}
	}

	/**
	 * TODO 根据文件的最后修改时间进行排序 *
	 */
	private class FileLastModifSort implements Comparator {
		@Override
		public int compare(Object arg0, Object arg1) {
			MyFile myFile1 = (MyFile) arg0;
			MyFile myFile2 = (MyFile) arg1;
			if (myFile1.getLastModify() > myFile2.getLastModify()) {
				return 1;
			} else if (myFile1.getLastModify() == myFile2.getLastModify()) {
				return 0;
			} else {
				return -1;
			}
		}
	}

	public Bitmap loadDrawable(final String imageUrl, final String tag,
			final ImageCallback11 imageCallback, final Boolean isTopic) {
		try {
			String tempFileURL;
			final String fileName = MD5.md5(imageUrl);
			if (imageUrl.startsWith("http://")) {
				if (imageUrl.contains("app.qlogo.cn")) {
					if (imageUrl.endsWith("/100")) {
						tempFileURL = imageUrl;
					} else {
						tempFileURL = imageUrl + "/100";
					}
				} else {
					tempFileURL = imageUrl;
				}
			} else {
				if (imageUrl.equals("/100")) {
					tempFileURL = "http://app.qlogo.cn/100";
				} else {
					tempFileURL = Define.host + imageUrl;
				}
			}
			final String download_url = tempFileURL;
			Bitmap bitmap = getBitmap(download_url);
			if (bitmap != null) {
				FileUtils fileUtils = new FileUtils();
				final String fullName = FileUtils.getDataDirPATH()
						+ pic_path
						+ MD5.md5(download_url);
				new Thread(new Runnable() {
					@Override
					public void run() {
						updateFileTime(fullName);
					}
				}).start();
				return bitmap;
			}

			// 查看本地是否有图片
			FileUtils fileUtils = new FileUtils();
			final String fullName = FileUtils.getDataDirPATH()
					+ pic_path
					+ MD5.md5(download_url);
			File file = new File(fullName);
			if (file.exists()) {
				Bitmap d = null;
				try {
					d = loadImageFromFile(fileName, tag, isTopic);
				} catch (OutOfMemoryError e) {
					System.gc();
				}
				if (d != null) {
					putBitmap(download_url, d);
					if (d != null) {
						new Thread(new Runnable() {
							@Override
							public void run() {
								updateFileTime(fullName);
							}
						}).start();
						return d;
					}
				}
			}

			final Handler handler = new Handler() {
				@Override
				public void handleMessage(Message message) {
					imageCallback.imageLoaded((Bitmap) message.obj, tag,
							download_url);
				}
			};
			// 用线程池来做下载图片的任务
			/* Future<?> future = */
			executor.submit(new Runnable() {
				@Override
				public void run() {
					try {
						tag_al.add(tag);
						Bitmap drawable = null;
						try {
							drawable = loadImageFromUrl(download_url, fileName,
									tag, isTopic);
						} catch (OutOfMemoryError e) {
							System.gc();
							drawable = loadImageFromUrl(download_url, fileName,
									tag, isTopic);
						}
						if (drawable != null) {
							putBitmap(download_url, drawable);
						}
						Message message = handler.obtainMessage(0, drawable);
						handler.sendMessage(message);
					} finally {
						tag_al.remove(tag);
					}
				}
			});
		} catch (OutOfMemoryError e) {
			System.gc();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		// future_al.add(future);
		return null;
	}

	public Bitmap loadDrawableFromAssets(final String imageName) {
		Bitmap bitmap = getBitmap(imageName);
		if (bitmap != null) {
			return bitmap;
		}

		Bitmap d = null;
		try {
			d = getImageFromAssetsFile(imageName);
		} catch (OutOfMemoryError e) {
			System.gc();
		}
		if (d != null) {
			putBitmap(imageName, d);
			if (d != null) {
				return d;
			}
		}
		return null;
	}

	/**
	 * 从Assets中读取图片
	 */
	private Bitmap getImageFromAssetsFile(String fileName) {
		Bitmap image = null;
		AssetManager am = mContext.getResources().getAssets();
		try {
			InputStream is = am.open(fileName);
			image = BitmapFactory.decodeStream(is);
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (OutOfMemoryError e) {
			System.gc();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return image;
	}

	/**
	 * 修改文件的最后修改时间 这里需要考虑,是否将使用的图片日期改为当前日期
	 * 
	 * @param path
	 */
	public void updateFileTime(String path) {
		File file = new File(path);
		long newModifiedTime = System.currentTimeMillis();
		file.setLastModified(newModifiedTime);
	}

	public  Bitmap loadImageFromUrl(String url, String fileName,
			String tag, Boolean isTopic) {
		SharedPreferences sp = PreferenceManager
				.getDefaultSharedPreferences(mContext);
		Boolean isHalfSizePic = sp.getBoolean("isHalfSizePic", true);
		String fullName = "";
		HttpDownloader hd = new HttpDownloader();
		FileUtils fileUtils = new FileUtils();
		fullName = FileUtils.getDataDirPATH() + pic_path + fileName;
		File file = new File(fullName);
		if (!file.exists()) {
			hd.downFile(url, pic_path, fileName, tag);
		}
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		Bitmap d = BitmapFactory.decodeFile(fullName, options);
		int width = options.outWidth;
		int height = options.outHeight;
		options.inPreferredConfig = Bitmap.Config.RGB_565;
		options.inPurgeable = true;
		options.inInputShareable = true;
		options.inJustDecodeBounds = false;
		FileInputStream is;
		if (isHalfSizePic && isTopic) {
			if (isHalfSizePic) {
				if (width >= 480 || height >= 800) {
					options.inSampleSize = 2 * Tools.getFitSample(width / 2,
							height / 2, 480, 800);
				}
			} else {
				// 如果宽超过800,高超过1000，折半处理
				options.inSampleSize = Tools.getFitSample(width, height, 480,
						800);
			}

			try {
				is = new FileInputStream(new File(fullName));
				d = BitmapFactory.decodeStream(is, null, options);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (OutOfMemoryError e) {
				System.gc();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		} else {
			try {
				is = new FileInputStream(new File(fullName));
				d = BitmapFactory.decodeStream(is, null, options);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (OutOfMemoryError e) {
				System.gc();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		if (d == null) {
			file.delete();
			hd.downFile(url, pic_path, fileName, tag);
			if (isHalfSizePic && isTopic) {
				if (isHalfSizePic) {
					if (width >= 480 || height >= 800) {
						options.inSampleSize = 2 * Tools.getFitSample(
								width / 2, height / 2, 480, 800);
					}
				} else {
					// 如果宽超过1024,高超过1024，折半处理
					options.inSampleSize = Tools.getFitSample(width, height,
							480, 800);
				}
				try {
					is = new FileInputStream(new File(fullName));
					d = BitmapFactory.decodeStream(is, null, options);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (OutOfMemoryError e) {
					System.gc();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			} else {
				try {
					is = new FileInputStream(new File(fullName));
					d = BitmapFactory.decodeStream(is, null, options);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (OutOfMemoryError e) {
					System.gc();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		}
		return d;
	}

	public  Bitmap loadImageFromFile(String fileName, String tag,
			Boolean isTopic) {
		SharedPreferences sp = PreferenceManager
				.getDefaultSharedPreferences(mContext);
		Boolean isHalfSizePic = sp.getBoolean("isHalfSizePic", true);
		String fullName = "";
		HttpDownloader hd = new HttpDownloader();
		FileUtils fileUtils = new FileUtils();
		fullName = FileUtils.getDataDirPATH() + pic_path + fileName;
		File file = new File(fullName);
		if (!file.exists()) {
			return null;
		}
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		Bitmap d = BitmapFactory.decodeFile(fullName, options);
		int width = options.outWidth;
		int height = options.outHeight;
		options.inPreferredConfig = Bitmap.Config.RGB_565;
		options.inPurgeable = true;
		options.inInputShareable = true;
		options.inJustDecodeBounds = false;
		FileInputStream is;
		if (isHalfSizePic && isTopic) {
			if (isHalfSizePic) {
				if (width >= 480 || height >= 800) {
					options.inSampleSize = 2 * Tools.getFitSample(width / 2,
							height / 2, 480, 800);
				}
			} else {
				// 如果宽超过800,高超过1000，折半处理
				options.inSampleSize = Tools.getFitSample(width, height, 480,
						800);
			}

			try {
				is = new FileInputStream(new File(fullName));
				d = BitmapFactory.decodeStream(is, null, options);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (OutOfMemoryError e) {
				System.gc();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		} else {
			try {
				is = new FileInputStream(new File(fullName));
				d = BitmapFactory.decodeStream(is, null, options);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (OutOfMemoryError e) {
				System.gc();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return d;
	}

	public Bitmap loadEmojiFromUrl(String url, String fileName, String tag) {
		tag_al.add(tag);
		Bitmap d = null;
		try {
			Bitmap bitmap = getBitmap(url);
			if (bitmap != null) {
				return bitmap;
			}
			String fullName = "";
			HttpDownloader hd = new HttpDownloader();
			FileUtils fileUtils = new FileUtils();
			fullName = FileUtils.getDataDirPATH() + emoji_path
					+ fileName;
			File file = new File(fullName);
			if (!file.exists()) {
				hd.downFile(url, emoji_path, fileName, tag);
			}
			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inPreferredConfig = Bitmap.Config.RGB_565;
			options.inPurgeable = true;
			options.inInputShareable = true;
			options.inJustDecodeBounds = false;
			FileInputStream is;
			try {
				is = new FileInputStream(new File(fullName));
				d = BitmapFactory.decodeStream(is, null, options);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (OutOfMemoryError e) {
				System.gc();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			if (d == null) {
				file.delete();
				hd.downFile(url, emoji_path, fileName, tag);
				try {
					is = new FileInputStream(new File(fullName));
					d = BitmapFactory.decodeStream(is, null, options);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (OutOfMemoryError e) {
					System.gc();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		} catch (OutOfMemoryError e) {
			System.gc();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			tag_al.remove(tag);
		}

		return d;
	}
	public static String emoji_path = "/kfd/emoji/";
	public Bitmap loadEmojiFromFile(String url, String fileName, String tag) {
		Bitmap bitmap = getBitmap(url);
		if (bitmap != null) {
			return bitmap;
		}
		File file = new File(fileName);
		if (!file.exists()) {
			return null;
		}
		BitmapFactory.Options options = new BitmapFactory.Options();
		Bitmap d = null;
		options.inPreferredConfig = Bitmap.Config.RGB_565;
		options.inPurgeable = true;
		options.inInputShareable = true;
		options.inJustDecodeBounds = false;
		FileInputStream is;
		try {
			is = new FileInputStream(new File(fileName));
			d = BitmapFactory.decodeStream(is, null, options);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (OutOfMemoryError e) {
			System.gc();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return d;
	}

	public interface ImageCallback {
		public void imageLoaded(Bitmap imageDrawable, String imageUrl);
	}

	public interface ImageCallback11 {
		public void imageLoaded(Bitmap imageDrawable, String tag,
				String imageUrl);
	}
}
