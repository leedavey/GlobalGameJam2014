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

	public GameScreen(Context context) {
		Random rand = new Random();
		gameObjectBmps = new ArrayList<Bitmap>();

		// ask the bitmap factory not to scale the loaded bitmaps
		BitmapFactory.Options opts = new BitmapFactory.Options();
		opts.inScaled = false;
		gameObjectBmps.add(BitmapFactory.decodeResource(context.getResources(),
                R.drawable.adventurebackground, opts));
		gameObjectBmps.add(BitmapFactory.decodeResource(context.getResources(),
                R.drawable.tree1, opts));
		gameObjectBmps.add(BitmapFactory.decodeResource(context.getResources(),
                R.drawable.tree2, opts));
		gameObjectBmps.add(BitmapFactory.decodeResource(context.getResources(),
                R.drawable.grass1, opts));
		gameObjectBmps.add(BitmapFactory.decodeResource(context.getResources(),
                R.drawable.grass2, opts));
		gameObjectBmps.add(BitmapFactory.decodeResource(context.getResources(),
                R.drawable.trunk1, opts));
		gameObjectBmps.add(BitmapFactory.decodeResource(context.getResources(),
                R.drawable.rock1, opts));
		
		
		gameObjects = new ArrayList<GameObject>();
		gameObjects.add(new GameObject(80,27,2));
		gameObjects.add(new GameObject(20,30,2));
		gameObjects.add(new GameObject(70,35,1));
		gameObjects.add(new GameObject(10,35,2));
		gameObjects.add(new GameObject(90,40,1));
		// TODO: random grass, need to add in order of Y
		for (int i = 0; i < 5; i++) {
			int x = rand.nextInt(100);
			int y = rand.nextInt(20)+40;
			int b = rand.nextInt(2)+3;
			gameObjects.add(new GameObject(x,y,b));
		}
//		gameObjects.add(new GameObject(40,45,6));
		gameObjects.add(new GameObject(40,45,5));
		setPassableSquare(35, 42, 46, 46);
		
	}

	private void setPassableSquare(int x1, int y1, int x2, int y2) {
		for (int x = x1; x < x2; x++) {
			for (int y = y1; y < y2; y++) {
				passable[x][y] = 1;
			}
		}
	}
	
	public void drawBackground(Canvas canvas, float scaleX, float scaleY) {
		canvas.drawBitmap(gameObjectBmps.get(0),
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

}
