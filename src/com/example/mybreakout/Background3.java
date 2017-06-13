package com.example.mybreakout;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

public class Background3 {
	private Bitmap bmpBackground3;
	private Bitmap bmpLevel3_2;
	
	public Background3(Bitmap bmpBackground3, Bitmap bmpLevel3_2){
		this.bmpBackground3 = bmpBackground3;
		this.bmpLevel3_2 = bmpLevel3_2;
		
	}
	
	public void draw(Canvas canvas, Paint paint) {
		canvas.drawBitmap(bmpBackground3, MySurfaceView.screenW/2 - bmpBackground3.getWidth()/2, MySurfaceView.screenH/2 - bmpBackground3.getHeight()/2, paint);//±³¾°»æÍ¼
		canvas.drawBitmap(bmpLevel3_2, MySurfaceView.screenW/2 - bmpLevel3_2.getWidth()/2, 5, paint);//¹Ø¿¨±êÖ¾»æÍ¼
	}
}
