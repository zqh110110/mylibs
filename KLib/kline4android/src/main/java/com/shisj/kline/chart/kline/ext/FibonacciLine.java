package com.shisj.kline.chart.kline.ext;

import com.shisj.kline.chart.core.AbstractChart;
import com.shisj.kline.chart.core.Theme;
import com.shisj.kline.shape.Line;

import android.graphics.Color;


/**
 * 黄金分割线
 * @author shishengjie
 *
 */
public class FibonacciLine extends AssistLine{
	public static final String TYPE="FNL";
	float intervals[]=new float[]{0f,23.6f,38.2f,50.0f,61.8f,100,161.8f,261.8f,423.6f};
	Line lines[]=new Line[intervals.length];

	public FibonacciLine(AbstractChart chart,float bx, float by, float ex, float ey) {
		super(chart,bx, by, ex, ey);
	}

	@Override
	public void paintLines(float bx, float by,float ex,float ey) {
		
		float min_X=Math.min(bx,ex)+5;
		float max_X=Math.max(bx,ex)-5;
		float perY=(by-ey)/100;
		
		for (int i =0; i<intervals.length; i++) {
            float y=ey+intervals[i]*perY;
            this._createLine(i,min_X,max_X,y,intervals[i]);
        }
	}
	
	private void _createLine(int index, float min_X, float max_X, float y, float text) {
		Line line=this.lines[index];
		if(line!=null){
			line.setStartX(min_X);
			line.setStartY(y);
			line.setStopX(max_X);
			line.setStopY(y);
			return;
		}
		
		//创建line并添加到数组中
		line=new Line(min_X, y, max_X, y);
		line.setColor(Theme.getColor("FINA_LINE_BG"));
		line.setText(String.valueOf(text));
		line.setZlevel(30);
//		line.
		addShape(line);
		lines[index]=line;
	}
	
	@Override
	public String getConfig() {
		// TODO Auto-generated method stub
		return TYPE+"|"+beginPos.getIndex()+"|"+beginPos.getPrice()+"|"+endPos.getIndex()+"|"
				+endPos.getPrice()+"|END";
	}
	
	@Override
	public void onDispose() {
		super.onDispose();
		for(Line line:lines){
			delShape(line);
		}
	}
}
