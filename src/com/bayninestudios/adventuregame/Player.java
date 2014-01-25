package com.bayninestudios.adventuregame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;

public class Player {

	private float walkSpeed = 3f;
	private Bitmap playerBmp;
	private Vector2 characterPos;
	private Vector2 moveTo;
	private int frame = 0;
	private int frameRow = 2;
	private int frameTime = 0;
	private int frameLimit = 10;

	public Player(Context context) {
		BitmapFactory.Options opts = new BitmapFactory.Options();
		opts.inScaled = false;
		playerBmp = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.gb_walk, opts);
		moveTo = new Vector2(20,40);
		characterPos = new Vector2(200,400);
	}

	public void drawCharacter(Canvas canvas, float scaleX, float scaleY) {
		if (frameRow != 2) {
			frameTime++;
			if (frameTime > frameLimit) {
				frameTime = 0;
				frame++;
				if (frame == 6)
					frame = 0;
			}
		}
		int sizeX = 104;
		int sizeY = 151;
		int ssizeX = (int)(sizeX * scaleX);
		int ssizeY = (int)(sizeY * scaleY);
		int cX = (int)(characterPos.x * scaleX);
		int cY = (int)(characterPos.y * scaleY);
		canvas.drawBitmap(playerBmp,
				new Rect(sizeX*frame,sizeY*frameRow,(sizeX*frame)+sizeX,(sizeY*frameRow)+sizeY),
				new Rect(cX-(ssizeX/2),cY-ssizeY,cX+(ssizeX/2),cY),
				null);
	}

	public void setPosition(int x, int y) {
		characterPos.setXY(x,y);
	}

	public void setMoveTo(int x, int y) {
		moveTo.setXY(x,y);
	}

	public void update(GameScreen gameScreen) {
		// move player if needed
		if ((characterPos.x != moveTo.x*10) || (characterPos.y != moveTo.y*10)) {
			float dirX = moveTo.x*10f-characterPos.x;
			// use X direction to determine which direction walking sprite to use
			if (dirX > 0) {
				frameRow = 0;
			} else {
				frameRow = 1;
			}
			float dirY = moveTo.y*10f-characterPos.y;
			float length = (float) Math.sqrt((double)(dirX * dirX + dirY * dirY));
			if (length < 10f) {
				if (gameScreen.getTileType(moveTo.x,moveTo.y) == 0) {
					characterPos.x = moveTo.x*10;
					characterPos.y = moveTo.y*10;
				} else {
					// too close to the move to point which is impassable
					stopMovement();
				}
			} else {
				float normX = dirX / length;
				float normY = dirY / length;
//				Log.d("debug","xv: "+normX+", yv: "+normY);
				Vector2 futureTile = new Vector2((characterPos.x+(int)(normX * walkSpeed))/10,(characterPos.y+(int)(normY * walkSpeed))/10);
				if (gameScreen.getTileType(futureTile.x,futureTile.y) == 0) {
					characterPos.x += (int)(normX * walkSpeed);
					characterPos.y += (int)(normY * walkSpeed);
				} else {
					stopMovement();
				}
			}
		} else {
			// not moving reset walking frame to standing
			if (frameRow == 0) {
				frameRow = 2;
				frame = 0;
			}
			if (frameRow == 1) {
				frameRow = 2;
				frame = 1;
			}
		}
			
	}

	private void stopMovement() {
		moveTo.x = characterPos.x/10;
		moveTo.y = characterPos.y/10;
		characterPos.x = moveTo.x*10;
		characterPos.y = moveTo.y*10;
	}

	public Vector2 getPosition() {
		return characterPos;
	}
}
