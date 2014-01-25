package com.bayninestudios.adventuregame;

public class GameObject {
	public Vector2 position;
	public int objectBitmap;
	public float scale;
	// animated?
	// moving?

	public GameObject(int x, int y, int bitmapID) {
		position = new Vector2(x,y);
		objectBitmap = bitmapID;
	}
	
	public void updateObject() {
		// use this method to animate or move
	}
}
