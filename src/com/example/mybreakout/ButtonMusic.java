package com.example.mybreakout;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;

public class ButtonMusic {
	private Bitmap bmpMusic;
	private Bitmap bmpNoMusic;
	private boolean isPressMusic;
	static boolean musicOn;

	private int mx,my;
	
	public ButtonMusic(Bitmap bmpMusic, Bitmap bmpNoMusic){
		
		this.bmpMusic = bmpMusic;
		this.bmpNoMusic = bmpNoMusic;

		mx = MySurfaceView.screenW - bmpMusic.getWidth() - 10;
		my = 10;

		isPressMusic = false;
	}
	
	public void draw(Canvas canvas, Paint paint) {
		
		if (isPressMusic){
			canvas.drawBitmap(bmpNoMusic, mx, my, paint);
		}else {
			canvas.drawBitmap(bmpMusic, mx, my, paint);
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
			if (pointX > mx && pointX < mx + bmpMusic.getWidth() && isPressMusic == false) {
				if (pointY > my && pointY < my + bmpMusic.getHeight() ) {
					isPressMusic = true;
				} else {
					isPressMusic = false;
				}
			}else if (pointX > mx && pointX < mx + bmpMusic.getWidth() && isPressMusic == true) {
				if (pointY > my && pointY < my + bmpMusic.getHeight() ) {
					isPressMusic = false;
				} else {
					isPressMusic = true;
				}
			}
		}else if (event.getAction() == MotionEvent.ACTION_UP) {
	    		//̧���ж��Ƿ�����ť����ֹ�û��ƶ�����
			if (pointX > mx && pointX < mx + bmpMusic.getWidth()) {
					if (pointY > my && pointY < my + bmpMusic.getHeight() && isPressMusic == true) {
						//��ԭButton״̬Ϊδ����״̬
						isPressMusic = true;					
					}
			}else if (pointX > mx && pointX < mx + bmpMusic.getWidth() && isPressMusic == false) {
				if (pointY > my && pointY < my + bmpMusic.getHeight() ) {
					isPressMusic = false;
				}
			}
		  
		}
	}

	public void MusicOn(){
		if (isPressMusic == true){
			musicOn = false;
		}else if (isPressMusic == false){
			musicOn = true;
		}
	}
}
