package com.example.mybreakout;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;

public class ButtonMusic {
	private Bitmap bmpMusic;
	private Bitmap bmpNoMusic;
	private boolean isPressMusic;
	static boolean musicOn;

	private int mx,my;
	
	public ButtonMusic(Bitmap bmpMusic, Bitmap bmpNoMusic){
		
		this.bmpMusic = bmpMusic;
		this.bmpNoMusic = bmpNoMusic;

		mx = MySurfaceView.screenW - bmpMusic.getWidth() - 10;
		my = 10;

		isPressMusic = false;
	}
	
	public void draw(Canvas canvas, Paint paint) {
		
		if (isPressMusic){
			canvas.drawBitmap(bmpNoMusic, mx, my, paint);
		}else {
			canvas.drawBitmap(bmpMusic, mx, my, paint);
		}
		
	}

	//背景触屏事件函数，主要用于处理按钮事件
	public void onTouchEvent(MotionEvent event) {
		//获取用户当前触屏位置
		int pointX = (int) event.getX();
		int pointY = (int) event.getY();
		//当用户是按下动作或移动动作
		if (event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_MOVE) {
			//判定用户是否点击了按钮
			if (pointX > mx && pointX < mx + bmpMusic.getWidth() && isPressMusic == false) {
				if (pointY > my && pointY < my + bmpMusic.getHeight() ) {
					isPressMusic = true;
				} else {
					isPressMusic = false;
				}
			}else if (pointX > mx && pointX < mx + bmpMusic.getWidth() && isPressMusic == true) {
				if (pointY > my && pointY < my + bmpMusic.getHeight() ) {
					isPressMusic = false;
				} else {
					isPressMusic = true;
				}
			}
		}else if (event.getAction() == MotionEvent.ACTION_UP) {
	    		//抬起判断是否点击按钮，防止用户移动到别处
			if (pointX > mx && pointX < mx + bmpMusic.getWidth()) {
					if (pointY > my && pointY < my + bmpMusic.getHeight() && isPressMusic == true) {
						//还原Button状态为未按下状态
						isPressMusic = true;					
					}
			}else if (pointX > mx && pointX < mx + bmpMusic.getWidth() && isPressMusic == false) {
				if (pointY > my && pointY < my + bmpMusic.getHeight() ) {
					isPressMusic = false;
				}
			}
		  
		}
	}

	public void MusicOn(){
		if (isPressMusic == true){
			musicOn = false;
		}else if (isPressMusic == false){
			musicOn = true;
		}
	}
}
