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
import android.util.Log;

public class GameScreen {
	static public final int SCN_FIELD = 1;
	static public final int SCN_WATERFALL = 2;
	static public final int SCN_CAVE_ENTRANCE = 3;
	static public final int SCN_STUMP = 4;

	private final int GAME_SIZE_X = 1000;
	private final int GAME_SIZE_Y = 600;

	private int passable[][] = new int[100][64];
	private ArrayList<Bitmap> gameObjectBmps;
	private ArrayList<GameObject> gameObjects;
//	private SparkleEffect sparkle;
	private int currentBackground;
	private int currentScreen = GameScreen.SCN_FIELD;
	
	// these need refactoring, don't know how yet
	public int boundW;
	public int nextW;
	public int boundE;
	public int nextE;
	public int boundN;
	public int nextN;
	public int boundS;
	public int nextS;
	
	public boolean endGame = false;

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
		gameObjectBmps.add(BitmapFactory.decodeResource(context.getResources(), // 11
                R.drawable.background4, opts));
		gameObjectBmps.add(BitmapFactory.decodeResource(context.getResources(), // 12
                R.drawable.flower1, opts));
		gameObjectBmps.add(BitmapFactory.decodeResource(context.getResources(), // 13
                R.drawable.chest, opts));

		setScreen(currentScreen);
	}

	private void setPassableSquare(int x1, int y1, int x2, int y2) {
		for (int x = x1; x < x2; x++) {
			for (int y = y1; y < y2; y++) {
				passable[x][y] = 1;
			}
		}
	}
	
	public void drawBackground(CanvasHelper canvasHelper) {
		canvasHelper.drawBitmap(gameObjectBmps.get(currentBackground),
				new Rect(0,0,GAME_SIZE_X,GAME_SIZE_Y),
				new Rect(0,0,GAME_SIZE_X,GAME_SIZE_Y));
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

	// move this code to the game object class
	public void drawObjects(Canvas canvas, boolean background, float scaleX, float scaleY, int playerY) {
		for (GameObject obj:gameObjects) {
			if (obj.visible) {
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
	}
	
	public int getTileType(int x, int y) {
		return passable[x][y];
	}

	public int getScreenId() {
		return currentScreen;
	}

	public void setScreen(int screen) {
		Random rand = new Random();
		if (screen == GameScreen.SCN_FIELD) {
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
			for (int i = 0; i < 20; i++) {
				int x = rand.nextInt(60)+20;
				int y = rand.nextInt(30)+20;
				gameObjects.add(new GameObject(x,y,12,false,false,0));
			}

			passable = new int[GAME_SIZE_X][GAME_SIZE_Y];
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
			setNextScene(GameScreen.SCN_WATERFALL,GameScreen.SCN_STUMP,0,0);
			
//			sparkle = new SparkleEffect(new Rect(0,550,1000,600), Color.WHITE, 1);
		} else if (screen == GameScreen.SCN_WATERFALL) {
			currentBackground = 7;
			gameObjects = new ArrayList<GameObject>();
			gameObjects.add(new GameObject(61,21,6));
			gameObjects.add(new GameObject(29,50,10,false,false,0));

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
			setNextScene(0,GameScreen.SCN_FIELD,GameScreen.SCN_CAVE_ENTRANCE,0);

//			sparkle = new SparkleEffect(new Rect(0,550,1000,600), Color.BLUE, 0);
		} else if (screen == GameScreen.SCN_CAVE_ENTRANCE) {
			currentBackground = 9;
			gameObjects = new ArrayList<GameObject>();
			gameObjects.add(new GameObject(63,28,13,true,false,2));

			passable = new int[GAME_SIZE_X][GAME_SIZE_Y];
			setPassableSteps(14, 42, 53, false);
			setPassableSquare(40, 27, 78, 28);
			setPassableSquare(40, 27, 78, 28);
			setPassableSquare(60, 28, 70, 29);
			setPassableSteps(75, 88, 29, true);
			setPassableSquare(87,42,88,44);
			setPassableSteps(87, 96, 45, true);


			// bottom
			setPassableSquare(0, 54, 53, 55);
			setPassableSquare(62, 54, 100, 55);

			setPassableSquare(52, 54, 53, 60);
			setPassableSquare(62, 54, 63, 60);

			setBounds(90, 910, 190, 550);
			setNextScene(0,0,0,GameScreen.SCN_WATERFALL);
			
		} else if (screen == GameScreen.SCN_STUMP) {
			Log.d("log","in here");
			currentBackground = 11;
			gameObjects = new ArrayList<GameObject>();
			gameObjects.add(new GameObject(45,35,5,true,true,1));

			passable = new int[GAME_SIZE_X][GAME_SIZE_Y];
			// stump
			setPassableSquare(39, 32, 53, 36);

			setPassableSquare(69, 16, 70, 23);
			setPassableSquare(70, 23, 71, 28);
			setPassableSquare(71, 28, 72, 33);
			setPassableSquare(72, 33, 73, 37);
			setPassableSquare(73, 37, 75, 38);
			setPassableSquare(74, 37, 75, 42);
			setPassableSquare(75, 42, 76, 45);
			setPassableSquare(76, 44, 77, 47);
			setPassableSquare(77, 46, 78, 56);

			setBounds(90, 910, 190, 570);
			setNextScene(GameScreen.SCN_FIELD,0,0,0);
		}
	}
	
	private void setPassableSteps(int x1, int x2, int y, boolean invert) {
		for (int x = x1; x < x2; x++) {
			passable[x][y] = 1;
			passable[x][y-1] = 1;
			if (invert)
				y++;
			else
				y--;
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

	public int interactCheck(int x, int y, Vector2 playerPos) {
		int returnVal = 0;
		for (GameObject obj:gameObjects) {
			if ((obj.visible) && (obj.interact)) {
				// all these calculations should be in FINE game world coords
				// did the player touch the object?
				Vector2 pos = obj.position;
				Bitmap bmp = gameObjectBmps.get(obj.objectBitmap);
				int x1 = (pos.x*10)-(bmp.getWidth()/2);
				int x2 = (pos.x*10)+(bmp.getWidth()/2);
				int y1 = (pos.y*10)-(bmp.getHeight());
				int y2 = (pos.y*10);
				if ((x > x1)&&(x<x2)&&(y>y1)&&(y<y2)) {
					// and are they close enough?
					int diffX = (pos.x*10)-playerPos.x;
					int diffY = (pos.y*10)-playerPos.y;
					float distance = (float)(Math.sqrt((double)(diffX*diffX)+(diffY*diffY)));
					if (distance < 75f)
						returnVal = obj.interactID;
				}
				
			}
		}
		return returnVal;
	}

	public void playerInteract(int interactID, Player player) {
		Log.d("log","INTERACT!");
		if (interactID == 1) {
			player.receiveGlasses();
		} else if (interactID == 2) {
			endGame = true;
		}
	}

	public void activateGlasses() {
		for (GameObject obj:gameObjects) {
			if (!obj.visible) {
				obj.visible = true;
			}
		}
	}

	public void restart() {
		currentScreen = GameScreen.SCN_FIELD;
		setScreen(currentScreen);
	}
}
