package com.kfd.ui;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import com.kfd.activityfour.R;

public class MyDialog extends Dialog {

	Context context;

	public MyDialog(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		this.context = context;
	}

	public MyDialog(Context context, int theme) {
		super(context, theme);
		this.context = context;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.mydialog);
	}

}