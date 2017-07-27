package com.kfd.bean;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.kfd.common.LogUtils;

public class MainMessageListBean extends Entity {

	private static final long serialVersionUID = 1L;

	private String maincatalog;
	private int platecatalog;
	private int pageSize;
	private int pageCount;
	private static ArrayList<MainMesageBean> financelist = new ArrayList<MainMesageBean>();

	private static ArrayList<MainMesageBean> daofulist = new ArrayList<MainMesageBean>();

	public ArrayList<MainMesageBean> getFinancelist() {
		return financelist;
	}

	public void setFinancelist(ArrayList<MainMesageBean> financelist) {
		this.financelist = financelist;
	}

	public ArrayList<MainMesageBean> getDaofulist() {
		return daofulist;
	}

	public void setDaofulist(ArrayList<MainMesageBean> daofulist) {
		this.daofulist = daofulist;
	}

	public String getMaincatalog() {
		return maincatalog;
	}

	public void setMaincatalog(String maincatalog) {
		this.maincatalog = maincatalog;
	}

	public int getPlatecatalog() {
		return platecatalog;
	}

	public void setPlatecatalog(int platecatalog) {
		this.platecatalog = platecatalog;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPageCount() {
		return pageCount;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

	
	private static  String  replaceWord(String inputString){
		// 生成 Pattern 对象并且编译一个简单的正则表达式"Kelvin"
        Pattern p = Pattern.compile("道富"); 
        // 用 Pattern 类的 matcher() 方法生成一个 Matcher 对象
        Matcher m = p.matcher(inputString); 
        StringBuffer sb = new StringBuffer(); 
        int i=0; 
        // 使用 find() 方法查找第一个匹配的对象
        boolean result = m.find(); 
        // 使用循环将句子里所有的 kelvin 找出并替换再将内容加到 sb 里
        while(result) { 
            i++; 
            m.appendReplacement(sb, "贝尔"); 
           // System.out.println("第"+i+"次匹配后 sb 的内容是："+sb); 
            // 继续查找下一个匹配对象
            result = m.find(); 
        } 
        // 最后调用 appendTail() 方法将最后一次匹配后的剩余字符串加到 sb 里；
        m.appendTail(sb); 
        return sb.toString();
//        System.out.println("调用 m.appendTail(sb) 后 sb 的最终内容是 :"+ 
//			sb.toString());
    } 


	
	/**
	 * 数据解析
	 * 
	 * @param string
	 * @return
	 */

	public static MainMessageListBean parseData(String string) {
	
		//首先将数据里面的贝尔点评
		LogUtils.log("test", string);
		
			
		
		
		MainMessageListBean listBean = new MainMessageListBean();
		try {
			
			if (financelist != null && financelist.size() > 0) {
				financelist.clear();
			}
			if (daofulist != null && daofulist.size() > 0) {
				daofulist.clear();
			}
			if (string != null && string.length() > 0) {
				JSONObject jsonObject = JSONObject.parseObject(string);
				JSONArray array = jsonObject.getJSONArray("hqzx");
				for (int i = 0; i < array.size(); i++) {
					JSONObject jsonObject2 = array.getJSONObject(i);
					MainMesageBean mainMesageBean = new MainMesageBean();
					mainMesageBean.setArticle_id(jsonObject2
							.getString("article_id"));
					mainMesageBean.setC_id(jsonObject2.getString("c_id"));
					mainMesageBean.setTitle(jsonObject2.getString("title"));
					String abstractsting  =	replaceWord(jsonObject2
							.getString("abstract"));
					mainMesageBean.setAbstractmessage(abstractsting);
					mainMesageBean.setTitle_pic(jsonObject2.getString("title_pic"));
					mainMesageBean.setAdd_time(jsonObject2.getString("add_time"));
					mainMesageBean.setContent(jsonObject2.getString("content")
							.trim());
					financelist.add(mainMesageBean);
				}
				listBean.setFinancelist(financelist);
	
				JSONArray array2 = jsonObject.getJSONArray("brgd");
				for (int i = 0; i < array2.size(); i++) {
					JSONObject jsonObject2 = array2.getJSONObject(i);
					MainMesageBean mainMesageBean = new MainMesageBean();
					mainMesageBean.setArticle_id(jsonObject2
							.getString("article_id"));
					mainMesageBean.setC_id(jsonObject2.getString("c_id"));
					mainMesageBean.setTitle(jsonObject2.getString("title"));
					mainMesageBean.setAbstractmessage(jsonObject2
							.getString("abstract"));
					mainMesageBean.setTitle_pic(jsonObject2.getString("title_pic"));
					mainMesageBean.setAdd_time(jsonObject2.getString("add_time"));
					mainMesageBean.setContent(jsonObject2.getString("content")
							.trim());
					daofulist.add(mainMesageBean);
				}
				listBean.setDaofulist(daofulist);
	
			} else {
				listBean = null;
			}
		
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return listBean;

	}

}