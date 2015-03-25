package com.hua.goddess.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.TextView;

import com.hua.goddess.utils.BitmapTools;

public class ImageTextView extends TextView {
	private Bitmap bitmap;
	private String text;
	Drawable d;

	public ImageTextView(Context context) {
		super(context);
		text = this.getText().toString().substring(0, 1);
		bitmap = BitmapTools.getIndustry(context, text);
		d = BitmapTools.bitmapTodrawable(bitmap);
		this.setCompoundDrawables(d, null, null, null);
	}

	public ImageTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		text = this.getText().toString().substring(0, 1);
		bitmap = BitmapTools.getIndustry(context, text);
		d = BitmapTools.bitmapTodrawable(bitmap);
		this.setCompoundDrawables(d, null, null, null);
	}

	public ImageTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		text = this.getText().toString().substring(0, 1);
		bitmap = BitmapTools.getIndustry(context, text);
		d = BitmapTools.bitmapTodrawable(bitmap);
		this.setCompoundDrawables(d, null, null, null);
	}

}
