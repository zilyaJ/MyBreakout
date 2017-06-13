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
	
	//��ť�Ƿ��±�ʶλ
	private Boolean isPress1;
	private Boolean isPress2;
	private Boolean isPress3;
	private Boolean isPress4;
	
	//�˵���ʼ��
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
	
	//�˵���ͼ����
	public void draw(Canvas canvas, Paint paint) {	 
		canvas.drawBitmap(bmpLevel1, lx1, ly1, paint);
		canvas.drawBitmap(bmpLevel2, lx2, ly2, paint);
		canvas.drawBitmap(bmpLevel3, lx3, ly3, paint);
		canvas.drawBitmap(bmpHelp, lx4, ly4, paint);
	}

	//�˵������¼���������Ҫ���ڴ���ť�¼�
	public void onTouchEvent(MotionEvent event) {
		//��ȡ�û���ǰ����λ��
		int pointX = (int) event.getX();
		int pointY = (int) event.getY();
		//���û��ǰ��¶������ƶ�����
		if (event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_MOVE) {
			//�ж��û��Ƿ����˰�ť
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
	    		//̧���ж��Ƿ�����ť����ֹ�û��ƶ�����
	    		if  (pointX > lx1 && pointX < lx1 + bmpLevel1.getWidth()) {
					if (pointY > ly1 && pointY < ly1 + bmpLevel1.getHeight()) {
						//��ԭButton״̬Ϊδ����״̬
						isPress1 = false;
						if(ButtonMusic.musicOn){ 
							MySurfaceView.playSound(MySurfaceView.BUTTON, 0);
						}
						//�ı䵱ǰ��Ϸ״̬Ϊ��һ��
						MySurfaceView.gameState = MySurfaceView.GAME_TARGET;
					}else if (pointY > ly3 && pointY < ly3 + bmpLevel3.getHeight()) {
						//��ԭButton״̬Ϊδ����״̬
						isPress3 = false;
						if(ButtonMusic.musicOn){ 
							MySurfaceView.playSound(MySurfaceView.BUTTON, 0);
						}
						//�ı䵱ǰ��Ϸ״̬Ϊ������
						MySurfaceView.gameState = MySurfaceView.GAME_TARGET3;
					}
				} else if (pointX > lx2 && pointX < lx2 + bmpLevel2.getWidth()) {
					if (pointY > ly2 && pointY < ly2 + bmpLevel2.getHeight()) {
						//��ԭButton״̬Ϊδ����״̬
						isPress2 = false;
						if(ButtonMusic.musicOn){ 
							MySurfaceView.playSound(MySurfaceView.BUTTON, 0);
						}
						//�ı䵱ǰ��Ϸ״̬Ϊ�ڶ���
						MySurfaceView.gameState = MySurfaceView.GAME_TARGET2;
					}else if (pointY > ly4 && pointY < ly4 + bmpHelp.getHeight()) {
						//��ԭButton״̬Ϊδ����״̬
						isPress4 = false;
						if(ButtonMusic.musicOn){ 
							MySurfaceView.playSound(MySurfaceView.BUTTON, 0);
						}
						//�ı䵱ǰ��Ϸ״̬Ϊ�ڶ���  ��δ���
						MySurfaceView.gameState = MySurfaceView.GAME_HELP;
					}
		    	}		  
		}
	}
}
