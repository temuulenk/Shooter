package Weapons;

import org.newdawn.slick.Image;

public class Shotgun extends Weapons {
	
	Image gun;
	Image icon;
	
	float x;
	float y;
	
	// in milliseconds
	int speed = 500;
	int reload = 500;
	int magezine = 2;
	
	
	public Shotgun(Image gun, Image icon) {
		gun = this.gun;
		icon = this.icon;
	}
	
	public Weapons get() { return new M16(gun, icon); }

	public String name() { return "Shotgun"; }
	
	public Image image() { return gun; }
	public Image icon() { return icon; }

	public float getX(){ return x; }
	public float getY(){ return x; }
	public void setX(int i) { x = i; }
	public void setY(int i) { y = i; }

	public int speed() { return speed; }
	public int reload_time() { return reload; }
	public int magezine() { return magezine; }
	
	

}
