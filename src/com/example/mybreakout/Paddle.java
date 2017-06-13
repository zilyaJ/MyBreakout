package com.example.mybreakout;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;

public class Paddle {
	
	static Bitmap bmpPaddle;
	//设置坐标
	static int px;
	static int py;
	
	public Paddle(Bitmap bmpPaddle){
		this.bmpPaddle = bmpPaddle;
		px = MySurfaceView.screenW/2 - bmpPaddle.getWidth()/2;
		py = MySurfaceView.screenH - bmpPaddle.getHeight()*5;		
	}
	
/*触屏实现控制	
    public boolean onTouchEvent(MotionEvent event) {
	    
		//获取用户手指触屏的X,Y坐标赋值与文本的XY坐标
		int x = (int) event.getX();
	    
	  //手指点击屏幕
	    if (event.getAction()== MotionEvent.ACTION_MOVE){//只使用该方法可以移动板
	    	px = x;
	    	if (px + bmpPaddle.getWidth() >= MySurfaceView.screenW){
				px = MySurfaceView.screenW - bmpPaddle.getWidth();				
			}else if(px <=0){
				px = 0;
			}
	    	
	    }	    
		return true;
    }	*/  
	
	public void SetPx(int ppx)
	{
		px = ppx - bmpPaddle.getWidth()/2;
		if (px + bmpPaddle.getWidth() >= MySurfaceView.screenW){
			px = MySurfaceView.screenW - bmpPaddle.getWidth();				
		}else if(px <=0){
			px = 0;
		}
	}
	    //重绘画布
	public void draw(Canvas canvas, Paint paint) {
		canvas.drawBitmap(bmpPaddle, px, py, paint);
	
	}


	//小球掉落后重置paddle
	public void resetPaddle(){
		px = MySurfaceView.screenW/2 - bmpPaddle.getWidth()/2;
		py = MySurfaceView.screenH - bmpPaddle.getHeight()*5;
		MySurfaceView.arc_x = MySurfaceView.screenW / 2;
		MySurfaceView.arc_y = MySurfaceView.screenH / 2;	
	}
	
	public void hitPaddleCheck() {
		if (Ball.isMoving){
			// 下碰挡板正面
		    if (Ball.speedy > 0 &&
		    	Ball.ball_x >= Ball.ball_r && Ball.ball_x <= MySurfaceView.screenW - Ball.ball_r && //在屏幕内，起码条件
			    Ball.ball_x >= px && Ball.ball_x <= px + bmpPaddle.getWidth() && //在挡板范围内
			    Ball.ball_y >= py-Ball.ball_r && Ball.ball_y < py
			    )
		    {
		    	Ball.ball_y  =  py - Ball.ball_r;
			    Ball.ballUpOrDownHit();
		    }
		
		    //朝右碰砖的left面
		    else if(Ball.speedx > 0 && 
		    	    Ball.ball_y >= py && Ball.ball_y <= py + bmpPaddle.getHeight() && 
		    	    Ball.ball_x + Ball.ball_r >= px &&
		    	    Ball.ball_x < px
		    	    )
		    {
		    	Ball.ball_x  =  px - Ball.ball_r;
		    	Ball.ballLeftOrRightHit();
		    }
	
		    //朝左碰砖的right面
		    else if(Ball.speedx < 0 && 
				    Ball.ball_y >= py && Ball.ball_y <= py + bmpPaddle.getHeight() && 
				    Ball.ball_x - Ball.ball_r <= px + bmpPaddle.getWidth() &&
				    Ball.ball_x > px + bmpPaddle.getHeight()
				    ) 				    
	        {
		    	Ball.ball_x  =  px + bmpPaddle.getWidth() + Ball.ball_r;
			    Ball.ballLeftOrRightHit();
	        }
		
		    //碰左上角
		    else if (Ball.ball_x < px && Ball.ball_y < py && //限制在左上角位置
				     Math.pow(Ball.ball_y - py , 2) + Math.pow(Ball.ball_x - px, 2) <= Math.pow(Ball.ball_r, 2) //触碰
				     //Math.pow(Ball.ball_y - Ball.speedy - brick_top , 2) + Math.pow(Ball.ball_x - Ball.speedx - brick_left, 2) > Math.pow(Ball.ball_r, 2)//之前未触碰
				     )
		    {   
		    	Ball.ball_x = px - Ball.ball_r;
		    	Ball.ball_y = py - Ball.ball_r;
		    	Ball.topLeftCorner();
		    }
		
		    //碰左下角
		    else if (Ball.ball_x < px && Ball.ball_y > py + bmpPaddle.getHeight()&& //限制在左下角位置
				     Math.pow(Ball.ball_y -  py + bmpPaddle.getHeight() , 2) + Math.pow(Ball.ball_x - px, 2) <= Math.pow(Ball.ball_r, 2)  //触碰
				     //Math.pow(Ball.ball_y - Ball.speedy - brick_bottom , 2) + Math.pow(Ball.ball_x - Ball.speedx - brick_left, 2) > Math.pow(Ball.ball_r, 2)//之前未触碰
				     )
		    {
			    Ball.ball_x = px - Ball.ball_r;
		    	Ball.ball_y = py + bmpPaddle.getHeight() + Ball.ball_r;
			    Ball.bottomLeftCorner();
		    }
		
		    //碰右上角
		    else if (Ball.ball_x > px + bmpPaddle.getWidth() && Ball.ball_y < py && //限制在右上角位置
				     Math.pow(Ball.ball_y - py, 2) + Math.pow(Ball.ball_x - px + bmpPaddle.getWidth(), 2) <= Math.pow(Ball.ball_r, 2) //触碰
				     //Math.pow(Ball.ball_y - Ball.speedy - brick_top , 2) + Math.pow(Ball.ball_x - Ball.speedx - brick_right, 2) > Math.pow(Ball.ball_r, 2)//之前未触碰
				    )
	     	{			    
			    Ball.ball_x = px + bmpPaddle.getWidth() + Ball.ball_r;
		    	Ball.ball_y = py - Ball.ball_r;
		    	Ball.topRightCorner();
		    }
		
		    //碰右下角
		    else if (Ball.ball_x > px + bmpPaddle.getWidth() && Ball.ball_y >  py + bmpPaddle.getHeight() && //限制在左下角位置
				     Math.pow(Ball.ball_y -  py + bmpPaddle.getHeight() , 2) + Math.pow(Ball.ball_x - px + bmpPaddle.getWidth(), 2) <= Math.pow(Ball.ball_r, 2)  //触碰
				     //Math.pow(Ball.ball_y - Ball.speedy - brick_bottom , 2) + Math.pow(Ball.ball_x - Ball.speedx - brick_right, 2) > Math.pow(Ball.ball_r, 2)//之前未触碰
				     )
		    {			   
			    Ball.ball_x = px + bmpPaddle.getWidth() + Ball.ball_r;
		    	Ball.ball_y = py + bmpPaddle.getHeight() + Ball.ball_r; 
		    	Ball.bottomLeftCorner();
		    }
	    }
	}
}
