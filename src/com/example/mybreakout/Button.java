package com.example.mybreakout;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;

public class Button {
	private Bitmap bmpScore;
	static Bitmap bmpStop;
	static Bitmap bmpStopped;
	
	static boolean isPressStop;
	
	private int sx;
	private int sy;
	
	public Button(Bitmap bmpScore,Bitmap bmpStop, Bitmap bmpStopped){
		
		this.bmpScore = bmpScore;
		this.bmpStop = bmpStop;
		this.bmpStopped = bmpStopped;

		sx = MySurfaceView.screenW - bmpStop.getWidth()*2 - 20;
		sy = 10;

		isPressStop = false;
	}
	
	public void draw(Canvas canvas, Paint paint) {
		
		canvas.drawBitmap(bmpScore, 0, MySurfaceView.screenH - bmpScore.getHeight(),paint);//计分绘图
	
        if (isPressStop){
			canvas.drawBitmap(bmpStopped, sx, sy, paint);
		}else {
			canvas.drawBitmap(bmpStop, sx, sy, paint);
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
			if (pointX > sx && pointX < sx + bmpStop.getWidth()) {
				if (pointY > sy && pointY < sy + bmpStop.getHeight() && isPressStop == false) {
					isPressStop = true;
				} else {
					isPressStop = false;
				} 
			} else if (pointX > sx && pointX < sx + bmpStop.getWidth()) {
				if (pointY > sy && pointY < sy + bmpStop.getHeight() && isPressStop == true) {
					isPressStop = false;
				} else {
					isPressStop = true;
				} 
			}
		}else if (event.getAction() == MotionEvent.ACTION_UP) {
	    		//抬起判断是否点击按钮，防止用户移动到别处
			if (pointX > sx && pointX < sx + bmpStop.getWidth()) {
				if (pointY > sy && pointY < sy + bmpStop.getHeight() && isPressStop == true)  {
						//确实按下
						isPressStop = true;
				}
			} else if (pointX > sx && pointX < sx + bmpStop.getWidth()) {
				    if (pointY > sy && pointY < sy + bmpStop.getHeight() && isPressStop == false) {
					    isPressStop = false;					   
				}
			} 
		}
	}
	
	public void isMoving(){
		if (isPressStop == true){
			Ball.isMoving = false;
			Brick.isMoving = false;
			Brick2.isMoving = false;
			Brick3.isMoving = false;
		}else if (isPressStop == false && Ball.firstTouch == false){
			Ball.isMoving = true;
		    Brick.isMoving = true;
		    Brick2.isMoving = true;
		    Brick3.isMoving = true;
		}else if (isPressStop == false && Ball.firstTouch == true){
			Ball.isMoving = false;
			Brick.isMoving = false;
			Brick2.isMoving = false;
			Brick3.isMoving = false;
		}
	}

}
