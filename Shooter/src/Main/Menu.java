package Main;
import java.awt.Font;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

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
import org.newdawn.slick.util.ResourceLoader;


public class Menu extends BasicGameState{
	public Menu(int state){}
	
	public static TrueTypeFont font;
	public static TrueTypeFont font1;
	
	public static String name = System.getProperty("user.name");
	
	String[] options = new String[] {
		"Training",
		"Multiplayer",
		"Statistics",
		"Highscores",
		"World Builder",
		"Quit"
	};
	
	String[][] stats = new String[][] {
		{ "Name", name        },
		{ "Total Games", "0"  },
		{ "Total Wins", "0"   },
		{ "Total Losses", "0" },
		{ "Win Rate", "0"     },
		{ "Loss Rate", "0"    }
	};
	
	private int last_selected = -1;
	public static Sound click;
	
	private int current_state = -1;
	
	public static boolean mute;
	
	public static Image M16;
	
	public static Image Pistol;
	
	public static Image Shotgun;
	
	
	private Image Background;
	private Image toolbar;
	
	private Image img;
	int rr = 0;
	int gg = 0;
	int bb = 0;
	
	Random rand = new Random();
	
	public static boolean enteredWorldBuilder;
	

	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
    	try {
    		InputStream inputStream	= ResourceLoader.getResourceAsStream("lib/res/Fonts/visitor2.ttf");
    		Font awtFont = Font.createFont(Font.TRUETYPE_FONT, inputStream);
    		awtFont = awtFont.deriveFont(20f);
    		font = new TrueTypeFont(awtFont, false);
    		
    		InputStream inputStream1 = ResourceLoader.getResourceAsStream("lib/res/Fonts/ocr.ttf");
    		Font awtFont1 = Font.createFont(Font.TRUETYPE_FONT, inputStream1);
    		awtFont1 = awtFont1.deriveFont(10f);
    		font1 = new TrueTypeFont(awtFont1, false);
    		
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    	
//    	mute_icon = new Image("lib/res/Menu/MusicNote.png");
    	click = new Sound("lib/res/Sounds/click.wav");
    	gc.setMouseCursor(new Image("lib/res/Misc/mouse.png"), 0, 0);
    	
    	M16 = new Image("lib/res/Guns/m16.png");
    	Pistol = new Image("lib/res/Guns/pistol.png");
    	Shotgun = new Image("lib/res/Shop/Shotgun_icon.png"); // TODO Change to actual gun, not icon
    	
    	Background = new Image("lib/res/Menu/Background.png");
    	toolbar = new Image("lib/res/Misc/toolbar.png");
    	
    	img = new Image("lib/res/Misc/logo.png");
    	
	}

	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		gc.setAlwaysRender(true);
		g.setBackground(new Color(220, 220, 220));
//		g.setBackground(Color.decode("#0D0923"));
//		g.setBackground(Color.black);
		
		Input input = gc.getInput();
		
		int mx = input.getMouseX();
		int my = input.getMouseY();
		
		Rectangle mouse = new Rectangle(mx, my, 1, 1);
		
		Background.draw(0, 0);
		
		
		if(input.isKeyPressed(Input.KEY_BACK)) current_state = -1;
		
		
		if(current_state == -1) menu(sbg, g, gc, input, font1, mouse);
		
		else if(current_state == 2){
			// statistics
			stats(g, gc, input, font, font1, mouse);
		}
		
		if(current_state != -1){
			font.drawString(20, gc.getHeight() - font.getHeight("Back") - 2, "Back", Color.black);
		}
		
		
		
	}
	
	
	private void selection(StateBasedGame sbg, int i){
		if(options[i].equalsIgnoreCase("training")){
			Play.inMultiplayer = false;
			sbg.enterState(2);
		}
		
		else if(options[i].equalsIgnoreCase("quit")){
			if(enteredWorldBuilder)
				WorldBuilder.Write();
			System.exit(0);
		}else if(options[i].equalsIgnoreCase("multiplayer")){
			Play.inMultiplayer = true;
			Thread client = new Thread(new Runnable() {
			     public void run() {
			    	 Client.running();
			     }
			});  
			client.start();
			sbg.enterState(2);
		}else if(options[i].equalsIgnoreCase("statistics")){
			current_state = i;
		}else if(options[i].equalsIgnoreCase("world builder")){
			enteredWorldBuilder = true;
			WorldBuilder.selecting = true;
			sbg.enterState(3);
		}
		
		
	}
	
	public void stats(Graphics g, GameContainer gc, Input input, TrueTypeFont font, TrueTypeFont font1, Rectangle mouse){
		String statistics = "- Statistics -";
		font.drawString((gc.getWidth() - font1.getWidth(statistics))/2, 10, statistics, Color.black);
		// #336699 - Blue
		// #669999 - Greenish
		
		
		int half = gc.getWidth()/2;
		
		int line_height = 0;
		for(int i=0;i<stats.length;i++){
			int w = font1.getWidth(stats[i][0]);
			int h = font1.getHeight(stats[i][0]);
			line_height += (h + 5);
			write(half - w - 10, 100 + (i * (h + 5)), stats[i][0], Color.black);
			write(half + 10, 100 + (i * (h + 5)), stats[i][1], Color.black);
		}
		
		
		DecimalFormat df = new DecimalFormat("#.##");
		stats[4][1] = (to_float(stats[2][1]) > 0) ? "" + df.format(to_float(stats[2][1]) / to_float(stats[1][1])) : "0";
		stats[5][1] = (to_float(stats[3][1]) > 0) ? "" + df.format(to_float(stats[3][1]) / to_float(stats[1][1])) : "0";
		
		if(input.isKeyPressed(Input.KEY_W)){
			stats[1][1] = "" + (to_int(stats[1][1]) + 1);
			stats[2][1] = "" + (to_int(stats[2][1]) + 1);
		}else if(input.isKeyPressed(Input.KEY_S)){
			stats[1][1] = "" + (to_int(stats[1][1]) + 1);
			stats[3][1] = "" + (to_int(stats[3][1]) + 1);
		}
		
		
		g.setColor(Color.black);
		g.drawLine(half, 100, half, 100 + line_height);
		
		
		DateFormat dateFormat = new SimpleDateFormat("HHmm");
		Date date = new Date();
		try {
			int milTime = Integer.parseInt(dateFormat.format(date));
			date = new SimpleDateFormat("hhmm").parse(String.format("%04d", milTime));
			SimpleDateFormat sdf = new SimpleDateFormat("h:mm a");
			String ss = sdf.format(date);
			font.drawString((gc.getWidth() - font.getWidth(ss))/2, gc.getHeight() - font.getHeight(ss) - 10, ss, Color.black);
		} catch (ParseException e) {
		}

		

	}
	
	private float to_float(String str){
		return Float.parseFloat(str);
	}
	
	private int to_int(String str){
		return Integer.parseInt(str);
	}
	
	private void write(int x, int y, String s, Color color){
		font.drawString(x, y, s, color);
	}
	
	private float center(float x, float y, float z){
		return x + (y - z)/2;
	}
	
	private int width(String text) { return font.getWidth(text); }
	private int height(String text) { return font.getHeight(text); }
	
	public void menu(StateBasedGame sbg, Graphics g, GameContainer gc, Input input, TrueTypeFont font1, Rectangle mouse){

		for(int i=0;i<options.length;i++){
			int w = toolbar.getWidth();
			int h = toolbar.getHeight();
			
			int x = (int) center(0, 704, w);
			int y = 100 + (h * 2) * i;
			
			Rectangle box = new Rectangle(x, y, w, h);
			
			if(mouse.intersects(box)){
				toolbar.draw(center(0, 704, w), y - 1);
				if(i != last_selected){
					rr = rand.nextInt(255);
					gg = rand.nextInt(255);
					bb = rand.nextInt(255);
				}
				font.drawString((int)center(x, w, width(options[i])), y + (h - height(options[i]))/2 - 1, options[i], new Color(rr, gg, bb));
				
				last_selected = i;
				if(input.isMousePressed(0)){
					click.play(1, .3f);
					selection(sbg, i);
				}
			}else{
				toolbar.draw(center(0, 704, w), y);
				font.drawString((int)center(x, w, width(options[i])), y + (h - height(options[i]))/2, options[i], Color.decode("#C1C3CE"));
			}
		
			
		}
		
		String signature = "Temuulen Khurelbaatar";
		font.drawString((int)center(0, 704, width(signature)), 500, signature, Color.black);
		
		img.draw(center(0, 704, img.getWidth()), 40);
		write((int)center(0, 704, width("8bit Shooter")), 20, "8bit Shooter", Color.white);
		
		
	}
	
	
	

	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
	}

	
	
	

	public int getID(){
		return 1;
	}
	

}
