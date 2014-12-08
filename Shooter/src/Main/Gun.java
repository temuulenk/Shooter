package Main;
import java.util.ArrayList;
import java.util.Random;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.geom.Rectangle;


public class Gun {
	
	public float x = 150;
	public float y = 150;

	boolean right = true;
	boolean left;
	float shootX;
	float shootY;
	
	Image gun_right;
	Image gun_left;
	Image bullet_image;
	
	Sound laser_sound;
	
	boolean shot;
	boolean show_statistics;
	
	public static float theta;
	
	public static ArrayList<Bullet> bullets = new ArrayList<Bullet>(0);
	
	Random rand = new Random();
	
	int count = 0;

	public Gun() throws SlickException {
		
		gun_right = new Image("lib/res/Misc/m16.png");
		gun_left = gun_right.getFlippedCopy(true, false);
		bullet_image = new Image("lib/res/Misc/normal_bullet.png");
		
		laser_sound = new Sound("lib/res/Sounds/laser_sound.wav");
		
	}
	
	public void logic(Graphics g, GameContainer gc, Input input){
		
//		x = Play.player.getX();
//		y = Play.player.getY();
		
		int mx = input.getMouseX() - Play.translate_x;
		int my = input.getMouseY() - Play.translate_y;
		
		shootX = (x);
		shootY = (y + gun_right.getHeight()/2);
		
		float xDistance = (mx) - x;
		float yDistance = y - (my);
		theta = (float) Math.toDegrees(Math.atan2(xDistance, yDistance));
		
		Rectangle mouse = new Rectangle(mx, my, 1, 1);
		g.draw(mouse);
		
		
		if(Player.facing_right){
			x = Player.centerX - 5;
			y = Play.player.getY() + 6;
			gun_right.setCenterOfRotation(8, gun_right.getHeight()/2);
			gun_right.draw(x, y);
			gun_right.setRotation(theta - 90);
			
			
		}
		
		else if(Player.facing_left){
			x = Play.player.getX();
			y = Play.player.getY() + 6;
			
			gun_left.setCenterOfRotation(gun_left.getWidth() - 8, gun_right.getHeight()/2);
			gun_left.draw(x - gun_left.getWidth()/2 - 1, y);
			gun_left.setRotation(theta + 90);
		}
		

			
		
		if(input.isMouseButtonDown(0) && count > 5){
			shot = true;
			shoot(g, theta, mx, my);
			count = 0;
			
		}
		
		count ++;
		
		
		if(Player.facing_right){
		}
		
		g.setColor(new Color(194, 0, 0));
		g.setAntiAlias(true);
		g.setLineWidth(1);
		theta = (float) (theta * Math.PI / 180); // converting to radians from degrees
		float startX = Player.centerX;
		float startY = y + 15 - 10;
		float endX   = (float) (startX + (16/2) * Math.sin(theta));
		float endY   = (float) (startY + 15 * -Math.cos(theta));
		
		Rectangle box = new Rectangle(startX, startY, 4, 4);
		g.draw(box);
		
		
//		Line line = new Line(startX, startY, mx, my);
//		g.draw(line);
		
		
		drawBullets(g);

		
		
	}
	
	
	public void shoot(Graphics g, float theta, int toX, int toY){
		try{
			theta = (float) (theta * Math.PI / 180); // converting to radians from degrees
			float startX = x;
			float startY = y + gun_right.getHeight()/2;
			int mid = (int) startY;
			int re = rand.nextInt(10) - 5;
			float endX   = (float) (startX + 20 * Math.sin(theta));
			float endY   = (float) (startY + 20 * -Math.cos(theta));
			System.out.println(re);
			
			if(right){
				bullets.add(new Bullet(bullet_image, endX, endY - (bullet_image.getHeight()), toX, toY + re, 5));
				String bullet_str = "" + endX + ":" + (endY - (bullet_image.getHeight())) + ":" + toX + ":" + toY;
				if(Play.inMultiplayer)
					Client.send("bullet:" + bullet_str);
			}else{
				bullets.add(new Bullet(bullet_image, endX + 15, endY - (bullet_image.getHeight()), toX, toY, 5));
				String bullet_str = "" + (endX + 15) + ":" + (endY - (bullet_image.getHeight())) + ":" + toX + ":" + toY;
				if(Play.inMultiplayer)
					Client.send("bullet:" + bullet_str);
			}
			
			laser_sound.play(1, .4f);
			shot = false;
			
			// TODO: Send to server and Barrel Explosion..
		} catch(Exception e){
			e.printStackTrace();
		}
		
		
	}
	
	
	public void drawBullets(Graphics g){
		
		for(int i=0;i<bullets.size();i++){
			if(bullets.get(i).hit) bullets.remove(i);
			else bullets.get(i).draw(g);
			
		}
		
		
	}
	
	
	
	
	
	
}
