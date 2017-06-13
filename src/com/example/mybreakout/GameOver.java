package com.example.mybreakout;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.MotionEvent;

public class GameOver {
	private Bitmap bmpGameOver;
	private Bitmap bmpBack;//����level select
	private Bitmap bmpReplay;//���¿�ʼ

	private int ox, oy;
	private int kx, ky;
	private int rx, ry;
	private int lost;
	
	//��ť�Ƿ��±�ʶλ
	private Boolean isPressBack;
	private Boolean isPressReplay;
		
	//�˵���ʼ��
	public GameOver(Bitmap bmpGameOver, Bitmap bmpBack, Bitmap bmpReplay) {
		this.bmpGameOver = bmpGameOver;
		this.bmpBack = bmpBack;
		this.bmpReplay = bmpReplay;
		
		ox = MySurfaceView.screenW/2 - bmpGameOver.getWidth()/2;
		oy = bmpGameOver.getHeight()*3;
		kx = MySurfaceView.screenW/2 - bmpBack.getWidth()/3*5;
		ky = MySurfaceView.screenH/2 + bmpBack.getHeight();
		rx = MySurfaceView.screenW/2 + bmpReplay.getWidth()/3*2;
		ry = MySurfaceView.screenH/2 + bmpBack.getHeight();
		
		isPressBack = false;
		isPressReplay = false;
	    lost = 3 - Ball.ballHp; 
		
		}	
	
	//�˵���ͼ����
	public void draw(Canvas canvas, Paint paint) {
	 
		canvas.drawBitmap(bmpGameOver, ox, oy, paint);
		canvas.drawBitmap(bmpBack, kx, ky, paint);
		canvas.drawBitmap(bmpReplay, rx, ry, paint);
		
		//paint.setTextSize(30);
		//paint.setTypeface(Typeface.DEFAULT_BOLD); 
		//paint.setColor(Color.GRAY);
		//canvas.drawText("You got: "+Brick.score+" seeds!", 120 , MySurfaceView.screenH/2, paint);
	}

	//�˵������¼���������Ҫ���ڴ���ť�¼�
	public void onTouchEvent(MotionEvent event) {
		//��ȡ�û���ǰ����λ��
		int pointX = (int) event.getX();
		int pointY = (int) event.getY();
		//���û��ǰ��¶������ƶ�����
		if (event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_MOVE) {
			//�ж��û��Ƿ����˰�ť
			if (pointX > kx && pointX < kx + bmpBack.getWidth()) {
				if (pointY > ky && pointY < ky + bmpBack.getHeight()) {
					isPressBack = true;
				} else {
					isPressBack = false;
				}
			} else if (pointX > rx && pointX < rx + bmpReplay.getWidth()) {
				if (pointY > ry && pointY < ry + bmpReplay.getHeight()) {
					isPressReplay = true;
				} else {
					isPressReplay = false;
				}
			}
		}else if (event.getAction() == MotionEvent.ACTION_UP) {
	    		//̧���ж��Ƿ�����ť����ֹ�û��ƶ�����
	    		if (pointX > kx && pointX < kx + bmpBack.getWidth()) {
					if (pointY > ky && pointY < ky + bmpBack.getHeight()) {
						//��ԭButton״̬Ϊδ����״̬
						isPressBack = false;
						if(ButtonMusic.musicOn){ 
							MySurfaceView.playSound(MySurfaceView.BUTTON, 0);
						}
						//�ı䵱ǰ��Ϸ״̬Ϊ�ؿ�ѡ��
						MySurfaceView.gameState = MySurfaceView.GAME_LEVEL;
						MySurfaceView.resetGame();
					}
				} else if (pointX > rx && pointX < rx + bmpReplay.getWidth()) {
					if (pointY > ry && pointY < ry + bmpReplay.getHeight()) {
						//��ԭButton״̬Ϊδ����״̬
						isPressReplay = false;
						if(ButtonMusic.musicOn){ 
							MySurfaceView.playSound(MySurfaceView.BUTTON, 0);
						}
						//�ı䵱ǰ��Ϸ״̬Ϊ��һ��
						MySurfaceView.gameState = MySurfaceView.GAME_TARGET;
						MySurfaceView.resetGame();
						
					}
		    	} 
		  
		}
	}
}
