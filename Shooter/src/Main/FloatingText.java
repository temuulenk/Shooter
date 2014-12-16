package Main;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.TrueTypeFont;

public class FloatingText {
	
	public String text = "";
	public float x;
	public float y;
	public float startX;
	public float startY;
	public Color color;
	public float transparency = 1;
	public boolean remove;
	
	
	public FloatingText(float pos1, float pos2, String str, Color c) {
		startX = pos1;
		startY = pos2;
		x = startX;
		y = startY;
		text = str;
		color = c;
	}
	
	
	public void draw(Graphics g, GameContainer gc, TrueTypeFont font){
		
		
		font.drawString(x, y, text, new Color(color.getRed(), color.getGreen(), color.getBlue(), transparency));
		
		y -= 1f;
		transparency -= .025f;
		
		if(transparency <= 0) remove = true;
		else remove = false;
		
		
	}
	
	
	
	
	
	
	
	

}
