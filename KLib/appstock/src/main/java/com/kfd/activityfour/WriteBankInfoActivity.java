package com.kfd.activityfour;



import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.Date;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.Wire;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import com.kfd.activityfour.R.id;
import com.kfd.adapter.TryoutBankInfo;
import com.kfd.api.CustomerHttpClient;
import com.kfd.api.HttpRequest;
import com.kfd.api.MD5;
import com.kfd.api.Tools;
import com.kfd.common.Define;
import com.kfd.common.StringUtils;

import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

/*
 *填写银行卡信息 
 */
public class WriteBankInfoActivity extends  BaseActivity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.writebankinfo);
		resolver  = getContentResolver();
		initTitle("完善银行信息");
		initUI();
	}
	EditText   bankusernamEditText,bankaccountEditText;
	TextView choicebankEdittext,banknameEditText;

	RelativeLayout choicemainlayout,choiceoppositelayout;
	private int index;
	TextView  maintextView,oppositetextView;
	private void initUI(){
		maintextView = (TextView) findViewById(R.id.maintextView);
		oppositetextView  =  (TextView) findViewById(R.id.oppositetextView);
		choicemainlayout = (RelativeLayout) findViewById(R.id.choicemainlayout);
		choicemainlayout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				index =1;
				choseSelect();
			}
		});
		choiceoppositelayout = (RelativeLayout) findViewById(R.id.choiceoppositelayout);
		choiceoppositelayout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				index =2;
				choseSelect();
			}
		});
		bankusernamEditText=(EditText) findViewById(R.id.bankusernamEditText); 
		choicebankEdittext =  (TextView) findViewById(R.id.choicebankEdittext);
		bankaccountEditText  = (EditText) findViewById(R.id.bankaccountEditText);
		banknameEditText  = (TextView) findViewById(R.id.banknameEditText);
		findViewById(R.id.choicebanklayout).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent  intent =  new Intent(getApplicationContext(), TryoutChoiceBankCardActivity.class);
				startActivityForResult(intent, 101);
			}
		});
		findViewById(R.id.button1).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				updateUserBank();
			}
		});
		 findViewById(R.id.regionlayout).setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent intent = new Intent();
					intent.setClass(WriteBankInfoActivity.this, MallSelectProv.class);
					startActivityForResult(intent, SELECT_PROV_FOR_RESULT);
				}
			});
	}
	private void updateUserBank(){
//		bank_name 是 POST
//		 String 银行名称
//		 
//		account 是 POST
//		 String 银行账号
//		 
//		branch 是 POST
//		 String 支行名称
//		 
//		idcard_main 是 POST
//		 String 身份证正面网址
//		请用上传图片接口先传图片
//		 
//		idcard_side 是 POST
//		 String 身份证反面网址
//		请用上传图片接口先传图片
//		 
//		province 是 POST
//		 String 省份
//		 
//		city 是 POST
//		 String 城市
//		 
//		district 是 POST
//		 String 地区 
final	String	bank_name  =bankusernamEditText.getText().toString().trim();
final		String	account  =bankaccountEditText.getText().toString().trim();
final	String	branch  =choicebankEdittext.getText().toString().trim();
	if (StringUtils.isEmpty(bank_name)) {
		showToast("银行名称不能为空");
		return;
	}
	if (StringUtils.isEmpty(account)) {
		showToast("银行账号不能为空");
		return;
	}
	if (StringUtils.isEmpty(branch)) {
		showToast("支行名称不能为空");
		return;
	}
	if (StringUtils.isEmpty(mainpicurl)) {
		showToast("身份证正面未上传");
		return;
	}
	if (StringUtils.isEmpty(oppsitepicurl)) {
		showToast("身份证反面未上传");
		return;
	}
	if (StringUtils.isEmpty(city)) {
		showToast("请选择城市");
		return;
	}
		showDialog("请稍候...");
		executorService.execute(new Runnable() {
			
			@Override
			public void run() {
				LinkedHashMap<String, String>  hashMap  = new LinkedHashMap<String, String>();
				if (!StringUtils.isEmpty(bank_name)) {
					hashMap.put("bank_name", bank_name);						
				}
				if (!StringUtils.isEmpty(account)) {
					hashMap.put("account", account);	
				}
				if (!StringUtils.isEmpty(branch)) {
					hashMap.put("branch", branch);	
				}
				if (!StringUtils.isEmpty(mainpicurl)) {
					hashMap.put("idcard_main", mainpicurl);	
				}
				if (!StringUtils.isEmpty(oppsitepicurl)) {
					hashMap.put("idcard_side", oppsitepicurl);	
				}
				if (!StringUtils.isEmpty(province)) {
					hashMap.put("province", province);	
				}
				if (!StringUtils.isEmpty(city)) {
					hashMap.put("city", city);	
				}
				if (!StringUtils.isEmpty(district)) {
					hashMap.put("district", district);	
				}

				String  result  =  HttpRequest.sendPostRequestWithMd5(WriteBankInfoActivity.this, Define.host+"/api-user-main/dobank", hashMap);
			
				if (!StringUtils.isEmpty(result)) {
					Message message  = new Message();
					message.obj = result;
					message.what=1;
					updateUserInfoHandler.sendMessage(message);
				}else {
					updateUserInfoHandler.sendEmptyMessage(0);
				}
			}
		});
	}
	private Handler  updateUserInfoHandler =  new Handler(){
		public void handleMessage(Message msg) {
			dismissDialog();
			switch (msg.what) {
			case 1:
				String  result  = (String) msg.obj;
				try {
					JSONObject jsonObject = new JSONObject(result);
					String ret  = jsonObject.optString("ret");
					if (ret.equals("0")) {
						showToast("更新资料成功");
						finish();
					}else {
						String  messgsg  = jsonObject.optString("msg");
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
	CharSequence[]  choice   ={"相机","相册","取消"};
	 AlertDialog  alertDialog ;
	private void choseSelect(){
		
	  alertDialog   = new AlertDialog.Builder(WriteBankInfoActivity.this).setTitle("获取图片方式？").setItems(choice, new DialogInterface.OnClickListener() {
			
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
	
	String  SAVED_IMAGE_DIR_PATH=Environment.getExternalStorageDirectory()	+ "/kfd/";
	String  capturePath;
	int  REQUEST_CODE_CAPTURE_CAMEIA=101;
	 protected void getImageFromAlbum() {
	        Intent intent = new Intent(Intent.ACTION_PICK);
	        intent.setType("image/*");//相片类型
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
       }
       else {
           Toast.makeText(getApplicationContext(), "请确认已经插入SD卡", Toast.LENGTH_LONG).show();
       }
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
        opts.inSampleSize = (int)scale;
        WeakReference<Bitmap> weak = new WeakReference<Bitmap>(BitmapFactory.decodeFile(path, opts));
        return Bitmap.createScaledBitmap(weak.get(), w, h, true);
    }

	int  REQUEST_CODE_PICK_IMAGE=102;
	ContentResolver resolver;
	String facepath;
	String   province,city;
	 @Override
	    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	   
	        if (requestCode==101&&resultCode==102) {
				tryoutBankInfo  =  (TryoutBankInfo) data.getSerializableExtra("TryoutBankInfo");
				choicebankEdittext.setText(tryoutBankInfo.getName());
			}else {
				if (resultCode == RESULT_OK) {
					switch (requestCode) {
					case SELECT_PROV_FOR_RESULT:
						city = data.getStringExtra("city");
						province = data.getStringExtra("prov");
						district = data.getStringExtra("couny");
						banknameEditText.setText(province + "   " + city + "   " + district);
					//	doUpdateUserInfo();
						break;
					}
				}
				
				
			     if (requestCode == REQUEST_CODE_PICK_IMAGE) {           
			        	try {
			        		Uri originalUri = data.getData();        //获得图片的uri 
			        		Bitmap    bm = MediaStore.Images.Media.getBitmap(resolver, originalUri);        //显得到bitmap图片
			            //   这里开始的第二部分，获取图片的路径：
			                String[] proj = {MediaStore.Images.Media.DATA};
			                //好像是android多媒体数据库的封装接口，具体的看Android文档
			                Cursor cursor = managedQuery(originalUri, proj, null, null, null); 
			                //按我个人理解 这个是获得用户选择的图片的索引值
			                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
			                //将光标移至开头 ，这个很重要，不小心很容易引起越界
			                cursor.moveToFirst();
			                //最后根据索引值获取图片路径
			                facepath = cursor.getString(column_index);
			                Bitmap  bitmap  = convertToBitmap(facepath, 50, 50);
			                if (index==1) {
								maintextView.setVisibility(View.GONE);
								choicemainlayout.setBackgroundDrawable(new BitmapDrawable(bitmap));
							}else if (index==2) {
								oppositetextView.setVisibility(View.GONE);
								choiceoppositelayout.setBackgroundDrawable(new BitmapDrawable(bitmap));
							}
			               // roundImageView.setImageBitmap(bitmap);
			                scaleImage(facepath);
			            }catch (IOException e) {
			            	e.printStackTrace();
			            }
			        } else if (requestCode == REQUEST_CODE_CAPTURE_CAMEIA ) {           

						facepath   = capturePath;
						 Bitmap  bitmap  = convertToBitmap(facepath, 50, 50);
						 if (index==1) {
								maintextView.setVisibility(View.GONE);
								choicemainlayout.setBackgroundDrawable(new BitmapDrawable(bitmap));
							}else if (index==2) {
								oppositetextView.setVisibility(View.GONE);
								choiceoppositelayout.setBackgroundDrawable(new BitmapDrawable(bitmap));
							}
			               // roundImageView.setImageBitmap(bitmap);
			             
			                scaleImage(facepath);
			               
			               
			    }
			}
	        
	     
	      
	 }
	//计算图片的缩放值
	 public static int calculateInSampleSize(BitmapFactory.Options options,int reqWidth, int reqHeight) {
	     final int height = options.outHeight;
	     final int width = options.outWidth;
	     int inSampleSize = 1;

	     if (height > reqHeight || width > reqWidth) {
	              final int heightRatio = Math.round((float) height/ (float) reqHeight);
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
	 String  district;
	 public final static int SELECT_PROV_FOR_RESULT = 0x100 + 910;
		/***
		 * 压缩图片
		 * 
		 * @param step
		 */
		private void scaleImage(final String filepath ) {
			
		
			Bitmap result = getSmallBitmap(filepath);
			

			String resultPath =SAVED_IMAGE_DIR_PATH  + Tools.getDate(new Date().getTime(), "yyyyMMddHHmmss") + ".png";
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
		String  mainpicurl,oppsitepicurl;
		/***
		 * 上传图片
		 * 
		 * @param context
		 * @param myPath
		 */
		private void postPicture(final Context context, final String  filepath) {
			
		
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
				
					
					
					String uriAPI = Define.host + "/api-upload/image";
					DefaultHttpClient httpclient = CustomerHttpClient
							.getHttpClient();
					HttpPost httpRequest = new HttpPost(uriAPI);
					httpclient.getParams().setIntParameter(
							CoreConnectionPNames.SO_TIMEOUT, 60000);
					// 超时设置
					httpclient.getParams().setIntParameter(
							CoreConnectionPNames.CONNECTION_TIMEOUT, 60000);
					try {
						MultipartEntity entity = new MultipartEntity();
						//entity.addPart("type", new StringBody("idcard"));
						entity.addPart("os", new StringBody("android"));
						
						String versionName = Tools.getAppVersionName(WriteBankInfoActivity.this);
						if (versionName != null) {
							entity.addPart("version",
									new StringBody(versionName));
						}
						if (filepath != null && filepath.length() > 0) {
							entity.addPart("picture", new FileBody(new File(filepath)));

						}
						Hashtable t = new Hashtable();

						//t.put("type", "idcard");
						t.put("os", "android");
						t.put("version", versionName);
						Map.Entry[] set = Tools.getSortedHashtableByKey(t);
						String md5 = "";
						for (int i = 0; i < set.length; i++) {
							md5 = md5 + set[i].getKey().toString() + "="
									+ set[i].getValue().toString() + "&";

						}
						entity.addPart("sign",
								new StringBody(MD5.md5(md5 + "key=" + HttpRequest.mKey)));
						httpRequest.setEntity(entity);
						httpclient.setCookieStore(Tools
								.getCookie(getApplicationContext()));
						HttpResponse httpResponse = httpclient.execute(httpRequest);
						Tools.cookieStore = httpclient.getCookieStore();
						Tools.saveCookie(WriteBankInfoActivity.this, httpclient.getCookieStore());
						String strResult = EntityUtils.toString(httpResponse
								.getEntity());

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
								JSONObject  jsonObject2 = jsonObject.optJSONObject("data");
								String  pic = jsonObject2.optString("picture");
								if (!StringUtils.isEmpty(pic)) {
									if (index==1) {
										mainpicurl =pic;
									}else if (index==2) {
										oppsitepicurl   = pic;
									}
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
									Toast.makeText(context, msg, Toast.LENGTH_LONG)
											.show();
								    dismissDialog();
								}
							});
						}
					} catch (final ClientProtocolException e) {
						e.printStackTrace();
						runOnUiThread(new Runnable() {
							@Override
							public void run() {

								Toast.makeText(WriteBankInfoActivity.this, "请求失败", Toast.LENGTH_LONG)
										.show();
								dismissDialog();
							}

						});
					} catch (final ConnectTimeoutException e) {
						e.printStackTrace();
						runOnUiThread(new Runnable() {
							@Override
							public void run() {
								Toast.makeText(WriteBankInfoActivity.this, "请求超时", Toast.LENGTH_LONG)
										.show();
								dismissDialog();
							}
						});
					} catch (final Exception e) {
						e.printStackTrace();
						runOnUiThread(new Runnable() {
							@Override
							public void run() {

								Toast.makeText(context, "发送图片失败！",
										Toast.LENGTH_LONG).show();
								dismissDialog();

							}
						});
					}

				}
			});
		}

		private Handler uploadfacehandler = new Handler() {
			@Override
			public void handleMessage(android.os.Message msg) {
				dismissDialog();
				
				switch (msg.what) {
			
				case UI_EVENT_SEND_PHOTO_SUCCESS: // 发送图片成功
					Tools.hideInputBoard(WriteBankInfoActivity.this);
					showToast("上传成功");
					//doUpdateUserInfo();
					break;
					
			
				default:
					break;
				}
			}
		};
	TryoutBankInfo tryoutBankInfo;
	public static final int UI_EVENT_BASE = 0x100;
	/**
	 * 发送图片成功
	 */
	public static final int UI_EVENT_SEND_PHOTO_SUCCESS = UI_EVENT_BASE + 126;
	
}
