package com.example.mybreakout;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Path.Direction;

public class Bullet {
	//�ӵ�ͼƬ��Դ
	public Bitmap bmpBullet;
	//�ӵ�������
	public int bulletX1, bulletX2;
	public int bulletY;
	//�ӵ����ٶ�
	public int speed = 10;
	//�ӵ��Ƿ����� �Ż�����
	public boolean isDead;
	

	
	public Bullet(Bitmap bmpBullet, int bulletX1, int bulletY) {
		this.bmpBullet = bmpBullet;
		this.bulletX1 = bulletX1;
		this.bulletX2 = bulletX2;
		this.bulletY = bulletY;
		speed = 10;
		
	}

	//�ӵ��Ļ���
	public void draw(Canvas canvas, Paint paint) {		
		canvas.drawBitmap(bmpBullet, bulletX1, bulletY, paint);
	}

	//�ӵ����߼�
	public void logic() {		
			bulletY -= 5;	
			if (bulletY < -50) {
				isDead = true;
			}
			
		
	}
}
