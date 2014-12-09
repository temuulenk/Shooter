package Weapons;

import org.newdawn.slick.Image;

public class Pistol extends Weapons {
	
	Image gun;
	Image icon;
	
	float x;
	float y;
	
	int speed = 200;
	int reload = 50;
	int magezine = 10;
	
	
	public Pistol(Image GUN, Image ICON) {
		gun = GUN;
		icon = ICON;
	}
	
	public Weapons get() { return new M16(gun, icon); }

	public String name() { return "Pistol"; }
	public int price() { return 100; }
	
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
