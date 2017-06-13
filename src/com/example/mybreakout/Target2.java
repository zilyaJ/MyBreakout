package com.example.mybreakout;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.MotionEvent;

public class Target2 {
	private Bitmap bmpTarget;
	private Bitmap bmpLevel2_2;
	int seed;
	private int x,y,sx,sy;
	
	public Target2(Bitmap bmpTarget, Bitmap bmpLevel2_2){
		this.bmpTarget = bmpTarget;
		this.bmpLevel2_2 = bmpLevel2_2;
		
		x = MySurfaceView.screenW/2 - bmpTarget.getWidth()/2;
		y = MySurfaceView.screenH/2 - bmpTarget.getHeight()/2;
		sx = MySurfaceView.screenW/2-25;
		sy = MySurfaceView.screenH/2 + bmpTarget.getHeight()/4-5;
		
		seed = 5;
			
	}
	
	public void draw(Canvas canvas, Paint paint) {
	    //绘制目标背景图
		canvas.drawBitmap(bmpTarget,x,y, paint);
		//数字
		paint.setTextSize(50);
		paint.setTypeface(Typeface.DEFAULT_BOLD); 
		paint.setColor(Color.WHITE);
		canvas.drawText(""+seed, sx,sy, paint);
		
		canvas.drawBitmap(bmpLevel2_2, MySurfaceView.screenW/2 - bmpLevel2_2.getWidth()/2, 5, paint);//关卡标志绘图	
	}

	 public boolean onTouchEvent(MotionEvent event) {
	        
		  //手指点击屏幕
		    if (event.getAction()== MotionEvent.ACTION_DOWN ){	
		    	if(ButtonMusic.musicOn){ 
					MySurfaceView.playSound(MySurfaceView.BUTTON, 0);
				}
				MySurfaceView.gameState = MySurfaceView.GAME_LEVEL2;			
		    }
			return false;
	    }	
	 
}
