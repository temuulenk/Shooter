package Weapons;
import org.newdawn.slick.Image;


public abstract class Weapons {
	
	public abstract Weapons get();
	
	public abstract Image image();
	public abstract Image icon();
	
	public abstract String name();
	public abstract int price();
	public abstract float getX();
	public abstract float getY();
	public abstract void setX(int i);
	public abstract void setY(int i);
	
	public abstract int speed();
	public abstract int reload_time();
	public abstract int magezine();
	
	
	
}
