package com.example.mybreakout;

import java.util.HashMap;
import java.util.Vector;

import android.app.Service;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;

public class MySurfaceView extends SurfaceView implements Callback, Runnable, SensorEventListener {
		
	private SensorManager sm;
	private Sensor sensor;
	private SensorEventListener mySensorListener;
	static int arc_x;// 圆形的x,y位置
	static int arc_y;
	private float x = 0, y = 0, z = 0;	
	
	private SurfaceHolder sfh;
	private Paint paint;
	private Thread th;
	private boolean flag;
	private Canvas canvas;
	public static int screenW, screenH;
	
	//定义游戏状态常量
	public static final int GAME_MENU = 0;//游戏菜单
	public static final int GAME_LEVEL1 = 1;//第一关
	public static final int GAME_LEVEL2 = 2;//第二关
	public static final int GAME_LEVEL3 = 3;//第三关
	public static final int GAME_WIN = 4;//游戏胜利
	public static final int GAME_WIN2 = 5;//游戏胜利
	public static final int GAME_WIN3 = 6;//游戏胜利
	public static final int GAME_OVER = 7;//游戏失败
	public static final int GAME_OVER2 = 8;//游戏失败
	public static final int GAME_OVER3 = 9;//游戏失败
	public static final int GAME_LEVEL = 10;//关卡选择
	public static final int GAME_HELP = 11;//帮助界面
	public static final int GAME_TARGET = 12;//目标界面
	public static final int GAME_TARGET2 = 13;//目标界面
	public static final int GAME_TARGET3 = 14;//目标界面
	
	
	//当前游戏状态(默认初始在游戏菜单界面)
	public static int gameState = GAME_MENU;
	
	
	//声明一个Resources实例便于加载图片
	private Resources res = this.getResources();

	//声明游戏需要用到的图片资源(图片声明)
	private Bitmap bmpBackground;//游戏背景
	private Bitmap bmpBackground2;//游戏背景
	private Bitmap bmpBackground3;//游戏背景
    private Bitmap bmpPlay;//游戏开始按钮
    private Bitmap bmpPlayPress;//游戏开始按钮被点击		
	private Bitmap bmpBall;
	private Bitmap bmpPaddle;
	private Bitmap bmpGameWin;//游戏胜利背景
	private Bitmap bmpGameOver;//游戏失败背景
	private Bitmap bmpBallHp;//主角飞机血量
	private Bitmap bmpMenu;//菜单背景
	private Bitmap bmpScore;//计分背景
	private Bitmap bmpReplay;//重新开始
	private Bitmap bmpNext;//下一关
	private Bitmap bmpBack;//返回
	private Bitmap bmpStop;
	private Bitmap bmpStopped;
	private Bitmap bmpMusic;
	private Bitmap bmpNoMusic;
	private Bitmap bmpLevel1;
	private Bitmap bmpLevel2;
	private Bitmap bmpLevel3;
	private Bitmap bmpLevel1_2;
	private Bitmap bmpLevel2_2;
	private Bitmap bmpLevel3_2;
	private Bitmap bmpHelp;
	private Bitmap bmpHelpPage;
	private Bitmap bmpBrick1;
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
	private Bitmap bmpBullet;//子弹
	private Bitmap bmpTarget;
		
	//声明一个菜单对象
	private GameMenu gameMenu;
	private LevelSelect levelSelect;
	private GameOver gameOver;
	private GameOver2 gameOver2;
	private GameOver3 gameOver3;
	private GameWin gameWin;
	private GameWin2 gameWin2;
	private GameWin3 gameWin3;
	private Target target;
	private Target2 target2;
	private Target3 target3;
	
	//声明一个背景对象
	private Background background;	
	private Background2 background2;
	private Background3 background3;
	private Button button;
	private ButtonMusic buttonMusic;
	
    //声明主角对象
	private static Ball ball;
	private static Paddle paddle;
	private static Brick brick;
	private static Brick2 brick2;
	private static Brick3 brick3;
	//敌机子弹容器
    private Vector<Bullet> vcBullet;
	//添加子弹的计数器
	private int countBullet=0;		
	private int countSpeed=0;
	
	//声音相关变量
	static SoundPool soundPool;//声音
	static HashMap<Integer, Integer> soundPoolMap; 
	MediaPlayer mMediaPlayer;	
	public static final int BRICK = 0;//声音常量
	public static final int CATCH_SEED = 1;
	public static final int SPECIAL_SEED = 2;
	public static final int WIN = 3;
	public static final int OVER = 4;
	public static final int LOSE_HP = 5;
	public static final int WALL = 6;
	public static final int SHOOT = 7;	
	public static final int BUTTON = 8;

	public MySurfaceView(Context context) {
		super(context);
		sfh = this.getHolder();
		sfh.addCallback(this);
		paint = new Paint();
		paint.setColor(Color.WHITE);
		paint.setAntiAlias(true);
		setFocusable(true);
		setFocusableInTouchMode(true);
		//设置背景常亮
		this.setKeepScreenOn(true);
		
		//通过服务得到传感器管理对象 
		sm = (SensorManager) MainActivity.instance.getSystemService(Service.SENSOR_SERVICE);
		sensor = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);//得到一个重力传感器实例
		sm.registerListener(this, sensor, SensorManager.SENSOR_DELAY_GAME);
		mySensorListener = new SensorEventListener() {
			
			@Override
			//传感器获取值发生改变时在响应此函数
			public void onSensorChanged(SensorEvent event) {
				//传感器获取值发生改变，在此处理 
				x = event.values[0]; //手机横向翻滚
				//x>0 说明当前手机左翻 x<0右翻     
				y = event.values[1]; //手机纵向翻滚
				//y>0 说明当前手机下翻 y<0上翻
				z = event.values[2]; //屏幕的朝向
				//z>0 手机屏幕朝上 z<0 手机屏幕朝下
				arc_x -= x * 3;
				arc_y += y;
			}

			@Override
			//传感器的精度发生改变时响应此函数
			public void onAccuracyChanged(Sensor sensor, int accuracy) {
				// TODO Auto-generated method stub

			}
		};
		sm.registerListener(mySensorListener, sensor, SensorManager.SENSOR_DELAY_GAME);
			
		
	}
		
		/**
		 * SurfaceView视图创建，响应此函数
		 */
		@Override
		public void surfaceCreated(SurfaceHolder holder) {
			screenW = this.getWidth();
			screenH = this.getHeight();			
			
			arc_x = this.getWidth() / 2;
			arc_y = this.getHeight() / 2;			
			
			initGame();
			flag = true;
			//实例线程
			th = new Thread(this);
			//启动线程
			th.start();

			buttonMusic = new ButtonMusic(bmpMusic, bmpNoMusic);
			initSounds();

		}
		
		private void initGame() {
			
			//放置游戏切入后台重新进入游戏时，游戏被重置!
			//当游戏状态处于菜单时，才会重置游戏
			if (gameState == GAME_MENU||gameState == GAME_LEVEL||
				gameState == GAME_OVER||gameState == GAME_OVER2||gameState == GAME_OVER3||
				gameState == GAME_WIN||gameState == GAME_WIN2||gameState == GAME_WIN3) {
				//加载游戏资源
				bmpBackground = BitmapFactory.decodeResource(res, R.drawable.background);
				bmpBackground2 = BitmapFactory.decodeResource(res, R.drawable.background2);
				bmpBackground3 = BitmapFactory.decodeResource(res, R.drawable.background3);
				bmpPlay = BitmapFactory.decodeResource(res, R.drawable.play);
				bmpPlayPress = BitmapFactory.decodeResource(res, R.drawable.playpress);
				bmpBall = BitmapFactory.decodeResource(res, R.drawable.ball);
				bmpPaddle = BitmapFactory.decodeResource(res, R.drawable.paddle);
				bmpGameWin = BitmapFactory.decodeResource(res, R.drawable.gamewin);
				bmpGameOver = BitmapFactory.decodeResource(res, R.drawable.gameover);
				bmpBallHp = BitmapFactory.decodeResource(res, R.drawable.hp);
				bmpMenu = BitmapFactory.decodeResource(res, R.drawable.menu);
				bmpScore = BitmapFactory.decodeResource(res, R.drawable.score);
				bmpReplay = BitmapFactory.decodeResource(res, R.drawable.replay);
				bmpNext = BitmapFactory.decodeResource(res, R.drawable.next);
				bmpStop = BitmapFactory.decodeResource(res, R.drawable.stop);
				bmpStopped = BitmapFactory.decodeResource(res, R.drawable.stopped);
				bmpMusic = BitmapFactory.decodeResource(res, R.drawable.music);
				bmpNoMusic = BitmapFactory.decodeResource(res, R.drawable.nomusic);
				bmpBack = BitmapFactory.decodeResource(res, R.drawable.back);
				bmpLevel1 = BitmapFactory.decodeResource(res, R.drawable.level1);
				bmpLevel2 = BitmapFactory.decodeResource(res, R.drawable.level2);
				bmpLevel3 = BitmapFactory.decodeResource(res, R.drawable.level3);
				bmpHelp = BitmapFactory.decodeResource(res, R.drawable.help);
				bmpHelpPage = BitmapFactory.decodeResource(res, R.drawable.helppage);
				bmpLevel1_2 = BitmapFactory.decodeResource(res, R.drawable.level1_2);
				bmpLevel2_2 = BitmapFactory.decodeResource(res, R.drawable.level2_2);
				bmpLevel3_2 = BitmapFactory.decodeResource(res, R.drawable.level3_2);
				bmpBrick1 = BitmapFactory.decodeResource(res, R.drawable.brick1);
				bmpBrick2 = BitmapFactory.decodeResource(res, R.drawable.brick2);
				bmpBrick3 = BitmapFactory.decodeResource(res, R.drawable.brick3);
				bmpBrick4 = BitmapFactory.decodeResource(res, R.drawable.brick4);
				bmpBrick5 = BitmapFactory.decodeResource(res, R.drawable.brick5);
				bmpBrick6 = BitmapFactory.decodeResource(res, R.drawable.brick6);
				bmpBrick7 = BitmapFactory.decodeResource(res, R.drawable.brick7);
				bmpBrick8 = BitmapFactory.decodeResource(res, R.drawable.brick8);
				bmpBrick9 = BitmapFactory.decodeResource(res, R.drawable.brick9);
				bmpSeed = BitmapFactory.decodeResource(res, R.drawable.seed);
				bmpSeed1 = BitmapFactory.decodeResource(res, R.drawable.seed1);
				bmpBullet = BitmapFactory.decodeResource(res, R.drawable.bullet);
				bmpTarget = BitmapFactory.decodeResource(res, R.drawable.target);
				
				//实例游戏菜单
				gameMenu = new GameMenu (bmpMenu, bmpPlay, bmpPlayPress);
				//实例关卡选择
				levelSelect = new LevelSelect(bmpLevel1,bmpLevel2,bmpLevel3,bmpHelp);
				//实例目标
				target = new Target(bmpTarget,bmpLevel1_2);
				target2 = new Target2(bmpTarget,bmpLevel2_2);
				target3 = new Target3(bmpTarget,bmpLevel3_2);
				//实例游戏结束
				gameOver = new GameOver(bmpGameOver,bmpBack,bmpReplay);
				gameOver2 = new GameOver2(bmpGameOver,bmpBack,bmpReplay);
				gameOver3 = new GameOver3(bmpGameOver,bmpBack,bmpReplay);
				//实例游戏胜利
				gameWin = new GameWin(bmpGameWin,bmpBack,bmpReplay,bmpNext);
				gameWin2 = new GameWin2(bmpGameWin,bmpBack,bmpReplay,bmpNext);
				gameWin3 = new GameWin3(bmpGameWin,bmpBack,bmpReplay,bmpNext);
				//实例游戏按键
				button = new Button(bmpScore, bmpStop, bmpStopped);
				//实例游戏背景
				background  = new Background(bmpBackground, bmpLevel1_2);
				background2  = new Background2(bmpBackground2, bmpLevel2_2);
				background3  = new Background3(bmpBackground3, bmpLevel3_2);
				//实例paddle
				paddle = new Paddle(bmpPaddle);
				//实例小球
				ball = new Ball(bmpBall, bmpBallHp);
				//实例砖块1
				brick = new Brick(bmpBrick1,bmpBrick2,bmpBrick3,bmpBrick4,bmpBrick5,bmpBrick6,bmpBrick7,bmpBrick8,bmpBrick9,bmpSeed,bmpSeed1);
				brick2 = new Brick2(bmpBrick1,bmpBrick2,bmpBrick3,bmpBrick4,bmpBrick5,bmpBrick6,bmpBrick7,bmpBrick8,bmpBrick9,bmpSeed,bmpSeed1);
				brick3 = new Brick3(bmpBrick1,bmpBrick2,bmpBrick3,bmpBrick4,bmpBrick5,bmpBrick6,bmpBrick7,bmpBrick8,bmpBrick9,bmpSeed,bmpSeed1);
				//敌机子弹容器实例
				vcBullet = new Vector<Bullet>();
				countBullet = 0;
				vcBullet.removeAllElements();
				}	
		
		}

		public void initSounds(){
		     soundPool = new SoundPool(4, AudioManager.STREAM_MUSIC, 100);
		     soundPoolMap = new HashMap<Integer, Integer>();   
		     soundPoolMap.put(BRICK, soundPool.load(MainActivity.instance, R.raw.brick, 1));
		     soundPoolMap.put(CATCH_SEED, soundPool.load(MainActivity.instance, R.raw.catchseed, 1));
		     soundPoolMap.put(SPECIAL_SEED, soundPool.load(MainActivity.instance, R.raw.specialseed, 1));
		     soundPoolMap.put(WIN, soundPool.load(MainActivity.instance, R.raw.gamewin, 1));
		     soundPoolMap.put(OVER, soundPool.load(MainActivity.instance, R.raw.gameover, 1));
		     soundPoolMap.put(LOSE_HP, soundPool.load(MainActivity.instance, R.raw.losehp, 1));
		     soundPoolMap.put(WALL, soundPool.load(MainActivity.instance, R.raw.wall, 1));
		     soundPoolMap.put(SHOOT, soundPool.load(MainActivity.instance, R.raw.shoot, 1));
		     soundPoolMap.put(BUTTON, soundPool.load(MainActivity.instance, R.raw.button, 1));
		}
		//播放声音的方法
		public static void playSound(int sound, int loop) {
		    AudioManager mgr = (AudioManager)MainActivity.instance.getSystemService(Service.AUDIO_SERVICE);   
		    float streamVolumeCurrent = mgr.getStreamVolume(AudioManager.STREAM_MUSIC);   
		    float streamVolumeMax = mgr.getStreamMaxVolume(AudioManager.STREAM_MUSIC);       
		    float volume = streamVolumeCurrent / streamVolumeMax;   	    
		    soundPool.play(soundPoolMap.get(sound), volume, volume, 1, loop, 1f);
		}

		/**
		 * 游戏绘图
		 */
		public void myDraw() {
			try {
				canvas = sfh.lockCanvas();
				if (canvas != null) {
					canvas.drawColor(Color.WHITE);									
						
					//绘图函数根据游戏状态不同进行不同绘制
					switch (gameState) {
					case GAME_MENU:
						//菜单的绘图函数
						gameMenu.draw(canvas, paint);
						break;
					case GAME_LEVEL1:
						background.draw(canvas, paint);	
						buttonMusic.draw(canvas, paint);
						button.draw(canvas,paint);
						paddle.draw(canvas, paint);
						brick.draw(canvas, paint);
						if (ball.setBallExist(true)){
							ball.draw(canvas, paint);
						}	      
						if (brick.bullet_exist ) {				
							//子弹绘制
							for (int i = 0; i < vcBullet.size(); i++) {
								vcBullet.elementAt(i).draw(canvas, paint);
							}
						}
						break;
					case GAME_LEVEL2:
						background2.draw(canvas, paint);
						button.draw(canvas,paint);
						paddle.draw(canvas, paint);
						brick2.draw(canvas, paint);
						if (ball.setBallExist(true)){
							ball.draw(canvas, paint);
						}	      
						if (brick2.bullet_exist ) {				
							//子弹绘制
							for (int i = 0; i < vcBullet.size(); i++) {
								vcBullet.elementAt(i).draw(canvas, paint);
							}
						}
						break;
					case GAME_LEVEL3:
						background3.draw(canvas, paint);
						button.draw(canvas,paint);
						paddle.draw(canvas, paint);
						brick3.draw(canvas, paint);
						if (ball.setBallExist(true)){
							ball.draw(canvas, paint);
						}	      
						if (brick3.bullet_exist ) {				
							//子弹绘制
							for (int i = 0; i < vcBullet.size(); i++) {
								vcBullet.elementAt(i).draw(canvas, paint);
							}
						}
						break;
					case GAME_WIN:
						gameWin.draw(canvas, paint);
						break;
					case GAME_WIN2:
						gameWin2.draw(canvas, paint);
						break;
					case GAME_WIN3:
						gameWin3.draw(canvas, paint);
						break;
					case GAME_OVER:
						gameOver.draw(canvas, paint);
						break;
					case GAME_OVER2:
						gameOver2.draw(canvas, paint);
						break;
					case GAME_OVER3:
						gameOver3.draw(canvas, paint);
						break;
					case GAME_LEVEL:
						levelSelect.draw(canvas,paint);
						break;
					case GAME_HELP:
						canvas.drawBitmap(bmpHelpPage, 0, 0, paint);
						break;
					case GAME_TARGET:
						target.draw(canvas, paint);
						break;
					case GAME_TARGET2:
						target2.draw(canvas, paint);
						break;
					case GAME_TARGET3:
						target3.draw(canvas, paint);
						break;
					}
					buttonMusic.draw(canvas, paint);
				}
			} catch (Exception e) {
				// TODO: handle exception
			} finally {
				if (canvas != null)
					sfh.unlockCanvasAndPost(canvas);
			}
		} 
		
			
	
	//触屏监听
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		//触屏监听事件函数根据游戏状态不同进行不同监听
		buttonMusic.onTouchEvent(event);
		switch (gameState) {
		case GAME_MENU:
			//菜单的触屏事件处理
			gameMenu.onTouchEvent(event);
			break;
		case GAME_LEVEL1:
			button.onTouchEvent(event);
			//paddle.onTouchEvent(event);
			ball.onTouchEvent(event);
			brick.onTouchEvent(event);
			break;
		case GAME_LEVEL2:
			button.onTouchEvent(event);
			//paddle.onTouchEvent(event);
			ball.onTouchEvent(event);
			brick2.onTouchEvent(event);
			break;
		case GAME_LEVEL3:
			button.onTouchEvent(event);
			//paddle.onTouchEvent(event);
			ball.onTouchEvent(event);
			brick3.onTouchEvent(event);
			break;
		case GAME_WIN:
			gameWin.onTouchEvent(event);
			break;
		case GAME_WIN2:
			gameWin2.onTouchEvent(event);
			break;
		case GAME_WIN3:
			gameWin3.onTouchEvent(event);
			break;
		case GAME_OVER:
			gameOver.onTouchEvent(event);
			break;
		case GAME_OVER2:
			gameOver2.onTouchEvent(event);
			break;
		case GAME_OVER3:
			gameOver3.onTouchEvent(event);
			break;
		case GAME_LEVEL:
			levelSelect.onTouchEvent(event);
			break;
		case GAME_HELP:
			break;
		case GAME_TARGET:
			target.onTouchEvent(event);
			break;
		case GAME_TARGET2:
			target2.onTouchEvent(event);
			break;
		case GAME_TARGET3:
			target3.onTouchEvent(event);
			break;
		}
		return true;
	}
	/**
	 * 按键按下事件监听
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		//处理back返回按键
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			//游戏胜利、失败、进行时都默认返回菜单
			if( gameState == GAME_HELP||
                gameState == GAME_WIN ||gameState == GAME_WIN2 ||gameState == GAME_WIN3||
				gameState == GAME_OVER||gameState == GAME_OVER2||gameState == GAME_OVER3||
			    gameState == GAME_LEVEL1 ||gameState == GAME_LEVEL2 ||gameState == GAME_LEVEL3||
			    gameState == GAME_TARGET || gameState == GAME_TARGET2 || gameState == GAME_TARGET3) {
			    gameState = GAME_LEVEL;
			    resetGame();
			    initGame();
			}else if (gameState == GAME_LEVEL ) {
				      //当前游戏状态在菜单界面，默认返回按键退出游戏
				      MainActivity.instance.finish();
				      System.exit(0);
			}
			//表示此按键已处理，不再交给系统处理，
			//从而避免游戏被切入后台
			return true;
		}
	
		return super.onKeyDown(keyCode, event);
	}

	/**
	 * 按键抬起事件监听
	 */
	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		//处理back返回按键
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			//游戏胜利、失败、进行时都默认返回菜单
			if ( gameState == GAME_HELP||
				 gameState == GAME_WIN ||gameState == GAME_WIN2 ||gameState == GAME_WIN3||
				 gameState == GAME_OVER||gameState == GAME_OVER2||gameState == GAME_OVER3||
				 gameState == GAME_LEVEL1 ||gameState == GAME_LEVEL2 ||gameState == GAME_LEVEL3||
				 gameState == GAME_TARGET || gameState == GAME_TARGET2 || gameState == GAME_TARGET3) {
				 gameState = GAME_LEVEL;
			} 		
			//表示此按键已处理，不再交给系统处理，
			//从而避免游戏被切入后台
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	/**
	 * 游戏逻辑
	 */
	private void logic() {
		buttonMusic.MusicOn();
		//逻辑处理根据游戏状态不同进行不同处理
		switch (gameState) {
		case GAME_MENU:
			break;
		//第一关
		case GAME_LEVEL1:
			button.isMoving();
			ball.ballRunning();
			ball.hitWallCheck();
			if (ball.ball_y >= screenH) {
				ball.ballExist = true;
				ball.isMoving = false;
				if (ball.getBallHp()>1){	
					if(buttonMusic.musicOn){ 
						playSound(LOSE_HP, 0);
					}
					ball.setBallExist(false);
					brick.resetSeed();
					ball.resetBall();
					paddle.resetPaddle();
					ball.setBallHp(ball.getBallHp()-1);
					vcBullet.removeAllElements();
					arc_x = screenW / 2;
					arc_y = screenH / 2;
				}else if(ball.getBallHp() <= 1){
					gameState = GAME_OVER;
					if(buttonMusic.musicOn){ 
						playSound(OVER, 0);
					}
				}				
			}
			brick.hitBrickCheck();
			brick.seedDroping();
			if (ball.isMoving == true){
				paddle.SetPx(arc_x);
			}									
			paddle.hitPaddleCheck();
			//子弹逻辑
			if (brick.bullet_exist && brick.isMoving){
				if (countBullet <= 150){//每次子弹发射时长
					countBullet++;				
				if (countBullet % 20 == 0) {//每1秒添加一个主角子弹				
				    vcBullet.add(new Bullet(bmpBullet, paddle.px + 20,paddle.py - 12));
				    vcBullet.add(new Bullet(bmpBullet, paddle.px + bmpPaddle.getWidth() - 30,paddle.py - 12));
				    if(buttonMusic.musicOn){ 
						playSound(SHOOT, 0);
					}
				}
			    //处理子弹逻辑
			    for (int i = 0; i < vcBullet.size(); i++) {
				    Bullet b = vcBullet.elementAt(i);
				    brick.bulletCollision(vcBullet.elementAt(i));
				    if (b.isDead) {
					    vcBullet.removeElement(b);
				    }  else {
					    b.logic();
				    }
			    }
			    }else {
			    	Brick.bullet_exist = false;
			    	countBullet = 0;
			    	vcBullet.removeAllElements();
			    }
			}
			
			//游戏成功逻辑，达到目标不需要打完所有种子打完所有砖块未达到目标失败，
			int count = 0;
            for(boolean s:brick.brick_exist){
				if(!s){
						count++;
					}				
				if(brick.score >= target.seed){
					brick.win = true;	
					brick.win();
				}else if (count == 64 && brick.score < target.seed){
					gameState = GAME_OVER;
					if(buttonMusic.musicOn){ 
						playSound(OVER, 0);
					}
				}
			}
			break;
			
		//第二关
		case GAME_LEVEL2:
			button.isMoving();
			ball.ballRunning();
			ball.hitWallCheck();
			if (ball.ball_y >= screenH) {
				ball.ballExist = true;
				ball.isMoving = false;
				if (ball.getBallHp()>1){	
					if(buttonMusic.musicOn){ 
						playSound(LOSE_HP, 0);
					}
					ball.setBallExist(false);
					brick2.resetSeed();
					ball.resetBall();
					paddle.resetPaddle();
					ball.setBallHp(ball.getBallHp()-1);
					vcBullet.removeAllElements();
					arc_x = screenW / 2;
					arc_y = screenH / 2;
				}else if(ball.getBallHp() <= 1){
					gameState = GAME_OVER2;
					if(buttonMusic.musicOn){ 
						playSound(OVER, 0);
					}
				}				
			}
			brick2.hitBrickCheck();
			brick2.seedDroping();
			if (ball.isMoving == true){
				if (x > 0) {
					paddle.SetPx(arc_x);
				} else if (x < 0) {
					paddle.SetPx(arc_x);
				}
			}									
			paddle.hitPaddleCheck();
			
			//子弹逻辑
			if (Brick2.bullet_exist && Brick2.isMoving){
				if (countBullet <= 150){//每次子弹发射时长
					countBullet++;				
				if (countBullet % 20 == 0) {//每1秒添加一个主角子弹				
				    vcBullet.add(new Bullet(bmpBullet, paddle.px + 20,paddle.py - 12));
				    vcBullet.add(new Bullet(bmpBullet, paddle.px + bmpPaddle.getWidth() - 30,paddle.py - 12));
				    if(buttonMusic.musicOn){ 
						playSound(SHOOT, 0);
					}
				}
			    //处理子弹逻辑
			    for (int i = 0; i < vcBullet.size(); i++) {
				    Bullet b = vcBullet.elementAt(i);
				    brick2.bulletCollision(vcBullet.elementAt(i));
				    if (b.isDead) {
					    vcBullet.removeElement(b);
				    }  else {
					    b.logic();
				    }
			    }
			    }else {
			    	Brick2.bullet_exist = false;
			    	countBullet = 0;
			    	vcBullet.removeAllElements();
			    }
			}
			
			//游戏成功逻辑
			int count2 = 0;
            for(boolean s:brick2.brick_exist){
				if(!s){
						count2++;
					}				
				if(brick2.score >= target2.seed){
					brick2.win = true;	
					brick2.win();
				}else if (count2 == 64 && brick2.score < target2.seed){
					gameState = GAME_OVER2;
					if(buttonMusic.musicOn){ 
						playSound(OVER, 0);
					}
				}
			}
			break;
			
		//第三关
		case GAME_LEVEL3:
			button.isMoving();
			ball.ballRunning();
			ball.hitWallCheck();
			if (ball.ball_y >= screenH) {
				ball.ballExist = true;
				ball.isMoving = false;
				if (ball.getBallHp()>1){	
					if(buttonMusic.musicOn){ 
						playSound(LOSE_HP, 0);
					}
					ball.setBallExist(false);
					brick3.resetSeed();
					ball.resetBall();
					paddle.resetPaddle();
					ball.setBallHp(ball.getBallHp()-1);
					vcBullet.removeAllElements();
					arc_x = screenW / 2;
					arc_y = screenH / 2;
				}else if(ball.getBallHp() <= 1){
					gameState = GAME_OVER3;
					if(buttonMusic.musicOn){ 
						playSound(OVER, 0);
					}
				}				
			}
			brick3.hitBrickCheck();
			brick3.seedDroping();
			if (ball.isMoving == true){
				if (x > 0) {
					paddle.SetPx(arc_x);
				} else if (x < 0) {
					paddle.SetPx(arc_x);
				}
			}									
			paddle.hitPaddleCheck();
			//子弹逻辑
			if (Brick3.bullet_exist && Brick3.isMoving){
				if (countBullet <= 150){//每次子弹发射时长
					countBullet++;				
				if (countBullet % 20 == 0) {//每1秒添加一个主角子弹				
				    vcBullet.add(new Bullet(bmpBullet, paddle.px + 20,paddle.py - 12));
				    vcBullet.add(new Bullet(bmpBullet, paddle.px + bmpPaddle.getWidth() - 30,paddle.py - 12));
				    if(buttonMusic.musicOn){ 
						playSound(SHOOT, 0);
					}
				}
			    //处理子弹逻辑
			    for (int i = 0; i < vcBullet.size(); i++) {
				    Bullet b = vcBullet.elementAt(i);
				    brick3.bulletCollision(vcBullet.elementAt(i));
				    if (b.isDead) {
					    vcBullet.removeElement(b);
				    }  else {
					    b.logic();
				    }
			    }
			    }else {
			    	Brick3.bullet_exist = false;
			    	countBullet = 0;
			    	vcBullet.removeAllElements();
			    }
			}
			
			//游戏成功逻辑
			int count3 = 0;
            for(boolean s:brick3.brick_exist){
				if(!s){
						count3++;
					}				
				if(brick3.score >= target3.seed){
					brick3.win = true;	
					brick3.win();
				}else if (count3 == 64 && brick3.score < target3.seed){
					gameState = GAME_OVER3;
					if(buttonMusic.musicOn){ 
						playSound(OVER, 0);
					}
				}
			}
			break;
		case GAME_WIN:
			Button.isPressStop = true;
			break;
		case GAME_WIN2:
			Button.isPressStop = true;
			break;
		case GAME_WIN3:
			Button.isPressStop = true;
			break;
		case GAME_OVER:
			break;
		case GAME_LEVEL:
			break;
		case GAME_HELP:
			break;
		case GAME_TARGET:
			break;

		}
	}
	
	public static void resetGame(){
		brick.resetGame();
		brick2.resetGame();
		brick3.resetGame();
		ball.resetGame();
		paddle.resetPaddle();
		brick.score = 0;
		brick2.score = 0;
		brick3.score = 0;
		arc_x = screenW / 2 - 25;
		arc_y = screenH / 2 - 25;
	}
		

		@Override
		public void run() {
			while (flag) {
				
				long start = System.currentTimeMillis();
				myDraw();
				logic();
				long end = System.currentTimeMillis();
				try {
					if (end - start < 10) {
						Thread.sleep(10 - (end - start));
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
			}
		}

		
		/**
		 * SurfaceView视图状态发生改变，响应此函数
		 */
		@Override
		public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
		}

		/**
		 * SurfaceView视图消亡时，响应此函数
		 */
		@Override
		public void surfaceDestroyed(SurfaceHolder holder) {
			flag = false;
		}

		@Override
		public void onSensorChanged(SensorEvent event) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onAccuracyChanged(Sensor sensor, int accuracy) {
			// TODO Auto-generated method stub
			
		}
}

				
				
