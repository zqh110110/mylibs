package com.shisj.kline.chart.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.shisj.kline.shape.Base;
import com.shisj.kline.shape.Drawable;



/**
 * 保存所有的图形
 * @author shishengjie
 *
 */
public class Storage implements Comparator<Drawable>{

	private List<Base> roots=new ArrayList<Base>();
	private long uid=0x0;//id的index
	private boolean needSort=true;//是否需要排序
	//可移动的图形
	private List<Base> moves=new ArrayList<Base>();
	
	public boolean isNeedSort() {
		return needSort;
	}

	public void setNeedSort(boolean needSort) {
		this.needSort = needSort;
	}

	public void addShape(Base shape){
		roots.add(shape);
		String id=shape.getId();
		if(id!=null)id=id.trim();
		if(id==null||"".equals(id))
			id="kchart_"+(uid++);
		
		//若可移动，加入moves中
		boolean move=shape.isMoveable();
		if(move){
			moves.add(shape);
		}
	}
	
	public void delShape(Base shape){
		int index=roots.indexOf(shape);
		if(index==-1)return;
		roots.set(index,null);//设置为空
		
		//添加时若可以移动，删除时从moves中删除
		moves.remove(shape);
	}
	
	public int getSize(){
		return roots.size();
	}
	
	public List<Base> getSortedList(){
		if(needSort){
			Collections.sort(roots, this);
			needSort=false;
		}
		return roots;
	}

	@Override
	public int compare(Drawable lhs, Drawable rhs) {
		 int lz=0,rz=0;
		 if(lhs!=null)
			 lz=lhs.getZlevel();
		 if(rhs!=null)
			 rz=rhs.getZlevel();
		 
		 return lz-rz;
	}
	
	/**
	 * 是否选择了可移动的点
	 * @param x
	 * @param y
	 * @return
	 */
	public Base getMoveableShape(float x,float y){
		for(Base base:moves){
			if(base.inArea(x, y))
				return base;
		}
		return null;
	}
}
