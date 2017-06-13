package com.example.mybreakout;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

public class Background2 {
	private Bitmap bmpBackground2;
	private Bitmap bmpLevel2_2;
	
	public Background2(Bitmap bmpBackground2, Bitmap bmpLevel2_2){
		this.bmpBackground2 = bmpBackground2;
		this.bmpLevel2_2 = bmpLevel2_2;
		
	}
	
	public void draw(Canvas canvas, Paint paint) {
		canvas.drawBitmap(bmpBackground2, MySurfaceView.screenW/2 - bmpBackground2.getWidth()/2, MySurfaceView.screenH/2 - bmpBackground2.getHeight()/2, paint);//±³¾°»æÍ¼
		canvas.drawBitmap(bmpLevel2_2, MySurfaceView.screenW/2 - bmpLevel2_2.getWidth()/2, 5, paint);//¹Ø¿¨±êÖ¾»æÍ¼
		
	}

}
