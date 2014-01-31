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

	private boolean debug = false;
	private int screen_size_x;
	private int screen_size_y;
	private float scaleX;
	private float scaleY;

	private Player player;
	private GameScreen gameScreen;
	private boolean showPassable = false;
	private CanvasHelper canvasHelper;
	private TouchFeedback touchFeedback;
	
	public CanvasView(Context context) {
		super(context);
		player = new Player(context);
		gameScreen = new GameScreen(context);
		canvasHelper = new CanvasHelper();
		touchFeedback = new TouchFeedback(context);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		canvasHelper.setCanvas(canvas);
		screen_size_x = canvas.getWidth();
		screen_size_y = canvas.getHeight();
		scaleX = screen_size_x/(GAME_SIZE_X*1f);
		scaleY = screen_size_y/(GAME_SIZE_Y*1f);

		gameScreen.drawBackground(canvasHelper);
		Paint paint = new Paint();
		paint.setColor(Color.WHITE);
		player.update(gameScreen);
		gameScreen.drawObjects(canvas, true, scaleX, scaleY, player.getPosition().y/10);
		player.drawCharacter(canvas, scaleX, scaleY);
		gameScreen.drawObjects(canvas, false, scaleX, scaleY, player.getPosition().y/10);
//		drawMenu(canvas);
//		drawPopupText(canvas);
		if (showPassable)
			gameScreen.drawPassable(canvas, scaleX, scaleY);
		if (debug) {
			drawDebug(canvas);
		}
		touchFeedback.draw(canvasHelper);
		// draw text popup
		if (gameScreen.endGame) {
			drawEndGame(canvas, scaleX, scaleY);
		}
		this.invalidate();
	}

	private void drawMenu(Canvas canvas) {
		Paint paint = new Paint();
		paint.setColor(Color.GRAY);
		canvas.drawRoundRect(new RectF(0f,0f,50f*scaleX,50f*scaleY), 10f, 10f, paint);
	}

	private void drawDebug(Canvas canvas) {
		Paint paint = new Paint();
		paint.setColor(Color.WHITE);
		paint.setTextSize(30);
		String text = "pos:"+player.getPosition().x+","+player.getPosition().y;		
		canvas.drawText(text, 500, 30, paint);
	}

	private void drawEndGame(Canvas canvas, float scaleX, float scaleY) {
		// every draw needs to be scaled
		Paint paint1 = new Paint();
		paint1.setColor(Color.WHITE);
		canvas.drawRoundRect(new RectF(300f*scaleX,100f*scaleY,700f*scaleX,300f*scaleY), 10f, 10f, paint1);
		paint1.setColor(Color.BLACK);
		canvas.drawRoundRect(new RectF(305f*scaleX,105f*scaleY,695f*scaleX,295f*scaleY), 10f, 10f, paint1);
		paint1.setColor(Color.LTGRAY);
		canvas.drawRoundRect(new RectF(420f*scaleX,220f*scaleY,580f*scaleX,270f*scaleY), 10f, 10f, paint1);
		Paint paint = new Paint();
		paint.setColor(Color.GREEN);
		paint.setTextSize(30);
		String text1 = "Congratulations!";
		canvas.drawText(text1, 330*scaleX, 150*scaleY, paint);
		String text2 = "You got the scepter!";
		canvas.drawText(text2, 330*scaleX, 180*scaleY, paint);
		paint.setColor(Color.BLACK);
		String text3 = "Restart";		
		canvas.drawText(text3, 450*scaleX, 255*scaleY, paint);
		
	}

	@Override
	public boolean onTouchEvent(MotionEvent e) {
	    float x = e.getX();
	    float y = e.getY();
	    if (e.getAction() == MotionEvent.ACTION_UP) {
	    	touchFeedback.addTouchEvent((int)(x/scaleX), (int)(y/scaleY));
	    	if (!gameScreen.endGame) {
		    	// check for a game object that is interactable first
		    	int interactID = gameScreen.interactCheck((int)(x/scaleX),(int)(y/scaleY), player.getPosition());
		    	if (interactID == 0) {
		    		// player wants to move
		    		// calculate tile touched
				    int tileX = (int)(x / scaleX)/10;
				    int tileY = (int)(y / scaleY)/10;
	
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
		    		gameScreen.playerInteract(interactID, player);
		    	}
	    	} else {
	    		int tileX = (int)(x / scaleX)/10;
			    int tileY = (int)(y / scaleY)/10;
			    if ((tileX>42)&&(tileX<58)&&(tileY>20)&&(tileY<29)) {
			    	gameScreen.restart();
			    	player.removeGlasses();
			    	player.setCharacterPosition(20, 40);
			    	gameScreen.endGame = false;
			    }
			}
	    }
	    return true;
	}
}
