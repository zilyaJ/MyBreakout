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
		
		canvas.drawBitmap(bmpScore, 0, MySurfaceView.screenH - bmpScore.getHeight(),paint);//�Ʒֻ�ͼ
	
        if (isPressStop){
			canvas.drawBitmap(bmpStopped, sx, sy, paint);
		}else {
			canvas.drawBitmap(bmpStop, sx, sy, paint);
		}
		
		
	}

	//���������¼���������Ҫ���ڴ���ť�¼�
	public void onTouchEvent(MotionEvent event) {
		//��ȡ�û���ǰ����λ��
		int pointX = (int) event.getX();
		int pointY = (int) event.getY();
		//���û��ǰ��¶������ƶ�����
		if (event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_MOVE) {
			//�ж��û��Ƿ����˰�ť
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
	    		//̧���ж��Ƿ�����ť����ֹ�û��ƶ�����
			if (pointX > sx && pointX < sx + bmpStop.getWidth()) {
				if (pointY > sy && pointY < sy + bmpStop.getHeight() && isPressStop == true)  {
						//ȷʵ����
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
