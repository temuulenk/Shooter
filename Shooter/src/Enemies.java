import java.util.ArrayList;
import java.util.Random;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;


public class Enemies {

	String info = "";
	boolean usingJetpack;
	boolean flying;
	boolean jumping;
	boolean moving;
	boolean facing_right;
	boolean facing_left;
	float x;
	float y;
	public Rectangle box;
	public String name;
	public int health;
	
	ArrayList<Circles> circles = new ArrayList<Circles>(0);
	Random random = new Random();
	
	public Enemies() {
		
	}
	
	public boolean getJetpack(){
		return usingJetpack;
	}
	
	public boolean flying(){
		return flying;
	}
	
	
	public void logic(Graphics g, GameContainer gc, String str){
//		Input input = gc.getInput();

		try{
			info = str;
			
			String[] split = info.split(":");
			
			usingJetpack = Boolean.parseBoolean(split[1]);
			flying = Boolean.parseBoolean(split[2]);
			jumping = Boolean.parseBoolean(split[3]);
			moving = Boolean.parseBoolean(split[4]);
			facing_right = Boolean.parseBoolean(split[5]);
			facing_left = Boolean.parseBoolean(split[6]);
			x = Float.parseFloat(split[7]);
			y = Float.parseFloat(split[8]);
			health = Integer.parseInt(split[9]);
			name = split[10];
			
			
			
			if(!jumping && !moving){
				if(facing_right){
					Player.hero_rightSS.getSubImage(0, 0).draw(x, y);
				}else if(facing_left){
					Player.hero_leftSS.getSubImage(0, 0).draw(x, y);
				}
			}
			
			if(moving || jumping){
				if(facing_right){
					Player.hero_right.draw(x, y);
				}else if(facing_left){
					Player.hero_left.draw(x, y);
				}
			}
			
			
			
			box = new Rectangle(x, y, 24, 27);
			
			
			
		}catch(Exception e){
//			e.printStackTrace();
		}
		
		
		
	}
	
	
	
	
	
}

