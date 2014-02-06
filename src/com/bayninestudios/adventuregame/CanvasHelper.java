package com.bayninestudios.adventuregame;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;

public class CanvasHelper {

	public Canvas canvas;
	public float scaleX;
	public float scaleY;

	public CanvasHelper() {
	}

	public void setCanvas(Canvas canv) {
		canvas = canv;
		scaleX = canvas.getWidth() / (CanvasView.GAME_SIZE_X * 1f);
		scaleY = canvas.getHeight() / (CanvasView.GAME_SIZE_Y * 1f);
	}

	public void setScale(float x, float y) {
		scaleX = x;
		scaleY = y;
	}

	// draw with location center bottom
	// draw with rect
	// draw with location center

	public void drawBitmap(Bitmap bmp, Rect srcRect, Rect destRect) {
		RectF dst = new RectF(destRect.left * scaleX, destRect.top * scaleY,
				destRect.right * scaleX, destRect.bottom * scaleY);
		canvas.drawBitmap(bmp, srcRect, dst, null);
	}

	// draw the entire bitmap at a location
	public void drawBitmap(Bitmap bmp, Rect destRect) {
		Rect src = new Rect(0, 0, bmp.getWidth(), bmp.getHeight());
		RectF dst = new RectF(destRect.left * scaleX, destRect.top * scaleY,
				destRect.right * scaleX, destRect.bottom * scaleY);
		canvas.drawBitmap(bmp, src, dst, null);
	}

	// draw the entire bitmap at a location
	public void drawBitmap(Bitmap bmp, Rect destRect, int alpha) {
		Paint paint = new Paint();
		paint.setAlpha(alpha);
		Rect src = new Rect(0, 0, bmp.getWidth(), bmp.getHeight());
		RectF dst = new RectF(destRect.left * scaleX, destRect.top * scaleY,
				destRect.right * scaleX, destRect.bottom * scaleY);
		canvas.drawBitmap(bmp, src, dst, paint);
	}

	public void drawRoundRect(RectF rectF, float f, float g, Paint paint) {
		canvas.drawRoundRect(rectF, f, g, paint);
	}
}
