package com.kfd.activityfour;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.json.JSONArray;
import org.json.JSONObject;

import com.kfd.adapter.DiaryAdapter;
import com.kfd.adapter.EventAdapter;
import com.kfd.adapter.FianceCountryAdapter;
import com.kfd.api.HttpRequest;
import com.kfd.bean.Base;
import com.kfd.bean.EventBean;
import com.kfd.bean.FinaceCountryBean;
import com.kfd.bean.Holiday;
import com.kfd.bean.TitleBase;
import com.kfd.bean.TradeBean;
import com.kfd.common.Define;
import com.kfd.common.StringUtils;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class FinaceCalenderFragment extends  Fragment  implements OnClickListener{
	View view;
	Context  context;
	ListView  listView1;
	FianceCountryAdapter  adapter;
//	ListView  listView2;
//	DiaryAdapter  adapter2;
//	ListView  listView3;
//	EventAdapter  adapter3;
	LayoutInflater inflater;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view  = inflater.inflate(R.layout.finance, null);
		context  =  inflater.getContext();
		this.inflater = inflater;
		listView1  = (ListView) view.findViewById(R.id.listView1);
		//adapter  = new FianceCountryAdapter(countryList, inflater.getContext(), inflater);
	//	listView1.setAdapter(adapter);
//		listView2  = (ListView) view.findViewById(R.id.listView2);
//		adapter2  = new DiaryAdapter(holidayArrayList, inflater.getContext(), inflater);
//		listView2.setAdapter(adapter2);
//		listView3  = (ListView) view.findViewById(R.id.listView3);
//		adapter3  = new EventAdapter(eventList, inflater.getContext(), inflater);
//		listView3.setAdapter(adapter3);
		initUI(view);
		
		
		getNowDay();
		return view;
	}
	private void getNowDay(){
		executorService.execute(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				LinkedHashMap<String, String> hashMap =  new LinkedHashMap<String, String>();
				String result = HttpRequest.sendGetRequestWithMd5(context, Define.host+"/api-market/financeDate", hashMap);
				if (!StringUtils.isEmpty(result)) {
					Message message = new Message();
					message.obj=  result;
					message.what=1;
					updateHandler.sendMessage(message);
				}
			}
		});
	}
	ArrayList<FinaceDay>  arrayList  = new ArrayList<FinaceCalenderFragment.FinaceDay>();
	private Handler updateHandler  = new Handler(){
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				
				break;
			case 1:
				String resultString  = (String) msg.obj;
				try {
					
					JSONObject jsonObject  = new JSONObject(resultString);
					JSONObject  data  =  jsonObject.optJSONObject("data");
					JSONArray   priceArray  = data.optJSONArray("list");
					arrayList.clear();
					for (int i = 0; i < priceArray.length(); i++) {
						JSONObject  jsonObject2  =  priceArray.optJSONObject(i);
						FinaceDay day  = new FinaceDay();
						day.setTime(jsonObject2.optString("time"));
						day.setToday(jsonObject2.optString("today"));
						day.setWeek(jsonObject2.optString("week"));
						arrayList.add(day);
					}
					
					for (int j = 0; j < arrayList.size(); j++) {
						FinaceDay day  = arrayList.get(j);
						String time =  StringUtils.phpdateformat3(day.getTime());
						String weekday = day.getWeek();
						if (j==0) {
							 datetextView1.setText(time+"");
							 weektextView1.setText(weekday);
						}
						if (j==1) {
							 datetextView2.setText(time+"");
							 weektextView2.setText(weekday);						
												
						}
						if (j==2) {
							 datetextView3.setText(time+"");
							 weektextView3.setText(weekday);
						}
						if (j==3) {
							 datetextView4.setText(time+"");
							 weektextView4.setText(weekday);
						}
						if (j==4) {
							 datetextView5.setText(time+"");
							 weektextView5.setText(weekday);
						}
						if (j==5) {
							 datetextView6.setText(time+"");
							 weektextView6.setText(weekday);
						}
					
						if (day.getToday().equals("1")) {
							changeTextColor(j+1);
							loadNEWS(StringUtils.phpdateformat5(arrayList.get(j).getTime()));
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

class  FinaceDay{
	/**
	 * time 时间
 
week
 周几
 
today
 是否今天 1-是 0-否 

	 */
	private String time,week,today;

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getWeek() {
		return week;
	}

	public void setWeek(String week) {
		this.week = week;
	}

	public String getToday() {
		return today;
	}

	public void setToday(String today) {
		this.today = today;
	}
	
}


	private int  currentpage=1;
	public ExecutorService executorService = Executors.newFixedThreadPool(5);
	private void loadNEWS(final  String date){
		
		executorService.execute(new Runnable() {
			@Override
			public void run() {
				LinkedHashMap<String, String>  hashMap  = new LinkedHashMap<String, String>();
			//	hashMap.put("ps", "25");
			//	hashMap.put("p", pageindex+"");
				hashMap.put("date", date);
				String result = HttpRequest.sendGetRequestWithMd5(context, Define.host+"/api-market/finance", hashMap);
				if (!StringUtils.isEmpty(result)) {
					Message message= new Message();
					message.what=2;
					message.obj = result;
					updateUIHandler.sendMessage(message);
				}else {
					updateUIHandler.sendEmptyMessage(3);
				}
				
				
			}
		});
	}
	private ArrayList<Base>  arrayList2  = new ArrayList<Base>();
	private ArrayList<FinaceCountryBean>  countryList  = new ArrayList<FinaceCountryBean>();
	private ArrayList<Holiday>   holidayArrayList  =  new ArrayList<Holiday>();
	private ArrayList<EventBean>  eventList  =  new ArrayList<EventBean>();
	private Handler updateUIHandler =  new Handler(){
		public void handleMessage(Message msg) {
		
		//	dismissDialog();
			switch (msg.what) {
	
			case 2:
				currentpage++;
				String resultsecond = (String) msg.obj;
				try {
					JSONObject jsonObject  = new JSONObject(resultsecond);
					JSONObject  data  =  jsonObject.optJSONObject("data");
					JSONArray   priceArray  = data.optJSONArray("finance");
					arrayList2.clear();
					countryList.clear();
					TitleBase titleBase = new TitleBase();
					titleBase.setType(4);
					titleBase.setItem(1);
				
				//	if (priceArray!=null  && priceArray.length()>0) {
						arrayList2.add(titleBase);
				//	}
					for (int i = 0; i < priceArray.length(); i++) {
						JSONObject  jsonObject2  =  priceArray.optJSONObject(i);
						FinaceCountryBean countryBean  = new FinaceCountryBean();
						countryBean.setBefore_num(jsonObject2.optString("before_num"));
						countryBean.setContent(jsonObject2.optString("content"));
						countryBean.setCountry(jsonObject2.optString("country"));
						countryBean.setCountry_img(jsonObject2.optString("country_img"));
						countryBean.setForecast(jsonObject2.optString("forecast"));
						countryBean.setImportant(jsonObject2.optString("important"));
						countryBean.setResult(jsonObject2.optString("result"));
						countryBean.setTime(jsonObject2.optString("time"));
						countryBean.setType(1);
						countryList.add(countryBean);
						arrayList2.add(countryBean);
					}
					TitleBase titleBase2 = new TitleBase();
					titleBase2.setType(4);
					titleBase2.setItem(2);
				
					
					
					JSONArray   holidayArray  = data.optJSONArray("holiday");
				//	if (holidayArray!=null && holidayArray.length()>0) {
						
						arrayList2.add(titleBase2);
					//}
					holidayArrayList.clear();
					for (int i = 0; i < holidayArray.length(); i++) {
						JSONObject  jsonObject2  =  holidayArray.optJSONObject(i);
						Holiday holiday  = new Holiday();
						holiday.setContent(jsonObject2.optString("content"));
						holiday.setCountry(jsonObject2.optString("country"));
						holiday.setTime(jsonObject2.optString("time"));
						holiday.setType(2);
						holidayArrayList.add(holiday);
						arrayList2.add(holiday);
					}
					
					
					TitleBase titleBase3= new TitleBase();
					titleBase3.setType(4);
					titleBase3.setItem(3);
				
					
					JSONArray   eventArray  = data.optJSONArray("event");
				//	if (eventArray!=null && eventArray.length()>0) {
						arrayList2.add(titleBase3);
				//	}
					
					eventList.clear();
					for (int i = 0; i < eventArray.length(); i++) {
						JSONObject  jsonObject2  =  eventArray.optJSONObject(i);
						EventBean holiday  = new EventBean();
						holiday.setContent(jsonObject2.optString("content"));
						holiday.setCountry(jsonObject2.optString("country"));
						holiday.setTime(jsonObject2.optString("time"));
						holiday.setImportant(jsonObject2.optString("important"));
						holiday.setType(3);
						eventList.add(holiday);
						arrayList2.add(holiday);
					}
					
					adapter  = new FianceCountryAdapter(arrayList2, inflater.getContext(), inflater);
					listView1.setAdapter(adapter);

					setListViewHeightBasedOnChildren(listView1);

				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
			case  3:
				
				break;
			default:
				break;
			}
		};
	};
	
	
	   public static void setListViewHeightBasedOnChildren(ListView listView) {
		   try {
			   ListAdapter listAdapter = listView.getAdapter(); 
	           if (listAdapter == null) {
	               // pre-condition
	               return;
	           }

	           int totalHeight = 0;
	           for (int i = 0; i < listAdapter.getCount(); i++) {
	               View listItem = listAdapter.getView(i, null, listView);
	               listItem.measure(0, 0);
	               totalHeight += listItem.getMeasuredHeight();
	           }

	           ViewGroup.LayoutParams params = listView.getLayoutParams();
	           params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
	           listView.setLayoutParams(params);
		} catch (Exception e) {
			e.printStackTrace();
		}
           
       }
	
	LinearLayout datelayout1,datelayout2,datelayout3,datelayout4,datelayout5,datelayout6;
	TextView datetextView1,datetextView2,datetextView3,datetextView4,datetextView5,datetextView6;
	TextView weektextView1,weektextView2,weektextView3,weektextView4,weektextView5,weektextView6;
	
	private void initUI(View view){
		datelayout1= (LinearLayout) view.findViewById(R.id.datelayout1);
		datelayout2= (LinearLayout) view.findViewById(R.id.datelayout2);
		datelayout3= (LinearLayout) view.findViewById(R.id.datelayout3);
		datelayout4= (LinearLayout) view.findViewById(R.id.datelayout4);
		datelayout5= (LinearLayout) view.findViewById(R.id.datelayout5);
		datelayout6= (LinearLayout) view.findViewById(R.id.datelayout6);
		
		
		datetextView1 = (TextView) view.findViewById(R.id.datetextView1);
		datetextView2 = (TextView) view.findViewById(R.id.datetextView2);
		datetextView3 = (TextView) view.findViewById(R.id.datetextView3);
		datetextView4 = (TextView) view.findViewById(R.id.datetextView4);
		datetextView5 = (TextView) view.findViewById(R.id.datetextView5);
		datetextView6 = (TextView) view.findViewById(R.id.datetextView6);
		
		weektextView1= (TextView) view.findViewById(R.id.weektextView1);
		weektextView2= (TextView) view.findViewById(R.id.weektextView2);
		weektextView3= (TextView) view.findViewById(R.id.weektextView3);
		weektextView4= (TextView) view.findViewById(R.id.weektextView4);
		weektextView5= (TextView) view.findViewById(R.id.weektextView5);
		weektextView6= (TextView) view.findViewById(R.id.weektextView6);
		
		datelayout1.setOnClickListener(this);
		datelayout2.setOnClickListener(this);
		datelayout3.setOnClickListener(this);
		datelayout4.setOnClickListener(this);
		datelayout5.setOnClickListener(this);
		datelayout6.setOnClickListener(this);
		changeTextColor(1);
		
		 
	}

	@Override
	public void onClick(View v) {
		if (v==datelayout1) {
			changeTextColor(1);
		loadNEWS(StringUtils.phpdateformat5(arrayList.get(1).getTime()));
		}else if (v==datelayout2) {
			changeTextColor(2);
			loadNEWS(StringUtils.phpdateformat5(arrayList.get(2).getTime()));
		}else if (v==datelayout3) {
			changeTextColor(3);
			loadNEWS(StringUtils.phpdateformat5(arrayList.get(3).getTime()));
		}else if (v==datelayout4) {
			changeTextColor(4);
			loadNEWS(StringUtils.phpdateformat5(arrayList.get(4).getTime()));
		}else if (v==datelayout5) {
			changeTextColor(5);
			loadNEWS(StringUtils.phpdateformat5(arrayList.get(5).getTime()));
		}else if (v==datelayout6) {
			changeTextColor(6);
			loadNEWS(StringUtils.phpdateformat5(arrayList.get(6).getTime()));
		}
		
	}
	private void changeTextColor(int index){
		 datelayout1.setBackgroundColor(Color.parseColor("#ffffff"));
		 datelayout2.setBackgroundColor(Color.parseColor("#ffffff"));
		 datelayout3.setBackgroundColor(Color.parseColor("#ffffff"));
		 datelayout4.setBackgroundColor(Color.parseColor("#ffffff"));
		 datelayout5.setBackgroundColor(Color.parseColor("#ffffff"));
		 datelayout6.setBackgroundColor(Color.parseColor("#ffffff"));
		datetextView1.setTextColor(Color.parseColor("#999999"));
		datetextView2.setTextColor(Color.parseColor("#999999"));
		datetextView3.setTextColor(Color.parseColor("#999999"));
		datetextView4.setTextColor(Color.parseColor("#999999"));
		datetextView5.setTextColor(Color.parseColor("#999999"));
		datetextView6.setTextColor(Color.parseColor("#999999"));
		weektextView1.setTextColor(Color.parseColor("#999999"));
		weektextView2.setTextColor(Color.parseColor("#999999"));
		weektextView3.setTextColor(Color.parseColor("#999999"));
		weektextView4.setTextColor(Color.parseColor("#999999"));
		weektextView5.setTextColor(Color.parseColor("#999999"));
		weektextView6.setTextColor(Color.parseColor("#999999"));
		switch (index) {
		case 1:
			 datelayout1.setBackgroundColor(Color.parseColor("#b02b31"));
			 datetextView1.setTextColor(Color.parseColor("#ffffff"));
			 weektextView1.setTextColor(Color.parseColor("#ffffff"));
			break;
		case 2:
			 datelayout2.setBackgroundColor(Color.parseColor("#b02b31"));
			 datetextView2.setTextColor(Color.parseColor("#ffffff"));
			 weektextView2.setTextColor(Color.parseColor("#ffffff"));
				break;
		case 3:
			 datelayout3.setBackgroundColor(Color.parseColor("#b02b31"));
			 datetextView3.setTextColor(Color.parseColor("#ffffff"));
			 weektextView3.setTextColor(Color.parseColor("#ffffff"));
			break;
		case 4:
			 datelayout4.setBackgroundColor(Color.parseColor("#b02b31"));
			 datetextView4.setTextColor(Color.parseColor("#ffffff"));
			 weektextView4.setTextColor(Color.parseColor("#ffffff"));
			break;
		case 5:
			 datelayout5.setBackgroundColor(Color.parseColor("#b02b31"));
			 datetextView5.setTextColor(Color.parseColor("#ffffff"));
			 weektextView5.setTextColor(Color.parseColor("#ffffff"));
			break;
		case 6:
			 datelayout6.setBackgroundColor(Color.parseColor("#b02b31"));
			 datetextView6.setTextColor(Color.parseColor("#ffffff"));
			 weektextView6.setTextColor(Color.parseColor("#ffffff"));
			break;
		default:
			break;
		}
	}
}
