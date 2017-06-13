package com.example.mybreakout;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Path.Direction;

public class Bullet {
	//子弹图片资源
	public Bitmap bmpBullet;
	//子弹的坐标
	public int bulletX1, bulletX2;
	public int bulletY;
	//子弹的速度
	public int speed = 10;
	//子弹是否超屏， 优化处理
	public boolean isDead;
	

	
	public Bullet(Bitmap bmpBullet, int bulletX1, int bulletY) {
		this.bmpBullet = bmpBullet;
		this.bulletX1 = bulletX1;
		this.bulletX2 = bulletX2;
		this.bulletY = bulletY;
		speed = 10;
		
	}

	//子弹的绘制
	public void draw(Canvas canvas, Paint paint) {		
		canvas.drawBitmap(bmpBullet, bulletX1, bulletY, paint);
	}

	//子弹的逻辑
	public void logic() {		
			bulletY -= 5;	
			if (bulletY < -50) {
				isDead = true;
			}
			
		
	}
}
