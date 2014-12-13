package Weapons;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.geom.Point;

public class Python extends Weapons {
	
	Image gun;
	Image gun1;
	Image icon;
	Image usingIcon;
	
	Sound gunshot;
	
	float x;
	float y;
	
	int speed = 200;
	int reload = 50;
	int magezine = 7;
	int ammo = magezine;
	
	
	public Python(Image GUN, Image ICON, Image USING) throws SlickException {
		gun = GUN;
		gun1 = GUN.getFlippedCopy(true, false);
		icon = ICON;
		usingIcon = USING;
		
		gunshot = new Sound("lib/res/Sounds/Pistol_sound.wav");
		
	}
	
	public Weapons get() throws SlickException { return new Pistol(gun, icon, usingIcon); }

	public String name() { return "Python"; }
	public int price() { return 200; }
	
	public Image gunRight() { return gun;  }
	public Image gunLeft()  { return gun1; }
	
	public Image icon() { return icon; }
	public Image usingIcon() { return usingIcon; }
	

	
	public Point OFFSET() {
		return new Point(1, 6);
	}
	
	public Point CENTER() {
		return new Point(9, 0);
	}
	

	public float getX(){ return x; }
	public float getY(){ return x; }
	public void setX(int i) { x = i; }
	public void setY(int i) { y = i; }
	
	public Sound gunshot(){
		return gunshot;
	}

	public int speed() { return speed; }
	public int reload_time() { return reload; }
	public int magezine() { return magezine; }
	public int ammo() { return ammo; }
	public void shoot(int num){ ammo -= num; }
	public void reload(int num) { ammo += num; }
	
	public boolean once() {
		return true;
	}

	
	

}
