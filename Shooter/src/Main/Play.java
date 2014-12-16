package Main;
import static org.lwjgl.opengl.GL11.glTranslatef;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
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
	public static Image button;
	
//	private Music background_music;
	
	public static Sound gun_sound;
	
	ClientHandler clientHandler;
	
	
	ArrayList<Weapons> guns = new ArrayList<Weapons>(0);
	public static Image skull;
	
	public static ArrayList<FloatingText> text = new ArrayList<FloatingText>(0);
	

	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		font = Menu.font;
		font1 = Menu.font1;
		
		shop = new Shop(font);
		
		tilemap = new TileMap();
		player = new Player(tilemap);
		gun = new Gun();
		client_handler = new ClientHandler();
		chat = new Chat(font);
		
		
    	m16 = new Image("lib/res/Misc/m16.png");
    	m16_1 = m16.getFlippedCopy(true, false);
    	
    	health = new Image("lib/res/Misc/health.png");
    	health1 = new Image("lib/res/Misc/health_scale.png");
		
    	
    	wall = new Image("lib/res/Misc/wall.png");
    	background = new Image("lib/res/Misc/background.png");
    	skull = new Image("lib/res/Misc/skull.png");
    	button = new Image("lib/res/Misc/button.png");
    	
//    	background_music = new Music("lib/res/Sounds/Derek.wav");
    	gun_sound = new Sound("lib/res/Sounds/gun.wav");
    	
    	
    	guns.add(new Pistol(Menu.Pistol, Shop.Pistol_icon, Shop.Pistol_white));
    	
    	clientHandler = new ClientHandler();
    	
    	
    	
	}

	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		gc.setAlwaysRender(true);
		g.setBackground(Color.decode("#003366"));
		
		Input input = gc.getInput();
		
		
		if(input.isKeyPressed(Input.KEY_BACK)){
			sbg.enterState(1);
		}
		
		
	    translate_x = (int)-Player.freeCamX + gc.getWidth()/2;
	    translate_y = (int)-Player.freeCamY + gc.getHeight()/2;
		
		glTranslatef(translate_x, translate_y, 0);

//		if(background_music.playing() == false){
//			background_music.loop();
//		}
		
		g.drawString("" + gc.getFPS(), 100, 100);
		
		tilemap.draw(g, gc, input);
	    player.draw(g, gc, input, font);
		gun.logic(g, gc, input);
		
		clientHandler.draw(g, gc, input, font1);
		
		for(int i=0;i<text.size();i++){
			if(text.get(i).remove) text.remove(i);
			else{
				text.get(i).draw(g, gc, font1);
			}
		}
		
		
		// TODO - Spectators mode, alert system
		
		// ----------------------------------------------------------------------------------------
		
		
		// Reset viewport
		glTranslatef(-translate_x, -translate_y, 0);

		clientHandler.names(font1, input);
		
		
		shop.draw(g, gc);
	    
		
		Gun.wielding.icon().draw(10, 10);         
		
		g.setAntiAlias(false);
		g.setLineWidth(1);
		
		for(int i=0;i<Gun.wielding.magezine() + 1;i++){
			if(i <= Gun.wielding.ammo()){
				g.setColor(Color.white);
			}else{
				g.setColor(Color.darkGray);
			}
			Rectangle box = new Rectangle(
					20 + Gun.wielding.icon().getWidth() + (4 * i), 
					15, 
					2,
					6
				);
			g.fill(box);
		}

		
		health.draw((704 - health.getWidth())/2, 10);
		float scale = health.getWidth() / player.max_health();
		health1.getScaledCopy(Player.health * (int) scale, 6).draw((704 - health.getWidth())/2 + 2, 12);
		font.drawString((704 - health.getWidth())/2 + (health.getWidth() - font.getWidth("" + Player.health))/2, 
				10 + (health.getHeight() - font.getHeight("1"))/2, 
				"" + Player.health);
		
		
		if(Player.dead){
			g.setColor(new Color(0, 0, 0, .4f));
			g.fillRect(0, 0, gc.getWidth(), gc.getHeight());
			String slainBy = "Slain: " + Player.lastHitBy;
			font.drawString((704 - font.getWidth(slainBy))/2, 30, slainBy, Color.decode("#C1C3CE"));
			font.drawString((int) ((704 - font.getWidth("Respawn in: " + Player.deathTimer))/2), 50, "Respawn in: " + Player.deathTimer, Color.decode("#C30000"));
		}
		
		
		lostFocus(g, gc);
		
		
	}
	
	
	public void lostFocus(Graphics g, GameContainer gc){
		if(gc.hasFocus() == false){
			g.setColor(new Color(0, 0, 0, .4f));
			g.fillRect(0, 0, gc.getWidth(), gc.getHeight());
			String idle = "- Lost Focus -";
			font.drawString((704 - font.getWidth(idle))/2, (544 - font.getHeight(idle))/2, idle, Color.orange);
			DateFormat dateFormat = new SimpleDateFormat("HHmm");
			Date date = new Date();
			try {
				int milTime = Integer.parseInt(dateFormat.format(date));
				date = new SimpleDateFormat("hhmm").parse(String.format("%04d", milTime));
				SimpleDateFormat sdf = new SimpleDateFormat("h:mm");
				String ss = sdf.format(date);
				font.drawString((gc.getWidth() - font.getWidth(ss))/2, gc.getHeight() - font.getHeight(ss) - 10, ss, Color.orange);
			} catch (ParseException e) {
			}
		}
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
