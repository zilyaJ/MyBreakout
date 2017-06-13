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
	//砖的属性
		static int brick_width;//每块砖宽
		int brick_height;//每块砖高
		boolean brick_exist[];//砖是否存在
		boolean seed_exist[];
		static boolean bullet_exist;
		boolean bulletCollision;
		static boolean isMoving = true;;
		boolean win = false;
		int k ;// 列//到for循环里才初始化
		int j ;// 行
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

		//砖的构造函数
		public Brick3(Bitmap bmpBrick1,Bitmap bmpBrick2,Bitmap bmpBrick3,Bitmap bmpBrick4,Bitmap bmpBrick5,Bitmap bmpBrick6,Bitmap bmpBrick7,Bitmap bmpBrick8,Bitmap bmpBrick9,Bitmap bmpSeed, Bitmap bmpSeed1) {
			//砖的属性
			brick_width = bmpBrick1.getWidth();//每块砖宽64
			brick_height = bmpBrick1.getHeight();//每块砖高32
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
		
		//砖的绘图函数
		public void draw(Canvas canvas, Paint paint) {
			
			//计分
			paint.setTextSize(30);
			paint.setTypeface(Typeface.DEFAULT_BOLD); 
			paint.setColor(Color.WHITE);
			//paint.setARGB(255,254,235,206);
			canvas.drawText(""+score, 45,MySurfaceView.screenH-30, paint);
			
			//砖块的绘图
			for (int i = 0; i < 64 ; i++) {
								 			
				if (brick_exist[i]) {
					k = i % 8+1;// 列
					j = i / 8+1;// 行
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
					//种子的绘图，特殊种子显示不一样
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
			//成功显示
			if (win){
				paint.setTextSize(55);
				paint.setTypeface(Typeface.DEFAULT_BOLD); 
				paint.setColor(Color.WHITE);
				canvas.drawText("You got "+score+" seeds!", 20 , MySurfaceView.screenH/2, paint);
			}
        }
		
		//触屏检测
		 public boolean onTouchEvent(MotionEvent event) {   
			if(win){
		    	//获取用户当前触屏位置
		    	int pointX = (int) event.getX();
		    	int pointY = (int) event.getY();
		    	//手指点击屏幕
			    if (event.getAction()== MotionEvent.ACTION_DOWN && pointY >= 100){
			    	MySurfaceView.gameState = MySurfaceView.GAME_WIN3;
					if(ButtonMusic.musicOn){ 
						MySurfaceView.playSound(MySurfaceView.WIN, 0);
					}
			    }
			}
				return false;
		    }	
		
		 //成功逻辑
		 public void win(){
			 if (win){
				 Button.isPressStop = true;
			 }
		 }
		 
	    //砖的碰撞检测
		public void hitBrickCheck() {
			for (int i = 0; i < 64 ; i++) {
				if (brick_exist[i]) {
					k = i % 8+1;// 列
					j = i / 8+1;// 行
					brick_left = MySurfaceView.screenW/2 + brick_width*(k-5);
					brick_right = MySurfaceView.screenW/2 + brick_width*(k-4);
					brick_top = brick_height*j+brick_height/2;
					brick_bottom = brick_height*(j+1)+brick_height/2;
				
				//朝下碰砖的top面 
				if( Ball.speedy > 0 && //限制从上往下弹球
					Ball.ball_x >= brick_left && Ball.ball_x <= brick_right && //限制在左右边界内
					Ball.ball_y + Ball.ball_r >= brick_top &&//球触碰到砖块边界
					Ball.ball_y < brick_top//触碰之前未触碰状态
					)
									
				{
					Ball.ball_y = brick_top;
					Ball.ballUpOrDownHit();
					brick_exist[i] = false;
					seed_exist[i] = true;					
			    }			
				
				//朝上碰砖的bottom面
				else if(Ball.speedy < 0 && //限制从下往上弹球
						Ball.ball_x >= brick_left && Ball.ball_x <= brick_right && //限制在左右边界内
						Ball.ball_y - Ball.ball_r <= brick_bottom &&//球触碰到砖块边界
						Ball.ball_y  > brick_bottom
						)

			    {
					Ball.ball_y = brick_bottom;
					Ball.ballUpOrDownHit();
					brick_exist[i] = false;
					seed_exist[i] = true;
				}
			
				//朝右碰砖的left面
				else if(Ball.speedx > 0 && //限制从左往右弹球
						Ball.ball_y >= brick_top && Ball.ball_y <= brick_bottom && //限制在左右边界内
						Ball.ball_x + Ball.ball_r >= brick_left &&//球触碰到砖块边界
						Ball.ball_x < brick_left//触碰之前未触碰状态
						)
				{				
					Ball.ball_x = brick_left;
					Ball.ballLeftOrRightHit();
					brick_exist[i] = false;
					seed_exist[i] = true;
				}
			
				//朝左碰砖的right面
				else if(Ball.speedx < 0 && //限制从右往左弹球
						Ball.ball_y >= brick_top && Ball.ball_y <= brick_bottom && //限制在左右边界内
						Ball.ball_x - Ball.ball_r <= brick_right &&//球触碰到砖块边界
						Ball.ball_x > brick_right//触碰之前未触碰状态
						) 				    
			   {			
					Ball.ball_x = brick_right;
					Ball.ballLeftOrRightHit();
					brick_exist[i] = false;
					seed_exist[i] = true;
			   }
				
				//碰左上角
				else if (Ball.ball_x < brick_left && Ball.ball_y < brick_top && //限制在左上角位置
						 Math.pow(Ball.ball_y - brick_top , 2) + Math.pow(Ball.ball_x - brick_left, 2) <= Math.pow(Ball.ball_r, 2) //触碰
						 //Math.pow(Ball.ball_y - Ball.speedy - brick_top , 2) + Math.pow(Ball.ball_x - Ball.speedx - brick_left, 2) > Math.pow(Ball.ball_r, 2)//之前未触碰
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
				
				//碰左下角
				else if (Ball.ball_x < brick_left && Ball.ball_y > brick_bottom && //限制在左下角位置
						 Math.pow(Ball.ball_y - brick_bottom , 2) + Math.pow(Ball.ball_x - brick_left, 2) <= Math.pow(Ball.ball_r, 2)  //触碰
						 //Math.pow(Ball.ball_y - Ball.speedy - brick_bottom , 2) + Math.pow(Ball.ball_x - Ball.speedx - brick_left, 2) > Math.pow(Ball.ball_r, 2)//之前未触碰
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
				
				//碰右上角
				else if (Ball.ball_x > brick_right && Ball.ball_y < brick_top && //限制在右上角位置
						 Math.pow(Ball.ball_y - brick_top , 2) + Math.pow(Ball.ball_x - brick_right, 2) <= Math.pow(Ball.ball_r, 2) //触碰
						 //Math.pow(Ball.ball_y - Ball.speedy - brick_top , 2) + Math.pow(Ball.ball_x - Ball.speedx - brick_right, 2) > Math.pow(Ball.ball_r, 2)//之前未触碰
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
				
				//碰右下角
				else if (Ball.ball_x > brick_right && Ball.ball_y > brick_bottom && //限制在左下角位置
						 Math.pow(Ball.ball_y - brick_bottom, 2) + Math.pow(Ball.ball_x - brick_right, 2) <= Math.pow(Ball.ball_r, 2)  //触碰
						 //Math.pow(Ball.ball_y - Ball.speedy - brick_bottom , 2) + Math.pow(Ball.ball_x - Ball.speedx - brick_right, 2) > Math.pow(Ball.ball_r, 2)//之前未触碰
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
		
		//种子逻辑
		public void seedDroping(){
			for(int i = 0; i < 64 ; i++) {
				if (seed_exist[i] && isMoving){
					sy[i]+=2;
					//和挡板碰撞检测
					if (sx[i] >  Paddle.px - bmpSeed.getWidth() /5*4  && sx[i] < Paddle.px + Paddle.bmpPaddle.getWidth() - bmpSeed.getWidth() /5 && //在挡板范围内
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
		//判断碰撞
		public void bulletCollision(Bullet bullet) {
			for (int i = 0; i < 64 ; i++) {
				if (brick_exist[i]) {
					k = i % 8+1;// 列
					j = i / 8+1;// 行
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
						//发生碰撞，让其死亡
						bullet.isDead = true;
					}
				}
			}		
		}
}
