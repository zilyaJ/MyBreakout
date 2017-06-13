package com.example.mybreakout;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;

public class GameMenu {
	//�˵�����ͼ
	private Bitmap bmpMenu;
	//��ťͼƬ��Դ(���º�δ����ͼ)
	private Bitmap bmpPlay, bmpPlayPress;
	//��ť������
	private int btnX, btnY;
	//��ť�Ƿ��±�ʶλ
	private Boolean isPress;
		
	//�˵���ʼ��
	public GameMenu(Bitmap bmpMenu, Bitmap bmpPlay, Bitmap bmpPlayPress) {
		this.bmpMenu = bmpMenu;
		this.bmpPlay = bmpPlay;
		this.bmpPlayPress = bmpPlayPress;
		//X���У�Y������Ļ�ײ�
		btnX = MySurfaceView.screenW / 2 - bmpPlay.getWidth() / 2;
		btnY = MySurfaceView.screenH - bmpPlay.getHeight()*3;
		isPress = false;
		}	
	
	//�˵���ͼ����
	public void draw(Canvas canvas, Paint paint) {
	    //���Ʋ˵�����ͼ
		canvas.drawBitmap(bmpMenu, MySurfaceView.screenW / 2 - bmpMenu.getWidth() / 2, bmpMenu.getHeight()/2, paint);
		//����δ���°�ťͼ
		if (isPress) {//�����Ƿ��»��Ʋ�ͬ״̬�İ�ťͼ
			canvas.drawBitmap(bmpPlayPress, btnX, btnY, paint);
		} else {
			canvas.drawBitmap(bmpPlay, btnX, btnY, paint);
		}
	}

	//�˵������¼���������Ҫ���ڴ���ť�¼�
	public void onTouchEvent(MotionEvent event) {
		//��ȡ�û���ǰ����λ��
		int pointX = (int) event.getX();
		int pointY = (int) event.getY();
		//���û��ǰ��¶������ƶ�����
		if (event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_MOVE) {
			//�ж��û��Ƿ����˰�ť
			if (pointX > btnX && pointX < btnX + bmpPlay.getWidth()) {
				if (pointY > btnY && pointY < btnY + bmpPlay.getHeight()) {
					isPress = true;
				} else {
					isPress = false;
				}
			} else {
				isPress = false;
			}
			//���û���̧����
		} else if (event.getAction() == MotionEvent.ACTION_UP) {
			//̧���ж��Ƿ�����ť����ֹ�û��ƶ�����
			if (pointX > btnX && pointX < btnX + bmpPlay.getWidth()) {
				if (pointY > btnY && pointY < btnY + bmpPlay.getHeight()) {
					//��ԭButton״̬Ϊδ����״̬
					isPress = false;
					if(ButtonMusic.musicOn){ 
						MySurfaceView.playSound(MySurfaceView.BUTTON, 0);
					}
					//�ı䵱ǰ��Ϸ״̬Ϊ��ʼ��Ϸ
					MySurfaceView.gameState = MySurfaceView.GAME_LEVEL;
				}
			}
		}
	}
}
