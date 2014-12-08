import java.util.Random;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;


public class Circles {
	
	float x;
	float y;
	float dx;
	float dy;
	int red;
	int green;
	int blue;
	Random random = new Random();
	float startPosY;
	float startPosX;
	boolean remove = false;
	boolean end = false;
	int size;
	
	public Circles(float num, float num1, int gg, int ss){
		x = num;
		y = num1;
		startPosX = num;
		startPosY = num1;
		
		size = ss;
		
		// rocket
		dx = (float) (Math.random()*2 - .5f) / 3.5f;
		dy = 1.2f;
		
		
		// horizontal
//		dy = (float) (Math.random()*2 - .5f) / 1.5f;
//		dx = 3.5f;
		
		
		green = gg;
		
		
	
	}
	
	public void draw(Graphics g, GameContainer gc){
		
		x += dx;
		y += dy;
		
		g.setColor(new Color(255, green, 0));
//		g.drawOval(x, y, 10, 10);
		g.fillOval(x, y, size, size);
//		g.fillRect(x, y, 4, 4);
		
		if(Math.abs(startPosY - y) > 40 || Math.abs(startPosX - x) > 64){
			end = true;
		}
		
		int posX = (int) (x / 32);
		int posY = (int) ((y + size) / 32);
		
		
		if(Play.tilemap.getTile(posY, posX).equals("a")){
			remove = true;
		}
			
		
		if(end){
			size -= .5f;
		}
		if(size < 0){
			remove = true;
		}
		
		
	}
	

}
