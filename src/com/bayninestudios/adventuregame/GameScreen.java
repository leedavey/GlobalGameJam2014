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
	private final int GAME_SIZE_X = 1000;
	private final int GAME_SIZE_Y = 600;

	private int passable[][] = new int[100][64];
	private ArrayList<Bitmap> gameObjectBmps;
	private ArrayList<GameObject> gameObjects;
	private int currentBackground = 0;

	public GameScreen(Context context) {
		loadLevel(0);
		Random rand = new Random();
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
		
		gameObjects = new ArrayList<GameObject>();
		gameObjects.add(new GameObject(80,17,2));
		gameObjects.add(new GameObject(20,20,2));
		gameObjects.add(new GameObject(60,25,1));
		gameObjects.add(new GameObject(10,25,2));
		gameObjects.add(new GameObject(58,30,1));
		// TODO: random grass, need to add in order of Y
		for (int i = 0; i < 5; i++) {
			int x = rand.nextInt(100);
			int y = rand.nextInt(20)+30;
			int b = rand.nextInt(2)+3;
			gameObjects.add(new GameObject(x,y,b));
		}
//		gameObjects.add(new GameObject(40,45,6));
		gameObjects.add(new GameObject(45,35,5));
		// stump
		setPassableSquare(39, 32, 53, 36);
		// river
		setPassableSquare(0, 53, 100, 54);
		// Left trees
		setPassableSquare(0, 25, 16, 26);
		setPassableSquare(16, 20, 17, 26);
		setPassableSquare(16, 20, 25, 21);
		setPassableSquare(25, 0, 26, 21);
		// right trees
		setPassableSquare(74, 0, 75, 18);
		setPassableSquare(74, 17, 100, 18);
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

	public void setBackground(int i) {
		currentBackground = 7;
		gameObjects = new ArrayList<GameObject>();
	}

	private void loadLevel(int i) {
	}

}
