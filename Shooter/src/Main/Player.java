package Main;
import java.util.ArrayList;
import java.util.Random;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.geom.Rectangle;


public class Player {
	
	private TileMap tileMap;
	
	private float x = 90;
	private float y = 470;
	private float dx;
	private float dy;
	
	private int width;
	private int height;
	
	private boolean move_left;
	private boolean move_right;
	private boolean jumping;
	private boolean falling;
	public static boolean facing_right;
	public static boolean facing_left;
	
	
	public static boolean right;
	public static boolean left;
	
	private float moveSpeed;
	private float maxSpeed;
	private float stopSpeed;
	private float maxFallingSpeed;
	private float jumpStart;
	private float gravity;
	
	private boolean topRight;
	private boolean topLeft;
	private boolean bottomRight;
	private boolean bottomLeft;
	
	public static boolean moving;
	
	public static int max_health = 15;
	public static int health = max_health;
	public int screen_health = health;
	
	public static Rectangle player_rect;
	public static boolean flying;
	
	Random random = new Random();
	
	public boolean usingJetpack = false;
	
	public static String player_line;
	
	public static boolean dead = false;
	public static boolean respawn = false;
	public static boolean send_death = true;
	public static String lastHitBy = "";
	
	ArrayList<Circles> circles = new ArrayList<Circles>(0);
	
	public static boolean shoot_right;
	public static boolean choice_normal;
	public static boolean choice_grenade;
	
	public static SpriteSheet hero_rightSS;
	public static SpriteSheet hero_leftSS;
	public static Animation hero_right;
	public static Animation hero_left;
	
	public static SpriteSheet fallingSS;
	public static Animation fallingA;
	
	public static float centerX;
	public static float centerY;

	
	public static double angleToTurn;
	public static boolean jumped;
	
	Image jump;
	
	
	public Player(TileMap map) throws SlickException {
		tileMap = map;
		
		width = 18;
		height = 32;
		
		moveSpeed = 1.6f;
		maxSpeed = 3f;
		maxFallingSpeed = 5f;
		stopSpeed = 0.60f;
		jumpStart = -6.2f;
		gravity = 0.44f;
		
		hero_rightSS = new SpriteSheet("lib/res/Player/right.png", 16, 34);
		hero_leftSS = new SpriteSheet("lib/res/Player/left.png", 16, 34);
		hero_right = new Animation(hero_rightSS, 150);
		hero_left = new Animation(hero_leftSS, 150);
		fallingSS = new SpriteSheet("lib/res/Player/falling.png", 16, 34);
		fallingA = new Animation(fallingSS, 400);
		
		jump = new Image("lib/res/Player/left_jump.png");
		
		
	}
	
	// getters and setters
	public int health(){
		return health;
	}
	public int max_health(){
		return max_health;
	}
	public boolean moving(){
		return moving;
	}
	public void set_health(int i){
		health = i;
	}
	
	public void setX(int i){ x = i; }
	public void setY(int i) { y = i; }
	
	public void setLeft(boolean b){ move_left = b; }
	public void setRight(boolean b){ move_right = b; }
	
	public void setJumping(boolean b){
		if(!falling){
			jumping = true;
		}
	}
	public float getX(){
		return (x - width/2);
	}
	public float getY(){
		return (y - height/2);
	}
	
	public float centerX(){
		return getX() + hero_rightSS.getSubImage(0, 0).getWidth() / 2;
	}
	
	public float centerY() {
		return getY() + hero_rightSS.getSubImage(0, 0).getHeight() / 2;
	}
	
	public void logic(GameContainer gc, Input input){
		
		handler(gc, input);
		collisions();
		
		
	}
	
	
	public void collisions(){
		// determining left and right movement
		if(move_left){
			dx -= moveSpeed;
			if(dx < -maxSpeed) dx = -maxSpeed;
		}else if(move_right){
			dx += moveSpeed;
			if(dx > maxSpeed) dx = maxSpeed;
		}else{
			if(dx > 0){
				dx -= stopSpeed;
				if(dx < 0) dx = 0;
			}else if(dx < 0){
				dx += stopSpeed;
				if(dx > 0) dx = 0;
			}
		}
		
//		 determining jumping and falling movement
		if(jumping){
			dy = jumpStart;
			falling = true;
			jumping = false;
		}
		
		if(falling){
			dy += gravity;
			if(dy > maxFallingSpeed){
				dy = maxFallingSpeed;
			}
		}else{
			if(!usingJetpack){
				dy = 0;
			}
		}
		
		
		
		// check collisions
		int currCol = tileMap.getColTile((int) x); // which X tile we are on..
		int currRow = tileMap.getRowTile((int) y); // which Y tile we are on..
		
		float tox = x + dx; // determine temporary X position before changing actual X position..
		float toy = y + dy; // determine temporary Y position before changing actual Y position..
		
		float tempx = x;
		float tempy = y;

		
		calculateCorners(x, toy); // current X position and next Y position..
		if(dy < 0) { // Jumping..
			if(topLeft || topRight) {
				dy = 0;
				tempy = currRow * tileMap.getTileSize() + height / 2;
			}
			else {
				tempy += dy;
			}
		}
		
		if(dy > 0) { // Falling..
			if(bottomLeft || bottomRight) {
				dy = 0;
				falling = false;
				tempy = (currRow + 1) * tileMap.getTileSize() - height / 2;
			}
			else {
				tempy += dy;
			}
		}
		
		calculateCorners(tox, y); // next X position and current Y position..
		if(dx < 0) { // Movement to right..
			if(topLeft || bottomLeft) {
				dx = 0;
				tempx = currCol * tileMap.getTileSize() + width / 2;
			}
			else {
				tempx += dx;
			}
		}
		if(dx > 0) { // Movement to left..
			if(topRight || bottomRight) {
				dx = 0;
				tempx = (currCol + 1) * tileMap.getTileSize() - width / 2;
			}
			else {
				tempx += dx;
			}
		}
		
		if(!falling) { // If we are not falling, check whether we should be falling or not..
			calculateCorners(x, y + 1);
			if(!bottomLeft && !bottomRight) {
				falling = true;
			}
		}
		
		x = tempx; // set temporary X to X position..
		y = tempy; // set temporary Y to Y position..
	}
	
	
	
	public void handler(GameContainer gc, Input input){
		if(health < 0) health = 0;
		if(health > max_health) health = max_health;
		if(facing_right) facing_left = false;
		
		if(health > 0){
			if(input.isKeyDown(Input.KEY_D) || input.isKeyDown(Input.KEY_RIGHT) ||
					input.isKeyDown(Input.KEY_A) || input.isKeyDown(Input.KEY_LEFT)){
				moving = true;
			}else{
				moving = false;
			}
				
			
			
			if(input.isKeyDown(Input.KEY_D) || input.isKeyDown(Input.KEY_RIGHT)){
				left = false;
				right = true;
				setRight(true);
			}else{
				setRight(false);
			}
			
			if(input.isKeyDown(Input.KEY_A) || input.isKeyDown(Input.KEY_LEFT)){
				right = false;
				right = true;
				setLeft(true);
			}else{
				setLeft(false);
			}
			
			if(input.isKeyPressed(Input.KEY_W) || input.isKeyPressed(Input.KEY_UP) || input.isKeyPressed(Input.KEY_SPACE)){
				if(!usingJetpack)
					setJumping(true);
			}
			
			if((input.isKeyDown(Input.KEY_W) || input.isKeyDown(Input.KEY_UP) || input.isKeyDown(Input.KEY_SPACE)) && usingJetpack){
//				flying = true;
				jumping = true;
				if(dy > -3.5f)
					dy -= .65f;
				else
					dy = -3.5f;
			}else{
				flying = false;
			}
		}
		
		
		
		
	}
	
	public void draw(Graphics g, GameContainer gc, Input input, TrueTypeFont font){
		
		if(dy < 0) jumped = true;
		else jumped = false;
		
		
		if(!dead){
			TileMap.mapX = getX();
			TileMap.mapY = getY();
		}
		
		shoot_right = (input.getMouseX() - Play.translate_x) - getX() > 0;
		
		float pos = getX() - (input.getMouseX() - Play.translate_x);
		
		facing_right = (pos <= 0) ? true: false;
		facing_left = (pos > 0) ? true: false;
		
		
		if(health <= 0 && send_death){
//			Client.send("DEATH:" + Main.name);
			//TODO
//			Alerts.append(lastHitBy + " has killed " + Main.name);
//			String s = lastHitBy + " has killed " + Menu.name;
//			Client.send("DEATH:" + s);
			send_death = false;
		}
		
		if(respawn){
			health = max_health;
			dead = false;
			send_death = true;
			respawn = false;
		}
		
		if(health <= 0){
			health = 0;
			dead = true;
		}else{
			dead = false;
		}
		
		player_line = ""  + usingJetpack +
			":" + flying  +
			":" + jumping +
			":" + moving  +
			":" + facing_right + 
			":" + facing_left + 
			":" + getX()  + 
			":" + getY()  +
			":" + health  +
			":" + Menu.name + 
			":" + Gun.theta;
		
//		System.out.println(player_line);
		
		
		
		logic(gc, input);
		g.setLineWidth(2f);
		
		
		if(input.isKeyPressed(Input.KEY_U)){
			usingJetpack = !usingJetpack;
		}
		
		
		for(int i=0;i<circles.size();i++){
			if(circles.get(i).remove){
				circles.remove(i);
			}else{
				circles.get(i).draw(g, gc);
			}
		}
		
		
		if(health > 0){
			
			if(jumped){
				if(facing_left){
					jump.draw(getX(), getY());
				}else if(facing_right){
					jump.getFlippedCopy(true, false).draw(getX(), getY());
				}
			}
			
			if(!jumped){
				if(!moving && facing_right){
					hero_rightSS.getSubImage(0, 0).draw(getX(), getY() - 2);
				}else if(!moving && facing_left){
					hero_leftSS.getSubImage(0, 0).draw(getX(), getY() - 2);
				}
				
				if(moving && facing_right){
					hero_right.draw(getX(), getY() - 2);
				}
				
				else if(moving && facing_left){
					hero_left.draw(getX(), getY() - 2);
				}
			}
			
			
//			if(usingJetpack && flying){
//				int green = random.nextInt(100 - 0) + 0;
//				int size = random.nextInt(12 - 3) + 3;
//				if(facing_right)
//					circles.add(new Circles(getX(), getY() + 13, green, size));
//				else
//					circles.add(new Circles(getX() + (width - 4), getY() + 13,green, size));
//			}
//			
//			
//			
//			if(!jumping){
//				
//				if((flying || falling) && facing_right && !falling){
////					hero_rightSS.getSubImage(1, 0).draw(getX(), getY());
//					jump.getFlippedCopy(true, false).draw(getX(), getY());
//				}
//				else if((flying || falling) && facing_left && !falling){
////					hero_leftSS.getSubImage(0, 0).draw(getX(), getY());
//					jump.draw(getX(), getY());
//				}
//				
//				else if(facing_right && !moving || falling){
//					hero_rightSS.getSubImage(1, 0).draw(getX(), getY());
//				}
//				else if(facing_left && !moving || falling){
//					hero_leftSS.getSubImage(0, 0).draw(getX(), getY());
//				}
//				
//				else if(facing_right && moving){
//					hero_right.draw(getX(), getY());
//				}
//				else if(facing_left && moving){
//					hero_left.draw(getX(), getY());
//				}
//			}
		}
			
//		font.drawString(100,100, "" + dy);
		
		player_rect = new Rectangle(getX(), getY(), width, height);
		

		
		
		
	}
	
	public void healthbar(Graphics g, TrueTypeFont font){
		int _width = 100;
		int _height = 20;
		if(health < 0) health = 0;
		if(health > max_health) health = max_health;
		g.setColor(Color.decode("#BA0000"));
		Rectangle healthbar = new Rectangle(704-111, 9, _width, _height);
		g.draw(healthbar);
		
		float scale = (_width / max_health);
		
		if(health > screen_health) screen_health ++;
		if(health < screen_health) screen_health --;
		
		g.setColor(Color.red);
		if(health > 0)
			g.fillRect(704 - 110, 10, (scale * screen_health) - 2, _height - 2);
		
	
		font.drawString(
			(704 - 110) + (100 - font.getWidth("" + health))/2,
			12,
			"" + (int) health
		);
	}
	
	
	private void calculateCorners(double x, double y){
		int leftTile = tileMap.getColTile((int) (x - width / 2));
		int rightTile = tileMap.getColTile((int) (x + width / 2) - 1);
		int topTile = tileMap.getRowTile((int) (y - height / 2));
		int bottomTile = tileMap.getRowTile((int) (y + height / 2) - 1);
		
		topLeft = !tileMap.getTile(topTile, leftTile).equals("w");
		topRight = !tileMap.getTile(topTile, rightTile).equals("w");
		bottomLeft = !tileMap.getTile(bottomTile, leftTile).equals("w");
		bottomRight = !tileMap.getTile(bottomTile, rightTile).equals("w");
	}
	public int center(int skew, int x, int y){
		return skew + ((x - y)/2);
	}
	public int width(TrueTypeFont font, String str){
		return font.getWidth(str);
	}
	public int height(TrueTypeFont font, String str){
		return font.getHeight(str);
	}
	
	

}
