package Main;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.Timer;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

import Weapons.Weapons;


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
	public static Image recoil;
	
	Sound laser_sound;
	
	boolean shot;
	boolean show_statistics;
	
	public static float theta;
	public static boolean reloading;
	
	Sound mp5;
	
	public static ArrayList<Bullet> bullets = new ArrayList<Bullet>(0);
	
	Random rand = new Random();
	
	int count = 0;
	int reload_timer = 100;
	int reload_time = 100;
	
	public static ArrayList<Weapons> guns = new ArrayList<Weapons>(0);
	
	public static Weapons wielding;
	
	float playerX;
	float playerY;
	
	float offsetX;
	float offsetY;
	float xDistance;
	float yDistance;
	
	Timer reloadTimer;
	
	public static int currentGun = 0;
	
	Sound reloadSound;
	Sound tick;
	
	public static boolean allowedToShoot = true;
	
	public static ArrayList<Weapons> inventory = new ArrayList<Weapons>(0);
	
	
	public class TimerListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			reload_timer ++;
		}
	}
	
	

	public Gun() throws SlickException {
		
		gun_right = new Image("lib/res/Misc/m16.png");
		gun_left = gun_right.getFlippedCopy(true, false);
		bullet_image = new Image("lib/res/Misc/normal_bullet.png");
		
		laser_sound = new Sound("lib/res/Sounds/laser_sound.wav");
		
		mp5 = new Sound("lib/res/Sounds/machine_gun.wav");
		recoil = new Image("lib/res/Misc/recoil.png");
		
		for(Weapons weapon: Shop.guns){
			guns.add(weapon);
		}
		
		reloadSound = new Sound("lib/res/Sounds/reload_sound.wav");
		tick = new Sound("lib/res/Sounds/tick.wav");

		reloadTimer = new Timer(reload_time, new TimerListener());
		
		inventory.add(Shop.gun(0));
		
		
		wielding = inventory.get(0);
		inventory.add(Shop.gun(1));
		
	}
	
	public void logic(Graphics g, GameContainer gc, Input input){

	    int dWheel = Mouse.getDWheel();
	    if (dWheel < 0) {
	    	reloading = false;
	    	if(currentGun == 0) currentGun = inventory.size() - 1;
	    	else currentGun --;
			wielding = inventory.get(currentGun);
	    } else if (dWheel > 0){
	    	reloading = false;
			if(currentGun + 1> inventory.size() - 1) currentGun = 0;
			else currentGun ++;
			wielding = inventory.get(currentGun);
	    }
		
	    
	    
		
		int mx = input.getMouseX() - Play.translate_x;
		int my = input.getMouseY() - Play.translate_y;
		
		shootX = (x);
		shootY = (y + gun_right.getHeight()/2);
		
		
//		Rectangle mouse = new Rectangle(mx, my, 1, 1);
		
		g.setColor(new Color(194, 0, 0));
		g.setAntiAlias(true);
		g.setLineWidth(1);
		drawBullets(g);
		
		playerX = Play.player.getX();
		playerY = Play.player.getY();
		
		offsetX = wielding.OFFSET().getX();
		offsetY = wielding.OFFSET().getY();
		
		
		// Gun directional handler
		// 16 PIXELS - Width of player 
		if(Player.facing_right){
			float tempx = playerX + 16 + offsetX;
			float tempy = 0;
			if(Player.moving && 
				(Player.hero_right.getFrame() == 2 || 
				 Player.hero_right.getFrame() == 3 || 
				 Player.hero_right.getFrame() == 7 || 
				 Player.hero_right.getFrame() == 8)
				){
				tempy = playerY + offsetY - 2;
			}else{
				tempy = playerY + offsetY;
			}
			wielding.gunRight().draw(tempx, tempy);
			
			xDistance = (mx) - tempx;
			yDistance = tempy - (my);
			theta = (float) Math.toDegrees(Math.atan2(xDistance, yDistance));
			
			wielding.gunRight().setCenterOfRotation(
				- wielding.CENTER().getX(), 
				wielding.CENTER().getY()
			);
			wielding.gunRight().setRotation(theta - 90);
		}
		
		
		
		else if(Player.facing_left){
			float tempx = playerX - wielding.gunLeft().getWidth() - offsetX;
			float tempy = 0;
			if(Player.moving && 
				(Player.hero_left.getFrame() == 2 || 
				 Player.hero_left.getFrame() == 3 || 
				 Player.hero_left.getFrame() == 7 || 
				 Player.hero_left.getFrame() == 8)
				){
				tempy = playerY + offsetY - 2;
			}else{
				tempy = playerY + offsetY;
			}
			wielding.gunLeft().draw(tempx, tempy);
			
			xDistance = (mx) - tempx;
			yDistance = tempy - (my);
			theta = (float) Math.toDegrees(Math.atan2(xDistance, yDistance));
			
			wielding.gunLeft().setCenterOfRotation(
				wielding.CENTER().getX() + wielding.gunLeft().getWidth(), 
				wielding.CENTER().getY()
			);
			wielding.gunLeft().setRotation(theta + 90);
			
			
		}
		

		
		if(allowedToShoot){
			if(wielding.once()){
				if(wielding.ammo() >= 0 && input.isMousePressed(0) && count > 5){
					shot = true;
					shoot(g, theta, mx, my);
					count = 0;
				}
			}else{
				if(wielding.ammo() >= 0 && input.isMouseButtonDown(0) && count > 5){
					shot = true;
					shoot(g, theta, mx, my);
					count = 0;
				}
			}
			count ++;
		}
		
		
		
		if(input.isKeyPressed(Input.KEY_R) && reloadTimer.isRunning() == false && wielding.ammo() < wielding.magezine() && !Shop.open){
			// TODO Reload..
			reloading = true;
			reloadTimer.start();
		}
		
		if(wielding.ammo() == wielding.magezine()) reloading = false;
		
		if(reloading){
			if(reloading){
				if(reload_timer > 1){
					wielding.reload(1);
					reload_timer = 0;
				}
			}
		}else{
			reloadTimer.stop();
		}
		
		
		
		
		
	}
	
	
	public void shoot(Graphics g, float theta, int toX, int toY){
		reloading = false;
		wielding.shoot(1);
		try{
			theta = (float) (theta * Math.PI / 180); // converting to radians from degrees
			
			int re = rand.nextInt(15) - 5;
			int distance = 25; 

			
			float xx = Play.player.getX() + 8;
			float yy = Play.player.getY() + 20 - gun_right.getHeight()/2;
			float xDistance = (toX) - xx;
			float yDistance = yy - (toY);
			double angle = Math.atan2(-(yDistance), (xDistance)) * 180/Math.PI;
			float xSpawn = (float) (xx + Math.cos(angle * Math.PI/180) * distance);
			float ySpawn = (float) (yy + Math.sin(angle * Math.PI/180) * distance);
			
			
			
			if(wielding.name().equalsIgnoreCase("shotgun")){
				bullets.add(new Bullet(bullet_image, xSpawn, ySpawn, toX, toY + rand.nextInt(40) - 5, 5));
				bullets.add(new Bullet(bullet_image, xSpawn, ySpawn, toX, toY + rand.nextInt(40) - 5, 5));
				bullets.add(new Bullet(bullet_image, xSpawn, ySpawn, toX, toY + rand.nextInt(40) - 5, 5));
			}else{
				bullets.add(new Bullet(bullet_image, xSpawn, ySpawn, toX, toY + re, 5));
			}
			
			wielding.gunshot().play(1, .5f);
			shot = false;
			
			// TODO: Send to server	..
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
