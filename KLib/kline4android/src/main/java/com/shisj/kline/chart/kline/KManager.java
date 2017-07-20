package com.shisj.kline.chart.kline;

import com.shisj.kline.chart.kline.ext.AssistLine;
import com.shisj.kline.chart.kline.ext.BollLine;
import com.shisj.kline.chart.kline.ext.CrossLinePainter;
import com.shisj.kline.chart.kline.ext.FibonacciLine;
import com.shisj.kline.chart.kline.ext.FlagLine;
import com.shisj.kline.chart.kline.ext.MACDWindow;
import com.shisj.kline.chart.kline.ext.MALine;
import com.shisj.kline.chart.kline.ext.CrossLine.ICallback;
import com.shisj.kline.shape.IAssistMove;
import com.shisj.kline.shape.Text;
import com.shisj.kline.util.KPosition;
import com.shisj.kline.view.ChartView;
import com.shisj.kline.view.RecordManager;

import android.util.Log;


/**
 * 提供附加线的接口
 * @author shishengjie
 * @date 2016-6-13 下午1:47:39
 */
public class KManager {
	RecordManager recordManager=null;
	ChartView view=null;
	KLine kLine=null;
	public KManager(ChartView view){
		this.view=view;
		this.recordManager=view.getRecordManager();
		this.kLine=(KLine) view.getChart();
	}
	
	
	public void register(){
		
		//注册MALine的创建方式
		this.recordManager.register(MALine.TYPE, new RecordManager.ICreator() {
			@Override
			public Object getInstance(String params[]) {
				MALine maLine=new MALine(kLine);
				view.addPainter(maLine);
		    	return maLine;
			}

			@Override
			public void dispose(Object object) {
				MALine maLine=(MALine)object;
				view.delPainter(maLine);
			}
		});
		
		//注册BollLine的创建方式
		this.recordManager.register(BollLine.TYPE, new RecordManager.ICreator() {
			@Override
			public Object getInstance(String params[]) {
				BollLine bollLine=new BollLine(kLine);
				view.addPainter(bollLine);
				return bollLine;
			}
			@Override
			public void dispose(Object object) {
				BollLine bollLine=(BollLine)object;
				view.delPainter(bollLine);
			}
		});		
		
		//注册MACD的创建方式
		this.recordManager.register(MACDWindow.TYPE, new RecordManager.ICreator() {
			@Override
			public Object getInstance(String params[]) {
				MACDWindow window=new MACDWindow(kLine,kLine.getpHeight()/4);
//				window.setHeight(kLine.getpHeight()/4);
				kLine.addWindow(window);
				kLine.reLayout();
				view.addToRecord(window);
				return window;
			}

			@Override
			public void dispose(Object object) {
				MACDWindow window=(MACDWindow)object;
				kLine.delWindow(window);
				view.delFromRecord(window);
				kLine.reLayout();
			}
		});
		
		//注册FlagLine的创建方式
		this.recordManager.register(FlagLine.TYPE, new RecordManager.ICreator() {
			@Override
			public Object getInstance(String params[]) {
				KLine kline=(KLine) view.getChart();
				Float price=Float.valueOf(params[1]);
				String text="";
				int color=-1;
				if(params.length>2){
					text=params[2];
				}
				if(params.length==4){//设置颜色
					color=Integer.valueOf(params[3]);
				}
						
				FlagLine fLine=new FlagLine(kline);
				fLine.setPrice(price);
				fLine.setShowText(text);
				if(color!=-1){//设置颜色
					fLine.setColor(color);
				}
				view.addPainter(fLine);
				return fLine;
			}

			@Override
			public void dispose(Object object) {
				FlagLine fLine=(FlagLine)object;
				view.delPainter(fLine);
			}
		});
		
		//注册AssistLine的创建方式
		this.recordManager.register(AssistLine.TYPE, new RecordManager.ICreator() {
			@Override
			public Object getInstance(String params[]) {
						
				float ret[]=getAssistLocation(params);
				AssistLine line=new AssistLine(kLine,ret[0],ret[1],ret[2],ret[3]);
				view.addPainter(line);
				return line;
			}

			@Override
			public void dispose(Object object) {
				// TODO Auto-generated method stub
				AssistLine assistLine=(AssistLine)object;
				view.delPainter(assistLine);
			}
		});
		
		//注册AssistLine的创建方式
		this.recordManager.register(FibonacciLine.TYPE, new RecordManager.ICreator() {
			@Override
			public Object getInstance(String params[]) {
						
				float ret[]=getAssistLocation(params);
				FibonacciLine line=new FibonacciLine(kLine,ret[0],ret[1],ret[2],ret[3]);
				view.addPainter(line);
				return line;
			}
			@Override
			public void dispose(Object object) {
				FibonacciLine fibonacciLine=(FibonacciLine)object;
				view.delPainter(fibonacciLine);
			}
		});
		
		
		this.recordManager.register(CrossLinePainter.TYPE, new RecordManager.ICreator() {
			@Override
			public Object getInstance(String params[]) {
				
				KPosition pos=getCrossLocation(params);
				CrossLinePainter line=new CrossLinePainter(kLine,pos,new ICallback() {
					
					@Override
					public void update(KPosition pos) {
						int index=pos.getIndex();
						CandleData data=kLine.getQueue().getCandleData(index);
						if(data==null)return;
						Text text=kLine.getKMain().getPriceText();
						String price=kLine.getKMain().getCandleText(data);
						text.setText(price);
						view.rePaint();
					}
				});
				line.setClose(new IAssistMove() {
					
					@Override
					public void up() {
					}
					
					@Override
					public void move(float offsetX, float offsetY) {
						
					}

					@Override
					public void down() {
						delCrossLine();//删除
					}
				});
				view.addPainter(line);
				kLine.setMoveable(false);
				return line;
			}

			@Override
			public void dispose(Object object) {
				// TODO Auto-generated method stub
				CrossLinePainter crossLine=(CrossLinePainter)object;
				view.delPainter(crossLine);
				kLine.setMoveable(true);
			}
		});
	}
	
	
	private KPosition getCrossLocation(String params[]){
		int index;
		float price,x = 0,y=0;
		if(params.length==3){//手工添加,x&y
			x=Float.valueOf(params[1]);
			y=Float.valueOf(params[2]);
		}else{//旋转 index&price
			index=Integer.valueOf(params[1]);
			price=Float.valueOf(params[2]);
			//根据四个变量计算出bx by ex ey
			x=kLine.getXPxWin(index);
			y=kLine.getYPx(price);
		}
		return kLine.adsorb(x,y,true);
	}
	
	private float[] getAssistLocation(String params[]){
		int bIndex,eIndex;
		float bPrice,ePrice,bx = 0,by=0,ex=0,ey=0;
		if(params.length==5){//手工添加
			bx=Float.valueOf(params[1]);
			by=Float.valueOf(params[2]);
			ex=Float.valueOf(params[3]);
			ey=Float.valueOf(params[4]);
		}else{//旋转
			Log.e("prt", params.length+" ");
			bIndex=Integer.valueOf(params[1]);
			eIndex=Integer.valueOf(params[3]);
			bPrice=Float.valueOf(params[2]);
			ePrice=Float.valueOf(params[4]);
			//根据四个变量计算出bx by ex ey
			bx=kLine.getXPxWin(bIndex);
			by=kLine.getYPx(bPrice);
			
			ex=kLine.getXPxWin(eIndex);
			ey=kLine.getYPx(ePrice);
		}
		return new float[]{bx,by,ex,ey};
	}
	
	public Object getLine(String type, int index) {
		return this.recordManager.getInstance(type, index);
	}
	
	
	/**
	 * 获取某种辅助线的实例对象
	 * 
	 * @param type
	 * @return
	 */
	public int lineSize(String type) {
		return this.recordManager.instanceSize(type);
	}

	/**
	 * 添加标志线
	 * 
	 * @param price
	 * @param text
	 * @param color
	 */
	public void addFlagLine(float price, String text, int color) {
		this.recordManager.create(FlagLine.TYPE + "|" + price + "|" + text + "|"
				+ color);
		view.rePaint();
	}

	/**
	 * 删除标志线
	 * 
	 * @param index
	 */
	public void delFlagLine(int index) {
		this.recordManager.dispose(FlagLine.TYPE, index);
		view.rePaint();
	}

	/**
	 * 添加MV（移动平均线）
	 * 
	 * @param boll
	 *            为true时表示是布林带
	 */
	public void addMALine() {
		Object ret = this.recordManager.getInstance(MALine.TYPE, 0);
		if (ret == null) {
			this.recordManager.create(MALine.TYPE);
		}
		view.rePaint();
	}

	/**
	 * 删除MV 只有一条
	 */
	public void delMALine() {
		this.recordManager.dispose(MALine.TYPE, 0);
		view.rePaint();
	}

	/**
	 * 添加BOLL（布林带）
	 * 
	 * @param boll
	 *            为true时表示是布林带
	 */
	public void addBollLine() {
		Object ret = this.recordManager.getInstance(BollLine.TYPE, 0);
		if (ret == null) {
			this.recordManager.create(BollLine.TYPE);
		}
		view.rePaint();
	}

	/**
	 * 删除MV或BOLL 只有一条
	 */
	public void delBollLine() {
		this.recordManager.dispose(BollLine.TYPE, 0);
		view.rePaint();
	}
	
	/**
	 * 添加MACD窗口
	 */
	public void addMACDWindow() {
		// 只能添加一个
		Object ret = this.recordManager.getInstance(MACDWindow.TYPE, 0);
		if (ret == null) {
			this.recordManager.create(MACDWindow.TYPE);
			view.rePaint();
		}
	}

	/**
	 * 删除MACD窗口
	 */
	public void delMACDWindow() {
		this.recordManager.dispose(MACDWindow.TYPE, 0);
		view.rePaint();
	}

	/**
	 * 添加辅助线
	 */
	public void addAssistLine() {
		this.recordManager.create(AssistLine.TYPE + "|50|50|150|150");
		view.rePaint();
	}

	/**
	 * 删除辅助线
	 * 
	 * @param index
	 */
	public void delAssistLine(int index) {
		this.recordManager.dispose(AssistLine.TYPE, index);
		view.rePaint();
	}
	
	/**
	 * 删除所有辅助线
	 */
	public void delAssistLine(){
		int size=lineSize(AssistLine.TYPE);
		for(int i=0;i<size;i++){
			this.recordManager.dispose(AssistLine.TYPE, 0);
		}
		view.rePaint();
	}
	
	/**
	 * 删除黄金分割线
	 */
	public void delFibonacciLine(){
		int size=lineSize(FibonacciLine.TYPE);
		for(int i=0;i<size;i++){
			this.recordManager.dispose(FibonacciLine.TYPE, 0);
		}
		view.rePaint();
	}

	/**
	 * 添加黄金分割线
	 */
	public void addFibonacciLine() {
		this.recordManager.create(FibonacciLine.TYPE + "|50|50|150|150");
		view.rePaint();
	}

	/**
	 * 删除黄金分割线
	 * 
	 * @param index
	 */
	public void delFibonacciLine(int index) {
		this.recordManager.dispose(FibonacciLine.TYPE, index);
		view.rePaint();
	}

	/**
	 * 添加十字线
	 */
	public void addCrossLine() {
		
		Object ret = this.recordManager.getInstance(CrossLinePainter.TYPE, 0);
		if (ret == null) {
			float x = kLine.getpWidth() / 2;
			float y = kLine.getpHeight() / 2;
			this.recordManager.create(CrossLinePainter.TYPE + "|" + x + "|" + y);
			view.rePaint();
		}
		
	}

	/**
	 * 删除十字线
	 */
	public void delCrossLine() {
		this.recordManager.dispose(CrossLinePainter.TYPE, 0);
		view.rePaint();
	}
	
}
