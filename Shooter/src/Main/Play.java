package Main;
import java.util.ArrayList;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import Weapons.Pistol;
import Weapons.Weapons;


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
	
	public static Image wall;
	public static Image background;
	
	private Music background_music;
	
	
	Image pistol;
	
	
	ArrayList<Weapons> guns = new ArrayList<Weapons>(0);
	

	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		font = Menu.font;
		font1 = Menu.font1;
		
		shop = new Shop(font);
		
		tilemap = new TileMap();
		player = new Player(tilemap);
		gun = new Gun();
		client_handler = new ClientHandler();
		chat = new Chat(font);
		
		
    	p = new SpriteSheet("lib/res/Misc/p.png", 16, 30);
    	pp = new Animation(p, 150);
    	
    	m16 = new Image("lib/res/Misc/m16.png");
    	m16_1 = m16.getFlippedCopy(true, false);
    	
    	pistol = new Image("lib/res/Misc/pistol.png");
    	
    	health = new Image("lib/res/Misc/health.png");
    	health1 = new Image("lib/res/Misc/health_scale.png");
		
    	
    	wall = new Image("lib/res/Misc/wall.png");
    	background = new Image("lib/res/Misc/background.png");
    	
    	background_music = new Music("lib/res/Sounds/Derek.wav");
    	
    	
    	guns.add(new Pistol(Menu.Pistol, Shop.Pistol_icon, Shop.Pistol_white));
    	
    	
	}

	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
//		g.setBackground(new Color(220, 220, 220));
//		g.setBackground(Color.black);
//		g.setBackground(Color.decode("#080925"));
		gc.setAlwaysRender(true);
		g.setBackground(Color.decode("#0D0923"));
		
		Input input = gc.getInput();
//		int mx = input.getMouseX();
//		int my = input.getMouseY();
		
		
		if(input.isKeyPressed(Input.KEY_BACK)){
			sbg.enterState(1);
		}
		
		
//	    translate_x = (int)-player.getX() + gc.getWidth()/2;
//	    translate_y = (int)-player.getY() + gc.getHeight()/2;
//		
//		glTranslatef(translate_x, translate_y, 0);

//		if(background_music.playing() == false){
//			background_music.loop();
//		}
		
		g.drawString("" + gc.getFPS(), 100, 100);
		
		tilemap.draw(g, gc, input);
	    player.draw(g, gc, input, font);
		gun.logic(g, gc, input);
//		
//		shop.draw(g, gc);
	    
	    
//	    pistol.draw(((player.getX() + 32 ) - pistol.getWidth()), player.getY() + 8);
	    
		
		// PISTOL - 9 PIXELS
		
//	    guns.get(0).gunRight().draw((player.getX() + 16) + guns.get(0).OFFSET().getX(), player.getY() + guns.get(0).OFFSET().getY());
	    
	    
//	    Point OFFSET_X = new Point(1, 8);
//	    
//		if(Player.facing_right){
//			pistol.draw((player.getX() + 16) + OFFSET_X.getX(), player.getY() + OFFSET_X.getY());
//			
//			
//		}else if(Player.facing_left){
//	
//			pistol.getFlippedCopy(true, false).draw((player.getX()) - OFFSET_X.getX() - pistol.getWidth(), player.getY() + OFFSET_X.getY());
//			
//		}
		
	    
		
		health.draw((704 - health.getWidth())/2, 10);
		float scale = health.getWidth() / player.max_health();
		health1.getScaledCopy(Player.health * (int) scale, 6).draw((704 - health.getWidth())/2 + 2, 12);
		font.drawString((704 - health.getWidth())/2 + (health.getWidth() - font.getWidth("" + Player.health))/2, 
				10 + (health.getHeight() - font.getHeight("1"))/2, 
				"" + Player.health);
		
		
		
		if(input.isKeyPressed(Input.KEY_T)){
			player.set_health(player.health() + 2);
		}
		
		
		
//		// Reset viewport
//		glTranslatef(-translate_x, -translate_y, 0);
//		displayFPS(font, gc.getFPS());
		
		
		
		
	}
	
	public float to_float(String str){ return Float.parseFloat(str); }
	public int to_int(String str) { return Integer.parseInt(str); }
	
	public void displayFPS(TrueTypeFont font, int fps){
		font.drawString(10, 10, "FPS: " + fps);
	}

	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		Player.hero_left.update(delta);
		Player.hero_right.update(delta);
		
		
		
	}

	
	
	
	public int getID() {
		return 2;
	}

}
