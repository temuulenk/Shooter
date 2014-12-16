package Main;
import java.util.ArrayList;
import java.util.Random;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Rectangle;

import Weapons.Weapons;


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
	boolean dead;
	
	
	ArrayList<Circles> circles = new ArrayList<Circles>(0);
	Random random = new Random();
	
	
	private SpriteSheet hero_rightSS;
	private SpriteSheet hero_leftSS;
	
	
	Weapons wielding;
	float theta;
	float offsetX;
	float offsetY;
	float xDistance;
	float yDistance;
	float skewX;
	float skewY;
	
	Image gun;
	Image gun1;
	
	ArrayList<Weapons> guns = new ArrayList<Weapons>(0);
	
	Image Python, Uzi, Shotgun, M16, Pistol;
	
	public Enemies() throws SlickException {
		hero_rightSS = new SpriteSheet("lib/res/Player/right.png", 16, 34);
		hero_leftSS = new SpriteSheet("lib/res/Player/left.png", 16, 34);
		
    	Pistol = new Image("lib/res/Misc/pistol.png");
    	M16 = new Image("lib/res/Misc/m16.png");
    	Python = new Image("lib/res/Misc/python.png");
    	Uzi = new Image("lib/res/Misc/uzi.png");
    	Shotgun = new Image("lib/res/Misc/shotgun.png");
		
		
	}
	
	public boolean getJetpack(){
		return usingJetpack;
	}
	
	public boolean flying(){
		return flying;
	}
	
	
	public void logic(Graphics g, GameContainer gc, String str){

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
			theta = Float.parseFloat(split[11]);
			float tempx = Float.parseFloat(split[12]);
			float tempy = Float.parseFloat(split[13]);
			float skewX = Float.parseFloat(split[14]);
			float skewY = Float.parseFloat(split[15]);
			
			ClientHandler.screen_name[Integer.parseInt(split[0])] = name;
			
			if(health <= 0) dead = true;
			else dead = false;
			
			if(health > 0){
				if(!jumping && !moving){
					if(facing_right){
						hero_rightSS.getSubImage(0, 0).draw(x, y - 2);
					}else if(facing_left){
						hero_leftSS.getSubImage(0, 0).draw(x, y - 2);
					}
				}
				
				if(moving || jumping){
					if(facing_right){
						Player.hero_right.draw(x, y - 2);
					}else if(facing_left){
						Player.hero_left.draw(x, y - 2);
					}
				}
			
			
				if(facing_right) gun = weapon(split[16]);
				else if(facing_left) gun1 = weapon1(split[16]);
				
				if(facing_right){
					if(gun.getWidth() > 20){
						gun.setCenterOfRotation(
								skewX + gun.getWidth()/2,
								skewY
						);
					}else{
						gun.setCenterOfRotation(
								skewX - gun.getWidth(),
								skewY
						);
					}
					gun.setRotation(theta - 90);
					gun.draw(tempx, tempy);
					
				}
				
				else{
					gun1.setCenterOfRotation(
						skewX + gun1.getWidth(), 
						skewY
					);
					gun1.setRotation(theta + 90);
					gun1.draw(tempx, tempy);
				}
			}
			
			
			
			
			
			box = new Rectangle(x, y, 24, 27);
			
			
		}catch(Exception e){
//			e.printStackTrace();
		}
		
		
		
	}

	
	public Image weapon(String name){
		if(name.equalsIgnoreCase("pistol")){
			return Pistol;
		}else if(name.equalsIgnoreCase("m16")){
			return M16;
		}else if(name.equalsIgnoreCase("python")){
			return Python;
		}else if(name.equalsIgnoreCase("shotgun")){
			return Shotgun;
		}else{
			return Uzi;
		}
	}
	
	public Image weapon1(String name){
		if(name.equalsIgnoreCase("pistol")){
			return Pistol.getFlippedCopy(true, false);
		}else if(name.equalsIgnoreCase("m16")){
			return M16.getFlippedCopy(true, false);
		}else if(name.equalsIgnoreCase("python")){
			return Python.getFlippedCopy(true, false);
		}else if(name.equalsIgnoreCase("shotgun")){
			return Shotgun.getFlippedCopy(true, false);
		}else{
			return Uzi.getFlippedCopy(true, false);
		}
	}
	
	
	
	
}

