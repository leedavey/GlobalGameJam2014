package com.bayninestudios.adventuregame;

public class Vector2 {

	public int x;
	public int y;
	
	public Vector2() {
		x = 0;
		y = 0;
	}

	public Vector2(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public void setXY(int x2, int y2) {
		x = x2;
		y = y2;
	}
	
	public Vector2 sub(Vector2 mod) {
		int xVal = this.x - mod.x;
		int yVal = this.y - mod.y;
		return new Vector2(xVal, yVal);
	}
	
	public float length(Vector2 v) {
		return (float) Math.sqrt((double)(v.x * v.x + v.y * v.y));
	}

}
