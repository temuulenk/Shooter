package Main;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.geom.Rectangle;

public class UserInterface {
	
	Image up;
	int current = 0;
	float x;
	float y;
	int max;
	int width;
	String text;
	
	boolean holding = false;
	Rectangle box;
	
	float posX;
	float posY;
	
	float scale;
	Rectangle temp;
	
	
	public UserInterface(int posx, int posy, int w, int m, String s) throws SlickException {
		up = new Image("lib/res/Misc/upArrow.png");
		x = posx;
		y = posy;
		max = m;
		width = w;
		
		posX = x;
		posY = y;
		
		scale = (float) max / (float) width;
		
	}
	
	
	public void draw(Graphics g, GameContainer gc, TrueTypeFont font){
		Input input = gc.getInput();
		Rectangle mouse = new Rectangle(input.getMouseX(), input.getMouseY(), 1, 1);
		g.setColor(Color.black);
		
		
		box = new Rectangle(x, y, width, 1);
		g.fill(box);
		
		temp = new Rectangle(x, y - 4, width, 12);
		
		if(mouse.intersects(mouse)){
		    int dWheel = Mouse.getDWheel();
		    if (dWheel < 0) {
		    	posX --;
		    } else if (dWheel > 0){
		        posX ++;
		   }
		}
		
		
		up.draw(posX - (up.getWidth()/2), posY);
		Rectangle upR = new Rectangle(posX - 10, posY - 3, up.getWidth() * 2, up.getHeight() * 2);
		
		if(input.isMouseButtonDown(0) && temp.intersects(mouse)){
			posX = input.getMouseX();
		}
		
		if(mouse.intersects(upR) && input.isMouseButtonDown(0)){
			holding = true;
		}
		
		if(holding){
			posX = input.getMouseX();
			if(input.isMouseButtonDown(0) == false) holding = false;
		}
		
		if(posX > (width + x)) posX = (width + x);
		if(posX < x) posX = x;
		
		String text = "" + (int) (scale * (posX - x));
		current = Integer.parseInt(text);
		
		font.drawString(x + (width - font.getWidth(text))/2, y - font.getHeight(text), text, Color.black);
		
		
		
		
	}

	public int getValue(){
		return current;
	}
	
	
	
	
	
	
	

}
