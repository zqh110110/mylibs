package com.kfd.activityfour;

import in.srain.cube.views.loadmore.LoadMoreContainer;
import in.srain.cube.views.loadmore.LoadMoreHandler;
import in.srain.cube.views.loadmore.LoadMoreListViewContainer;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.preference.PreferenceManager;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.kfd.adapter.InvalistAdapter;
import com.kfd.api.AppContext;
import com.kfd.api.HttpRequest;
import com.kfd.api.Tools;
import com.kfd.bean.InvestBean;
import com.kfd.bean.UserBean;
import com.kfd.common.Define;
import com.kfd.common.StringUtils;
import com.kfd.common.ToastUtils;
import com.kfd.ui.RoundImageView;

import emoj.EmoKeybord;

/**
 * 投资心得列表
 */
public class InvestListActivity extends BaseActivity {
	private static final int NETWORK_FAIL = 0;
	private static final int LOAD_DATA_COMPLETE = 1;
	private static final int LOAD_MORE_DATA_COMPLETE = 2;

	private Button writeButton;
	private LinearLayout replylayout;
	private EditText replyEditText;
	private Button replyButton;

	private PtrFrameLayout mPtrFrameLayout;
	private LoadMoreListViewContainer loadMoreListViewContainer;

	private ListView mListView;

	private RoundImageView roundImageView1;
	private LinearLayout personlayout;
	private TextView nicknametextView;
	private InvalistAdapter mAdapter;
	private ArrayList<InvestBean> mDatas = new ArrayList<InvestBean>();

	private int pageSize = 8;

	private int currentPage = 1;
	
	private EmoKeybord popupWindow;

	private static InternalHandler sHandler = null;

	static {
		sHandler = new InternalHandler(Looper.getMainLooper());
	}

	private static class InternalHandler extends Handler {
		InternalHandler(Looper looper) {
			super(looper);
		}

		@Override
		public void handleMessage(Message msg) {
			InvestListActivity activity = (InvestListActivity) msg.obj;
			switch (msg.what) {
			case NETWORK_FAIL:
				ToastUtils.show(activity, "请检查网络");
				activity.loadMoreListViewContainer.loadMoreFinish(false, true);
				break;

			case LOAD_DATA_COMPLETE:
				if (msg.getData() != null && msg.getData().getSerializable("list") != null) {
					@SuppressWarnings("unchecked")
					List<InvestBean> list = (List<InvestBean>) msg.getData().getSerializable("list");
					activity.mDatas.clear();
					activity.mDatas.addAll(list);
					activity.mAdapter.notifyDataSetChanged();
					activity.loadMoreListViewContainer.loadMoreFinish(false, true);
				}
				break;

			case LOAD_MORE_DATA_COMPLETE:
				if (msg.getData() != null && msg.getData().getSerializable("list") != null) {
					@SuppressWarnings("unchecked")
					List<InvestBean> list = (List<InvestBean>) msg.getData().getSerializable("list");
					activity.mDatas.addAll(list);
					activity.mAdapter.notifyDataSetChanged();
					activity.loadMoreListViewContainer.loadMoreFinish(false, true);
				}
				break;

			default:
				break;
			}
			activity.mPtrFrameLayout.refreshComplete();
		}

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.investlayout);
		initTitle("投资圈");
		Tools.hideInputBoard(InvestListActivity.this);
		replylayout = (LinearLayout) findViewById(R.id.replylayout);
		replyEditText = (EditText) findViewById(R.id.replyeditText1);
		replyButton = (Button) findViewById(R.id.replybutton1);
		writeButton = (Button) findViewById(R.id.refresh);
		writeButton.setBackgroundResource(R.drawable.header_publish_ico);
		writeButton.setVisibility(View.VISIBLE);
		writeButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (!AppContext.getInstance().isLogin()) {
					startActivity(new Intent(getApplicationContext(), LoginActivity.class));
					return;
				}
				startActivity(new Intent(getApplicationContext(), WriteInvestActivity.class));
			}
		});
		initUI();
		loadData(1);
	}

	protected void onResume() {
		super.onResume();
		if (AppContext.getInstance().isLogin()) {
			loadUserData();
		}
	};

	private void initUI() {
		roundImageView1 = (RoundImageView) findViewById(R.id.roundImageView1);
		nicknametextView = (TextView) findViewById(R.id.nicknametextView);

		// pull to refresh
		mPtrFrameLayout = (PtrFrameLayout) findViewById(R.id.load_more_list_view_ptr_frame);

		mPtrFrameLayout.setLoadingMinTime(1000);
		mPtrFrameLayout.setPtrHandler(new PtrHandler() {
			@Override
			public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {

				// here check list view, not content.
				return PtrDefaultHandler.checkContentCanBePulledDown(frame, mListView, header);
			}

			@Override
			public void onRefreshBegin(PtrFrameLayout frame) {
				currentPage = 1;
				loadData(currentPage);
			}
		});

		mListView = (ListView) findViewById(R.id.load_more_small_image_list_view);

		loadMoreListViewContainer = (LoadMoreListViewContainer) findViewById(R.id.load_more_list_view_container);
		loadMoreListViewContainer.useDefaultHeader();

		
		LinearLayout parentLayout = (LinearLayout) findViewById(R.id.list_parent);

		LinearLayout emoticonsCover = (LinearLayout) findViewById(R.id.footer_for_emoticons);
		
		ImageView view = (ImageView) findViewById(R.id.expression_add_iv);
		
		popupWindow = new EmoKeybord(this, parentLayout, emoticonsCover, view, replyEditText);
		

		// binding view and data
		mAdapter = new InvalistAdapter(mDatas, InvestListActivity.this, getLayoutInflater(), imageLoader, InvestListActivity.this, popupWindow.getEmoticons());
		mListView.setAdapter(mAdapter);
		loadMoreListViewContainer.setLoadMoreHandler(new LoadMoreHandler() {
			@Override
			public void onLoadMore(LoadMoreContainer loadMoreContainer) {
				if (!AppContext.getInstance().isLogin()) {
					loadMoreContainer.loadMoreFinish(false, true);
					Intent intent = new Intent(InvestListActivity.this, LoginActivity.class);
					InvestListActivity.this.startActivity(intent);
					return;
				}
				currentPage++;
				loadData(currentPage);
			}
		});
		
		mListView.setOnTouchListener(new OnTouchListener()
		{
			@Override
			public boolean onTouch(View v, MotionEvent event)
			{
				if (popupWindow.isShowing())
				{
					popupWindow.dismiss();
				}
				
				replylayout.setVisibility(View.GONE);
				return false;
			}
		});
	}
	
	@Override
	public void onBackPressed() {
		if (popupWindow.isShowing())
		{
			popupWindow.dismiss();
		}
		else
		{
			super.onBackPressed();
		}
	}

	private void loadUserData() {
		showDialog("请稍候");
		executorService.execute(new Runnable() {

			@Override
			public void run() {
				LinkedHashMap<String, String> hashMap = new LinkedHashMap<String, String>();
				String result = HttpRequest.sendGetRequestWithMd5(InvestListActivity.this, Define.host + "/api-user-main/my", hashMap);
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

	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			dismissDialog();
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
						UserBean userBean = new UserBean();
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
						imageLoader.displayImage(userBean.getFace(), roundImageView1, options);
						nicknametextView.setText(userBean.getNickname());
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

	private void loadData(int page) {
		executorService.execute(new Runnable() {
			@Override
			public void run() {
				LinkedHashMap<String, String> hashMap = new LinkedHashMap<String, String>();
				hashMap.put("p", String.valueOf(currentPage));
				hashMap.put("ps", String.valueOf(pageSize));

				String result = HttpRequest.sendGetRequestWithMd5(context, Define.host + "/api-quan-topic", hashMap);
				if (!StringUtils.isEmpty(result)) {

					ArrayList<InvestBean> resultList = InvestBean.parseData(result);

					Message message = new Message();
					message.what = currentPage == 1 ? LOAD_DATA_COMPLETE : LOAD_MORE_DATA_COMPLETE;
					message.obj = InvestListActivity.this;
					Bundle data = new Bundle();
					data.putSerializable("list", resultList);
					message.setData(data);
					sHandler.sendMessage(message);
				} else {
					Message message = new Message();
					message.what = NETWORK_FAIL;
					message.obj = InvestListActivity.this;
					sHandler.sendMessage(message);
				}

			}
		});
	}

	public void replyinvest(final String tid) {
		
		if(!AppContext.getInstance().isLogin())
		{
			Intent intent = new Intent(this, LoginActivity.class);
			startActivity(intent);
			return;
		}
		
		replylayout.setVisibility(View.VISIBLE);
		replyButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String content = replyEditText.getText().toString().trim();
				
				if (StringUtils.isEmpty(content)) {
					showToast("回复内容不能为空");
					return;
				}
				
				if(content.contains("￼"))
				{
					@SuppressWarnings("unchecked")
					ArrayList<String> list = (ArrayList<String>) replyEditText.getTag();
					
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
				
				showDialog("请稍候..");
				Tools.hideInputBoard(InvestListActivity.this);
				executorService.execute(new Runnable() {
					@Override
					public void run() {
						try {
							LinkedHashMap<String, String> hashMap = new LinkedHashMap<String, String>();
							String url;
							url = Define.host + "/api-quan-comment/add";
							String uidString = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("userid", "");
							hashMap.put("tid", tid);
							hashMap.put("cuid", uidString);
							hashMap.put("content", finalContent);
							String result = HttpRequest.sendPostRequestWithMd5(InvestListActivity.this, url, hashMap);
							JSONObject jsonObject = new JSONObject(result);

							final String ret = jsonObject.optString("ret");
							String msg = jsonObject.optString("msg");
							if (ret.equals("0")) {
								runOnUiThread(new Runnable() {
									public void run() {
										dismissDialog();
										showToast("回复成功");
										replylayout.setVisibility(View.GONE);
										replyEditText.setText("");
										mDatas.clear();
										loadData(1);
									}
								});
							} else {
								dismissDialog();
								showToast(msg);
								// replylayout.setVisibility(View.GONE);
								// loadData();
							}

						} catch (Exception e) {
							// TODO: handle exception
						}

					}
				});
			}
		});

	}

	private boolean islike = false;

	public void changeLike(final boolean islike, final String tid, final int position) {
		showDialog("请稍候..");
		executorService.execute(new Runnable() {
			@Override
			public void run() {
				try {
					LinkedHashMap<String, String> hashMap = new LinkedHashMap<String, String>();
					String url;
					if (islike) {
						url = Define.host + "/api-quan-topic/like";
					} else {
						url = Define.host + "/api-quan-topic/unlike";
					}
					InvestListActivity.this.islike = islike;
					hashMap.put("tid", tid);
					String result = HttpRequest.sendGetRequestWithMd5(context, url, hashMap);
					JSONObject jsonObject = new JSONObject(result);
					JSONObject jsonObject2 = jsonObject.optJSONObject("data");
					final String likes = jsonObject2.optString("likes");
					runOnUiThread(new Runnable() {
						public void run() {
							dismissDialog();
							InvestBean investBean = mAdapter.getItem(position);
							if (islike) {
								investBean.setIslike("1");
								showToast("点赞成功");
							} else {
								investBean.setIslike("0");
								showToast("取消点赞成功");
							}

							investBean.setLikes(likes);

							mAdapter.notifyDataSetChanged();
						}
					});

				} catch (Exception e) {
					// TODO: handle exception
				}

			}
		});
	}
	
	public View getView()
	{
		return findViewById(R.id.list_parent);
	}
}
