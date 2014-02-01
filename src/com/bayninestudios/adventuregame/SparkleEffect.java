package com.bayninestudios.adventuregame;

import java.util.ArrayList;
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
	private ArrayList<Sparkle> sparkles;

	public SparkleEffect(Rect rect, int color, int count) {
		area = rect;
		this.color = color;
		this.count = count;
		rand = new Random(System.currentTimeMillis());
		sparkles = new ArrayList<Sparkle>();
		for (int i=0; i<count; i++) {
			sparkles.add(new Sparkle(
					rand.nextInt(area.width())+area.left,
					rand.nextInt(area.height())+area.top));
		}
	}
	
	public void draw(CanvasHelper canvas) {
		Paint paint = new Paint();
		paint.setColor(color);
		int sizeX = (int)(4*canvas.scaleX);
		int sizeY = (int)(4*canvas.scaleY);
		for (Sparkle sparkle:sparkles) {
			if (sparkle.fade > 0) {
				paint.setAlpha(sparkle.fade);
				int x = (int)(sparkle.x*canvas.scaleX);
				int y = (int)(sparkle.y*canvas.scaleY);
				canvas.canvas.drawRect(new Rect(x,y,x+sizeX,y+sizeY), paint);
				sparkle.fade--;
				if (sparkle.fade <= 0) {
					sparkle.fade = 0;
					sparkle.sleep = rand.nextInt(100);
				}
			} else {
				sparkle.sleep--;
				if (sparkle.sleep <= 0) {
					sparkle.sleep = 0;
					sparkle.fade = 150;
				}
			}
		}
	}
	
	private class Sparkle {
		public int x;
		public int y;
		public int fade;
		public int sleep;
		
		public Sparkle(int x, int y) {
			this.x = x;
			this.y = y;
			fade = 150;
			sleep = 0;
		}
	}
}
