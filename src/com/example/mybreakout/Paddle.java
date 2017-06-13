package com.example.mybreakout;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;

public class Paddle {
	
	static Bitmap bmpPaddle;
	//��������
	static int px;
	static int py;
	
	public Paddle(Bitmap bmpPaddle){
		this.bmpPaddle = bmpPaddle;
		px = MySurfaceView.screenW/2 - bmpPaddle.getWidth()/2;
		py = MySurfaceView.screenH - bmpPaddle.getHeight()*5;		
	}
	
/*����ʵ�ֿ���	
    public boolean onTouchEvent(MotionEvent event) {
	    
		//��ȡ�û���ָ������X,Y���긳ֵ���ı���XY����
		int x = (int) event.getX();
	    
	  //��ָ�����Ļ
	    if (event.getAction()== MotionEvent.ACTION_MOVE){//ֻʹ�ø÷��������ƶ���
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
	    //�ػ滭��
	public void draw(Canvas canvas, Paint paint) {
		canvas.drawBitmap(bmpPaddle, px, py, paint);
	
	}


	//С����������paddle
	public void resetPaddle(){
		px = MySurfaceView.screenW/2 - bmpPaddle.getWidth()/2;
		py = MySurfaceView.screenH - bmpPaddle.getHeight()*5;
		MySurfaceView.arc_x = MySurfaceView.screenW / 2;
		MySurfaceView.arc_y = MySurfaceView.screenH / 2;	
	}
	
	public void hitPaddleCheck() {
		if (Ball.isMoving){
			// ������������
		    if (Ball.speedy > 0 &&
		    	Ball.ball_x >= Ball.ball_r && Ball.ball_x <= MySurfaceView.screenW - Ball.ball_r && //����Ļ�ڣ���������
			    Ball.ball_x >= px && Ball.ball_x <= px + bmpPaddle.getWidth() && //�ڵ��巶Χ��
			    Ball.ball_y >= py-Ball.ball_r && Ball.ball_y < py
			    )
		    {
		    	Ball.ball_y  =  py - Ball.ball_r;
			    Ball.ballUpOrDownHit();
		    }
		
		    //������ש��left��
		    else if(Ball.speedx > 0 && 
		    	    Ball.ball_y >= py && Ball.ball_y <= py + bmpPaddle.getHeight() && 
		    	    Ball.ball_x + Ball.ball_r >= px &&
		    	    Ball.ball_x < px
		    	    )
		    {
		    	Ball.ball_x  =  px - Ball.ball_r;
		    	Ball.ballLeftOrRightHit();
		    }
	
		    //������ש��right��
		    else if(Ball.speedx < 0 && 
				    Ball.ball_y >= py && Ball.ball_y <= py + bmpPaddle.getHeight() && 
				    Ball.ball_x - Ball.ball_r <= px + bmpPaddle.getWidth() &&
				    Ball.ball_x > px + bmpPaddle.getHeight()
				    ) 				    
	        {
		    	Ball.ball_x  =  px + bmpPaddle.getWidth() + Ball.ball_r;
			    Ball.ballLeftOrRightHit();
	        }
		
		    //�����Ͻ�
		    else if (Ball.ball_x < px && Ball.ball_y < py && //���������Ͻ�λ��
				     Math.pow(Ball.ball_y - py , 2) + Math.pow(Ball.ball_x - px, 2) <= Math.pow(Ball.ball_r, 2) //����
				     //Math.pow(Ball.ball_y - Ball.speedy - brick_top , 2) + Math.pow(Ball.ball_x - Ball.speedx - brick_left, 2) > Math.pow(Ball.ball_r, 2)//֮ǰδ����
				     )
		    {   
		    	Ball.ball_x = px - Ball.ball_r;
		    	Ball.ball_y = py - Ball.ball_r;
		    	Ball.topLeftCorner();
		    }
		
		    //�����½�
		    else if (Ball.ball_x < px && Ball.ball_y > py + bmpPaddle.getHeight()&& //���������½�λ��
				     Math.pow(Ball.ball_y -  py + bmpPaddle.getHeight() , 2) + Math.pow(Ball.ball_x - px, 2) <= Math.pow(Ball.ball_r, 2)  //����
				     //Math.pow(Ball.ball_y - Ball.speedy - brick_bottom , 2) + Math.pow(Ball.ball_x - Ball.speedx - brick_left, 2) > Math.pow(Ball.ball_r, 2)//֮ǰδ����
				     )
		    {
			    Ball.ball_x = px - Ball.ball_r;
		    	Ball.ball_y = py + bmpPaddle.getHeight() + Ball.ball_r;
			    Ball.bottomLeftCorner();
		    }
		
		    //�����Ͻ�
		    else if (Ball.ball_x > px + bmpPaddle.getWidth() && Ball.ball_y < py && //���������Ͻ�λ��
				     Math.pow(Ball.ball_y - py, 2) + Math.pow(Ball.ball_x - px + bmpPaddle.getWidth(), 2) <= Math.pow(Ball.ball_r, 2) //����
				     //Math.pow(Ball.ball_y - Ball.speedy - brick_top , 2) + Math.pow(Ball.ball_x - Ball.speedx - brick_right, 2) > Math.pow(Ball.ball_r, 2)//֮ǰδ����
				    )
	     	{			    
			    Ball.ball_x = px + bmpPaddle.getWidth() + Ball.ball_r;
		    	Ball.ball_y = py - Ball.ball_r;
		    	Ball.topRightCorner();
		    }
		
		    //�����½�
		    else if (Ball.ball_x > px + bmpPaddle.getWidth() && Ball.ball_y >  py + bmpPaddle.getHeight() && //���������½�λ��
				     Math.pow(Ball.ball_y -  py + bmpPaddle.getHeight() , 2) + Math.pow(Ball.ball_x - px + bmpPaddle.getWidth(), 2) <= Math.pow(Ball.ball_r, 2)  //����
				     //Math.pow(Ball.ball_y - Ball.speedy - brick_bottom , 2) + Math.pow(Ball.ball_x - Ball.speedx - brick_right, 2) > Math.pow(Ball.ball_r, 2)//֮ǰδ����
				     )
		    {			   
			    Ball.ball_x = px + bmpPaddle.getWidth() + Ball.ball_r;
		    	Ball.ball_y = py + bmpPaddle.getHeight() + Ball.ball_r; 
		    	Ball.bottomLeftCorner();
		    }
	    }
	}
}
