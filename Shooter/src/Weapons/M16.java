package Weapons;

import org.newdawn.slick.Image;

public class M16 extends Weapons {
	
	Image gun;
	Image icon;
	
	float x;
	float y;
	
	int speed = 100;
	int reload = 50;
	int magezine = 30;
	
	
	public M16(Image gun, Image icon) {
		gun = this.gun;
		icon = this.icon;
	}
	
	public Weapons get() { return new M16(gun, icon); }

	public String name() { return "M16"; }
	
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
