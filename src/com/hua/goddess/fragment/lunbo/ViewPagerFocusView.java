package com.hua.goddess.fragment.lunbo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import com.hua.goddess.R;

public class ViewPagerFocusView extends View{
	
	private int count;					//圆点的总数量
	private int currentIndex = 0;		//当前焦点的位置从0开始
	private int interval = 15;			//圆点与圆点之间的间隔
	private Bitmap losesFocus;			//没有获得焦点的圆点
	private Bitmap getFocus;			//获得焦点的圆点
	private Paint paint;
	private String title = "";				//标题
	private int textPaddingTop = 20;
	
	private int textPaddingFont; // 文字间距
	
	private Context context;
	
	private int paddingLeftSize;
	private int paddingRightSize;

	public ViewPagerFocusView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.context = context;
		initPaddingSize();
	}

	public ViewPagerFocusView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		initPaddingSize();
	}

	public ViewPagerFocusView(Context context) {
		super(context);
		this.context = context;
		initPaddingSize();
	}
	
	private void initPaddingSize(){
		paddingLeftSize = context.getResources().getDimensionPixelSize(R.dimen.viewpager_focusview_paddingleft_size);
		paddingRightSize = context.getResources().getDimensionPixelSize(R.dimen.viewpager_focusview_paddingright_size);
		textPaddingFont = context.getResources().getDimensionPixelSize(R.dimen.viewpager_focusview_text_padding_font);
	}
	
	/**
	 * 图片的最多数量
	 * @param count
	 */
	public void setCount(int count){
		this.count = count;
		if(count >= 0){
			losesFocus = BitmapFactory.decodeResource(getResources(), R.drawable.page_indicator);
			getFocus = BitmapFactory.decodeResource(getResources(), R.drawable.page_indicator_focused);
			paint = new Paint(Paint.ANTI_ALIAS_FLAG);
			paint.setColor(Color.WHITE);
//			paint.setTextAlign(Align.CENTER);
			paint.setTextAlign(Align.LEFT);
			paint.setTextSize((int)context.getResources().getDimensionPixelSize(R.dimen.lunbo_fontsize));
		}
	}
	public void setCurrentIndex(int currentIndex){
		this.currentIndex = currentIndex;
		invalidate();
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		drawRound(canvas);
	}
	
	/**
	 * 设置标题
	 * @param title
	 */
	public void setTitle(String title){
		this.title = title;
	}
	
	private String getText(int width){
		Rect rect = new Rect(); 

		//返回包围整个字符串的最小的一个Rect区域 
		paint.getTextBounds("中", 0, 1, rect); //测量一个中文字体的宽度
		int maxLength = width/(rect.width()+2);
		textPaddingTop = (getHeight() - rect.height())/2 + rect.height();
		if(!"".equals(title) && title.length() > maxLength){
			return title.substring(0, maxLength-2);
		}else{
			return title;
		}
	}
	
	private void drawRound(Canvas canvas) {
		if(losesFocus == null)return;
		
		int height = getHeight();
		int ratioPaddingTop = height/2 - losesFocus.getHeight() /2;
		
		int start = (getWidth() - (count+1)*interval - losesFocus.getWidth()*count - paddingRightSize);//居右时的起始坐标
		
//		int focusWidth = (count+1)*interval + losesFocus.getWidth()*count;//所有小点，加间隙的总长度
//		int start = getWidth()/2 - focusWidth/2;//居中时起始坐标
		
		for (int i = 0; i < count; i++) {
			int left = (start+ i*interval+interval + i*losesFocus.getWidth());
			if(i == 0 && !isEmpty(title)){
				canvas.drawText(getText(left-textPaddingFont*2), paddingLeftSize, textPaddingTop, paint);
			}
			if(i == currentIndex){
				canvas.drawBitmap(getFocus, left, ratioPaddingTop, paint);
			}else{
				canvas.drawBitmap(losesFocus, left, ratioPaddingTop, paint);
			}
		}
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int measuredHeight = 30+(losesFocus!=null?losesFocus.getWidth():0);   
		setMeasuredDimension(widthMeasureSpec, measuredHeight);   
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}
	
	private boolean isEmpty(String url) {
		if (url == null || "".equals(url) || "null".equals(url)) {
			return true;
		}
		return false;
	}
}