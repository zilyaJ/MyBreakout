package com.example.mybreakout;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.BitmapDrawable;
import android.view.MotionEvent;

public class Background {
	
	private Bitmap bmpBackground;
	private Bitmap bmpLevel1_2;
	
	public Background(Bitmap bmpBackground, Bitmap bmpLevel1_2){
		this.bmpBackground = bmpBackground;
		this.bmpLevel1_2 = bmpLevel1_2;
		
	}
	
	public void draw(Canvas canvas, Paint paint) {
		canvas.drawBitmap(bmpBackground, 0, 0, paint);//±³¾°»æÍ¼
		canvas.drawBitmap(bmpLevel1_2, MySurfaceView.screenW/2 - bmpLevel1_2.getWidth()/2, 5, paint);//¹Ø¿¨±êÖ¾»æÍ¼		
	}
}
