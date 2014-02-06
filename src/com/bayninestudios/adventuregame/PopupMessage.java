package com.bayninestudios.adventuregame;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

public class PopupMessage {

	public boolean showing;
	public String messageText;
	
	public void draw(CanvasHelper canvas) {
		// every draw needs to be scaled
		Paint paint1 = new Paint();
		paint1.setColor(Color.WHITE);
		canvas.drawRoundRect(new RectF(300f,100f,700f,300f), 10f, 10f, paint1);
		paint1.setColor(Color.BLACK);
		canvas.drawRoundRect(new RectF(305f,105f,695f,295f), 10f, 10f, paint1);
//		paint1.setColor(Color.LTGRAY);
//		canvas.drawRoundRect(new RectF(420f*scaleX,220f*scaleY,580f*scaleX,270f*scaleY), 10f, 10f, paint1);
//		Paint paint = new Paint();
//		paint.setColor(Color.GREEN);
//		paint.setTextSize(30);
//		String text1 = "Congratulations!";
//		canvas.drawText(text1, 330*scaleX, 150*scaleY, paint);
//		String text2 = "You got the scepter!";
//		canvas.drawText(text2, 330*scaleX, 180*scaleY, paint);
//		paint.setColor(Color.BLACK);
//		String text3 = "Restart";		
//		canvas.drawText(text3, 450*scaleX, 255*scaleY, paint);
	}
}
