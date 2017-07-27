package com.kfd.common;



import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import com.kfd.activityfour.BaseActivity;



import android.os.Environment;
import android.util.Log;

public class FileUtils {
	private static String SDPATH;

	static int FILESIZE = 4 * 1024;

	public static String getDataDirPATH() {
		return SDPATH;
	}

	public FileUtils() {
		// 锟矫碉拷锟斤拷前锟解部锟芥储锟借备锟斤拷目录( /SDCARD )
//		String InternalMemoryPastUpdataApkPATH = Environment.getDataDirectory() + "/data/"+context.getPackageName();
//		File file = new File(InternalMemoryPastUpdataApkPATH);
//		SDPATH = InternalMemoryPastUpdataApkPATH;
		SDPATH = Environment.getExternalStorageDirectory() + "/";
	}

	/**
	 * 锟斤拷SD锟斤拷锟较达拷锟斤拷锟侥硷拷
	 * 
	 * @param fileName
	 * @return
	 * @throws IOException
	 */
	public static File createSDFile(String fileName) throws IOException {
		File file = new File(SDPATH + fileName);
		file.createNewFile();
		return file;
	}

	/**
	 * 锟斤拷SD锟斤拷锟较达拷锟斤拷目录
	 * 
	 * @param dirName
	 * @return
	 */
	public static File createSDDir(String dirName) {
		File dir = new File(SDPATH + dirName);
		dir.mkdirs();
		return dir;
	}

	/**
	 * 锟叫讹拷SD锟斤拷锟较碉拷锟侥硷拷锟斤拷锟角凤拷锟斤拷锟�
	 * 
	 * @param fileName
	 * @return
	 */
	public boolean isFileExist(String fileName) {
		File file = new File(SDPATH + fileName);
		return file.exists();
	}

	/**
	 * 锟斤拷一锟斤拷InputStream锟斤拷锟斤拷锟斤拷锟斤拷写锟诫到SD锟斤拷锟斤拷
	 * 
	 * @param path
	 * @param fileName
	 * @param input
	 * @return
	 */
	public static File write2SDFromInput(String path, String fileName,
			InputStream input, String tag) {
		File file = null;
		OutputStream output = null;
		if(input==null){
			return null;
		}
		try {
			createSDDir(path);
			file = createSDFile(path + fileName);
			output = new FileOutputStream(file);
			byte[] buffer = new byte[FILESIZE];
			// 锟斤拷取锟斤拷锟侥硷拷
			int len; 
			while ((len=input.read(buffer))!= -1 && BaseActivity.asyncImageLoader.containTag(tag)) {
				output.write(buffer,0,len);
			}
			output.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				output.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return file;
	}

	public static void Unzip(String zipFile, String targetDir) {
		int BUFFER = 4096; // 锟斤拷锟斤缓锟斤拷锟斤拷锟斤拷锟斤拷使锟斤拷4KB锟斤拷
		String strEntry; // 锟斤拷锟斤拷每锟斤拷zip锟斤拷锟斤拷目锟斤拷锟�

		try {
			BufferedOutputStream dest = null; // 锟斤拷锟斤拷锟斤拷锟斤拷锟�
			FileInputStream fis = new FileInputStream(zipFile);
			ZipInputStream zis = new ZipInputStream(
					new BufferedInputStream(fis));
			ZipEntry entry; // 每锟斤拷zip锟斤拷目锟斤拷实锟斤拷

			while ((entry = zis.getNextEntry()) != null) {

				try {
					Log.i("Unzip: ", "=" + entry);
					int count;
					byte data[] = new byte[BUFFER];
					strEntry = entry.getName();

					File entryFile = new File(targetDir + strEntry);
					File entryDir = new File(entryFile.getParent());
					if (!entryDir.exists()) {
						entryDir.mkdirs();
					}

					FileOutputStream fos = new FileOutputStream(entryFile);
					dest = new BufferedOutputStream(fos, BUFFER);
					while ((count = zis.read(data, 0, BUFFER)) != -1) {
						dest.write(data, 0, count);
					}
					dest.flush();
					dest.close();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
			zis.close();
		} catch (Exception cwj) {
			cwj.printStackTrace();
		}
	}

}
