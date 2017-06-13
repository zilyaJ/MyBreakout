package com.example.mybreakout;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.MotionEvent;

public class GameOver {
	private Bitmap bmpGameOver;
	private Bitmap bmpBack;//返回level select
	private Bitmap bmpReplay;//重新开始

	private int ox, oy;
	private int kx, ky;
	private int rx, ry;
	private int lost;
	
	//按钮是否按下标识位
	private Boolean isPressBack;
	private Boolean isPressReplay;
		
	//菜单初始化
	public GameOver(Bitmap bmpGameOver, Bitmap bmpBack, Bitmap bmpReplay) {
		this.bmpGameOver = bmpGameOver;
		this.bmpBack = bmpBack;
		this.bmpReplay = bmpReplay;
		
		ox = MySurfaceView.screenW/2 - bmpGameOver.getWidth()/2;
		oy = bmpGameOver.getHeight()*3;
		kx = MySurfaceView.screenW/2 - bmpBack.getWidth()/3*5;
		ky = MySurfaceView.screenH/2 + bmpBack.getHeight();
		rx = MySurfaceView.screenW/2 + bmpReplay.getWidth()/3*2;
		ry = MySurfaceView.screenH/2 + bmpBack.getHeight();
		
		isPressBack = false;
		isPressReplay = false;
	    lost = 3 - Ball.ballHp; 
		
		}	
	
	//菜单绘图函数
	public void draw(Canvas canvas, Paint paint) {
	 
		canvas.drawBitmap(bmpGameOver, ox, oy, paint);
		canvas.drawBitmap(bmpBack, kx, ky, paint);
		canvas.drawBitmap(bmpReplay, rx, ry, paint);
		
		//paint.setTextSize(30);
		//paint.setTypeface(Typeface.DEFAULT_BOLD); 
		//paint.setColor(Color.GRAY);
		//canvas.drawText("You got: "+Brick.score+" seeds!", 120 , MySurfaceView.screenH/2, paint);
	}

	//菜单触屏事件函数，主要用于处理按钮事件
	public void onTouchEvent(MotionEvent event) {
		//获取用户当前触屏位置
		int pointX = (int) event.getX();
		int pointY = (int) event.getY();
		//当用户是按下动作或移动动作
		if (event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_MOVE) {
			//判定用户是否点击了按钮
			if (pointX > kx && pointX < kx + bmpBack.getWidth()) {
				if (pointY > ky && pointY < ky + bmpBack.getHeight()) {
					isPressBack = true;
				} else {
					isPressBack = false;
				}
			} else if (pointX > rx && pointX < rx + bmpReplay.getWidth()) {
				if (pointY > ry && pointY < ry + bmpReplay.getHeight()) {
					isPressReplay = true;
				} else {
					isPressReplay = false;
				}
			}
		}else if (event.getAction() == MotionEvent.ACTION_UP) {
	    		//抬起判断是否点击按钮，防止用户移动到别处
	    		if (pointX > kx && pointX < kx + bmpBack.getWidth()) {
					if (pointY > ky && pointY < ky + bmpBack.getHeight()) {
						//还原Button状态为未按下状态
						isPressBack = false;
						if(ButtonMusic.musicOn){ 
							MySurfaceView.playSound(MySurfaceView.BUTTON, 0);
						}
						//改变当前游戏状态为关卡选择
						MySurfaceView.gameState = MySurfaceView.GAME_LEVEL;
						MySurfaceView.resetGame();
					}
				} else if (pointX > rx && pointX < rx + bmpReplay.getWidth()) {
					if (pointY > ry && pointY < ry + bmpReplay.getHeight()) {
						//还原Button状态为未按下状态
						isPressReplay = false;
						if(ButtonMusic.musicOn){ 
							MySurfaceView.playSound(MySurfaceView.BUTTON, 0);
						}
						//改变当前游戏状态为第一关
						MySurfaceView.gameState = MySurfaceView.GAME_TARGET;
						MySurfaceView.resetGame();
						
					}
		    	} 
		  
		}
	}
}
