
package com.kfd.activityfour;

import android.content.Context;
import android.widget.TextView;

import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.CandleEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.Utils;


/**
 * Custom implementation of the MarkerView.
 * 
 * @author Philipp Jahoda
 */
public class MyMarkerView  extends MarkerView {

    private TextView tvContent;

    public MyMarkerView(Context context, int layoutResource) {
        super(context, layoutResource);

        tvContent = (TextView) findViewById(R.id.tvContent);
    }


	@Override
	public int getXOffset()
	{
		return -this.getMeasuredWidth() / 2;
	}

	@Override
	public int getYOffset()
	{
		return -this.getMeasuredHeight();
	}

	@Override
	public void refreshContent(Entry arg0, Highlight arg1)
	{

        if (arg0 instanceof CandleEntry) {

            CandleEntry ce = (CandleEntry) arg0;

            tvContent.setText("" + Utils.formatNumber(ce.getHigh(), 0, true));
        } else {

//            tvContent.setText("" + Utils.formatNumber(e.getVal(), 0, true));
        	tvContent.setText("" +arg0.getVal());
        }		
	}
}
