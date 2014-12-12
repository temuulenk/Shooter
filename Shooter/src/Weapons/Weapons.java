package Weapons;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Point;


public abstract class Weapons {
	
	public abstract Weapons get();
	
	public abstract Image gunRight();
	public abstract Image gunLeft();
	public abstract Image icon();
	public abstract Image usingIcon();
	
	
	public abstract Point OFFSET();
	public abstract Point CENTER();
	
	
	public abstract String name();
	public abstract int price();
	public abstract float getX();
	public abstract float getY();
	public abstract void setX(int i);
	public abstract void setY(int i);
	
	public abstract int speed();
	public abstract int reload_time();
	public abstract int magezine();
	public abstract int ammo();
	public abstract void shoot(int num);
	public abstract void reload(int num);
	public abstract boolean once();
	
	
	
}
