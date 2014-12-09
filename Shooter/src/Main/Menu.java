package Main;
import java.awt.Font;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.lwjgl.input.Mouse;
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
		"World Builder",
		"Exit"
	};
	
	String[][] stats = new String[][] {
		{ "Name", name        },
		{ "Total Games", "0"  },
		{ "Total Wins", "0"   },
		{ "Total Losses", "0" },
		{ "Win Rate", "0"     },
		{ "Loss Rate", "0"    }
	};
	
	private int selected;
	private int last_selected;
	public static Sound click;
	
	private boolean playstate;
//	private int current_state = -1;
	
	public static boolean mute;
//	private Image mute_icon;
	
	public static Image M16;
	
	public static Image Pistol;
	
	public static Image Shotgun;
	
	

	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
    	try {
    		InputStream inputStream	= ResourceLoader.getResourceAsStream("lib/res/Fonts/visitor2.ttf");
    		Font awtFont = Font.createFont(Font.TRUETYPE_FONT, inputStream);
    		awtFont = awtFont.deriveFont(20f);
    		font = new TrueTypeFont(awtFont, false);
    		
    		InputStream inputStream1 = ResourceLoader.getResourceAsStream("lib/res/Fonts/RA1.ttf");
    		Font awtFont1 = Font.createFont(Font.TRUETYPE_FONT, inputStream1);
    		awtFont1 = awtFont1.deriveFont(20f);
    		font1 = new TrueTypeFont(awtFont1, false);
    		
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    	
//    	mute_icon = new Image("lib/res/Menu/MusicNote.png");
    	click = new Sound("lib/res/Sounds/click.wav");
    	gc.setMouseCursor(new Image("lib/res/misc/mouse.png"), 0, 0);
    	
    	M16 = new Image("lib/res/Guns/m16.png");
    	Pistol = new Image("lib/res/Guns/pistol.png");
    	Shotgun = new Image("lib/res/Shop/Shotgun_icon.png"); // TODO Change to actual gun, not icon
    	
    	
	}

	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		gc.setAlwaysRender(true);
		g.setBackground(new Color(220, 220, 220));
		
		if(playstate) sbg.enterState(2);
		
//		Input input = gc.getInput();
		
//		int mx = input.getMouseX();
//		int my = input.getMouseY();
		
//		Rectangle mouse = new Rectangle(mx, my, 1, 1);
		
//		if(input.isKeyPressed(Input.KEY_BACK)) current_state = -1;
//		
//		
//		if(current_state == -1) menu(sbg, g, gc, input, font1, mouse);
//		
//		else if(current_state == 2){
//			// statistics
//			stats(g, gc, input, font, font1, mouse);
//		}
//		
//		mute_icon.draw(gc.getWidth() - mute_icon.getWidth() - 10, 10);
//		
//		if(current_state != -1)
//			font.drawString(10, gc.getHeight() - font.getHeight("Back") - 5, "Back", Color.black);
		
		
//		pp.draw(200, 200);
		
		
//		Line line = new Line(0, 234, 500, 234);
//		g.draw(line);
		
		
//		float xDistance = (mx) - 201;
//		float yDistance = 216 - (my);
//		float theta = (float) Math.toDegrees(Math.atan2(xDistance, yDistance));
//		m16.draw(201, 216);
//		m16.setCenterOfRotation(7, m16.getHeight()/2);
//		m16.setRotation(theta - 90);
//		
//		
//		theta = (float) (theta * Math.PI / 180); // converting to radians from degrees
//		float startX = 201;
//		float startY = 216 + 13/2;
//		float endX   = (float) (startX + 20 * Math.sin(theta));
//		float endY   = (float) (startY + 20 * -Math.cos(theta));
//		
//		g.setColor(new Color(255, 0, 0, .5f));
//		
//		Line line = new Line(endX + 7, endY - 1, mx, my);
//		g.draw(line);
		
		
		
		
		
		
		
		
		
		
//		if(pp.getFrame() == 2 || pp.getFrame() == 3 || pp.getFrame() == 7 || pp.getFrame() == 8){
//			m16.draw(201, 206);
//		}else
//			m16.draw(201, 210);
//		m16.setCenterOfRotation(7, m16.getHeight()/2);
//		m16.setRotation(theta - 90);
		
		
	}
	
	
	private void selection(StateBasedGame sbg, int i){
//		current_state = i;
		
		
		if(i == 0){
			playstate = true;
		}else if(i == 1){
			Play.inMultiplayer = true;
			Thread client = new Thread(new Runnable() {
			     public void run() {
			    	 Client.running();
			     }
			});  
			client.start();
			sbg.enterState(2);
		}else if(i == 2){
//			current_state = i;
		}else if(i == 3){
			sbg.enterState(3);
			Editor.chose_map = false;
		}
		
		else if(i == options.length - 1){
			System.exit(0);
		}
		
		
	}
	
	public void stats(Graphics g, GameContainer gc, Input input, TrueTypeFont font, TrueTypeFont font1, Rectangle mouse){
		String statistics = "- Statistics -";
		font1.drawString((gc.getWidth() - font1.getWidth(statistics))/2, 10, statistics, Color.black);
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
		font1.drawString(x, y, s, color);
	}
	
	public void menu(StateBasedGame sbg, Graphics g, GameContainer gc, Input input, TrueTypeFont font1, Rectangle mouse){
		for(int i=0;i<options.length;i++){
			int w = font1.getWidth("" + options[i]);
			int h = font1.getHeight("" + options[i]);
			Rectangle box = new Rectangle((gc.getWidth() - w)/2, 100 + i * (h+5), w, h);
			
			if(mouse.intersects(box)){
				selected = i;
				if(last_selected != selected) click.play(1, .2f);
				if(input.isMousePressed(0)){
					selection(sbg, i);
				}
			}
			
			if(selected == i){
				font1.drawString((gc.getWidth() - w)/2, 100 + i * (h+5), options[i]);
				font1.drawString(((gc.getWidth() - w)/2) - font1.getWidth("--") - 10, 100 + i * (h+5), "--");
				font1.drawString(((gc.getWidth() - w)/2) + w + 10, 100 + i * (h+5), "--");
			}else{
				font1.drawString((gc.getWidth() - w)/2, 100 + i * (h+5), options[i], Color.gray);
			}
			last_selected = selected;
		}
		
		
		int dWheel = Mouse.getDWheel();
		if (dWheel > 0) {
			if(selected <= 0) selected = options.length - 1;
			else selected --;
		} else if (dWheel < 0){
			if(selected == (options.length - 1)) selected = 0;
			else selected ++;
		}
		
		
		input.enableKeyRepeat();
		
		if(input.isKeyPressed(Input.KEY_ENTER)){
			selection(sbg, selected);
		}
		
		else if(input.isKeyPressed(Input.KEY_S) || input.isKeyPressed(Input.KEY_DOWN)){
			click.play(1, .2f);
			if(selected == (options.length - 1))
				selected = 0;
			else
				selected ++;
		}
		
		else if(input.isKeyPressed(Input.KEY_W) || input.isKeyPressed(Input.KEY_UP)){
			click.play(1, .2f);
			if(selected == 0)
				selected = options.length - 1;
			else
				selected --;
		}
		
		String signature = "- Developed by: Temuulen Khurelbaatar -";
		font.drawString((gc.getWidth() - font.getWidth(signature))/2, gc.getHeight() - 30, signature, Color.black);
		
		String title = "Shooter";
		int pos = (gc.getWidth() - font1.getWidth(title))/2;
		write(pos, 30, title, Color.black);
		
	}
	
	
	

	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
	}

	
	
	

	public int getID(){
		return 1;
	}
	

}
