package com.kfd.activityfour;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.ref.WeakReference;
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

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import com.kfd.api.CustomerHttpClient;
import com.kfd.api.HttpRequest;
import com.kfd.api.MD5;
import com.kfd.api.Tools;
import com.kfd.bean.Images;
import com.kfd.bean.Thumbnails;
import com.kfd.bean.UserBean;
import com.kfd.common.Define;
import com.kfd.common.StringUtils;
import com.kfd.db.SharePersistent;
import com.kfd.ui.RoundImageView;

import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Bitmap.Config;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.provider.ContactsContract.CommonDataKinds.Relation;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.ShareActionProvider;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 
 我的 2015-6-7
 */
public class MineActivity extends BaseActivity implements OnClickListener {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// JumpLogin();
		setContentView(R.layout.mine);

		resolver = getContentResolver();
		initUI();
		initTitle("我的");
		// backButton.setVisibility(View.GONE);
		loadData();
	}

	RelativeLayout changepwLayout;
	TextView nametextView, nicknametextView, sextextview, quyutextview, telTextView, banktextView, emailtextView;
	RoundImageView roundImageView;

	private void initUI() {
		findViewById(R.id.setnicklayout).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(), SetNameAndPasswordActivity.class);
				intent.putExtra("type", "setnickname");
				if (!StringUtils.isEmpty(userBean.getNickname())) {
					intent.putExtra("nickname", userBean.getNickname());
				}
				startActivity(intent);
			}
		});
		findViewById(R.id.banklayout).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(), WriteBankInfoActivity.class);
				startActivity(intent);
			}
		});
		changepwLayout = (RelativeLayout) findViewById(R.id.changepwlayout);
		changepwLayout.setOnClickListener(this);
		nametextView = (TextView) findViewById(R.id.nametextView);
		nicknametextView = (TextView) findViewById(R.id.nicknametextView);
		sextextview = (TextView) findViewById(R.id.sextextview);
		quyutextview = (TextView) findViewById(R.id.quyutextview);
		telTextView = (TextView) findViewById(R.id.teltextView2);
		banktextView = (TextView) findViewById(R.id.banktextView2);
		emailtextView = (TextView) findViewById(R.id.emailtextView);
		roundImageView = (RoundImageView) findViewById(R.id.roundImageView1);
		roundImageView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// choseSelect();
				Intent intent = new Intent(MineActivity.this, SelectFaceActivity.class);
				startActivityForResult(intent, 201);
			}
		});
		findViewById(R.id.regionlayout).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(MineActivity.this, MallSelectProv.class);
				startActivityForResult(intent, SELECT_PROV_FOR_RESULT);
			}
		});
		findViewById(R.id.bindemail).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				startActivity(new Intent(getApplicationContext(), BindEmailActivity.class));
			}
		});

	}

	CharSequence[] choice = { "相机", "相册", "取消" };
	AlertDialog alertDialog;

	private void choseSelect() {
		alertDialog = new AlertDialog.Builder(MineActivity.this).setTitle("获取图片方式？").setItems(choice, new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
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
	String facepath;

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
				facepath = cursor.getString(column_index);
				Bitmap bitmap = convertToBitmap(facepath, 50, 50);
				roundImageView.setImageBitmap(bitmap);
				scaleImage(facepath);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else if (requestCode == REQUEST_CODE_CAPTURE_CAMEIA) {

			facepath = capturePath;
			Bitmap bitmap = convertToBitmap(facepath, 50, 50);
			roundImageView.setImageBitmap(bitmap);

			scaleImage(facepath);

		}

		if (resultCode == RESULT_OK) {
			switch (requestCode) {
			case SELECT_PROV_FOR_RESULT:
				city = data.getStringExtra("city");
				province = data.getStringExtra("prov");
				district = data.getStringExtra("couny");
				quyutextview.setText(province + "   " + city + "   " + district);
				doUpdateUserInfo();
				break;
			}
		}

		if (requestCode == 201) {
			faceurl = data.getStringExtra("faceurl");
			doUpdateUserInfo();
		}
	}

	// 计算图片的缩放值
	public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;

		if (height > reqHeight || width > reqWidth) {
			final int heightRatio = Math.round((float) height / (float) reqHeight);
			final int widthRatio = Math.round((float) width / (float) reqWidth);
			inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
		}
		return inSampleSize;
	}

	// 根据路径获得图片并压缩，返回bitmap用于显示
	public static Bitmap getSmallBitmap(String filePath) {
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(filePath, options);

		// Calculate inSampleSize
		options.inSampleSize = calculateInSampleSize(options, 400, 400);

		// Decode bitmap with inSampleSize set
		options.inJustDecodeBounds = false;

		return BitmapFactory.decodeFile(filePath, options);
	}

	String district;
	public final static int SELECT_PROV_FOR_RESULT = 0x100 + 910;

	/***
	 * 压缩图片
	 * 
	 * @param step
	 */
	private void scaleImage(final String filepath) {

		Bitmap result = getSmallBitmap(filepath);

		String resultPath = SAVED_IMAGE_DIR_PATH + Tools.getDate(new Date().getTime(), "yyyyMMddHHmmss") + ".png";
		File resultFile = new File(resultPath);
		try {
			FileOutputStream out = new FileOutputStream(resultFile);
			result.compress(Bitmap.CompressFormat.PNG, 90, out);
			out.flush();
			out.close();
			postPicture(getApplicationContext(), resultPath);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/***
	 * 上传图片
	 * 
	 * @param context
	 * @param myPath
	 */
	private void postPicture(final Context context, final String filepath) {

		if (TextUtils.isEmpty(filepath)) {
			return;
		}

		if (!Tools.isNetworkAvailable(this)) {
			showToast("暂无网络");
			return;
		}

		showDialog("请稍候...");
		executorService.execute(new Runnable() {

			@Override
			public void run() {

				String uriAPI = Define.host + "/api-user-main/face";
				DefaultHttpClient httpclient = CustomerHttpClient.getHttpClient();
				HttpPost httpRequest = new HttpPost(uriAPI);
				httpclient.getParams().setIntParameter(CoreConnectionPNames.SO_TIMEOUT, 60000);
				// 超时设置
				httpclient.getParams().setIntParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 60000);
				try {
					MultipartEntity entity = new MultipartEntity();
					// entity.addPart("type", new StringBody("face"));
					entity.addPart("os", new StringBody("android"));

					String versionName = Tools.getAppVersionName(MineActivity.this);
					if (versionName != null) {
						entity.addPart("version", new StringBody(versionName));
					}
					if (filepath != null && filepath.length() > 0) {
						entity.addPart("picture", new FileBody(new File(filepath)));

					}
					Hashtable t = new Hashtable();

					// t.put("type", "face");
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
					Tools.saveCookie(MineActivity.this, httpclient.getCookieStore());
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
							faceurl = jsonObject2.optString("face");
							if (!StringUtils.isEmpty(faceurl)) {

								uploadfacehandler.sendEmptyMessage(UI_EVENT_SEND_PHOTO_SUCCESS);
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

							Toast.makeText(MineActivity.this, "请求失败", Toast.LENGTH_LONG).show();
							dismissDialog();
						}

					});
				} catch (final ConnectTimeoutException e) {
					e.printStackTrace();
					runOnUiThread(new Runnable() {
						@Override
						public void run() {
							Toast.makeText(MineActivity.this, "请求超时", Toast.LENGTH_LONG).show();
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

	/*
	 * sex 否 POST Int 性别 1-男 0-女
	 */
	private void updateUserInfo(final String nickname, final String province, final String city, final String face, final String sex) {
		showDialog("更新资料中");
		executorService.execute(new Runnable() {

			@Override
			public void run() {
				LinkedHashMap<String, String> hashMap = new LinkedHashMap<String, String>();
				if (!StringUtils.isEmpty(nickname)) {
					hashMap.put("nickname", nickname);
				}
				if (!StringUtils.isEmpty(province)) {
					hashMap.put("province", province);
				}
				if (!StringUtils.isEmpty(city)) {
					hashMap.put("city", city);
				}
				if (!StringUtils.isEmpty(face)) {
					hashMap.put("face", face);
				}
				if (!StringUtils.isEmpty(sex)) {
					hashMap.put("sex", sex);
				}

				String result = HttpRequest.sendPostRequestWithMd5(MineActivity.this, Define.host + "/api-user-main/update", hashMap);

				if (!StringUtils.isEmpty(result)) {
					Message message = new Message();
					message.obj = result;
					message.what = 1;
					updateUserInfoHandler.sendMessage(message);
				} else {
					updateUserInfoHandler.sendEmptyMessage(0);
				}
			}
		});
	}

	private Handler updateUserInfoHandler = new Handler() {
		public void handleMessage(Message msg) {
			dismissDialog();
			switch (msg.what) {
			case 1:
				String result = (String) msg.obj;
				try {
					JSONObject jsonObject = new JSONObject(result);
					String ret = jsonObject.optString("ret");
					if (ret.equals("0")) {
						showToast("更新资料成功");
					} else {
						String messgsg = jsonObject.optString("msg");
						showToast(messgsg);
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

	String faceurl;
	private Handler uploadfacehandler = new Handler() {
		@Override
		public void handleMessage(android.os.Message msg) {
			dismissDialog();

			switch (msg.what) {

			case UI_EVENT_SEND_PHOTO_SUCCESS: // 发送图片成功
				Tools.hideInputBoard(MineActivity.this);

				doUpdateUserInfo();
				break;

			default:
				break;
			}
		}
	};
	String province, city;

	private void doUpdateUserInfo() {
		String nickname = nicknametextView.getText().toString().trim();
		updateUserInfo(nickname, province, city, faceurl, userBean.getSex());
	}

	public Bitmap convertToBitmap(String path, int w, int h) {
		BitmapFactory.Options opts = new BitmapFactory.Options();
		// 设置为ture只获取图片大小
		opts.inJustDecodeBounds = true;
		opts.inPreferredConfig = Bitmap.Config.ARGB_8888;
		// 返回为空
		BitmapFactory.decodeFile(path, opts);
		int width = opts.outWidth;
		int height = opts.outHeight;
		float scaleWidth = 0.f, scaleHeight = 0.f;
		if (width > w || height > h) {
			// 缩放
			scaleWidth = ((float) width) / w;
			scaleHeight = ((float) height) / h;
		}
		opts.inJustDecodeBounds = false;
		float scale = Math.max(scaleWidth, scaleHeight);
		opts.inSampleSize = (int) scale;
		WeakReference<Bitmap> weak = new WeakReference<Bitmap>(BitmapFactory.decodeFile(path, opts));
		return Bitmap.createScaledBitmap(weak.get(), w, h, true);
	}

	private void loadData() {
		executorService.execute(new Runnable() {

			@Override
			public void run() {
				LinkedHashMap<String, String> hashMap = new LinkedHashMap<String, String>();
				String result = HttpRequest.sendGetRequestWithMd5(MineActivity.this, Define.host + "/api-user-main/my", hashMap);
				if (!StringUtils.isEmpty(result)) {
					Message message = new Message();
					message.what = 1;
					message.obj = result;
					handler.sendMessage(message);
				} else {
					handler.sendEmptyMessage(0);
				}
			}
		});
	}

	UserBean userBean;
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:

				break;
			case 1:
				String resulString = (String) msg.obj;

				try {
					JSONObject jsonObject = new JSONObject(resulString);
					String ret = jsonObject.getString("ret");
					if (ret.equals("0")) {
						JSONObject jsonObject2 = jsonObject.getJSONObject("data");
						JSONObject jsonObject3 = jsonObject2.getJSONObject("user");
						// Gson gson = new Gson();
						// UserBean userBean =
						// gson.fromJson(jsonObject3.toString(),new
						// TypeToken<UserBean>(){}.getType());
						userBean = new UserBean();
						userBean.setUid(jsonObject3.optString("uid"));
						userBean.setBank(jsonObject3.optString("bank"));
						userBean.setBirthday(jsonObject3.optString("birthday"));
						userBean.setCity(jsonObject3.optString("city"));
						userBean.setFace(jsonObject3.optString("face"));
						userBean.setFollow_msg(jsonObject3.optString("follow_msg"));
						userBean.setIsemail(jsonObject3.optString("isemail"));
						userBean.setLike_msg(jsonObject3.optString("like_msg"));
						userBean.setLv(jsonObject3.optString("lv"));
						userBean.setMobile(jsonObject3.optString("mobile"));
						userBean.setNickname(jsonObject3.optString("nickname"));
						userBean.setProvince(jsonObject3.optString("province"));
						userBean.setRealname(jsonObject3.optString("realname"));
						userBean.setRec_msg(jsonObject3.optString("rec_msg"));
						userBean.setScores(jsonObject3.optString("scores"));
						userBean.setSex(jsonObject3.optString("sex"));
						userBean.setSignature(jsonObject3.optString("signature"));
						userBean.setSys_msg(jsonObject3.optString("sys_msg"));
						/**
						 * uid,mobile,nickname,birthday,province,city,face,
						 * signature,lv,
						 * scores,like_msg,follow_msg,rec_msg,sys_msg
						 * ,isemail,realname,bank,sex;
						 */
						imageLoader.displayImage(userBean.getFace(), roundImageView, options);
						nametextView.setText(userBean.getRealname());
						nicknametextView.setText(userBean.getNickname());
						if (userBean.getSex().equals("0")) {
							sextextview.setText("女");
						} else if (userBean.getSex().equals("1")) {
							sextextview.setText("男");
						}
						quyutextview.setText(userBean.getProvince());
						telTextView.setText(userBean.getMobile());
						banktextView.setText(userBean.getBank());
						if (userBean.getIsemail().equals("1")) {
							emailtextView.setText("已验证");
						} else {
							emailtextView.setText("未验证");
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

	@Override
	public void onClick(View v) {
		if (v == changepwLayout) {
			Intent intent = new Intent(getApplicationContext(), FindPwdActivity.class);
			intent.putExtra("type", "change");
			startActivity(intent);
		}
	}

	public static final int UI_EVENT_BASE = 0x100;
	/**
	 * 发送图片成功
	 */
	public static final int UI_EVENT_SEND_PHOTO_SUCCESS = UI_EVENT_BASE + 126;
}
