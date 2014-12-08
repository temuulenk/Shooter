package Main;
import static org.lwjgl.opengl.GL11.glTranslatef;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.geom.Line;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;


public class Play extends BasicGameState {
	public Play(int state) {}
	
	TrueTypeFont font;
	TrueTypeFont font1;
	
	public static boolean quit = false;
	
	public static String[] players = new String[10];
	public static String[] names = new String[10];
	
	public static TileMap tilemap;
	public static Player player;
	public static Gun gun;
	public static ClientHandler client_handler;
	public static Chat chat;
	public static Shop shop;
	
	
	public static int translate_x;
	public static int translate_y;
	
	public static Rectangle b;
	
	public static boolean inMultiplayer = false;
	
	
	
	float x = 100;
	float y = 100;
	SpriteSheet p;
	Animation pp;
	Image m16;
	Image m16_1;
	
	Image health;
	Image health1;
	
	boolean rotate = true;
	boolean shooting = false;
	boolean rotate_back = false;
	float last = 0;
	
	
	float laserX = 201, laserY = 476;
	

	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		font = Menu.font;
		font1 = Menu.font1;
		
		tilemap = new TileMap();
		player = new Player(tilemap);
		gun = new Gun();
		client_handler = new ClientHandler();
		chat = new Chat(font);
		shop = new Shop(font);
		
		
    	p = new SpriteSheet("lib/res/Misc/p.png", 16, 30);
    	pp = new Animation(p, 150);
    	
    	m16 = new Image("lib/res/Misc/m16.png");
    	m16_1 = m16.getFlippedCopy(true, false);
    	
    	health = new Image("lib/res/Misc/health.png");
    	health1 = new Image("lib/res/Misc/health_scale.png");
		
    	
    	
	}

	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
//		g.setBackground(new Color(220, 220, 220));
//		g.setBackground(Color.black);
//		g.setBackground(Color.decode("#080925"));
		gc.setAlwaysRender(true);
		g.setBackground(Color.decode("#0D0923"));
		
		Input input = gc.getInput();
		int mx = input.getMouseX();
		int my = input.getMouseY();
		
		
		if(input.isKeyPressed(Input.KEY_BACK)){
			sbg.enterState(1);
		}
		
		
//	    translate_x = (int)-player.getX() + gc.getWidth()/2;
//	    translate_y = (int)-player.getY() + gc.getHeight()/2;
//		
//		glTranslatef(translate_x, translate_y, 0);
//	    
//	    tilemap.draw(g, gc, input);
//	    player.draw(g, gc, input, font);
//		gun.logic(g, gc, input);
		
		shop.draw(g, gc);
		
		
		
		
		
		
//		font.drawString(100, 100, "Bought", Color.black);
//		font1.drawString(100, 120, "Bought", Color.black);
		
//		client_handler.draw(g, gc, input, font);
//		
//		
//		
//		float xDistance = (mx) - 201;
//		float yDistance = 476 - (my);
//		float theta = (float) Math.toDegrees(Math.atan2(xDistance, yDistance));
//		m16.draw(201, 476);
//		m16.setCenterOfRotation(7, m16.getHeight()/2);
//		m16.setRotation(theta - 90);
//		
//		
//		theta = (float) (theta * Math.PI / 180); // converting to radians from degrees
//		float startX = 201;
//		float startY = 476 + 13/2;
//		float endX   = (float) (startX + 20 * Math.sin(theta));
//		float endY   = (float) (startY + 20 * -Math.cos(theta));
//		
//		g.setColor(new Color(255, 0, 0, .5f));
//		
//		Line line = new Line(endX + 7, endY - 1, mx, my);
//		g.draw(line);
		
		
		
//		chat.draw(g, gc);
		
		
		
		
		
		
		
		
		
		
//		if(tilemap.map[my/32][mx/32].equals("w")) System.out.println("YES");
		
		
		
//		// Reset viewport
//		glTranslatef(-translate_x, -translate_y, 0);
//		displayFPS(font, gc.getFPS());
		
		
//		player.healthbar(g, font);
//		client_handler.names(font, input);
		
		
		
		
//		pp.draw(x, y);
//		
//		boolean right = true;
//		boolean left = false;
//		float center = (100 + (16/2));
//		
//		if(mx > center) {
//			left = false;
//			right = true;
//		}
//		else if(mx < center) {
//			right = false;
//			left = true;
//		}
//		
//		
//		float xDistance = (mx) - (x + 1);
//		float yDistance = (y + 6) - (my);
//		float theta = (float) Math.toDegrees(Math.atan2(xDistance, yDistance));
//		
//		m16.setCenterOfRotation(7, m16.getHeight()/2);
//		if(rotate)
//			m16.setRotation(theta - 90);
//		
//		
//		if(right)
//			m16.draw(x + 1, y + 6);
//		
//		if(left){
//			m16_1.setCenterOfRotation(m16.getWidth() - 7, m16.getHeight()/2);
//			m16_1.draw(x - 16, y + 6);
//			m16_1.setRotation(theta + 90);
//		}
//		
//		font.drawString(20, 20, "$7000", Color.decode("#00942D"));
//		
//		health.draw((704 - health.getWidth())/2, 10);
//		
//		health1.getScaledCopy(300, 6).draw((704 - health.getWidth())/2 + 2, 12);
//		
//		font.drawString(100, 10, "100", new Color(194, 0, 0));
//		
//		
//		font.drawString(100, 300, "The quick brown fox jumps over the lazy dog.", Color.decode("#028CAA"));
//		
//		if(input.isMousePressed(0)){
//			last = m16.getRotation();
//			shooting = true;
//			rotate = false;
//		}
//		
//		if(shooting) {
//			m16.rotate(-3);
//			if(m16.getRotation() < last - 7) {
//				shooting = false;
//				rotate_back = true;
//			}
//		}
//		
//		if(rotate_back) {
//			rotate = true;
//			rotate_back = false;
//		}
//		
//		if(input.isKeyDown(Input.KEY_D)){
//			x+= 3;
//		}
		
		
		
		
		
	}
	
	public float to_float(String str){ return Float.parseFloat(str); }
	public int to_int(String str) { return Integer.parseInt(str); }
	
	public void displayFPS(TrueTypeFont font, int fps){
		font.drawString(10, 10, "FPS: " + fps);
	}

	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		Player.hero_left.update(delta);
		Player.hero_right.update(delta);
		
		
		pp.update(delta);
		
	}

	
	
	
	public int getID() {
		return 2;
	}

}
