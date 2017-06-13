package com.example.mybreakout;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;

public class LevelSelect {
	
	private Bitmap bmpLevel1;
	private Bitmap bmpLevel2;
	private Bitmap bmpLevel3;
	private Bitmap bmpHelp;

	private int lx1, ly1;
	private int lx2, ly2;
	private int lx3, ly3;
	private int lx4, ly4;
	
	//按钮是否按下标识位
	private Boolean isPress1;
	private Boolean isPress2;
	private Boolean isPress3;
	private Boolean isPress4;
	
	//菜单初始化
	public LevelSelect(Bitmap bmpLevel1, Bitmap bmpLevel2, Bitmap bmpLevel3, Bitmap bmpHelp) {
		this.bmpLevel1 = bmpLevel1;
		this.bmpLevel2 = bmpLevel2;
		this.bmpLevel3 = bmpLevel3;
		this.bmpHelp = bmpHelp;
		
		lx1 = bmpLevel1.getWidth() / 4;
		ly1 = MySurfaceView.screenH/2 - bmpLevel2.getHeight()/5 - bmpLevel2.getHeight();
		lx2 = MySurfaceView.screenW - bmpLevel2.getWidth() - bmpLevel2.getWidth() / 4;
		ly2 = MySurfaceView.screenH/2 - bmpLevel2.getHeight()/5 - bmpLevel2.getHeight();
		lx3 = bmpLevel3.getWidth() / 4;
		ly3 = MySurfaceView.screenH/2 + bmpLevel3.getHeight()/5;
		lx4 = MySurfaceView.screenW - bmpHelp.getWidth() - bmpHelp.getWidth() / 4;
		ly4 = MySurfaceView.screenH/2 + bmpHelp.getHeight()/5;
		isPress1 = false;
		isPress2 = false;
		isPress3 = false;
		isPress4 = false;
		}	
	
	//菜单绘图函数
	public void draw(Canvas canvas, Paint paint) {	 
		canvas.drawBitmap(bmpLevel1, lx1, ly1, paint);
		canvas.drawBitmap(bmpLevel2, lx2, ly2, paint);
		canvas.drawBitmap(bmpLevel3, lx3, ly3, paint);
		canvas.drawBitmap(bmpHelp, lx4, ly4, paint);
	}

	//菜单触屏事件函数，主要用于处理按钮事件
	public void onTouchEvent(MotionEvent event) {
		//获取用户当前触屏位置
		int pointX = (int) event.getX();
		int pointY = (int) event.getY();
		//当用户是按下动作或移动动作
		if (event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_MOVE) {
			//判定用户是否点击了按钮
			if (pointX > lx1 && pointX < lx1 + bmpLevel1.getWidth()) {
				if (pointY > ly1 && pointY < ly1 + bmpLevel1.getHeight()) {
					isPress1 = true;
				} else if (pointY > ly3 && pointY < ly3 + bmpLevel3.getHeight()) {
					isPress3 = true;
				} 
			} else if (pointX > lx2 && pointX < lx2 + bmpLevel2.getWidth()) {
				if (pointY > ly2 && pointY < ly2 + bmpLevel2.getHeight()) {
					isPress2 = true;
				} else if (pointY > ly4 && pointY < ly4 + bmpHelp.getHeight()) {
					isPress4 = true;
				}
			} 
		}else if (event.getAction() == MotionEvent.ACTION_UP) {
	    		//抬起判断是否点击按钮，防止用户移动到别处
	    		if  (pointX > lx1 && pointX < lx1 + bmpLevel1.getWidth()) {
					if (pointY > ly1 && pointY < ly1 + bmpLevel1.getHeight()) {
						//还原Button状态为未按下状态
						isPress1 = false;
						if(ButtonMusic.musicOn){ 
							MySurfaceView.playSound(MySurfaceView.BUTTON, 0);
						}
						//改变当前游戏状态为第一关
						MySurfaceView.gameState = MySurfaceView.GAME_TARGET;
					}else if (pointY > ly3 && pointY < ly3 + bmpLevel3.getHeight()) {
						//还原Button状态为未按下状态
						isPress3 = false;
						if(ButtonMusic.musicOn){ 
							MySurfaceView.playSound(MySurfaceView.BUTTON, 0);
						}
						//改变当前游戏状态为第三关
						MySurfaceView.gameState = MySurfaceView.GAME_TARGET3;
					}
				} else if (pointX > lx2 && pointX < lx2 + bmpLevel2.getWidth()) {
					if (pointY > ly2 && pointY < ly2 + bmpLevel2.getHeight()) {
						//还原Button状态为未按下状态
						isPress2 = false;
						if(ButtonMusic.musicOn){ 
							MySurfaceView.playSound(MySurfaceView.BUTTON, 0);
						}
						//改变当前游戏状态为第二关
						MySurfaceView.gameState = MySurfaceView.GAME_TARGET2;
					}else if (pointY > ly4 && pointY < ly4 + bmpHelp.getHeight()) {
						//还原Button状态为未按下状态
						isPress4 = false;
						if(ButtonMusic.musicOn){ 
							MySurfaceView.playSound(MySurfaceView.BUTTON, 0);
						}
						//改变当前游戏状态为第二关  还未完成
						MySurfaceView.gameState = MySurfaceView.GAME_HELP;
					}
		    	}		  
		}
	}
}
