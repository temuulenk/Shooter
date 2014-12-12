package Weapons;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Point;

import Main.Bullet;
import Main.Gun;

public class Pistol extends Weapons {
	
	Image gun;
	Image gun1;
	Image icon;
	Image usingIcon;
	
	float x;
	float y;
	
	int speed = 200;
	int reload = 50;
	int magezine = 10;
	int ammo = magezine;
	
	
	public Pistol(Image GUN, Image ICON, Image USING) {
		gun = GUN;
		gun1 = GUN.getFlippedCopy(true, false);
		icon = ICON;
		usingIcon = USING;
	}
	
	public Weapons get() { return new Pistol(gun, icon, usingIcon); }

	public String name() { return "Pistol"; }
	public int price() { return 100; }
	
	public Image gunRight() { return gun;  }
	public Image gunLeft()  { return gun1; }
	
	public Image icon() { return icon; }
	public Image usingIcon() { return usingIcon; }
	

	
	public Point OFFSET() {
		return new Point(1, 10);
	}
	
	public Point CENTER() {
		return new Point(9, 0);
	}
	

	public float getX(){ return x; }
	public float getY(){ return x; }
	public void setX(int i) { x = i; }
	public void setY(int i) { y = i; }

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
