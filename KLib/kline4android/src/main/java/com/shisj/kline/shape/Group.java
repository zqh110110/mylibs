package com.shisj.kline.shape;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * 暂时先不支持group嵌套 后续再说吧
 * group中的shape不一定在同一个zlevel
 * @author shishengjie
 *
 */
public class Group implements Comparator<Base> {
	//保存所有的图形
	private List<Base> shapes=new ArrayList<Base>();
	
	//添加子图形
	public void addChildren(Base shape){
		shapes.add(shape);
	}
	
	public List<Base> getChildren(){
		return shapes;
	}
	
	//绘制
	public void paint(Canvas canvas, Paint paint) {
		//排序一下
		Collections.sort(shapes, this);
		for(Base shape:shapes){
			shape.paint(canvas, paint);
		}
	}

	@Override
	public int compare(Base lhs, Base rhs) {
		// TODO Auto-generated method stub
		return lhs.getZlevel()-rhs.getZlevel();
	}
}
