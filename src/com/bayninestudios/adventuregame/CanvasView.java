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

	private final int GAME_SIZE_X = 1000;
	private final int GAME_SIZE_Y = 600;
	private int screen_size_x;
	private int screen_size_y;
	private float scaleX;
	private float scaleY;

	private Player player;
	private GameScreen gameScreen;
	
	public CanvasView(Context context) {
		super(context);
		player = new Player(context);
		gameScreen = new GameScreen(context);

	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		screen_size_x = canvas.getWidth();
		screen_size_y = canvas.getHeight();
		scaleX = screen_size_x/(GAME_SIZE_X*1f);
		scaleY = screen_size_y/(GAME_SIZE_Y*1f);

		gameScreen.drawBackground(canvas, scaleX, scaleY);
		player.update(gameScreen);
		gameScreen.drawObjects(canvas, true, scaleX, scaleY, player.getPosition().y/10);
		player.drawCharacter(canvas, scaleX, scaleY);
		gameScreen.drawObjects(canvas, false, scaleX, scaleY, player.getPosition().y/10);
//		drawPopupText(canvas);
//		gameScreen.drawPassable(canvas, scaleX, scaleY);
		// draw text popup
		this.invalidate();
	}

	private void drawPopupText(Canvas canvas) {
		// every draw needs to be scaled
		Paint paint1 = new Paint();
		paint1.setColor(Color.WHITE);
		canvas.drawRoundRect(new RectF(500f,100f,800f,300f), 10f, 10f, paint1);
		paint1.setColor(Color.BLACK);
		canvas.drawRoundRect(new RectF(505f,105f,795f,295f), 10f, 10f, paint1);
		Paint paint = new Paint();
		paint.setColor(Color.GREEN);
		paint.setTextSize(30);
		String text = "Hello World!\nNew Line";
		canvas.drawText(text, 515, 200, paint);
	}

	@Override
	public boolean onTouchEvent(MotionEvent e) {
	    float x = e.getX();
	    float y = e.getY();
	    if (e.getAction() == MotionEvent.ACTION_UP) {
		    // calculate tile touched
		    int tileX = (int)(x / scaleX)/10;
		    int tileY = (int)(y / scaleY)/10;
		    // only move if passable
		    player.setMoveTo(tileX, tileY);
	    }
	    return true;
	}
}
