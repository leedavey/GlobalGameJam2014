package com.bayninestudios.adventuregame;

import java.util.Random;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;

public class SparkleEffect {
	
	private Rect area;
	private int color;
	private Random rand;
	private int count;
	private int alpha;
	private boolean fadein;

	public SparkleEffect(Rect rect, int color, int count) {
		area = rect;
		this.color = color;
		this.count = count;
		rand = new Random(System.currentTimeMillis());
		alpha = 0;
		fadein = true;
	}
	
	public void draw(Canvas canvas, float scaleX, float scaleY) {
		if (count > 0) {
			alpha++;
			Paint paint = new Paint();
			paint.setColor(color);
			paint.setAlpha(alpha);
			for (int i = 0; i < count; i++) {
				int x = (int)((rand.nextInt(area.width())+area.left)*scaleX);
				int y = (int)((rand.nextInt(area.height())+area.top)*scaleY);
				canvas.drawRect(new Rect(x, y, (int)(x+3*scaleX), (int)(y+3*scaleY)), paint);
			}
		}
	}
}
