package com.example.mybreakout;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Typeface;
import android.graphics.Path.Direction;
import android.view.MotionEvent;

public class Brick3 {
	//ש������
		static int brick_width;//ÿ��ש��
		int brick_height;//ÿ��ש��
		boolean brick_exist[];//ש�Ƿ����
		boolean seed_exist[];
		static boolean bullet_exist;
		boolean bulletCollision;
		static boolean isMoving = true;;
		boolean win = false;
		int k ;// ��//��forѭ����ų�ʼ��
		int j ;// ��
		static int brick_left ;
		static int brick_right ;
		static int brick_top ;
		static int brick_bottom ;
	 
	    private static Bitmap bmpBrick1;
		private Bitmap bmpBrick2;
		private Bitmap bmpBrick3;
		private Bitmap bmpBrick4;
		private Bitmap bmpBrick5;
		private Bitmap bmpBrick6;
		private Bitmap bmpBrick7;
		private Bitmap bmpBrick8;
		private Bitmap bmpBrick9;
		private Bitmap bmpSeed;
		private Bitmap bmpSeed1;
		private Bitmap bmpBoom;

		int sx[];
		int sy[];
		
		static int score;

		//ש�Ĺ��캯��
		public Brick3(Bitmap bmpBrick1,Bitmap bmpBrick2,Bitmap bmpBrick3,Bitmap bmpBrick4,Bitmap bmpBrick5,Bitmap bmpBrick6,Bitmap bmpBrick7,Bitmap bmpBrick8,Bitmap bmpBrick9,Bitmap bmpSeed, Bitmap bmpSeed1) {
			//ש������
			brick_width = bmpBrick1.getWidth();//ÿ��ש��64
			brick_height = bmpBrick1.getHeight();//ÿ��ש��32
			this.bmpBrick1 = bmpBrick1;
			this.bmpBrick2 = bmpBrick2;
			this.bmpBrick3 = bmpBrick3;
			this.bmpBrick4 = bmpBrick4;
			this.bmpBrick5 = bmpBrick5;
			this.bmpBrick6 = bmpBrick6;
			this.bmpBrick7 = bmpBrick7;
			this.bmpBrick8 = bmpBrick8;
			this.bmpBrick9 = bmpBrick9;
			this.bmpSeed = bmpSeed;
			this.bmpSeed1 = bmpSeed1;
					
			score = 0;
			brick_exist = new boolean[64];
			seed_exist = new boolean[64];
			bullet_exist = false;
			isMoving = true;
			win = false;
			sx = new int[64];
			sy = new int[64];
			
			for (int i = 0; i <64; i++) {
				seed_exist[i] = false;
				sx[i] = MySurfaceView.screenW/2 + brick_width*(i % 8-4) + brick_width/2 - bmpSeed.getWidth()/2;
			    sy[i] = brick_height*(i / 8+2)+brick_height/2;
			    switch(i){
		        case 1:case 8:case 10:case 17:
		        case 6:case 13:case 15:case 22:
		        case 41:case 48:case 50:case 57:
		        case 46:case 53:case 55:case 62:
		        case 9:case 14:case 49:case 54:	
		        case 27:case 28:case 35:case 36:	
		        case 19:case 20:case 26:case 29:case 34:case 37:case 43:case 44:
		        
			        brick_exist[i] = true;	
		            break;
	            }
			}				
		}
	    
		public void resetGame(){
			for (int i = 0; i <64; i++) {
				seed_exist[i] = false;
				sx[i] = MySurfaceView.screenW/2 + brick_width*(i % 8-4) + brick_width/2 - bmpSeed.getWidth()/2;
			    sy[i] = brick_height*(i / 8+2)+brick_height/2;
			    switch(i){
		            case 1:case 8:case 10:case 17:
		            case 6:case 13:case 15:case 22:
		            case 41:case 48:case 50:case 57:
		            case 46:case 53:case 55:case 62:
		            case 9:case 14:case 49:case 54:	
		            case 27:case 28:case 35:case 36:	
		            case 19:case 20:case 26:case 29:case 34:case 37:case 43:case 44:		        
			        brick_exist[i] = true;	
		            break;
	            }
			}	
			win  = false;
			Button.isPressStop = false;
		}
		
		//ש�Ļ�ͼ����
		public void draw(Canvas canvas, Paint paint) {
			
			//�Ʒ�
			paint.setTextSize(30);
			paint.setTypeface(Typeface.DEFAULT_BOLD); 
			paint.setColor(Color.WHITE);
			//paint.setARGB(255,254,235,206);
			canvas.drawText(""+score, 45,MySurfaceView.screenH-30, paint);
			
			//ש��Ļ�ͼ
			for (int i = 0; i < 64 ; i++) {
								 			
				if (brick_exist[i]) {
					k = i % 8+1;// ��
					j = i / 8+1;// ��
					brick_left = MySurfaceView.screenW/2 + brick_width*(k-5);
					brick_right = MySurfaceView.screenW/2 + brick_width*(k-4);
					brick_top = brick_height*j+brick_height/2;
					brick_bottom = brick_height*(j+1)+brick_height/2;
				
					switch(i){
					     case 1:case 8:case 10:case 17:			       
							canvas.drawBitmap(bmpBrick1, brick_left, brick_top, paint);
							break;
					     case 6:case 13:case 15:case 22:				        
						    canvas.drawBitmap(bmpBrick7, brick_left, brick_top, paint);
						    break;
					     case 41:case 48:case 50:case 57:				       
						    canvas.drawBitmap(bmpBrick4, brick_left, brick_top, paint);
						    break;
					     case 46:case 53:case 55:case 62:				        
						    canvas.drawBitmap(bmpBrick3, brick_left, brick_top, paint);
						    break;
					     case 9:case 14:case 49:case 54:					        
						    canvas.drawBitmap(bmpBrick2, brick_left, brick_top, paint);
						    break;
					     case 27:case 28:case 35:case 36:					        
						    canvas.drawBitmap(bmpBrick8, brick_left, brick_top, paint);
						    break;
					     case 19:case 20:case 26:case 29:case 34:case 37:case 43:case 44:
						    canvas.drawBitmap(bmpBrick9, brick_left, brick_top, paint);
						    break;					
					}
				}
				
				if (seed_exist[i]) {				
					//���ӵĻ�ͼ������������ʾ��һ��
					if( (i-3) % 11 == 0){
						canvas.save();
					    Path path = new Path();
					    path.addRect(sx[i], sy[i], sx[i] + bmpSeed.getWidth(), sy[i] + bmpSeed.getHeight(), Direction.CW);
					    canvas.clipPath(path);
						canvas.drawBitmap(bmpSeed1, sx[i], sy[i], paint);
						canvas.restore();
					}else{
						canvas.save();
				        Path path = new Path();
				        path.addRect(sx[i], sy[i], sx[i] + bmpSeed.getWidth(), sy[i] + bmpSeed.getHeight(), Direction.CW);
				        canvas.clipPath(path);
					    canvas.drawBitmap(bmpSeed, sx[i], sy[i], paint);
					    canvas.restore();
					}				
				}	
			}
			//�ɹ���ʾ
			if (win){
				paint.setTextSize(55);
				paint.setTypeface(Typeface.DEFAULT_BOLD); 
				paint.setColor(Color.WHITE);
				canvas.drawText("You got "+score+" seeds!", 20 , MySurfaceView.screenH/2, paint);
			}
        }
		
		//�������
		 public boolean onTouchEvent(MotionEvent event) {   
			if(win){
		    	//��ȡ�û���ǰ����λ��
		    	int pointX = (int) event.getX();
		    	int pointY = (int) event.getY();
		    	//��ָ�����Ļ
			    if (event.getAction()== MotionEvent.ACTION_DOWN && pointY >= 100){
			    	MySurfaceView.gameState = MySurfaceView.GAME_WIN3;
					if(ButtonMusic.musicOn){ 
						MySurfaceView.playSound(MySurfaceView.WIN, 0);
					}
			    }
			}
				return false;
		    }	
		
		 //�ɹ��߼�
		 public void win(){
			 if (win){
				 Button.isPressStop = true;
			 }
		 }
		 
	    //ש����ײ���
		public void hitBrickCheck() {
			for (int i = 0; i < 64 ; i++) {
				if (brick_exist[i]) {
					k = i % 8+1;// ��
					j = i / 8+1;// ��
					brick_left = MySurfaceView.screenW/2 + brick_width*(k-5);
					brick_right = MySurfaceView.screenW/2 + brick_width*(k-4);
					brick_top = brick_height*j+brick_height/2;
					brick_bottom = brick_height*(j+1)+brick_height/2;
				
				//������ש��top�� 
				if( Ball.speedy > 0 && //���ƴ������µ���
					Ball.ball_x >= brick_left && Ball.ball_x <= brick_right && //���������ұ߽���
					Ball.ball_y + Ball.ball_r >= brick_top &&//������ש��߽�
					Ball.ball_y < brick_top//����֮ǰδ����״̬
					)
									
				{
					Ball.ball_y = brick_top;
					Ball.ballUpOrDownHit();
					brick_exist[i] = false;
					seed_exist[i] = true;					
			    }			
				
				//������ש��bottom��
				else if(Ball.speedy < 0 && //���ƴ������ϵ���
						Ball.ball_x >= brick_left && Ball.ball_x <= brick_right && //���������ұ߽���
						Ball.ball_y - Ball.ball_r <= brick_bottom &&//������ש��߽�
						Ball.ball_y  > brick_bottom
						)

			    {
					Ball.ball_y = brick_bottom;
					Ball.ballUpOrDownHit();
					brick_exist[i] = false;
					seed_exist[i] = true;
				}
			
				//������ש��left��
				else if(Ball.speedx > 0 && //���ƴ������ҵ���
						Ball.ball_y >= brick_top && Ball.ball_y <= brick_bottom && //���������ұ߽���
						Ball.ball_x + Ball.ball_r >= brick_left &&//������ש��߽�
						Ball.ball_x < brick_left//����֮ǰδ����״̬
						)
				{				
					Ball.ball_x = brick_left;
					Ball.ballLeftOrRightHit();
					brick_exist[i] = false;
					seed_exist[i] = true;
				}
			
				//������ש��right��
				else if(Ball.speedx < 0 && //���ƴ���������
						Ball.ball_y >= brick_top && Ball.ball_y <= brick_bottom && //���������ұ߽���
						Ball.ball_x - Ball.ball_r <= brick_right &&//������ש��߽�
						Ball.ball_x > brick_right//����֮ǰδ����״̬
						) 				    
			   {			
					Ball.ball_x = brick_right;
					Ball.ballLeftOrRightHit();
					brick_exist[i] = false;
					seed_exist[i] = true;
			   }
				
				//�����Ͻ�
				else if (Ball.ball_x < brick_left && Ball.ball_y < brick_top && //���������Ͻ�λ��
						 Math.pow(Ball.ball_y - brick_top , 2) + Math.pow(Ball.ball_x - brick_left, 2) <= Math.pow(Ball.ball_r, 2) //����
						 //Math.pow(Ball.ball_y - Ball.speedy - brick_top , 2) + Math.pow(Ball.ball_x - Ball.speedx - brick_left, 2) > Math.pow(Ball.ball_r, 2)//֮ǰδ����
						 )
				{
					if (j !=1 && brick_exist[i-8]) {
						Ball.ballLeftOrRightHit();
						brick_exist[i] = false;
						seed_exist[i] = true;			
						brick_exist[i-8] = false;
						seed_exist[i-8] = true;
					} else if (k != 1 && brick_exist[i-1]){
						Ball.ballUpOrDownHit();
						brick_exist[i] = false;
						seed_exist[i] = true;
						brick_exist[i-1] = false;
						seed_exist[i-1] = true;
					} else {
					    Ball.topLeftCorner();
						brick_exist[i] = false;
						seed_exist[i] = true;
					}
				}
				
				//�����½�
				else if (Ball.ball_x < brick_left && Ball.ball_y > brick_bottom && //���������½�λ��
						 Math.pow(Ball.ball_y - brick_bottom , 2) + Math.pow(Ball.ball_x - brick_left, 2) <= Math.pow(Ball.ball_r, 2)  //����
						 //Math.pow(Ball.ball_y - Ball.speedy - brick_bottom , 2) + Math.pow(Ball.ball_x - Ball.speedx - brick_left, 2) > Math.pow(Ball.ball_r, 2)//֮ǰδ����
						 )
				{
					if (j != 8 && brick_exist[i+8]) {
						Ball.ballLeftOrRightHit();
						brick_exist[i] = false;
						seed_exist[i] = true;
						brick_exist[i+8] = false;
						seed_exist[i+8] = true;
					} else if (k != 1 && brick_exist[i-1]){
						Ball.ballUpOrDownHit();
						brick_exist[i] = false;
						seed_exist[i] = true;
						brick_exist[i-1] = false;
						seed_exist[i-1] = true;
					} else {
						Ball.bottomLeftCorner();
						brick_exist[i] = false;
						seed_exist[i] = true;
					}
				}
				
				//�����Ͻ�
				else if (Ball.ball_x > brick_right && Ball.ball_y < brick_top && //���������Ͻ�λ��
						 Math.pow(Ball.ball_y - brick_top , 2) + Math.pow(Ball.ball_x - brick_right, 2) <= Math.pow(Ball.ball_r, 2) //����
						 //Math.pow(Ball.ball_y - Ball.speedy - brick_top , 2) + Math.pow(Ball.ball_x - Ball.speedx - brick_right, 2) > Math.pow(Ball.ball_r, 2)//֮ǰδ����
						 )
				{
					if (j != 1 && brick_exist[i-8]) {
						Ball.ballLeftOrRightHit();
						brick_exist[i] = false;
						seed_exist[i] = true;
						brick_exist[i-8] = false;
						seed_exist[i-8] = true;
					} else if (k != 10 && brick_exist[i+1]){
						Ball.ballUpOrDownHit();
						brick_exist[i] = false;
						seed_exist[i] = true;
						brick_exist[i+1] = false;
						seed_exist[i+1] = true;
					} else {
						Ball.topRightCorner();
						brick_exist[i] = false;
						seed_exist[i] = true;
					}
				}
				
				//�����½�
				else if (Ball.ball_x > brick_right && Ball.ball_y > brick_bottom && //���������½�λ��
						 Math.pow(Ball.ball_y - brick_bottom, 2) + Math.pow(Ball.ball_x - brick_right, 2) <= Math.pow(Ball.ball_r, 2)  //����
						 //Math.pow(Ball.ball_y - Ball.speedy - brick_bottom , 2) + Math.pow(Ball.ball_x - Ball.speedx - brick_right, 2) > Math.pow(Ball.ball_r, 2)//֮ǰδ����
						 )
				{
					if (j != 8 && brick_exist[i+8]) {
						Ball.ballLeftOrRightHit();
						brick_exist[i] = false;
						seed_exist[i] = true;
						brick_exist[i+8] = false;
						seed_exist[i+8] = true;
					} else if (k != 8 && brick_exist[i+1]){
						Ball.ballUpOrDownHit();
						brick_exist[i] = false;
						seed_exist[i] = true;
						brick_exist[i+1] = false;
						seed_exist[i+1] = true;
					} else {
						Ball.bottomLeftCorner();
						brick_exist[i] = false;
						seed_exist[i] = true;
					}
				}
			}
			}
		}
		
		//�����߼�
		public void seedDroping(){
			for(int i = 0; i < 64 ; i++) {
				if (seed_exist[i] && isMoving){
					sy[i]+=2;
					//�͵�����ײ���
					if (sx[i] >  Paddle.px - bmpSeed.getWidth() /5*4  && sx[i] < Paddle.px + Paddle.bmpPaddle.getWidth() - bmpSeed.getWidth() /5 && //�ڵ��巶Χ��
						sy[i] >= Paddle.py - bmpSeed.getHeight() && sy[i] <= Paddle.py )
						{
							seed_exist[i] = false;
							if(ButtonMusic.musicOn){ 
								MySurfaceView.playSound(MySurfaceView.CATCH_SEED, 0);
							}
							score +=1;
							if ( (i-3) % 11 == 0){
								bullet_exist = true;
								if(ButtonMusic.musicOn){ 
									MySurfaceView.playSound(MySurfaceView.SPECIAL_SEED, 0);
								}
							}
						}
			    }
			}
		}
		
		public void resetSeed(){
			for (int i = 0; i <64; i++) {
				seed_exist[i] = false;
				bullet_exist = false;
			}	
		}
		//�ж���ײ
		public void bulletCollision(Bullet bullet) {
			for (int i = 0; i < 64 ; i++) {
				if (brick_exist[i]) {
					k = i % 8+1;// ��
					j = i / 8+1;// ��
					brick_left = MySurfaceView.screenW/2 + brick_width*(k-5);
					brick_right = MySurfaceView.screenW/2 + brick_width*(k-4);
					brick_top = brick_height*j+brick_height/2;
					brick_bottom = brick_height*(j+1)+brick_height/2;
					
					if(bullet.bulletY <= brick_bottom && bullet.bulletX1 >= brick_left && bullet.bulletX1 <= brick_right){
						brick_exist[i] = false;
						seed_exist[i] = true;
						bulletCollision = true;	
						if(ButtonMusic.musicOn){ 
							MySurfaceView.playSound(MySurfaceView.BRICK, 0);
						}
						//������ײ����������
						bullet.isDead = true;
					}
				}
			}		
		}
}
