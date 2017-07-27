package com.kfd.activityfour;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.text.Spanned;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.kfd.adapter.MessageBoardPhotoAddAdapter;
import com.kfd.api.CustomerHttpClient;
import com.kfd.api.HttpRequest;
import com.kfd.api.MD5;
import com.kfd.api.Tools;
import com.kfd.bean.Images;
import com.kfd.bean.Thumbnails;
import com.kfd.common.Define;
import com.kfd.common.StringUtils;

import emoj.EmoKeybord;

public class WriteInvestActivity extends BaseActivity {
	public static List<Images> imageChose;
	Context _context;
	Button commitButton;

	private LinearLayout emoticonsCover;

	private EmoKeybord popupWindow;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// JumpLogin();
		setContentView(R.layout.writeinvestlayout);
		imm = (InputMethodManager) getSystemService(WriteInvestActivity.this.INPUT_METHOD_SERVICE);
		_context = WriteInvestActivity.this;
		imageChose = new ArrayList<Images>();
		resolver = getContentResolver();
		initTitle("发表心得");
		initUI();
		commitButton = (Button) findViewById(R.id.refresh);
		commitButton.setVisibility(View.VISIBLE);
		commitButton.setText("发布");

		commitButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				setLLGone();
				Tools.hideInputBoard(WriteInvestActivity.this);
				// TODO Auto-generated method stub
				if (imageChose != null && imageChose.size() > 0) {
					isImagesAllSend();

				} else {
					commitApply();
				}
			}
		});

		LinearLayout parentLayout = (LinearLayout) findViewById(R.id.list_parent);

		emoticonsCover = (LinearLayout) findViewById(R.id.footer_for_emoticons);
		popupWindow = new EmoKeybord(this, parentLayout, emoticonsCover, mFaceBtn, edit_et);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
			if (popupWindow.isShowing()) {
				popupWindow.dismiss();
				setLLGone();
				return true;
			} else {
				finish();
				return super.onKeyDown(keyCode, event);
			}
		} else {
			return super.onKeyDown(keyCode, event);
		}
	}

	CharSequence[] choice = { "相机", "相册", "取消" };
	AlertDialog alertDialog;

	private void choseSelect() {
		alertDialog = new AlertDialog.Builder(WriteInvestActivity.this).setTitle("获取图片方式？").setItems(choice, new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				switch (which) {
				case 0:
					getImageFromCamera();
					break;
				case 1:
					getImageFromAlbum();
					break;
				case 2:
					alertDialog.dismiss();
				default:
					break;
				}
			}
		}).create();
		alertDialog.show();

	}

	EditText edit_et;
	ImageView photo_add_iv;
	MessageBoardPhotoAddAdapter messageBoardPhotoAddAdapter;
	public static Button photoNumBtn;
	GridView message_board_photo_gv;
	ImageView mFaceBtn;

	private void initUI() {

		mFaceBtn = (ImageView) findViewById(R.id.expression_add_iv);
		/*
		 * mFaceBtn.setOnClickListener(new OnClickListener() {
		 * 
		 * @Override public void onClick(View v) { if (popupWindow.isShowing())
		 * { popupWindow.dismiss(); } else {
		 * 
		 * } } });
		 */

		edit_et = (EditText) findViewById(R.id.edit_et);

		photo_add_iv = (ImageView) findViewById(R.id.photo_add_iv);
		photoNumBtn = (Button) findViewById(R.id.photo_add_num_btn);
		photo_add_iv.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				setLLGone();
				choseSelect();

			}
		});
		message_board_photo_gv = (GridView) findViewById(R.id.message_board_photo_gv);
		messageBoardPhotoAddAdapter = new MessageBoardPhotoAddAdapter(context, WriteInvestActivity.this, imageChose);
		message_board_photo_gv.setAdapter(messageBoardPhotoAddAdapter);
		message_board_photo_gv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				setLLGone();
				if ((position + 1) > imageChose.size()) {
					choseSelect();
				} else {
					// 查看大图
				}
			}
		});
	}

	private void setLLGone() {
		imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
	}

	private InputMethodManager imm;
	String SAVED_IMAGE_DIR_PATH = Environment.getExternalStorageDirectory() + "/kfd/";
	String capturePath;
	int REQUEST_CODE_CAPTURE_CAMEIA = 101;

	protected void getImageFromAlbum() {
		Intent intent = new Intent(Intent.ACTION_PICK);
		intent.setType("image/*");// 相片类型
		startActivityForResult(intent, REQUEST_CODE_PICK_IMAGE);
	}

	protected void getImageFromCamera() {
		String state = Environment.getExternalStorageState();
		if (state.equals(Environment.MEDIA_MOUNTED)) {
			Intent getImageByCamera = new Intent("android.media.action.IMAGE_CAPTURE");
			String out_file_path = SAVED_IMAGE_DIR_PATH;
			File dir = new File(out_file_path);
			if (!dir.exists()) {
				dir.mkdirs();
			}
			capturePath = SAVED_IMAGE_DIR_PATH + System.currentTimeMillis() + ".jpg";
			getImageByCamera.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(capturePath)));
			getImageByCamera.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
			startActivityForResult(getImageByCamera, REQUEST_CODE_CAPTURE_CAMEIA);
		} else {
			Toast.makeText(getApplicationContext(), "请确认已经插入SD卡", Toast.LENGTH_LONG).show();
		}
	}

	int REQUEST_CODE_PICK_IMAGE = 102;
	ContentResolver resolver;

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == REQUEST_CODE_PICK_IMAGE) {
			try {
				Uri originalUri = data.getData(); // 获得图片的uri
				Bitmap bm = MediaStore.Images.Media.getBitmap(resolver, originalUri); // 显得到bitmap图片
				// 这里开始的第二部分，获取图片的路径：
				String[] proj = { MediaStore.Images.Media.DATA };
				// 好像是android多媒体数据库的封装接口，具体的看Android文档
				Cursor cursor = managedQuery(originalUri, proj, null, null, null);
				// 按我个人理解 这个是获得用户选择的图片的索引值
				int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
				// 将光标移至开头 ，这个很重要，不小心很容易引起越界
				cursor.moveToFirst();
				// 最后根据索引值获取图片路径
				String path = cursor.getString(column_index);

				Images image = new Images();
				image.set_data(path);
				Thumbnails thumbnails = new Thumbnails();
				image.setThumbnails(thumbnails);
				image.getThumbnails().set_data(path);
				imageChose.add(image);
				messageBoardPhotoAddAdapter = new MessageBoardPhotoAddAdapter(context, WriteInvestActivity.this, imageChose);
				message_board_photo_gv.setAdapter(messageBoardPhotoAddAdapter);
				messageBoardPhotoAddAdapter.notifyDataSetChanged();
				message_board_photo_gv.invalidate();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else if (requestCode == REQUEST_CODE_CAPTURE_CAMEIA) {
			Images image = new Images();
			image.set_data(capturePath);
			Thumbnails thumbnails = new Thumbnails();
			image.setThumbnails(thumbnails);
			image.getThumbnails().set_data(capturePath);
			imageChose.add(image);
			messageBoardPhotoAddAdapter = new MessageBoardPhotoAddAdapter(context, WriteInvestActivity.this, imageChose);
			message_board_photo_gv.setAdapter(messageBoardPhotoAddAdapter);
			messageBoardPhotoAddAdapter.notifyDataSetChanged();
			message_board_photo_gv.invalidate();

		}
	}

	boolean isScale;

	/***
	 * 检查图片是否全部发送，未发送的继续发送
	 */
	private boolean isImagesAllSend() {
		allimgsend = true;
		if (imageChose != null && imageChose.size() > 0) {
			showDialog("请稍候,压缩图片...");
			executorService.execute(new Runnable() {

				@Override
				public void run() {

					// 压缩图片
					for (int i = imageChose.size(); i > 0; i--) {
						int pos = i - 1;
						Images img = imageChose.get(pos);
						if (img != null) {
							if (TextUtils.isEmpty(img.getDescription())) {
								img.setDescription(img.get_data() + "_" + pos);
							}
							if (TextUtils.isEmpty(img.getUrl())) {
								allimgsend = false;
							}
							if (!isScale) {
								String _img = img.get_data();
								if (!TextUtils.isEmpty(_img)) {
									Bitmap bitMap = getMyImage(_img);
									if (bitMap != null) {
										bitMap = null;
										scaleImage(img);
									}
								}
							}
						}
					}
					isScale = true;
					postHandler.sendEmptyMessage(0);

				}
			});
		}
		return allimgsend;
	}

	private boolean allimgsend;
	private Handler postHandler = new Handler() {
		public void handleMessage(Message msg) {
			if (!allimgsend) {
				for (int i = imageChose.size(); i > 0; i--) {
					int pos = i - 1;
					Images img = imageChose.get(pos);
					if (img != null) {
						if (TextUtils.isEmpty(img.getDescription())) {
							img.setDescription(img.get_data() + "_" + pos);
						}
						if (TextUtils.isEmpty(img.getUrl())) {
							String _img = img.get_data();
							if (!TextUtils.isEmpty(_img)) {
								Bitmap bitMap = getMyImage(_img);
								if (bitMap != null) {
									bitMap = null;
									allimgsend = false;
									postPicture(_context, img);
								}
							}
						}
					}
				}
			}
		};
	};
	Bitmap mBitmap;

	private Bitmap getMyImage(String url) {
		try {

			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inJustDecodeBounds = true;
			mBitmap = BitmapFactory.decodeFile(url, options);
			int width = options.outWidth;
			int height = options.outHeight;
			options.inPreferredConfig = Bitmap.Config.RGB_565;
			options.inPurgeable = true;
			options.inInputShareable = true;
			options.inJustDecodeBounds = false;
			FileInputStream is;
			options.inSampleSize = Tools.getFitSample(width, height, 480, 800);
			try {
				is = new FileInputStream(new File(url));
				mBitmap = BitmapFactory.decodeStream(is, null, options);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		} catch (OutOfMemoryError e) {
			System.gc();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return mBitmap;
	}

	/***
	 * 压缩图片
	 * 
	 * @param step
	 */
	private void scaleImage(final Images scaleimg) {
		if (scaleimg == null) {
			return;
		}
		int totalwidth = 0;
		int totalheight = 0;
		List<Images> _images = new ArrayList<Images>();
		_images.add(scaleimg);
		List<Bitmap> _bitmaps = new ArrayList<Bitmap>();
		if (_images != null && _images.size() > 0) {
			for (int i = 0; i < _images.size(); i++) {
				Images img = _images.get(i);
				if (img != null) {
					String fpath = img.get_data();
					Bitmap _bitmap = Tools.getSmallBitmap(fpath, 320, 0);
					if (_bitmap != null) {
						if (_bitmap.getWidth() > totalwidth) {
							totalwidth = _bitmap.getWidth();
						}
						totalheight += _bitmap.getHeight();
						_bitmaps.add(_bitmap);
					}
				}
			}
		}
		Bitmap result = Bitmap.createBitmap(totalwidth, totalheight, Config.ARGB_8888);
		int currentHeight = 0;
		for (int j = 0; j < _bitmaps.size(); j++) {
			Bitmap _bitmap = _bitmaps.get(j);
			if (_bitmap != null) {
				Canvas canvas = new Canvas(result);
				canvas.drawBitmap(_bitmap, 0, currentHeight, null);
				currentHeight += _bitmap.getHeight();
			}
		}

		String resultPath = SAVED_IMAGE_DIR_PATH + Tools.getDate(new Date().getTime(), "yyyyMMddHHmmss") + ".png";
		File resultFile = new File(resultPath);
		try {
			FileOutputStream out = new FileOutputStream(resultFile);
			result.compress(Bitmap.CompressFormat.PNG, 90, out);
			out.flush();
			out.close();
			scaleimg.set_data(resultPath);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private List<String> ImgsHasUpload = new ArrayList<String>();// 已上传的图片列表

	/***
	 * 上传图片
	 * 
	 * @param context
	 * @param myPath
	 */
	private void postPicture(final Context context, final Images imgToPost) {
		if (imgToPost == null) {
			return;
		}
		final String myPath = imgToPost.get_data();
		if (TextUtils.isEmpty(myPath)) {
			return;
		}

		if (!Tools.isNetworkAvailable(this)) {
			showToast("暂无网络");
			return;
		}

		if (TextUtils.isEmpty(imgToPost.getDescription())) {
			return;
		}
		if (ImgsHasUpload.contains(imgToPost.getDescription())) {
			return;
		}

		showDialog("请稍候...");
		executorService.execute(new Runnable() {

			@Override
			public void run() {

				String uriAPI = Define.host + "/api-quan-topic/upload";
				DefaultHttpClient httpclient = CustomerHttpClient.getHttpClient();
				HttpPost httpRequest = new HttpPost(uriAPI);
				httpclient.getParams().setIntParameter(CoreConnectionPNames.SO_TIMEOUT, 60000);
				// 超时设置
				httpclient.getParams().setIntParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 60000);
				try {
					MultipartEntity entity = new MultipartEntity();
					// entity.addPart("type", new StringBody("topic"));
					entity.addPart("os", new StringBody("android"));

					String versionName = Tools.getAppVersionName(_context);
					if (versionName != null) {
						entity.addPart("version", new StringBody(versionName));
					}
					if (myPath != null && myPath.length() > 0) {
						entity.addPart("picture", new FileBody(new File(myPath)));

					}
					Hashtable t = new Hashtable();

					// t.put("type", "topic");
					t.put("os", "android");
					t.put("version", versionName);
					Map.Entry[] set = Tools.getSortedHashtableByKey(t);
					String md5 = "";
					for (int i = 0; i < set.length; i++) {
						md5 = md5 + set[i].getKey().toString() + "=" + set[i].getValue().toString() + "&";

					}
					entity.addPart("sign", new StringBody(MD5.md5(md5 + "key=" + HttpRequest.mKey)));
					httpRequest.setEntity(entity);
					httpclient.setCookieStore(Tools.getCookie(getApplicationContext()));
					HttpResponse httpResponse = httpclient.execute(httpRequest);
					Tools.cookieStore = httpclient.getCookieStore();
					Tools.saveCookie(_context, httpclient.getCookieStore());
					String strResult = EntityUtils.toString(httpResponse.getEntity());

					JSONObject jsonObject = null;
					try {
						jsonObject = new JSONObject(strResult);
					} catch (JSONException e) {
						showToast("网络繁忙");
						return;
					}
					String ret = jsonObject.getString("ret");
					final String msg = jsonObject.getString("msg");
					if ("0".equalsIgnoreCase(ret)) {
						try {
							JSONObject jsonObject2 = jsonObject.optJSONObject("data");
							String id = jsonObject2.optString("id");
							if (!StringUtils.isEmpty(id)) {
								uploadArrayList.add(id);
								handler.sendEmptyMessage(UI_EVENT_SEND_PHOTO_SUCCESS);
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
						return;
					} else {
						runOnUiThread(new Runnable() {
							@Override
							public void run() {
								Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
								dismissDialog();
							}
						});
					}
				} catch (final ClientProtocolException e) {
					e.printStackTrace();
					runOnUiThread(new Runnable() {
						@Override
						public void run() {

							Toast.makeText(_context, "请求失败", Toast.LENGTH_LONG).show();
							dismissDialog();
						}

					});
				} catch (final ConnectTimeoutException e) {
					e.printStackTrace();
					runOnUiThread(new Runnable() {
						@Override
						public void run() {
							Toast.makeText(_context, "请求超时", Toast.LENGTH_LONG).show();
							dismissDialog();
						}
					});
				} catch (final Exception e) {
					e.printStackTrace();
					runOnUiThread(new Runnable() {
						@Override
						public void run() {

							Toast.makeText(context, "发送图片失败！", Toast.LENGTH_LONG).show();
							dismissDialog();

						}
					});
				}

			}
		});
	}

	private ArrayList<String> uploadArrayList = new ArrayList<String>();
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(android.os.Message msg) {
			dismissDialog();

			switch (msg.what) {

			case UI_EVENT_SEND_PHOTO_SUCCESS: // 发送图片成功
				commitButton.setEnabled(true);
				Tools.hideInputBoard(WriteInvestActivity.this);

				if (uploadArrayList.size() == imageChose.size()) {
					commitApply();
				}
				break;

			default:
				break;
			}
		}
	};

	private void commitApply() {
		
		// final String content =
		// ParseEmojiMsgUtil.convertToMsg(edit_et.getText(),
		// WriteInvestActivity.this);
		 String content = edit_et.getText().toString().trim();
		 
		 if (StringUtils.isEmpty(content)) {
				showToast("请填写心得");
				return;
			}
		
		if(content.contains("￼"))
		{
			@SuppressWarnings("unchecked")
			ArrayList<String> list = (ArrayList<String>) edit_et.getTag();
			
			String newContent = "";
			
			int j = 0;
			for(int i =0; i < content.length(); i++)
			{
				
				String a = content.toString().substring(i, i + 1);
				if(a.equals("￼"))
				{
					newContent = newContent + list.get(j);
					j ++;
				}
				else
				{
					newContent = newContent + a;
				}
			}
			
			content = newContent;
			
		}
		
		final String finalContent = String.valueOf(content);
		
		
		showDialog("请稍候...");
		executorService.execute(new Runnable() {

			@Override
			public void run() {

				try {
					LinkedHashMap<String, String> hashMap = new LinkedHashMap<String, String>();
					StringBuilder stringBuilder = new StringBuilder();
					if (uploadArrayList != null && uploadArrayList.size() > 0) {
						for (int i = 0; i < uploadArrayList.size(); i++) {
							if (i == 0) {
								stringBuilder.append(uploadArrayList.get(0));
							} else {
								stringBuilder.append("," + uploadArrayList.get(0));

							}
						}
						hashMap.put("picid", stringBuilder.toString());
					}
					hashMap.put("content", finalContent);
					String result = HttpRequest.sendPostRequestWithMd5(WriteInvestActivity.this, Define.host + "/api-quan-topic/add", hashMap);
					if (!StringUtils.isEmpty(result)) {
						Message message = new Message();
						message.what = 1;
						message.obj = result;
						commitHandler.sendMessage(message);
					} else {
						Message message = new Message();
						message.what = 0;
						commitHandler.sendMessage(message);
					}
				} catch (Exception e) {
					Message message = new Message();
					message.what = 0;
					commitHandler.sendMessage(message);
					e.printStackTrace();
				}

			}
		});
	}

	private Handler commitHandler = new Handler() {
		public void handleMessage(Message msg) {
			dismissDialog();
			switch (msg.what) {
			case 0:
				showToast("发表失败，请重试!");
				break;
			case 1:
				String result = (String) msg.obj;
				try {
					JSONObject jsonObject = new JSONObject(result);
					String ret = jsonObject.optString("ret");
					if (!TextUtils.isEmpty(ret)) {
						if (ret.equals("0")) {
							// 成功
							showToast("发表成功!");
							finish();
						} else {
							// 失败
							String message = jsonObject.optString("msg");
							if (!StringUtils.isEmpty(message)) {
								showToast(message);
							}
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
			default:
				break;
			}
		};
	};
	public static final int UI_EVENT_BASE = 0x100;
	/**
	 * 发送图片成功
	 */
	public static final int UI_EVENT_SEND_PHOTO_SUCCESS = UI_EVENT_BASE + 126;
}