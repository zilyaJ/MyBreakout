package com.example.mybreakout;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.MotionEvent;

public class GameWin {
	private Bitmap bmpGameWin;
	private Bitmap bmpBack;//返回level select
	private Bitmap bmpReplay;//重新开始
	private Bitmap bmpNext;

	private int wx, wy;
	private int kx, ky;
	private int rx, ry;
	private int nx, ny;
	
	//按钮是否按下标识位
	private Boolean isPressBack;
	private Boolean isPressReplay;
	private Boolean isPressNext;
	
	//菜单初始化
	public GameWin(Bitmap bmpGameWin, Bitmap bmpBack, Bitmap bmpReplay, Bitmap bmpNext) {
		this.bmpGameWin = bmpGameWin;
		this.bmpBack = bmpBack;
		this.bmpReplay = bmpReplay;
		this.bmpNext = bmpNext;
		//X居中，Y紧接屏幕底部
		wx = MySurfaceView.screenW / 2 - bmpGameWin.getWidth() / 2;
		wy = bmpGameWin.getHeight()*2;
		rx = MySurfaceView.screenW/2 - bmpReplay.getWidth()/2;
		ry = MySurfaceView.screenH/2 + bmpReplay.getHeight();
		nx = MySurfaceView.screenW - bmpNext.getWidth() /3*5;
		ny = MySurfaceView.screenH/2 + bmpReplay.getHeight();
		kx = bmpBack.getWidth()/3*2;
		ky = MySurfaceView.screenH/2 + bmpReplay.getHeight();
		
		
		isPressBack = false;
		isPressReplay = false;
		isPressNext = false;
	
		}	
	
	//菜单绘图函数
	public void draw(Canvas canvas, Paint paint) {
	 
		canvas.drawBitmap(bmpGameWin, wx, wy, paint);
		canvas.drawBitmap(bmpBack, kx, ky, paint);
		canvas.drawBitmap(bmpReplay, rx, ry, paint);
		canvas.drawBitmap(bmpNext, nx, ny, paint);
		
		//paint.setTextSize(30);
		//paint.setTypeface(Typeface.DEFAULT_BOLD); 
		//paint.setColor(Color.GRAY);
		//canvas.drawText("You got: "+Brick.score+" seeds!", 120 , MySurfaceView.screenH/2 + 20, paint);
		
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
			} else if(pointX > nx && pointX < nx + bmpReplay.getWidth()) {
				if (pointY > ny && pointY < ny + bmpReplay.getHeight()) {
					isPressNext = true;
				} else {
					isPressNext = false;
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
		    	} else if(pointX > nx && pointX < nx + bmpReplay.getWidth()) {
					if (pointY > ny && pointY < ny + bmpReplay.getHeight()) {
						//还原Button状态为未按下状态
						isPressReplay = false;
						if(ButtonMusic.musicOn){ 
							MySurfaceView.playSound(MySurfaceView.BUTTON, 0);
						}
						//改变当前游戏状态为下一关 
						MySurfaceView.gameState = MySurfaceView.GAME_TARGET2;
						MySurfaceView.resetGame();
					}
		    	}
		  
		}
	}

}
