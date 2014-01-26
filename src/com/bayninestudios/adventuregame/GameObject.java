package com.bayninestudios.adventuregame;

public class GameObject {
	public Vector2 position;
	public int objectBitmap;
	public float scale;
	public boolean interact;
	public int interactID;
	public boolean visible;
	// animated?
	// moving?

	public GameObject(int x, int y, int bitmapID) {
		position = new Vector2(x,y);
		objectBitmap = bitmapID;
		interact = false;
		visible = true;
	}
	
	public GameObject(int x, int y, int bitmapID, boolean inter, boolean vis, int interID) {
		position = new Vector2(x,y);
		objectBitmap = bitmapID;
		interact = inter;
		visible = vis;
		interactID = interID;
	}
	
	public void updateObject() {
		// use this method to animate or move
	}
	
	public void setInteract(boolean value) {
		interact = true;
	}
	
	public void setVisible(boolean value) {
		visible = true;
	}
}
