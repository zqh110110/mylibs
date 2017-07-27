package com.kfd.activityfour;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import org.json.JSONObject;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.text.Html;
import android.text.Html.ImageGetter;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.kfd.adapter.DynamicImageAdapter;
import com.kfd.adapter.InvestListItemAdapter;
import com.kfd.api.AppContext;
import com.kfd.api.HttpRequest;
import com.kfd.api.Tools;
import com.kfd.bean.InvestBean;
import com.kfd.common.Define;
import com.kfd.common.EmojiUtil;
import com.kfd.common.StringUtils;
import com.kfd.ui.MyGridView;
import com.kfd.ui.RoundImageView;

import emoj.EmoKeybord;

public class InvestDetailActivity extends BaseActivity {
	InvestBean investBean;

	private EmoKeybord popupWindow;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.investdetail);
		initTitle("心得详情");
		if (getIntent().getSerializableExtra("investBean") != null) {
			investBean = (InvestBean) getIntent().getSerializableExtra("investBean");
			initUI();
		}
	}

	private ImageView replyImageView;
	private RoundImageView iconImageView;
	private TextView nicknameTextView, contentTextView, timeTextView, commentTextView, priseTextView, prisenameTextView;
	private ListView listView1;
	private MyGridView gridView;
	private TextView spline;
	private LinearLayout priselayout;
	private ImageView priseimageview;
	LinearLayout replylayout;
	EditText replyEditText;
	Button replyButton;

	private void initUI() {
		iconImageView = (RoundImageView) findViewById(R.id.roundImageView1);
		nicknameTextView = (TextView) findViewById(R.id.nametextView1);
		contentTextView = (TextView) findViewById(R.id.contenttextView1);
		gridView = (MyGridView) findViewById(R.id.fresh_news_photo_gv);
		timeTextView = (TextView) findViewById(R.id.time_textview);
		prisenameTextView = (TextView) findViewById(R.id.comment_name_tv);
		commentTextView = (TextView) findViewById(R.id.comment_count_tv);
		priseTextView = (TextView) findViewById(R.id.fresh_news_reply_tv);
		listView1 = (ListView) findViewById(R.id.listView1);
		spline = (TextView) findViewById(R.id.spline);
		priselayout = (LinearLayout) findViewById(R.id.priselayout);
		priseimageview = (ImageView) findViewById(R.id.priseimageview);
		replyImageView = (ImageView) findViewById(R.id.fresh_news_praise_iv);

		replylayout = (LinearLayout) findViewById(R.id.replylayout);
		replyEditText = (EditText) findViewById(R.id.replyeditText1);
		replyButton = (Button) findViewById(R.id.replybutton1);
		imageLoader.displayImage(investBean.getFace(), iconImageView, options);
		nicknameTextView.setText(investBean.getNickname());
		

		LinearLayout parentLayout = (LinearLayout) findViewById(R.id.list_parent);

		LinearLayout emoticonsCover = (LinearLayout) findViewById(R.id.footer_for_emoticons);

		ImageView view = (ImageView) findViewById(R.id.expression_add_iv);
		popupWindow = new EmoKeybord(this, parentLayout, emoticonsCover, view, replyEditText);
		
		if (!StringUtils.isEmpty(investBean.getContent())) {
			String temp_title = EmojiUtil.convertTag(investBean.getContent());

			ImageGetter imageGetter = new ImageGetter() {
				public Drawable getDrawable(String source) {
					Drawable d = new BitmapDrawable(InvestDetailActivity.this.getResources(), popupWindow.getEmoticons()[Integer.parseInt(source.replace(".png", "")) - 100]);
					d.setBounds(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());
					return d;
				}
			};
			contentTextView.setText(Html.fromHtml(temp_title, imageGetter, null));
		}

		// contentTextView.setText(investBean.getContent());
		if (investBean.getCommArrayList() != null && investBean.getCommArrayList().size() > 0) {

			listView1.setAdapter(new InvestListItemAdapter(investBean.getCommArrayList(), context, getLayoutInflater(), popupWindow.getEmoticons()));

			spline.setVisibility(View.VISIBLE);
			listView1.invalidate();
		} else {

		}
		// timeTextView.setText(StringUtils.friendly_time(investBean.get));
		StringBuilder stringBuilder = new StringBuilder();
		if (investBean.getArrayList().size() > 0) {
			for (int i = 0; i < investBean.getArrayList().size(); i++) {
				if (i != 0) {
					stringBuilder.append("," + investBean.getArrayList().get(i).getNickname());
				} else {
					stringBuilder.append(investBean.getArrayList().get(i).getNickname());
				}

			}

			prisenameTextView.setText(stringBuilder.toString());
		} else {
			spline.setVisibility(View.GONE);
			priselayout.setVisibility(View.GONE);
		}

		timeTextView.setText(StringUtils.phpdateformat(investBean.getDateline()));
		if (investBean.getPicListsArrayList().size() > 0) {
			DynamicImageAdapter dynamicImageAdapter = new DynamicImageAdapter(context, investBean.getPicListsArrayList(), imageLoader);
			gridView.setVisibility(View.VISIBLE);
			gridView.setAdapter(dynamicImageAdapter);
			gridView.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
					/*
					 * ArrayList<String> urlArrayList = new ArrayList<String>();
					 * for (int i = 0; i <
					 * investBean.getPicListsArrayList().size(); i++) {
					 * urlArrayList
					 * .add(investBean.getPicListsArrayList().get(i).
					 * getPicture()); } DressUpBean dressupbean = new
					 * DressUpBean(); dressupbean.setPic(urlArrayList); if
					 * (position < arrayList.size()) { Intent intent = new
					 * Intent(context, DressUpDetailBigImgclass);
					 * intent.putExtra("dressUpBean",dressupbean);
					 * intent.putExtra("currSelectIndex", position);
					 * context.startActivity(intent); } else {
					 * Toast.makeText(context, "数据异常", 0).show(); }
					 */
				}
			});
		} else {
			gridView.setVisibility(View.GONE);
		}
		if (!StringUtils.isEmpty(investBean.getLikes())) {

			priseTextView.setText(investBean.getLikes());
		}
		if (!StringUtils.isEmpty(investBean.getComments())) {

			commentTextView.setText(investBean.getComments());
		}
		if (investBean.getIslike().equals("1")) {
			priseimageview.setImageDrawable(context.getResources().getDrawable(R.drawable.praise_green_ico));
		} else {
			priseimageview.setImageDrawable(context.getResources().getDrawable(R.drawable.praise_ico));
		}
		priseimageview.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String tid = investBean.getId();
				if (investBean.getIslike().equals("1")) {
					changeLike(false, tid);
				} else {
					changeLike(true, tid);
				}

			}
		});

		replyButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				replyinvest(investBean.getId());
			}
		});

	}

	private boolean islike = false;

	public void changeLike(final boolean islike, final String tid) {
		JumpLogin();
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
					InvestDetailActivity.this.islike = islike;
					hashMap.put("tid", tid);
					String result = HttpRequest.sendGetRequestWithMd5(context, url, hashMap);
					JSONObject jsonObject = new JSONObject(result);
					JSONObject jsonObject2 = jsonObject.optJSONObject("data");
					final String likes = jsonObject2.optString("likes");
					runOnUiThread(new Runnable() {
						public void run() {
							dismissDialog();
							// InvestBean investBean= adapter.getItem(position);
							if (islike) {
								investBean.setIslike("1");
								showToast("点赞成功");
							} else {
								investBean.setIslike("0");
								showToast("取消点赞成功");
							}
							investBean.setLikes(likes);
							if (investBean.getIslike().equals("1")) {
								priseimageview.setImageDrawable(context.getResources().getDrawable(R.drawable.praise_green_ico));
							} else {
								priseimageview.setImageDrawable(context.getResources().getDrawable(R.drawable.praise_ico));
							}

							// adapter.notifyDataSetChanged();
						}
					});

				} catch (Exception e) {
					// TODO: handle exception
				}

			}
		});
	}

	public void replyinvest(final String tid) {

		if (!AppContext.getInstance().isLogin()) {
			Intent intent = new Intent(this, LoginActivity.class);
			startActivity(intent);
			return;
		}

		replyButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String content = replyEditText.getText().toString().trim();
				if (StringUtils.isEmpty(content)) {
					showToast("回复内容不能为空");
					return;
				}
				if (content.contains("￼")) {
					@SuppressWarnings("unchecked")
					ArrayList<String> list = (ArrayList<String>) replyEditText.getTag();

					String newContent = "";

					int j = 0;
					for (int i = 0; i < content.length(); i++) {

						String a = content.toString().substring(i, i + 1);
						if (a.equals("￼")) {
							newContent = newContent + list.get(j);
							j++;
						} else {
							newContent = newContent + a;
						}
					}

					content = newContent;

				}

				final String finalContent = String.valueOf(content);

				showDialog("请稍候..");
				Tools.hideInputBoard(InvestDetailActivity.this);
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
							String result = HttpRequest.sendPostRequestWithMd5(InvestDetailActivity.this, url, hashMap);
							JSONObject jsonObject = new JSONObject(result);

							final String ret = jsonObject.optString("ret");
							String msg = jsonObject.optString("msg");
							if (ret.equals("0")) {
								runOnUiThread(new Runnable() {
									public void run() {
										dismissDialog();
										showToast("回复成功");

										replyEditText.setText("");
										/*
										 * arrayList.clear();
										 * pullToRefreshListView =
										 * (PullToRefreshListView)
										 * findViewById(R
										 * .id.pullToRefreshListView1); adapter
										 * = new InvalistAdapter(arrayList,
										 * InvestListActivity.this,
										 * getLayoutInflater
										 * (),imageLoader,InvestListActivity
										 * .this);
										 * pullToRefreshListView.setAdapter
										 * (adapter);
										 * pullToRefreshListView.invalidate();
										 */
										loadData();
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

	private void loadData() {
		showDialog("请稍候..");
		executorService.execute(new Runnable() {
			@Override
			public void run() {
				LinkedHashMap<String, String> hashMap = new LinkedHashMap<String, String>();
				String result = HttpRequest.sendGetRequestWithMd5(InvestDetailActivity.this, Define.host + "/api-quan-topic", hashMap);
				if (!StringUtils.isEmpty(result)) {
					Message message = new Message();
					message.what = 1;
					message.obj = result;
					updateUIHandler.sendMessage(message);
				} else {
					updateUIHandler.sendEmptyMessage(0);
				}

			}
		});
	}

	private Handler updateUIHandler = new Handler() {
		public void handleMessage(Message msg) {

			dismissDialog();
			switch (msg.what) {
			case 0:

				break;
			case 1:
				String result = (String) msg.obj;
				ArrayList<InvestBean> arrayListresult = InvestBean.parseData(result);
				for (int i = 0; i < arrayListresult.size(); i++) {
					InvestBean investBean = arrayListresult.get(i);
					if (investBean.getId().equals(InvestDetailActivity.this.investBean.getId())) {
						InvestDetailActivity.this.investBean = investBean;
						initUI();
						break;
					}
				}
				break;

			default:
				break;
			}
		};
	};
}
