package com.example.mybreakout;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.MotionEvent;

public class GameWin {
	private Bitmap bmpGameWin;
	private Bitmap bmpBack;//����level select
	private Bitmap bmpReplay;//���¿�ʼ
	private Bitmap bmpNext;

	private int wx, wy;
	private int kx, ky;
	private int rx, ry;
	private int nx, ny;
	
	//��ť�Ƿ��±�ʶλ
	private Boolean isPressBack;
	private Boolean isPressReplay;
	private Boolean isPressNext;
	
	//�˵���ʼ��
	public GameWin(Bitmap bmpGameWin, Bitmap bmpBack, Bitmap bmpReplay, Bitmap bmpNext) {
		this.bmpGameWin = bmpGameWin;
		this.bmpBack = bmpBack;
		this.bmpReplay = bmpReplay;
		this.bmpNext = bmpNext;
		//X���У�Y������Ļ�ײ�
		wx = MySurfaceView.screenW / 2 - bmpGameWin.getWidth() / 2;
		wy = bmpGameWin.getHeight()*2;
		rx = MySurfaceView.screenW/2 - bmpReplay.getWidth()/2;
		ry = MySurfaceView.screenH/2 + bmpReplay.getHeight();
		nx = MySurfaceView.screenW - bmpNext.getWidth() /3*5;
		ny = MySurfaceView.screenH/2 + bmpReplay.getHeight();
		kx = bmpBack.getWidth()/3*2;
		ky = MySurfaceView.screenH/2 + bmpReplay.getHeight();
		
		
		isPressBack = false;
		isPressReplay = false;
		isPressNext = false;
	
		}	
	
	//�˵���ͼ����
	public void draw(Canvas canvas, Paint paint) {
	 
		canvas.drawBitmap(bmpGameWin, wx, wy, paint);
		canvas.drawBitmap(bmpBack, kx, ky, paint);
		canvas.drawBitmap(bmpReplay, rx, ry, paint);
		canvas.drawBitmap(bmpNext, nx, ny, paint);
		
		//paint.setTextSize(30);
		//paint.setTypeface(Typeface.DEFAULT_BOLD); 
		//paint.setColor(Color.GRAY);
		//canvas.drawText("You got: "+Brick.score+" seeds!", 120 , MySurfaceView.screenH/2 + 20, paint);
		
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
			} else if(pointX > nx && pointX < nx + bmpReplay.getWidth()) {
				if (pointY > ny && pointY < ny + bmpReplay.getHeight()) {
					isPressNext = true;
				} else {
					isPressNext = false;
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
		    	} else if(pointX > nx && pointX < nx + bmpReplay.getWidth()) {
					if (pointY > ny && pointY < ny + bmpReplay.getHeight()) {
						//��ԭButton״̬Ϊδ����״̬
						isPressReplay = false;
						if(ButtonMusic.musicOn){ 
							MySurfaceView.playSound(MySurfaceView.BUTTON, 0);
						}
						//�ı䵱ǰ��Ϸ״̬Ϊ��һ�� 
						MySurfaceView.gameState = MySurfaceView.GAME_TARGET2;
						MySurfaceView.resetGame();
					}
		    	}
		  
		}
	}

}
