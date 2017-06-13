package com.example.mybreakout;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Path.Direction;
import android.view.MotionEvent;

public class Ball {
	
	public static int bx;
	public int by;
	public static int ball_x;
	public static int ball_y;
	public static int ball_r;

	static int ballHp = 3;
	static int lost = 3 - ballHp;
	private Bitmap bmpBall;
	static int speedx = 3;
	static int speedy = -4;
	static boolean firstTouch = true;
	static boolean isMoving = false;
	private Bitmap bmpBallHp;
	boolean ballExist = true;


	
	public Ball(Bitmap bmpBall, Bitmap bmpBallHp){
		this.bmpBall = bmpBall;
		this.bmpBallHp = bmpBallHp;
		bx = MySurfaceView.screenW/2 - bmpBall.getWidth()/2;
		by = Paddle.py-bmpBall.getHeight();	
		ball_x = bx + bmpBall.getWidth()/2;
		ball_y = by + bmpBall.getHeight()/2;
		ball_r = bmpBall.getWidth()/2;
	}
	
	public void draw(Canvas canvas, Paint paint) {		
		    canvas.save();
		    Path path = new Path();
		    path.addCircle(ball_x,ball_y,ball_r,Direction.CCW);
		    canvas.clipPath(path);
		    canvas.drawBitmap(bmpBall, ball_x-bmpBall.getWidth()/2, ball_y-bmpBall.getHeight()/2, paint);
		    canvas.restore();
		    
		for (int i = 0; i < ballHp; i++){
			canvas.drawBitmap(bmpBallHp, (i+1)*bmpBallHp.getWidth(), bmpBallHp.getHeight()/2, paint);
		}
	}
	
    public boolean onTouchEvent(MotionEvent event) {   
	   
    	//获取用户当前触屏位置
    	int pointX = (int) event.getX();
    	int pointY = (int) event.getY();
    	//手指点击屏幕
	    if (event.getAction()== MotionEvent.ACTION_DOWN & firstTouch && pointY >= 100){
	    	firstTouch = false;
	    	isMoving = true; 
	    }
		return false;
    }	   
    
    //小球掉落重置游戏
    public void resetBall(){
    	ballExist = true;
    	bx = MySurfaceView.screenW/2 - bmpBall.getWidth()/2;
		by = Paddle.py-bmpBall.getHeight();	
		ball_x = bx + bmpBall.getWidth()/2;
		ball_y = by + bmpBall.getHeight()/2;
		ball_r = bmpBall.getWidth()/2;
	    firstTouch = true;
		isMoving = false;
		speedx = 3;
		speedy = -4;
    }
    
    public void resetGame(){
    	resetBall();
    	ballHp = 3;
    }
    
    public void hitWallCheck() {
		if (isMoving){
			// 左碰墙
		    if (ball_x <= ball_r) {
		    	ball_x = ball_r;
			    ballLeftOrRightHit();
		    }
		    // 右碰墙
		    if (ball_x >= MySurfaceView.screenW - ball_r ) {
			    ball_x = MySurfaceView.screenW - ball_r;
			    ballLeftOrRightHit();
		    }
		    // 上碰墙
		    if (ball_y <= ball_r) {
			    ball_y = ball_r;
			    ballUpOrDownHit();
		    }
		    // 下碰墙
		}  	
	}
	
	//让小球移动
		public void ballRunning() {
			 if (isMoving){			
			    ball_x += speedx;				 
				ball_y += speedy;
			 }		
		}
		
	    //朝左或朝右碰撞后小球水平方向逆向
		public static void ballLeftOrRightHit() {
			speedx=-speedx;	
			if(ButtonMusic.musicOn){ 
				MySurfaceView.playSound(MySurfaceView.BRICK, 0);
			}
		}
		
		//朝上或朝下碰撞后小球竖直方向逆向
		public static void ballUpOrDownHit() {
			speedy=-speedy;
			if(ButtonMusic.musicOn){ 
				MySurfaceView.playSound(MySurfaceView.BRICK, 0);
			}
		}
        		
		//碰砖左上角
		public static void topLeftCorner() {
			int sx,sy; 
			sx = speedx;
			sy = speedy;
			if (speedx < 0 && speedy >0) { //state1 right-angle rebound
				speedx = -sy;
				speedy = sx;
			}else if(speedx > 0 && speedy >0) { //state2 45°
				speedx = -sy;
				speedy = -sx;
			}else if(speedx > 0 && speedy <0) { //state3 right-angle rebound
				speedx = sy;
				speedy = -sx;
			}
			if(ButtonMusic.musicOn){ 
				MySurfaceView.playSound(MySurfaceView.BRICK, 0);
			}
		}
		
		//碰砖左下角
		public static void bottomLeftCorner() {
			int sx,sy; //赋值 方便使用
			sx = speedx;
			sy = speedy;
			if (speedx > 0 && speedy >0) { //碰角情况1 直角反弹
				speedx = -sy;
				speedy = sx;
			}else if(speedx > 0 && speedy <0) { //碰角情况2 45°
				speedx = sy;
				speedy = sx;
			}else if(speedx < 0 && speedy <0) { //碰角情况3 直角反弹
				speedx = sy;
				speedy = -sx;
			}
			if(ButtonMusic.musicOn){ 
				MySurfaceView.playSound(MySurfaceView.BRICK, 0);
			}
		}
		
		//碰砖右上角
		public static void topRightCorner() {
			int sx,sy;
			sx = speedx;
			sy = speedy;
			if (speedx > 0 && speedy >0) { //state1 right-angle rebound
				speedx = sy;
				speedy = -sx;
			}else if(speedx < 0 && speedy >0) { //state2 45°
				speedx = sy;
				speedy = sx;
			}else if(speedx < 0 && speedy <0) { //state3 right-angle rebound
				speedx = -sy;
				speedy = sx;
			}
			if(ButtonMusic.musicOn){ 
				MySurfaceView.playSound(MySurfaceView.BRICK, 0);
			}
		}
		
		//碰砖右下角
		public static void bottomRightCorner() {
			int sx,sy; //赋值 方便使用
			sx = speedx;
			sy = speedy;
			if (speedx < 0 && speedy >0) { //碰角情况1 直角反弹
				speedx = sy;
				speedy = -sx;
			}else if(speedx < 0 && speedy <0) { //碰角情况2 45°
				speedx = -sy;
				speedy = -sx;
			}else if(speedx > 0 && speedy <0) { //碰角情况3 直角反弹
				speedx = -sy;
				speedy = sx;
			}
			if(ButtonMusic.musicOn){ 
				MySurfaceView.playSound(MySurfaceView.BRICK, 0);
			}
		}
		
		
	public void setBallHp(int hp){
		this.ballHp = hp;
	}
    
	public int getBallHp(){
		return ballHp;
	}

	public boolean isBallExist() {
		return ballExist;
	}

	public boolean setBallExist(boolean ballExist) {
		this.ballExist = ballExist;
		return ballExist;
	}
	

}
