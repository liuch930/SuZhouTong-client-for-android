package com.hua.goddess.fragment.lunbo;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;


/**
 * 轮播
 *
 */
public class MyViewPager extends ViewPager {
	
	private boolean isTimerNull = true;     	//是否启动了定时器
	Timer timer;								//计时器
	Context context;							//上下文
	private int startIndex;						//第一次设置的索引
	boolean flag = false;						//标记事件传递

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		super.requestDisallowInterceptTouchEvent(true);
		return true;
	}
	
	@Override  
    public boolean dispatchTouchEvent(MotionEvent ev) {  
        getParent().requestDisallowInterceptTouchEvent(true);//这句话的作用 告诉父view，我的单击事件我自行处理，不要阻碍我。    
        return super.dispatchTouchEvent(ev);  
    }  

	
	float x = 0;
	float y = 0;
	
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		super.onTouchEvent(ev);
		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			System.out.println("Action_DOWN");
			x = ev.getX();
			y = ev.getY();
			stopTimer();
			break;
		case MotionEvent.ACTION_MOVE:
			if(Math.abs(ev.getX() - x) > 5 || Math.abs(ev.getY() - y) > 5 ){
				flag = true;
			}
			return true;
		case MotionEvent.ACTION_UP:
			if(isTimerNull){
				startTimer();
			}
			for (int i = 0; i < getChildCount(); i++) {
				if(!flag){
					View v = getChildAt(i);
					if((Integer)(v.getTag()) == getCurrentItem()){
						if(v != null){
							v.performClick();
							break;
						}
					}
				}
			}
			flag = false;
			break;
		}
		return true;
	}

	public MyViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
	}

	public MyViewPager(Context context) {
		super(context);
		this.context = context;
	}
	
	@Override
	public void setCurrentItem(int item) {
		super.setCurrentItem(item);
		this.startIndex = item;
		postInvalidate();
	}
	
	/**
	 * 是否进行幻灯片播放（轮播）；
	 * @param isSlide
	 */
	public void isSlide(boolean isSlide){
		if(isSlide){
			startTimer();
		}
	}
	
	/**
	 * 启动线程进行播放幻灯片
	 */
	public void startTimer(){
		if(timer == null){
			timer = new Timer();
			isTimerNull = true;
		}
		timer.schedule(new Slide(), 3000, 4000);
	}
	
	/**
	 * 停止线程进行播放幻灯片
	 */
	public void stopTimer(){
		if(timer != null)
			timer.cancel();
		timer = null;
	}
	
	class Slide extends TimerTask{
		@Override
		public void run() {
			((Activity)context).runOnUiThread(new Runnable() {
				@Override
				public void run() {
					if(getChildCount() < 1)return;
					int currentIndex = MyViewPager.this.getCurrentItem();
					if (currentIndex + 1 >= Integer.MAX_VALUE) {
						MyViewPager.this.setCurrentItem(startIndex);
					} else {
						setCurrentItem(currentIndex + 1);
					}
				}
			});
		}
	}
}
