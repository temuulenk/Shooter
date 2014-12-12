package Main;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Line;
import org.newdawn.slick.geom.Rectangle;


public class Bullet {
	
	String owner;
	final int damage = 1;
	
	float x;
	float y;
	float dx;
	float dy;
	float speed = 20;
	float theta;
	public static Image bullet;
	Rectangle rect;
	
	float startX, startY;
	
	boolean hit = false;
	
	Line bLine;
	
	

	public Bullet(Image img, float posX, float posY, float toX, float toY, float velocity) {
		bullet = img;
//		speed = velocity;
		startX = posX;
		startY = posY;
		x = posX;
		y = posY;
		
		float xDistance = (toX) - x;
		float yDistance = y - (toY);
		theta = (float) Math.toDegrees(Math.atan2(xDistance, yDistance));
		try{
			bullet.setRotation(theta - 90);
		}catch(Exception e){}
		
		float rad = (float)(Math.atan2(toX - x, toY - y));
	         
	    dx = (float) Math.sin(rad) * speed;
        dy = (float) Math.cos(rad) * speed;
		
        
	}
	
	public void logic(){
		
//		if(TileMap.map[(int)y/32][(int)x/32].equals("w")){
//			x += dx;
//			y += dy;
//		}
		
		try{
			if(dx > 0){
				if(TileMap.map[(int) (y + 6)/32][(int) (x + 6)/32].equals("a")){
					hit = true;
				}
			}else{
				if(TileMap.map[(int) y/32][(int) (x)/32].equals("a")){
					hit = true;
				}
			}
			
			if(Math.abs(Play.player.getX() - x) > 1000 || Math.abs(Play.player.getY() - y) > 1000) hit = true;
			
		}catch(Exception e){
			
		}
		
		
		x += dx;
		y += dy;
		
		
	}
	
	public void draw(Graphics g){
		
		g.setColor(new Color(255, 255, 255, .1f));
//		g.setColor(new Color(255, 255, 255));
		g.setLineWidth(.3f);
		logic();
		
		try{
			rect = new Rectangle(x, y, 5, 5);
//			bullet.draw(x, y);
			bLine = new Line(startX, startY, x, y);
			g.draw(bLine);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	
	
	
	
	
	
	
}
