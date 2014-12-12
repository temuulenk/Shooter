package Weapons;

import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Point;

public class Shotgun {
	
	Image gun;
	Image icon;
	Image usingIcon;
	
	float x;
	float y;
	
	// in milliseconds
	int speed = 500;
	int reload = 500;
	int magezine = 2;
	
	
	public Shotgun(Image GUN, Image ICON, Image USING) {
		gun = GUN;
		icon = ICON;
		usingIcon = USING;
	}
	
//	public Weapons get() { return new M16(gun, icon, usingIcon); }

	public String name() { return "Shotgun"; }
	public int price() { return 1000; }
	
	public Image image() { return gun; }
	public Image image1() { return gun; }
	public Image icon() { return icon; }
	public Image usingIcon() { return usingIcon; }
	
	public Point right_skew() { 
		return new Point(2, 6);
	}
	public Point left_skew() { 
		return new Point(2, 6);
	}
	public Point center() {
		return new Point(2, 6);
	}
	public Point center1() {
		return new Point(2, 6);
	}
	

	public float getX(){ return x; }
	public float getY(){ return x; }
	public void setX(int i) { x = i; }
	public void setY(int i) { y = i; }

	public int speed() { return speed; }
	public int reload_time() { return reload; }
	public int magezine() { return magezine; }
	
	

}
