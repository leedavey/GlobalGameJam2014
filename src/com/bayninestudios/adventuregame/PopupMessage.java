package com.bayninestudios.adventuregame;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

public class PopupMessage {

	public boolean showing;
	private String messageText1;
	private String messageText2;
	private String buttonText;

	public PopupMessage() {
		resetPopup();
	}
	
	public void resetPopup() {
		showing = false;
		messageText1 = "";
		messageText2 = "";
		buttonText = "";
	}
	
	public void displayPopup(String msg1, String msg2, String btnMsg) {
		showing = true;
		messageText1 = msg1;
		messageText2 = msg2;
		buttonText = btnMsg;
	}

	public void displayPopup(String msg1) {
		displayPopup(msg1, "", "Ok");
	}

	public void draw(CanvasHelper canvas) {
		// every draw needs to be scaled
		Paint paint1 = new Paint();
		paint1.setColor(Color.WHITE);
		canvas.drawRoundRect(new RectF(300f,100f,700f,300f), 10f, 10f, paint1);
		paint1.setColor(Color.BLACK);
		canvas.drawRoundRect(new RectF(305f,105f,695f,295f), 10f, 10f, paint1);
		Paint paint = new Paint();
		paint.setColor(Color.GREEN);
		paint.setTextSize(30);
		canvas.drawText(messageText1, 330, 150, paint);
		canvas.drawText(messageText2, 330, 180, paint);
		paint1.setColor(Color.LTGRAY);
		canvas.drawRoundRect(new RectF(420f,220f,580f,270f), 10f, 10f, paint1);
		paint.setColor(Color.BLACK);
		canvas.drawText(buttonText, 450, 255, paint);
	}
}
