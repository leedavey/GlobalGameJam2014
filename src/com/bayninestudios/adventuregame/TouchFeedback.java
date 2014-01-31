package com.bayninestudios.adventuregame;

import java.util.ArrayList;
import java.util.Iterator;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

public class TouchFeedback {

	private final int STARTINGALPHA = 100;
	private final int FADESPEED = 5;
	
	private Bitmap bmp;
	private ArrayList<TouchFeedbackItem> feedbackItems;
	private final int halfBmpSize;
	private Rect drawDest;

	public TouchFeedback(Context context) {
		BitmapFactory.Options opts = new BitmapFactory.Options();
		opts.inScaled = false;
		bmp = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.highlight, opts);
		feedbackItems = new ArrayList<TouchFeedbackItem>();
		halfBmpSize = bmp.getHeight()/2;
		drawDest = new Rect();
	}

	public void draw(CanvasHelper canvas) {
		for (Iterator<TouchFeedbackItem> i = feedbackItems.iterator(); i.hasNext();) {
			TouchFeedbackItem item = i.next();
			drawDest.set(item.x-halfBmpSize,item.y-halfBmpSize,item.x+halfBmpSize,item.y+halfBmpSize);
			canvas.drawBitmap(bmp, drawDest, item.fade);
			item.fade -= FADESPEED;
			if (item.fade < 0) {
				i.remove();
			}
		}
	}
	
	// fine game coords
	public void addTouchEvent(int x, int y) {
		feedbackItems.add(new TouchFeedbackItem(x,y));
	}
	
	// inner class to store and track touch events
	private class TouchFeedbackItem {
		
		public int x;
		public int y;
		public int fade;
		
		public TouchFeedbackItem(int x, int y) {
			this.x = x;
			this.y = y;
			this.fade = STARTINGALPHA;
		}
	}
}
