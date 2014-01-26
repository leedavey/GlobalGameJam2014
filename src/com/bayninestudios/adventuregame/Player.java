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
	private Bitmap glassesBmp;
	private Vector2 characterPos;
	private Vector2 moveTo;
	private int frame = 0;
	private int frameRow = 2;
	private int frameTime = 0;
	private int frameLimit = 10;
	private boolean glasses = false;

	public Player(Context context) {
		BitmapFactory.Options opts = new BitmapFactory.Options();
		opts.inScaled = false;
		playerBmp = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.gb_walk2, opts);
		glassesBmp = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.glasses, opts);
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
		if (glasses == true) {
			canvas.drawBitmap(glassesBmp, new Rect(0,0,glassesBmp.getWidth(),glassesBmp.getHeight()),
					new Rect((int)(900*scaleX),(int)(25*scaleY),(int)(1000*scaleX),(int)(75*scaleY)), null);
		}
	}

	public void setPosition(int x, int y) {
		characterPos.setXY(x,y);
	}

	public void setMoveTo(int x, int y) {
		moveTo.setXY(x,y);
	}

	public void update(GameScreen gameScreen) {
		// TODO: Need refactoring on the bounds check, don't know how yet.
		if (characterPos.x < gameScreen.boundW) {
			Log.d("log","out of bounds W");
			if (gameScreen.nextW != 0) {
				gameScreen.setScreen(gameScreen.nextW);
				setCharacterPosition(90, characterPos.y/10);
			} else {
				characterPos.x = gameScreen.boundW;
				stopMovement();
			}
		}
		if (characterPos.x > gameScreen.boundE) {
			Log.d("log","out of bounds E");
			if (gameScreen.nextE != 0) {
				gameScreen.setScreen(gameScreen.nextE);
				setCharacterPosition(10, characterPos.y/10);
			} else {
				characterPos.x = gameScreen.boundE;
				stopMovement();
			}
		}
		if (characterPos.y > gameScreen.boundS) {
			if (gameScreen.nextS != 0) {
				if (gameScreen.nextS == GameScreen.SCN_WATERFALL)
					setCharacterPosition(73, 21);
				else
					setCharacterPosition(characterPos.x/10, 10);
				gameScreen.setScreen(gameScreen.nextS);
			} else {
				characterPos.y = gameScreen.boundS;
				stopMovement();
			}
		}
		if (characterPos.y < gameScreen.boundN) {
			if (gameScreen.nextN != 0) {
				gameScreen.setScreen(gameScreen.nextN);
				setCharacterPosition(characterPos.x/10, 50);
			} else {
				characterPos.y = gameScreen.boundN;
				stopMovement();
			}
		}
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

	// using game coords, set the character position and
	// the move to coords
	public void setCharacterPosition(int x, int y) {
		moveTo.x = x;
		moveTo.y = y;		
		characterPos.x = moveTo.x*10;
		characterPos.y = moveTo.y*10;
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

	public void receiveGlasses() {
		this.glasses = true;
	}

	public void removeGlasses() {
		this.glasses = false;
	}

	public boolean hasGlasses() {
		return glasses;
	}
}
