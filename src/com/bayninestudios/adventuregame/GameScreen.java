package com.bayninestudios.adventuregame;

import java.util.ArrayList;
import java.util.Random;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

public class GameScreen {
	static public final int SCN_STUMP = 1;
	static public final int SCN_WATERFALL = 2;
	static public final int SCN_CAVE_ENTRANCE = 3;

	private final int GAME_SIZE_X = 1000;
	private final int GAME_SIZE_Y = 600;

	private int passable[][] = new int[100][64];
	private ArrayList<Bitmap> gameObjectBmps;
	private ArrayList<GameObject> gameObjects;
//	private SparkleEffect sparkle;
	private int currentBackground;
	private int currentScreen = GameScreen.SCN_STUMP;
	
	public int boundW;
	public int nextW;
	public int boundE;
	public int nextE;
	public int boundN;
	public int nextN;
	public int boundS;
	public int nextS;

	public GameScreen(Context context) {
		gameObjectBmps = new ArrayList<Bitmap>();

		// ask the bitmap factory not to scale the loaded bitmaps
		BitmapFactory.Options opts = new BitmapFactory.Options();
		opts.inScaled = false;
		gameObjectBmps.add(BitmapFactory.decodeResource(context.getResources(), // 0
                R.drawable.background1, opts));
		gameObjectBmps.add(BitmapFactory.decodeResource(context.getResources(), // 1
                R.drawable.tree1, opts));
		gameObjectBmps.add(BitmapFactory.decodeResource(context.getResources(), // 2
                R.drawable.tree2, opts));
		gameObjectBmps.add(BitmapFactory.decodeResource(context.getResources(), // 3
                R.drawable.grass1, opts));
		gameObjectBmps.add(BitmapFactory.decodeResource(context.getResources(), // 4
                R.drawable.grass2, opts));
		gameObjectBmps.add(BitmapFactory.decodeResource(context.getResources(), // 5
                R.drawable.trunk1, opts));
		gameObjectBmps.add(BitmapFactory.decodeResource(context.getResources(), // 6
                R.drawable.rock1, opts));
		gameObjectBmps.add(BitmapFactory.decodeResource(context.getResources(), // 7
                R.drawable.background2, opts));
		gameObjectBmps.add(BitmapFactory.decodeResource(context.getResources(), // 8
                R.drawable.bush1, opts));
		gameObjectBmps.add(BitmapFactory.decodeResource(context.getResources(), // 9
                R.drawable.background3, opts));
		gameObjectBmps.add(BitmapFactory.decodeResource(context.getResources(), // 10
                R.drawable.waterfall1, opts));

		setScreen(currentScreen);
	}

	private void setPassableSquare(int x1, int y1, int x2, int y2) {
		for (int x = x1; x < x2; x++) {
			for (int y = y1; y < y2; y++) {
				passable[x][y] = 1;
			}
		}
	}
	
	public void drawBackground(Canvas canvas, float scaleX, float scaleY) {
		canvas.drawBitmap(gameObjectBmps.get(currentBackground),
				new Rect(0,0,GAME_SIZE_X,GAME_SIZE_Y),
				new Rect(0,0,(int)(GAME_SIZE_X*scaleX),(int)(GAME_SIZE_Y*scaleY)), null);
	}

	public void drawPassable(Canvas canvas, float scaleX, float scaleY) {
		int sizeX = (int)(10 * scaleX);
		int sizeY = (int)(10 * scaleY);
		for (int x = 0; x < 100; x++) {
			for (int y = 0; y < 64; y++) {
				if (passable[x][y] == 1) {
					Paint paint = new Paint();
					paint.setColor(Color.WHITE);
					int x1 = (int)(x * 10 * scaleX);
					int y1 = (int)(y * 10 * scaleY);
					canvas.drawRect(new Rect(x1,y1,x1+sizeX,y1+sizeY), paint);
				}
			}
		}
	}

	public void drawObjects(Canvas canvas, boolean background, float scaleX, float scaleY, int playerY) {
		for (GameObject obj:gameObjects) {
			if (((obj.position.y < playerY) && background) ||
				    ((obj.position.y >= playerY) && !background)) {
					Bitmap bmp = gameObjectBmps.get(obj.objectBitmap);
					canvas.drawBitmap(bmp,
							new Rect(0,0,bmp.getWidth(), bmp.getHeight()),
							new Rect((int)(obj.position.x*10*scaleX-((bmp.getWidth()/2)*scaleX)),
									(int)((obj.position.y*10-bmp.getHeight())*scaleY),
									(int)(obj.position.x*10*scaleX+((bmp.getWidth()/2)*scaleX)),
									(int)(obj.position.y*10*scaleY)), null);			
				}
		}
	}
	
	public int getTileType(int x, int y) {
		return passable[x][y];
	}

	public int getScreenId() {
		return currentScreen;
	}

	public void setScreen(int screen) {
		Random rand = new Random();
		if (screen == GameScreen.SCN_STUMP) {
			currentBackground = 0;
			gameObjects = new ArrayList<GameObject>();
			gameObjects.add(new GameObject(78,13,8));
			gameObjects.add(new GameObject(90,16,6));
			gameObjects.add(new GameObject(95,17,8));
			gameObjects.add(new GameObject(80,17,2));
			gameObjects.add(new GameObject(20,15,2));
			gameObjects.add(new GameObject(5,19,8));
			gameObjects.add(new GameObject(15,18,8));
			gameObjects.add(new GameObject(60,25,1));
			gameObjects.add(new GameObject(10,20,2));
			gameObjects.add(new GameObject(85,45,1));
			// TODO: random grass, need to add in order of Y
			for (int i = 0; i < 5; i++) {
				int x = rand.nextInt(100);
				int y = rand.nextInt(20)+30;
				int b = rand.nextInt(2)+3;
				gameObjects.add(new GameObject(x,y,b));
			}
			gameObjects.add(new GameObject(45,35,5));

			passable = new int[GAME_SIZE_X][GAME_SIZE_Y];
			// stump
			setPassableSquare(39, 32, 53, 36);
			// river
//			setPassableSquare(0, RIVER_TOP, 100, RIVER_TOP+1);
			// Left trees
			setPassableSquare(0, 20, 16, 21);
			setPassableSquare(16, 15, 17, 21);
			setPassableSquare(16, 15, 25, 16);
			setPassableSquare(25, 0, 26, 16);
			// right trees
			setPassableSquare(74, 0, 75, 18);
			setPassableSquare(74, 17, 100, 18);
			
			setBounds(90, 910, 90, 530);
			setNextScene(GameScreen.SCN_WATERFALL,0,0,0);
			
//			sparkle = new SparkleEffect(new Rect(0,550,1000,600), Color.WHITE, 1);
		}
		if (screen == GameScreen.SCN_WATERFALL) {
			currentBackground = 7;
			gameObjects = new ArrayList<GameObject>();
			gameObjects.add(new GameObject(61,21,6));
			gameObjects.add(new GameObject(29,50,10));

			passable = new int[GAME_SIZE_X][GAME_SIZE_Y];
			// top cave entrance
			setPassableSquare(28, 18, 69, 19);
			setPassableSquare(58, 19, 67, 20);
			setPassableSquare(59, 20, 66, 21);
			setPassableSquare(76, 18, 100, 19);

			setPassableSquare(27, 18, 28, 25);
			setPassableSquare(26, 25, 27, 30);
			setPassableSquare(25, 30, 26, 35);
			setPassableSquare(24, 35, 25, 40);
			// river
//			setPassableSquare(50, RIVER_TOP, 100, RIVER_TOP+1);
			setPassableSquare(24, 40, 54, 41);
			setPassableSquare(53, 41, 54, 53);

			setBounds(90, 910, 190, 530);
			setNextScene(0,GameScreen.SCN_STUMP,GameScreen.SCN_CAVE_ENTRANCE,0);

//			sparkle = new SparkleEffect(new Rect(0,550,1000,600), Color.BLUE, 0);
		}
		if (screen == GameScreen.SCN_CAVE_ENTRANCE) {
			currentBackground = 9;
			gameObjects = new ArrayList<GameObject>();

			passable = new int[GAME_SIZE_X][GAME_SIZE_Y];
			setBounds(90, 910, 190, 570);
			setNextScene(0,0,0,GameScreen.SCN_WATERFALL);
		}
	}
	
	private void setBounds(int w, int e, int n, int s) {
		this.boundW = w;
		this.boundE = e;
		this.boundN = n;
		this.boundS = s;
	}

	private void setNextScene(int w, int e, int n, int s) {
		this.nextW = w;
		this.nextE = e;
		this.nextN = n;
		this.nextS = s;
	}
}
