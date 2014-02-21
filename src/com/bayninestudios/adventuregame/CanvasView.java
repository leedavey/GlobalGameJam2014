package com.bayninestudios.adventuregame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class CanvasView extends View {

	public static final int GAME_SIZE_X = 1000;
	public static final int GAME_SIZE_Y = 600;

	private boolean debug = true;
	private int screen_size_x;
	private int screen_size_y;
	private float scaleX;
	private float scaleY;

	private Player player;
	private GameScreen gameScreen;
	private boolean showPassable = false;
	private CanvasHelper canvasHelper;
	private TouchFeedback touchFeedback;
	private PopupMessage popup;
	
	public boolean endGame = false;

	public CanvasView(Context context) {
		super(context);
		player = new Player(context);
		gameScreen = new GameScreen(context);
		canvasHelper = new CanvasHelper();
		touchFeedback = new TouchFeedback(context);
		popup = new PopupMessage();
		popup.displayPopup("Welcome!");
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		canvasHelper.setCanvas(canvas);

		// TODO remove these and replace with canvasHelper
		screen_size_x = canvas.getWidth();
		screen_size_y = canvas.getHeight();
		scaleX = screen_size_x/(GAME_SIZE_X*1f);
		scaleY = screen_size_y/(GAME_SIZE_Y*1f);

		gameScreen.drawBackground(canvasHelper);
		player.update(gameScreen);
		gameScreen.drawObjects(canvas, true, scaleX, scaleY, player.getPosition().y/10);
		player.drawCharacter(canvas, scaleX, scaleY);
		gameScreen.drawObjects(canvas, false, scaleX, scaleY, player.getPosition().y/10);
		if (popup.showing) {
			popup.draw(canvasHelper);
		}

		if (showPassable)
			gameScreen.drawPassable(canvas, scaleX, scaleY);
		if (debug) {
			drawDebug(canvas);
		}
		touchFeedback.draw(canvasHelper);

		this.invalidate();
	}

	private void drawDebug(Canvas canvas) {
		Paint paint = new Paint();
		paint.setColor(Color.WHITE);
		paint.setTextSize(30);
		String text = "pos:"+player.getPosition().x+","+player.getPosition().y;		
		canvas.drawText(text, 500, 30, paint);
	}

	@Override
	public boolean onTouchEvent(MotionEvent e) {
	    float x = e.getX();
	    float y = e.getY();
	    if (e.getAction() == MotionEvent.ACTION_UP) {
	    	// show touch
	    	touchFeedback.addTouchEvent((int)(x/scaleX), (int)(y/scaleY));

	    	// calculate tile touched
    		int tileX = (int)(x / scaleX)/10;
		    int tileY = (int)(y / scaleY)/10;

		    // popup showing, don't move
	    	if (popup.showing) {
	    		// send the touch event to the popup instead
	    		// it may return some kind of interact event
	    		// process that after
			    if ((tileX>42)&&(tileX<58)&&(tileY>20)&&(tileY<29)) {
			    	popup.showing = false;
			    	if (endGame) {
				    	gameScreen.restart();
				    	player.removeGlasses();
				    	player.setCharacterPosition(20, 40);
				    	endGame = false;
				    }
			    }
	    	} else {
		    	// check for a game object that is interactable first
		    	int interactID = gameScreen.interactCheck((int)(x/scaleX),(int)(y/scaleY), player.getPosition());
		    	if (interactID == 0) {
		    		// player wants to move
				    boolean shouldMove = true;
				    if ((tileX < 6) && (tileY < 6)) {
					    if (debug == true) {
					    	showPassable = !showPassable;
					    	shouldMove = false;
					    }
				    }
				    if ((tileX > 90) && (tileY < 10)) {
				    	if (player.hasGlasses()) {
					    	gameScreen.activateGlasses();
					    	shouldMove = false;
				    	}
				    }
					if (shouldMove)
					    player.setMoveTo(tileX, tileY);
		    	} else {
		    		// player wants to interact with the world
		    		playerInteract(interactID, player);
		    	}
			}
	    }
	    return true;
	}

	private void playerInteract(int interactID, Player player) {
		if (interactID == 1) {
			player.receiveGlasses();
			popup.displayPopup("You receive glasses!");
		} else if (interactID == 2) {
			endGame = true;
			popup.showing = true;
		}
	}
}
